package com.github.xrapalexandra.kr.service;

import com.github.xrapalexandra.kr.dao.ProductDao;
import com.github.xrapalexandra.kr.model.Product;
import com.github.xrapalexandra.kr.service.impl.DefaultOrderService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private ProductDao productDao;

    @InjectMocks
    private DefaultOrderService orderService;

    @Test
    void createOrderContent(){
        List<Integer> productsIdsList = new ArrayList<>();
        productsIdsList.add(12);
        Integer[] quantities = {2};

        Product product = new Product("item452", 45, 23);
        when(productDao.getProductById(any())).thenReturn(product);

        assertNotNull(orderService.createOrderContent(productsIdsList, quantities));
    }

}