package com.github.xrapalexandra.kr.service.impl;

import com.github.xrapalexandra.kr.dao.ProductDao;
import com.github.xrapalexandra.kr.dao.impl.DefaultProductDao;
import com.github.xrapalexandra.kr.model.Product;
import com.github.xrapalexandra.kr.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.util.List;

public class DefaultProductService implements ProductService {

    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private ProductDao productDao = DefaultProductDao.getInstance();

    private DefaultProductService() {
    }

    private static volatile ProductService instance;

    public static ProductService getInstance() {
        ProductService localInstance = instance;
        if (localInstance == null) {
            synchronized (ProductService.class) {
                localInstance = instance;
                if (localInstance == null)
                    localInstance = instance = new DefaultProductService();
            }
        }
        return localInstance;
    }

    @Override
    public List<Product> getProductList(int page) {
        return productDao.getProductList(page);
    }

    @Override
    public Integer getPageCount(){
        return productDao.getPageCount();
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
