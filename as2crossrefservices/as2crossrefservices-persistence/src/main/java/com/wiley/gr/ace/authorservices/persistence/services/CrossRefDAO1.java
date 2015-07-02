package com.wiley.gr.ace.authorservices.persistence.services;

import com.wiley.gr.ace.authorservices.persistence.entity.ProductPersonRelations;
import com.wiley.gr.ace.authorservices.persistence.entity.ProductRoles;
import com.wiley.gr.ace.authorservices.persistence.entity.Products;
import com.wiley.gr.ace.authorservices.persistence.entity.UserProfile;

public interface CrossRefDAO1 {
	UserProfile getUserProfileById(int userId) throws Exception;
	ProductRoles getProductRoles(String roleCd) throws Exception;
	Products getProducts(int dhId) throws Exception;
	boolean saveOrUpdateProductPersonRelation(ProductPersonRelations productPersonRelations) throws Exception;
	boolean deleteProductPersonRelation(int userId,String email, int dhId) throws Exception;
	boolean updateProductPersonRelation(int userId,String email, int dhId) throws Exception;
}
