package com.github.xrapalexandra.kr.dao.entity;

import com.github.xrapalexandra.kr.model.Role;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "user")
public class UserEntity {

    private Integer id;
    private String login;
    private String pass;
    private Role role;

    private UserAddressEntity address;

    private List<RatingEntity> ratingList;
    private List<OrderEntity> orderList;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    public List<RatingEntity> getRatingList() {
        return ratingList;
    }

    public void setRatingList(List<RatingEntity> ratingList) {
        this.ratingList = ratingList;
    }

    @OneToMany(mappedBy = "user")
    public List<OrderEntity> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<OrderEntity> orderList) {
        this.orderList = orderList;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(unique = true, nullable = false, length=170)
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    @Column(nullable = false)
    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    @Enumerated(EnumType.STRING)
    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

   @OneToOne(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
    public UserAddressEntity getAddress() {
        return address;
    }

    public void setAddress(UserAddressEntity address) {
        this.address = address;
    }

}
