package com.github.xrapalexandra.kr.dao.converter;

import com.github.xrapalexandra.kr.dao.entity.ProductEntity;
import com.github.xrapalexandra.kr.model.Product;

public class ProductConverter {

    public static ProductEntity toEntity(Product product) {
        if (product == null)
            return null;
        final ProductEntity productEntity = new ProductEntity();
        productEntity.setId(product.getId());
        productEntity.setName(product.getName());
        productEntity.setPrice(product.getPrice());
        productEntity.setQuantity(product.getQuantity());
        productEntity.setDescription(product.getDescription());
        productEntity.setImage(product.getImage());
        return productEntity;
    }

    public static Product fromEntity(ProductEntity productEntity){
        if (productEntity == null)
            return null;
        final Product product = new Product(
                productEntity.getName(),
                productEntity.getQuantity(),
                productEntity.getPrice()
        );
        product.setId(productEntity.getId());
        product.setDescription(productEntity.getDescription());
        product.setImage(productEntity.getImage());
        return product;
    }
}
