package com.github.xrapalexandra.kr.dao.entity;

import javax.persistence.*;

@Entity
@Table(name = "shop_address")
public class ShopAddressEntity extends  AddressEntity{

    private Integer id;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}
