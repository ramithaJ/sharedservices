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
package com.wiley.gr.ace.authorservices.services.context;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.wiley.gr.ace.authorservices.services.service.NotificationManagementService;
import com.wiley.gr.ace.authorservices.services.service.TemplateManagementService;
import com.wiley.gr.ace.authorservices.services.service.impl.NotificationManagementServiceImpl;
import com.wiley.gr.ace.authorservices.services.service.impl.TemplateManagementServiceImpl;

/**
 * The Class NotificationTemplateServiceBeanConfig.
 * 
 * @author virtusa version 1.0
 */
@Configuration
public class NotificationTemplateServiceBeanConfig {

    /**
     * Template management service.
     *
     * @return the template management service
     */
    @Bean(name = "TemplateManagementService")
    public TemplateManagementService templateManagementService() {
        return new TemplateManagementServiceImpl();
    }

    /**
     * Notification management service.
     *
     * @return the notification management service
     */
    @Bean(name = "NotificationManagementService")
    public NotificationManagementService notificationManagementService() {
        return new NotificationManagementServiceImpl();
    }
}
