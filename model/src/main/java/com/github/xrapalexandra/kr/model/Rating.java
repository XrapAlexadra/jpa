package com.github.xrapalexandra.kr.model;

public class Rating {

    private Integer Id;
    private Integer mark;
    private User user;
    private Product product;

    public Rating(Integer mark, User user, Product product) {
        this.mark = mark;
        this.user = user;
        this.product = product;
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public Integer getMark() {
        return mark;
    }

    public void setMark(Integer mark) {
        this.mark = mark;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public String toString() {
        return "Rating{" +
                "Id=" + Id +
                ", mark=" + mark +
                ", user=" + user +
                ", product=" + product +
                '}';
    }
}
