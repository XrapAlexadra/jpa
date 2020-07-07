package com.github.xrapalexandra.kr.service.impl;

import com.github.xrapalexandra.kr.dao.UserDao;
import com.github.xrapalexandra.kr.model.User;
import com.github.xrapalexandra.kr.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import java.lang.invoke.MethodHandles;

@Transactional
public class DefaultUserService implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final UserDao userDao;

    public DefaultUserService(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public User addUser(User user) {
        if (userDao.getByLogin(user.getLogin()) == null) {
            user.setId(userDao.addUser(user));
            logger.info("Save user: {} in table users.", user.getLogin());
            return user;
        } else {
            logger.info("Can't save user: {} in the table users, because user with this login is already exist!", user.getLogin());
            return null;
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
}
