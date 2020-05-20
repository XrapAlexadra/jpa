package com.github.xrapalexandra.kr.dao.util;

import com.github.xrapalexandra.kr.dao.entity.ProductEntity;
import com.github.xrapalexandra.kr.model.Product;

public class DaoUtil {

    public static void setNewValues(Product product, ProductEntity productEntity) {
        productEntity.setName(product.getName());
        productEntity.setPrice(product.getPrice());
        productEntity.setQuantity(product.getQuantity());
        productEntity.setDescription(product.getDescription());
        productEntity.setImage(product.getImage());
    }
}
