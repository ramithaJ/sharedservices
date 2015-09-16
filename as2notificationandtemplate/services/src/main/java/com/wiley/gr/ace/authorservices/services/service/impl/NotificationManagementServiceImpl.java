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
import java.io.File;
import java.io.Reader;
import java.sql.Clob;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.sql.rowset.serial.SerialClob;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;

import com.wiley.gr.ace.authorservices.email.service.MailSenderService;
import com.wiley.gr.ace.authorservices.model.NotificationDataObj;
import com.wiley.gr.ace.authorservices.model.NotificationDetails;
import com.wiley.gr.ace.authorservices.model.NotificationObj;
import com.wiley.gr.ace.authorservices.model.NotificationRecipientsObj;
import com.wiley.gr.ace.authorservices.model.NotificationResponse;
import com.wiley.gr.ace.authorservices.model.ScheduleObj;
import com.wiley.gr.ace.authorservices.model.ScheduleTemplateObj;
import com.wiley.gr.ace.authorservices.model.TemplateObj;
import com.wiley.gr.ace.authorservices.persistence.entity.Notification;
import com.wiley.gr.ace.authorservices.persistence.entity.NotificationData;
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

    /** The home path. */
    @Value("${attachmentsDirectory}")
    private String homePath;

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
            String appId = schedule.getAppId();
            if (!StringUtils.isEmpty(appId)) {
                scheduleEntity.setAppId(appId);
            }
            String createdBy = schedule.getCreatedBy();
            if (!StringUtils.isEmpty(createdBy)) {
                scheduleEntity.setCreatedBy(createdBy);
            }
            String createdOn = schedule.getCreatedOn();
            if (!StringUtils.isEmpty(createdOn)) {
                scheduleEntity.setCreatedOn(getDate(createdOn));
            }
            String description = schedule.getDescription();
            if (!StringUtils.isEmpty(description)) {
                scheduleEntity.setDescription(description);
            }
            String id = schedule.getId();
            if (!StringUtils.isEmpty(id)) {
                scheduleEntity.setId(id);
            }
            String lastModifiedOn = schedule.getLastModifiedOn();
            if (!StringUtils.isEmpty(lastModifiedOn)) {
                scheduleEntity.setLastModifiedOn(getDate(lastModifiedOn));
            }
            String modifiedBy = schedule.getModifiedBy();
            if (!StringUtils.isEmpty(modifiedBy)) {
                scheduleEntity.setModifiedBy(modifiedBy);
            }
            ScheduleTemplateObj scheduleTemplateObj = schedule
                    .getScheduleTemplate();
            if (!StringUtils.isEmpty(scheduleTemplateObj)) {
                ScheduleTemplate scheduleTemplate = getScheduleTemplateEntity(
                        scheduleTemplateObj, schedule.getId(),
                        schedule.getAppId());
                scheduleEntity.setScheduleTemplate(scheduleTemplate);
            }
            return notificationManagementDAO
                    .saveOrUpdateSchedule(scheduleEntity);
        } else {
            return false;
        }
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
            String appId = scheduleObj.getAppId();
            if (!StringUtils.isEmpty(appId)) {
                scheduleEntity.setAppId(appId);
            }
            ScheduleTemplateObj scheduleTemplateObj = scheduleObj
                    .getScheduleTemplate();
            if (!StringUtils.isEmpty(scheduleTemplateObj)) {
                scheduleTemplate = getScheduleTemplateEntity(
                        scheduleTemplateObj, scheduleId, applicationId);
                if (!StringUtils.isEmpty(scheduleTemplate)) {
                    scheduleTemplate.setSchedule(scheduleEntity);
                    scheduleEntity.setScheduleTemplate(scheduleTemplate);
                }
            }
            String description = scheduleObj.getDescription();
            if (!StringUtils.isEmpty(description)) {
                scheduleEntity.setDescription(description);
            }
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
                && !StringUtils.isEmpty(scheduleId)) {
            return notificationManagementDAO.deleteSchedule(applicationId,
                    scheduleId);
        } else {
            return false;
        }
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
        String onScreen = scheduleTemplateObj.getOnscreen();
        if (!StringUtils.isEmpty(onScreen)) {
            Template temTemplate = notificationManagementDAO.getTemplate(
                    onScreen, applcationId);
            scheduleTemplate.setTemplateByOnscreenTmpl(temTemplate);
        }
        String email = scheduleTemplateObj.getEmail();
        if (!StringUtils.isEmpty(email)) {
            Template temTemplate = notificationManagementDAO.getTemplate(email,
                    applcationId);
            scheduleTemplate.setTemplateByEmailTmpl(temTemplate);
        }
        Integer delay = scheduleTemplateObj.getDelay();
        if (!StringUtils.isEmpty(delay)) {
            scheduleTemplate.setDelay(delay);
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
        String id = scheduleEntity.getId();
        if (!StringUtils.isEmpty(id)) {
            schedule.setId(id);
        }
        String appId = scheduleEntity.getAppId();
        if (!StringUtils.isEmpty(appId)) {
            schedule.setAppId(appId);
        }
        String description = scheduleEntity.getDescription();
        if (!StringUtils.isEmpty(description)) {
            schedule.setDescription(description);
        }
        String createdBy = scheduleEntity.getCreatedBy();
        if (!StringUtils.isEmpty(createdBy)) {
            schedule.setCreatedBy(createdBy);
        }
        String modifiedBy = scheduleEntity.getModifiedBy();
        if (!StringUtils.isEmpty(modifiedBy)) {
            schedule.setModifiedBy(modifiedBy);
        }
        Date createdOn = scheduleEntity.getCreatedOn();
        if (!StringUtils.isEmpty(createdOn)) {
            schedule.setCreatedOn(createdOn.toString());
        }
        Date lastModifiedOn = scheduleEntity.getLastModifiedOn();
        if (!StringUtils.isEmpty(lastModifiedOn)) {
            schedule.setLastModifiedOn(lastModifiedOn.toString());
        }
        ScheduleTemplate scheduleTemplate = scheduleEntity
                .getScheduleTemplate();
        if (!StringUtils.isEmpty(scheduleTemplate)) {
            schedule.setScheduleTemplate(getScheduleTemplateVO(scheduleTemplate));
        }
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
        Template onScreen = scheduleTemplateEntity.getTemplateByOnscreenTmpl();
        if (!StringUtils.isEmpty(onScreen)) {
            scheduleTemplate.setOnscreen(onScreen.getId());
        }
        Template email = scheduleTemplateEntity.getTemplateByEmailTmpl();
        if (!StringUtils.isEmpty(email)) {
            scheduleTemplate.setEmail(email.getId());
        }
        Integer delay = scheduleTemplateEntity.getDelay();
        if (!StringUtils.isEmpty(delay)) {
            scheduleTemplate.setDelay(delay);
        }
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
        String appId = notificationEntity.getAppId();
        if (!StringUtils.isEmpty(appId)) {
            notification.setAppId(appId);
        }
        Clob content = notificationEntity.getContent();
        if (!StringUtils.isEmpty(content)) {
            notification.setContent(content.toString());
        }
        Integer id = notificationEntity.getId();
        if (!StringUtils.isEmpty(id)) {
            notification.setId(id);
        }
        Set<NotificationRecipients> notificationRecepients = notificationEntity
                .getNotificationRecipientses();
        if (!StringUtils.isEmpty(notificationRecepients)) {
            ArrayList<NotificationRecipientsObj> notificationRecipientsObjs = new ArrayList<NotificationRecipientsObj>();
            for (NotificationRecipients notificationRecipients : notificationRecepients) {
                NotificationRecipientsObj tempNotificationRecipient = getNotificationRecipientsVO(notificationRecipients);
                notificationRecipientsObjs.add(tempNotificationRecipient);
            }
            notification
                    .setNotificationRecipientsObjs(notificationRecipientsObjs);
        }
        String senderEmail = notificationEntity.getSenderEmail();
        if (!StringUtils.isEmpty(senderEmail)) {
            notification.setSenderEmail(senderEmail);
        }
        String senderId = notificationEntity.getSenderId();
        if (!StringUtils.isEmpty(senderId)) {
            notification.setSenderId(senderId);
        }
        String type = notificationEntity.getType();
        if (!StringUtils.isEmpty(type)) {
            notification.setType(type);
        }
        Date sentOn = notificationEntity.getSentOn();
        if (!StringUtils.isEmpty(sentOn)) {
            notification.setSentOn(sentOn);
        }
        Character unread = notificationEntity.getUnread();
        if (!StringUtils.isEmpty(unread)) {
            notification.setUnread(unread);
        }
        TemplateObj tempTemplate = getTemplateVO(notificationEntity
                .getTemplate());
        if (!StringUtils.isEmpty(tempTemplate)) {
            notification.setTemplate(tempTemplate);
        }
        NotificationData notificationData = notificationEntity
                .getNotificationData();
        if (!StringUtils.isEmpty(notificationData)) {
            NotificationDataObj notificationDataObj = new NotificationDataObj();
            notificationDataObj.setDataItemKey(notificationData
                    .getDataItemKey());
            notificationDataObj.setDataItemValue(notificationData
                    .getDataItemValue());
            notification.setNotificationData(notificationDataObj);
        }
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
        System.err.println(templateEntity);
        if (!StringUtils.isEmpty(templateEntity)) {
            String createdBy = templateEntity.getCreatedBy();
            if (!StringUtils.isEmpty(createdBy)) {
                template.setCreatedBy(createdBy);
            }
            String appId = templateEntity.getAppId();
            if (!StringUtils.isEmpty(appId)) {
                template.setAppId(appId);
            }
            Clob body = templateEntity.getBody();
            if (!StringUtils.isEmpty(body)) {
                template.setBody(clobToString(body));
            }
            String description = templateEntity.getDescription();
            if (!StringUtils.isEmpty(description)) {
                template.setDescription(description);
            }
            String modifiedBy = templateEntity.getModifiedBy();
            if (!StringUtils.isEmpty(modifiedBy)) {
                template.setModifiedBy(modifiedBy);
            }
            String tagL1 = templateEntity.getTagl1();
            if (!StringUtils.isEmpty(tagL1)) {
                template.setTagl1(tagL1);
            }
            String tagL2 = templateEntity.getTagl2();
            if (!StringUtils.isEmpty(tagL2)) {
                template.setTagl2(tagL2);
            }
            String id = templateEntity.getId();
            if (!StringUtils.isEmpty(id)) {
                template.setId(id);
            }
            /*
             * template.setLastModifiedOn(templateEntity.getLastModifiedOn());
             * template.setCreatedOn(templateEntity.getCreatedOn());
             */
        }
        return template;
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
                && !StringUtils.isEmpty(notificationId)) {
            notification = getNotificationVO(notificationManagementDAO
                    .getNotification(applicationId, notificationId));
        }
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
                && !StringUtils.isEmpty(notificationId)) {
            isSet = notificationManagementDAO.setNotificationFlag(
                    applicationId, notificationId);
        }
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
     * @param tagL1
     *            the tag l1
     * @param tagL2
     *            the tag l2
     * @param itemKey
     *            the item key
     * @param itemValue
     *            the item value
     * @return the notification history
     * @throws Exception
     *             the exception
     */
    @Override
    public final List<NotificationObj> getNotificationHistory(
            final String applicationId, final String from, final String to,
            final String type, final String offset, final String limit,
            final String unreadFlag, final String tagL1, final String tagL2,
            final String itemKey, final String itemValue) throws Exception {
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
                    if (!from.equalsIgnoreCase(n.getSenderEmail())) {
                        notificationList.remove(n);
                    }
                }

            }
            if (!StringUtils.isEmpty(to)) {
                for (NotificationObj n : notificationList) {
                    for (NotificationRecipients recipients : notificationManagementDAO
                            .getNotificationRecipients(n.getId())) {
                        if ("TO".equalsIgnoreCase(recipients.getType())) {
                            notificationRecipients = getNotificationRecipientsVO(recipients);
                            if (!to.equalsIgnoreCase(notificationRecipients
                                    .getUserId())) {
                                notificationList.remove(n);
                            }
                        }
                    }
                }
            }
            if (!StringUtils.isEmpty(type)) {
                for (NotificationObj n : notificationList) {
                    if (!type.equalsIgnoreCase(n.getType())) {
                        notificationList.remove(n);
                    }
                }
            }
            if (!StringUtils.isEmpty(unreadFlag)) {
                for (NotificationObj n : notificationList) {
                    if (!unreadFlag.equalsIgnoreCase(n.getUnread().toString())) {
                        notificationList.remove(n);
                    }
                }
            }
            if (!StringUtils.isEmpty(tagL1)) {
                for (NotificationObj n : notificationList) {
                    if (!tagL1.equalsIgnoreCase(n.getTemplate().getTagl1())) {
                        notificationList.remove(n);
                    }
                }
            }
            if (!StringUtils.isEmpty(tagL2)) {
                for (NotificationObj n : notificationList) {
                    if (!tagL2.equalsIgnoreCase(n.getTemplate().getTagl2())) {
                        notificationList.remove(n);
                    }
                }
            }
            if (!StringUtils.isEmpty(itemKey)) {
                for (NotificationObj n : notificationList) {
                    NotificationDataObj notificationData = n
                            .getNotificationData();
                    if (!StringUtils.isEmpty(notificationData)
                            && !itemKey.equalsIgnoreCase(notificationData
                                    .getDataItemKey())) {
                        notificationList.remove(n);
                    }
                }

            }
            if (!StringUtils.isEmpty(itemValue)) {
                for (NotificationObj n : notificationList) {
                    NotificationDataObj notificationData = n
                            .getNotificationData();
                    if (!StringUtils.isEmpty(notificationData)
                            && !itemValue.equalsIgnoreCase(notificationData
                                    .getDataItemValue())) {
                        notificationList.remove(n);
                    }

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
        File[] files = notificationDetails.getAttachments();
        String path = null;
        if (!StringUtils.isEmpty(files)) {
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
            String folderName = sdf.format(date);
            path = homePath + folderName + "/";
            File directory = new File(path);
            boolean isCreated = directory.mkdirs();
            if (isCreated) {
                for (File file : files) {
                    FileUtils.copyFileToDirectory(file, directory);
                }
                files=directory.listFiles();
            }
        }
        mailSenderService.sendEmail(notificationDetails.getFrom(),
                notificationDetails.getTo(), notificationDetails.getCcList(),
                notificationDetails.getBccList(), templateObj.getDescription(),
                templateObj.getBody(), files);
        NotificationObj notificationObj = new NotificationObj();

        notificationObj.setAppId(applicationId);
        notificationObj.setContent(templateObj.getBody());
        notificationObj.setSenderEmail(notificationDetails.getFrom());
        notificationObj.setTemplate(templateObj);
        notificationObj.setType("email");
        notificationObj.setUnread('n');
        notificationObj.setSentOn(new Date());
        notificationObj.setNotificationData(notificationDetails
                .getNotificationData());
        notificationObj.setAttachmentaPath(path);

        ArrayList<NotificationRecipientsObj> recipientList = new ArrayList<NotificationRecipientsObj>();
        ArrayList<String> toList = notificationDetails.getTo();
        if (!StringUtils.isEmpty(toList)) {
            for (String toEmail : toList) {
                NotificationRecipientsObj tempNotificationRecipientsObj = new NotificationRecipientsObj();
                tempNotificationRecipientsObj.setEmail(toEmail);
                tempNotificationRecipientsObj.setRecipientType("TO");
                recipientList.add(tempNotificationRecipientsObj);
            }
        }
        ArrayList<String> ccList = notificationDetails.getCcList();
        if (!StringUtils.isEmpty(ccList)) {
            for (String ccEmail : ccList) {
                NotificationRecipientsObj tempNotificationRecipientsObj = new NotificationRecipientsObj();
                tempNotificationRecipientsObj.setEmail(ccEmail);
                tempNotificationRecipientsObj.setRecipientType("CC");
                recipientList.add(tempNotificationRecipientsObj);
            }
        }
        ArrayList<String> bccList = notificationDetails.getBccList();
        if (!StringUtils.isEmpty(bccList)) {
            for (String bccEmail : bccList) {
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
        String path = notification.getAttachmentPath();
        File[] attachments = null;
        if (!StringUtils.isEmpty(path)) {
            attachments = new File(path).listFiles();
        }
        if (!StringUtils.isEmpty(attachments)) {
            Date dateForFolder = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
            String folderName = sdf.format(dateForFolder);
            path = homePath + folderName + "/";
            File uploadDirectory = new File(path);
            boolean isCreated = uploadDirectory.mkdirs();
            if (isCreated) {
                for (File attachment : attachments) {
                    FileUtils.copyFileToDirectory(attachment, uploadDirectory);
                }
            }
        }
        mailSenderService.sendEmail(notification.getSenderEmail(),
                toRecipients, ccRecipients, bccRecipients,
                templateObj.getDescription(), templateObj.getBody(),
                attachments);
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
     * @param notificationRecipientsObjList
     *            the notification recipients obj list
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
        notification.setContent(new SerialClob(notificationObj.getContent()
                .toCharArray()));
        notification.setSenderEmail(notificationObj.getSenderEmail());
        notification.setSentOn(notificationObj.getSentOn());
        NotificationData notificationData = new NotificationData();
        NotificationDataObj notificationDataObj = notificationObj
                .getNotificationData();
        notificationData.setDataItemKey(notificationDataObj.getDataItemKey());
        notificationData.setDataItemValue(notificationDataObj
                .getDataItemValue());
        notificationData.setNotification(notification);
        notification.setNotificationData(notificationData);
        Template template = new Template();
        TemplateObj templateObj = notificationObj.getTemplate();
        template.setAppId(templateObj.getAppId());
        template.setBody(new SerialClob(templateObj.getBody().toCharArray()));
        template.setDescription(templateObj.getDescription());
        template.setId(templateObj.getId());
        notification.setTemplate(template);
        notification.setAttachmentPath(notificationObj.getAttachmentaPath());
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

    /**
     * Clob to string.
     *
     * @param data
     *            the data
     * @return the string
     * @throws Exception
     *             the exception
     */
    private String clobToString(final Clob data) throws Exception {
        Reader reader = null;
        BufferedReader br = null;
        StringBuilder sb;
        try {
            sb = new StringBuilder();
            reader = data.getCharacterStream();
            br = new BufferedReader(reader);
            String line;
            while (null != (line = br.readLine())) {
                sb.append(line);
            }
        } finally {
            reader.close();
            br.close();
        }
        return sb.toString();
    }
}
