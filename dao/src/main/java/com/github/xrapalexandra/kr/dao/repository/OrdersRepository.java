package com.github.xrapalexandra.kr.dao.repository;

import com.github.xrapalexandra.kr.dao.entity.OrderEntity;
import com.github.xrapalexandra.kr.model.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface OrdersRepository extends JpaRepository<OrderEntity, Integer> {

        Page<OrderEntity> findAll(Pageable pageable);

        List<OrderEntity> findByUserLogin(String login);

        List<OrderEntity> findByStatus(Status status);

        @Transactional
        @Modifying
        @Query(value = "update OrderEntity oe set oe.status = :status where oe.id = :id")
        void updateOrderStatus(@Param("status") Status status, @Param("id") Integer id);
}
