package com.github.xrapalexandra.kr.dao.impl;

import com.github.xrapalexandra.kr.dao.UserDao;
import com.github.xrapalexandra.kr.dao.converter.UserConverter;
import com.github.xrapalexandra.kr.dao.entity.UserEntity;
import com.github.xrapalexandra.kr.dao.repository.UserRepository;
import com.github.xrapalexandra.kr.model.User;
import org.hibernate.Session;
import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

public class DefaultUserDao implements UserDao {

    private UserRepository repository;
    private EntityManager entityManager;

    public DefaultUserDao(UserRepository repository, EntityManager entityManager) {
        this.repository = repository;
        this.entityManager = entityManager;
    }

    @Override
    public Integer addUser(User user) {
        UserEntity userEntity = UserConverter.toEntity(user);
        Session session = entityManager.unwrap(Session.class);
        try {
            session.save(userEntity);
            session.getTransaction().commit();
            return userEntity.getId();
        } catch (Exception e) {
            session.getTransaction().rollback();
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
            repository.deleteById(userId);
            return true;
    }

    @Override
    public void updatePass(User user) {
        repository.saveAndFlush(UserConverter.toEntity(user));
    }

}
