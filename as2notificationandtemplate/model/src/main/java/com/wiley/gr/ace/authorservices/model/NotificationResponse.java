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

// TODO: Auto-generated Javadoc
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

	/** The sent to. */
	private ArrayList<String> sentToList;

	/** The sent cc list. */
	private ArrayList<String> sentCCList;

	/** The sent bcc list. */
	private ArrayList<String> sentBCCList;

	public final String getNotificationId() {
		return notificationId;
	}

	public final void setNotificationId(final String notificationId) {
		this.notificationId = notificationId;
	}

	public final String getTemplateId() {
		return templateId;
	}

	public final void setTemplateId(final String templateId) {
		this.templateId = templateId;
	}

	public final ArrayList<String> getSentToList() {
		return sentToList;
	}

	public final void setSentToList(final ArrayList<String> sentToList) {
		this.sentToList = sentToList;
	}

	public final ArrayList<String> getSentCCList() {
		return sentCCList;
	}

	public final void setSentCCList(final ArrayList<String> sentCCList) {
		this.sentCCList = sentCCList;
	}

	public final ArrayList<String> getSentBCCList() {
		return sentBCCList;
	}

	public final void setSentBCCList(final ArrayList<String> sentBCCList) {
		this.sentBCCList = sentBCCList;
	}

}
