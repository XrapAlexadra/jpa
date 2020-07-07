package com.github.xrapalexandra.kr.dao;

import com.github.xrapalexandra.kr.dao.config.DaoConfig;
import com.github.xrapalexandra.kr.dao.config.HibernateConfig;
import com.github.xrapalexandra.kr.model.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

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
    @Autowired
    private PlatformTransactionManager transactionManager;

    @Test
    @Transactional
    void addOrder() {
        User user = new User("user15", Role.USER, "pass15");
        Product product = new Product("item15", 20, 67);
        user.setId(userDao.addUser(user));
        product.setId(productDao.saveProduct(product));
        OrderContent orderContent = new OrderContent(product, 2);
        List<OrderContent> orderContentList = new ArrayList<>();
        orderContentList.add(orderContent);

        Order order = new Order(user, orderContentList , Status.ORDER);
        order.setId(orderDao.addOrder(order));

        assertNotNull(order.getId());
    }

    @Test
    @Transactional
    void deleteOrder(){
        User user = new User("user16", Role.USER, "pass15");
        Product product = new Product("item16", 20, 67);
        user.setId(userDao.addUser(user));
        product.setId(productDao.saveProduct(product));
        OrderContent orderContent = new OrderContent(product, 2);
        List<OrderContent> orderContentList = new ArrayList<>();
        orderContentList.add(orderContent);

        Order order = new Order(user, orderContentList, Status.ORDER);
        order.setId(orderDao.addOrder(order));
        orderDao.deleteOrder(order.getId());
        assertNull(orderDao.getUserOrders(user.getLogin()));

    }

    @Test
    @Transactional
    void getUserOrdersNull(){
        User user = new User("user17", Role.USER, "pass15");
        assertNull(orderDao.getUserOrders(user.getLogin()));
    }

    @Test
    @Transactional
    void getUserOrders(){
        User user = new User("user18", Role.USER, "pass15");
        Product product = new Product("item18", 20, 67);
        user.setId(userDao.addUser(user));
        product.setId(productDao.saveProduct(product));
        OrderContent orderContent = new OrderContent(product, 2);
        List<OrderContent> orderContentList = new ArrayList<>();
        orderContentList.add(orderContent);

        Order order = new Order(user, orderContentList, Status.ORDER);
        order.setId(orderDao.addOrder(order));

        assertEquals(order.getId(),orderDao.getUserOrders(user.getLogin()).get(0).getId());
    }

    @Test
    @Transactional
    void changeOrderStatus(){
        TransactionStatus transaction = transactionManager.getTransaction(
                new DefaultTransactionDefinition
                        (TransactionDefinition.PROPAGATION_REQUIRES_NEW));
        User user = new User("user19", Role.USER, "pass15");
        Product product = new Product("item19", 20, 67);
        user.setId(userDao.addUser(user));
        product.setId(productDao.saveProduct(product));
        OrderContent orderContent = new OrderContent(product, 2);
        List<OrderContent> orderContentList = new ArrayList<>();
        orderContentList.add(orderContent);

        Order order = new Order(user, orderContentList, Status.ORDER);
        order.setId(orderDao.addOrder(order));
        transactionManager.commit(transaction);

       transaction = transactionManager.getTransaction(
                new DefaultTransactionDefinition
                        (TransactionDefinition.PROPAGATION_REQUIRES_NEW));
        orderDao.changeOrderStatus(order.getId(), Status.PAID);
        Status status = orderDao.getUserOrders(user.getLogin()).get(0).getStatus();

        assertEquals(Status.PAID, status);

        orderDao.deleteOrder(order.getId());
        userDao.deleteUser(user.getId());
        productDao.deleteProduct(product.getId());
        transactionManager.commit(transaction);
    }

    @Test
    @Transactional
    void getAllOrders(){
        User user = new User("user20", Role.USER, "pass15");
        Product product = new Product("item20", 20, 67);
        user.setId(userDao.addUser(user));
        product.setId(productDao.saveProduct(product));
        OrderContent orderContent = new OrderContent(product, 2);
        List<OrderContent> orderContentList = new ArrayList<>();
        orderContentList.add(orderContent);

        Order order = new Order(user, orderContentList, Status.ORDER);
        order.setId(orderDao.addOrder(order));

        assertNotNull(orderDao.getAllOrders(1, 8));
    }

    @Test
    @Transactional
    void getPaidOrders(){
        User user = new User("user21", Role.USER, "pass15");
        Product product = new Product("item21", 20, 67);
        user.setId(userDao.addUser(user));
        product.setId(productDao.saveProduct(product));
        OrderContent orderContent = new OrderContent(product, 2);
        List<OrderContent> orderContentList = new ArrayList<>();
        orderContentList.add(orderContent);

        Order order = new Order(user, orderContentList, Status.PAID);
        order.setId(orderDao.addOrder(order));

        assertNotNull(orderDao.getPaidOrders());
    }

}
