package com.github.xrapalexandra.kr.dao.impl;

import com.github.xrapalexandra.kr.dao.UserDao;
import com.github.xrapalexandra.kr.dao.converter.UserAddressConverter;
import com.github.xrapalexandra.kr.dao.converter.UserConverter;
import com.github.xrapalexandra.kr.dao.entity.UserAddressEntity;
import com.github.xrapalexandra.kr.dao.entity.UserEntity;
import com.github.xrapalexandra.kr.dao.util.HibernateUtil;
import com.github.xrapalexandra.kr.model.User;
import com.github.xrapalexandra.kr.model.UserAddress;
import org.hibernate.Session;

import javax.persistence.NoResultException;

public class DefaultUserDao implements UserDao {

    private static volatile UserDao instance;

    private DefaultUserDao() {
    }

    public static UserDao getInstance() {
        UserDao localInstance = instance;
        if (localInstance == null) {
            synchronized (UserDao.class) {
                localInstance = instance;
                if (localInstance == null) localInstance = instance = new DefaultUserDao();
            }
        }
        return localInstance;
    }


    @Override
    public Integer addUser(User user) {
        UserEntity userEntity = UserConverter.toEntity(user);
        final Session session = HibernateUtil.getSession();
        session.beginTransaction();
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
        try {
            final Session session = HibernateUtil.getSession();
            UserEntity userEntity = (UserEntity) session
                    .createQuery("FROM UserEntity u WHERE  u.login = :login")
                    .setParameter("login", login)
                    .getSingleResult();
            return UserConverter.fromEntity(userEntity);
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public Boolean delUser(Integer userId) {
        final Session session = HibernateUtil.getSession();
        session.beginTransaction();
        try {
            UserEntity userEntity = session.get(UserEntity.class, userId);
            session.delete(userEntity);
            session.getTransaction().commit();
            return true;
        } catch (Exception e) {
            session.getTransaction().rollback();
            return false;
        }
    }

    @Override
    public void updatePass(User user) {
        final Session session = HibernateUtil.getSession();
        session.beginTransaction();
        session.createQuery("update UserEntity u set u.pass = :pass where u.id = :userId")
                .setParameter("userId", user.getId())
                .setParameter("pass", user.getPass())
                .executeUpdate();
        session.getTransaction().commit();
    }

    @Override
    public void updateAddress(User user) {
        final Session session = HibernateUtil.getSession();
        session.beginTransaction();
        session.createQuery("update UserAddressEntity a set a.city = :city, a.street = :street, a.building = :building where a.id = :addressId")
                .setParameter("addressId", user.getAddress().getId())
                .setParameter("street", user.getAddress().getStreet())
                .setParameter("building", user.getAddress().getBuilding())
                .setParameter("city", user.getAddress().getCity())
                .executeUpdate();
        session.getTransaction().commit();
    }

    @Override
    public Integer addAddress(User user) {
        UserAddressEntity userAddressEntity = UserAddressConverter.toEntity(user.getAddress());
        final Session session = HibernateUtil.getSession();
        session.beginTransaction();
        UserEntity userEntity = session.get(UserEntity.class, user.getId());
        session.save(userAddressEntity);
        userAddressEntity.setUser(userEntity);
        userEntity.setAddress(userAddressEntity);
        session.getTransaction().commit();
        return userAddressEntity.getId();
    }

    @Override
    public UserAddress getUserAddress(Integer userId) {
        final Session session = HibernateUtil.getSession();
        UserEntity userEntity = session.get(UserEntity.class, userId);
        UserAddressEntity userAddressEntity = userEntity.getAddress();
        return UserAddressConverter.fromEntity(userAddressEntity);
    }
}
