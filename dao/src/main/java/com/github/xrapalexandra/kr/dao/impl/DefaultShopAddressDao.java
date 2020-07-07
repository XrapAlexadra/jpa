package com.github.xrapalexandra.kr.dao.impl;

import com.github.xrapalexandra.kr.dao.ShopAddressDao;
import com.github.xrapalexandra.kr.dao.converter.ShopAddressConverter;
import com.github.xrapalexandra.kr.dao.entity.ShopAddressEntity;
import com.github.xrapalexandra.kr.dao.repository.ShopAddressRepository;
import com.github.xrapalexandra.kr.model.ShopAddress;

import java.util.List;
import java.util.stream.Collectors;

public class DefaultShopAddressDao implements ShopAddressDao {

    private final ShopAddressRepository repository;

    public DefaultShopAddressDao(ShopAddressRepository repository) {
        this.repository = repository;
    }

    @Override
    public Integer addAddress(ShopAddress shopAddress) {
        ShopAddressEntity shopAddressEntity = ShopAddressConverter.toEntity(shopAddress);
        repository.saveAndFlush(shopAddressEntity);
        return shopAddressEntity.getId();
    }

    @Override
    public void delAddress(Integer shopAddressId) {
        repository.deleteById(shopAddressId);
    }

    @Override
    public List<ShopAddress> getAddressList() {
        final List<ShopAddressEntity> addressList =repository.findAll();
        return addressList.stream()
                .map(ShopAddressConverter::fromEntity)
                .collect(Collectors.toList());
    }
}
