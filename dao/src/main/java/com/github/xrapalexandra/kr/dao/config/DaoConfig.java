package com.github.xrapalexandra.kr.dao.config;

import com.github.xrapalexandra.kr.dao.*;
import com.github.xrapalexandra.kr.dao.impl.*;
import com.github.xrapalexandra.kr.dao.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;


@Configuration
@Import(HibernateConfig.class)
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "com.github.xrapalexandra.kr.dao.repository")
public class DaoConfig {

    @Autowired
    private ShopAddressRepository shopAddressRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private RatingRepository ratingRepository;
    @Autowired
    private OrdersRepository ordersRepository;


    @Bean
    public ShopAddressDao shopAddressDao() {
        return new DefaultShopAddressDao(shopAddressRepository);
    }

    @Bean
    public UserDao userDao() {
        return new DefaultUserDao(userRepository);
    }

    @Bean
    public ProductDao productDao() {
        return new DefaultProductDao(productRepository);
    }

    @Bean
    public RatingDao ratingDao() {
        return new DefaultRatingDao(ratingRepository);
    }

    @Bean
    public OrderDao orderDao() {
        return new DefaultOrderDao(ordersRepository);
    }
}
