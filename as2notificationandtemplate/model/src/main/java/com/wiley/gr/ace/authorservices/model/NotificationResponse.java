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

import java.util.ArrayList;

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


    /** The template id. */
    private String templateId;


    /** The sent to list. */
    private ArrayList<String> sentToList;


    /** The sent cc list. */
    private ArrayList<String> sentCCList;


    /** The sent bcc list. */
    private ArrayList<String> sentBCCList;

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
     * @param notificationId the new notification id
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
     * @param templateId the new template id
     */
    public final void setTemplateId(final String templateId) {
        this.templateId = templateId;
    }

    /**
     * Gets the sent to list.
     *
     * @return the sent to list
     */
    public final ArrayList<String> getSentToList() {
        return sentToList;
    }

    /**
     * Sets the sent to list.
     *
     * @param sentToList the new sent to list
     */
    public final void setSentToList(final ArrayList<String> sentToList) {
        this.sentToList = sentToList;
    }

    /**
     * Gets the sent cc list.
     *
     * @return the sent cc list
     */
    public final ArrayList<String> getSentCCList() {
        return sentCCList;
    }

    /**
     * Sets the sent cc list.
     *
     * @param sentCCList the new sent cc list
     */
    public final void setSentCCList(final ArrayList<String> sentCCList) {
        this.sentCCList = sentCCList;
    }

    /**
     * Gets the sent bcc list.
     *
     * @return the sent bcc list
     */
    public final ArrayList<String> getSentBCCList() {
        return sentBCCList;
    }

    /**
     * Sets the sent bcc list.
     *
     * @param sentBCCList the new sent bcc list
     */
    public final void setSentBCCList(final ArrayList<String> sentBCCList) {
        this.sentBCCList = sentBCCList;
    }

}
