package com.github.xrapalexandra.kr.dao.entity;

import com.github.xrapalexandra.kr.model.Status;

import javax.persistence.*;
import java.util.List;


@Entity
@Table(name = "orders")
public class OrderEntity {

    private Integer id;
    private UserEntity user;
    private Status status;

    private List<OrderContentEntity> orderContentList;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    public List<OrderContentEntity> getOrderContentList() {
        return orderContentList;
    }

    public void setOrderContentList(List<OrderContentEntity> orderContentList) {
        this.orderContentList = orderContentList;
    }

    @Enumerated(EnumType.STRING)
    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
