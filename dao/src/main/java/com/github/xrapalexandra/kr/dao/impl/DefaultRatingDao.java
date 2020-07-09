package com.github.xrapalexandra.kr.dao.impl;

import com.github.xrapalexandra.kr.dao.RatingDao;
import com.github.xrapalexandra.kr.dao.converter.RatingConverter;
import com.github.xrapalexandra.kr.dao.entity.RatingEntity;
import com.github.xrapalexandra.kr.dao.repository.RatingRepository;
import com.github.xrapalexandra.kr.model.Product;
import com.github.xrapalexandra.kr.model.Rating;

import java.util.List;
import java.util.stream.Collectors;

public class DefaultRatingDao implements RatingDao {

    private final RatingRepository repository;

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
    public Double getAvrRatingByProduct(Product product) {
        return repository.getAvgByProduct(product.getId());
    }

    @Override
    public List<Rating> getProductRating(Integer productId) {
        List<RatingEntity> ratingEntityList = repository.findByProductId(productId);
        return ratingEntityList.stream()
                .map(RatingConverter::fromEntity)
                .collect(Collectors.toList());
    }

}
