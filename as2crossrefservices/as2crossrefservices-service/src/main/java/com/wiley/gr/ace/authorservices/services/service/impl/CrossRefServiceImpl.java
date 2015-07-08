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

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.wiley.gr.ace.authorservices.model.AuthorDetails;
import com.wiley.gr.ace.authorservices.model.PersonDetails;
import com.wiley.gr.ace.authorservices.model.ProductId;
import com.wiley.gr.ace.authorservices.model.ProductPersonRelationObj;
import com.wiley.gr.ace.authorservices.model.ProductPersonRelationRequest;
import com.wiley.gr.ace.authorservices.persistence.entity.ProductPersonRelations;
import com.wiley.gr.ace.authorservices.persistence.entity.ProductRoles;
import com.wiley.gr.ace.authorservices.persistence.entity.Products;
import com.wiley.gr.ace.authorservices.persistence.entity.UserProfile;
import com.wiley.gr.ace.authorservices.persistence.services.CrossRefDAO;
import com.wiley.gr.ace.authorservices.services.service.CrossRefService;

/**
 * The Class CrossRefServiceImpl.
 * 
 * @author virtusa version 1.0
 */
public class CrossRefServiceImpl implements CrossRefService {

	/** The cross ref dao. */
	@Autowired(required = true)
	CrossRefDAO crossRefDAO;

	/**
	 * Gets the product person relation obj.
	 *
	 * @param dhId
	 *            the dh id
	 * @return the product person relation obj
	 * @throws Exception
	 *             the exception
	 */
	public ProductPersonRelationObj getProductPersonRelationObj(Integer dhId)
			throws Exception {
		ProductPersonRelationObj productPersonRelationObj = new ProductPersonRelationObj();
		PersonDetails correspondingAuthor = new PersonDetails();
		List<PersonDetails> coAuthorList = new ArrayList<PersonDetails>();
		List<ProductPersonRelations> productPersonRelationList = null;
		if (!StringUtils.isEmpty(dhId)) {
			productPersonRelationList = crossRefDAO
					.getProductPersonRelations(dhId);
			if (!StringUtils.isEmpty(productPersonRelationList)) {

				productPersonRelationObj.setDhId(dhId);

				for (ProductPersonRelations productPersonRelations : productPersonRelationList) {
					if ("102".equalsIgnoreCase(productPersonRelations
							.getProductRoles().getProductRoleCd())) {
						PersonDetails tempPersonDetails = new PersonDetails();
						tempPersonDetails.setEmailAddr(productPersonRelations
								.getEmailAddr());
						tempPersonDetails.setUserId(productPersonRelations
								.getUserProfile().getUserId());
						coAuthorList.add(tempPersonDetails);
					} else {
						correspondingAuthor.setEmailAddr(productPersonRelations
								.getEmailAddr());
						correspondingAuthor.setUserId(productPersonRelations
								.getUserProfile().getUserId());
					}
				}

				productPersonRelationObj.setCoAuthorList(coAuthorList);
				productPersonRelationObj
						.setCorrespondingAuthor(correspondingAuthor);
			}
		}

		return productPersonRelationObj;
	}

	/**
	 * Gets the author details by email.
	 *
	 * @param email
	 *            the email
	 * @return the author details by email
	 * @throws Exception
	 *             the exception
	 */
	@Override
	public AuthorDetails getAuthorDetailsByEmail(String email) throws Exception {
		AuthorDetails authorDetails = null;
		if (!StringUtils.isEmpty(email)) {
			List<ProductPersonRelations> productPersonRelationEntityList = crossRefDAO
					.getProductPersonRelationsByEmailAddr(email);
			authorDetails = new AuthorDetails();
			authorDetails.setEmail(email);
			ProductId productId = null;
			List<ProductId> coAuthorList = new ArrayList<ProductId>();
			List<ProductId> authorList = new ArrayList<ProductId>();
			for (ProductPersonRelations p : productPersonRelationEntityList) {
				if ("101".equals(p.getProductRoles().getProductRoleCd())) {
					productId = new ProductId();
					productId.setDhId(p.getProducts().getDhId().toString());
					authorList.add(productId);
				} else {
					productId = new ProductId();
					productId.setDhId(p.getProducts().getDhId().toString());
					coAuthorList.add(productId);
				}
			}
			authorDetails.setCoAuthor(coAuthorList);
			authorDetails.setCorresPondingAuthor(authorList);
		}
		return authorDetails;
	}

	/**
	 * Gets the author details by id.
	 *
	 * @param userId
	 *            the user id
	 * @return the author details by id
	 * @throws Exception
	 *             the exception
	 */
	@Override
	public AuthorDetails getAuthorDetailsById(String userId) throws Exception {
		AuthorDetails authorDetails1 = null;
		if (!StringUtils.isEmpty(userId)) {
			List<ProductPersonRelations> productPersonRelationEntityList = crossRefDAO
					.getProductPersonRelationsByUserId(Integer.valueOf(userId));
			if (StringUtils.isEmpty(productPersonRelationEntityList)) {
				authorDetails1 = new AuthorDetails();
				authorDetails1.setUserId(userId);
				ProductId productId1 = new ProductId();
				List<ProductId> coAuthorList = new ArrayList<ProductId>();
				List<ProductId> AuthorList = new ArrayList<ProductId>();
				for (ProductPersonRelations p : productPersonRelationEntityList) {
					if ("102".equals("p.getProductRoles().getProductRoleCd()")) {
						productId1
								.setDhId(p.getProducts().getDhId().toString());
						coAuthorList.add(productId1);
					} else {
						productId1
								.setDhId(p.getProducts().getDhId().toString());
						AuthorList.add(productId1);
					}
				}
				authorDetails1.setCoAuthor(coAuthorList);
				authorDetails1.setCorresPondingAuthor(AuthorList);
			}
		}
		return authorDetails1;
	}

	/**
	 * Creates the cross ref.
	 *
	 * @param productPersonRelation
	 *            the product person relation
	 * @return true, if successful
	 * @throws Exception
	 *             the exception
	 */
	@Override
	public boolean createCrossRef(
			ProductPersonRelationRequest productPersonRelation)
			throws Exception {
		boolean isCreated = false;
		if (!StringUtils.isEmpty(productPersonRelation)) {
			ProductPersonRelations productPersonRelations = getProductPersonRelationsEntity(productPersonRelation);
			isCreated = crossRefDAO
					.saveOrUpdateProductPersonRelation(productPersonRelations);
		}
		return isCreated;
	}

	/**
	 * Update cross ref.
	 *
	 * @param productPersonRelation
	 *            the product person relation
	 * @return true, if successful
	 * @throws Exception
	 *             the exception
	 */
	@Override
	public boolean updateCrossRef(
			ProductPersonRelationRequest productPersonRelation)
			throws Exception {

		boolean isUpdated = false;
		if (!StringUtils.isEmpty(productPersonRelation)) {
			isUpdated = crossRefDAO.updateProductPersonRelation(
					Integer.parseInt(productPersonRelation.getUserId()),
					productPersonRelation.getEmail(),
					Integer.parseInt(productPersonRelation.getDhId()));
		}
		return isUpdated;
	}

	/**
	 * Delete cross ref.
	 *
	 * @param productPersonRelation
	 *            the product person relation
	 * @return true, if successful
	 * @throws Exception
	 *             the exception
	 */
	@Override
	public boolean deleteCrossRef(
			ProductPersonRelationRequest productPersonRelation)
			throws Exception {
		boolean isDeleted = false;
		if (!StringUtils.isEmpty(productPersonRelation)) {
			isDeleted = crossRefDAO.deleteProductPersonRelation(
					Integer.parseInt(productPersonRelation.getUserId()),
					productPersonRelation.getEmail(),
					Integer.parseInt(productPersonRelation.getDhId()));
		}
		return isDeleted;

	}

	/**
	 * Gets the product person relations entity.
	 *
	 * @param productPersonRelations
	 *            the product person relations
	 * @return the product person relations entity
	 * @throws Exception
	 *             the exception
	 */
	private ProductPersonRelations getProductPersonRelationsEntity(
			ProductPersonRelationRequest productPersonRelations)
			throws Exception {
		ProductPersonRelations productPersonRelationsEntity = null;
		if (!StringUtils.isEmpty(productPersonRelations)) {
			productPersonRelationsEntity = new ProductPersonRelations();
			String userId = productPersonRelations.getUserId();
			String email = productPersonRelations.getEmail();
			String dhId = productPersonRelations.getDhId();
			String roleCd = productPersonRelations.getRole();
			if (!StringUtils.isEmpty(userId)) {
				UserProfile user = crossRefDAO.getUserProfileById(Integer
						.parseInt(userId));
				productPersonRelationsEntity.setUserProfile(user);
			} else if (!StringUtils.isEmpty(email)) {
				productPersonRelationsEntity.setEmailAddr(email);
			}
			if (!StringUtils.isEmpty(dhId)) {
				Products product = crossRefDAO.getProducts(Integer
						.parseInt(dhId));
				productPersonRelationsEntity.setProducts(product);
			}
			if (!StringUtils.isEmpty(roleCd)) {
				ProductRoles role = crossRefDAO.getProductRoles(roleCd);
				productPersonRelationsEntity.setProductRoles(role);
			}

		}
		return productPersonRelationsEntity;
	}
}
