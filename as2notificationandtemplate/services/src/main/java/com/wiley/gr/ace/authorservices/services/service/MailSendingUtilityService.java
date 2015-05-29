package com.wiley.gr.ace.authorservices.services.service;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

public interface MailSendingUtilityService {

	void sendEmail(String from, String to, String Subject, String body) throws AddressException,MessagingException;
}
