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
package com.wiley.gr.ace.auth.security.model;

/**
 * @author Virtusa
 *
 */
public class SecurityRequest {

    /**
     * This field holds the value of userId
     */
    private String userId;

    /**
     * This method gets UserId
     *
     * @return String
     */
    public String getUserId() {
        return this.userId;
    }

    /**
     * This method sets UserId
     *
     * @param userId
     */
    public void setUserId(final String userId) {
        this.userId = userId;
    }
}