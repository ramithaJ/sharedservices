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
	NotificationObj getNotification(String applicationId, Integer notificationId)
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
	boolean setNotificationFlag(String applicationId, Integer notificationId)
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
	 * @return the notification history
	 * @throws Exception
	 *             the exception
	 */
	List<NotificationObj> getNotificationHistory(String applicationId,
			String from, String to, String type, String offset, String limit,
			String unreadFlag) throws Exception;

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
	 * @throws AddressException
	 *             the address exception
	 * @throws MessagingException
	 *             the messaging exception
	 * @throws Exception
	 *             the exception
	 */
	NotificationResponse  sendEmailNotification(String applicationId, String templateId,
			NotificationDetails notificationDetails, TemplateObj templateObj)
			throws AddressException, MessagingException, Exception;

	/**
	 * Resend email notification.
	 *
	 * @param applicationId
	 *            the application id
	 * @param notificationId
	 *            the notification id
	 * @throws AddressException
	 *             the address exception
	 * @throws MessagingException
	 *             the messaging exception
	 * @throws Exception
	 *             the exception
	 */
	NotificationResponse resendEmailNotification(String applicationId, Integer notificationId)
			throws AddressException, MessagingException, Exception;

}
