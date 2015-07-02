package com.wiley.gr.ace.authorservices.services.service;

import com.wiley.gr.ace.authorservices.model.AuthorDetails;
import com.wiley.gr.ace.authorservices.model.ProductPersonRelationObj;
import com.wiley.gr.ace.authorservices.model.ProductPersonRelationRequest;

public interface CrossRefService {
	ProductPersonRelationObj getProductPersonRelationObj(Integer dhId)
			throws Exception;

	AuthorDetails getAuthorDetailsByEmail(String email) throws Exception;

	AuthorDetails getAuthorDetailsById(String userId) throws Exception;
	
	boolean createCrossRef(ProductPersonRelationRequest productPersonRelation)
			throws Exception;

	boolean updateCrossRef(ProductPersonRelationRequest productPersonRelation)
			throws Exception;

	boolean deleteCrossRef(ProductPersonRelationRequest productPersonRelation)
			throws Exception;
}
