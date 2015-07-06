package com.wiley.gr.ace.authorservices.persistence.services.impl;

import static com.wiley.gr.ace.authorservices.persistence.connection.CrossRefHibernateConnection.getSessionFactory;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.springframework.util.StringUtils;

import com.wiley.gr.ace.authorservices.persistence.entity.ProductPersonRelations;
import com.wiley.gr.ace.authorservices.persistence.entity.ProductRoles;
import com.wiley.gr.ace.authorservices.persistence.entity.Products;
import com.wiley.gr.ace.authorservices.persistence.entity.UserProfile;
import com.wiley.gr.ace.authorservices.persistence.services.CrossRefDAO;

/**
 * The Class CrossRefDAOImpl.
 */
public class CrossRefDAOImpl implements CrossRefDAO {

	/**
	 * Gets the product person relations.
	 *
	 * @param dhId
	 *            the dh id
	 * @return the product person relations
	 * @throws Exception
	 *             the exception
	 */
	@Override
	public final List<ProductPersonRelations> getProductPersonRelations(
			final Integer dhId) throws Exception {
		List<ProductPersonRelations> productPersonRelationList = null;
		Session session = null;
		try {
			session = getSessionFactory().openSession();
			String hql = "from ProductPersonRelations ppr where ppr.products.dhId=:dhId";
			productPersonRelationList = session.createQuery(hql)
					.setInteger("dhId", dhId).list();
		} finally {
			if (!StringUtils.isEmpty(session)) {
				session.flush();
				session.close();
			}
		}
		return productPersonRelationList;
	}

	/**
	 * Gets the product person relations by user id.
	 *
	 * @param userId
	 *            the user id
	 * @return the product person relations by user id
	 * @throws Exception
	 *             the exception
	 */
	@Override
	public final List<ProductPersonRelations> getProductPersonRelationsByUserId(
			final Integer userId) throws Exception {
		List<ProductPersonRelations> productPersonRelationList = null;
		Session session = null;
		try {
			session = getSessionFactory().openSession();
			String hql = "from ProductPersonRelations ppr where ppr.userProfile.userId=:userId";
			productPersonRelationList = session.createQuery(hql)
					.setInteger("userId", userId).list();
		} finally {
			if (!StringUtils.isEmpty(session)) {
				session.flush();
				session.close();
			}
		}
		return productPersonRelationList;
	}

	/**
	 * Gets the product person relations by email addr.
	 *
	 * @param emailAddr
	 *            the email addr
	 * @return the product person relations by email addr
	 * @throws Exception
	 *             the exception
	 */
	@Override
	public final List<ProductPersonRelations> getProductPersonRelationsByEmailAddr(
			final String emailAddr) throws Exception {
		List<ProductPersonRelations> productPersonRelationList = null;
		Session session = null;
		try {
			session = getSessionFactory().openSession();
			String hql = "from ProductPersonRelations ppr where ppr.emailAddr=:emailAddr";
			productPersonRelationList = session.createQuery(hql)
					.setString("emailAddr", emailAddr).list();
		} finally {
			if (!StringUtils.isEmpty(session)) {
				session.flush();
				session.close();
			}
		}
		return productPersonRelationList;
	}

	/**
	 * Gets the user profile by id.
	 *
	 * @param userId
	 *            the user id
	 * @return the user profile by id
	 * @throws Exception
	 *             the exception
	 */
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

	/**
	 * Gets the product roles.
	 *
	 * @param roleCd
	 *            the role cd
	 * @return the product roles
	 * @throws Exception
	 *             the exception
	 */
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

	/**
	 * Gets the products.
	 *
	 * @param dhId
	 *            the dh id
	 * @return the products
	 * @throws Exception
	 *             the exception
	 */
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

	/**
	 * Save or update product person relation.
	 *
	 * @param productPersonRelations
	 *            the product person relations
	 * @return true, if successful
	 * @throws Exception
	 *             the exception
	 */
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

	/**
	 * Delete product person relation.
	 *
	 * @param userId
	 *            the user id
	 * @param email
	 *            the email
	 * @param dhId
	 *            the dh id
	 * @return true, if successful
	 * @throws Exception
	 *             the exception
	 */
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

	/**
	 * Update product person relation.
	 *
	 * @param userId
	 *            the user id
	 * @param email
	 *            the email
	 * @param dhId
	 *            the dh id
	 * @return true, if successful
	 * @throws Exception
	 *             the exception
	 */
	@Override
	public boolean updateProductPersonRelation(int userId, String email,
			int dhId) throws Exception {
		boolean isUpdated = false;
		Session session = null;
		if (!StringUtils.isEmpty(dhId) && !StringUtils.isEmpty(email)
				&& !StringUtils.isEmpty(userId)) {
			try {
				session = getSessionFactory().openSession();
				session.beginTransaction();
				String hql = "from ProductPersonRelations pr where pr.emailAddr = :email and pr.products.dhId = :dhId";
				ProductPersonRelations productPersonRelations = (ProductPersonRelations) session
						.createQuery(hql).setString("email", email)
						.setInteger("dhId", dhId).uniqueResult();
				productPersonRelations
						.setUserProfile(getUserProfileById(userId));
				productPersonRelations.setEmailAddr(null);
				session.saveOrUpdate(productPersonRelations);
				session.getTransaction().commit();
				isUpdated = true;
			} catch (Exception e) {
				isUpdated = false;
			} finally {
				if (!StringUtils.isEmpty(session)) {
					session.flush();
					session.close();
				}
			}
		}
		return isUpdated;
	}

}
