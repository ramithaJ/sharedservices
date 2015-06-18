package com.wiley.gr.ace.authorservices.services.service.impl;

import java.io.BufferedReader;
import java.sql.Clob;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.wiley.gr.ace.authorservices.model.NotificationRecipientsVO;
import com.wiley.gr.ace.authorservices.model.NotificationVO;
import com.wiley.gr.ace.authorservices.model.ScheduleTemplateVO;
import com.wiley.gr.ace.authorservices.model.ScheduleVO;
import com.wiley.gr.ace.authorservices.model.TemplateVO;
import com.wiley.gr.ace.authorservices.persistence.entity.Notification;
import com.wiley.gr.ace.authorservices.persistence.entity.NotificationRecipients;
import com.wiley.gr.ace.authorservices.persistence.entity.Schedule;
import com.wiley.gr.ace.authorservices.persistence.entity.ScheduleTemplate;
import com.wiley.gr.ace.authorservices.persistence.entity.Template;
import com.wiley.gr.ace.authorservices.persistence.services.NotificationManagementDAO;
import com.wiley.gr.ace.authorservices.services.service.NotificationManagementService;

public class NotificationManagementServiceImpl implements
		NotificationManagementService {
	@Autowired(required = true)
	NotificationManagementDAO notificationManagementDAO;

	@Override
	public ScheduleVO getSchedule(String applicationId, String scheduleId)
			throws Exception {
		if (!StringUtils.isEmpty(applicationId)
				&& !StringUtils.isEmpty(scheduleId)) {
				return getScheduleVO(notificationManagementDAO.getSchedule(
					applicationId,scheduleId));
		}
		return null;
	}

	@Override
	public boolean insertSchedule(ScheduleVO schedule) throws Exception {
		if (!StringUtils.isEmpty(schedule)) {
			Schedule ScheduleEntity = new Schedule();
			ScheduleEntity.setAppId(schedule.getAppId());
			ScheduleEntity.setCreatedBy(schedule.getCreatedBy());
			ScheduleEntity.setCreatedOn(schedule.getCreatedOn());
			ScheduleEntity.setDescription(schedule.getDescription());
			ScheduleEntity.setId(schedule.getId());
			ScheduleEntity.setLastModifiedOn(schedule.getLastModifiedOn());
			ScheduleEntity.setModifiedBy(schedule.getModifiedBy());
			ScheduleTemplate scheduleTemplate =
					getScheduleTemplateEntity(schedule.getScheduleTemplate(), schedule.getId());
			ScheduleEntity.setScheduleTemplate(scheduleTemplate);
			return notificationManagementDAO
					.saveOrUpdateSchedule(ScheduleEntity,scheduleTemplate);
		} else
			return false;
	}

	@Override
	public boolean updateSchedule(String applicationId, String scheduleId,
			ScheduleVO scheduleObj) throws Exception {
		boolean updateStatus = false;
		Schedule scheduleEntity = null;
		ScheduleTemplate scheduleTemplate = null;
		scheduleEntity = notificationManagementDAO.getSchedule(applicationId,
				scheduleId);

		if (!StringUtils.isEmpty(scheduleObj)) {
			if (!StringUtils.isEmpty(scheduleObj.getAppId()))
				scheduleEntity.setAppId(scheduleObj.getAppId());
			if (!StringUtils.isEmpty(scheduleObj.getScheduleTemplate())) {
				scheduleTemplate =
						getScheduleTemplateEntity(scheduleObj.getScheduleTemplate(), scheduleObj.getId());
				scheduleEntity.setScheduleTemplate(scheduleTemplate);
				scheduleEntity
						.setScheduleTemplate(scheduleTemplate);
			}
			if (!StringUtils.isEmpty(scheduleObj.getDescription()))
				scheduleEntity.setDescription(scheduleObj.getDescription());
		}

		updateStatus = notificationManagementDAO
				.saveOrUpdateSchedule(scheduleEntity,scheduleTemplate);
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
			ScheduleTemplateVO scheduleTemplateObj, String scheduleId) throws Exception {
		ScheduleTemplate scheduleTemplate = new ScheduleTemplate();
		scheduleTemplate.setScheduleId(scheduleId);
		scheduleTemplate
				.setTemplateByOnscreenTmpl(notificationManagementDAO.getTemplate(scheduleTemplateObj.getTemplateByOnscreenTmplId()));
	
		scheduleTemplate
		.setTemplateByEmailTmpl(notificationManagementDAO.getTemplate(scheduleTemplateObj.getTemplateByEmailTmplId()));
		scheduleTemplate.setDelay(scheduleTemplateObj.getDelay());
		return scheduleTemplate;
	}

	private ScheduleVO getScheduleVO(Schedule scheduleEntity) throws Exception {
		ScheduleVO schedule = new ScheduleVO();
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

	private ScheduleTemplateVO getScheduleTemplateVO(
			ScheduleTemplate scheduleTemplateEntity) throws Exception {
		ScheduleTemplateVO scheduleTemplate = new ScheduleTemplateVO();
		scheduleTemplate.setScheduleId(scheduleTemplateEntity.getScheduleId());
		scheduleTemplate
				.setTemplateByOnscreenTmplId(scheduleTemplateEntity.getTemplateByOnscreenTmpl().getId());
		scheduleTemplate
				.setTemplateByEmailTmplId(scheduleTemplateEntity.getTemplateByEmailTmpl().getId());
		scheduleTemplate.setDelay(scheduleTemplateEntity.getDelay());
		return scheduleTemplate;
	}

	
	  private NotificationVO getNotificationVO(Notification NotificationEntity)
	  throws Exception{ 
	NotificationVO notification = new NotificationVO();
	  notification.setAppId(NotificationEntity.getAppId());
	 notification.setContent(NotificationEntity.getContent());
	  notification.setId(NotificationEntity.getId());
	  notification.setNotificationRecipients(getNotificationRecipientsVO(NotificationEntity
	 .getNotificationRecipients()));
	 notification.setSenderEmail(NotificationEntity.getSenderEmail());
	 notification.setType(NotificationEntity.getType());
	 notification.setSentOn(NotificationEntity.getSentOn());
	 notification.setUnread(NotificationEntity.getUnread());
	 notification.setTemplate(getTemplateVO(NotificationEntity.getTemplate())); return notification;
	 
	  }
	 

	
	 private NotificationRecipientsVO
	 getNotificationRecipientsVO(NotificationRecipients
	 notoficationRecipients) throws Exception{ 
		 NotificationRecipientsVO
	 notificationRecipients = new NotificationRecipientsVO();
	 notificationRecipients.setNotificationId(notoficationRecipients.getNotificationId());
	 notificationRecipients.setUserId(notoficationRecipients.getUserId());
	 notificationRecipients.setEmail(notoficationRecipients.getEmail());
	 return notificationRecipients;
	 }
	 
		private TemplateVO getTemplateVO(Template templateEntity) 
				throws Exception {
			TemplateVO template = new TemplateVO();
			
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
	 

	/*private Template getTemplateEntity(TemplateVO template) throws Exception {
		Template templateEntity = new Template();
		templateEntity.setId(template.getId());
		templateEntity.setAppId(template.getAppId());
		templateEntity
				.setBody(new SerialClob(template.getBody().toCharArray()));
		templateEntity.setCreatedBy(template.getCreatedBy());
		templateEntity.setCreatedOn(template.getCreatedOn());
		templateEntity.setDescription(template.getDescription());
		templateEntity.setLastModifiedOn(template.getLastModifiedOn());
		templateEntity.setModifiedBy(template.getModifiedBy());
		templateEntity.setTagl1(template.getTagl1());
		templateEntity.setTagl2(template.getTagl2());
		return templateEntity;
	}*/

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
	public List<ScheduleVO> templateLookup(String applicationId,
			String templateId, String type) throws Exception {
		List<ScheduleVO> scheduleList = new ArrayList<ScheduleVO>();
		
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
	public NotificationVO getNotification(String applicationId,
			String notificationId) throws Exception {
		NotificationVO notification = null;
		if(!StringUtils.isEmpty(applicationId)&&!StringUtils.isEmpty(applicationId))
			notification = getNotificationVO(notificationManagementDAO.getNotification(applicationId, notificationId));
		return notification;
	}

	@Override
	public boolean setNotificationFlag(String applicationId,
			String notificationId) throws Exception {
		boolean isSet=false;
		if(!StringUtils.isEmpty(applicationId)&&!StringUtils.isEmpty(applicationId))
			isSet=notificationManagementDAO.setNotificationFlag(applicationId, notificationId);
			return isSet;
	}

}
