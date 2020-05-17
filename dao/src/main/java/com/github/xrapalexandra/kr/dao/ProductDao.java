package com.github.xrapalexandra.kr.dao;

import com.github.xrapalexandra.kr.model.Product;

import java.util.List;

public interface ProductDao {

    Integer addProduct(Product product);

    Boolean updateProduct(Product product);

    Boolean delProduct(Integer productId);

    Product getProductById(Integer id);

    List<Product> getProductList(int page);

    Integer getPageCount();

}
