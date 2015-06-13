package com.wiley.gr.ace.authorservices.persistence.services.impl;

import static com.wiley.gr.ace.authorservices.persistence.connection.HibernateConnection.getSessionFactory;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.wiley.gr.ace.authorservices.persistence.entity.Template;
import com.wiley.gr.ace.authorservices.persistence.services.TemplateManagementDAO;

public class TemplateManagementDAOImpl implements TemplateManagementDAO {

	@Override
	public void getTags() {
		// TODO Auto-generated method stub

	}

	@Override
	public Template getTemplate(String templateId, String applicationId) {
		// TODO Auto-generated method stub
		Session session = null;
		Template template = null;
		try {
			session = getSessionFactory().openSession();
			String hql = "from Template where id=:templateId and app_id=:applicationId";
			template = (Template) session.createQuery(hql)
					.setString("templateId", templateId)
					.setString("applicationId", applicationId).list().get(0);
		} finally {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
		return template;
	}

	@Override
	public boolean insertId(String id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateId(String id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteTemplate(String templateId, String applicationId) {
		// TODO Auto-generated method stub
		Session session = null;
		try {
			session = getSessionFactory().openSession();
			Transaction txn = session.beginTransaction();
			String hql = "delete from Template where id=:templateId and app_id=:applicationId";
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
			String hql = "from Template where app_id=:applicationId and tagl1 = :tagL1 and tagl2 = :tagL2";
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
