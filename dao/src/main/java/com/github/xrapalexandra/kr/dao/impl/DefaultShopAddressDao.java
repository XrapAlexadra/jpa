package com.github.xrapalexandra.kr.dao.impl;

import com.github.xrapalexandra.kr.dao.ShopAddressDao;
import com.github.xrapalexandra.kr.dao.converter.ShopAddressConverter;
import com.github.xrapalexandra.kr.dao.entity.ShopAddressEntity;
import com.github.xrapalexandra.kr.dao.util.HibernateUtil;
import com.github.xrapalexandra.kr.model.ShopAddress;
import org.hibernate.Session;


import java.util.List;
import java.util.stream.Collectors;

public class DefaultShopAddressDao implements ShopAddressDao {

    private static volatile ShopAddressDao instance;

    public static ShopAddressDao getInstance() {
        ShopAddressDao localInstance = instance;
        if (localInstance == null) {
            synchronized (ShopAddressDao.class) {
                localInstance = instance;
                if (localInstance == null)
                    instance = localInstance = new DefaultShopAddressDao();

            }
        }
        return localInstance;
    }


    @Override
    public Integer addAddress(ShopAddress shopAddress) {
        ShopAddressEntity shopAddressEntity = ShopAddressConverter.toEntity(shopAddress);
        final Session session = HibernateUtil.getSession();
        session.beginTransaction();
        session.save(shopAddressEntity);
        session.getTransaction().commit();
        return shopAddressEntity.getId();
    }

    @Override
    public void delAddress(Integer shopAddressId) {
        final Session session = HibernateUtil.getSession();
        session.beginTransaction();
        ShopAddressEntity address = session.get(ShopAddressEntity.class, shopAddressId);
        session.delete(address);
        session.getTransaction().commit();
    }

    @Override
    public void updateAddress(ShopAddress shopAddress) {
        final Session session = HibernateUtil.getSession();
        session.beginTransaction();
        ShopAddressEntity address = session.get(ShopAddressEntity.class, shopAddress.getId());
        address.setBuilding(shopAddress.getBuilding());
        address.setStreet(shopAddress.getStreet());
        address.setCity(shopAddress.getCity());
        session.getTransaction().commit();
    }

    @Override
    public List<ShopAddress> getAddressList() {
        Session session = HibernateUtil.getSession();
        final List<ShopAddressEntity> addressList = session
                .createQuery("FROM ShopAddressEntity", ShopAddressEntity.class)
                .list();
        return addressList.stream()
                .map(ShopAddressConverter::fromEntity)
                .collect(Collectors.toList());
    }
}
