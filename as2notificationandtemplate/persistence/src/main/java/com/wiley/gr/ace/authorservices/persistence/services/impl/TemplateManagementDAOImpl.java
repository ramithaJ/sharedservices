package com.wiley.gr.ace.authorservices.persistence.services.impl;

import static com.wiley.gr.ace.authorservices.persistence.connection.HibernateConnection.getSessionFactory;

import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.util.StringUtils;

import com.wiley.gr.ace.authorservices.persistence.entity.Template;
import com.wiley.gr.ace.authorservices.persistence.services.TemplateManagementDAO;

public class TemplateManagementDAOImpl implements TemplateManagementDAO {

	@Override
	public List<Template> getTemplateTags(String applicationId) {

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

	@Override
	public Template getTemplate(String templateId, String applicationId) {
		// TODO Auto-generated method stub
		Session session = null;
		Template template = null;
		if (!StringUtils.isEmpty(templateId)
				&& !StringUtils.isEmpty(applicationId)) {

				session = getSessionFactory().openSession();
				String hql = "from Template t where t.id=:templateId and t.appId=:applicationId";
				try{
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

	@Override
	public boolean insertTemplate(Template template) {
		Session session = null;
		try {
			session = getSessionFactory().openSession();
			session.beginTransaction();
			session.persist(template);
			session.getTransaction().commit();
			return true;

		} catch (Exception e) {
			return false;
		} finally {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
	}

	@Override
	public boolean updateTemplate(String templateId, String applicationId,
			Map<String, Object> templateMap) {
		Session session = null;
		try {
			session = getSessionFactory().openSession();
			int result = 0;
			String hql;
			for (Map.Entry<String, Object> entry : templateMap.entrySet()) {
				session.beginTransaction();
				String key = entry.getKey();
				if (key.equalsIgnoreCase("appid"))
					key = "app_id";
				if (key.equalsIgnoreCase("createdby"))
					key = "created_by";
				if (key.equalsIgnoreCase("modifiedby"))
					key = "modified_by";
				if (key.equalsIgnoreCase("createdon"))
					key = "created_on";
				if (key.equalsIgnoreCase("lastmodifiedon"))
					key = "last_modified_on";
				try{
				hql = "update Template t set "
						+ key
						+ " = :value where t.id=:templateId and t.appId = :applicationId";
				result = session.createQuery(hql)
						.setString("value", entry.getValue().toString())
						.setString("templateId", templateId)
						.setString("applicationId", applicationId)
						.executeUpdate();
				
					session.getTransaction().commit();
				} catch (Exception e) {
					e.printStackTrace();
					session.clear();
				}
			}

			System.err.println(result);
			if (result > 0)
				return true;
			else
				return false;
		} finally {
			if (session != null)
				session.flush();
			session.close();
		}

	}

	@Override
	public boolean deleteTemplate(String templateId, String applicationId) {
		// TODO Auto-generated method stub
		Session session = null;
		try {
			session = getSessionFactory().openSession();
			Transaction txn = session.beginTransaction();
			String hql = "delete from Template t where t.id=:templateId and t.appId=:applicationId";
			int result = session.createQuery(hql)
					.setString("templateId", templateId)
					.setString("applicationId", applicationId).executeUpdate();
			txn.commit();
			if (result > 0)
				return true;
			else
				return false;
		} finally {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
	}

	@Override
	public Template searchTemplate(String applicationId, String tagL1,
			String tagL2) {
		// TODO Auto-generated method stub
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
