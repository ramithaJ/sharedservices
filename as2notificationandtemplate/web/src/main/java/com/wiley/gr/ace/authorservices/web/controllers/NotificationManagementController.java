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
package com.wiley.gr.ace.authorservices.web.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wiley.gr.ace.authorservices.model.ErrorPOJO;
import com.wiley.gr.ace.authorservices.model.NotificationDetails;
import com.wiley.gr.ace.authorservices.model.NotificationObj;
import com.wiley.gr.ace.authorservices.model.NotificationResponse;
import com.wiley.gr.ace.authorservices.model.ScheduleObj;
import com.wiley.gr.ace.authorservices.model.Service;
import com.wiley.gr.ace.authorservices.model.TemplateObj;
import com.wiley.gr.ace.authorservices.services.service.NotificationManagementService;
import com.wiley.gr.ace.authorservices.services.service.TemplateManagementService;

/**
 * The Class NotificationManagementController.
 * 
 * @author virtusa version 1.0
 */
@RestController
@RequestMapping("/v1/notifications")
public class NotificationManagementController {

    /** logger configured. */
    private static final Logger LOGGER = LoggerFactory
            .getLogger(NotificationManagementController.class);

    /** The notification management service. */
    @Autowired(required = true)
    private NotificationManagementService notificationManagementService;

    /** The template management service. */
    @Autowired(required = true)
    private TemplateManagementService templateManagementService;
    
    /** value from props file configured. */
    @Value("${NotificationManagementController.noSchedule.code}")
    private int noScheduleErrorCode;

    /** value from props file configured. */
    @Value("${NotificationManagementController.noSchedule.message}")
    private String noScheduleErrorMessage;
    
    /** value from props file configured. */
    @Value("${NotificationManagementController.getSchedule.code}")
    private int getScheduleErrorCode;

    /** value from props file configured. */
    @Value("${NotificationManagementController.getSchedule.message}")
    private String getScheduleErrorMessage;

    /** value from props file configured. */
    @Value("${NotificationManagementController.noDeleteSchedule.code}")
    private int noDeleteScheduleErrorCode;

    /** value from props file configured. */
    @Value("${NotificationManagementController.noDeleteSchedule.message}")
    private String noDeleteScheduleErrorMessage;
    
    /** value from props file configured. */
    @Value("${NotificationManagementController.deleteSchedule.code}")
    private int deleteScheduleErrorCode;

    /** value from props file configured. */
    @Value("${NotificationManagementController.deleteSchedule.message}")
    private String deleteScheduleErrorMessage;
    
    /** value from props file configured. */
    @Value("${NotificationManagementController.noUpdateSchedule.code}")
    private int noUpdateScheduleErrorCode;

    /** value from props file configured. */
    @Value("${NotificationManagementController.noUpdateSchedule.message}")
    private String noUpdateScheduleErrorMessage;
    
    /** value from props file configured. */
    @Value("${NotificationManagementController.updateSchedule.code}")
    private int updateScheduleErrorCode;

    /** value from props file configured. */
    @Value("${NotificationManagementController.updateSchedule.message}")
    private String updateScheduleErrorMessage;
    
    /** value from props file configured. */
    @Value("${NotificationManagementController.noCreateSchedule.code}")
    private int noCreateScheduleErrorCode;

    /** value from props file configured. */
    @Value("${NotificationManagementController.noCreateSchedule.message}")
    private String noCreateScheduleErrorMessage;
    
    /** value from props file configured. */
    @Value("${NotificationManagementController.createSchedule.code}")
    private int createScheduleErrorCode;

    /** value from props file configured. */
    @Value("${NotificationManagementController.createSchedule.message}")
    private String createScheduleErrorMessage;
    
    /** value from props file configured. */
    @Value("${NotificationManagementController.noTemplateLookup.code}")
    private int noTemplateLookupErrorCode;

    /** value from props file configured. */
    @Value("${NotificationManagementController.noTemplateLookup.message}")
    private String noTemplateLookupErrorMessage;
    
    /** value from props file configured. */
    @Value("${NotificationManagementController.templateLookup.code}")
    private int templateLookupErrorCode;

    /** value from props file configured. */
    @Value("${NotificationManagementController.templateLookup.message}")
    private String templateLookupErrorMessage;
    
    /** value from props file configured. */
    @Value("${NotificationManagementController.noNotification.code}")
    private int noNotificationErrorCode;

    /** value from props file configured. */
    @Value("${NotificationManagementController.noNotification.message}")
    private String noNotificationErrorMessage;
    
    /** value from props file configured. */
    @Value("${NotificationManagementController.getNotification.code}")
    private int getNotificationErrorCode;

    /** value from props file configured. */
    @Value("${NotificationManagementController.getNotification.message}")
    private String getNotificationErrorMessage;
    
    /** value from props file configured. */
    @Value("${NotificationManagementController.noSetNotificationFlag.code}")
    private int noSetNotificationFlagErrorCode;

    /** value from props file configured. */
    @Value("${NotificationManagementController.noSetNotificationFlag.message}")
    private String noSetNotificationFlagErrorMessage;
    
    /** value from props file configured. */
    @Value("${NotificationManagementController.setNotificationFlag.code}")
    private int setNotificationFlagErrorCode;

    /** value from props file configured. */
    @Value("${NotificationManagementController.setNotificationFlag.message}")
    private String setNotificationFlagErrorMessage;
    
    /** value from props file configured. */
    @Value("${NotificationManagementController.noNotificationHistory.code}")
    private int noNotificationHistoryErrorCode;

    /** value from props file configured. */
    @Value("${NotificationManagementController.noNotificationHistory.message}")
    private String noNotificationHistoryErrorMessage;
    
    /** value from props file configured. */
    @Value("${NotificationManagementController.getNotificationHistory.code}")
    private int getNotificationHistoryErrorCode;

    /** value from props file configured. */
    @Value("${NotificationManagementController.getNotificationHistory.message}")
    private String getNotificationHistoryErrorMessage;
    
    /** value from props file configured. */
    @Value("${NotificationManagementController.sendNotification.code}")
    private int sendNotificationErrorCode;

    /** value from props file configured. */
    @Value("${NotificationManagementController.sendNotification.message}")
    private String sendNotificationErrorMessage;
    
    /** value from props file configured. */
    @Value("${NotificationManagementController.resendNotification.code}")
    private int resendNotificationErrorCode;

    /** value from props file configured. */
    @Value("${NotificationManagementController.resendNotification.message}")
    private String resendNotificationErrorMessage;
    
    /**
     * Gets the schedule.
     *
     * @param applicationId
     *            the application id
     * @param scheduleId
     *            the schedule id
     * @return the schedule
     */
    @RequestMapping(value = "/{applicationId}/schedule/{scheduleId}", method = RequestMethod.GET)
    public final Service getSchedule(
            @PathVariable("applicationId") final String applicationId,
            @PathVariable("scheduleId") final String scheduleId) {
        LOGGER.info("inside getSchedule method of NotificationManagementController");
        Service service = new Service();
        ScheduleObj schedule = null;
        try {
            schedule = notificationManagementService.getSchedule(applicationId,
                    scheduleId);
            if (!StringUtils.isEmpty(schedule)) {
                service.setStatus("SUCCESS");
                service.setPayload(schedule);
            } else {
                final ErrorPOJO error = new ErrorPOJO();
                error.setCode(noScheduleErrorCode);
                error.setMessage(noScheduleErrorMessage);
                service.setStatus("ERROR");
                service.setError(error);
            }
        } catch (final Exception e) {
            LOGGER.error("Print Stack Trace- ", e);
            final ErrorPOJO error = new ErrorPOJO();
            error.setCode(getScheduleErrorCode);
            error.setMessage(getScheduleErrorMessage);
            service.setStatus("ERROR");
            service.setError(error);
        }
        return service;
    }

    /**
     * Delete schedule.
     *
     * @param applicationId
     *            the application id
     * @param scheduleId
     *            the schedule id
     * @return the service
     */
    @RequestMapping(value = "/{applicationId}/schedule/{scheduleId}", method = RequestMethod.DELETE)
    public final Service deleteSchedule(
            @PathVariable("applicationId") final String applicationId,
            @PathVariable("scheduleId") final String scheduleId) {
        LOGGER.info("inside deleteSchedule method of NotificationManagementController");
        Service service = new Service();
        boolean isDeleted = false;
        try {
            isDeleted = notificationManagementService.deleteSchedule(
                    applicationId, scheduleId);
        } catch (final Exception e) {
            LOGGER.error("Print Stack Trace- ", e);
            final ErrorPOJO error = new ErrorPOJO();
            error.setCode(deleteScheduleErrorCode);
            error.setMessage(deleteScheduleErrorMessage);
            service.setStatus("ERROR");
            service.setError(error);
        }
        if (isDeleted) {
            service.setStatus("SUCCESS");
            service.setPayload(isDeleted);
        } else {
            final ErrorPOJO error = new ErrorPOJO();
            error.setCode(noDeleteScheduleErrorCode);
            error.setMessage(noDeleteScheduleErrorMessage);
            service.setStatus("FAILURE");
            service.setError(error);
        }
        return service;
    }

    /**
     * Update schedule.
     *
     * @param scheduleId
     *            the schedule id
     * @param applicationId
     *            the application id
     * @param scheduleObj
     *            the schedule obj
     * @return the service
     */
    @RequestMapping(value = "/{applicationId}/schedule/{scheduleId}", method = RequestMethod.PUT)
    public final Service updateSchedule(
            @PathVariable("scheduleId") final String scheduleId,
            @PathVariable("applicationId") final String applicationId,
            @RequestBody final ScheduleObj scheduleObj) {
        LOGGER.info("inside updateSchedule method of NotificationManagementController");
        Service service = new Service();
        boolean isUpdated = false;
        try {
            isUpdated = notificationManagementService.updateSchedule(
                    applicationId, scheduleId, scheduleObj);
        } catch (final Exception e) {
            LOGGER.error("Print Stack Trace- ", e);
            final ErrorPOJO error = new ErrorPOJO();
            error.setCode(updateScheduleErrorCode);
            error.setMessage(updateScheduleErrorMessage);
            service.setStatus("ERROR");
            service.setError(error);
        }
        if (isUpdated) {
            service.setStatus("SUCCESS");
        } else {
            final ErrorPOJO error = new ErrorPOJO();
            error.setCode(noUpdateScheduleErrorCode);
            error.setMessage(noUpdateScheduleErrorMessage);
            service.setStatus("FAILURE");
            service.setError(error);
        }
        return service;
    }

    /**
     * Creates the schedule.
     *
     * @param schedule
     *            the schedule
     * @return the service
     */
    @RequestMapping(method = RequestMethod.POST)
    public final Service createSchedule(@RequestBody final ScheduleObj schedule) {
        LOGGER.info("inside createSchedule method of NotificationManagementController");
        Service service = new Service();
        boolean isCreated = false;
        try {
            isCreated = notificationManagementService.insertSchedule(schedule);
        } catch (final Exception e) {
            LOGGER.error("Print Stack Trace- ", e);
            final ErrorPOJO error = new ErrorPOJO();
            error.setCode(createScheduleErrorCode);
            error.setMessage(createScheduleErrorMessage);
            service.setStatus("ERROR");
            service.setError(error);
        }
        if (isCreated) {
            service.setStatus("SUCCESS");
            service.setPayload(isCreated);
        } else {
            ErrorPOJO error = new ErrorPOJO();
            error.setCode(noCreateScheduleErrorCode);
            error.setMessage(noCreateScheduleErrorMessage);
            service.setStatus("FAILURE");
            service.setError(error);
        }
        return service;
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
     * @return the service
     */
    @RequestMapping(value = "/{applicationId}/schedule/template/{templateId}", method = RequestMethod.GET)
    public final Service templateLookup(
            @PathVariable final String applicationId,
            @PathVariable final String templateId,
            @RequestParam(value = "type") final String type) {
        LOGGER.info("inside templateLookup method of NotificationManagementController");
        Service service = new Service();
        try {
            final List<ScheduleObj> scheduleList = notificationManagementService
                    .templateLookup(applicationId, templateId, type);
            if (!StringUtils.isEmpty(scheduleList)) {
                service.setStatus("SUCCESS");
                service.setPayload(scheduleList);
            } else {
                final ErrorPOJO error = new ErrorPOJO();
                error.setCode(noTemplateLookupErrorCode);
                error.setMessage(noTemplateLookupErrorMessage);
                service.setStatus("FAILURE");
                service.setError(error);

            }
        } catch (final Exception e) {
            LOGGER.error("Print Stack Trace- ", e);
            final ErrorPOJO error = new ErrorPOJO();
            error.setCode(templateLookupErrorCode);
            error.setMessage(templateLookupErrorMessage);
            service.setStatus("FAILURE");
            service.setError(error);
        }
        return service;
    }

    /**
     * Gets the notification.
     *
     * @param applicationId
     *            the application id
     * @param notificationId
     *            the notification id
     * @return the notification
     */
    @RequestMapping(value = "/{applicationId}/{notificationId}", method = RequestMethod.GET)
    public final Service getNotification(
            @PathVariable("applicationId") final String applicationId,
            @PathVariable("notificationId") final Integer notificationId) {
        LOGGER.info("inside getNotification method of NotificationManagementController");
        Service service = new Service();
        NotificationObj notification = null;
        try {
            notification = notificationManagementService.getNotification(
                    applicationId, notificationId);
            if (!StringUtils.isEmpty(notification)) {
                service.setStatus("SUCCESS");
                service.setPayload(notification);
            } else {
                final ErrorPOJO error = new ErrorPOJO();
                error.setCode(noNotificationErrorCode);
                error.setMessage(noNotificationErrorMessage);
                service.setStatus("ERROR");
                service.setError(error);
            }
        } catch (final Exception e) {
            LOGGER.error("Print Stack Trace- ", e);
            final ErrorPOJO error = new ErrorPOJO();
            error.setCode(getNotificationErrorCode);
            error.setMessage(getNotificationErrorMessage);
            service.setStatus("ERROR");
            service.setError(error);
        }
        return service;
    }

    /**
     * Sets the notification flag.
     *
     * @param applicationId
     *            the application id
     * @param notificationId
     *            the notification id
     * @return the service
     */
    @RequestMapping(value = "/{applicationId}/{notificationId}", method = RequestMethod.PUT)
    public final Service setNotificationFlag(
            @PathVariable("applicationId") final String applicationId,
            @PathVariable("notificationId") final Integer notificationId) {
        LOGGER.info("inside setNotificationFlag method of NotificationManagementController");
        Service service = new Service();
        boolean isSet = false;
        try {
            isSet = notificationManagementService.setNotificationFlag(
                    applicationId, notificationId);
        } catch (final Exception e) {
            LOGGER.error("Print Stack Trace- ", e);
            final ErrorPOJO error = new ErrorPOJO();
            error.setCode(setNotificationFlagErrorCode);
            error.setMessage(setNotificationFlagErrorMessage);
            service.setStatus("ERROR");
            service.setError(error);
        }
        if (isSet) {
            service.setStatus("SUCCESS");
            service.setPayload(isSet);
        } else {
            final ErrorPOJO error = new ErrorPOJO();
            error.setCode(noSetNotificationFlagErrorCode);
            error.setMessage(noSetNotificationFlagErrorMessage);
            service.setStatus("FAILURE");
            service.setError(error);
        }
        return service;
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
     */
    @RequestMapping(value = "/{applicationId}", method = RequestMethod.GET)
    public final Service getNotificationHistory(
            @PathVariable("applicationId") final String applicationId,
            @RequestParam(value = "from") final String from,
            @RequestParam(value = "to") final String to,
            @RequestParam(value = "type") final String type,
            @RequestParam(value = "offset") final String offset,
            @RequestParam(value = "limit") final String limit,
            @RequestParam(value = "unreadFlag") final String unreadFlag) {
        LOGGER.info("inside getNotificationHistory method of NotificationManagementController");
        Service service = new Service();
        List<NotificationObj> notificationHistory = null;
        try {
            notificationHistory = notificationManagementService
                    .getNotificationHistory(applicationId, from, to, type,
                            offset, limit, unreadFlag);
            if (!StringUtils.isEmpty(notificationHistory)) {
                service.setStatus("SUCCESS");
                service.setPayload(notificationHistory);
            } else {
                final ErrorPOJO error = new ErrorPOJO();
                error.setCode(noNotificationHistoryErrorCode);
                error.setMessage(noNotificationHistoryErrorMessage);
                service.setStatus("ERROR");
                service.setError(error);
            }
        } catch (final Exception e) {
            LOGGER.error("Print Stack Trace- ", e);
            final ErrorPOJO error = new ErrorPOJO();
            error.setCode(getNotificationHistoryErrorCode);
            error.setMessage(getNotificationHistoryErrorMessage);
            service.setStatus("ERROR");
            service.setError(error);
        }
        return service;
    }

    /**
     * Send notification.
     *
     * @param applicationId
     *            the application id
     * @param templateId
     *            the template id
     * @param type
     *            the type
     * @param notificationDetails
     *            the notification details
     * @return the service
     */
    @RequestMapping(value = "/{applicationId}/send", method = RequestMethod.POST)
    public final Service sendNotification(
            @PathVariable("applicationId") final String applicationId,
            @RequestParam("tmpl") final String templateId,
            @RequestParam("type") final String type,
            @RequestBody final NotificationDetails notificationDetails) {
        LOGGER.info("inside sendNotification method of NotificationManagementController");
        Service service = new Service();
        TemplateObj templateObj = null;
        NotificationResponse notificationResponse = null;
        try {
            if ("email".equalsIgnoreCase(type)) {

                templateObj = templateManagementService.renderTemplate(
                        applicationId, templateId,
                        notificationDetails.getTemplateDetails());
                notificationResponse = notificationManagementService
                        .sendEmailNotification(applicationId, templateId,
                                notificationDetails, templateObj);
                service.setStatus("SUCCESS");
                service.setPayload(notificationResponse);
            }
        } catch (Exception e) {
            LOGGER.error("Print Stack Trace- ", e);
            service.setStatus("ERROR");
            ErrorPOJO err = new ErrorPOJO();
            err.setCode(sendNotificationErrorCode);
            err.setMessage(sendNotificationErrorMessage);
            service.setError(err);
        }
        return service;
    }

    /**
     * Resend notification.
     *
     * @param applicationId
     *            the application id
     * @param notificationId
     *            the notification id
     * @return the service
     */
    @RequestMapping(value = "/{applicationId}/resend/{notificationId}", method = RequestMethod.POST)
    public final Service resendNotification(
            @PathVariable("applicationId") final String applicationId,
            @PathVariable("notificationId") final Integer notificationId) {
        LOGGER.info("inside resendNotification method of NotificationManagementController");
        Service service = new Service();
        NotificationResponse notificationResponse = null;
        try {
            notificationResponse = notificationManagementService
                    .resendEmailNotification(applicationId, notificationId);
            service.setStatus("SUCCESS");
            service.setPayload(notificationResponse);
        } catch (Exception e) {
            LOGGER.error("Print Stack Trace- ", e);
            service.setStatus("ERROR");
            ErrorPOJO err = new ErrorPOJO();
            err.setCode(resendNotificationErrorCode);
            err.setMessage(resendNotificationErrorMessage);
            service.setError(err);
        }
        return service;
    }
}
