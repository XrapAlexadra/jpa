package com.github.xrapalexandra.kr.dao;

import com.github.xrapalexandra.kr.model.Order;
import com.github.xrapalexandra.kr.model.Product;
import org.springframework.data.domain.Page;


public interface ProductDao {

    Integer addProduct(Product product);

    Boolean updateProduct(Product product);

    Boolean delProduct(Integer productId);

    Product getProductById(Integer id);

    Page<Product> getProductList(int page, int number);

    Integer getPageCount(int number);

    void updateQuantityByOrder(Order order);
}
