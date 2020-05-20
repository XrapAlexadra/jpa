package com.github.xrapalexandra.kr.dao.impl;

import com.github.xrapalexandra.kr.dao.OrderDao;
import com.github.xrapalexandra.kr.dao.converter.OrderConverter;
import com.github.xrapalexandra.kr.dao.entity.OrderContentEntity;
import com.github.xrapalexandra.kr.dao.entity.OrderEntity;
import com.github.xrapalexandra.kr.dao.entity.ProductEntity;
import com.github.xrapalexandra.kr.dao.util.HibernateUtil;
import com.github.xrapalexandra.kr.model.Order;
import com.github.xrapalexandra.kr.model.OrderContent;
import com.github.xrapalexandra.kr.model.Status;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.stream.Collectors;

public class DefaultOrderDao implements OrderDao {

    private static volatile OrderDao instance;
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());


    public static OrderDao getInstance() {
        OrderDao localInstance = instance;
        if (localInstance == null) {
            synchronized (OrderDao.class) {
                localInstance = instance;
                if (localInstance == null)
                    instance = localInstance = new DefaultOrderDao();

            }
        }
        return localInstance;
    }

    @Override
    public Integer addOrder(Order order) {
        OrderEntity orderEntity = OrderConverter.toEntity(order);
        final Session session = HibernateUtil.getSession();
        session.beginTransaction();
        session.save(orderEntity);
        for (OrderContentEntity i : orderEntity.getOrderContentList()) {
            session.get(OrderContentEntity.class, i.getId()).setOrder(orderEntity);
        }
        session.getTransaction().commit();
        logger.info("{} add into DataBase", orderEntity);
        return orderEntity.getId();
    }

    @Override
    public void delOrder(Integer orderId) {
        final Session session = HibernateUtil.getSession();
        session.beginTransaction();
        OrderEntity orderEntity = session.get(OrderEntity.class, orderId);
        session.delete(orderEntity);
        session.getTransaction().commit();
        logger.info("{} delete from DataBase", orderId);
    }

    @Override
    public List<Order> getAllOrders(int page) {
        final Session session = HibernateUtil.getSession();
        List<OrderEntity> orderList = session.createQuery("FROM OrderEntity ", OrderEntity.class).list();
        if (orderList.isEmpty())
            return null;
        return orderList.stream()
                .map(OrderConverter::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<Order> getUserOrders(Integer userId) {
        final Session session = HibernateUtil.getSession();
        List<OrderEntity> orderList = session
                .createQuery("FROM OrderEntity WHERE user.id = :userId ", OrderEntity.class)
                .setParameter("userId", userId)
                .list();
        if (orderList.isEmpty())
            return null;
        return orderList.stream()
                .map(OrderConverter::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void changeOrderStatus(Integer orderId, Status status) {
        final Session session = HibernateUtil.getSession();
        session.beginTransaction();
        OrderEntity orderEntity = session.get(OrderEntity.class, orderId);
        orderEntity.setStatus(status);
        session.getTransaction().commit();
        logger.info("Order id {} change status {}.", orderId, status);
    }

    @Override
    public List<Order> getPaidOrders() {
        final Session session = HibernateUtil.getSession();
        List<OrderEntity> orderList = session.createQuery("FROM OrderEntity WHERE status = :status", OrderEntity.class)
                .setParameter("status", Status.PAID)
                .list();
        if (orderList.isEmpty())
            return null;
        return orderList.stream()
                .map(OrderConverter::fromEntity)
                .collect(Collectors.toList());
    }
    @Override
    public void updateProductQuantity(Order order) {
        List<OrderContent> orderContentList = order.getContentList();
        final Session session = HibernateUtil.getSession();
        session.beginTransaction();
        for(OrderContent i: orderContentList) {
            ProductEntity productEntity = session.get(ProductEntity.class, i.getProduct().getId());
            Integer resultQuantity = productEntity.getQuantity()-i.getOrderQuantity();
            productEntity.setQuantity(resultQuantity);
        }
        session.getTransaction().commit();
        logger.info("Update products quantity with order id{}", order.getId());
    }

}

