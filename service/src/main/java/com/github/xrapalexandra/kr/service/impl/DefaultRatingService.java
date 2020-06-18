package com.github.xrapalexandra.kr.service.impl;

import com.github.xrapalexandra.kr.dao.RatingDao;
import com.github.xrapalexandra.kr.model.Product;
import com.github.xrapalexandra.kr.model.Rating;
import com.github.xrapalexandra.kr.service.RatingService;
import com.github.xrapalexandra.kr.service.util.ServiceUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import java.lang.invoke.MethodHandles;
import java.util.List;

@Transactional
public class DefaultRatingService implements RatingService {

    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private RatingDao ratingDao;

    public DefaultRatingService(RatingDao ratingDao) {
        this.ratingDao = ratingDao;
    }

    @Override
    public Integer addRating(Rating rating) {
        List<Rating> ratingList = ratingDao.getProductRating(rating.getProduct().getId());
        if (ServiceUtil.isUserExist(ratingList, rating.getUser())) {
            logger.info("Not add {} into DataBase.", rating);
            return null;
        }
        rating.setId(ratingDao.addRating(rating));
        logger.info("Add {} into DataBase.", rating);
        return rating.getId();
    }

    @Override
    public void delRating(Integer ratingId) {
        ratingDao.delRating(ratingId);
        logger.info("Delete {} from DataBase.", ratingId);
    }

    @Override
    public Integer getAvrRatingByProduct(Product product) {
        Double average = ratingDao.getAvrRatingByProduct(product);
        if (average == null)
            return null;
        return (int) Math.round(average);
    }
}
