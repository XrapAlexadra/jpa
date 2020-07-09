package com.github.xrapalexandra.kr.model;

public class OrderContent {

    private Integer id;
    private Product product;
    private Integer orderQuantity;


    public OrderContent() {
    }

    public OrderContent(Product product, Integer orderQuantity) {
        this.product = product;
        this.orderQuantity = orderQuantity;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getOrderQuantity() {
        return orderQuantity;
    }

    public void setOrderQuantity(Integer orderQuantity) {
        this.orderQuantity = orderQuantity;
    }

    @Override
    public String toString() {
        return "OrderContent{" +
                "id=" + id +
                ", product=" + product +
                ", orderQuantity=" + orderQuantity +
                '}';
    }
}
