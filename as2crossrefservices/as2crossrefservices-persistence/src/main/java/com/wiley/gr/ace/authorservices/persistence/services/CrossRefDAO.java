package com.wiley.gr.ace.authorservices.persistence.services;

import java.util.List;

import com.wiley.gr.ace.authorservices.persistence.entity.ProductPersonRelations;
import com.wiley.gr.ace.authorservices.persistence.entity.ProductRoles;
import com.wiley.gr.ace.authorservices.persistence.entity.Products;
import com.wiley.gr.ace.authorservices.persistence.entity.UserProfile;

public interface CrossRefDAO {
	List<ProductPersonRelations> getProductPersonRelations(Integer dhId)
			throws Exception;

	List<ProductPersonRelations> getProductPersonRelationsByUserId(
			Integer userId) throws Exception;

	List<ProductPersonRelations> getProductPersonRelationsByEmailAddr(
			String emailAddr) throws Exception;
	UserProfile getUserProfileById(int userId) throws Exception;
	ProductRoles getProductRoles(String roleCd) throws Exception;
	Products getProducts(int dhId) throws Exception;
	boolean saveOrUpdateProductPersonRelation(ProductPersonRelations productPersonRelations) throws Exception;
	boolean deleteProductPersonRelation(int userId,String email, int dhId) throws Exception;
	boolean updateProductPersonRelation(int userId,String email, int dhId) throws Exception;
	
}
