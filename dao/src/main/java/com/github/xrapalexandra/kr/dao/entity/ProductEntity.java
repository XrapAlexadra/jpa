package com.github.xrapalexandra.kr.dao.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "product")
public class ProductEntity {

    private Integer id;

    private String name;
    private Integer quantity;
    private Integer price;
    private String description;
    private String image;

    private List<RatingEntity> ratingList = new ArrayList<>();
    private List<OrderContentEntity> orderContentList = new ArrayList<>();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    public List<RatingEntity> getRatingList() {
        return ratingList;
    }

    public void setRatingList(List<RatingEntity> ratingList) {
        this.ratingList = ratingList;
    }

    @OneToMany(mappedBy = "product")
    public List<OrderContentEntity> getOrderContentList() {
        return orderContentList;
    }

    public void setOrderContentList(List<OrderContentEntity> orderContentList) {
        this.orderContentList = orderContentList;
    }

    @Column(nullable = false, unique = true, length=170)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(nullable = false)
    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Column(nullable = false)
    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
