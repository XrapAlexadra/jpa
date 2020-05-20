package com.github.xrapalexandra.kr.dao;


import com.github.xrapalexandra.kr.dao.impl.DefaultOrderDao;
import com.github.xrapalexandra.kr.dao.impl.DefaultProductDao;
import com.github.xrapalexandra.kr.dao.impl.DefaultUserDao;
import com.github.xrapalexandra.kr.model.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class UserDaoTest {

    public UserDao userDao = DefaultUserDao.getInstance();
    public ProductDao productDao = DefaultProductDao.getInstance();
    public OrderDao orderDao = DefaultOrderDao.getInstance();

    @Test
    void addUserNull(){
        User user1 = new User("login", Role.USER, "user");
        User user2 = new User("login", Role.USER, "user123");
        user1.setId(userDao.addUser(user1));
        user2.setId(userDao.addUser(user2));
        assertNull(user2.getId());
        userDao.delUser(user1.getId());
        userDao.delUser(user2.getId());
    }

    @Test
    void addUser(){
        User user1 = new User("user11", Role.USER, "user11");
        user1.setId(userDao.addUser(user1));
        assertNotNull(userDao.getByLogin("user11"));
        userDao.delUser(user1.getId());
    }

    @Test
    void getByLogin(){
        User user = new User("user", Role.USER, "user345");
        user.setId(userDao.addUser(user));
        assertEquals(user, userDao.getByLogin(user.getLogin()));
        userDao.delUser(user.getId());
    }

    @Test
    void getByLoginNull(){
        assertNull(userDao.getByLogin("прпр"));
    }

    @Test
    void delUser(){
        User user = new User("zxcvb", Role.USER, "425146");
        user.setId(userDao.addUser(user));
        assertTrue(userDao.delUser(user.getId()));
    }

    @Test
    void delUserFalse(){
        User user = new User("zxcvb", Role.USER, "425146");
        user.setId(userDao.addUser(user));
        Product product = new Product("name1", 12, 23);
        product.setId(productDao.addProduct(product));
        OrderContent orderContent = new OrderContent(product, 2);
        List<OrderContent> orderContentList= new ArrayList<>();
        orderContentList.add(orderContent);
        Order order = new Order(user, orderContentList, Status.ORDER);
        order.setId(orderDao.addOrder(order));

        assertFalse(userDao.delUser(user.getId()));

        orderDao.delOrder(order.getId());
        productDao.delProduct(product.getId());
       userDao.delUser(user.getId());
    }
    @Test
    void updatePass(){
        User user = new User("zxcvb", Role.USER, "425146");
        user.setId(userDao.addUser(user));
        user.setPass("popopo");
        userDao.updatePass(user);
        assertEquals("popopo", userDao.getByLogin("zxcvb").getPass());
        userDao.delUser(user.getId());
    }

    @Test
    void updateAddress(){
        User user = new User("user2", Role.USER, "425146");
        user.setId(userDao.addUser(user));
        user.setAddress(new UserAddress("Minsk", "lenina", "10a"));
        user.getAddress().setId(userDao.addAddress(user));
        user.getAddress().setCity("Rio");
        userDao.updateAddress(user);
        assertEquals(user.getAddress(), userDao.getUserAddress(user.getId()));
        userDao.delUser(user.getId());

    }
//    @Test
//    void addAdmin (){
//        User user = new User("admin", Role.ADMIN, "admin");
//        userDao.addUser(user);
//    }
}
