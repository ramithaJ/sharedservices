package com.wiley.gr.ace.authorservices.email.config;

import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import com.wiley.gr.ace.authorservices.email.service.MailSenderService;
import com.wiley.gr.ace.authorservices.email.service.impl.MailSenderServiceImpl;


/**
 * The Class EmailConfig.
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
		mailSender.setHost("localhost");
		mailSender.setPort(2525);
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
