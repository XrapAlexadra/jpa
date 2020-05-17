package com.github.xrapalexandra.kr.dao;

import com.github.xrapalexandra.kr.dao.impl.DefaultOrderDao;
import com.github.xrapalexandra.kr.dao.impl.DefaultProductDao;
import com.github.xrapalexandra.kr.dao.impl.DefaultUserDao;
import com.github.xrapalexandra.kr.model.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class OrderDaoTest {

    private OrderDao orderDao = DefaultOrderDao.getInstance();

    static User user;
    static Product product;
    static ProductDao productDao = DefaultProductDao.getInstance();
    static UserDao userDao = DefaultUserDao.getInstance();
    static List<OrderContent> orderContentList = new ArrayList<>();

    @BeforeAll
    private static void init(){
        user = new User("user15", Role.USER, "pass15");
        product = new Product("item15", 20, 67);
        user.setId(userDao.addUser(user));
        product.setId(productDao.addProduct(product));
        OrderContent orderContent = new OrderContent(product, 2);
        orderContentList.add(orderContent);
    }

    @Test
    void addOrder() {
        Order order = new Order(user, orderContentList , Status.ORDER);
        order.setId(orderDao.addOrder(order));
        assertNotNull(order.getId());
        orderDao.delOrder(order.getId());
    }

    @Test
    void delOrder(){
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
        Order order = new Order(user, orderContentList, Status.ORDER);
        order.setId(orderDao.addOrder(order));
        assertEquals(order.getId(),orderDao.getUserOrders(user.getId()).get(0).getId());
        orderDao.delOrder(order.getId());
    }

    @Test
    void changeOrderStatus(){
        Order order = new Order(user, orderContentList, Status.ORDER);
        order.setId(orderDao.addOrder(order));
        orderDao.changeOrderStatus(order.getId(), Status.PAID);
        assertEquals(Status.PAID, orderDao.getUserOrders(user.getId()).get(0).getStatus());
        orderDao.delOrder(order.getId());
    }

    @Test
    void getAllOrders(){
        Order order = new Order(user, orderContentList, Status.ORDER);
        order.setId(orderDao.addOrder(order));
        assertNotNull(orderDao.getAllOrders(1));
        orderDao.delOrder(order.getId());
    }

    @Test
    void getPaidOrders(){
        Order order = new Order(user, orderContentList, Status.PAID);
        order.setId(orderDao.addOrder(order));
        assertNotNull(orderDao.getPaidOrders());
        orderDao.delOrder(order.getId());
    }

    @Test
    void updateProductQuantity(){
        Order order = new Order(user, orderContentList, Status.CONFIRMED);
        order.setId(orderDao.addOrder(order));
        Integer result = product.getQuantity()-orderContentList.get(0).getOrderQuantity();
        orderDao.updateProductQuantity(order);
        assertEquals(result, productDao.getProductById(product.getId()).getQuantity());
        orderDao.delOrder(order.getId());
    }
    @AfterAll
    private static void cleanBD(){
        userDao.delUser(user.getId());
        productDao.delProduct(product.getId());
    }
}
