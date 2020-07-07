package com.github.xrapalexandra.kr.dao.impl;

import com.github.xrapalexandra.kr.dao.ProductDao;
import com.github.xrapalexandra.kr.dao.converter.ProductConverter;
import com.github.xrapalexandra.kr.dao.entity.ProductEntity;
import com.github.xrapalexandra.kr.dao.repository.ProductRepository;
import com.github.xrapalexandra.kr.model.Order;
import com.github.xrapalexandra.kr.model.OrderContent;
import com.github.xrapalexandra.kr.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.stream.Collectors;

public class DefaultProductDao implements ProductDao {

    private final ProductRepository repository;

    public DefaultProductDao(ProductRepository repository) {
        this.repository = repository;
    }

    @Override
    public Integer saveProduct(Product product) {
        ProductEntity productEntity = ProductConverter.toEntity(product);
        repository.saveAndFlush(productEntity);
        return  productEntity.getId();
    }

    @Override
    public void deleteProduct(Integer productId) {
        repository.deleteById(productId);
    }

    @Override
    public Product getProductById(Integer id) {
        ProductEntity productEntity = repository.findById(id).orElse(null);
        return ProductConverter.fromEntity(productEntity);
    }

    @Override
    public Page<Product> getProductList(int page, int number) {
        Page<ProductEntity> productEntityPage = repository.findAll(PageRequest.of(page, number, Sort.by("id")));
        return productEntityPage.map(ProductConverter::fromEntity);

    }

    @Override
    public void updateQuantityByOrder(Order order) {
        List<OrderContent> orderContentList = order.getContentList();
        for (OrderContent i : orderContentList) {
            ProductEntity productEntity = repository.getOne(i.getProduct().getId());
            Integer resultQuantity = productEntity.getQuantity() - i.getOrderQuantity();
            productEntity.setQuantity(resultQuantity);
            repository.saveAndFlush(productEntity);
        }
    }

    @Override
    public List<Product> getProductListByIds(List<Integer> ids) {
        List<ProductEntity> productEntityList = repository.findAllById(ids);
        if (productEntityList.isEmpty())
            return null;
        return productEntityList.stream()
                .map(ProductConverter::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public Product getProductByName(String name) {
        ProductEntity productEntity = repository.findByName(name);
        return ProductConverter.fromEntity(productEntity);
    }
}