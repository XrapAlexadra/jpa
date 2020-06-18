package com.github.xrapalexandra.kr.dao.impl;

import com.github.xrapalexandra.kr.dao.UserDao;
import com.github.xrapalexandra.kr.dao.converter.UserConverter;
import com.github.xrapalexandra.kr.dao.entity.UserEntity;
import com.github.xrapalexandra.kr.dao.repository.UserRepository;
import com.github.xrapalexandra.kr.model.User;
import org.springframework.dao.DataAccessException;

public class DefaultUserDao implements UserDao {

    UserRepository repository;

    public DefaultUserDao(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public Integer addUser(User user) {
        UserEntity userEntity = UserConverter.toEntity(user);
        try {
            repository.saveAndFlush(userEntity);
            return userEntity.getId();
        }
        catch (DataAccessException e){
            return null;
        }
    }

    @Override
    public User getByLogin(String login) {
        UserEntity userEntity = repository.findByLogin(login);
        return UserConverter.fromEntity(userEntity);
    }

    @Override
    public Boolean delUser(Integer userId) {
        try {
            repository.deleteById(userId);
            return true;
        }
        catch (DataAccessException e){
            return false;
        }
    }

    @Override
    public void updatePass(User user) {
        repository.saveAndFlush(UserConverter.toEntity(user));
    }

}
