package com.github.xrapalexandra.kr.dao.impl;

import com.github.xrapalexandra.kr.dao.ProductDao;
import com.github.xrapalexandra.kr.dao.converter.ProductConverter;
import com.github.xrapalexandra.kr.dao.entity.ProductEntity;
import com.github.xrapalexandra.kr.dao.repository.ProductPagingRepository;
import com.github.xrapalexandra.kr.dao.repository.ProductRepository;
import com.github.xrapalexandra.kr.model.Order;
import com.github.xrapalexandra.kr.model.OrderContent;
import com.github.xrapalexandra.kr.model.Product;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.stream.Collectors;

public class DefaultProductDao implements ProductDao {

    private ProductRepository repository;
    private ProductPagingRepository pagingRepository;

    public DefaultProductDao(ProductRepository repository, ProductPagingRepository pagingRepository) {
        this.repository = repository;
        this.pagingRepository = pagingRepository;
    }

    @Override
    public Integer addProduct(Product product) {
        ProductEntity productEntity = ProductConverter.toEntity(product);
        try {
            repository.saveAndFlush(productEntity);
            return productEntity.getId();
        } catch (DataAccessException e) {
            return null;
        }
    }

    @Override
    public Boolean updateProduct(Product product) {
        return addProduct(product) != null;
    }

    @Override
    public Boolean delProduct(Integer productId) {
        try {
            repository.deleteById(productId);
            return true;
        } catch (DataAccessException e) {
            return false;
        }
    }

    @Override
    public Product getProductById(Integer id) {
        ProductEntity productEntity = repository.findById(id).orElse(null);
        return ProductConverter.fromEntity(productEntity);
    }

    @Override
    public Page<Product> getProductList(int page, int number) {
        Page<ProductEntity> productEntityPage = pagingRepository.findAll(PageRequest.of(page-1, number, Sort.by("id")));
        List<ProductEntity> productEntityList= productEntityPage.getContent();
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
        return productEntityList.stream()
                .map(ProductConverter::fromEntity)
                .collect(Collectors.toList());
    }
}