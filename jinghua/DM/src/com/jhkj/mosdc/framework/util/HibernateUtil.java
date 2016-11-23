package com.jhkj.mosdc.framework.util;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
    
    private static final SessionFactory sessionFactory;
    
    static {
        try {
            Configuration cfg = new Configuration().configure();
            sessionFactory = cfg.buildSessionFactory();
        } catch(Throwable e) {
            System.err.println("Initial SessionFactory creation failed" + e );
            throw new ExceptionInInitializerError(e);
        }
    }
    
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
    
    public static Session getSession() {
        return sessionFactory.openSession();
    }
    
    public static void closeSession(Session session) throws HibernateException {
        if(session != null) {
            if(session.isOpen()) {
                session.close();
            }
        }
    }
    
    public static void rollback( Transaction tran ) {
        try {
            if(tran != null) {
                tran.rollback();
            } 
        } catch (HibernateException he) {
            System.out.println("Rollback faild." + he);
        }
    }    
}





