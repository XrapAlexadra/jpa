package com.github.xrapalexandra.kr.dao.util;

import org.hibernate.Session;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class HibernateUtil {

    private static EntityManagerFactory emFactory =
            Persistence.createEntityManagerFactory("com/github/xrapalexandra/kr/dao");


    public static Session getSession(){
        return emFactory.createEntityManager().unwrap(Session.class);
    }

    private static void closeSession(){
        emFactory.close();
    }

}
