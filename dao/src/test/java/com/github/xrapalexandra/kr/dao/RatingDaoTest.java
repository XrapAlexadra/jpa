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

    private User user = new User("user16", Role.USER, "pass16");
    private Product product = new Product("item16", 21, 30);

    @Test
    void addRating() {
        user.setId(userDao.addUser(user));
        product.setId(productDao.addProduct(product));

        Rating rating = new Rating(5, user, product);
        rating.setId(ratingDao.addRating(rating));

        assertNotNull(rating.getId());
    }

    @Test
    void getAvrRatingByProductId() {
        user.setId(userDao.addUser(user));
        product.setId(productDao.addProduct(product));

        Rating rating = new Rating(5, user, product);
        rating.setId(ratingDao.addRating(rating));
        Double mark = ratingDao.getAvrRatingByProduct(product);

        assertEquals(5, mark, 0.01);
    }

    @Test
    void getAvrRatingByProductIdNull() {
        product.setId(productDao.addProduct(product));
        assertNull(ratingDao.getAvrRatingByProduct(product));
    }

}
