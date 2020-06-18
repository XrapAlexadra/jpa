package com.github.xrapalexandra.kr.service;

import com.github.xrapalexandra.kr.model.Order;
import com.github.xrapalexandra.kr.model.Status;
import org.springframework.data.domain.Page;

import java.util.List;

public interface OrderService {

    void addOrder (Order order);

    List<Order> getUserOrders(Integer userId);

    Page<Order> getAllOrders(int page);

    void changeOrderStatus(Integer orderId, Status status);

    void writeOffOrders();
}
