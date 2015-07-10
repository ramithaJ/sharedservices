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

import com.wiley.gr.ace.authorservices.persistence.connection.NotificationTemplateHibernateConnection;
import com.wiley.gr.ace.authorservices.persistence.services.NotificationManagementDAO;
import com.wiley.gr.ace.authorservices.persistence.services.TemplateManagementDAO;
import com.wiley.gr.ace.authorservices.persistence.services.impl.NotificationManagementDAOImpl;
import com.wiley.gr.ace.authorservices.persistence.services.impl.TemplateManagementDAOImpl;


/**
 * The Class NotificationTemplatePersistenceBeanConfig.
 * 
 * @author virtusa version 1.0
 */
@Configuration
public class NotificationTemplatePersistenceBeanConfig {

	/**
	 * Notification template hibernate connection.
	 *
	 * @return the notification template hibernate connection
	 */
	@Bean(name = "NotificationTemplateHibernateConnection")
	public NotificationTemplateHibernateConnection notificationTemplateHibernateConnection() {
		return new NotificationTemplateHibernateConnection();
	}

	/**
	 * Template management dao.
	 *
	 * @return the template management dao
	 */
	@Bean(name = "TemplateManagementDAO")
	public TemplateManagementDAO templateManagementDAO() {
		return new TemplateManagementDAOImpl();
	}
	
	/**
	 * Notification management dao.
	 *
	 * @return the notification management dao
	 */
	@Bean(name = "NotificationManagementDAO")
	public NotificationManagementDAO notificationManagementDAO(){
		return new NotificationManagementDAOImpl();
	}
}