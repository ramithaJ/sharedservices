package com.wiley.gr.ace.authorservices.services.service.impl;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.springframework.beans.factory.annotation.Autowired;

import com.wiley.gr.ace.authorservices.email.service.MailSenderService;
import com.wiley.gr.ace.authorservices.services.service.MailSendingUtilityService;

public class MailSendingUtilityServiceImpl implements MailSendingUtilityService {

	@Autowired
	MailSenderService mailSenderService;
	
	@Override
	public void sendEmail(String from, String to, String subject, String body)
			throws AddressException, MessagingException {
		// TODO Auto-generated method stub

		mailSenderService.sendEmail(from, to, subject, body);
	}

}
