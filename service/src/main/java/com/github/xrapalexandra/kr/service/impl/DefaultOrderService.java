package com.github.xrapalexandra.kr.service.impl;

import com.github.xrapalexandra.kr.dao.OrderDao;
import com.github.xrapalexandra.kr.dao.impl.DefaultOrderDao;
import com.github.xrapalexandra.kr.model.Order;
import com.github.xrapalexandra.kr.model.Status;
import com.github.xrapalexandra.kr.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.util.List;

public class DefaultOrderService implements OrderService {


    private DefaultOrderService() {
    }

    private static volatile OrderService instance;
    private OrderDao orderDao = DefaultOrderDao.getInstance();
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public static OrderService getInstance() {
        OrderService localInstance = instance;
        if (localInstance == null) {
            synchronized (OrderService.class) {
                localInstance = instance;
                if (localInstance == null)
                    localInstance = instance = new DefaultOrderService();
            }
        }
        return localInstance;
    }

    @Override
    public void addOrder(Order order) {
        order.setId(orderDao.addOrder(order));
        logger.info("{} add into DataBase", order);
    }

    @Override
    public List<Order> getUserOrders(Integer userId) {
        return orderDao.getUserOrders(userId);
    }

    @Override
    public List<Order> getAllOrders(int page) {
        return orderDao.getAllOrders(page);
    }

    @Override
    public void changeOrderStatus(Integer orderId, Status status) {
        orderDao.changeOrderStatus(orderId, status);
        logger.info("Order id {} change status {}.", orderId, status);
    }

    @Override
    public void writeOffOrders() {
        List<Order> paidOrders = orderDao.getPaidOrders();
        if (paidOrders != null)
            for (Order order : paidOrders) {
                orderDao.updateProductQuantity(order);
                logger.info("Update products quantity with order id{}", order);
                orderDao.delOrder(order.getId());
                logger.info("{} delete from DataBase", order.getId());
            }
    }
}
