package com.github.xrapalexandra.kr.service;

import com.github.xrapalexandra.kr.model.Product;

import java.util.List;

public interface ProductService {

    Boolean addProduct(Product product);

    Boolean updateProduct(Product product);

    Boolean delProduct(Integer productId);

    Product getProductById(int product_id);

    List<Product> getProductList(int page);

    Integer getPageCount();
}
