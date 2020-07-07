package com.github.xrapalexandra.kr.service.impl;

import com.github.xrapalexandra.kr.dao.ShopAddressDao;
import com.github.xrapalexandra.kr.model.ShopAddress;
import com.github.xrapalexandra.kr.service.ShopAddressService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import java.lang.invoke.MethodHandles;
import java.util.List;

@Transactional
public class DefaultShopAddressService implements ShopAddressService {

    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final ShopAddressDao shopAddressDao;

    public DefaultShopAddressService(ShopAddressDao shopAddressDao) {
        this.shopAddressDao = shopAddressDao;
    }

    @Override
    public Integer addAddress(ShopAddress shopAddress) {
        shopAddress.setId(shopAddressDao.addAddress(shopAddress));
        logger.info("Add {} into DataBase.", shopAddress);
        return shopAddress.getId();
    }

    @Override
    public void delAddress(Integer shopAddressId) {
        shopAddressDao.delAddress(shopAddressId);
        logger.info("Delete {} from DataBase.", shopAddressId);
    }

    @Override
    public List<ShopAddress> getShopAddressList() {
        return shopAddressDao.getAddressList();
    }
}
