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
package com.wiley.gr.ace.discount.services.context;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.wiley.gr.ace.discount.services.service.DiscountService;
import com.wiley.gr.ace.discount.services.service.impl.DiscountServiceImpl;

@Configuration
public class ServiceBeanConfig {
    
    @Bean(name = "discountService")
    public DiscountService discountService() {
        return new DiscountServiceImpl();
    }
          
}
