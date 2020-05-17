package com.github.xrapalexandra.kr.dao;

import com.github.xrapalexandra.kr.model.Order;
import com.github.xrapalexandra.kr.model.Status;

import java.util.List;

public interface OrderDao {

    Integer addOrder(Order order);

    void delOrder(Integer orderId);

    List<Order> getAllOrders(int page);

    List<Order> getUserOrders(Integer userId);

    void changeOrderStatus(Integer orderId, Status status);

    List<Order> getPaidOrders();

    void updateProductQuantity(Order order);
}
