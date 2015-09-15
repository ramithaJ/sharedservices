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
package com.wiley.gr.ace.authorservices.email.service.impl;

import java.io.File;
import java.util.ArrayList;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.util.StringUtils;

import com.wiley.gr.ace.authorservices.email.service.MailSenderService;

/**
 * The Class MailSenderServiceImpl.
 * 
 * @author virtusa version 1.0
 */
public class MailSenderServiceImpl implements MailSenderService {

    /** The mail sender. */
    @Autowired
    private JavaMailSenderImpl mailSender;

    /**
     * Send email.
     *
     * @param from
     *            the from
     * @param to
     *            the to
     * @param subject
     *            the subject
     * @param body
     *            the body
     * @throws MessagingException
     *             the messaging exception
     */
    @Override
    public final void sendEmail(final String from, final ArrayList<String> to,
            final ArrayList<String> cc, final ArrayList<String> bcc,
            final String subject, final String body, File[] attachments)
            throws MessagingException {

        MimeMessage message = mailSender.createMimeMessage();

        MimeMessageHelper mailMsg = new MimeMessageHelper(message);
        mailMsg.setFrom(from);
        mailMsg.setTo(to.toArray(new String[to.size()]));
        if (!StringUtils.isEmpty(cc)) {
            mailMsg.setCc(cc.toArray(new String[to.size()]));
        }
        if (!StringUtils.isEmpty(bcc)) {
            mailMsg.setBcc(bcc.toArray(new String[to.size()]));
        }
        if (!StringUtils.isEmpty(attachments)) {
            for (File file : attachments) {
                mailMsg.addAttachment(file.getName(),file);
            }
        }
        mailMsg.setSubject(subject);
        mailMsg.setText(body, true);
        mailSender.send(message);
    }

}
