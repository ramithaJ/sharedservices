package com.wiley.gr.ace.authorservices.web.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.wiley.gr.ace.authorservices.model.ErrorPOJO;
import com.wiley.gr.ace.authorservices.model.NotificationVO;
import com.wiley.gr.ace.authorservices.model.ScheduleVO;
import com.wiley.gr.ace.authorservices.model.Service;
import com.wiley.gr.ace.authorservices.services.service.NotificationManagementService;

@RestController
@RequestMapping("/v1/notifications")
public class NotificationManagementController {
	@Autowired(required=true)
	NotificationManagementService notificationManagementService;
	@RequestMapping(value = "get/{applicationId}/{scheduleId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public  @ResponseBody Service getSchedule(@PathVariable("applicationId") String applicationId, 
			@PathVariable("scheduleId") String scheduleId){
		Service service = new Service();
		ScheduleVO schedule = null;
		try{
			schedule = notificationManagementService.getSchedule(applicationId,scheduleId);
		if(!StringUtils.isEmpty(schedule)){
			service.setStatus("SUCCESS");
			service.setPayload(schedule);
		}
		else{
			ErrorPOJO error = new ErrorPOJO();
			error.setCode(301);
			error.setMessage("No records found for the required criteria");
			service.setStatus("ERROR");
			service.setError(error);
		}
		}catch(Exception e){
		ErrorPOJO error = new ErrorPOJO();
		error.setCode(302);
		error.setMessage("Error Fetching Schedule");
		service.setStatus("ERROR");
		service.setError(error);
		}
		return service;
	}
	public Service insertNotification(){
		return new Service();		
	}
	public Service updateNotification(){
		return new Service();		
	}
	@RequestMapping(value = "delete/{applicationId}/{scheduleId}", method = RequestMethod.DELETE)
	public @ResponseBody Service deleteSchedule(@PathVariable("applicationId") String applicationId, 
			@PathVariable("scheduleId") String scheduleId){
		Service service = new Service();
		boolean isDeleted = false;
		try {
			isDeleted = notificationManagementService.deleteSchedule(applicationId,scheduleId);
		} catch (Exception e) {
			ErrorPOJO error = new ErrorPOJO();
			error.setCode(303);
			error.setMessage("Error Fetching Schedule");
			service.setStatus("ERROR");
			service.setError(error);
		}
		if (isDeleted) {
			service.setStatus("SUCCESS");
			service.setPayload(isDeleted);
		}

		else {
			ErrorPOJO error = new ErrorPOJO();
			error.setCode(304);
			error.setMessage("No records found for the required criteria");
			service.setStatus("FAILURE");
			service.setError(error);
		}
		return service;		
	}
	@RequestMapping(value = "/update/{applicationId}/{scheduleId}", method = RequestMethod.POST)
	public @ResponseBody Service updateSchedule(
			@PathVariable("scheduleId") String scheduleId,
			@PathVariable("applicationId") String applicationId,
			@RequestBody ScheduleVO scheduleObj) {
		Service service = new Service();
		boolean isUpdated = false;
		try {
			isUpdated = notificationManagementService.updateSchedule(applicationId,
					scheduleId, scheduleObj);
		} catch (Exception e) {
			ErrorPOJO error = new ErrorPOJO();
			error.setCode(305);
			error.setMessage("Error Fetching Schedule");
			service.setStatus("ERROR");
			service.setError(error);
		}
		if (isUpdated) {
			service.setStatus("SUCCESS");
		}

		else {
			ErrorPOJO error = new ErrorPOJO();
			error.setCode(306);
			error.setMessage("No records found for the required criteria");
			service.setStatus("FAILURE");
			service.setError(error);
		}
		return service;
	}
	@RequestMapping(value = "/create", method = RequestMethod.PUT)
	public @ResponseBody Service createSchedule(@RequestBody ScheduleVO schedule) {
		Service service = new Service();
		boolean isCreated = false;
		try {
			isCreated = notificationManagementService.insertSchedule(schedule);
		} catch (Exception e) {
			ErrorPOJO error = new ErrorPOJO();
			error.setCode(307);
			error.setMessage("Error creating Schedule");
			service.setStatus("ERROR");
			service.setError(error);
		}
		if (isCreated) {
			service.setStatus("SUCCESS");
			service.setPayload(isCreated);
		}

		else {
			ErrorPOJO error = new ErrorPOJO();
			error.setCode(308);
			error.setMessage("No records found for the required criteria");
			service.setStatus("FAILURE");
			service.setError(error);

		}
		return service;
	}
	@RequestMapping(value = "{applicationId}/schedule/template/{templateId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Service templateLookup(@PathVariable String applicationId,@PathVariable String templateId,
			@RequestParam(value = "type") String type){
		Service service = new Service();
		try{
		List<ScheduleVO> scheduleList = notificationManagementService.templateLookup(applicationId, templateId, type);
		if (!StringUtils.isEmpty(scheduleList)) {
			service.setStatus("SUCCESS");
			service.setPayload(scheduleList);
		}

		else {
			ErrorPOJO error = new ErrorPOJO();
			error.setCode(309);
			error.setMessage("No records found for the required criteria");
			service.setStatus("FAILURE");
			service.setError(error);

		}
		}
		catch(Exception e){
			ErrorPOJO error = new ErrorPOJO();
			error.setCode(310);
			error.setMessage("Error fetching Schedule");
			service.setStatus("FAILURE");
			service.setError(error);
		}
		return service;
	}
	@RequestMapping(value = "/{applicationId}/{notificationId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Service getNotificationHistory(@PathVariable("applicationId") String applicationId,
			@PathVariable("notificationId") String notificationId){
		Service service = new Service();
		NotificationVO notification = null;
		try{
			notification = notificationManagementService.getNotification(applicationId,notificationId);
		if(!StringUtils.isEmpty(notification)){
			service.setStatus("SUCCESS");
			service.setPayload(notification);
		}
		else{
			ErrorPOJO error = new ErrorPOJO();
			error.setCode(311);
			error.setMessage("No records found for the required criteria");
			service.setStatus("ERROR");
			service.setError(error);
		}
		}catch(Exception e){
		ErrorPOJO error = new ErrorPOJO();
		error.setCode(312);
		error.setMessage("Error Fetching Notification");
		service.setStatus("ERROR");
		service.setError(error);
		}
		return service;
	}
	@RequestMapping(value = "/{applicationId}/{notificationId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Service setNotificationFlag(@PathVariable("applicationId") String applicationId,
			@PathVariable("notificationId") String notificationId){
		Service service = new Service();
		boolean isSet = false;
		try {
			isSet = notificationManagementService.setNotificationFlag(applicationId, notificationId);
		} catch (Exception e) {
			ErrorPOJO error = new ErrorPOJO();
			error.setCode(313);
			error.setMessage("Error Fetching Notification");
			service.setStatus("ERROR");
			service.setError(error);
		}
		if (isSet) {
			service.setStatus("SUCCESS");
			service.setPayload(isSet);
		}

		else {
			ErrorPOJO error = new ErrorPOJO();
			error.setCode(314);
			error.setMessage("Flag already set");
			service.setStatus("FAILURE");
			service.setError(error);
		}
		return service;	
	}
	@RequestMapping(value = "/{applicationId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Service getNotificationHistory(@PathVariable("applicationId") String applicationId,
			@RequestParam(value = "from") String from,
			@RequestParam(value = "to") String to,
			@RequestParam(value = "type") String type,
			@RequestParam(value = "offset") String offset,
			@RequestParam(value = "limit") String limit,
			@RequestParam(value = "unreadFlag") String unreadFlag){
				Service service = new Service();
				List<NotificationVO> notificationHistory = null;
				try{
					notificationHistory = notificationManagementService.getNotificationHistory(applicationId,from,to,type,offset,limit,unreadFlag);
				if(!StringUtils.isEmpty(notificationHistory)){
					service.setStatus("SUCCESS");
					service.setPayload(notificationHistory);
				}
				else{
					ErrorPOJO error = new ErrorPOJO();
					error.setCode(315);
					error.setMessage("No records found for the required criteria");
					service.setStatus("ERROR");
					service.setError(error);
				}
				}catch(Exception e){
				ErrorPOJO error = new ErrorPOJO();
				error.setCode(316);
				error.setMessage("Error Fetching Notification History");
				service.setStatus("ERROR");
				service.setError(error);
				}
				return service;
		}
}
