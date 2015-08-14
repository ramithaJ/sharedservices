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
public class SecurityResponse {

    /**
     * This field holds the value of status
     */
    private String status;

    /**
     * This method gets Status
     *
     * @return String
     */
    public String getStatus() {
        return this.status;
    }

    /**
     * This method sets Status
     *
     * @param status
     */
    public void setStatus(final String status) {
        this.status = status;
    }
}
