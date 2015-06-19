package com.wiley.gr.ace.authorservices.services.service;

import java.util.List;

import com.wiley.gr.ace.authorservices.model.NotificationVO;
import com.wiley.gr.ace.authorservices.model.ScheduleVO;

public interface NotificationManagementService {
	
	ScheduleVO getSchedule(String applicationId,String scheduleId) throws Exception;
	boolean insertSchedule(ScheduleVO schedule)throws Exception;
	boolean updateSchedule(String applicationId,String scheduleId,ScheduleVO scheduleObj)throws Exception;
	boolean deleteSchedule(String applicationId,String scheduleId)throws Exception;
	List<ScheduleVO> templateLookup( String applicationId, String templateId, String type) throws Exception;
	NotificationVO getNotification(String applicationId, String notificationId) throws Exception;
	boolean setNotificationFlag(String applicationId, String notificationId) throws Exception;
	List<NotificationVO> getNotificationHistory(String applicationId, String from, String to, String type, String offset, String limit, String unreadFlag) throws Exception;
}
