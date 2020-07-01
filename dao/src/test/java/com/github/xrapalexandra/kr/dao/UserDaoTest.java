package com.github.xrapalexandra.kr.dao;


import com.github.xrapalexandra.kr.dao.config.DaoConfig;
import com.github.xrapalexandra.kr.model.*;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = DaoConfig.class)
//@Transactional
public class UserDaoTest {
//
//    @Autowired
//    public UserDao userDao;
//    @Autowired
//    public ProductDao productDao;
//    @Autowired
//    public OrderDao orderDao;
//    @Autowired
//    private SessionFactory sessionFactory;
//
//    @Test
//    void addUserNull(){
//        User user1 = new User("login", Role.USER, "user");
//        User user2 = new User("login", Role.USER, "user123");
//        user1.setId(userDao.addUser(user1));
//        user2.setId(userDao.addUser(user2));
//
//        assertNull(user2.getId());
//    }
//
//    @Test
//    void addUser(){
//        User user1 = new User("user11", Role.USER, "user11");
//        user1.setId(userDao.addUser(user1));
//        assertNotNull(userDao.getByLogin("user11"));
//    }
//
////    @Test
////    void getByLogin(){
////        User user = new User("user", Role.USER, "user345");
////        user.setId(userDao.addUser(user));
////        assertEquals(user, userDao.getByLogin(user.getLogin()));
////    }
//
//    @Test
//    void getByLoginNull(){
//        assertNull(userDao.getByLogin("прпр"));
//    }
//
//    @Test
//    void delUser(){
//        User user = new User("zxcvb2", Role.USER, "425146");
//        user.setId(userDao.addUser(user));
//        assertTrue(userDao.delUser(user.getId()));
//
//    }
//
//    @Test
//    void delUserFalse(){
//        User user = new User("zxc", Role.USER, "425146");
//        user.setId(userDao.addUser(user));
//        Product product = new Product("name1345", 12, 23);
//        product.setId(productDao.addProduct(product));
//
//        OrderContent orderContent = new OrderContent(product, 2);
//        List<OrderContent> orderContentList= new ArrayList<>();
//        orderContentList.add(orderContent);
//
//        Order order = new Order(user, orderContentList, Status.ORDER);
//        order.setId(orderDao.addOrder(order));
//
//        assertFalse(userDao.delUser(user.getId()));
//    }
//
//    @Test
//    @Transactional
//    void updatePass(){
//        User user = new User("zxcvb1", Role.USER, "425146");
//        user.setId(userDao.addUser(user));
//        user.setPass("popopo");
//        userDao.updatePass(user);
//        assertEquals("popopo", userDao.getByLogin("zxcvb1").getPass());
//    }

//    @Test
//    void addAdmin (){
//        User user = new User("admin", Role.ADMIN, "admin");
//        userDao.addUser(user);
//    }
}
