package com.wiley.gr.ace.authorservices.services.service.impl;

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
		ProductPersonRelationObj productPersonRelationObj = null;
		PersonDetails personDetails = null;
		if (!StringUtils.isEmpty(dhId)) {
			ProductPersonRelations productPersonRelations = crossRefDAO
					.getProductPersonRelations(dhId);
			if (!StringUtils.isEmpty(productPersonRelations)) {
				productPersonRelationObj.setDhId(productPersonRelations
						.getProducts().getDhId());
				personDetails.setUserId(personDetails.getUserId());
				personDetails.setEmailAddr(personDetails.getEmailAddr());
				personDetails.setProductRoleCode(personDetails
						.getProductRoleCode());
				productPersonRelationObj.setPersonDetails(personDetails);
			}
		}

		return productPersonRelationObj;
	}

}
