package com.github.xrapalexandra.kr.service;

import com.github.xrapalexandra.kr.model.Order;
import com.github.xrapalexandra.kr.model.Status;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

public interface OrderService {

    void addOrder(Map<Integer, Integer> orderСatalog);

    List<Order> getUserOrders(String login);

    Page<Order> getAllOrders(int page);

    void changeOrderStatus(Integer orderId, Status status);

    void writeOffOrders();
}
