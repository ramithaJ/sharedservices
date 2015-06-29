package com.wiley.gr.ace.authorservices.email.service;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;


/**
 * The Interface MailSenderService.
 */
public interface MailSenderService {

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
	void sendEmail(String from, String to, String subject, String body) throws AddressException,MessagingException;
}
