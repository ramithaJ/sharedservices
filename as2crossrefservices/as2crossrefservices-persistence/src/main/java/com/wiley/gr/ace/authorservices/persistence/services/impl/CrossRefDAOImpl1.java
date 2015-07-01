package com.wiley.gr.ace.authorservices.persistence.services.impl;

import static com.wiley.gr.ace.authorservices.persistence.connection.CrossRefHibernateConnection.getSessionFactory;

import org.hibernate.Session;
import org.springframework.util.StringUtils;

import com.wiley.gr.ace.authorservices.persistence.entity.ProductPersonRelations;
import com.wiley.gr.ace.authorservices.persistence.entity.ProductRoles;
import com.wiley.gr.ace.authorservices.persistence.entity.Products;
import com.wiley.gr.ace.authorservices.persistence.entity.UserProfile;
import com.wiley.gr.ace.authorservices.persistence.services.CrossRefDAO1;

public class CrossRefDAOImpl1 implements CrossRefDAO1 {

	@Override
	public UserProfile getUserProfileById(int userId) throws Exception {
		UserProfile user = null;
		Session session = null;
		if (!StringUtils.isEmpty(userId)) {
			try {
				session = getSessionFactory().openSession();
				String hql = "from UserProfile u where u.id = :userId ";
				user = (UserProfile) session.createQuery(hql)
						.setInteger("userId", userId).uniqueResult();
			} finally {
				if (!StringUtils.isEmpty(session)) {
					session.flush();
					session.close();
				}
			}

		}
		return user;
	}

	@Override
	public ProductRoles getProductRoles(String roleCd) throws Exception {
		ProductRoles roles = null;
		Session session = null;
		if (!StringUtils.isEmpty(roleCd)) {
			try {
				session = getSessionFactory().openSession();
				String hql = "from ProductRoles pr where pr.productRoleCd = :roleCd";
				roles = (ProductRoles) session.createQuery(hql)
						.setString("roleCd", roleCd).uniqueResult();
			} finally {
				if (!StringUtils.isEmpty(session)) {
					session.flush();
					session.close();
				}
			}

		}
		return roles;
	}

	@Override
	public Products getProducts(int dhId) throws Exception {
		Products product = null;
		Session session = null;
		if (!StringUtils.isEmpty(dhId)) {
			try {
				session = getSessionFactory().openSession();
				String hql = "from Products p where p.dhId = :dhId ";
				product = (Products) session.createQuery(hql)
						.setInteger("dhId", dhId).uniqueResult();
			} finally {
				if (!StringUtils.isEmpty(session)) {
					session.flush();
					session.close();
				}
			}

		}
		return product;
	}

	@Override
	public boolean saveOrUpdateProductPersonRelation(
			ProductPersonRelations productPersonRelations) throws Exception {
		boolean isCreated = false;
		Session session = null;
		if (!StringUtils.isEmpty(productPersonRelations)) {
			try {
				session = getSessionFactory().openSession();
				session.beginTransaction();
				session.saveOrUpdate(productPersonRelations);
				session.getTransaction().commit();
				isCreated = true;
			} catch (Exception e) {
				e.printStackTrace();
				isCreated = false;
			} finally {
				if (!StringUtils.isEmpty(session)) {
					session.flush();
					session.close();
				}
			}
		}
		return isCreated;
	}

	@Override
	public boolean deleteProductPersonRelation(int userId, String email,
			int dhId) throws Exception {
		boolean isDeleted = false;
		Session session = null;
		ProductPersonRelations productPersonRelations = null;
		if (!StringUtils.isEmpty(dhId)) {
			try {
				session = getSessionFactory().openSession();
				if (!StringUtils.isEmpty(userId)) {
					String hql = "from ProductPersonRelations pr where pr.userProfile.userId = :userId and pr.products.dhId = :dhId";
					productPersonRelations = (ProductPersonRelations) session
							.createQuery(hql).setInteger("userId", userId)
							.setInteger("dhId", dhId).uniqueResult();
				} else if (!StringUtils.isEmpty(email)) {
					String hql = "from ProductPersonRelations pr where pr.emailAddr = :email and pr.products.dhId = :dhId";
					productPersonRelations = (ProductPersonRelations) session
							.createQuery(hql).setString("email", email)
							.setInteger("dhId", dhId).uniqueResult();
				}
				session.beginTransaction();
				session.delete(productPersonRelations);
				session.getTransaction().commit();
				isDeleted = true;
			} catch (Exception e) {
				e.printStackTrace();
				isDeleted = false;
			} finally {
				if (!StringUtils.isEmpty(session)) {
					session.flush();
					session.close();
				}
			}
		}
		return isDeleted;
	}

}