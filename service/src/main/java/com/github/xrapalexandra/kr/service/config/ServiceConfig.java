package com.github.xrapalexandra.kr.service.config;

import com.github.xrapalexandra.kr.dao.config.DaoConfig;
import com.github.xrapalexandra.kr.service.*;
import com.github.xrapalexandra.kr.service.impl.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfig {

    private DaoConfig daoConfig;

    public ServiceConfig(DaoConfig daoConfig) {
        this.daoConfig = daoConfig;
    }

    @Bean
    public UserService userService(){
        return new DefaultUserService(daoConfig.userDao());
    }

    @Bean
    public ShopAddressService shopAddressService(){
        return new DefaultShopAddressService(daoConfig.shopAddressDao());
    }

    @Bean
    public RatingService ratingService(){
        return new DefaultRatingService(daoConfig.ratingDao());
    }

    @Bean
    public ProductService productService(){
        return  new DefaultProductService(daoConfig.productDao());
    }

    @Bean
    public OrderService orderService(){
        return new DefaultOrderService(daoConfig.orderDao(), daoConfig.productDao());
    }
}
