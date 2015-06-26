package com.wiley.gr.ace.authorservices.persistence.entity;

// Generated Jun 26, 2015 10:24:59 AM by Hibernate Tools 4.0.0

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.Parameter;

/**
 * NotificationRecipients generated by hbm2java
 */
@Entity
@Table(name = "NOTIFICATION_RECIPIENTS")
public class NotificationRecipients implements java.io.Serializable {

	private Integer notificationId;
	private Notification notification;
	private String userId;
	private String email;

	public NotificationRecipients() {
	}

	public NotificationRecipients(Notification notification) {
		this.notification = notification;
	}

	public NotificationRecipients(Notification notification, String userId,
			String email) {
		this.notification = notification;
		this.userId = userId;
		this.email = email;
	}

	@GenericGenerator(name = "generator", strategy = "foreign", parameters = @Parameter(name = "property", value = "notification"))
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "NOTIFICATION_ID", unique = true, nullable = false, precision = 22, scale = 0)
	public Integer getNotificationId() {
		return this.notificationId;
	}

	public void setNotificationId(Integer notificationId) {
		this.notificationId = notificationId;
	}

	@OneToOne(fetch = FetchType.LAZY)
	@PrimaryKeyJoinColumn
	@OnDelete(action = OnDeleteAction.CASCADE)
	public Notification getNotification() {
		return this.notification;
	}

	public void setNotification(Notification notification) {
		this.notification = notification;
	}

	@Column(name = "USER_ID")
	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Column(name = "EMAIL")
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
