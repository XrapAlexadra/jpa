package com.github.xrapalexandra.kr.service.impl;

import com.github.xrapalexandra.kr.dao.OrderDao;
import com.github.xrapalexandra.kr.dao.ProductDao;
import com.github.xrapalexandra.kr.model.Order;
import com.github.xrapalexandra.kr.model.Status;
import com.github.xrapalexandra.kr.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import java.lang.invoke.MethodHandles;
import java.util.List;

@Transactional
public class DefaultOrderService implements OrderService {

    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private OrderDao orderDao;
    private ProductDao productDao;

    public DefaultOrderService(OrderDao orderDao, ProductDao productDao) {
        this.orderDao = orderDao;
        this.productDao = productDao;
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
    public Page<Order> getAllOrders(int page) {
        return orderDao.getAllOrders(page,8);
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
                productDao.updateQuantityByOrder(order);
                logger.info("Update products quantity with order id{}", order);
                orderDao.delOrder(order.getId());
                logger.info("{} delete from DataBase", order.getId());
            }
    }
}
