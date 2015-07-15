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
package com.wiley.gr.ace.authorservices.persistence.services.impl;

import static com.wiley.gr.ace.authorservices.persistence.connection.NotificationTemplateHibernateConnection.getSessionFactory;

import java.util.List;

import org.hibernate.Session;
import org.springframework.util.StringUtils;

import com.wiley.gr.ace.authorservices.persistence.entity.Notification;
import com.wiley.gr.ace.authorservices.persistence.entity.NotificationRecipients;
import com.wiley.gr.ace.authorservices.persistence.entity.Schedule;
import com.wiley.gr.ace.authorservices.persistence.entity.ScheduleTemplate;
import com.wiley.gr.ace.authorservices.persistence.entity.Template;
import com.wiley.gr.ace.authorservices.persistence.services.NotificationManagementDAO;

/**
 * The Class NotificationManagementDAOImpl.
 * 
 * @author virtusa version 1.0
 */
public class NotificationManagementDAOImpl implements NotificationManagementDAO {

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
    public final Schedule getSchedule(final String applicationId,
            final String scheduleId) throws Exception {
        Session session = null;
        Schedule schedule = null;
        if (!StringUtils.isEmpty(applicationId)
                && !StringUtils.isEmpty(applicationId)) {
            try {
                session = getSessionFactory().openSession();
                String hql = "from Schedule s where s.appId = :applicationId and s.id = :scheduleId";
                schedule = (Schedule) session.createQuery(hql)
                        .setString("applicationId", applicationId)
                        .setString("scheduleId", scheduleId).list().get(0);

            } finally {
                if (!StringUtils.isEmpty(session)) {
                    session.flush();
                    session.close();
                }
            }
        }
        return schedule;
    }

    /**
     * Save or update schedule.
     *
     * @param schedule
     *            the schedule
     * @return true, if successful
     * @throws Exception
     *             the exception
     */
    @Override
    public final boolean saveOrUpdateSchedule(final Schedule schedule)
            throws Exception {
        Session session = null;
        boolean isSaved = false;
        try {
            session = getSessionFactory().openSession();
            session.beginTransaction();
            session.saveOrUpdate(schedule);
            session.getTransaction().commit();
            isSaved = true;

        } catch (Exception e) {
            isSaved = false;
            if (null != session)
                session.getTransaction().rollback();
        } finally {
            if (session != null) {
                session.flush();
                session.close();
            }
        }

        return isSaved;
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
        Session session = null;
        boolean isDeleted = false;
        if (!StringUtils.isEmpty(applicationId)
                && !StringUtils.isEmpty(applicationId)) {
            try {
                Schedule schedule = getSchedule(applicationId, scheduleId);
                session = getSessionFactory().openSession();
                session.beginTransaction();
                session.delete(schedule);
                session.getTransaction().commit();
                isDeleted = true;
            } catch (Exception e) {
                isDeleted = false;
                if (null != session)
                    session.getTransaction().rollback();
            } finally {
                if (session != null) {
                    session.flush();
                    session.close();
                }
            }
        }
        return isDeleted;
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
    public final List<Schedule> templateLookup(final String applicationId,
            final String templateId, final String type) throws Exception {
        Session session = null;
        List<Schedule> scheduleList = null;
        if (!StringUtils.isEmpty(applicationId)
                && !StringUtils.isEmpty(templateId)
                && ("onscreen".equalsIgnoreCase(type) || "email"
                        .equalsIgnoreCase(type))) {
            try {
                session = getSessionFactory().openSession();
                String hql = "from Schedule s where s.appId = :applicationId";
                scheduleList = session.createQuery(hql)
                        .setString("applicationId", applicationId).list();
                for (Schedule s : scheduleList) {
                    hql = "from ScheduleTemplate st where st.scheduleId = :scheduleId";
                    ScheduleTemplate scheduleTemplate = (ScheduleTemplate) session
                            .createQuery(hql)
                            .setString("scheduleId", s.getId()).list().get(0);
                    Template template = null;
                    if ("onscreen".equalsIgnoreCase(type)) {
                        template = scheduleTemplate.getTemplateByOnscreenTmpl();
                        if (!templateId.equalsIgnoreCase(template.getId()))
                            scheduleList.remove(s);
                    } else if ("email".equalsIgnoreCase(type)) {
                        template = scheduleTemplate.getTemplateByOnscreenTmpl();
                        if (!templateId.equalsIgnoreCase(template.getId()))
                            scheduleList.remove(s);
                    }
                }
            } catch (Exception e) {
                scheduleList = null;
            } finally {
                if (session != null) {
                    session.flush();
                    session.close();
                }
            }
        }
        return scheduleList;
    }

    /**
     * Gets the template.
     *
     * @param templateId
     *            the template id
     * @param applicationId
     *            the application id
     * @return the template
     * @throws Exception
     *             the exception
     */
    @Override
    public final Template getTemplate(final String templateId,
            final String applicationId) throws Exception {

        Session session = null;
        Template template = null;
        if (!StringUtils.isEmpty(templateId)) {

            session = getSessionFactory().openSession();
            String hql = "from Template t where t.id=:templateId and t.appId = :applicationId";
            try {
                template = (Template) session.createQuery(hql)
                        .setString("templateId", templateId)
                        .setString("applicationId", applicationId).list()
                        .get(0);

            } finally {
                if (session != null) {
                    session.flush();
                    session.close();
                }
            }
        }
        return template;
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
    public final Notification getNotification(final String applicationId,
            final Integer notificationId) throws Exception {
        Notification notification = null;
        Session session = null;
        if (!StringUtils.isEmpty(applicationId)
                && !StringUtils.isEmpty(notificationId)) {
            try {
                session = getSessionFactory().openSession();
                String hql = "from Notification n where n.id = :notificationId and n.appId = :applicationId";
                notification = (Notification) session.createQuery(hql)
                        .setInteger("notificationId", notificationId)
                        .setString("applicationId", applicationId).list()
                        .get(0);
            } finally {
                if (session != null) {
                    session.flush();
                    session.close();
                }
            }
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
        boolean isSet = false;
        Session session = null;
        if (!StringUtils.isEmpty(applicationId)
                && !StringUtils.isEmpty(notificationId)) {
            Notification notification = getNotification(applicationId,
                    notificationId);
            String unread = notification.getUnread().toString();
            if (!"n".equalsIgnoreCase(unread)) {
                try {

                    session = getSessionFactory().openSession();
                    session.beginTransaction();
                    notification.setUnread('n');
                    session.saveOrUpdate(notification);
                    session.getTransaction().commit();
                    isSet = true;

                } catch (Exception e) {
                    isSet = false;
                    if (null != session)
                        session.getTransaction().rollback();
                } finally {
                    if (!StringUtils.isEmpty(session)) {
                        session.flush();
                        session.close();
                    }
                }

            }

        }
        return isSet;
    }

    /**
     * Gets the notification list.
     *
     * @param applicationId
     *            the application id
     * @return the notification list
     * @throws Exception
     *             the exception
     */
    @Override
    public final List<Notification> getNotificationList(
            final String applicationId) throws Exception {
        List<Notification> notificationList = null;
        Session session = null;
        if (!StringUtils.isEmpty(applicationId)) {
            try {
                session = getSessionFactory().openSession();
                String hql = "from Notification n where n.appId = :applicationId";
                notificationList = session.createQuery(hql)
                        .setString("applicationId", applicationId).list();

            } finally {
                if (!StringUtils.isEmpty(session)) {
                    session.flush();
                    session.close();
                }
            }
        }
        return notificationList;
    }

    /**
     * Gets the notification recipients.
     *
     * @param notificationId
     *            the notification id
     * @return the notification recipients
     * @throws Exception
     *             the exception
     */
    @Override
    public final NotificationRecipients getNotificationRecipients(
            final Integer notificationId) throws Exception {
        NotificationRecipients notificationRecipients = null;
        Session session = null;
        if (!StringUtils.isEmpty(notificationId)) {
            try {
                session = getSessionFactory().openSession();
                String hql = "from NotificationRecipients nr where nr.notificationId = :notificationId";
                notificationRecipients = (NotificationRecipients) session
                        .createQuery(hql)
                        .setInteger("notificationId", notificationId).list()
                        .get(0);
            } finally {
                if (!StringUtils.isEmpty(session)) {
                    session.flush();
                    session.close();
                }
            }
        }
        return notificationRecipients;
    }

    /**
     * Creates the notification history.
     *
     * @param notification
     *            the notification
     * @return true, if successful
     * @throws Exception
     *             the exception
     */
    @Override
    public final Integer createNotificationHistory(
            final Notification notification) throws Exception {
        Session session = null;
        Integer notificationId = null;
        try {
            session = getSessionFactory().openSession();
            session.beginTransaction();
            session.save(notification);
            session.getTransaction().commit();
            notificationId = notification.getId();

        } catch (Exception e) {
            if (null != session)
                session.getTransaction().rollback();
        } finally {

            if (session != null) {
                session.flush();
                session.close();
            }
        }

        return notificationId;
    }

    /**
     * Gets the schedule template entity.
     *
     * @param scheduleId
     *            the schedule id
     * @return the schedule template entity
     * @throws Exception
     *             the exception
     */
    @Override
    public final ScheduleTemplate getScheduleTemplateEntity(
            final String scheduleId) throws Exception {
        ScheduleTemplate scheduleTemplate = null;
        Session session = null;
        if (!StringUtils.isEmpty(scheduleId)) {
            try {
                session = getSessionFactory().openSession();
                String hql = "from ScheduleTemplate st where st.scheduleId = :scheduleId";
                scheduleTemplate = (ScheduleTemplate) session.createQuery(hql)
                        .setString("scheduleId", scheduleId).list().get(0);
            } finally {
                if (!StringUtils.isEmpty(session)) {
                    session.flush();
                    session.close();
                }
            }
        }
        return scheduleTemplate;

    }
}
