package com.wiley.gr.ace.authorservices.model;


public class NotificationRecipientsObj {
	private String notificationId;
	private NotificationObj notification;
	private String userId;
	private String email;
	public String getNotificationId() {
		return notificationId;
	}
	public void setNotificationId(String notificationId) {
		this.notificationId = notificationId;
	}
	public NotificationObj getNotification() {
		return notification;
	}
	public void setNotification(NotificationObj notification) {
		this.notification = notification;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
}