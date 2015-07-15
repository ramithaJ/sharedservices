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

    /**
     * Gets the schedule.
     *
     * @param applicationId
     *            the application id
     * @param scheduleId
     *            the schedule id
     * @return the schedule
     */
    @RequestMapping(value = "/{applicationId}/schedule/{scheduleId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody final Service getSchedule(
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
                error.setCode(301);
                error.setMessage("No records found for the required criteria");
                service.setStatus("ERROR");
                service.setError(error);
            }
        } catch (final Exception e) {
            LOGGER.error("Print Stack Trace- ", e);
            final ErrorPOJO error = new ErrorPOJO();
            error.setCode(302);
            error.setMessage("Error Fetching Schedule");
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
    public @ResponseBody final Service deleteSchedule(
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
            error.setCode(303);
            error.setMessage("Error Fetching Schedule");
            service.setStatus("ERROR");
            service.setError(error);
        }
        if (isDeleted) {
            service.setStatus("SUCCESS");
            service.setPayload(isDeleted);
        } else {
            final ErrorPOJO error = new ErrorPOJO();
            error.setCode(304);
            error.setMessage("No records found for the required criteria");
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
    public @ResponseBody final Service updateSchedule(
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
            error.setCode(305);
            error.setMessage("Error Fetching Schedule");
            service.setStatus("ERROR");
            service.setError(error);
        }
        if (isUpdated) {
            service.setStatus("SUCCESS");
        } else {
            final ErrorPOJO error = new ErrorPOJO();
            error.setCode(306);
            error.setMessage("No records found for the required criteria");
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
    public @ResponseBody final Service createSchedule(
            @RequestBody final ScheduleObj schedule) {
        LOGGER.info("inside createSchedule method of NotificationManagementController");
        Service service = new Service();
        boolean isCreated = false;
        try {
            isCreated = notificationManagementService.insertSchedule(schedule);
        } catch (final Exception e) {
            LOGGER.error("Print Stack Trace- ", e);
            final ErrorPOJO error = new ErrorPOJO();
            error.setCode(307);
            error.setMessage("Error creating Schedule");
            service.setStatus("ERROR");
            service.setError(error);
        }
        if (isCreated) {
            service.setStatus("SUCCESS");
            service.setPayload(isCreated);
        } else {
            ErrorPOJO error = new ErrorPOJO();
            error.setCode(308);
            error.setMessage("No records found for the required criteria");
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
    @RequestMapping(value = "/{applicationId}/schedule/template/{templateId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody final Service templateLookup(
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
                error.setCode(309);
                error.setMessage("No records found for the required criteria");
                service.setStatus("FAILURE");
                service.setError(error);

            }
        } catch (final Exception e) {
            LOGGER.error("Print Stack Trace- ", e);
            final ErrorPOJO error = new ErrorPOJO();
            error.setCode(310);
            error.setMessage("Error fetching Schedule");
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
    @RequestMapping(value = "/{applicationId}/{notificationId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody final Service getNotification(
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
                error.setCode(311);
                error.setMessage("No records found for the required criteria");
                service.setStatus("ERROR");
                service.setError(error);
            }
        } catch (final Exception e) {
            LOGGER.error("Print Stack Trace- ", e);
            final ErrorPOJO error = new ErrorPOJO();
            error.setCode(312);
            error.setMessage("Error Fetching Notification");
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
    @RequestMapping(value = "/{applicationId}/{notificationId}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody final Service setNotificationFlag(
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
            error.setCode(313);
            error.setMessage("Error Fetching Notification");
            service.setStatus("ERROR");
            service.setError(error);
        }
        if (isSet) {
            service.setStatus("SUCCESS");
            service.setPayload(isSet);
        } else {
            final ErrorPOJO error = new ErrorPOJO();
            error.setCode(314);
            error.setMessage("Flag already set");
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
    @RequestMapping(value = "/{applicationId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody final Service getNotificationHistory(
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
                error.setCode(315);
                error.setMessage("No records found for the required criteria");
                service.setStatus("ERROR");
                service.setError(error);
            }
        } catch (final Exception e) {
            LOGGER.error("Print Stack Trace- ", e);
            final ErrorPOJO error = new ErrorPOJO();
            error.setCode(316);
            error.setMessage("Error Fetching Notification History");
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
    @RequestMapping(value = "/{applicationId}/send", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody final Service sendNotification(
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
            err.setCode(317);
            err.setMessage("Mail sending failed");
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
    @RequestMapping(value = "/{applicationId}/resend/{notificationId}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody final Service resendNotification(
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
            err.setCode(318);
            err.setMessage("Mail sending failed");
            service.setError(err);
        }

        return service;
    }

}
