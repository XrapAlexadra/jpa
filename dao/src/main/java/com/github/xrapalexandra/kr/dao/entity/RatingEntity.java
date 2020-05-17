package com.github.xrapalexandra.kr.dao.entity;


import javax.persistence.*;

@Entity
@Table(name = "rating")
public class RatingEntity {

    private Integer id;
    private Integer mark;
    private UserEntity user;
    private ProductEntity product;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    @Column(nullable = false)
    public Integer getMark() {
        return mark;
    }

    public void setMark(Integer mark) {
        this.mark = mark;
    }

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    public ProductEntity getProduct() {
        return product;
    }

    public void setProduct(ProductEntity producty) {
        this.product = producty;
    }

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

}
