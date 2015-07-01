package com.wiley.gr.ace.authorservices.services.service;

import com.wiley.gr.ace.authorservices.model.ProductPersonRelationRequest;

public interface CrossRefService1 {
	boolean createCrossRef(ProductPersonRelationRequest productPersonRelation)
			throws Exception;

	boolean updateCrossRef(ProductPersonRelationRequest productPersonRelation)
			throws Exception;

	boolean deleteCrossRef(ProductPersonRelationRequest productPersonRelation)
			throws Exception;
}
