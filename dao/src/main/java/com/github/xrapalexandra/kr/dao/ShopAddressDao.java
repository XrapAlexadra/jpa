package com.github.xrapalexandra.kr.dao;

import com.github.xrapalexandra.kr.model.ShopAddress;

import java.util.List;

public interface ShopAddressDao {

    Integer addAddress(ShopAddress shopAddress);

    void delAddress(Integer shopAddressId);

    List<ShopAddress> getAddressList();
}
