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
@Transactional
public class ProductDaoTest {

    @Autowired
    public ProductDao productDao;
    @Autowired
    public UserDao userDao;
    @Autowired
    private OrderDao orderDao;

    @Test
    void addProduct() {
        Product product = new Product("somth", 78, 56);
        product.setId(productDao.addProduct(product));
        assertNotNull(product.getId());
    }

    @Test
    void  getProductById(){
        Product product = new Product("item9", 56, 45);
        product.setId(productDao.addProduct(product));
        assertEquals(product, productDao.getProductById(product.getId()));
    }

    @Test
    void getProductByIdNull(){
        assertNull(productDao.getProductById(15000));
    }

    @Test
    void updateProductFalse(){
        Product product1 = new Product("item10", 13, 12);
        Product product2 = new Product("item21", 6, 8);
        product1.setId(productDao.addProduct(product1));
        product2.setId(productDao.addProduct(product2));
        product1.setPrice(20);
        product1.setName(product2.getName());
        assertFalse(productDao.updateProduct(product1));
    }

    @Test
    void updateProduct(){
        Product product1 = new Product("item22", 14, 11);
        product1.setId(productDao.addProduct(product1));
        product1.setPrice(20);
        product1.setQuantity(1);
        assertTrue(productDao.updateProduct(product1));
    }

    @Test
    void delProduct(){
        Product product = new Product("item15", 6, 8);
        product.setId(productDao.addProduct(product));
        productDao.delProduct(product.getId());
        assertNull(productDao.getProductById(product.getId()));
    }
    @Test
    void updateProductQuantity(){
        User user = new User("user234", Role.USER, "pass15");
        Product product = new Product("item234", 20, 67);

        user.setId(userDao.addUser(user));
        product.setId(productDao.addProduct(product));

        OrderContent orderContent = new OrderContent(product, 2);
        List<OrderContent> orderContentList = new ArrayList<>();
        orderContentList.add(orderContent);

        Order order = new Order(user, orderContentList, Status.CONFIRMED);
        order.setId(orderDao.addOrder(order));

        Integer result = product.getQuantity()-orderContentList.get(0).getOrderQuantity();
        productDao.updateQuantityByOrder(order);
        assertEquals(result, productDao.getProductById(product.getId()).getQuantity());
    }

//    @Test
//    void add(){
//        Product product = new Product("item150", 6, 8);
//        productDao.addProduct(product);
//        product = new Product("item220", 14, 11);
//        productDao.addProduct(product);
//        product = new Product("item100", 13, 12);
//        productDao.addProduct(product);
//        product = new Product("item210", 6, 8);
//        productDao.addProduct(product);
//    }
}
