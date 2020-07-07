package com.github.xrapalexandra.kr.dao;

import com.github.xrapalexandra.kr.dao.config.DaoConfig;
import com.github.xrapalexandra.kr.model.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = DaoConfig.class)

public class ProductDaoTest {

    @Autowired
    public ProductDao productDao;
    @Autowired
    public UserDao userDao;
    @Autowired
    private OrderDao orderDao;

    @Test
    @Transactional
    void saveProduct() {
        Product product = new Product("item725", 78, 56);
        product.setId(productDao.saveProduct(product));
        assertNotNull(product.getId());
    }

    @Test
    @Transactional
    void delProduct() {
        Product product = new Product("item154", 6, 8);
        product.setId(productDao.saveProduct(product));
        productDao.deleteProduct(product.getId());
        assertNull(productDao.getProductById(product.getId()));
    }

    @Test
    @Transactional
    void getProductById() {
        Product product = new Product("item956", 56, 45);
        product.setId(productDao.saveProduct(product));
        assertEquals(product, productDao.getProductById(product.getId()));
    }

    @Test
    @Transactional
    void getProductByIdNull() {
        assertNull(productDao.getProductById(15000));
    }

    @Test
    @Transactional
    void getProductList() {
        Product product = new Product("item155", 6, 8);
        product.setId(productDao.saveProduct(product));
        assertNotNull(productDao.getProductList(0, 8));
    }

    @Test
    @Transactional
    void updateQuantityByOrder() {
        User user = new User("user234", Role.USER, "pass15");
        Product product = new Product("item234", 20, 67);
        user.setId(userDao.addUser(user));
        product.setId(productDao.saveProduct(product));

        OrderContent orderContent = new OrderContent(product, 2);
        List<OrderContent> orderContentList = new ArrayList<>();
        orderContentList.add(orderContent);
        Order order = new Order(user, orderContentList, Status.CONFIRMED);
        order.setId(orderDao.addOrder(order));

        Integer result = product.getQuantity() - orderContentList.get(0).getOrderQuantity();
        productDao.updateQuantityByOrder(order);
        assertEquals(result, productDao.getProductById(product.getId()).getQuantity());
    }


    @Test
    @Transactional
    void getProductListByIds() {
        Product product = new Product("item224", 14, 11);
        product.setId(productDao.saveProduct(product));

        List<Integer> productsIds = new ArrayList<>();
        productsIds.add(product.getId());

        assertNotNull(productDao.getProductListByIds(productsIds));
    }

    @Test
    @Transactional
    void getProductListByIdsull() {
        List<Integer> productsIds = new ArrayList<>();
        productsIds.add(1500000);

        assertNull(productDao.getProductListByIds(productsIds));
    }



}
