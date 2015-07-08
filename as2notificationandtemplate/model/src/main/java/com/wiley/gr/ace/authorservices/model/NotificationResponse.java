package com.wiley.gr.ace.authorservices.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * The Class NotificationResponse.
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
