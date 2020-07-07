package com.github.xrapalexandra.kr.dao.impl;

import com.github.xrapalexandra.kr.dao.OrderDao;
import com.github.xrapalexandra.kr.dao.converter.OrderConverter;
import com.github.xrapalexandra.kr.dao.entity.OrderContentEntity;
import com.github.xrapalexandra.kr.dao.entity.OrderEntity;
import com.github.xrapalexandra.kr.dao.repository.OrdersRepository;
import com.github.xrapalexandra.kr.model.Order;
import com.github.xrapalexandra.kr.model.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.stream.Collectors;


public class DefaultOrderDao implements OrderDao {

    private final OrdersRepository repository;

    public DefaultOrderDao(OrdersRepository repository) {
        this.repository = repository;
    }

    @Override
    public Integer addOrder(Order order) {
        OrderEntity orderEntity = OrderConverter.toEntity(order);

        for (OrderContentEntity i : orderEntity.getOrderContentList()) {
            i.setOrder(orderEntity);
        }
        repository.save(orderEntity);
        return orderEntity.getId();
    }

    @Override
    public void deleteOrder(Integer orderId) {
        repository.deleteById(orderId);
    }

    @Override
    public Page<Order> getAllOrders(int page, int number) {
        Page<OrderEntity> orderEntityPage = repository.findAll(PageRequest.of(page-1, number, Sort.by("id")));
        return orderEntityPage
                .map(OrderConverter::fromEntity);
    }

    @Override
    public List<Order> getUserOrders(String login) {
        List<OrderEntity> orderEntityList = repository.findByUserLogin(login);
        if (orderEntityList.isEmpty())
            return null;
        return orderEntityList.stream()
                .map(OrderConverter::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void changeOrderStatus(Integer orderId, Status status) {
        repository.updateOrderStatus(status, orderId);
    }

    @Override
    public List<Order> getPaidOrders() {
        List<OrderEntity> orderEntityList = repository.findByStatus(Status.PAID);
        return orderEntityList.stream()
                .map(OrderConverter::fromEntity)
                .collect(Collectors.toList());
    }

}

