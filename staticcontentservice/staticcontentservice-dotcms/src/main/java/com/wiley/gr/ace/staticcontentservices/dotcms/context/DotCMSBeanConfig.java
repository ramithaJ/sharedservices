package com.wiley.gr.ace.staticcontentservices.dotcms.context;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import com.wiley.gr.ace.staticcontentservices.dotcms.service.DotCMSDataService;

import com.wiley.gr.ace.staticcontentservices.dotcms.service.impl.DotCMSDataServiceImpl;


/**
 * The Class DotCMSBeanConfig.
 */
@Configuration
public class DotCMSBeanConfig {

	/**
	 * Dot cms data service.
	 *
	 * @return the dot cms data service
	 */
	@Bean(name = "DotCMSDataService")
	public DotCMSDataService dotCMSDataService() {
		return new DotCMSDataServiceImpl();
	}

}
