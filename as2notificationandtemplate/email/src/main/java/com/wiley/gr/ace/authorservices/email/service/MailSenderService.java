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
package com.wiley.gr.ace.authorservices.email.service;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;


/**
 * The Interface MailSenderService.
 * 
 * @author virtusa version 1.0
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
