package com.wiley.gr.ace.authorservices.services.service;

import com.wiley.gr.ace.authorservices.model.AuthorDetails;
import com.wiley.gr.ace.authorservices.model.ProductPersonRelationObj;

public interface CrossRefService {
	ProductPersonRelationObj getProductPersonRelationObj(Integer dhId)
			throws Exception;

	AuthorDetails getAuthorDetailsByEmail(String email) throws Exception;

	AuthorDetails getAuthorDetailsById(String userId) throws Exception;
}
