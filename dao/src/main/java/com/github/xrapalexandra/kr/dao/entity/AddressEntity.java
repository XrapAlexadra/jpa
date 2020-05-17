package com.github.xrapalexandra.kr.dao.entity;

import javax.persistence.*;

@MappedSuperclass
public abstract class AddressEntity {

    private String city;
    private String street;
    private String building;


    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

}
