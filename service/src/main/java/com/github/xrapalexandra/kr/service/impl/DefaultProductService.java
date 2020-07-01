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

    private ProductDao productDao;

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
        product.setId(productDao.addProduct(product));
        if (product.getId() == null) {
            logger.info("{} is already exist in database", product);
            return false;
        } else {
            logger.info("{} add in database (products).", product);
            return true;
        }
    }

    @Override
    public Boolean updateProduct(Product product) {
        Boolean result = productDao.updateProduct(product);
        if (!result)
            logger.info("{} is already exist in database.", product.getName());
        else
            logger.info("{} update in database.", product);
        return result;
    }

    @Override
    public Boolean delProduct(Integer productId) {
        Boolean result = productDao.delProduct(productId);
        if (!result)
            logger.info("product {} don't delete from database", productId);
        else
            logger.info("Delete product with ID: {} from database.", productId);
        return result;
    }

    @Override
    public Product getProductById(int product_id) {
        return productDao.getProductById(product_id);
    }

}
