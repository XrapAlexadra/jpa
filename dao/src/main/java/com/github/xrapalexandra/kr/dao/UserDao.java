package com.github.xrapalexandra.kr.dao;

import com.github.xrapalexandra.kr.model.User;
public interface UserDao {

    Integer addUser(User user);

    User getByLogin(String login);

    void deleteUser(Integer userId);

    void updatePass(User user);

}
