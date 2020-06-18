package com.github.xrapalexandra.kr.dao;

import com.github.xrapalexandra.kr.model.ShopAddress;
import com.github.xrapalexandra.kr.model.User;
import com.github.xrapalexandra.kr.model.UserAddress;
public interface UserDao {

    Integer addUser(User user);

    User getByLogin(String login);

    Boolean delUser(Integer userId);

    void updatePass(User user);

}
