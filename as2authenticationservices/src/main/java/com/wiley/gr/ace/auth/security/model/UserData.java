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

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The Class UserData.
 */
public class UserData {
	
	/** The user pay load list. */
	@JsonProperty("UserPayload")
	private List<UserPayload> userPayLoadList;

	/**
	 * Gets the user pay load list.
	 *
	 * @return the user pay load list
	 */
	public List<UserPayload> getUserPayLoadList() {
		return userPayLoadList;
	}

	/**
	 * Sets the user pay load list.
	 *
	 * @param userPayLoadList the new user pay load list
	 */
	public void setUserPayLoadList(List<UserPayload> userPayLoadList) {
		this.userPayLoadList = userPayLoadList;
	}

}
