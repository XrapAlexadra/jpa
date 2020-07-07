package com.github.xrapalexandra.kr.dao;

import com.github.xrapalexandra.kr.dao.config.DaoConfig;
import com.github.xrapalexandra.kr.model.Product;
import com.github.xrapalexandra.kr.model.Rating;
import com.github.xrapalexandra.kr.model.Role;
import com.github.xrapalexandra.kr.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = DaoConfig.class)
@Transactional
public class RatingDaoTest {

    @Autowired
    public RatingDao ratingDao;
    @Autowired
    public ProductDao productDao;
    @Autowired
    public UserDao userDao;

    @Test
    void addRating() {
        User user = new User("user161", Role.USER, "pass16");
        Product product = new Product("item161", 21, 30);
        user.setId(userDao.addUser(user));
        product.setId(productDao.saveProduct(product));

        Rating rating = new Rating(5, user, product);
        rating.setId(ratingDao.addRating(rating));
        assertNotNull(rating.getId());
    }

    @Test
    void getAvrRatingByProductId() {
        User user = new User("user162", Role.USER, "pass16");
        Product product = new Product("item162", 21, 30);
        user.setId(userDao.addUser(user));
        product.setId(productDao.saveProduct(product));

        Rating rating = new Rating(5, user, product);
        rating.setId(ratingDao.addRating(rating));
        Double mark = ratingDao.getAvrRatingByProduct(product);
        assertNotNull(mark);
        assertEquals(5, mark, 0.01);
    }

    @Test
    void getAvrRatingByProductIdNull() {
        User user = new User("user163", Role.USER, "pass16");
        Product product = new Product("item163", 21, 30);
        user.setId(userDao.addUser(user));
        product.setId(productDao.saveProduct(product));
        assertNull(ratingDao.getAvrRatingByProduct(product));
    }

    @Test
    void getProductRating(){
        User user = new User("user161", Role.USER, "pass16");
        Product product = new Product("item161", 21, 30);
        user.setId(userDao.addUser(user));
        product.setId(productDao.saveProduct(product));

        Rating rating = new Rating(5, user, product);
        rating.setId(ratingDao.addRating(rating));

        assertNotNull(ratingDao.getProductRating(product.getId()));
    }

}
