package com.github.xrapalexandra.kr.dao.entity;


import javax.persistence.*;


@Entity
@Table(name = "user_address")
public class UserAddressEntity extends AddressEntity {

    private UserEntity user;
    private Integer userId;


    @Column(name = "user_id", updatable = false, insertable = false)
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }
}
