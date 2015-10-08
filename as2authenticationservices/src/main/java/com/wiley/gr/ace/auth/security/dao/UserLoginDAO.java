/**
 * ****************************************************************************
 * Copyright (c) 2015 John Wiley & Sons, Inc. All rights reserved.
 * <p>
 * All material contained herein is proprietary to John Wiley & Sons
 * and its third party suppliers, if any. The methods, techniques and
 * technical concepts contained herein are considered trade secrets
 * and confidential and may be protected by intellectual property laws.
 * Reproduction or distribution of this material, in whole or in part,
 * is strictly forbidden except by express prior written permission
 * of John Wiley & Sons.
 * *****************************************************************************
 */
package com.wiley.gr.ace.auth.security.dao;

// TODO: Auto-generated Javadoc
/**
 * The Interface UserLoginDAO.
 *
 * @author Virtusa
 */
public interface UserLoginDAO {

    /**
     * This method inserts User.
     *
     * @param userId the user id
     * @param appKey the app key
     * @return boolean
     */
    boolean insertUser(String userId, String appKey);

    /**
     * This method removes User.
     *
     * @param userId the user id
     * @return boolean
     */
    boolean removeUser(String userId);

    /**
     * This method updates Time Stamp.
     *
     * @param userId the user id
     * @return boolean
     */
    boolean updateTimeStamp(String userId);

    /**
     * This method updates User.
     *
     * @param userId the user id
     * @return boolean
     */
    boolean updateUser(String userId);

    /**
     * This method returns user Account Details.
     *
     * @param userId the user id
     * @return LockedAccountDetails
     */
    LockedAccountDetails userAccountDetails(String userId);
}
