package com.github.xrapalexandra.kr.dao.impl;

import com.github.xrapalexandra.kr.dao.RatingDao;
import com.github.xrapalexandra.kr.dao.converter.ProductConverter;
import com.github.xrapalexandra.kr.dao.converter.RatingConverter;
import com.github.xrapalexandra.kr.dao.entity.ProductEntity;
import com.github.xrapalexandra.kr.dao.entity.RatingEntity;
import com.github.xrapalexandra.kr.dao.repository.RatingRepository;
import com.github.xrapalexandra.kr.model.Product;
import com.github.xrapalexandra.kr.model.Rating;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.stream.Collectors;

public class DefaultRatingDao implements RatingDao {

    private RatingRepository repository;

    public DefaultRatingDao(RatingRepository repository) {
        this.repository = repository;
    }

    @Override
    public Integer addRating(Rating rating) {
        RatingEntity ratingEntity = RatingConverter.toEntity(rating);
        repository.save(ratingEntity);
        return ratingEntity.getId();
    }

    @Override
    public Boolean delRating(Integer ratingId) {
        try {
            repository.deleteById(ratingId);
            return true;
        }
        catch (DataIntegrityViolationException e){
            return false;
        }
    }

    @Override
    public Double getAvrRatingByProduct(Product product) {
        ProductEntity productEntity = ProductConverter.toEntity(product);
        return repository.getAvgByProduct(productEntity);
    }

    @Override
    public List<Rating> getProductRating(Integer productId) {
        List<RatingEntity> ratingEntityList = repository.findByProductId(productId);
        return ratingEntityList.stream()
                .map(RatingConverter::fromEntity)
                .collect(Collectors.toList());
    }

}
