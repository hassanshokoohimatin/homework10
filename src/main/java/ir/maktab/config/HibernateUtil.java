package ir.maktab.config;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

    private static SessionFactory sessionFactoryOne;
    private static SessionFactory sessionFactoryTwo;
    private static Session sessionOne;
    private static Session sessionTwo;

    static {
        sessionFactoryOne = new Configuration().configure("hibernatedb1.cfg.xml").buildSessionFactory();
        sessionFactoryTwo = new Configuration().configure("hibernatedb2.cfg.xml").buildSessionFactory();
        sessionOne = sessionFactoryOne.openSession();
        sessionTwo = sessionFactoryTwo.openSession();
    }

    public static Session getSessionOne() {
        return sessionOne;
    }
    public static Session getSessionTwo(){return sessionTwo;}
}
