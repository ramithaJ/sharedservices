package com.wiley.gr.ace.authorservices.services.service;

import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import com.wiley.gr.ace.authorservices.model.NotificationDetails;
import com.wiley.gr.ace.authorservices.model.NotificationObj;
import com.wiley.gr.ace.authorservices.model.ScheduleObj;

public interface NotificationManagementService {
	
	ScheduleObj getSchedule(String applicationId,String scheduleId) throws Exception;
	boolean insertSchedule(ScheduleObj schedule)throws Exception;
	boolean updateSchedule(String applicationId,String scheduleId,ScheduleObj scheduleObj)throws Exception;
	boolean deleteSchedule(String applicationId,String scheduleId)throws Exception;
	List<ScheduleObj> templateLookup( String applicationId, String templateId, String type) throws Exception;
	NotificationObj getNotification(String applicationId, String notificationId) throws Exception;
	boolean setNotificationFlag(String applicationId, String notificationId) throws Exception;
	List<NotificationObj> getNotificationHistory(String applicationId, String from, String to, String type, String offset, String limit, String unreadFlag) throws Exception;
	void sendOnscreenNotification(String applicationId, String templateId, NotificationDetails notificationDetails) throws Exception;
	void sendEmailNotification(String applicationId, String templateId, NotificationDetails notificationDetails) throws AddressException,MessagingException,Exception;
	void resendEmailNotification(String applicationId, String notificationId) throws AddressException,
			MessagingException, Exception;
}
