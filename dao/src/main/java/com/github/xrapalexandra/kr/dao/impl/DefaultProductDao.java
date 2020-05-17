package com.github.xrapalexandra.kr.dao.impl;

import com.github.xrapalexandra.kr.dao.ProductDao;
import com.github.xrapalexandra.kr.dao.converter.ProductConverter;
import com.github.xrapalexandra.kr.dao.entity.ProductEntity;
import com.github.xrapalexandra.kr.dao.util.AddItemUtil;
import com.github.xrapalexandra.kr.dao.util.HibernateUtil;
import com.github.xrapalexandra.kr.model.Product;
import org.hibernate.Session;

import java.util.List;
import java.util.stream.Collectors;

public class DefaultProductDao implements ProductDao {

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
        productEntity = AddItemUtil.addItem(productEntity);
        if (productEntity == null)
            return null;
        return productEntity.getId();
    }

    final int MAX_RESULTS = 8;

    @Override
    public Integer getPageCount() {
        final Session session = HibernateUtil.getSession();
        int productCount =session.createQuery("select count(p.id) from ProductEntity  p",Long.class)
                .getSingleResult()
                .intValue();
        Integer result = (productCount / MAX_RESULTS);
        if (productCount % MAX_RESULTS != 0)
            result++;
        return  result;
    }

    @Override
    public List<Product> getProductList(int page) {
        final Session session = HibernateUtil.getSession();
        List<ProductEntity> productList = session
                .createQuery("FROM ProductEntity", ProductEntity.class)
                .setMaxResults(MAX_RESULTS)
                .setFirstResult((page - 1) * MAX_RESULTS)
                .list();
        return productList.stream()
                .map(ProductConverter::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public Product getProductById(Integer id) {
        final Session session = HibernateUtil.getSession();
        ProductEntity productEntity = session.get(ProductEntity.class, id);
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
            return true;
        } catch (Exception e) {
            session.getTransaction().rollback();
            return false;
        }
    }

    @Override
    public Boolean updateProduct(Product product) {
        final Session session = HibernateUtil.getSession();
        session.beginTransaction();
        try {
            ProductEntity productEntity = session.get(ProductEntity.class, product.getId());
            productEntity.setName(product.getName());
            productEntity.setPrice(product.getPrice());
            productEntity.setQuantity(product.getQuantity());
            productEntity.setDescription(product.getDescription());
            productEntity.setImage(product.getImage());
            session.getTransaction().commit();
            return true;
        } catch (Exception e) {
            session.getTransaction().rollback();
            return false;
        }
    }
}