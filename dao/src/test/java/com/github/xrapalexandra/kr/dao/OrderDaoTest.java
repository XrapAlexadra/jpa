package com.github.xrapalexandra.kr.dao;

import com.github.xrapalexandra.kr.dao.config.DaoConfig;
import com.github.xrapalexandra.kr.dao.config.HibernateConfig;
import com.github.xrapalexandra.kr.model.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {HibernateConfig.class, DaoConfig.class})

public class OrderDaoTest {

    @Autowired
    public OrderDao orderDao;
    @Autowired
    public ProductDao productDao;
    @Autowired
    public UserDao userDao;


    private User user = new User("user15", Role.USER, "pass15");
    private Product product = new Product("item15", 20, 67);

    @Test
    void addOrder() {
        user.setId(userDao.addUser(user));
        product.setId(productDao.addProduct(product));
        OrderContent orderContent = new OrderContent(product, 2);
        List<OrderContent> orderContentList = new ArrayList<>();
        orderContentList.add(orderContent);

        Order order = new Order(user, orderContentList , Status.ORDER);
        order.setId(orderDao.addOrder(order));

        assertNotNull(order.getId());
    }

    @Test
    void delOrder(){
        user.setId(userDao.addUser(user));
        product.setId(productDao.addProduct(product));
        OrderContent orderContent = new OrderContent(product, 2);
        List<OrderContent> orderContentList = new ArrayList<>();
        orderContentList.add(orderContent);

        Order order = new Order(user, orderContentList, Status.ORDER);
        order.setId(orderDao.addOrder(order));
        orderDao.delOrder(order.getId());
        assertNull(orderDao.getUserOrders(user.getId()));
    }

    @Test
    void getUserOrdersNull(){
        assertNull(orderDao.getUserOrders(user.getId()));
    }

    @Test
    void getUserOrders(){
        user.setId(userDao.addUser(user));
        product.setId(productDao.addProduct(product));
        OrderContent orderContent = new OrderContent(product, 2);
        List<OrderContent> orderContentList = new ArrayList<>();
        orderContentList.add(orderContent);

        Order order = new Order(user, orderContentList, Status.ORDER);
        order.setId(orderDao.addOrder(order));

        assertEquals(order.getId(),orderDao.getUserOrders(user.getId()).get(0).getId());
    }

    @Test
    void changeOrderStatus(){
        user.setId(userDao.addUser(user));
        product.setId(productDao.addProduct(product));
        OrderContent orderContent = new OrderContent(product, 2);
        List<OrderContent> orderContentList = new ArrayList<>();
        orderContentList.add(orderContent);

        Order order = new Order(user, orderContentList, Status.ORDER);
        order.setId(orderDao.addOrder(order));

        orderDao.changeOrderStatus(order.getId(), Status.PAID);


        Status status = orderDao.getUserOrders(user.getId()).get(0).getStatus();

        assertEquals(Status.PAID, status);
    }

    @Test
    void getAllOrders(){
        user.setId(userDao.addUser(user));
        product.setId(productDao.addProduct(product));
        OrderContent orderContent = new OrderContent(product, 2);
        List<OrderContent> orderContentList = new ArrayList<>();
        orderContentList.add(orderContent);

        Order order = new Order(user, orderContentList, Status.ORDER);
        order.setId(orderDao.addOrder(order));

        assertNotNull(orderDao.getAllOrders(1, 8));
    }

    @Test
    void getPaidOrders(){
        user.setId(userDao.addUser(user));
        product.setId(productDao.addProduct(product));
        OrderContent orderContent = new OrderContent(product, 2);
        List<OrderContent> orderContentList = new ArrayList<>();
        orderContentList.add(orderContent);

        Order order = new Order(user, orderContentList, Status.PAID);
        order.setId(orderDao.addOrder(order));

        assertNotNull(orderDao.getPaidOrders());
    }

}
