package com.github.xrapalexandra.kr.dao.repository;

import com.github.xrapalexandra.kr.dao.entity.ProductEntity;
import com.github.xrapalexandra.kr.dao.entity.RatingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RatingRepository extends JpaRepository<RatingEntity, Integer> {

    List<RatingEntity> findByProductId(Integer productId);

    @Query("select avg (mark) from RatingEntity where product = :product")
    Double getAvgByProduct(@Param("product") ProductEntity productEntity);
}
