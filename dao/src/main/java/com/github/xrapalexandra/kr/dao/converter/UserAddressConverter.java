package com.github.xrapalexandra.kr.dao.converter;

import com.github.xrapalexandra.kr.dao.entity.UserAddressEntity;
import com.github.xrapalexandra.kr.model.UserAddress;

public class UserAddressConverter {

    public static UserAddressEntity toEntity(UserAddress address){
        if (address == null)
            return null;
        final UserAddressEntity userAddressEntity = new UserAddressEntity();
        userAddressEntity.setBuilding(address.getBuilding());
        userAddressEntity.setCity(address.getCity());
        userAddressEntity.setStreet(address.getStreet());
        return userAddressEntity;
    }

    public static UserAddress fromEntity(UserAddressEntity address){
        if (address == null)
            return null;
        return new UserAddress(
                address.getCity(),
                address.getStreet(),
                address.getBuilding()
        );
    }
}
