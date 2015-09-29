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
package com.wiley.gr.ace.auth.security.constants;

/**
 * @author Virtusa
 */
public class CommonConstant {

    /**
     * This field holds the value of SUBJECT
     */
    public static final String SUBJECT = "token.subject";

    /**
     * This field holds the value of AUDIENCE
     */
    public static final String AUDIENCE = "token.audience";

    /**
     * This field holds the value of EXPIRATION_TIME
     */
    public static final String EXPIRATION_TIME = "token.expiration.time";

    /**
     * This field holds the value of CLAIM_KEY
     */
    public static final String CLAIM_KEY = "token.claim.key";

    /**
     * This field holds the value of TIME_BEFORE
     */
    public static final String TIME_BEFORE = "token.time.before";

    /**
     * This field holds the value of DOT
     */
    public static final String DOT = ".";

    /**
     * This field holds the value of AUTHENTICATE_SERVICE_URL
     */
    public static final String AUTHENTICATE_SERVICE_URL = "/auth/authenticate";

    /**
     * This field holds the value of SEARCH_USER_URL
     */
    public static final String SEARCH_USER_URL = "/user";

    /**
     * This field holds the value of TOKEN_SERVICE_URL
     */
    public static final String TOKEN_SERVICE_URL = "/token";

    /**
     * This field holds the value of TOKEN_REFRESH_SERVICE_URL
     */
    public static final String TOKEN_REFRESH_SERVICE_URL = "/token/refresh";

    /**
     * This field holds the value of EXCEPTION
     */
    public static final String EXCEPTION = "Exception Occurred While Processing the Request";
    
    /**
     * This field holds the value of INTERNAL_SERVER_ERROR
     */
    public static final String INTERNAL_SERVER_ERROR = "Unable to process your request. Please try after some time..";

    /**
     * This field holds the value of ISS
     */
    public static final String ISS = "iss";

    /**
     * This field holds the value of ROLES
     */
    public static final String ROLES = "roles";

    /**
     * This field holds the value of USER_ID
     */
    public static final String USER_ID = "userid";

    /**
     * This field holds the value of STATUS_CODE
     */
    public static final String STATUS_CODE = "200";

    /**
     * This field holds the value of FAIL_CODE
     */
    public static final String FAIL_CODE = "201";
    
    /**
     * his field holds the value of LOCK_UNLOCK_FAIL_CODE
     */
    public static final String LOCK_UNLOCK_FAIL_CODE = "2015";

    /**
     * This field holds the value of LOCKED_CODE
     */
    public static final String LOCKED_CODE = "202";

    /**
     * This field holds the value of SUCCESS_STATUS
     */
    public static final String SUCCESS_STATUS = "SUCCESS";

    /**
     * This field holds the value of FAILURE_STATUS
     */
    public static final String FAILURE_STATUS = "FAILURE";

    /**
     * This field holds the value of LDAP
     */
    public static final String LDAP = "LDAP";

    /**
     * This field holds the value of AUTO
     */
    public static final String AUTO = "AUTO";

    /**
     * This field holds the value of WILEY_DOMAIN
     */
    public static final String WILEY_DOMAIN = "wiley.com";

    /**
     * This field holds the value of SOURCESYSTEM
     */
    public static final String SOURCESYSTEM = "AS";
    
    /**
     * This field holds the value of email
     */
    public static final String EMAIL = "email";

    private CommonConstant() {

    }
}
