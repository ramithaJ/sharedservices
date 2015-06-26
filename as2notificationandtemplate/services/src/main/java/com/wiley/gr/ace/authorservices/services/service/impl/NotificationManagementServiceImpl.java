package com.wiley.gr.ace.authorservices.services.service.impl;

import java.io.BufferedReader;
import java.sql.Clob;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.wiley.gr.ace.authorservices.email.service.MailSenderService;
import com.wiley.gr.ace.authorservices.model.NotificationDetails;
import com.wiley.gr.ace.authorservices.model.NotificationObj;
import com.wiley.gr.ace.authorservices.model.NotificationRecipientsObj;
import com.wiley.gr.ace.authorservices.model.ScheduleObj;
import com.wiley.gr.ace.authorservices.model.ScheduleTemplateObj;
import com.wiley.gr.ace.authorservices.model.TemplateObj;
import com.wiley.gr.ace.authorservices.persistence.entity.Notification;
import com.wiley.gr.ace.authorservices.persistence.entity.NotificationRecipients;
import com.wiley.gr.ace.authorservices.persistence.entity.Schedule;
import com.wiley.gr.ace.authorservices.persistence.entity.ScheduleTemplate;
import com.wiley.gr.ace.authorservices.persistence.entity.Template;
import com.wiley.gr.ace.authorservices.persistence.services.NotificationManagementDAO;
import com.wiley.gr.ace.authorservices.services.service.NotificationManagementService;
import com.wiley.gr.ace.authorservices.services.service.TemplateManagementService;

/**
 * The Class NotificationManagementServiceImpl.
 */
public class NotificationManagementServiceImpl implements
		NotificationManagementService {

	/** The notification management dao. */
	@Autowired(required = true)
	NotificationManagementDAO notificationManagementDAO;

	/** The template management service. */
	@Autowired(required = true)
	TemplateManagementService templateManagementService;

	/** The mail sender service. */
	@Autowired(required = true)
	MailSenderService mailSenderService;

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
	@Override
	public ScheduleObj getSchedule(String applicationId, String scheduleId)
			throws Exception {
		if (!StringUtils.isEmpty(applicationId)
				&& !StringUtils.isEmpty(scheduleId)) {
			return getScheduleVO(notificationManagementDAO.getSchedule(
					applicationId, scheduleId));
		}
		return null;
	}

	/**
	 * Insert schedule.
	 *
	 * @param schedule
	 *            the schedule
	 * @return true, if successful
	 * @throws Exception
	 *             the exception
	 */
	@Override
	public boolean insertSchedule(ScheduleObj schedule) throws Exception {
		if (!StringUtils.isEmpty(schedule)) {
			Schedule scheduleEntity = new Schedule();
			if (!StringUtils.isEmpty(schedule.getAppId()))
				scheduleEntity.setAppId(schedule.getAppId());
			if (!StringUtils.isEmpty(schedule.getCreatedBy()))
				scheduleEntity.setCreatedBy(schedule.getCreatedBy());
			if (!StringUtils.isEmpty(schedule.getCreatedOn()))
				scheduleEntity.setCreatedOn(getDate(schedule.getCreatedOn()));
			if (!StringUtils.isEmpty(schedule.getDescription()))
				scheduleEntity.setDescription(schedule.getDescription());
			if (!StringUtils.isEmpty(schedule.getId()))
				scheduleEntity.setId(schedule.getId());
			if (!StringUtils.isEmpty(schedule.getLastModifiedOn()))
				scheduleEntity.setLastModifiedOn(getDate(schedule
						.getLastModifiedOn()));
			if (!StringUtils.isEmpty(schedule.getAppId()))
				scheduleEntity.setModifiedBy(schedule.getModifiedBy());
			if (!StringUtils.isEmpty(schedule.getScheduleTemplate())) {
				ScheduleTemplate scheduleTemplate = getScheduleTemplateEntity(
						schedule.getScheduleTemplate(), schedule.getId(),
						schedule.getAppId());
						scheduleTemplate.setSchedule(scheduleEntity);
				scheduleEntity.setScheduleTemplate(scheduleTemplate);
			}
			return notificationManagementDAO
					.saveOrUpdateSchedule(scheduleEntity);
		} else
			return false;
	}

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
	@Override
	public boolean updateSchedule(String applicationId, String scheduleId,
			ScheduleObj scheduleObj) throws Exception {
		Schedule scheduleEntity = null;
		ScheduleTemplate scheduleTemplate = null;
		scheduleEntity = notificationManagementDAO.getSchedule(applicationId,
				scheduleId);
		if (!StringUtils.isEmpty(scheduleObj)) {
			if (!StringUtils.isEmpty(scheduleObj.getAppId()))
				scheduleEntity.setAppId(scheduleObj.getAppId());
			if (!StringUtils.isEmpty(scheduleObj.getScheduleTemplate())) {
				scheduleTemplate = getScheduleTemplateEntity(
						scheduleObj.getScheduleTemplate(), scheduleId,applicationId);
				if (!StringUtils.isEmpty(scheduleTemplate)) {
					scheduleTemplate.setSchedule(scheduleEntity);
					scheduleEntity.setScheduleTemplate(scheduleTemplate);
				}
			}
			if (!StringUtils.isEmpty(scheduleObj.getDescription()))
				scheduleEntity.setDescription(scheduleObj.getDescription());
		}

		return notificationManagementDAO.saveOrUpdateSchedule(scheduleEntity);

	}

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
	@Override
	public boolean deleteSchedule(String applicationId, String scheduleId)
			throws Exception {
		if (!StringUtils.isEmpty(applicationId)
				&& !StringUtils.isEmpty(scheduleId))
			return notificationManagementDAO.deleteSchedule(applicationId,
					scheduleId);
		else
			return false;
	}

	/**
	 * Gets the schedule template entity.
	 *
	 * @param scheduleTemplateObj
	 *            the schedule template obj
	 * @param scheduleId
	 *            the schedule id
	 * @param applicationId
	 *            the application id
	 * @return the schedule template entity
	 * @throws Exception
	 *             the exception
	 */
	private ScheduleTemplate getScheduleTemplateEntity (
			ScheduleTemplateObj scheduleTemplateObj, String scheduleId,
			String applcationId) throws Exception {
		ScheduleTemplate scheduleTemplate = notificationManagementDAO.getScheduleTemplateEntity(scheduleId);
		if(StringUtils.isEmpty(scheduleTemplate)){
			scheduleTemplate = new ScheduleTemplate();
			scheduleTemplate.setScheduleId(scheduleId);
		}
		if (!StringUtils.isEmpty(scheduleTemplateObj.getOnscreen())) {
			Template temTemplate = notificationManagementDAO.getTemplate(
					scheduleTemplateObj.getOnscreen(), applcationId);
			scheduleTemplate.setTemplateByOnscreenTmpl(temTemplate);
		}

		if (!StringUtils.isEmpty(scheduleTemplateObj.getEmail())) {
			Template temTemplate = notificationManagementDAO.getTemplate(
					scheduleTemplateObj.getEmail(), applcationId);
			scheduleTemplate.setTemplateByEmailTmpl(temTemplate);
		}
		if (!StringUtils.isEmpty(scheduleTemplateObj.getDelay())) {
			scheduleTemplate.setDelay(scheduleTemplateObj.getDelay());
		}
		return scheduleTemplate;
	}

	/**
	 * Gets the schedule vo.
	 *
	 * @param scheduleEntity
	 *            the schedule entity
	 * @return the schedule vo
	 * @throws Exception
	 *             the exception
	 */
	private ScheduleObj getScheduleVO(Schedule scheduleEntity) throws Exception {
		ScheduleObj schedule = new ScheduleObj();
		if (!StringUtils.isEmpty(scheduleEntity.getId()))
			schedule.setId(scheduleEntity.getId());
		if (!StringUtils.isEmpty(scheduleEntity.getAppId()))
			schedule.setAppId(scheduleEntity.getAppId());
		if (!StringUtils.isEmpty(scheduleEntity.getDescription()))
			schedule.setDescription(scheduleEntity.getDescription());
		if (!StringUtils.isEmpty(scheduleEntity.getCreatedBy()))
			schedule.setCreatedBy(scheduleEntity.getCreatedBy());
		if (!StringUtils.isEmpty(scheduleEntity.getModifiedBy()))
			schedule.setModifiedBy(scheduleEntity.getModifiedBy());
		if (!StringUtils.isEmpty(scheduleEntity.getCreatedOn()))
			schedule.setCreatedOn(scheduleEntity.getCreatedOn().toString());
		if (!StringUtils.isEmpty(scheduleEntity.getLastModifiedOn()))
			schedule.setLastModifiedOn(scheduleEntity.getLastModifiedOn()
					.toString());
		if (!StringUtils.isEmpty(scheduleEntity.getScheduleTemplate()))
			schedule.setScheduleTemplate(getScheduleTemplateVO(scheduleEntity
					.getScheduleTemplate()));
		return schedule;
	}

	/**
	 * Gets the schedule template vo.
	 *
	 * @param scheduleTemplateEntity
	 *            the schedule template entity
	 * @return the schedule template vo
	 * @throws Exception
	 *             the exception
	 */
	private ScheduleTemplateObj getScheduleTemplateVO(
			ScheduleTemplate scheduleTemplateEntity) throws Exception {
		ScheduleTemplateObj scheduleTemplate = new ScheduleTemplateObj();
		if(!StringUtils.isEmpty(scheduleTemplateEntity
				.getTemplateByOnscreenTmpl()))
		scheduleTemplate.setOnscreen(scheduleTemplateEntity
				.getTemplateByOnscreenTmpl().getId());
		if(!StringUtils.isEmpty(scheduleTemplateEntity
				.getTemplateByEmailTmpl()))
		scheduleTemplate.setEmail(scheduleTemplateEntity
				.getTemplateByEmailTmpl().getId());
		if(!StringUtils.isEmpty(scheduleTemplateEntity
				.getDelay()))
		scheduleTemplate.setDelay(scheduleTemplateEntity.getDelay());
		return scheduleTemplate;
	}

	/**
	 * Gets the notification vo.
	 *
	 * @param notificationEntity
	 *            the notification entity
	 * @return the notification vo
	 * @throws Exception
	 *             the exception
	 */
	private NotificationObj getNotificationVO(Notification notificationEntity)
			throws Exception {
		NotificationObj notification = new NotificationObj();
		if (!StringUtils.isEmpty(notificationEntity.getAppId()))
			notification.setAppId(notificationEntity.getAppId());
		if (!StringUtils.isEmpty(notificationEntity.getContent()))
			notification.setContent(notificationEntity.getContent());
		if (!StringUtils.isEmpty(notificationEntity.getId()))
			notification.setId(notificationEntity.getId());
		if (!StringUtils
				.isEmpty(notificationEntity.getNotificationRecipients())) {
			NotificationRecipientsObj tempNotificationRecipient = getNotificationRecipientsVO(notificationEntity
					.getNotificationRecipients());
			notification.setNotificationRecipients(tempNotificationRecipient);
		}
		if (!StringUtils.isEmpty(notificationEntity.getSenderEmail()))
			notification.setSenderEmail(notificationEntity.getSenderEmail());
		if (!StringUtils.isEmpty(notificationEntity.getSenderId()))
			notification.setSenderId(notificationEntity.getSenderId());
		if (!StringUtils.isEmpty(notificationEntity.getType()))
			notification.setType(notificationEntity.getType());
		if (!StringUtils.isEmpty(notificationEntity.getSentOn()))
			notification.setSentOn(notificationEntity.getSentOn().toString());
		if (!StringUtils.isEmpty(notificationEntity.getUnread()))
			notification.setUnread(notificationEntity.getUnread());
		TemplateObj tempTemplate = getTemplateVO(notificationEntity
				.getTemplate());
		if (!StringUtils.isEmpty(tempTemplate))
			notification.setTemplate(tempTemplate);
		return notification;

	}

	/**
	 * Gets the notification recipients vo.
	 *
	 * @param notoficationRecipients
	 *            the notofication recipients
	 * @return the notification recipients vo
	 * @throws Exception
	 *             the exception
	 */
	private NotificationRecipientsObj getNotificationRecipientsVO(
			NotificationRecipients notoficationRecipients) throws Exception {
		NotificationRecipientsObj notificationRecipients = new NotificationRecipientsObj();
		notificationRecipients.setNotificationId(notoficationRecipients
				.getNotificationId());
		notificationRecipients.setUserId(notoficationRecipients.getUserId());
		notificationRecipients.setEmail(notoficationRecipients.getEmail());
		return notificationRecipients;
	}

	/**
	 * Gets the template vo.
	 *
	 * @param templateEntity
	 *            the template entity
	 * @return the template vo
	 * @throws Exception
	 *             the exception
	 */
	private TemplateObj getTemplateVO(Template templateEntity) throws Exception {
		TemplateObj template = new TemplateObj();

		template.setCreatedBy(templateEntity.getCreatedBy());
		template.setAppId(templateEntity.getAppId());
		template.setBody(clobStringConversion(templateEntity.getBody()));
		template.setDescription(templateEntity.getDescription());
		template.setModifiedBy(templateEntity.getModifiedBy());
		template.setTagl1(templateEntity.getTagl1());
		template.setTagl2(templateEntity.getTagl2());
		template.setId(templateEntity.getId());
		/*
		 * template.setLastModifiedOn(templateEntity.getLastModifiedOn());
		 * template.setCreatedOn(templateEntity.getCreatedOn());
		 */
		return template;
	}

	/**
	 * Clob string conversion.
	 *
	 * @param clb
	 *            the clb
	 * @return the string
	 * @throws Exception
	 *             the exception
	 */
	private static String clobStringConversion(Clob clb) throws Exception {
		if (clb == null)
			return "";

		StringBuilder str = new StringBuilder();
		String strng;

		BufferedReader bufferRead = new BufferedReader(clb.getCharacterStream());

		while ((strng = bufferRead.readLine()) != null)
			str.append(strng);
		bufferRead.close();
		return str.toString();

	}

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
	@Override
	public List<ScheduleObj> templateLookup(String applicationId,
			String templateId, String type) throws Exception {
		List<ScheduleObj> scheduleList = new ArrayList<ScheduleObj>();

		if (!StringUtils.isEmpty(applicationId)
				&& !StringUtils.isEmpty(templateId)
				&& !StringUtils.isEmpty(type)) {
			List<Schedule> scheduleEntityList = notificationManagementDAO
					.templateLookup(applicationId, templateId, type);
			for (Schedule s : scheduleEntityList)
				scheduleList.add(getScheduleVO(s));
		}
		return scheduleList;
	}

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
	@Override
	public NotificationObj getNotification(String applicationId,
			String notificationId) throws Exception {
		NotificationObj notification = null;
		if (!StringUtils.isEmpty(applicationId)
				&& !StringUtils.isEmpty(notificationId))
			notification = getNotificationVO(notificationManagementDAO
					.getNotification(applicationId, notificationId));
		return notification;
	}

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
	@Override
	public boolean setNotificationFlag(String applicationId,
			String notificationId) throws Exception {
		boolean isSet = false;
		if (!StringUtils.isEmpty(applicationId)
				&& !StringUtils.isEmpty(notificationId))
			isSet = notificationManagementDAO.setNotificationFlag(
					applicationId, notificationId);
		return isSet;
	}

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
	@Override
	public List<NotificationObj> getNotificationHistory(String applicationId,
			String from, String to, String type, String offset, String limit,
			String unreadFlag) throws Exception {
		List<NotificationObj> notificationList = null;
		NotificationRecipientsObj notificationRecipients = null;
		if (!StringUtils.isEmpty(applicationId)) {
			List<Notification> notificationEntityList = notificationManagementDAO
					.getNotificationList(applicationId);
			notificationList = new CopyOnWriteArrayList<NotificationObj>();
			for (Notification ne : notificationEntityList) {
				notificationList.add(getNotificationVO(ne));
			}
			if (!StringUtils.isEmpty(from)) {
				for (NotificationObj n : notificationList) {
					if (!from.equalsIgnoreCase(n.getSenderId()))
						notificationList.remove(n);
				}

			}
			if (!StringUtils.isEmpty(to)) {
				for (NotificationObj n : notificationList) {
					notificationRecipients = getNotificationRecipientsVO(notificationManagementDAO
							.getNotificationRecipients(n.getId()));
					if (!to.equalsIgnoreCase(notificationRecipients.getUserId()))
						notificationList.remove(n);
				}
			}
			if (!StringUtils.isEmpty(type)) {
				for (NotificationObj n : notificationList) {
					if (!type.equalsIgnoreCase(n.getType()))
						notificationList.remove(n);
				}
			}
			if (!StringUtils.isEmpty(unreadFlag)) {
				for (NotificationObj n : notificationList) {
					if (!unreadFlag.equalsIgnoreCase(n.getUnread().toString()))
						notificationList.remove(n);
				}
			}
			if (!StringUtils.isEmpty(offset) && !StringUtils.isEmpty(limit)) {
				int index = Integer.parseInt(offset);
				int range = Integer.parseInt(limit);
				List<NotificationObj> tempList = new ArrayList<NotificationObj>();
				for (int i = index; i < range; i++)
					tempList.add(notificationList.get(i));
				notificationList.retainAll(tempList);

			}

		}
		return notificationList;
	}

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
	@Override
	public void sendEmailNotification(String applicationId, String templateId,
			NotificationDetails notificationDetails) throws AddressException,
			MessagingException, Exception {

		TemplateObj templateObj = templateManagementService.renderTemplate(
				applicationId, templateId,
				notificationDetails.getTemplateDetails());

		mailSenderService.sendEmail(notificationDetails.getFrom(),
				notificationDetails.getTo(), templateObj.getDescription(),
				templateObj.getBody());
	}

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
	@Override
	public void resendEmailNotification(String applicationId,
			String notificationId) throws AddressException, MessagingException,
			Exception {

		NotificationRecipients notificationRecipients = notificationManagementDAO
				.getNotificationRecipients(notificationId);
		Notification notification = notificationManagementDAO.getNotification(
				applicationId, notificationId);
		Template template = notification.getTemplate();
		TemplateObj templateObj = getTemplateVO(template);

		mailSenderService.sendEmail(notification.getSenderEmail(),
				notificationRecipients.getUserId(),
				templateObj.getDescription(), templateObj.getBody());
	}

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
	@Override
	public void sendOnscreenNotification(String applicationId,
			String templateId, NotificationDetails notificationDetails)
			throws Exception {

	}

	private Date getDate(String strDate) throws Exception {
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		Date date = formatter.parse(strDate);
		return date;
	}

}
