package fr.polytech.codev.backend.sessions;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateSession {

    public static final String HIBERNATE_CONFIGURATION_FILE = "hibernate.cfg.xml";

    private static final Object lock = new Object();

    private static SessionFactory sessionFactory = null;

    private HibernateSession() {
        // Singleton
    }

    public static Session getSession() {
        synchronized (lock) {
            if (sessionFactory == null) {
                sessionFactory = new Configuration().configure(HIBERNATE_CONFIGURATION_FILE).buildSessionFactory();
            }

            return sessionFactory.openSession();
        }
    }
}