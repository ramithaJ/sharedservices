package com.wiley.gr.ace.authorservices.services.context;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.wiley.gr.ace.authorservices.services.service.CrossRefService;
import com.wiley.gr.ace.authorservices.services.service.impl.CrossRefServiceImpl;

@Configuration
public class CrossRefServiceBeanConfig {
	@Bean(name = "CrossRef")
	public CrossRefService crossRefService() {
		return new CrossRefServiceImpl();
	}

}
