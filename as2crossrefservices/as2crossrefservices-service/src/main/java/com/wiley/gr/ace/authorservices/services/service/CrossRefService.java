package com.wiley.gr.ace.authorservices.services.service;

import com.wiley.gr.ace.authorservices.model.ProductPersonRelationObj;


public interface CrossRefService {
	ProductPersonRelationObj getProductPersonRelationObj(Integer dhId) throws Exception;
	
}
