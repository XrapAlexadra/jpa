package com.github.xrapalexandra.kr.model;

import java.util.List;

public class Order {

    private Integer id;
    private User user;
    private List<OrderContent> contentList;
    private Status status = Status.ORDER;

    public Order() {
    }

    public Order(User user, List<OrderContent> contentList, Status status) {
        this.user = user;
        this.contentList = contentList;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<OrderContent> getContentList() {
        return contentList;
    }

    public void setContentList(List<OrderContent> contentList) {
        this.contentList = contentList;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", user=" + user +
                ", contentList=" + contentList +
                ", status=" + status +
                '}';
    }
}
