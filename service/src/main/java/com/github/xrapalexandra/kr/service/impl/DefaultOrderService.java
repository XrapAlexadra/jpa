package com.github.xrapalexandra.kr.service.impl;

import com.github.xrapalexandra.kr.dao.OrderDao;
import com.github.xrapalexandra.kr.dao.ProductDao;
import com.github.xrapalexandra.kr.model.*;
import com.github.xrapalexandra.kr.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Transactional
public class DefaultOrderService implements OrderService {

    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private static final int MAX_NUMBER_ORDER_ON_PAGE = 4;

    private final OrderDao orderDao;
    private final ProductDao productDao;

    public DefaultOrderService(OrderDao orderDao, ProductDao productDao) {
        this.orderDao = orderDao;
        this.productDao = productDao;
    }

    @Override
    public void addOrder(Map<Integer, Integer> orderCatalog) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<OrderContent> orderContentList = createOrderContent(orderCatalog);
        Order order = new Order(user, orderContentList, Status.ORDER);
        order.setId(orderDao.addOrder(order));
        logger.info("{} add into DataBase", order);
    }

    private List<OrderContent> createOrderContent(Map<Integer, Integer> orderCatalog) {
        List<OrderContent> orderContentList = new ArrayList<>();
        for (Map.Entry<Integer, Integer> i: orderCatalog.entrySet())
            orderContentList.add(
                    new OrderContent(productDao.getProductById(i.getKey()),i.getValue())
            );
      return orderContentList;
    }

    @Override
    public List<Order> getUserOrders(String login) {
        return orderDao.getUserOrders(login);
    }

    @Override
    public Page<Order> getAllOrders(int page) {
        return orderDao.getAllOrders(page,MAX_NUMBER_ORDER_ON_PAGE);
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
                orderDao.deleteOrder(order.getId());
                logger.info("{} delete from DataBase", order.getId());
            }
    }
}
