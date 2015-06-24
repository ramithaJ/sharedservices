package com.wiley.gr.ace.authorservices.model;



/**
 * The Class NotificationRecipientsObj.
 */
public class NotificationRecipientsObj {
	
	/** The notification id. */
	private String notificationId;
	
	/** The notification. */
	private NotificationObj notification;
	
	/** The user id. */
	private String userId;
	
	/** The email. */
	private String email;
	
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
	 * Gets the notification.
	 *
	 * @return the notification
	 */
	public NotificationObj getNotification() {
		return notification;
	}
	
	/**
	 * Sets the notification.
	 *
	 * @param notification the new notification
	 */
	public void setNotification(NotificationObj notification) {
		this.notification = notification;
	}
	
	/**
	 * Gets the user id.
	 *
	 * @return the user id
	 */
	public String getUserId() {
		return userId;
	}
	
	/**
	 * Sets the user id.
	 *
	 * @param userId the new user id
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	/**
	 * Gets the email.
	 *
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	
	/**
	 * Sets the email.
	 *
	 * @param email the new email
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	
}
