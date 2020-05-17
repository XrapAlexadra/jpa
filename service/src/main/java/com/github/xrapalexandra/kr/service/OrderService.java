package com.github.xrapalexandra.kr.service;

import com.github.xrapalexandra.kr.model.Order;
import com.github.xrapalexandra.kr.model.Status;

import java.util.List;

public interface OrderService {

    void addOrder (Order order);

    List<Order> getUserOrders(Integer userId);

    List<Order> getAllOrders(int page);

    void changeOrderStatus(Integer orderId, Status status);

    List<Order> getPaidOrders();

    void delOrder(Integer orderId);

    void updateProductQuantity(Order order);
}
