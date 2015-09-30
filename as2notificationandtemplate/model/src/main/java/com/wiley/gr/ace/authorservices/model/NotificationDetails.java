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
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

// TODO: Auto-generated Javadoc
/**
 * The Class NotificationDetails.
 * 
 * @author virtusa version 1.0
 */
@JsonInclude(Include.NON_NULL)
public class NotificationDetails {

    /** The from. */
    private String from;

    /** The to. */
    private ArrayList<String> to;

    /** The cc list. */
    private ArrayList<String> ccList;

    /** The bcc list. */
    private ArrayList<String> bccList;

    /** The template details. */
    private TemplateDetails templateDetails;

    /** The attachments. */
    private Map<String,byte[]> attachments;

    /**
     * Gets the attachments.
     *
     * @return the attachments
     */
    public Map<String, byte[]> getAttachments() {
        return attachments;
    }

    /**
     * Sets the attachments.
     *
     * @param attachments the attachments
     */
    public void setAttachments(Map<String, byte[]> attachments) {
        this.attachments = attachments;
    }

    /** The notification data. */
    private NotificationDataObj notificationData;

    /**
     * Gets the notification data.
     *
     * @return the notification data
     */
    public final NotificationDataObj getNotificationData() {
        return notificationData;
    }

    /**
     * Sets the notification data.
     *
     * @param notificationData
     *            the new notification data
     */
    public final void setNotificationData(final NotificationDataObj notificationData) {
        this.notificationData = notificationData;
    }

    /**
     * Gets the from.
     *
     * @return the from
     */
    public final String getFrom() {
        return from;
    }

    /**
     * Sets the from.
     *
     * @param from
     *            the new from
     */
    public final void setFrom(final String from) {
        this.from = from;
    }

    /**
     * Gets the to.
     *
     * @return the to
     */
    public final ArrayList<String> getTo() {
        return to;
    }

    /**
     * Gets the cc list.
     *
     * @return the cc list
     */
    public final ArrayList<String> getCcList() {
        return ccList;
    }

    /**
     * Sets the cc list.
     *
     * @param ccList
     *            the new cc list
     */
    public final void setCcList(final ArrayList<String> ccList) {
        this.ccList = ccList;
    }

    /**
     * Gets the bcc list.
     *
     * @return the bcc list
     */
    public final ArrayList<String> getBccList() {
        return bccList;
    }

    /**
     * Sets the bcc list.
     *
     * @param bccList
     *            the new bcc list
     */
    public final void setBccList(final ArrayList<String> bccList) {
        this.bccList = bccList;
    }

    /**
     * Sets the to.
     *
     * @param to
     *            the new to
     */
    public final void setTo(final ArrayList<String> to) {
        this.to = to;
    }

    /**
     * Gets the template details.
     *
     * @return the template details
     */
    public final TemplateDetails getTemplateDetails() {
        return templateDetails;
    }

    /**
     * Sets the template details.
     *
     * @param templateDetails
     *            the new template details
     */
    public final void setTemplateDetails(final TemplateDetails templateDetails) {
        this.templateDetails = templateDetails;
    }

}
