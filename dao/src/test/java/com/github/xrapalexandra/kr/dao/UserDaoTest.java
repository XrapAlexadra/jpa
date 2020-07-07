package com.github.xrapalexandra.kr.dao;


import com.github.xrapalexandra.kr.dao.config.DaoConfig;
import com.github.xrapalexandra.kr.model.Role;
import com.github.xrapalexandra.kr.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = DaoConfig.class)

public class UserDaoTest {

    @Autowired
    public UserDao userDao;
    @Autowired
    public ProductDao productDao;
    @Autowired
    public OrderDao orderDao;


    @Test
    @Transactional
    void addUser(){
        User user1 = new User("user11", Role.USER, "user11");
        user1.setId(userDao.addUser(user1));
        assertNotNull(user1.getId());
    }

    @Test
    @Transactional
    void getByLogin(){
        User user = new User("user", Role.USER, "user345");
        user.setId(userDao.addUser(user));
        assertEquals(user.getId(), userDao.getByLogin(user.getLogin()).getId());
    }

    @Test
    @Transactional
    void getByLoginNull(){
        assertNull(userDao.getByLogin("прпр"));
    }

    @Test
    @Transactional
    void deleteUser(){
        User user = new User("zxcvb2", Role.USER, "425146");
        user.setId(userDao.addUser(user));
        userDao.deleteUser(user.getId());
        assertNull(userDao.getByLogin(user.getLogin()));
    }
}
