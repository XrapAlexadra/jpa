package com.github.xrapalexandra.kr.model;

public class ShopAddress extends Address{

    private Integer id;

    public ShopAddress() {
    }

    public ShopAddress(String city, String street, String building) {
        super(city, street, building);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
