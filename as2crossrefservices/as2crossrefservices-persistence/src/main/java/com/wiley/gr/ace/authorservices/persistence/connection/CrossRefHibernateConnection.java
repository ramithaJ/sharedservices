package com.wiley.gr.ace.authorservices.persistence.connection;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CrossRefHibernateConnection {

    
    private static final Logger LOGGER = LoggerFactory
            .getLogger(CrossRefHibernateConnection.class);
    private static final SessionFactory sessionFactory = buildSessionFactory();
    
    private static SessionFactory buildSessionFactory() {
        try {
            Configuration configuration = new Configuration().configure();
            StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties());
            LOGGER.info("Hibernate Configuration loaded");
            
            return configuration.buildSessionFactory(builder.build());
        } catch (Exception ex) {
            LOGGER.error("Initial SessionFactory creation failed.", ex);
            throw new ExceptionInInitializerError(ex);
        }
    }
    
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
    
    public static void shutDownSessionFactory() {
        getSessionFactory().close();
    }
    


}
