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

/**
 * @author Virtusa
 *
 */
public class User {

	/**
	 * This field holds the value of firstName
	 */
	private String firstName;

	/**
	 * This field holds the value of lastName
	 */
	private String lastName;

	/**
	 * This method gets FirstName
	 *
	 * @return String
	 */
	public String getFirstName() {
		return this.firstName;
	}

	/**
	 * This method gets LastName
	 *
	 * @return String
	 */
	public String getLastName() {
		return this.lastName;
	}

	/**
	 * This method sets FirstName
	 *
	 * @param firstName
	 */
	public void setFirstName(final String firstName) {
		this.firstName = firstName;
	}

	/**
	 * This method sets LastName
	 *
	 * @param lastName
	 */
	public void setLastName(final String lastName) {
		this.lastName = lastName;
	}
}