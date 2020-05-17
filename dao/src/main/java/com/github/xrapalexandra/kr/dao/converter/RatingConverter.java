package com.github.xrapalexandra.kr.dao.converter;

import com.github.xrapalexandra.kr.dao.entity.RatingEntity;
import com.github.xrapalexandra.kr.model.Rating;

public class RatingConverter {

    public static RatingEntity toEntity(Rating rating) {
        if (rating == null)
            return null;
        final RatingEntity ratingEntity = new RatingEntity();
        ratingEntity.setId(rating.getId());
        ratingEntity.setMark(rating.getMark());
        ratingEntity.setProduct(ProductConverter.toEntity(rating.getProduct()));
        ratingEntity.setUser(UserConverter.toEntity(rating.getUser()));
        return ratingEntity;
    }

    public static Rating fromEntity(RatingEntity ratingEntity){
        if (ratingEntity == null)
            return null;
        final Rating rating = new Rating(
                ratingEntity.getMark(),
                UserConverter.fromEntity(ratingEntity.getUser()),
                ProductConverter.fromEntity(ratingEntity.getProduct())
        );
        rating.setId(ratingEntity.getId());
        return rating;
    }
}
