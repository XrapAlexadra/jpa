package com.github.xrapalexandra.kr.dao.config;

import com.github.xrapalexandra.kr.dao.*;
import com.github.xrapalexandra.kr.dao.impl.*;
import com.github.xrapalexandra.kr.dao.repository.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;


@Configuration
@Import(HibernateConfig.class)
public class DaoConfig {

    @Bean
    ShopAddressDao shopAddressDao(ShopAddressRepository repository) {
        return new DefaultShopAddressDao(repository);
    }

    @Bean
    UserDao userDao(UserRepository repository){
        return new DefaultUserDao(repository);
    }

    @Bean
    ProductDao productDao(ProductRepository repository){
        return  new DefaultProductDao(repository);
    }

    @Bean
    RatingDao ratingDao(RatingRepository repository){
        return new DefaultRatingDao(repository);
    }

    @Bean
    OrderDao orderDao(OrdersRepository repository){
        return new DefaultOrderDao(repository);
    }
}
