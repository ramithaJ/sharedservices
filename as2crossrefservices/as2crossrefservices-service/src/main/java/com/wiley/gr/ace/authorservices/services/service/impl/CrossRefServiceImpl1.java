package com.wiley.gr.ace.authorservices.services.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.wiley.gr.ace.authorservices.model.ProductPersonRelationRequest;
import com.wiley.gr.ace.authorservices.persistence.entity.ProductPersonRelations;
import com.wiley.gr.ace.authorservices.persistence.entity.ProductRoles;
import com.wiley.gr.ace.authorservices.persistence.entity.Products;
import com.wiley.gr.ace.authorservices.persistence.entity.UserProfile;
import com.wiley.gr.ace.authorservices.persistence.services.CrossRefDAO1;
import com.wiley.gr.ace.authorservices.services.service.CrossRefService1;

public class CrossRefServiceImpl1 implements CrossRefService1 {

	@Autowired(required = true)
	CrossRefDAO1 crossRefDAO;

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

	@Override
	public boolean updateCrossRef(
			ProductPersonRelationRequest productPersonRelation)
			throws Exception {

		boolean isUpdated = false;
		if (!StringUtils.isEmpty(productPersonRelation.getEmail())
				&& !StringUtils.isEmpty(productPersonRelation.getDhId())
				&& !StringUtils.isEmpty(productPersonRelation.getUserId())) {
			isUpdated = crossRefDAO.updateProductPersonRelation(
					Integer.parseInt(productPersonRelation.getUserId()),
					productPersonRelation.getEmail(),
					Integer.parseInt(productPersonRelation.getDhId()));
		}
		return isUpdated;
	}

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

	private ProductPersonRelations getProductPersonRelationsEntity(
			ProductPersonRelationRequest productPersonRelations)
			throws Exception {
		ProductPersonRelations productPersonRelationsEntity = null;
		if (!StringUtils.isEmpty(productPersonRelations)) {
			productPersonRelationsEntity = new ProductPersonRelations();
			System.err.println(productPersonRelations.getUserId());
			if (!StringUtils.isEmpty(productPersonRelations.getUserId())) {
				UserProfile user = crossRefDAO.getUserProfileById(Integer
						.parseInt(productPersonRelations.getUserId()));
				productPersonRelationsEntity.setUserProfile(user);
			} else if (!StringUtils.isEmpty(productPersonRelations.getEmail())) {
				productPersonRelationsEntity
						.setEmailAddr(productPersonRelations.getEmail());
			}
			if (!StringUtils.isEmpty(productPersonRelations.getDhId())) {
				Products product = crossRefDAO.getProducts(Integer
						.parseInt(productPersonRelations.getDhId()));
				productPersonRelationsEntity.setProducts(product);
			}
			if (!StringUtils.isEmpty(productPersonRelations.getRole())) {
				ProductRoles role = crossRefDAO
						.getProductRoles(productPersonRelations.getRole());
				productPersonRelationsEntity.setProductRoles(role);
			}

		}
		return productPersonRelationsEntity;
	}
}
