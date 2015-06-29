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

import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * @author Virtusa
 *
 */
@JsonInclude(Include.NON_NULL)
public class TokenRequest {

	/**
	 * This field holds the value of userId
	 */
	@NotNull
	@NotBlank
	private String userId;

	/**
	 * This field holds the value of roles
	 */
	private List<String> roles;

	/**
	 * This field holds the value of appKey
	 */
	private String appKey;

	/**
	 * This method gets AppKey
	 *
	 * @return String
	 */
	public String getAppKey() {
		return this.appKey;
	}

	/**
	 * This method gets the list of Roles
	 *
	 * @return List<String>
	 */
	public List<String> getRoles() {
		return this.roles;
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
	 * This method sets AppKey
	 *
	 * @param appKey
	 */
	public void setAppKey(final String appKey) {
		this.appKey = appKey;
	}

	/**
	 * This method sets the Roles
	 *
	 * @param roles
	 */
	public void setRoles(final List<String> roles) {
		this.roles = roles;
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
	 * This is toString method
	 *
	 * @return
	 */
	@Override
	public String toString() {
		return "TokenRequest{" + "userId='" + this.userId + '\'' + ", roles="
				+ this.roles + ", appKey='" + this.appKey + '\'' + '}';
	}
}
