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

public class NotificationManagementDAOImpl implements NotificationManagementDAO {

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

	@Override
	public boolean saveOrUpdateSchedule(Schedule schedule,
			ScheduleTemplate scheduleTemplate) throws Exception {
		Session session = null;
		try {
			session = getSessionFactory().openSession();
			session.beginTransaction();
			session.saveOrUpdate(schedule);
			session.saveOrUpdate(scheduleTemplate);
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

	@Override
	public boolean deleteSchedule(String applicationId, String scheduleId)
			throws Exception {
		Session session = null;
		boolean isDeleted = false;
		Schedule schedule = new Schedule();
		ScheduleTemplate scheduleTemplate = new ScheduleTemplate();
		scheduleTemplate.setScheduleId(scheduleId);
		schedule.setId(scheduleId);
		schedule.setAppId(applicationId);
		if (!StringUtils.isEmpty(applicationId)
				&& !StringUtils.isEmpty(applicationId)) {
			try {
				session = getSessionFactory().openSession();
				session.beginTransaction();
				session.delete(scheduleTemplate);
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
			}
		}
		return scheduleList;
	}

	@Override
	public Template getTemplate(String templateId) throws Exception {

		Session session = null;
		Template template = null;
		if (!StringUtils.isEmpty(templateId)) {

			session = getSessionFactory().openSession();
			String hql = "from Template t where t.id=:templateId";
			try {
				template = (Template) session.createQuery(hql)
						.setString("templateId", templateId).list().get(0);

			} finally {
				if (session != null) {
					session.flush();
					session.close();
				}
			}
		}
		return template;
	}

	@Override
	public Notification getNotification(String applicationId,
			String notificationId) throws Exception {
		Notification notification = null;
		if (!StringUtils.isEmpty(applicationId)
				&& !StringUtils.isEmpty(notificationId)) {
			Session session = getSessionFactory().openSession();
			String hql = "from Notification n where n.id = :notificationId and n.appId = :applicationId";
			notification = (Notification) session.createQuery(hql)
					.setString("notificationId", notificationId)
					.setString("applicationId", applicationId).list().get(0);
		}
		return notification;
	}

	@Override
	public boolean setNotificationFlag(String applicationId,
			String notificationId) throws Exception {
		boolean isSet = false;
		if (!StringUtils.isEmpty(applicationId)
				&& !StringUtils.isEmpty(notificationId)) {
			Notification notification = getNotification(applicationId,
					notificationId);
			String unread = notification.getUnread().toString();
			if (unread.equalsIgnoreCase("y")) {
				try {
					Session session = getSessionFactory().openSession();
					session.beginTransaction();
					notification.setUnread('n');
					session.save(notification);
					session.getTransaction().commit();
					isSet = true;
				} catch (Exception e) {
					isSet = false;
				}
			}
		}
		return isSet;
	}

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

	@Override
	public NotificationRecipients getNotificationRecipients(
			String notificationId) throws Exception {
		NotificationRecipients notificationRecipients = null;
		if (!StringUtils.isEmpty(notificationId)) {
			Session session = getSessionFactory().openSession();
			String hql = "from NotificationRecepients nr where nr.notificationId = :notificationId";
			notificationRecipients = (NotificationRecipients) session
					.createQuery(hql)
					.setString("notificationId", notificationId).list().get(0);
		}
		return notificationRecipients;
	}

	@Override
	public boolean createNotificationHistory(String applicationId,
			String templateId, String senderEmail, String recipientEmail,
			String content, String type) throws Exception {
		Notification notification = new Notification();
		NotificationRecipients notificationRecipients = new NotificationRecipients();
		
		notificationRecipients.setEmail(recipientEmail);
		notification.setAppId(applicationId);
		
		return false;
	}
}
