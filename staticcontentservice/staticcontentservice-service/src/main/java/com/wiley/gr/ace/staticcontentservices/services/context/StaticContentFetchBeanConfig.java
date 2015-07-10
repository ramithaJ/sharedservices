package com.wiley.gr.ace.staticcontentservices.services.context;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.wiley.gr.ace.staticcontentservices.services.service.StaticContentFetchService;
import com.wiley.gr.ace.staticcontentservices.services.service.impl.StaticContentFetchServiceImpl;


/**
 * The Class StaticContentFetchBeanConfig.
 */
@Configuration
public class StaticContentFetchBeanConfig {

	/**
	 * Static content fetch service.
	 *
	 * @return the static content fetch service
	 */
	@Bean(name = "StaticContentFetchService")
	public StaticContentFetchService staticContentFetchService() {
		return new StaticContentFetchServiceImpl();
	}
	
}
