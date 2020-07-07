package com.github.xrapalexandra.kr.service.impl;

import com.github.xrapalexandra.kr.dao.ProductDao;
import com.github.xrapalexandra.kr.model.Product;
import com.github.xrapalexandra.kr.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import java.lang.invoke.MethodHandles;
import java.util.List;

@Transactional
public class DefaultProductService implements ProductService {

    private static final int MAX_NUMBER_PRODUCT_ON_PAGE = 8;
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final ProductDao productDao;

    public DefaultProductService(ProductDao productDao) {
        this.productDao = productDao;
    }

    @Override
    public Page<Product> getProductsPage(int page) {
        return productDao.getProductList(page, MAX_NUMBER_PRODUCT_ON_PAGE);
    }

    @Override
    public List<Product> getProductListByIds(List<Integer> ids) {
        return productDao.getProductListByIds(ids);
    }

    @Override
    public Boolean addProduct(Product product) {
        if (productDao.getProductByName(product.getName()) == null) {
            product.setId(productDao.saveProduct(product));
            logger.info("{} add in database (products).", product);
            return true;
        } else {
            logger.info("{} is already exist in database", product);
            return false;
        }
    }

    @Override
    public Boolean updateProduct(Product product) {
        Product productFromBd = productDao.getProductByName(product.getName());
        if (productFromBd == null || productFromBd.getId().equals(product.getId())) {
            productDao.saveProduct(product);
            logger.info("{} update in database.", product);
            return true;
        } else {
            logger.info("{} is already exist in database.", product.getName());
            return false;
        }
    }

    @Override
    public void deleteProduct(Integer productId) {
        productDao.deleteProduct(productId);
        logger.info("Delete product with ID: {} from database.", productId);

    }

    @Override
    public Product getProductById(int product_id) {
        return productDao.getProductById(product_id);
    }
}
