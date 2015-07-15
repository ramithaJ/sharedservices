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
 * The Class NotificationResponse.
 * 
 * @author virtusa version 1.0
 */
@JsonInclude(Include.NON_NULL)
public class NotificationResponse {

    /** The notification id. */
   private String notificationId;

    /**
     * Gets the notification id.
     *
     * @return the notification id
     */
    public final String getNotificationId() {
        return notificationId;
    }

    /**
     * Sets the notification id.
     *
     * @param notificationId
     *            the new notification id
     */
    public final void setNotificationId(final String notificationId) {
        this.notificationId = notificationId;
    }

    /**
     * Gets the template id.
     *
     * @return the template id
     */
    public final String getTemplateId() {
        return templateId;
    }

    /**
     * Sets the template id.
     *
     * @param templateId
     *            the new template id
     */
    public final void setTemplateID(final String templateId) {
        this.templateId = templateId;
    }

    /**
     * Gets the sent to.
     *
     * @return the sent to
     */
    public final String getSentTo() {
        return sentTo;
    }

    /**
     * Sets the sent to.
     *
     * @param sentTo
     *            the new sent to
     */
    public final void setSentTo(final String sentTo) {
        this.sentTo = sentTo;
    }

    /** The template id. */
   private String templateId;

    /** The sent to. */
   private String sentTo;
}
