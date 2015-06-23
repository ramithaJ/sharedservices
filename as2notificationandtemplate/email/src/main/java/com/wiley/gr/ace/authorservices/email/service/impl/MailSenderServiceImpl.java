package com.wiley.gr.ace.authorservices.email.service.impl;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import com.wiley.gr.ace.authorservices.email.service.MailSenderService;

public class MailSenderServiceImpl implements MailSenderService {

	@Autowired
	private JavaMailSenderImpl mailSender;
	
	@Override
	public void sendEmail(String from, String to, String subject, String body)
			throws AddressException,MessagingException {
		// TODO Auto-generated method stub
		
		 MimeMessage message = mailSender.createMimeMessage();
		 
		 MimeMessageHelper mailMsg = new MimeMessageHelper(message);
		 mailMsg.setFrom(from);
		 mailMsg.setTo(to);
		 mailMsg.setSubject(subject);
		 mailMsg.setText(body);
		 mailSender.send(message);
	}

}
