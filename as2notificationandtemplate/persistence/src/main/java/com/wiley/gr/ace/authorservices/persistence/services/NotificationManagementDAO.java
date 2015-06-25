
package com.wiley.gr.ace.authorservices.persistence.services;

import java.util.List;

import com.wiley.gr.ace.authorservices.persistence.entity.Notification;
import com.wiley.gr.ace.authorservices.persistence.entity.NotificationRecipients;
import com.wiley.gr.ace.authorservices.persistence.entity.Schedule;
import com.wiley.gr.ace.authorservices.persistence.entity.ScheduleTemplate;
import com.wiley.gr.ace.authorservices.persistence.entity.Template;


/**
 * The Interface NotificationManagementDAO.
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
	Template getTemplate(String templateId, String applicationId) throws Exception;

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
	 * @param scheduleId the schedule id
	 * @return the schedule template entity
	 * @throws Exception the exception
	 */
	ScheduleTemplate getScheduleTemplateEntity(String scheduleId) throws Exception;

	/**
	 * Gets the notification.
	 *
	 * @param applicationId the application id
	 * @param notificationId            the notification id
	 * @return the notification
	 * @throws Exception             the exception
	 */
	Notification getNotification(String applicationId, String notificationId)
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
	boolean setNotificationFlag(String applicationId, String notificationId)
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
	NotificationRecipients getNotificationRecipients(String notificationId)
			throws Exception;

	/**
	 * Creates the notification history.
	 *
	 * @param applicationId
	 *            the application id
	 * @param templateId
	 *            the template id
	 * @param senderEmail
	 *            the sender email
	 * @param recipientEmail
	 *            the recipient email
	 * @param content
	 *            the content
	 * @param type
	 *            the type
	 * @return true, if successful
	 * @throws Exception
	 *             the exception
	 */
	boolean createNotificationHistory(String applicationId, String templateId,
			String senderEmail, String recipientEmail, String content,
			String type) throws Exception;
}
