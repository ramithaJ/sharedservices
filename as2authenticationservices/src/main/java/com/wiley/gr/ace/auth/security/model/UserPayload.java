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

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The Class UserPayload.
 */
public class UserPayload {
	
	/** The last name. */
	@JsonProperty("LastName")
	private String lastName;
	
	/** The first name. */
	@JsonProperty("FirstName")
	private String firstName;
	
	/** The email. */
	@JsonProperty("Email")
	private String email;
	
	/** The ecid. */
	@JsonProperty("ECID")
	private String ecid;
	
	/** The user status. */
	@JsonProperty("UserStatus")
	private String userStatus;
	
	/** The source system. */
	@JsonProperty("SourceSystem")
	private String sourceSystem;
	
	/** The user address. */
	@JsonProperty("UserAddress")
	private UserAddress userAddress;
	
	/** The security questions. */
	@JsonProperty("SecurityQuestions")
	private SecurityQuestions securityQuestions;
	
	/** The customer type. */
	@JsonProperty("CustomerType")
	private String customerType;
	
	/** The tc flag. */
	@JsonProperty("TCFlag")
	private String tcFlag;

	/**
	 * Gets the last name.
	 *
	 * @return the last name
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Sets the last name.
	 *
	 * @param lastName the new last name
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Gets the first name.
	 *
	 * @return the first name
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Sets the first name.
	 *
	 * @param firstName the new first name
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Gets the email.
	 *
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Sets the email.
	 *
	 * @param email the new email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Gets the ecid.
	 *
	 * @return the ecid
	 */
	public String getEcid() {
		return ecid;
	}

	/**
	 * Sets the ecid.
	 *
	 * @param ecid the new ecid
	 */
	public void setEcid(String ecid) {
		this.ecid = ecid;
	}

	/**
	 * Gets the user status.
	 *
	 * @return the user status
	 */
	public String getUserStatus() {
		return userStatus;
	}

	/**
	 * Sets the user status.
	 *
	 * @param userStatus the new user status
	 */
	public void setUserStatus(String userStatus) {
		this.userStatus = userStatus;
	}

	/**
	 * Gets the source system.
	 *
	 * @return the source system
	 */
	public String getSourceSystem() {
		return sourceSystem;
	}

	/**
	 * Sets the source system.
	 *
	 * @param sourceSystem the new source system
	 */
	public void setSourceSystem(String sourceSystem) {
		this.sourceSystem = sourceSystem;
	}

	/**
	 * Gets the user address.
	 *
	 * @return the user address
	 */
	public UserAddress getUserAddress() {
		return userAddress;
	}

	/**
	 * Sets the user address.
	 *
	 * @param userAddress the new user address
	 */
	public void setUserAddress(UserAddress userAddress) {
		this.userAddress = userAddress;
	}

	/**
	 * Gets the security questions.
	 *
	 * @return the security questions
	 */
	public SecurityQuestions getSecurityQuestions() {
		return securityQuestions;
	}

	/**
	 * Sets the security questions.
	 *
	 * @param securityQuestions the new security questions
	 */
	public void setSecurityQuestions(SecurityQuestions securityQuestions) {
		this.securityQuestions = securityQuestions;
	}

	/**
	 * Gets the customer type.
	 *
	 * @return the customer type
	 */
	public String getCustomerType() {
		return customerType;
	}

	/**
	 * Sets the customer type.
	 *
	 * @param customerType the new customer type
	 */
	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}

	/**
	 * Gets the tc flag.
	 *
	 * @return the tc flag
	 */
	public String getTcFlag() {
		return tcFlag;
	}

	/**
	 * Sets the tc flag.
	 *
	 * @param tcFlag the new tc flag
	 */
	public void setTcFlag(String tcFlag) {
		this.tcFlag = tcFlag;
	}

}
