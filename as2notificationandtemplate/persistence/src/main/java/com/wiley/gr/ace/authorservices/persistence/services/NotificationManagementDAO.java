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
package com.wiley.gr.ace.authorservices.persistence.services;

import java.util.ArrayList;
import java.util.List;

import com.wiley.gr.ace.authorservices.persistence.entity.Notification;
import com.wiley.gr.ace.authorservices.persistence.entity.NotificationRecipients;
import com.wiley.gr.ace.authorservices.persistence.entity.Schedule;
import com.wiley.gr.ace.authorservices.persistence.entity.ScheduleTemplate;
import com.wiley.gr.ace.authorservices.persistence.entity.Template;

// TODO: Auto-generated Javadoc
/**
 * The Interface NotificationManagementDAO.
 * 
 * @author virtusa version 1.0
 */
public interface NotificationManagementDAO {

	/**
	 * Gets the schedule.
	 *
	 * @param applicationId
	 *            the application id
	 * @param scheduleId
	 *            the schedule id
	 * @return the schedule
	 * @throws Exception
	 *             the exception
	 */
	Schedule getSchedule(String applicationId, String scheduleId)
			throws Exception;

	/**
	 * Delete schedule.
	 *
	 * @param applicationId
	 *            the application id
	 * @param scheduleId
	 *            the schedule id
	 * @return true, if successful
	 * @throws Exception
	 *             the exception
	 */
	boolean deleteSchedule(String applicationId, String scheduleId)
			throws Exception;

	/**
	 * Template lookup.
	 *
	 * @param applicationId
	 *            the application id
	 * @param templateId
	 *            the template id
	 * @param type
	 *            the type
	 * @return the list
	 * @throws Exception
	 *             the exception
	 */
	List<Schedule> templateLookup(String applicationId, String templateId,
			String type) throws Exception;

	/**
	 * Gets the template.
	 *
	 * @param templateId
	 *            the template id
	 * @param applicationId
	 *            the application id
	 * @return the template
	 * @throws Exception
	 *             the exception
	 */
	Template getTemplate(String templateId, String applicationId)
			throws Exception;

	/**
	 * Save or update schedule.
	 *
	 * @param schedule
	 *            the schedule
	 * @return true, if successful
	 * @throws Exception
	 *             the exception
	 */
	boolean saveOrUpdateSchedule(Schedule schedule) throws Exception;

	/**
	 * Gets the schedule template entity.
	 *
	 * @param scheduleId
	 *            the schedule id
	 * @return the schedule template entity
	 * @throws Exception
	 *             the exception
	 */
	ScheduleTemplate getScheduleTemplateEntity(String scheduleId)
			throws Exception;

	/**
	 * Gets the notification.
	 *
	 * @param applicationId
	 *            the application id
	 * @param notificationId
	 *            the notification id
	 * @return the notification
	 * @throws Exception
	 *             the exception
	 */
	Notification getNotification(String applicationId, Long notificationId)
			throws Exception;

	/**
	 * Sets the notification flag.
	 *
	 * @param applicationId
	 *            the application id
	 * @param notificationId
	 *            the notification id
	 * @return true, if successful
	 * @throws Exception
	 *             the exception
	 */
	boolean setNotificationFlag(String applicationId, Long notificationId)
			throws Exception;

	/**
	 * Gets the notification list.
	 *
	 * @param applicationId
	 *            the application id
	 * @return the notification list
	 * @throws Exception
	 *             the exception
	 */
	List<Notification> getNotificationList(String applicationId)
			throws Exception;

	/**
	 * Gets the notification recipients.
	 *
	 * @param notificationId
	 *            the notification id
	 * @return the notification recipients
	 * @throws Exception
	 *             the exception
	 */
	ArrayList<NotificationRecipients> getNotificationRecipients(
			Long notificationId) throws Exception;


	/**
	 * Creates the notification history.
	 *
	 * @param notification the notification
	 * @return the long
	 * @throws Exception the exception
	 */
	Long createNotificationHistory(Notification notification)
			throws Exception;
}
