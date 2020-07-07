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


    @Mock
    private UserDao userDao;

    @InjectMocks
    private DefaultUserService userService;

    @Test
    void addUserNotExist(){
        User user = new User("login221", Role.USER, "12345678");
        when(userDao.getByLogin(any())).thenReturn(null);
        when(userDao.addUser(any())).thenReturn(225);
        assertEquals(225, userService.addUser(user).getId());
    }
    @Test
    void addUserAlreadyExist(){
        User user1 = new User("login221", Role.USER, "12345678");
        User user2 = new User("login221", Role.USER, "1234");
        when(userDao.getByLogin(any())).thenReturn(user2);
        assertNull( userService.addUser(user1));
    }

    @Test
    void login(){
        User user = new User("lgin222", Role.USER, "123456");
        when(userDao.getByLogin(any())).thenReturn(user);
        assertEquals(user, userService.logIn(user.getLogin(), user.getPass()));
    }

    @Test
    void loginWithWrongPass(){
        User user = new User("qwerty", Role.USER, "123456");
        when(userDao.getByLogin(any())).thenReturn(user);
        assertNull(userService.logIn(user.getLogin(), "qwerty"));
    }

    @Test
    void loginUserNotExist(){
        when(userDao.getByLogin(any())).thenReturn(null);
        assertNull(userService.logIn("qwerty", "123456"));
    }
}
