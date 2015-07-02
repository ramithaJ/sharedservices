package com.wiley.gr.ace.authorservices.services.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.wiley.gr.ace.authorservices.model.AuthorDetails;
import com.wiley.gr.ace.authorservices.model.PersonDetails;
import com.wiley.gr.ace.authorservices.model.ProductId;
import com.wiley.gr.ace.authorservices.model.ProductPersonRelationObj;
import com.wiley.gr.ace.authorservices.persistence.entity.ProductPersonRelations;
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
			ProductId productId;
			List<ProductId> coAuthorList = new ArrayList<ProductId>();
			List<ProductId> AuthorList = new ArrayList<ProductId>();
			for (ProductPersonRelations p : productPersonRelationEntityList) {
				if ("102".equals("p.getProductRoles().getProductRoleCd()")) {
					productId = new ProductId();
					productId.setDhId(p.getProducts().getDhId().toString());
					coAuthorList.add(productId);
				} else {
					productId = new ProductId();
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
		AuthorDetails authorDetails = null;
		System.err.println("--------------------------------");
		if (!StringUtils.isEmpty(userId)) {
			List<ProductPersonRelations> productPersonRelationEntityList = crossRefDAO
					.getProductPersonRelationsByUserId(Integer.parseInt(userId));
			authorDetails = new AuthorDetails();
			authorDetails.setEmail(userId);
			ProductId productId;
			List<ProductId> coAuthorList = new ArrayList<ProductId>();
			List<ProductId> AuthorList = new ArrayList<ProductId>();
			for (ProductPersonRelations p : productPersonRelationEntityList) {
				if ("102".equals("p.getProductRoles().getProductRoleCd()")) {
					productId = new ProductId();
					productId.setDhId(p.getProducts().getDhId().toString());
					coAuthorList.add(productId);
				} else {
					productId = new ProductId();
					productId.setDhId(p.getProducts().getDhId().toString());
					AuthorList.add(productId);
				}
			}
			authorDetails.setCoAuthor(coAuthorList);
			authorDetails.setCorresPondingAuthor(AuthorList);
		}
		return authorDetails;
	}

}
