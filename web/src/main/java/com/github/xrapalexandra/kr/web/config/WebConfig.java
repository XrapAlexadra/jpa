package com.github.xrapalexandra.kr.web.config;

import com.github.xrapalexandra.kr.service.config.ServiceConfig;
import com.github.xrapalexandra.kr.web.controller.admins.AdminProductsController;
import com.github.xrapalexandra.kr.web.controller.admins.AdminOrdersController;
import com.github.xrapalexandra.kr.web.controller.admins.ShopAddressesController;
import com.github.xrapalexandra.kr.web.controller.users.BasketController;
import com.github.xrapalexandra.kr.web.controller.users.ProductsController;
import com.github.xrapalexandra.kr.web.controller.users.UsersController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.view.UrlBasedViewResolver;
import org.springframework.web.servlet.view.tiles3.TilesConfigurer;
import org.springframework.web.servlet.view.tiles3.TilesView;

import java.util.Locale;


@Configuration
@EnableWebMvc
public class WebConfig {

    private ServiceConfig serviceConfig;

    public WebConfig(ServiceConfig serviceConfig) {
        this.serviceConfig = serviceConfig;
    }

    @Bean
    public AdminProductsController adminProductsController() {
        return new AdminProductsController(serviceConfig.productService());
    }

    @Bean
    public AdminOrdersController ordersController() {
        return new AdminOrdersController(serviceConfig.orderService());
    }

    @Bean
    public ShopAddressesController shopAddressesController() {
        return new ShopAddressesController(serviceConfig.shopAddressService());
    }

    @Bean
    public BasketController basketController() {
        return new BasketController(serviceConfig.orderService(), serviceConfig.productService());
    }

    @Bean
    public ProductsController productsController() {
        return new ProductsController(serviceConfig.productService(), serviceConfig.ratingService());
    }

    @Bean
    public UsersController usersController() {
        return new UsersController(serviceConfig.shopAddressService(), serviceConfig.userService());
    }


    @Bean
    public UrlBasedViewResolver tilesViewResolver(){
        UrlBasedViewResolver resolver = new UrlBasedViewResolver();
        resolver.setViewClass(TilesView.class);
        return resolver;
    }

    @Bean
    public TilesConfigurer tilesConfigurer(){
        final TilesConfigurer tilesConfigurer = new TilesConfigurer();
        tilesConfigurer.setDefinitions("/WEB-INF/tiles.xml");
        tilesConfigurer.setCheckRefresh(true);
        return tilesConfigurer;
    }

    @Bean
    public ReloadableResourceBundleMessageSource messageSource() {
        ReloadableResourceBundleMessageSource source = new ReloadableResourceBundleMessageSource();
        source.setBasename("classpath:i18n/messages");
        source.setDefaultEncoding("UTF-8");

        return source;
    }

    @Bean
    public CookieLocaleResolver localeResolver() {
        CookieLocaleResolver resolver = new CookieLocaleResolver();
        resolver.setDefaultLocale(Locale.forLanguageTag("en"));
        resolver.setCookieName("LocaleCookie");
        resolver.setCookieMaxAge(3600);
        return resolver;
    }
}
