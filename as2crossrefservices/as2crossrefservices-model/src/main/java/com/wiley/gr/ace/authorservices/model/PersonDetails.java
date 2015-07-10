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
package com.wiley.gr.ace.authorservices.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * The Class PersonDetails.
 *
 * @author virtusa version 1.0
 */
@JsonInclude(Include.NON_NULL)
public class PersonDetails {

    /** The user id. */
    private Integer userId;

    /** The email addr. */
    private String emailAddr;

    /**
     * Gets the email addr.
     *
     * @return the email addr
     */
    public final String getEmailAddr() {
        return emailAddr;
    }

    /**
     * Sets the email addr.
     *
     * @param emailAddr
     *            the new email addr
     */
    public final void setEmailAddr(final String emailAddr) {
        this.emailAddr = emailAddr;
    }

    /**
     * Gets the user id.
     *
     * @return the user id
     */
    public final Integer getUserId() {
        return userId;
    }

    /**
     * Sets the user id.
     *
     * @param userId
     *            the new user id
     */
    public final void setUserId(final Integer userId) {
        this.userId = userId;
    }

}
