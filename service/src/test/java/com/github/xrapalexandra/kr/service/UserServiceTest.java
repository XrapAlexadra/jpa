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
    void login(){
        User user = new User("qwerty", Role.USER, "123456");
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

    @Test
    public void getInstance(){
        assertNotNull(DefaultUserService.getInstance());
    }
}
