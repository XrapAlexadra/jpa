package com.github.xrapalexandra.kr.service;

import com.github.xrapalexandra.kr.model.User;

public interface UserService {

    User addUser(User user);

    User logIn(String login, String pass);

    void updatePass(User user);

    void updateAddress(User user);

}
