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
import org.hibernate.Transaction;
import org.springframework.util.StringUtils;

import com.wiley.gr.ace.authorservices.persistence.entity.Template;
import com.wiley.gr.ace.authorservices.persistence.services.TemplateManagementDAO;

/**
 * The Class TemplateManagementDAOImpl.
 * 
 * @author virtusa version 1.0
 */
public class TemplateManagementDAOImpl implements TemplateManagementDAO {

	/**
	 * Gets the template tags.
	 *
	 * @param applicationId
	 *            the application id
	 * @return the template tags
	 * @throws Exception
	 *             the exception
	 */
	@Override
	public List<Template> getTemplateTags(String applicationId)
			throws Exception {
		Session session = null;
		List<Template> templateEntityList = null;
		try {
			if (!StringUtils.isEmpty(applicationId)) {
				session = getSessionFactory().openSession();
				String hql = "from Template t where t.appId=:applicationId";
				templateEntityList = session.createQuery(hql)
						.setString("applicationId", applicationId).list();
			}
		} finally {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
		return templateEntityList;

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
	public Template getTemplate(String templateId, String applicationId)
			throws Exception {

		Session session = null;
		Template template = null;
		if (!StringUtils.isEmpty(templateId)
				&& !StringUtils.isEmpty(applicationId)) {

			session = getSessionFactory().openSession();
			String hql = "from Template t where t.id=:templateId and t.appId=:applicationId";
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
	 * Save or update template.
	 *
	 * @param template
	 *            the template
	 * @return true, if successful
	 * @throws Exception
	 *             the exception
	 */
	@Override
	public boolean saveOrUpdateTemplate(Template template) throws Exception {
		Session session = null;
		boolean isSaved = false;
		try {
			session = getSessionFactory().openSession();
			session.beginTransaction();
			session.saveOrUpdate(template);
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
	 * Delete template.
	 *
	 * @param applicationId
	 *            the application id
	 * @param templateId
	 *            the template id
	 * @return true, if successful
	 * @throws Exception
	 *             the exception
	 */
	@Override
	public boolean deleteTemplate(String applicationId, String templateId)
			throws Exception {
		Session session = null;
		boolean deleteStatus = false;
		if (!StringUtils.isEmpty(templateId)) {
			Template template = new Template();
			template.setId(templateId);
			template.setAppId(applicationId);
			try {
				session = getSessionFactory().openSession();
				Transaction txn = session.beginTransaction();
				session.delete(template);
				txn.commit();
				deleteStatus = true;
			} catch (Exception e) {
				deleteStatus = false;
				if (null != session)
					session.getTransaction().rollback();
			} finally {
				if (session != null) {
					session.flush();
					session.close();
				}
			}
		}

		return deleteStatus;
	}

	/**
	 * Search template.
	 *
	 * @param applicationId
	 *            the application id
	 * @param tagL1
	 *            the tag l1
	 * @param tagL2
	 *            the tag l2
	 * @return the template
	 * @throws Exception
	 *             the exception
	 */
	@Override
	public Template searchTemplate(String applicationId, String tagL1,
			String tagL2) throws Exception {
		Session session = null;
		Template template = null;
		try {
			session = getSessionFactory().openSession();
			String hql = "from Template t where t.appId=:applicationId and t.tagl1 = :tagL1 and t.tagl2 = :tagL2";
			template = (Template) session.createQuery(hql)
					.setString("applicationId", applicationId)
					.setString("tagL1", tagL1).setString("tagL2", tagL2).list()
					.get(0);
		} finally {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
		return template;
	}

}
