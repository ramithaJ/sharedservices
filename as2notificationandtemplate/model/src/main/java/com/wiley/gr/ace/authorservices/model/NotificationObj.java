package com.wiley.gr.ace.authorservices.model;

import java.sql.Clob;
import java.util.Date;

public class NotificationObj {
	private String id;
	private TemplateObj template;
	private String appId;
	private String senderId;
	private String senderEmail;
	private Clob content;
	private String type;
	private Date sentOn;
	private Character unread;
	private NotificationRecipientsObj notificationRecipients;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public TemplateObj getTemplate() {
		return template;
	}
	public void setTemplate(TemplateObj template) {
		this.template = template;
	}
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getSenderId() {
		return senderId;
	}
	public void setSenderId(String senderId) {
		this.senderId = senderId;
	}
	public String getSenderEmail() {
		return senderEmail;
	}
	public void setSenderEmail(String senderEmail) {
		this.senderEmail = senderEmail;
	}
	public Clob getContent() {
		return content;
	}
	public void setContent(Clob content) {
		this.content = content;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Date getSentOn() {
		return sentOn;
	}
	public void setSentOn(Date sentOn) {
		this.sentOn = sentOn;
	}
	public Character getUnread() {
		return unread;
	}
	public void setUnread(Character unread) {
		this.unread = unread;
	}
	public NotificationRecipientsObj getNotificationRecipients() {
		return notificationRecipients;
	}
	public void setNotificationRecipients(
			NotificationRecipientsObj notificationRecipients) {
		this.notificationRecipients = notificationRecipients;
	}
}
