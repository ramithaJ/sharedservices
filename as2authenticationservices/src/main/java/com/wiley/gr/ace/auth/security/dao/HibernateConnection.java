/**
 * ****************************************************************************
 * Copyright (c) 2015 John Wiley & Sons, Inc. All rights reserved.
 * <p>
 * All material contained herein is proprietary to John Wiley & Sons
 * and its third party suppliers, if any. The methods, techniques and
 * technical concepts contained herein are considered trade secrets
 * and confidential and may be protected by intellectual property laws.
 * Reproduction or distribution of this material, in whole or in part,
 * is strictly forbidden except by express prior written permission
 * of John Wiley & Sons.
 * *****************************************************************************
 */
package com.wiley.gr.ace.auth.security.dao;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Virtusa
 */
public class HibernateConnection {

    /**
     * This field holds the value of LOGGER
     */
    private static final Logger LOGGER = LoggerFactory
            .getLogger(HibernateConnection.class);
    /**
     * This field holds the value of sessionFactory
     */
    private static final SessionFactory SESSION_FACTORY = buildSessionFactory();

    private HibernateConnection() {

    }

    /**
     * This method buildSessionFactory builds the sessionfactory object
     *
     * @return SessionFactory
     */
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

    /**
     * This method returns the sessionFactory object
     *
     * @return SessionFactory
     */
    public static SessionFactory getSessionFactory() {
        return SESSION_FACTORY;
    }

    /**
     * This method is responsible for closing the sessionfactory
     */
    public static void shutDownSessionFactory() {
        getSessionFactory().close();
    }

}
