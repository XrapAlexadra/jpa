package com.github.xrapalexandra.kr.dao.impl;

import com.github.xrapalexandra.kr.dao.RatingDao;
import com.github.xrapalexandra.kr.dao.converter.ProductConverter;
import com.github.xrapalexandra.kr.dao.converter.RatingConverter;
import com.github.xrapalexandra.kr.dao.entity.ProductEntity;
import com.github.xrapalexandra.kr.dao.entity.RatingEntity;
import com.github.xrapalexandra.kr.dao.util.HibernateUtil;
import com.github.xrapalexandra.kr.model.Product;
import com.github.xrapalexandra.kr.model.Rating;
import org.hibernate.Session;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.stream.Collectors;

public class DefaultRatingDao implements RatingDao {

    private static volatile RatingDao instance;

    public static RatingDao getInstance() {
        RatingDao localInstance = instance;
        if (localInstance == null) {
            synchronized (RatingDao.class) {
                localInstance = instance;
                if (localInstance == null)
                    instance = localInstance = new DefaultRatingDao();

            }
        }
        return localInstance;
    }

    @Override
    public Integer addRating(Rating rating) {
        RatingEntity ratingEntity = RatingConverter.toEntity(rating);
        final Session session = HibernateUtil.getSession();
        session.beginTransaction();
        session.save(ratingEntity);
        session.getTransaction().commit();
        return ratingEntity.getId();
    }

    @Override
    public void delRating(Integer ratingId) {
        final Session session = HibernateUtil.getSession();
        session.beginTransaction();
        RatingEntity ratingEntity = session.get(RatingEntity.class, ratingId);
        System.out.println(ratingEntity);
        session.delete(ratingEntity);
        session.getTransaction().commit();
    }

    @Override
    public Double getAvrRatingByProduct(Product product) {
        ProductEntity productEntity = ProductConverter.toEntity(product);

        final Session session = HibernateUtil.getSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Double> criteria = cb.createQuery(Double.class);
        Root<RatingEntity> rating = criteria.from(RatingEntity.class);

        criteria.where(cb.equal(rating.get("product"), productEntity));
        criteria.select(cb.avg(rating.get("mark")));
        Double average = session.createQuery(criteria).getSingleResult();

        return average;
    }

    @Override
    public List<Rating> getProductRating(Integer productId) {
        final Session session = HibernateUtil.getSession();
        ProductEntity productEntity = session.get(ProductEntity.class, productId);
        List<RatingEntity> ratingList = productEntity.getRatingList();

        return ratingList.stream()
                .map(RatingConverter::fromEntity)
                .collect(Collectors.toList());
    }


}