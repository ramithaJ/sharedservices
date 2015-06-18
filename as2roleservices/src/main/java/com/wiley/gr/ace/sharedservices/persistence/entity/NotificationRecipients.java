package com.wiley.gr.ace.sharedservices.persistence.entity;

// Generated May 29, 2015 5:33:29 PM by Hibernate Tools 3.4.0.CR1

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * NotificationRecipients generated by hbm2java
 */
@Entity
@Table(name = "NOTIFICATION_RECIPIENTS")
public class NotificationRecipients implements java.io.Serializable {

    private String notificationId;
    private String userId;
    private String email;

    public NotificationRecipients() {
    }

    public NotificationRecipients(String notificationId) {
        this.notificationId = notificationId;
    }

    public NotificationRecipients(String notificationId, String userId,
                                  String email) {
        this.notificationId = notificationId;
        this.userId = userId;
        this.email = email;
    }

    @Id
    @Column(name = "NOTIFICATION_ID", unique = true, nullable = false)
    public String getNotificationId() {
        return this.notificationId;
    }

    public void setNotificationId(String notificationId) {
        this.notificationId = notificationId;
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
