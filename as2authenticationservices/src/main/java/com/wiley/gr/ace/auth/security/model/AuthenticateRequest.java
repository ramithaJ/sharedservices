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
package com.wiley.gr.ace.auth.security.model;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * @author Virtusa
 *
 */
@JsonInclude(Include.NON_NULL)
public class AuthenticateRequest implements Serializable {

	/**
	 * This field holds the value of serialVersionUID
	 */
	private static final long serialVersionUID = 1131477618234873529L;

	/**
	 * This field holds the value of userId
	 */
	@NotNull
	@NotBlank
	@Email
	private String userId;

	/**
	 * This field holds the value of password
	 */
	@NotNull
	@NotBlank
	private String password;

	/**
	 * This field holds the value of authenticationType
	 */
	@NotNull
	@NotBlank
	private String authenticationType;

	/**
	 * This field holds the value of appKey
	 */
	@NotNull
	@NotBlank
	private String appKey;

	/**
	 * This method gets App Key
	 *
	 * @return String
	 */
	public String getAppKey() {
		return this.appKey;
	}

	/**
	 * This method gets Authentication Type
	 *
	 * @return String
	 */
	public String getAuthenticationType() {
		return this.authenticationType;
	}

	/**
	 * This method gets Password
	 *
	 * @return String
	 */
	public String getPassword() {
		return this.password;
	}

	/**
	 * This method gets UserId
	 *
	 * @return String
	 */
	public String getUserId() {
		return this.userId;
	}

	/**
	 * This method sets App Key
	 *
	 * @param appKey
	 */
	public void setAppKey(final String appKey) {
		this.appKey = appKey;
	}

	/**
	 * This method sets Authentication Type
	 *
	 * @param authenticationType
	 */
	public void setAuthenticationType(final String authenticationType) {
		this.authenticationType = authenticationType;
	}

	/**
	 * This method sets Password
	 *
	 * @param password
	 */
	public void setPassword(final String password) {
		this.password = password;
	}

	/**
	 * This method sets UserId
	 *
	 * @param userId
	 */
	public void setUserId(final String userId) {
		this.userId = userId;
	}

	/**
	 * This method toString
	 *
	 * @return
	 */
	@Override
	public String toString() {
		return "AuthenticateRequest{" + "userId='" + this.userId + '\''
				+ ", password='" + this.password + '\''
				+ ", authenticationType='" + this.authenticationType + '\''
				+ ", appKey='" + this.appKey + '\'' + '}';
	}
}
