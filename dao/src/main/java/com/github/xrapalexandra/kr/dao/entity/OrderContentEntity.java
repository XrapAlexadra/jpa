package com.github.xrapalexandra.kr.dao.entity;

import javax.persistence.*;

@Entity
@Table(name = "order_content")
public class OrderContentEntity {

    private Integer id;
    private ProductEntity product;
    private OrderEntity order;
    private Integer orderQuantity;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id", nullable = false)
    public ProductEntity getProduct() {
        return product;
    }

    public void setProduct(ProductEntity product) {
        this.product = product;
    }

    @ManyToOne
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    public OrderEntity getOrder() {
        return order;
    }

    public void setOrder(OrderEntity order) {
        this.order = order;
    }

    @Column(nullable = false)
    public Integer getOrderQuantity() {
        return orderQuantity;
    }

    public void setOrderQuantity(Integer orderQuantity) {
        this.orderQuantity = orderQuantity;
    }
}
