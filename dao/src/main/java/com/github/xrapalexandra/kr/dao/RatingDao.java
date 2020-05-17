package com.github.xrapalexandra.kr.dao;

import com.github.xrapalexandra.kr.model.Product;
import com.github.xrapalexandra.kr.model.Rating;

import java.util.List;

public interface RatingDao {

    Integer addRating(Rating rating);

    void delRating(Integer ratingId);

    Double getAvrRatingByProduct(Product product);

    List<Rating> getProductRating(Integer productId);
}
