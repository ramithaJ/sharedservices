/**
 * ****************************************************************************
 * Copyright (c) 2015 John Wiley & Sons, Inc. All rights reserved.
 * <p/>
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
 *
 */
public interface CommonConstant {

	/**
	 * This field holds the value of SUBJECT
	 */
	static String SUBJECT = "token.subject";

	/**
	 * This field holds the value of AUDIENCE
	 */
	static String AUDIENCE = "token.audience";

	/**
	 * This field holds the value of EXPIRATION_TIME
	 */
	static String EXPIRATION_TIME = "token.expiration.time";

	/**
	 * This field holds the value of CLAIM_KEY
	 */
	static String CLAIM_KEY = "token.claim.key";

	/**
	 * This field holds the value of TIME_BEFORE
	 */
	static String TIME_BEFORE = "token.time.before";

	/**
	 * This field holds the value of KEY_ID
	 */
	static String KEY_ID = "token.key.id";

	/**
	 * This field holds the value of ALGORITHM
	 */
	static String ALGORITHM = "token.algorithm";

	/**
	 * This field holds the value of DOT
	 */
	static String DOT = ".";

	/**
	 * This field holds the value of AUTHENTICATE_SERVICE_URL
	 */
	static String AUTHENTICATE_SERVICE_URL = "/auth/authenticate";

	/**
	 * This field holds the value of SEARCH_USER_URL
	 */
	static String SEARCH_USER_URL = "/user";

	/**
	 * This field holds the value of TOKEN_SERVICE_URL
	 */
	static String TOKEN_SERVICE_URL = "/token";

	/**
	 * This field holds the value of TOKEN_REFRESH_SERVICE_URL
	 */
	static String TOKEN_REFRESH_SERVICE_URL = "/token/refresh";

	/**
	 * This field holds the value of EXCEPTION
	 */
	static String EXCEPTION = "Exception Occurred While Processing the Request";

	/**
	 * This field holds the value of ISS
	 */
	static String ISS = "iss";

	/**
	 * This field holds the value of ROLES
	 */
	static String ROLES = "roles";

	/**
	 * This field holds the value of USER_ID
	 */
	static String USER_ID = "userid";

	/**
	 * This field holds the value of STATUS_CODE
	 */
	static String STATUS_CODE = "200";

	/**
	 * This field holds the value of FAIL_CODE
	 */
	static String FAIL_CODE = "201";

	/**
	 * This field holds the value of LOCKED_CODE
	 */
	static String LOCKED_CODE = "202";

	/**
	 * This field holds the value of SUCCESS_STATUS
	 */
	static String SUCCESS_STATUS = "SUCCESS";

	/**
	 * This field holds the value of FAILURE_STATUS
	 */
	static String FAILURE_STATUS = "FAILURE";

	/**
	 * This field holds the value of LDAP
	 */
	static String LDAP = "LDAP";

	/**
	 * This field holds the value of AUTO
	 */
	static String AUTO = "AUTO";

	/**
	 * This field holds the value of WILEY_DOMAIN
	 */
	static String WILEY_DOMAIN = "wiley.com";
}
