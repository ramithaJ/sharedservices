package com.wiley.gr.ace.authorservices.email.config;

import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import com.wiley.gr.ace.authorservices.email.service.MailSenderService;
import com.wiley.gr.ace.authorservices.email.service.impl.MailSenderServiceImpl;

@Configuration
public class EmailConfig {

	@Bean
	public JavaMailSenderImpl javaMailSenderImpl(){
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setHost("localhost");
		mailSender.setPort(2525);
		//Set gmail email id
		mailSender.setUsername("test.virtu.2015@gmail.com");
		//Set gmail email password
		mailSender.setPassword("abefcdgh");
		Properties prop = mailSender.getJavaMailProperties();
		prop.put("mail.transport.protocol", "smtp");
		prop.put("mail.smtp.auth", "true");
		prop.put("mail.smtp.starttls.enable", "true");
		prop.put("mail.debug", "true");
		return mailSender;
	}
	
	@Bean(name="MailSenderService")
	public MailSenderService mailSenderService() {
		return new MailSenderServiceImpl();
	}
}
