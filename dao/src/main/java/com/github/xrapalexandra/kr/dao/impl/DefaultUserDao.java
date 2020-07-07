package com.github.xrapalexandra.kr.dao.impl;

import com.github.xrapalexandra.kr.dao.UserDao;
import com.github.xrapalexandra.kr.dao.converter.UserConverter;
import com.github.xrapalexandra.kr.dao.entity.UserEntity;
import com.github.xrapalexandra.kr.dao.repository.UserRepository;
import com.github.xrapalexandra.kr.model.User;

public class DefaultUserDao implements UserDao {

    private final UserRepository repository;

    public DefaultUserDao(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public Integer addUser(User user) {
        UserEntity userEntity = UserConverter.toEntity(user);
        repository.saveAndFlush(userEntity);
        return userEntity.getId();
    }

    @Override
    public User getByLogin(String login) {
        UserEntity userEntity = repository.findByLogin(login);
        return UserConverter.fromEntity(userEntity);
    }

    @Override
    public void deleteUser(Integer userId) {
        repository.deleteById(userId);
    }

    @Override
    public void updatePass(User user) {
        repository.saveAndFlush(UserConverter.toEntity(user));
    }

}
