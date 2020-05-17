package com.github.xrapalexandra.kr.dao.converter;

import com.github.xrapalexandra.kr.dao.entity.ShopAddressEntity;
import com.github.xrapalexandra.kr.model.ShopAddress;

public class ShopAddressConverter {

    public static ShopAddressEntity toEntity(ShopAddress shopAddress) {
        if (shopAddress == null)
            return null;
        final ShopAddressEntity shopAddressEntity = new ShopAddressEntity();
        shopAddressEntity.setId(shopAddress.getId());
        shopAddressEntity.setCity(shopAddress.getCity());
        shopAddressEntity.setStreet(shopAddress.getStreet());
        shopAddressEntity.setBuilding(shopAddress.getBuilding());
        return shopAddressEntity;
    }

    public static ShopAddress fromEntity(ShopAddressEntity shopAddressEntity){
        if (shopAddressEntity == null)
            return null;
        final ShopAddress shopAddress = new ShopAddress(
                shopAddressEntity.getCity(),
                shopAddressEntity.getStreet(),
                shopAddressEntity.getBuilding()
        );
        shopAddress.setId(shopAddressEntity.getId());
        return shopAddress;
    }
}
