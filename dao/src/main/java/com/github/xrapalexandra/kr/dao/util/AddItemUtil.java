package com.github.xrapalexandra.kr.dao.util;

import org.hibernate.Session;

public class AddItemUtil {

    public static <T> T addItem(T item) {

        final Session session = HibernateUtil.getSession();
        session.beginTransaction();
        try {
            session.save(item);
            session.getTransaction().commit();
            return item;
        }
        catch (Exception e){
            session.getTransaction().rollback();
            return null;
        }
    }
}
