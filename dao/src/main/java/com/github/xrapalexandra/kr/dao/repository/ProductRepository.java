package com.github.xrapalexandra.kr.dao.repository;

import com.github.xrapalexandra.kr.dao.entity.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<ProductEntity, Integer>{

    Page<ProductEntity> findAll(Pageable pageable);

    ProductEntity findByName(String name);
}
