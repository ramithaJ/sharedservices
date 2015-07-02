package com.wiley.gr.ace.authorservices.services.context;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.wiley.gr.ace.authorservices.services.service.CrossRefService;
import com.wiley.gr.ace.authorservices.services.service.CrossRefService1;
import com.wiley.gr.ace.authorservices.services.service.impl.CrossRefServiceImpl;
import com.wiley.gr.ace.authorservices.services.service.impl.CrossRefServiceImpl1;

@Configuration
public class CrossRefServiceBeanConfig {
	@Bean(name = "CrossRefService")
	public CrossRefService crossRefService() {
		return new CrossRefServiceImpl();
	}
	
	@Bean(name = "CrossRefService1")
	public CrossRefService1 crossRefService1() {
		return new CrossRefServiceImpl1();
	}


}
