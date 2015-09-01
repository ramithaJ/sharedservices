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
package com.wiley.gr.ace.authorservices.services.service.impl;

import java.io.BufferedReader;
import java.sql.Clob;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.sql.rowset.serial.SerialClob;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.wiley.gr.ace.authorservices.email.service.MailSenderService;
import com.wiley.gr.ace.authorservices.model.NotificationDetails;
import com.wiley.gr.ace.authorservices.model.NotificationObj;
import com.wiley.gr.ace.authorservices.model.NotificationRecipientsObj;
import com.wiley.gr.ace.authorservices.model.NotificationResponse;
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

/**
 * The Class NotificationManagementServiceImpl.
 * 
 * @author virtusa version 1.0
 */
public class NotificationManagementServiceImpl implements
		NotificationManagementService {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory
			.getLogger(NotificationManagementServiceImpl.class);

	/** The notification management dao. */
	@Autowired(required = true)
	private NotificationManagementDAO notificationManagementDAO;

	/** The mail sender service. */
	@Autowired(required = true)
	private MailSenderService mailSenderService;

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
	public final ScheduleObj getSchedule(final String applicationId,
			final String scheduleId) throws Exception {
		LOGGER.info("inside getSchedule Method of NotificationManagementServiceImpl");
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
	public final boolean insertSchedule(final ScheduleObj schedule)
			throws Exception {
		LOGGER.info("inside insertSchedule Method of NotificationManagementServiceImpl");
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
	public final boolean updateSchedule(final String applicationId,
			final String scheduleId, final ScheduleObj scheduleObj)
			throws Exception {
		LOGGER.info("inside updateSchedule Method of NotificationManagementServiceImpl");
		Schedule scheduleEntity = null;
		ScheduleTemplate scheduleTemplate = null;
		scheduleEntity = notificationManagementDAO.getSchedule(applicationId,
				scheduleId);
		if (!StringUtils.isEmpty(scheduleObj)) {
			if (!StringUtils.isEmpty(scheduleObj.getAppId()))
				scheduleEntity.setAppId(scheduleObj.getAppId());
			if (!StringUtils.isEmpty(scheduleObj.getScheduleTemplate())) {
				scheduleTemplate = getScheduleTemplateEntity(
						scheduleObj.getScheduleTemplate(), scheduleId,
						applicationId);
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
	public final boolean deleteSchedule(final String applicationId,
			final String scheduleId) throws Exception {
		LOGGER.info("inside deleteSchedule Method of NotificationManagementServiceImpl");
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
	 * @param applcationId
	 *            the applcation id
	 * @return the schedule template entity
	 * @throws Exception
	 *             the exception
	 */
	private ScheduleTemplate getScheduleTemplateEntity(
			final ScheduleTemplateObj scheduleTemplateObj,
			final String scheduleId, final String applcationId)
			throws Exception {
		LOGGER.info("inside getScheduleTemplateEntity Method of NotificationManagementServiceImpl");
		ScheduleTemplate scheduleTemplate = notificationManagementDAO
				.getScheduleTemplateEntity(scheduleId);
		if (StringUtils.isEmpty(scheduleTemplate)) {
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
	private ScheduleObj getScheduleVO(final Schedule scheduleEntity)
			throws Exception {
		LOGGER.info("inside getScheduleVO Method of NotificationManagementServiceImpl");
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
			final ScheduleTemplate scheduleTemplateEntity) throws Exception {
		LOGGER.info("inside getScheduleTemplateVO Method of NotificationManagementServiceImpl");
		ScheduleTemplateObj scheduleTemplate = new ScheduleTemplateObj();
		if (!StringUtils.isEmpty(scheduleTemplateEntity
				.getTemplateByOnscreenTmpl()))
			scheduleTemplate.setOnscreen(scheduleTemplateEntity
					.getTemplateByOnscreenTmpl().getId());
		if (!StringUtils.isEmpty(scheduleTemplateEntity
				.getTemplateByEmailTmpl()))
			scheduleTemplate.setEmail(scheduleTemplateEntity
					.getTemplateByEmailTmpl().getId());
		if (!StringUtils.isEmpty(scheduleTemplateEntity.getDelay()))
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
	private NotificationObj getNotificationVO(
			final Notification notificationEntity) throws Exception {
		LOGGER.info("inside getNotificationVO Method of NotificationManagementServiceImpl");
		NotificationObj notification = new NotificationObj();
		if (!StringUtils.isEmpty(notificationEntity.getAppId()))
			notification.setAppId(notificationEntity.getAppId());
		if (!StringUtils.isEmpty(notificationEntity.getContent()))
			notification.setContent(notificationEntity.getContent().toString());
		if (!StringUtils.isEmpty(notificationEntity.getId()))
			notification.setId(notificationEntity.getId());
		if (!StringUtils.isEmpty(notificationEntity
				.getNotificationRecipientses())) {
			ArrayList<NotificationRecipientsObj> notificationRecipientsObjs = new ArrayList<NotificationRecipientsObj>();
			for (NotificationRecipients notificationRecipients : notificationEntity
					.getNotificationRecipientses()) {
				NotificationRecipientsObj tempNotificationRecipient = getNotificationRecipientsVO(notificationRecipients);
				notificationRecipientsObjs.add(tempNotificationRecipient);
			}
			notification
					.setNotificationRecipientsObjs(notificationRecipientsObjs);
		}
		if (!StringUtils.isEmpty(notificationEntity.getSenderEmail()))
			notification.setSenderEmail(notificationEntity.getSenderEmail());
		if (!StringUtils.isEmpty(notificationEntity.getSenderId()))
			notification.setSenderId(notificationEntity.getSenderId());
		if (!StringUtils.isEmpty(notificationEntity.getType()))
			notification.setType(notificationEntity.getType());
		if (!StringUtils.isEmpty(notificationEntity.getSentOn()))
			notification.setSentOn(notificationEntity.getSentOn());
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
			final NotificationRecipients notoficationRecipients)
			throws Exception {
		LOGGER.info("inside getNotificationRecipientsVO Method of NotificationManagementServiceImpl");
		NotificationRecipientsObj notificationRecipients = new NotificationRecipientsObj();
		notificationRecipients.setNotificationId(notoficationRecipients
				.getNotification().getId());
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
	private TemplateObj getTemplateVO(final Template templateEntity)
			throws Exception {
		LOGGER.info("inside getTemplateVO Method of NotificationManagementServiceImpl");
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
	private static String clobStringConversion(final Clob clb) throws Exception {
		LOGGER.info("inside clobStringConversion Method of NotificationManagementServiceImpl");
		if (clb == null)
			return "";

		StringBuilder str = new StringBuilder();
		String strng;

		BufferedReader bufferRead = new BufferedReader(clb.getCharacterStream());

		while ((strng = bufferRead.readLine()) != null) {
			str.append(strng);
			bufferRead.close();
		}
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
	public final List<ScheduleObj> templateLookup(final String applicationId,
			final String templateId, final String type) throws Exception {
		LOGGER.info("inside templateLookup Method of NotificationManagementServiceImpl");
		List<ScheduleObj> scheduleList = new ArrayList<ScheduleObj>();

		if (!StringUtils.isEmpty(applicationId)
				&& !StringUtils.isEmpty(templateId)
				&& !StringUtils.isEmpty(type)) {
			List<Schedule> scheduleEntityList = notificationManagementDAO
					.templateLookup(applicationId, templateId, type);
			for (Schedule s : scheduleEntityList) {
				scheduleList.add(getScheduleVO(s));
			}
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
	public final NotificationObj getNotification(final String applicationId,
			final Integer notificationId) throws Exception {
		LOGGER.info("inside getNotification Method of NotificationManagementServiceImpl");
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
	public final boolean setNotificationFlag(final String applicationId,
			final Integer notificationId) throws Exception {
		LOGGER.info("inside setNotificationFlag Method of NotificationManagementServiceImpl");
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
	public final List<NotificationObj> getNotificationHistory(
			final String applicationId, final String from, final String to,
			final String type, final String offset, final String limit,
			final String unreadFlag) throws Exception {
		LOGGER.info("inside getNotificationHistory Method of NotificationManagementServiceImpl");
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
					for (NotificationRecipients recipients : notificationManagementDAO
							.getNotificationRecipients(n.getId())) {
						if ("TO".equalsIgnoreCase(recipients.getType())) {
							notificationRecipients = getNotificationRecipientsVO(recipients);
							if (!to.equalsIgnoreCase(notificationRecipients
									.getUserId()))
								notificationList.remove(n);
						}
					}
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
				for (int i = index; i < range; i++) {
					tempList.add(notificationList.get(i));
				}
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
	 * @param templateObj
	 *            the template obj
	 * @return the notification response
	 * @throws Exception
	 *             the exception
	 */
	@Override
	public final NotificationResponse sendEmailNotification(
			final String applicationId, final String templateId,
			final NotificationDetails notificationDetails,
			final TemplateObj templateObj) throws Exception {
		LOGGER.info("inside sendEmailNotification Method of NotificationManagementServiceImpl");
		NotificationResponse notificationResponse = new NotificationResponse();
		mailSenderService.sendEmail(notificationDetails.getFrom(),
				notificationDetails.getTo(), notificationDetails.getCcList(),
				notificationDetails.getBccList(), templateObj.getDescription(),
				templateObj.getBody());
		NotificationObj notificationObj = new NotificationObj();

		notificationObj.setAppId(applicationId);
		notificationObj.setContent(templateObj.getBody());
		notificationObj.setSenderEmail(notificationDetails.getFrom());
		notificationObj.setTemplate(templateObj);
		notificationObj.setType("email");
		notificationObj.setUnread('n');
		notificationObj.setSentOn(new Date());

		ArrayList<NotificationRecipientsObj> recipientList = new ArrayList<NotificationRecipientsObj>();
		if (!StringUtils.isEmpty(notificationDetails.getTo())) {
			for (String toEmail : notificationDetails.getTo()) {
				NotificationRecipientsObj tempNotificationRecipientsObj = new NotificationRecipientsObj();
				tempNotificationRecipientsObj.setEmail(toEmail);
				tempNotificationRecipientsObj.setRecipientType("TO");
				recipientList.add(tempNotificationRecipientsObj);
			}
		}

		if (!StringUtils.isEmpty(notificationDetails.getCcList())) {
			for (String ccEmail : notificationDetails.getCcList()) {
				NotificationRecipientsObj tempNotificationRecipientsObj = new NotificationRecipientsObj();
				tempNotificationRecipientsObj.setEmail(ccEmail);
				tempNotificationRecipientsObj.setRecipientType("CC");
				recipientList.add(tempNotificationRecipientsObj);
			}
		}

		if (!StringUtils.isEmpty(notificationDetails.getBccList())) {
			for (String bccEmail : notificationDetails.getBccList()) {
				NotificationRecipientsObj tempNotificationRecipientsObj = new NotificationRecipientsObj();
				tempNotificationRecipientsObj.setEmail(bccEmail);
				tempNotificationRecipientsObj.setRecipientType("BCC");
				recipientList.add(tempNotificationRecipientsObj);
			}
		}

		Integer notificationId = createNotificationHistory(notificationObj,
				recipientList);
		if (!StringUtils.isEmpty(notificationId)) {
			notificationResponse.setNotificationId(notificationId.toString());
			notificationResponse.setSentToList(notificationDetails.getTo());
			notificationResponse.setSentCCList(notificationDetails.getCcList());
			notificationResponse.setSentBCCList(notificationDetails
					.getBccList());
			notificationResponse.setTemplateId(templateId);
		}

		return notificationResponse;
	}

	/**
	 * Resend email notification.
	 *
	 * @param applicationId
	 *            the application id
	 * @param notificationId
	 *            the notification id
	 * @return the notification response
	 * @throws Exception
	 *             the exception
	 */
	@Override
	public final NotificationResponse resendEmailNotification(
			final String applicationId, final Integer notificationId)
			throws Exception {
		LOGGER.info("inside resendEmailNotification Method of NotificationManagementServiceImpl");
		NotificationResponse notificationResponse = new NotificationResponse();
		ArrayList<NotificationRecipients> notificationRecipientsList = notificationManagementDAO
				.getNotificationRecipients(notificationId);
		Notification notification = notificationManagementDAO.getNotification(
				applicationId, notificationId);
		Template template = notification.getTemplate();
		TemplateObj templateObj = getTemplateVO(template);
		ArrayList<String> toRecipients = new ArrayList<String>();
		ArrayList<String> ccRecipients = new ArrayList<String>();
		ArrayList<String> bccRecipients = new ArrayList<String>();

		for (NotificationRecipients notificationRecipients : notificationRecipientsList) {
			if ("TO".equalsIgnoreCase(notificationRecipients.getType())) {
				toRecipients.add(notificationRecipients.getEmail());
			} else if ("CC".equalsIgnoreCase(notificationRecipients.getType())) {
				ccRecipients.add(notificationRecipients.getEmail());
			} else {
				bccRecipients.add(notificationRecipients.getEmail());
			}
		}
		mailSenderService.sendEmail(notification.getSenderEmail(),
				toRecipients, ccRecipients, bccRecipients,
				templateObj.getDescription(), templateObj.getBody());
		notificationResponse.setNotificationId(notificationId.toString());
		notificationResponse.setSentToList(toRecipients);
		notificationResponse.setSentCCList(ccRecipients);
		notificationResponse.setSentBCCList(bccRecipients);
		notificationResponse.setTemplateId(template.getId());
		return notificationResponse;
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
	public final void sendOnscreenNotification(final String applicationId,
			final String templateId,
			final NotificationDetails notificationDetails) throws Exception {
		LOGGER.info("inside sendOnscreenNotification Method of NotificationManagementServiceImpl");

	}

	/**
	 * Gets the date.
	 *
	 * @param strDate
	 *            the str date
	 * @return the date
	 * @throws Exception
	 *             the exception
	 */
	private Date getDate(final String strDate) throws Exception {
		LOGGER.info("inside getDate Method of NotificationManagementServiceImpl");
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		return formatter.parse(strDate);
	}

	/**
	 * Creates the notification history.
	 *
	 * @param notificationObj
	 *            the notification obj
	 * @param notificationRecipientsObj
	 *            the notification recipients obj
	 * @return the integer
	 * @throws Exception
	 *             the exception
	 */
	private Integer createNotificationHistory(
			final NotificationObj notificationObj,
			final ArrayList<NotificationRecipientsObj> notificationRecipientsObjList)
			throws Exception {
		LOGGER.info("inside createNotificationHistory Method of NotificationManagementServiceImpl");
		Notification notification = new Notification();

		notification.setAppId(notificationObj.getAppId());
		notification.setContent(new SerialClob(notificationObj.getContent().toCharArray()));
		notification.setSenderEmail(notificationObj.getSenderEmail());
		notification.setSentOn(notificationObj.getSentOn());

		Template template = new Template();
		template.setAppId(notificationObj.getTemplate().getAppId());
		template.setBody(new SerialClob(notificationObj.getTemplate().getBody()
				.toCharArray()));
		template.setDescription(notificationObj.getTemplate().getDescription());
		template.setId(notificationObj.getTemplate().getId());
		notification.setTemplate(template);

		notification.setType(notificationObj.getType());
		notification.setUnread(notificationObj.getUnread());

		Set<NotificationRecipients> notificationRecipientsSet = new HashSet<NotificationRecipients>();

		for (NotificationRecipientsObj notificationRecipientsObj : notificationRecipientsObjList) {
			NotificationRecipients notificationRecipients = new NotificationRecipients();
			notificationRecipients.setEmail(notificationRecipientsObj
					.getEmail());
			notificationRecipients.setType(notificationRecipientsObj
					.getRecipientType());
			notificationRecipients.setNotification(notification);
			notificationRecipientsSet.add(notificationRecipients);
		}

		notification.setNotificationRecipientses(notificationRecipientsSet);
		return notificationManagementDAO
				.createNotificationHistory(notification);
	}
}
