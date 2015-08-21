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
 * @author Virtusa version1.0
 *
 */
public class AuthenticationObject {

    /**
     * This field holds the value of authusername
     */
    private String authusername;

    /**
     * This field holds the value of authpassword
     */
    private String authpassword;

    /**
     * @return the authusername
     */
    public final String getAuthusername() {
        return authusername;
    }

    /**
     * @param authusername
     *            the authusername to set
     */
    public final void setAuthusername(final String authusername) {
        this.authusername = authusername;
    }

    /**
     * @return the authpassword
     */
    public final String getAuthpassword() {
        return authpassword;
    }

    /**
     * @param authpassword
     *            the authpassword to set
     */
    public final void setAuthpassword(final String authpassword) {
        this.authpassword = authpassword;
    }

}
