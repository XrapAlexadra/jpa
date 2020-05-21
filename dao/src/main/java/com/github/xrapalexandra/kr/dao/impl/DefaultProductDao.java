package com.github.xrapalexandra.kr.dao.impl;

import com.github.xrapalexandra.kr.dao.ProductDao;
import com.github.xrapalexandra.kr.dao.converter.ProductConverter;
import com.github.xrapalexandra.kr.dao.entity.ProductEntity;
import com.github.xrapalexandra.kr.dao.util.DaoUtil;
import com.github.xrapalexandra.kr.dao.util.HibernateUtil;
import com.github.xrapalexandra.kr.model.Product;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.stream.Collectors;

public class DefaultProductDao implements ProductDao {

    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private static volatile ProductDao instance;

    public static ProductDao getInstance() {
        ProductDao localInstance = instance;
        if (localInstance == null) {
            synchronized (ProductDao.class) {
                localInstance = instance;
                if (localInstance == null)
                    instance = localInstance = new DefaultProductDao();

            }
        }
        return localInstance;
    }

    @Override
    public Integer addProduct(Product product) {
        ProductEntity productEntity = ProductConverter.toEntity(product);
        final Session session = HibernateUtil.getSession();
        session.beginTransaction();
        try {
            session.save(productEntity);
            session.getTransaction().commit();
            logger.info("{} add in database (products).", productEntity);
            return productEntity.getId();
        } catch (Exception e) {
            session.getTransaction().rollback();
            logger.info("{} is already exist in database", productEntity);
            return null;
        }
        finally {
            session.close();
        }
    }

    final int MAX_RESULTS = 8;

    @Override
    public Integer getPageCount() {
        final Session session = HibernateUtil.getSession();
        int productCount = session.createQuery("select count(p.id) from ProductEntity  p", Long.class)
                .getSingleResult()
                .intValue();
        session.close();
        Integer result = (productCount / MAX_RESULTS);
        if (productCount % MAX_RESULTS != 0)
            result++;
        return result;
    }

    @Override
    public List<Product> getProductList(int page) {
        final Session session = HibernateUtil.getSession();
        List<ProductEntity> productList = session
                .createQuery("FROM ProductEntity", ProductEntity.class)
                .setMaxResults(MAX_RESULTS)
                .setFirstResult((page - 1) * MAX_RESULTS)
                .list();
        session.close();
        return productList.stream()
                .map(ProductConverter::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public Product getProductById(Integer id) {
        final Session session = HibernateUtil.getSession();
        ProductEntity productEntity = session.get(ProductEntity.class, id);
        session.close();
        return ProductConverter.fromEntity(productEntity);
    }

    @Override
    public Boolean delProduct(Integer productId) {
        final Session session = HibernateUtil.getSession();
        session.beginTransaction();
        try {
            session.createQuery("delete from ProductEntity p where p.id = :productId")
                    .setParameter("productId", productId)
                    .executeUpdate();
            session.getTransaction().commit();
            logger.info("product {} don't delete from database", productId);
            return true;
        } catch (Exception e) {
            session.getTransaction().rollback();
            logger.info("Delete product with ID: {} from database.", productId);
            return false;
        }
        finally {
            session.close();
        }
    }

    @Override
    public Boolean updateProduct(Product product) {
        final Session session = HibernateUtil.getSession();
        session.beginTransaction();
        try {
            ProductEntity productEntity = session.get(ProductEntity.class, product.getId());
            DaoUtil.setNewValues(product, productEntity);
            session.getTransaction().commit();
            logger.info("{} update in database.", productEntity);
            return true;
        } catch (Exception e) {
            session.getTransaction().rollback();
            logger.info("{} is already exist in database.", product.getName());
            return false;
        }
        finally {
            session.close();
        }
    }
}