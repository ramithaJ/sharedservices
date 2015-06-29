package com.wiley.gr.ace.authorservices.email.service.impl;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import com.wiley.gr.ace.authorservices.email.service.MailSenderService;


/**
 * The Class MailSenderServiceImpl.
 */
public class MailSenderServiceImpl implements MailSenderService {

	/** The mail sender. */
	@Autowired
	private JavaMailSenderImpl mailSender;
	
	/**
	 * Send email.
	 *
	 * @param from the from
	 * @param to the to
	 * @param subject the subject
	 * @param body the body
	 * @throws AddressException the address exception
	 * @throws MessagingException the messaging exception
	 */
	@Override
	public void sendEmail(String from, String to, String subject, String body)
			throws AddressException,MessagingException {
		
		 MimeMessage message = mailSender.createMimeMessage();
		 
		 MimeMessageHelper mailMsg = new MimeMessageHelper(message);
		 mailMsg.setFrom(from);
		 mailMsg.setTo(to);
		 mailMsg.setSubject(subject);
		 mailMsg.setText(body,"text/html; charset=utf-8");
		 mailSender.send(message);
	}

}
