package com.github.xrapalexandra.kr.service;

import com.github.xrapalexandra.kr.dao.ProductDao;
import com.github.xrapalexandra.kr.model.Product;
import com.github.xrapalexandra.kr.service.impl.DefaultProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductDao productDao;

    @InjectMocks
    private DefaultProductService productService;

    @Test
    public void addProductAlreadyExist(){
        Product product = new Product("Item345", 24, 32);
        Product productFromBd = new Product("Item345", 45, 32);
        productFromBd.setId(34);
        when(productDao.getProductByName(any())).thenReturn(productFromBd);
        assertFalse(productService.addProduct(product));
    }

    @Test
    public void addProductNotExist(){
        Product product = new Product("Item346", 24, 32);
        when(productDao.getProductByName(any())).thenReturn(null);
        assertTrue(productService.addProduct(product));
    }

    @Test
    public void updateProductNotExist(){
        Product product = new Product("Item347", 24, 32);
        product.setId(34);
        Product productFromBd = new Product("Item347", 45, 32);
        productFromBd.setId(34);
        when(productDao.getProductByName(any())).thenReturn(productFromBd);
        assertTrue(productService.updateProduct(product));
    }

    @Test
    public void updateProductAlreadyExist(){
        Product product = new Product("Item348", 24, 32);
        product.setId(34);
        Product productFromBd = new Product("Item348", 45, 32);
        productFromBd.setId(45);
        when(productDao.getProductByName(any())).thenReturn(productFromBd);
        assertFalse(productService.updateProduct(product));
    }

}