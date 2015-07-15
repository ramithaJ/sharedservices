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
	 * This field holds the value of code
	 */
	private String code;

	/**
	 * This field holds the value of message
	 */
	private String message;

	/**
	 * This field holds the value of status
	 */
	private String status;

	public User() {
		
	}
	/**
	 * Constructor
	 *
	 * @param code
	 * @param message
	 * @param status
	 */
	public User(final String code, final String message, final String status) {
		this.code = code;
		this.message = message;
		this.status = status;
	}

	/**
	 * @return the code
	 */
	public final String getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public final void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return the message
	 */
	public final String getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public final void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return the status
	 */
	public final String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public final void setStatus(String status) {
		this.status = status;
	}

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