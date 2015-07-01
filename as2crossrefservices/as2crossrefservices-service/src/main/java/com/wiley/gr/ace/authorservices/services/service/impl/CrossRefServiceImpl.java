package com.wiley.gr.ace.authorservices.services.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.wiley.gr.ace.authorservices.model.PersonDetails;
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

	public ProductPersonRelationObj getProductPersonRelationsByUserID(
			Integer userId) throws Exception {

		ProductPersonRelationObj productPersonRelationObj = new ProductPersonRelationObj();
		PersonDetails correspondingAuthor = new PersonDetails();
		List<PersonDetails> coAuthorList = new ArrayList<PersonDetails>();
		System.err.println("object of product person relations"
				+ productPersonRelationObj);
		if (!StringUtils.isEmpty(userId)) {
			List<ProductPersonRelations> productPersonRelationList = crossRefDAO
					.getProductPersonRelationsByUserID(userId);
			if (!StringUtils.isEmpty(productPersonRelationList)) {

				productPersonRelationObj.setUserId(userId);

				for (ProductPersonRelations productPersonRelations : productPersonRelationList) {
					if ("8011047".equalsIgnoreCase(productPersonRelations
							.getProductRoles().getProductRoleCd())) {
						PersonDetails tempPersonDetails = new PersonDetails();
						tempPersonDetails.setUserId(productPersonRelations
								.getUserProfile().getUserId());
						tempPersonDetails.setEmailAddr(productPersonRelations
								.getEmailAddr());

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

	public ProductPersonRelationObj getProductPersonRelationsByEmailAddr(
			String emailAddr) throws Exception {

		ProductPersonRelationObj productPersonRelationObj = new ProductPersonRelationObj();
		PersonDetails correspondingAuthor = new PersonDetails();
		List<PersonDetails> coAuthorList = new ArrayList<PersonDetails>();
		if (!StringUtils.isEmpty(emailAddr)) {
			List<ProductPersonRelations> productPersonRelationList = crossRefDAO
					.getProductPersonRelationsByEmailAddr(emailAddr);
			System.err.println("email addresss " + emailAddr);
			if (!StringUtils.isEmpty(productPersonRelationList)) {

				productPersonRelationObj.setEmailAddr(emailAddr);

				for (ProductPersonRelations productPersonRelations : productPersonRelationList) {
					if ("shiva@gmail.com"
							.equalsIgnoreCase(productPersonRelations
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

}
