package com.wiley.gr.ace.staticcontentservices.services.context;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.wiley.gr.ace.staticcontentservices.services.service.StaticContentFetchService;
import com.wiley.gr.ace.staticcontentservices.services.service.impl.StaticContentFetchServiceImpl;

@Configuration
public class StaticContentFetchBeanConfig {

	@Bean(name = "StaticContentFetchService")
	public StaticContentFetchService staticContentFetchService() {
		return new StaticContentFetchServiceImpl();
	}
}
