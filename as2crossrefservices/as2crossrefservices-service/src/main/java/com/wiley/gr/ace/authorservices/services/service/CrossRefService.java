package com.wiley.gr.ace.authorservices.services.service;

import com.wiley.gr.ace.authorservices.model.AuthorDetails;
import com.wiley.gr.ace.authorservices.model.ProductPersonRelationObj;
import com.wiley.gr.ace.authorservices.model.ProductPersonRelationRequest;

/**
 * The Interface CrossRefService.
 */
public interface CrossRefService {
	
	/**
	 * Gets the product person relation obj.
	 *
	 * @param dhId the dh id
	 * @return the product person relation obj
	 * @throws Exception the exception
	 */
	ProductPersonRelationObj getProductPersonRelationObj(Integer dhId)
			throws Exception;

	/**
	 * Gets the author details by email.
	 *
	 * @param email the email
	 * @return the author details by email
	 * @throws Exception the exception
	 */
	AuthorDetails getAuthorDetailsByEmail(String email) throws Exception;

	/**
	 * Gets the author details by id.
	 *
	 * @param userId the user id
	 * @return the author details by id
	 * @throws Exception the exception
	 */
	AuthorDetails getAuthorDetailsById(String userId) throws Exception;
	
	/**
	 * Creates the cross ref.
	 *
	 * @param productPersonRelation the product person relation
	 * @return true, if successful
	 * @throws Exception the exception
	 */
	boolean createCrossRef(ProductPersonRelationRequest productPersonRelation)
			throws Exception;

	/**
	 * Update cross ref.
	 *
	 * @param productPersonRelation the product person relation
	 * @return true, if successful
	 * @throws Exception the exception
	 */
	boolean updateCrossRef(ProductPersonRelationRequest productPersonRelation)
			throws Exception;

	/**
	 * Delete cross ref.
	 *
	 * @param productPersonRelation the product person relation
	 * @return true, if successful
	 * @throws Exception the exception
	 */
	boolean deleteCrossRef(ProductPersonRelationRequest productPersonRelation)
			throws Exception;
}
