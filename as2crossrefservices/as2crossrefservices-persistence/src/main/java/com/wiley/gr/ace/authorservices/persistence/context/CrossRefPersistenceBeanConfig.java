/*******************************************************************************
 * Copyright (c) 2015 John Wiley & Sons, Inc. All rights reserved.
 *
 * All material contained herein is proprietary to John Wiley & Sons 
 * and its third party suppliers, if any. The methods, techniques and 
 * technical concepts contained herein are considered trade secrets 
 * and confidential and may be protected by intellectual property laws.  
 * Reproduction or distribution of this material, in whole or in part, 
 * is strictly forbidden except by express prior written permission 
 * of John Wiley & Sons.
 *******************************************************************************/
package com.wiley.gr.ace.authorservices.persistence.context;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.wiley.gr.ace.authorservices.persistence.connection.CrossRefHibernateConnection;
import com.wiley.gr.ace.authorservices.persistence.services.CrossRefDAO;
import com.wiley.gr.ace.authorservices.persistence.services.impl.CrossRefDAOImpl;

/**
 * The Class CrossRefPersistenceBeanConfig.
 * 
 * @author virtusa version 1.0
 */
@Configuration
public class CrossRefPersistenceBeanConfig {

    /**
     * Cross ref hibernate connection.
     *
     * @return the cross ref hibernate connection
     */
    @Bean(name = "CrossRefHibernateConnection")
    public CrossRefHibernateConnection crossRefHibernateConnection() {
        return new CrossRefHibernateConnection();
    }

    /**
     * Cross ref dao.
     *
     * @return the cross ref dao
     */
    @Bean(name = "CrossRefDAO")
    public CrossRefDAO crossRefDAO() {
        return new CrossRefDAOImpl();
    }

}
