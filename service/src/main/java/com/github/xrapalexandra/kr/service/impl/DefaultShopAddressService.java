package com.github.xrapalexandra.kr.service.impl;

import com.github.xrapalexandra.kr.dao.ShopAddressDao;
import com.github.xrapalexandra.kr.dao.impl.DefaultShopAddressDao;
import com.github.xrapalexandra.kr.model.ShopAddress;
import com.github.xrapalexandra.kr.service.ShopAddressService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.util.List;

public class DefaultShopAddressService implements ShopAddressService {

    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private DefaultShopAddressService() {
    }

    private static volatile ShopAddressService instance;


    public static ShopAddressService getInstance() {
        ShopAddressService localInstance = instance;
        if (localInstance == null) {
            synchronized (ShopAddressService.class) {
                localInstance = instance;
                if (localInstance == null)
                    localInstance = instance = new DefaultShopAddressService();
            }
        }
        return localInstance;
    }

    private ShopAddressDao shopAddressDao = DefaultShopAddressDao.getInstance();

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
    public void updateAddress(ShopAddress newShopAddress) {
        shopAddressDao.updateAddress(newShopAddress);
        logger.info("Update in DataBase to {}", newShopAddress);
    }

    @Override
    public List<ShopAddress> getShopAddressList() {
        return shopAddressDao.getAddressList();
    }
}
