package com.github.xrapalexandra.kr.service.impl;

import com.github.xrapalexandra.kr.dao.UserDao;
import com.github.xrapalexandra.kr.dao.impl.DefaultUserDao;
import com.github.xrapalexandra.kr.model.User;
import com.github.xrapalexandra.kr.model.UserAddress;
import com.github.xrapalexandra.kr.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;

public class DefaultUserService implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private UserDao userDao = DefaultUserDao.getInstance();

    private DefaultUserService() {
    }

    private static volatile UserService instance;

    public static UserService getInstance() {
        UserService localInstance = instance;
        if (localInstance == null) {
            synchronized (UserService.class) {
                localInstance = instance;
                if (localInstance == null)
                    localInstance = instance = new DefaultUserService();
            }
        }
        return localInstance;
    }

    @Override
    public User addUser(User user) {
        user.setId(userDao.addUser(user));
        if (user.getId() == null) {
            logger.info("Can't save user: {} in the table users, because user with this login is already exist!", user.getLogin());
            return null;
        } else {
            logger.info("Save user: {} in table users.", user.getLogin());
            return user;
        }
    }

    @Override
    public User logIn(String login, String pass) {
        User user = userDao.getByLogin(login);
        if (user == null) {
            logger.info("Incorrect data entry (login {}).", login);
            return null;
        }
        if (user.getPass().equals(pass)) {
            logger.info("User {} Authenticated.", user.getLogin());
            return user;
        }
        logger.info("User {} entered incorrect pass: {}.", login, pass);
        return null;
    }

    @Override
    public void updatePass(User user) {
        userDao.updatePass(user);
        logger.info("User {} update pass.", user.getId());
    }

    @Override
    public void updateAddress(User user) {
        UserAddress userAddress = userDao.getUserAddress(user.getId());
        if(userAddress == null){
            userDao.addAddress(user);
            logger.info("User {} add address {}.", user.getId(), user.getAddress());
        }
        else {
            userDao.updateAddress(user);
            logger.info("User {} update address by {}.", user.getId(), user.getAddress());
        }
    }

    @Override
    public UserAddress getUserAddress(Integer userId) {
        return userDao.getUserAddress(userId);
    }
}
