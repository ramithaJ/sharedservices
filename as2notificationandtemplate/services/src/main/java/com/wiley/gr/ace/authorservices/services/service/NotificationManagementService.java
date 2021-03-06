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
package com.wiley.gr.ace.authorservices.services.service;

import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import com.wiley.gr.ace.authorservices.model.NotificationDetails;
import com.wiley.gr.ace.authorservices.model.NotificationObj;
import com.wiley.gr.ace.authorservices.model.NotificationResponse;
import com.wiley.gr.ace.authorservices.model.ScheduleObj;
import com.wiley.gr.ace.authorservices.model.TemplateObj;

/**
 * The Interface NotificationManagementService.
 * 
 * @author virtusa version 1.0
 */
public interface NotificationManagementService {

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
	ScheduleObj getSchedule(String applicationId, String scheduleId)
			throws Exception;

	/**
	 * Insert schedule.
	 *
	 * @param schedule
	 *            the schedule
	 * @return true, if successful
	 * @throws Exception
	 *             the exception
	 */
	boolean insertSchedule(ScheduleObj schedule) throws Exception;

	/**
	 * Update schedule.
	 *
	 * @param applicationId
	 *            the application id
	 * @param scheduleId
	 *            the schedule id
	 * @param scheduleObj
	 *            the schedule obj
	 * @return true, if successful
	 * @throws Exception
	 *             the exception
	 */
	boolean updateSchedule(String applicationId, String scheduleId,
			ScheduleObj scheduleObj) throws Exception;

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
	List<ScheduleObj> templateLookup(String applicationId, String templateId,
			String type) throws Exception;

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
	NotificationObj getNotification(String applicationId, Long notificationId)
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
	 * Gets the notification history.
	 *
	 * @param applicationId
	 *            the application id
	 * @param from
	 *            the from
	 * @param to
	 *            the to
	 * @param type
	 *            the type
	 * @param offset
	 *            the offset
	 * @param limit
	 *            the limit
	 * @param unreadFlag
	 *            the unread flag
	 * @param tagL1
	 *            the tag l1
	 * @param tagL2
	 *            the tag l2
	 * @param itemKey
	 *            the item key
	 * @param itemValue
	 *            the item value
	 * @return the notification history
	 * @throws Exception
	 *             the exception
	 */
	List<NotificationObj> getNotificationHistory(String applicationId,
			String from, String to, String type, String offset, String limit,
			String unreadFlag, String tagL1, String tagL2, String itemKey,
			String itemValue) throws Exception;

	/**
	 * Send onscreen notification.
	 *
	 * @param applicationId
	 *            the application id
	 * @param templateId
	 *            the template id
	 * @param notificationDetails
	 *            the notification details
	 * @throws Exception
	 *             the exception
	 */
	void sendOnscreenNotification(String applicationId, String templateId,
			NotificationDetails notificationDetails) throws Exception;

	/**
	 * Send email notification.
	 *
	 * @param applicationId
	 *            the application id
	 * @param templateId
	 *            the template id
	 * @param notificationDetails
	 *            the notification details
	 * @param templateObj
	 *            the template obj
	 * @return the notification response
	 * @throws AddressException
	 *             the address exception
	 * @throws MessagingException
	 *             the messaging exception
	 * @throws Exception
	 *             the exception
	 */
	NotificationResponse sendEmailNotification(String applicationId,
			String templateId, NotificationDetails notificationDetails,
			TemplateObj templateObj) throws AddressException,
			MessagingException, Exception;

	/**
	 * Resend email notification.
	 *
	 * @param applicationId
	 *            the application id
	 * @param notificationId
	 *            the notification id
	 * @return the notification response
	 * @throws AddressException
	 *             the address exception
	 * @throws MessagingException
	 *             the messaging exception
	 * @throws Exception
	 *             the exception
	 */
	NotificationResponse resendEmailNotification(String applicationId,
			Long notificationId) throws AddressException,
			MessagingException, Exception;



}
