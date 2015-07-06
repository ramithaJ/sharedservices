package com.wiley.gr.ace.authorservices.services.context;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.wiley.gr.ace.authorservices.services.service.CrossRefService;
import com.wiley.gr.ace.authorservices.services.service.impl.CrossRefServiceImpl;

/**
 * The Class CrossRefServiceBeanConfig.
 */
@Configuration
public class CrossRefServiceBeanConfig {
	
	/**
	 * Cross ref service.
	 *
	 * @return the cross ref service
	 */
	@Bean(name = "CrossRefService")
	public CrossRefService crossRefService() {
		return new CrossRefServiceImpl();
	}


}
