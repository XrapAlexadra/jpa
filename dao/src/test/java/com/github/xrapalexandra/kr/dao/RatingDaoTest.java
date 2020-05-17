package com.github.xrapalexandra.kr.dao;

import com.github.xrapalexandra.kr.dao.impl.DefaultProductDao;
import com.github.xrapalexandra.kr.dao.impl.DefaultRatingDao;
import com.github.xrapalexandra.kr.dao.impl.DefaultUserDao;
import com.github.xrapalexandra.kr.model.Product;
import com.github.xrapalexandra.kr.model.Rating;
import com.github.xrapalexandra.kr.model.Role;
import com.github.xrapalexandra.kr.model.User;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RatingDaoTest {

    private RatingDao ratingDao = DefaultRatingDao.getInstance();

    static User user;
    static Product product;
    static ProductDao productDao = DefaultProductDao.getInstance();
    static UserDao userDao = DefaultUserDao.getInstance();

    //
    @BeforeAll
    private static void init() {
        user = new User("user16", Role.USER, "pass16");
        product = new Product("item16", 21, 30);
        user.setId(userDao.addUser(user));
        product.setId(productDao.addProduct(product));
    }

    @Test
    void addRating() {
        Rating rating = new Rating(5, user, product);
        rating.setId(ratingDao.addRating(rating));
        assertNotEquals(0, rating.getId());
        ratingDao.delRating(rating.getId());
    }

    @Test
    void getAvrRatingByProductId() {
        Rating rating = new Rating(5, user, product);
        rating.setId(ratingDao.addRating(rating));
        int mark = (int) Math.round(ratingDao.getAvrRatingByProduct(product));
        assertEquals(5, mark);
        ratingDao.delRating(rating.getId());
    }

    @Test
    void getAvrRatingByProductIdNull() {
        assertNull(ratingDao.getAvrRatingByProduct(product));
    }

    @AfterAll
    private static void cleanBD() {
        userDao.delUser(user.getId());
        productDao.delProduct(product.getId());
    }
}
