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

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * The Class AuthorDetails.
 * 
 * @author virtusa version 1.0
 */
@JsonInclude(Include.NON_NULL)
public class AuthorDetails {

    /** The user id. */
    private String userId;

    /** The email. */
    private String email;

    /** The corres ponding author. */
    private List<ProductId> corresPondingAuthor;

    /** The co author. */
    private List<ProductId> coAuthor;

    /**
     * Gets the user id.
     *
     * @return the user id
     */
    public final String getUserId() {
        return userId;
    }

    /**
     * Sets the user id.
     *
     * @param userId
     *            the new user id
     */
    public final void setUserId(final String userId) {
        this.userId = userId;
    }

    /**
     * Gets the email.
     *
     * @return the email
     */
    public final String getEmail() {
        return email;
    }

    /**
     * Sets the email.
     *
     * @param email
     *            the new email
     */
    public final void setEmail(final String email) {
        this.email = email;
    }

    /**
     * Gets the corres ponding author.
     *
     * @return the corres ponding author
     */
    public final List<ProductId> getCorresPondingAuthor() {
        return corresPondingAuthor;
    }

    /**
     * Sets the corres ponding author.
     *
     * @param corresPondingAuthor
     *            the new corres ponding author
     */
    public final void setCorresPondingAuthor(
            final List<ProductId> corresPondingAuthor) {
        this.corresPondingAuthor = corresPondingAuthor;
    }

    /**
     * Gets the co author.
     *
     * @return the co author
     */
    public final List<ProductId> getCoAuthor() {
        return coAuthor;
    }

    /**
     * Sets the co author.
     *
     * @param coAuthor
     *            the new co author
     */
    public final void setCoAuthor(final List<ProductId> coAuthor) {
        this.coAuthor = coAuthor;
    }
}
