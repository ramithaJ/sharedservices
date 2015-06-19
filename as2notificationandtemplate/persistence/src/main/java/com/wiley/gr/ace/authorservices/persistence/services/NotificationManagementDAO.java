package com.wiley.gr.ace.authorservices.persistence.services;

import java.util.List;

import com.wiley.gr.ace.authorservices.persistence.entity.Notification;
import com.wiley.gr.ace.authorservices.persistence.entity.NotificationRecipients;
import com.wiley.gr.ace.authorservices.persistence.entity.Schedule;
import com.wiley.gr.ace.authorservices.persistence.entity.ScheduleTemplate;
import com.wiley.gr.ace.authorservices.persistence.entity.Template;

public interface NotificationManagementDAO {
	Schedule getSchedule(String applicationId,String scheduleId)throws Exception;
	boolean deleteSchedule(String applicationId,String scheduleId)throws Exception;
	List<Schedule> templateLookup(String applicationId,String templateId,String type) throws Exception;
	Template getTemplate(String templateId) throws Exception;
	boolean saveOrUpdateSchedule(Schedule schedule,ScheduleTemplate scheduleTemplate) throws Exception;
	Notification getNotification(String applicationId, String notificationId) throws Exception;
	boolean setNotificationFlag(String applicationId, String notificationId) throws Exception;
	List<Notification> getNotificationList(String applicationId) throws Exception; 
	NotificationRecipients getNotificationRecipients(String notificationId) throws Exception;
}
