/*
 * 
 */
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
	public Schedule getSchedule(String applicationId, String scheduleId)
			throws Exception {
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
	 * @param scheduleTemplate
	 *            the schedule template
	 * @return true, if successful
	 * @throws Exception
	 *             the exception
	 */
	@Override
	public boolean saveOrUpdateSchedule(Schedule schedule) throws Exception {
		Session session = null;
		try {
			session = getSessionFactory().openSession();
			session.beginTransaction();
			session.saveOrUpdate(schedule);
			session.getTransaction().commit();
			return true;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
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
	public boolean deleteSchedule(String applicationId, String scheduleId)
			throws Exception {
		Session session = null;
		boolean isDeleted = false;
		Schedule schedule = getSchedule(applicationId, scheduleId);
		if (!StringUtils.isEmpty(applicationId)
				&& !StringUtils.isEmpty(applicationId)) {
			try {
				session = getSessionFactory().openSession();
				session.beginTransaction();
				session.delete(schedule);
				session.getTransaction().commit();
				isDeleted = true;
			} catch (Exception e) {
				isDeleted = false;
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
	public List<Schedule> templateLookup(String applicationId,
			String templateId, String type) throws Exception {
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
	 * Save or update schedule.
	 *
	 * @param schedule
	 *            the schedule
	 * @param scheduleTemplate
	 *            the schedule template
	 * @param applicationId
	 *            the application id
	 * @return true, if successful
	 * @throws Exception
	 *             the exception
	 */
	@Override
	public Template getTemplate(String templateId, String applicationId)
			throws Exception {

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

			} catch (Exception e) {
				e.printStackTrace();
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
	public Notification getNotification(String applicationId,
			Integer notificationId) throws Exception {
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
	public boolean setNotificationFlag(String applicationId,
			Integer notificationId) throws Exception {
		boolean isSet = false;
		Session session = null;
		if (!StringUtils.isEmpty(applicationId)
				&& !StringUtils.isEmpty(notificationId)) {
			Notification notification = getNotification(applicationId,
					notificationId);
			String unread = notification.getUnread().toString();
			if (!unread.equalsIgnoreCase("n")) {
				try {

					session = getSessionFactory().openSession();
					session.beginTransaction();
					notification.setUnread('n');
					session.saveOrUpdate(notification);
					session.getTransaction().commit();
					isSet = true;

				} catch (Exception e) {
					isSet = false;
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
	public List<Notification> getNotificationList(String applicationId)
			throws Exception {
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
	public NotificationRecipients getNotificationRecipients(
			Integer notificationId) throws Exception {
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
	 * @param applicationId
	 *            the application id
	 * @param templateId
	 *            the template id
	 * @param senderEmail
	 *            the sender email
	 * @param recipientEmail
	 *            the recipient email
	 * @param content
	 *            the content
	 * @param type
	 *            the type
	 * @return true, if successful
	 * @throws Exception
	 *             the exception
	 */
	@Override
	public Integer createNotificationHistory(Notification notification)
			throws Exception {
		Session session = null;
		Integer notificationId = null;
		try {
			session = getSessionFactory().openSession();
			session.beginTransaction();
			session.save(notification);
			session.getTransaction().commit();
			notificationId = notification.getId();

		}
		finally {
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
	public ScheduleTemplate getScheduleTemplateEntity(String scheduleId)
			throws Exception {
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
