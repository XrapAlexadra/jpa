package com.github.xrapalexandra.kr.service;

import com.github.xrapalexandra.kr.model.Product;
import com.github.xrapalexandra.kr.model.Rating;

public interface RatingService {

    Integer addRating(Rating rating);

    Integer getAvrRatingByProduct(Product product);
}
