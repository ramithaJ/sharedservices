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
package com.wiley.gr.ace.authorservices.email.config;

import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import com.wiley.gr.ace.authorservices.email.service.MailSenderService;
import com.wiley.gr.ace.authorservices.email.service.impl.MailSenderServiceImpl;


/**
 * The Class EmailConfig.
 * 
 * @author virtusa version 1.0
 */
@Configuration
public class EmailConfig {

	/**
	 * Java mail sender impl.
	 *
	 * @return the java mail sender impl
	 */
	@Bean
	public JavaMailSenderImpl javaMailSenderImpl(){
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setHost("smtpgate.wiley.com");
		mailSender.setPort(25);
		Properties prop = mailSender.getJavaMailProperties();
		prop.put("mail.transport.protocol", "smtp");
		prop.put("mail.smtp.auth", "false");
		prop.put("mail.smtp.starttls.enable", "true");
		prop.put("mail.debug", "true");
		return mailSender;
	}
	
	/**
	 * Mail sender service.
	 *
	 * @return the mail sender service
	 */
	@Bean(name="MailSenderService")
	public MailSenderService mailSenderService() {
		return new MailSenderServiceImpl();
	}
}
