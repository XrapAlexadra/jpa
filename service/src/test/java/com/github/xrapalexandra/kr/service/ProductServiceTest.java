package com.github.xrapalexandra.kr.service;

import com.github.xrapalexandra.kr.dao.ProductDao;
import com.github.xrapalexandra.kr.model.Product;
import com.github.xrapalexandra.kr.service.impl.DefaultProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductDao productDao;

    @InjectMocks
    private DefaultProductService productService;
//
//    @Test
//    void updateProduct() {
//        Product product = new Product("item22", 5, 10);
//        when(productDao.findSameProduct(product)).thenReturn(null);
//        assertTrue(productService.updateProduct(product));
//    }

//    @Test
//    void updateProductIsExist() {
//        Product product = new Product("item22", 5, 10);
//        when(productDao.findSameProduct(product)).thenReturn(product);
//        assertFalse(productService.updateProduct(product));
//    }

}
