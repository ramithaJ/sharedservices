package com.wiley.gr.ace.authorservices.services.service.impl;

import java.io.BufferedReader;
import java.sql.Clob;
import java.util.ArrayList;
import java.util.List;

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

public class NotificationManagementServiceImpl implements
		NotificationManagementService {
	@Autowired(required = true)
	NotificationManagementDAO notificationManagementDAO;

	@Autowired(required = true)
	TemplateManagementService templateManagementService;

	@Autowired(required = true)
	MailSenderService mailSenderService;

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

	@Override
	public boolean insertSchedule(ScheduleObj schedule) throws Exception {
		if (!StringUtils.isEmpty(schedule)) {
			Schedule ScheduleEntity = new Schedule();
			ScheduleEntity.setAppId(schedule.getAppId());
			ScheduleEntity.setCreatedBy(schedule.getCreatedBy());
			ScheduleEntity.setCreatedOn(schedule.getCreatedOn());
			ScheduleEntity.setDescription(schedule.getDescription());
			ScheduleEntity.setId(schedule.getId());
			ScheduleEntity.setLastModifiedOn(schedule.getLastModifiedOn());
			ScheduleEntity.setModifiedBy(schedule.getModifiedBy());
			ScheduleTemplate scheduleTemplate = getScheduleTemplateEntity(
					schedule.getScheduleTemplate(), schedule.getId());
			ScheduleEntity.setScheduleTemplate(scheduleTemplate);
			return notificationManagementDAO.saveOrUpdateSchedule(
					ScheduleEntity, scheduleTemplate);
		} else
			return false;
	}

	@Override
	public boolean updateSchedule(String applicationId, String scheduleId,
			ScheduleObj scheduleObj) throws Exception {
		boolean updateStatus = false;
		Schedule scheduleEntity = null;
		ScheduleTemplate scheduleTemplate = null;
		scheduleEntity = notificationManagementDAO.getSchedule(applicationId,
				scheduleId);

		if (!StringUtils.isEmpty(scheduleObj)) {
			if (!StringUtils.isEmpty(scheduleObj.getAppId()))
				scheduleEntity.setAppId(scheduleObj.getAppId());
			if (!StringUtils.isEmpty(scheduleObj.getScheduleTemplate())) {
				scheduleTemplate = getScheduleTemplateEntity(
						scheduleObj.getScheduleTemplate(), scheduleObj.getId());
				if (!StringUtils.isEmpty(scheduleTemplate)) {
					scheduleEntity.setScheduleTemplate(scheduleTemplate);
				}
			}
			if (!StringUtils.isEmpty(scheduleObj.getDescription()))
				scheduleEntity.setDescription(scheduleObj.getDescription());
		}

		updateStatus = notificationManagementDAO.saveOrUpdateSchedule(
				scheduleEntity, scheduleTemplate);
		return updateStatus;
	}

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

	private ScheduleTemplate getScheduleTemplateEntity(
			ScheduleTemplateObj scheduleTemplateObj, String scheduleId)
			throws Exception {
		ScheduleTemplate scheduleTemplate = new ScheduleTemplate();
		scheduleTemplate.setScheduleId(scheduleId);
		if (!StringUtils.isEmpty(scheduleTemplateObj
				.getTemplateByOnscreenTmplId())) {
			Template temTemplate = notificationManagementDAO
					.getTemplate(scheduleTemplateObj
							.getTemplateByOnscreenTmplId());
			scheduleTemplate.setTemplateByOnscreenTmpl(temTemplate);
		}

		if (!StringUtils
				.isEmpty(scheduleTemplateObj.getTemplateByEmailTmplId())) {
			Template temTemplate = notificationManagementDAO
					.getTemplate(scheduleTemplateObj.getTemplateByEmailTmplId());
			scheduleTemplate.setTemplateByEmailTmpl(temTemplate);
		}
		if (!StringUtils.isEmpty(scheduleTemplateObj.getDelay())) {
			scheduleTemplate.setDelay(scheduleTemplateObj.getDelay());
		}
		return scheduleTemplate;
	}

	private ScheduleObj getScheduleVO(Schedule scheduleEntity) throws Exception {
		ScheduleObj schedule = new ScheduleObj();
		schedule.setId(scheduleEntity.getId());
		schedule.setAppId(scheduleEntity.getAppId());
		schedule.setDescription(scheduleEntity.getDescription());
		schedule.setCreatedBy(scheduleEntity.getCreatedBy());
		schedule.setModifiedBy(scheduleEntity.getModifiedBy());
		schedule.setCreatedOn(scheduleEntity.getCreatedOn());
		schedule.setLastModifiedOn(scheduleEntity.getLastModifiedOn());
		schedule.setScheduleTemplate(getScheduleTemplateVO(scheduleEntity
				.getScheduleTemplate()));
		return schedule;
	}

	private ScheduleTemplateObj getScheduleTemplateVO(
			ScheduleTemplate scheduleTemplateEntity) throws Exception {
		ScheduleTemplateObj scheduleTemplate = new ScheduleTemplateObj();
		scheduleTemplate.setScheduleId(scheduleTemplateEntity.getScheduleId());
		scheduleTemplate.setTemplateByOnscreenTmplId(scheduleTemplateEntity
				.getTemplateByOnscreenTmpl().getId());
		scheduleTemplate.setTemplateByEmailTmplId(scheduleTemplateEntity
				.getTemplateByEmailTmpl().getId());
		scheduleTemplate.setDelay(scheduleTemplateEntity.getDelay());
		return scheduleTemplate;
	}

	private NotificationObj getNotificationVO(Notification NotificationEntity)
			throws Exception {
		NotificationObj notification = new NotificationObj();
		notification.setAppId(NotificationEntity.getAppId());
		notification.setContent(NotificationEntity.getContent());
		notification.setId(NotificationEntity.getId());
		notification
				.setNotificationRecipients(getNotificationRecipientsVO(NotificationEntity
						.getNotificationRecipients()));
		notification.setSenderEmail(NotificationEntity.getSenderEmail());
		notification.setType(NotificationEntity.getType());
		notification.setSentOn(NotificationEntity.getSentOn());
		notification.setUnread(NotificationEntity.getUnread());
		notification
				.setTemplate(getTemplateVO(NotificationEntity.getTemplate()));
		return notification;

	}

	private NotificationRecipientsObj getNotificationRecipientsVO(
			NotificationRecipients notoficationRecipients) throws Exception {
		NotificationRecipientsObj notificationRecipients = new NotificationRecipientsObj();
		notificationRecipients.setNotificationId(notoficationRecipients
				.getNotificationId());
		notificationRecipients.setUserId(notoficationRecipients.getUserId());
		notificationRecipients.setEmail(notoficationRecipients.getEmail());
		return notificationRecipients;
	}

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
		template.setLastModifiedOn(templateEntity.getLastModifiedOn());
		template.setCreatedOn(templateEntity.getCreatedOn());
		return template;
	}


	private static String clobStringConversion(Clob clb) throws Exception {
		if (clb == null)
			return "";

		StringBuffer str = new StringBuffer();
		String strng;

		BufferedReader bufferRead = new BufferedReader(clb.getCharacterStream());

		while ((strng = bufferRead.readLine()) != null)
			str.append(strng);

		return str.toString();
	}

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

	@Override
	public boolean setNotificationFlag(String applicationId,
			String notificationId) throws Exception {
		boolean isSet = false;
		if (!StringUtils.isEmpty(applicationId)
				&& !StringUtils.isEmpty(applicationId))
			isSet = notificationManagementDAO.setNotificationFlag(
					applicationId, notificationId);
		return isSet;
	}

	@Override
	public List<NotificationObj> getNotificationHistory(String applicationId,
			String from, String to, String type, String offset, String limit,
			String unreadFlag) throws Exception {
		List<NotificationObj> notificationList = null;
		NotificationRecipientsObj notificationRecipients = null;
		if (!StringUtils.isEmpty(applicationId)) {
			List<Notification> notificationEntityList = notificationManagementDAO
					.getNotificationList(applicationId);
			notificationList = new ArrayList<NotificationObj>();
			for (Notification ne : notificationEntityList)
				notificationList.add(getNotificationVO(ne));
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
	
	

	@Override
	public void sendEmailNotification(String applicationId, String templateId,
			NotificationDetails notificationDetails) throws AddressException,
			MessagingException, Exception {

		TemplateObj templateObj = templateManagementService.renderTemplate(
				applicationId, templateId, notificationDetails.getTemplateDetails());

		mailSenderService.sendEmail(notificationDetails.getFrom(),
				notificationDetails.getTo(), templateObj.getDescription(),
				templateObj.getBody());
	}
	
	@Override
	public void resendEmailNotification(String applicationId, String notificationId) throws AddressException,
			MessagingException, Exception {
		
		NotificationRecipients notificationRecipients = notificationManagementDAO.getNotificationRecipients(notificationId);
		Notification notification = notificationManagementDAO.getNotification(applicationId, notificationId);
		Template template = notification.getTemplate();
		TemplateObj templateObj = getTemplateVO(template);

		mailSenderService.sendEmail(notification.getSenderEmail(),
				notificationRecipients.getUserId(),templateObj.getDescription(),
				templateObj.getBody());
	}
	@Override
	public void sendOnscreenNotification(String applicationId,
			String templateId, NotificationDetails notificationDetails)
			throws Exception {
		
		
	}

}
