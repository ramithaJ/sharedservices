package com.wiley.gr.ace.authorservices.email.service;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

public interface MailSenderService {

	void sendEmail(String from, String to, String subject, String body) throws AddressException,MessagingException;
}
