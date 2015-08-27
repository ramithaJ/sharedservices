/*******************************************************************************
 * Copyright (c) 2015 John Wiley & Sons, Inc. All rights reserved.
 *
 * All material contained herein is proprietary to John Wiley & Sons 
 * and its third party suppliers, if any. The methods, techniques and 
 * technical concepts contained herein are considered trade secrets 
 * and confidential and may be protected by intellectual property laws.  
 * Reproduction or distribution of this material, in whole or in part, 
 * is strictly forbidden except by express prior written permission 
 * of John Wiley & Sons.
 *******************************************************************************/
package com.wiley.gr.ace.authorservices.persistence.services;

import java.util.List;

import com.wiley.gr.ace.authorservices.persistence.entity.ProductPersonRelations;
import com.wiley.gr.ace.authorservices.persistence.entity.ProductRoles;
import com.wiley.gr.ace.authorservices.persistence.entity.Products;
import com.wiley.gr.ace.authorservices.persistence.entity.UserProfile;

/**
 * The Interface CrossRefDAO.
 * 
 * @author virtusa version 1.0
 */
public interface CrossRefDAO {

    /**
     * Gets the product person relations.
     *
     * @param dhId
     *            the dh id
     * @return the product person relations
     * @throws Exception
     *             the exception
     */
    List<ProductPersonRelations> getProductPersonRelations(Integer dhId)
            throws Exception;

    /**
     * Gets the product person relations by user id.
     *
     * @param userId
     *            the user id
     * @return the product person relations by user id
     * @throws Exception
     *             the exception
     */
    List<ProductPersonRelations> getProductPersonRelationsByUserId(
            Integer userId) throws Exception;

    /**
     * Gets the product person relations by email addr.
     *
     * @param emailAddr
     *            the email addr
     * @return the product person relations by email addr
     * @throws Exception
     *             the exception
     */
    List<ProductPersonRelations> getProductPersonRelationsByEmailAddr(
            String emailAddr) throws Exception;

    /**
     * Gets the user profile by id.
     *
     * @param userId
     *            the user id
     * @return the user profile by id
     * @throws Exception
     *             the exception
     */
    UserProfile getUserProfileById(int userId) throws Exception;

    /**
     * Gets the product roles.
     *
     * @param roleCd
     *            the role cd
     * @return the product roles
     * @throws Exception
     *             the exception
     */
    ProductRoles getProductRoles(String roleCd) throws Exception;

    /**
     * Gets the products.
     *
     * @param dhId
     *            the dh id
     * @return the products
     * @throws Exception
     *             the exception
     */
    Products getProducts(int dhId) throws Exception;

    /**
     * Save or update product person relation.
     *
     * @param productPersonRelations
     *            the product person relations
     * @return true, if successful
     * @throws Exception
     *             the exception
     */
    boolean saveOrUpdateProductPersonRelation(
            ProductPersonRelations productPersonRelations) throws Exception;

    /**
     * Delete product person relation.
     *
     * @param userId
     *            the user id
     * @param email
     *            the email
     * @param dhId
     *            the dh id
     * @return true, if successful
     * @throws Exception
     *             the exception
     */
    boolean deleteProductPersonRelation(Integer userId, String email, int dhId)
            throws Exception;

    /**
     * Update product person relation.
     *
     * @param userId
     *            the user id
     * @param email
     *            the email
     * @param dhId
     *            the dh id
     * @return true, if successful
     * @throws Exception
     *             the exception
     */
    boolean updateProductPersonRelation(int userId, String email, int dhId)
            throws Exception;

}
