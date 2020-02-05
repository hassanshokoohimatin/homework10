package ir.maktab.config;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class TestHibernateUtil {

    private static SessionFactory sessionFactory;
    private static Session session;

    static {
        sessionFactory = new Configuration().configure("hibernate.test.cfg.xml").buildSessionFactory();
        session = sessionFactory.openSession();
    }

    public static Session getSession(){
        return session;
    }
}
