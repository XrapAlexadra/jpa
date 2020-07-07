package com.github.xrapalexandra.kr.service;

import com.github.xrapalexandra.kr.model.ShopAddress;

import java.util.List;

public interface ShopAddressService {

    Integer addAddress(ShopAddress shopAddress);

    void delAddress(Integer shopAddressId);

    List<ShopAddress> getShopAddressList();
}
