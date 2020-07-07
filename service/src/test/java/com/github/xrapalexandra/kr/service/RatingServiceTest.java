package com.github.xrapalexandra.kr.service;

import com.github.xrapalexandra.kr.dao.RatingDao;
import com.github.xrapalexandra.kr.model.Product;
import com.github.xrapalexandra.kr.model.Rating;
import com.github.xrapalexandra.kr.model.Role;
import com.github.xrapalexandra.kr.model.User;
import com.github.xrapalexandra.kr.service.impl.DefaultRatingService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class RatingServiceTest {

    @Mock
    private RatingDao ratingDao;

    @InjectMocks
    private DefaultRatingService ratingService;

    @Test
    public void addRatingNull(){
        Product product = new Product("name", 12, 34);
        product.setId(12);
        User user = new User("login12", Role.USER, "jhsdjkfh");
        user.setId(10);
        Rating rating = new Rating(5, user, product);
        rating.setId(10);
        Rating newRating = new Rating(2, user, product);
        List<Rating> ratingList = new ArrayList<>();
        ratingList.add(rating);

        when(ratingDao.getProductRating(any())).thenReturn(ratingList);

        assertNull(ratingService.addRating(newRating));
    }

    @Test
    public void addRating(){
        Product product = new Product("smt1", 12, 34);
        product.setId(12);
        User user = new User("login13", Role.USER, "jhsdjkfh");
        user.setId(10);
        Rating rating = new Rating(5, user, product);
        Integer ratingId = 12;
        List<Rating> ratingList = new ArrayList<>();

        when(ratingDao.addRating(any())).thenReturn(ratingId);
        when(ratingDao.getProductRating(any())).thenReturn(ratingList);

        assertEquals(ratingId, ratingService.addRating(rating));
    }
}
