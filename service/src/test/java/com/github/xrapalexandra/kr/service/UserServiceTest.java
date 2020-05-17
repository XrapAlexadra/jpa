package com.github.xrapalexandra.kr.service;

import com.github.xrapalexandra.kr.dao.UserDao;
import com.github.xrapalexandra.kr.model.Role;
import com.github.xrapalexandra.kr.model.User;
import com.github.xrapalexandra.kr.service.impl.DefaultUserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

//
//    @Mock
//    private UserDao userDao;
//
//    @InjectMocks
//    private UserService userService;
//
//    @Test
//    void addUser(){
//        Integer resId = 10;
//        when(userDao.getByLogin(any())).thenReturn(null);
//        when(userDao.addUser(any())).thenReturn(resId);
//        User user = new User("qwerty", Role.USER, "123456");
//        User user1 = userService.addUser(user);
//        assertEquals(resId, user1.getId());
//    }
//
//    @Test
//    void addUserAlreadyExist(){
//        User user = new User("qwerty", Role.USER, "123456");
//        when(userDao.getByLogin(any())).thenReturn(user);
//        assertNull(userService.addUser(user));
//    }
//
//    @Test
//    void login(){
//        User user = new User("qwerty", Role.USER, "123456");
//        when(userDao.getByLogin(any())).thenReturn(user);
//        assertEquals(user, userService.login(user.getLogin(), user.getPass()));
//    }
//
//    @Test
//    void loginWithWrongPass(){
//        User user = new User("qwerty", Role.USER, "123456");
//        when(userDao.getByLogin(any())).thenReturn(user);
//        assertNull(userService.login(user.getLogin(), "qwerty"));
//    }
//
//    @Test
//    void loginUserNotExist(){
//        when(userDao.getByLogin(any())).thenReturn(null);
//        assertNull(userService.login("qwerty", "123456"));
//    }

}
