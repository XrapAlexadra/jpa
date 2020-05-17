package com.github.xrapalexandra.kr.dao;

import com.github.xrapalexandra.kr.dao.impl.DefaultProductDao;
import com.github.xrapalexandra.kr.model.Product;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ProductDaoTest {

    public ProductDao productDao = DefaultProductDao.getInstance();

    @Test
    void addProduct() {
        Product product = new Product("somth", 78, 56);
        product.setId(productDao.addProduct(product));
        assertNotEquals(0, product.getId() );
        productDao.delProduct(product.getId());
    }

    @Test
    void  getProductById(){
        Product product = new Product("item9", 56, 45);
        product.setId(productDao.addProduct(product));
        assertEquals(product, productDao.getProductById(product.getId()));
        productDao.delProduct(product.getId());
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
        productDao.delProduct(product1.getId());
        productDao.delProduct(product2.getId());
    }

    @Test
    void updateProduct(){
        Product product1 = new Product("item22", 14, 11);
        product1.setId(productDao.addProduct(product1));
        product1.setPrice(20);
        product1.setQuantity(1);
        assertTrue(productDao.updateProduct(product1));
        productDao.delProduct(product1.getId());
    }

    @Test
    void delProduct(){
        Product product = new Product("item15", 6, 8);
        product.setId(productDao.addProduct(product));
        productDao.delProduct(product.getId());
        assertNull(productDao.getProductById(product.getId()));
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
