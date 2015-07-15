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
package com.wiley.gr.ace.authorservices.model;

import java.sql.Clob;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * The Class NotificationObj.
 * 
 * @author virtusa version 1.0
 */
@JsonInclude(Include.NON_NULL)
public class NotificationObj {

    /** The id. */
    private Integer id;

    /** The template. */
    private TemplateObj template;

    /** The app id. */
    private String appId;

    /** The sender id. */
    private String senderId;

    /** The sender email. */
    private String senderEmail;

    /** The content. */
    private Clob content;

    /** The type. */
    private String type;

    /** The sent on. */
    private String sentOn;

    /** The unread. */
    private Character unread;

    /** The notification recipients. */
    private NotificationRecipientsObj notificationRecipients;

    /**
     * Gets the id.
     *
     * @return the id
     */
    public final Integer getId() {
        return id;
    }

    /**
     * Sets the id.
     *
     * @param id
     *            the new id
     */
    public final void setId(final Integer id) {
        this.id = id;
    }

    /**
     * Gets the template.
     *
     * @return the template
     */
    public final TemplateObj getTemplate() {
        return template;
    }

    /**
     * Sets the template.
     *
     * @param template
     *            the new template
     */
    public final void setTemplate(final TemplateObj template) {
        this.template = template;
    }

    /**
     * Gets the app id.
     *
     * @return the app id
     */
    public final String getAppId() {
        return appId;
    }

    /**
     * Sets the app id.
     *
     * @param appId
     *            the new app id
     */
    public final void setAppId(final String appId) {
        this.appId = appId;
    }

    /**
     * Gets the sender id.
     *
     * @return the sender id
     */
    public final String getSenderId() {
        return senderId;
    }

    /**
     * Sets the sender id.
     *
     * @param senderId
     *            the new sender id
     */
    public final void setSenderId(final String senderId) {
        this.senderId = senderId;
    }

    /**
     * Gets the sender email.
     *
     * @return the sender email
     */
    public final String getSenderEmail() {
        return senderEmail;
    }

    /**
     * Sets the sender email.
     *
     * @param senderEmail
     *            the new sender email
     */
    public final void setSenderEmail(final String senderEmail) {
        this.senderEmail = senderEmail;
    }

    /**
     * Gets the content.
     *
     * @return the content
     */
    public final Clob getContent() {
        return content;
    }

    /**
     * Sets the content.
     *
     * @param content
     *            the new content
     */
    public final void setContent(final Clob content) {
        this.content = content;
    }

    /**
     * Gets the type.
     *
     * @return the type
     */
    public final String getType() {
        return type;
    }

    /**
     * Sets the type.
     *
     * @param type
     *            the new type
     */
    public final void setType(final String type) {
        this.type = type;
    }

    /**
     * Gets the sent on.
     *
     * @return the sent on
     */
    public final String getSentOn() {
        return sentOn;
    }

    /**
     * Sets the sent on.
     *
     * @param sentOn
     *            the new sent on
     */
    public final void setSentOn(final String sentOn) {
        this.sentOn = sentOn;
    }

    /**
     * Gets the unread.
     *
     * @return the unread
     */
    public final Character getUnread() {
        return unread;
    }

    /**
     * Sets the unread.
     *
     * @param unread
     *            the new unread
     */
    public final void setUnread(final Character unread) {
        this.unread = unread;
    }

    /**
     * Gets the notification recipients.
     *
     * @return the notification recipients
     */
    public final NotificationRecipientsObj getNotificationRecipients() {
        return notificationRecipients;
    }

    /**
     * Sets the notification recipients.
     *
     * @param notificationRecipients
     *            the new notification recipients
     */
    public final void setNotificationRecipients(
            final NotificationRecipientsObj notificationRecipients) {
        this.notificationRecipients = notificationRecipients;
    }
}
