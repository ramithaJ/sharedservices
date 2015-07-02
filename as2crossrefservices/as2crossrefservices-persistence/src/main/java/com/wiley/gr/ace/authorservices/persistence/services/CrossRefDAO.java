package com.wiley.gr.ace.authorservices.persistence.services;

import java.util.List;

import com.wiley.gr.ace.authorservices.persistence.entity.ProductPersonRelations;

public interface CrossRefDAO {
	List<ProductPersonRelations> getProductPersonRelations(Integer dhId)
			throws Exception;

	List<ProductPersonRelations> getProductPersonRelationsByUserId(
			Integer userId) throws Exception;

	List<ProductPersonRelations> getProductPersonRelationsByEmailAddr(
			String emailAddr) throws Exception;
	
}
