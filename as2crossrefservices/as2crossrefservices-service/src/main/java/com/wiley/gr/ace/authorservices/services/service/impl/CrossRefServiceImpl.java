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

public class CrossRefServiceImpl implements CrossRefService {

	@Autowired(required = true)
	CrossRefDAO crossRefDAO;

	public ProductPersonRelationObj getProductPersonRelationObj(Integer dhId)
			throws Exception {
		ProductPersonRelationObj productPersonRelationObj = new ProductPersonRelationObj();
		PersonDetails correspondingAuthor = new PersonDetails();
		List<PersonDetails> coAuthorList = new ArrayList<PersonDetails>();
		if (!StringUtils.isEmpty(dhId)) {
			List<ProductPersonRelations> productPersonRelationList = crossRefDAO
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

	@Override
	public AuthorDetails getAuthorDetailsByEmail(String email) throws Exception {
		AuthorDetails authorDetails = null;
		System.err.println("--------------------------------");
		if (!StringUtils.isEmpty(email)) {
			List<ProductPersonRelations> productPersonRelationEntityList = crossRefDAO
					.getProductPersonRelationsByEmailAddr(email);
			authorDetails = new AuthorDetails();
			authorDetails.setEmail(email);
			ProductId productId = new ProductId();
			List<ProductId> coAuthorList = new ArrayList<ProductId>();
			List<ProductId> AuthorList = new ArrayList<ProductId>();
			for (ProductPersonRelations p : productPersonRelationEntityList) {
				if ("102".equals("p.getProductRoles().getProductRoleCd()")) {
					productId.setDhId(p.getProducts().getDhId().toString());
					coAuthorList.add(productId);
				} else {
					productId.setDhId(p.getProducts().getDhId().toString());
					AuthorList.add(productId);
				}
			}
			authorDetails.setCoAuthor(coAuthorList);
			authorDetails.setCorresPondingAuthor(AuthorList);
		}
		return authorDetails;
	}

	@Override
	public AuthorDetails getAuthorDetailsById(String userId) throws Exception {
		AuthorDetails authorDetails1 = null;
		System.err.println("--------------------------------");
		if (!StringUtils.isEmpty(userId)) {
			List<ProductPersonRelations> productPersonRelationEntityList = crossRefDAO
					.getProductPersonRelationsByUserId(Integer.parseInt(userId));
			authorDetails1 = new AuthorDetails();
			authorDetails1.setUserId(userId);
			ProductId productId1 = new ProductId();
			List<ProductId> coAuthorList = new ArrayList<ProductId>();
			List<ProductId> AuthorList = new ArrayList<ProductId>();
			for (ProductPersonRelations p : productPersonRelationEntityList) {
				if ("102".equals("p.getProductRoles().getProductRoleCd()")) {
					productId1.setDhId(p.getProducts().getDhId().toString());
					coAuthorList.add(productId1);
				} else {
					productId1.setDhId(p.getProducts().getDhId().toString());
					AuthorList.add(productId1);
				}
			}
			authorDetails1.setCoAuthor(coAuthorList);
			authorDetails1.setCorresPondingAuthor(AuthorList);
		}
		return authorDetails1;
	}
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
