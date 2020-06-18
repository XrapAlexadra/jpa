package com.github.xrapalexandra.kr.dao;

import com.github.xrapalexandra.kr.dao.config.DaoConfig;
import com.github.xrapalexandra.kr.dao.impl.DefaultShopAddressDao;
import com.github.xrapalexandra.kr.model.ShopAddress;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = DaoConfig.class)
@Transactional
public class AddressShopDaoTest {

    @Autowired
    ShopAddressDao shopAddressDao;

    @Test
    void addAddress() {
        ShopAddress address = new ShopAddress("Rio", "street", "12");
        address.setId(shopAddressDao.addAddress(address));
        assertNotNull(address.getId());
        shopAddressDao.delAddress(address.getId());
    }

    @Test
    void delAddress() {
        ShopAddress address = new ShopAddress("Minsk", "street2", "1");
        address.setId(shopAddressDao.addAddress(address));
        shopAddressDao.delAddress(address.getId());
        List<ShopAddress> addressList = shopAddressDao.getAddressList();
        assertEquals(-1, addressList.indexOf(address));
    }

    @Test
    void updateAddress() {
        ShopAddress address = new ShopAddress("Brest", "street2", "5а");
        address.setId(shopAddressDao.addAddress(address));
        address.setBuilding("24d");
        shopAddressDao.updateAddress(address);
        List<ShopAddress> addressList = shopAddressDao.getAddressList();
        assertNotEquals(-1, addressList.indexOf(address));
        shopAddressDao.delAddress(address.getId());
    }

//    @Test
//    void addShops(){
//        ShopAddress address = new ShopAddress("Rio", "street", "12");
//        shopAddressDao.addAddress(address);
//        address = new ShopAddress("Minsk", "street1", "1");
//        shopAddressDao.addAddress(address);
//        address = new ShopAddress("Brest", "street2", "5а");
//        shopAddressDao.addAddress(address);
//        address = new ShopAddress("Brest", "street3", "67");
//        shopAddressDao.addAddress(address);
//    }
}
