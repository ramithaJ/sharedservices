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

import static com.wiley.gr.ace.authorservices.persistence.connection.CrossRefHibernateConnection.getSessionFactory;

import java.util.List;

import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.wiley.gr.ace.authorservices.persistence.entity.ProductPersonRelations;
import com.wiley.gr.ace.authorservices.persistence.entity.ProductRoles;
import com.wiley.gr.ace.authorservices.persistence.entity.Products;
import com.wiley.gr.ace.authorservices.persistence.entity.UserProfile;
import com.wiley.gr.ace.authorservices.persistence.services.CrossRefDAO;

/**
 * The Class CrossRefDAOImpl.
 * 
 * @author virtusa version 1.0
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
	public final UserProfile getUserProfileById(final int userId)
			throws Exception {
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
	public final ProductRoles getProductRoles(final String roleCd)
			throws Exception {
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
	public final Products getProducts(final int dhId) throws Exception {
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
	public final boolean saveOrUpdateProductPersonRelation(
			final ProductPersonRelations productPersonRelations)
			throws Exception {
		boolean isCreated = false;
		Session session = null;
		if (!StringUtils.isEmpty(productPersonRelations)) {
			try {
				ProductPersonRelations prrelations = null;
				if (!StringUtils.isEmpty(productPersonRelations
						.getUserProfile())) {
					int userId = productPersonRelations.getUserProfile()
							.getUserId();
					int dhId = productPersonRelations.getProducts().getDhId();
					prrelations = getCrossRefByUser(userId, dhId);
				} else {
					String email = productPersonRelations.getEmailAddr();
					int dhId = productPersonRelations.getProducts().getDhId();
					prrelations = getCrossRefByEmail(email, dhId);
				}

				if (StringUtils.isEmpty(prrelations)) {
					session = getSessionFactory().openSession();
					session.beginTransaction();
					session.saveOrUpdate(productPersonRelations);
					session.getTransaction().commit();
					isCreated = true;
				} else {
					isCreated = false;
				}
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
	public final boolean deleteProductPersonRelation(final Integer userId,
			final String email, final int dhId) throws Exception {
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
	public final boolean updateProductPersonRelation(final int userId,
			final String email, final int dhId) throws Exception {
		boolean isUpdated = false;
		Session session = null;
		if (!StringUtils.isEmpty(dhId) && !StringUtils.isEmpty(email)
				&& !StringUtils.isEmpty(userId)) {
			try {
				ProductPersonRelations productPersonRelations = getCrossRefByEmail(
						email, dhId);
				session = getSessionFactory().openSession();
				session.beginTransaction();
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

	/**
	 * Gets the cross ref by user.
	 *
	 * @param userId
	 *            the user id
	 * @param dhId
	 *            the dh id
	 * @return the cross ref by user
	 */
	private ProductPersonRelations getCrossRefByUser(Integer userId,
			Integer dhId) {
		ProductPersonRelations productPersonRelations = null;
		Session session = null;
		try {
			session = getSessionFactory().openSession();
			String hql = "from ProductPersonRelations ppr where ppr.userProfile.userId = :userId and ppr.products.dhId=:dhId";
			productPersonRelations = (ProductPersonRelations) session
					.createQuery(hql).setInteger("userId", userId)
					.setInteger("dhId", dhId).uniqueResult();
		} finally {
			if (!StringUtils.isEmpty(session)) {
				session.flush();
				session.close();
			}
		}
		return productPersonRelations;
	}

	/**
	 * Gets the cross ref by email.
	 *
	 * @param email
	 *            the email
	 * @param dhId
	 *            the dh id
	 * @return the cross ref by email
	 */
	private ProductPersonRelations getCrossRefByEmail(String email, Integer dhId) {
		ProductPersonRelations productPersonRelations = null;
		Session session = null;
		try {
			session = getSessionFactory().openSession();
			String hql = "from ProductPersonRelations pr where pr.emailAddr = :email and pr.products.dhId = :dhId";
			productPersonRelations = (ProductPersonRelations) session
					.createQuery(hql).setString("email", email)
					.setInteger("dhId", dhId).uniqueResult();
		} finally {
			if (!StringUtils.isEmpty(session)) {
				session.flush();
				session.close();
			}
		}
		return productPersonRelations;
	}
}
