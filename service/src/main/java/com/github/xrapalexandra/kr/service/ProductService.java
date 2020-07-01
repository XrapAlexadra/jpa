package com.github.xrapalexandra.kr.service;

import com.github.xrapalexandra.kr.model.Product;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {

    Boolean addProduct(Product product);

    Boolean updateProduct(Product product);

    Boolean delProduct(Integer productId);

    Product getProductById(int product_id);

    Page<Product> getProductsPage(int page);

    List<Product> getProductListByIds(List<Integer> ids);
}
