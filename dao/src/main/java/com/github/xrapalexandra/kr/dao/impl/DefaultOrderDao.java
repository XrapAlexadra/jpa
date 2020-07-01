package com.github.xrapalexandra.kr.dao.impl;

import com.github.xrapalexandra.kr.dao.OrderDao;
import com.github.xrapalexandra.kr.dao.converter.OrderConverter;
import com.github.xrapalexandra.kr.dao.entity.OrderContentEntity;
import com.github.xrapalexandra.kr.dao.entity.OrderEntity;
import com.github.xrapalexandra.kr.dao.repository.OrdersPagingRepository;
import com.github.xrapalexandra.kr.dao.repository.OrdersRepository;
import com.github.xrapalexandra.kr.model.Order;
import com.github.xrapalexandra.kr.model.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.stream.Collectors;


public class DefaultOrderDao implements OrderDao {

    private OrdersRepository repository;
    private OrdersPagingRepository pagingRepository;

    public DefaultOrderDao(OrdersRepository repository, OrdersPagingRepository pagingRepository) {
        this.repository = repository;
        this.pagingRepository = pagingRepository;
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
    public void delOrder(Integer orderId) {
        repository.deleteById(orderId);
    }

    @Override
    public Page<Order> getAllOrders(int page, int number) {
        Page<OrderEntity> orderEntityPage = pagingRepository.findAll(PageRequest.of(page-1, number, Sort.by("id")));
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
//        Optional<OrderEntity> optionalOrder = repository.findById(orderId);
//        if(optionalOrder.isPresent()) {
//            OrderEntity orderEntity = optionalOrder.get();
//            orderEntity.setStatus(Status.PAID);
//            repository.saveAndFlush(orderEntity);
//        }
    }

    @Override
    public List<Order> getPaidOrders() {
        List<OrderEntity> orderEntityList = repository.findByStatus(Status.PAID);
        return orderEntityList.stream()
                .map(OrderConverter::fromEntity)
                .collect(Collectors.toList());
    }


    @Override
    public Integer getPageCount(int number) {
        int orderCount = (int) repository.count();
        return orderCount % number == 0 ? orderCount / number : orderCount / number + 1;
    }
}

