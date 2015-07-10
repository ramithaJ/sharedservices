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
	String notificationId;
	
	/**
	 * Gets the notification id.
	 *
	 * @return the notification id
	 */
	public String getNotificationId() {
		return notificationId;
	}
	
	/**
	 * Sets the notification id.
	 *
	 * @param notificationId the new notification id
	 */
	public void setNotificationId(String notificationId) {
		this.notificationId = notificationId;
	}
	
	/**
	 * Gets the template id.
	 *
	 * @return the template id
	 */
	public String getTemplateId() {
		return templateId;
	}
	
	/**
	 * Sets the template id.
	 *
	 * @param templateId the new template id
	 */
	public void setTemplateID(String templateId) {
		this.templateId = templateId;
	}
	
	/**
	 * Gets the sent to.
	 *
	 * @return the sent to
	 */
	public String getSentTo() {
		return sentTo;
	}
	
	/**
	 * Sets the sent to.
	 *
	 * @param sentTo the new sent to
	 */
	public void setSentTo(String sentTo) {
		this.sentTo = sentTo;
	}
	
	/** The template id. */
	String templateId;
	
	/** The sent to. */
	String sentTo;
}
