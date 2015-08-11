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

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * The Class NotificationRecipientsObj.
 * 
 * @author virtusa version 1.0
 */
@JsonInclude(Include.NON_NULL)
public class NotificationRecipientsObj {

    /** The notification id. */
    private Integer notificationId;

    /** The notification. */
    private NotificationObj notification;

    /** The user id. */
    private String userId;

    /** The email. */
    private String email;
    
    /** The recipient type. */
    private String recipientType;

    /**
     * Gets the notification id.
     *
     * @return the notification id
     */
    public final Integer getNotificationId() {
        return notificationId;
    }

    /**
     * Sets the notification id.
     *
     * @param notificationId
     *            the new notification id
     */
    public final void setNotificationId(final Integer notificationId) {
        this.notificationId = notificationId;
    }

    /**
     * Gets the notification.
     *
     * @return the notification
     */
    public final NotificationObj getNotification() {
        return notification;
    }

    /**
     * Sets the notification.
     *
     * @param notification
     *            the new notification
     */
    public final void setNotification(final NotificationObj notification) {
        this.notification = notification;
    }

    /**
     * Gets the user id.
     *
     * @return the user id
     */
    public final String getUserId() {
        return userId;
    }

    /**
     * Sets the user id.
     *
     * @param userId
     *            the new user id
     */
    public final void setUserId(final String userId) {
        this.userId = userId;
    }

    /**
     * Gets the email.
     *
     * @return the email
     */
    public final String getEmail() {
        return email;
    }

    /**
     * Sets the email.
     *
     * @param email
     *            the new email
     */
    public final void setEmail(final String email) {
        this.email = email;
    }

	/**
	 * Gets the recipient type.
	 *
	 * @return the recipient type
	 */
	public final String getRecipientType() {
		return recipientType;
	}

	/**
	 * Sets the recipient type.
	 *
	 * @param recipientType the new recipient type
	 */
	public final void setRecipientType(final String recipientType) {
		this.recipientType = recipientType;
	}

}
