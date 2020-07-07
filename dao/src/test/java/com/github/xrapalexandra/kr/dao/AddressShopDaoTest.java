package com.github.xrapalexandra.kr.dao;

import com.github.xrapalexandra.kr.dao.config.DaoConfig;
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
    public void getAddressList(){
        ShopAddress address = new ShopAddress("Brest", "street2", "5а");
        address.setId(shopAddressDao.addAddress(address));
        assertNotNull(shopAddressDao.getAddressList());
    }
}
