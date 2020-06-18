package com.github.xrapalexandra.kr.service.config;

import com.github.xrapalexandra.kr.dao.*;
import com.github.xrapalexandra.kr.dao.config.DaoConfig;
import com.github.xrapalexandra.kr.service.*;
import com.github.xrapalexandra.kr.service.impl.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(DaoConfig.class)
public class ServiceConfig {

    @Bean
    UserService userService(UserDao userDao){
        return new DefaultUserService(userDao);
    }

    @Bean
    ShopAddressService shopAddressService(ShopAddressDao shopAddressDao){
        return new DefaultShopAddressService(shopAddressDao);
    }

    @Bean
    RatingService ratingService(RatingDao ratingDao){
        return new DefaultRatingService(ratingDao);
    }

    @Bean
    ProductService productService(ProductDao productDao){
        return  new DefaultProductService(productDao);
    }

    @Bean
    OrderService orderService(OrderDao orderDao, ProductDao productDao){
        return new DefaultOrderService(orderDao, productDao);
    }
}
