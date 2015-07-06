/**
 * 
 */
package com.wiley.gr.ace.authorservices.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * The Class PersonDetails.
 *
 * @author bgundavarapu
 */
@JsonInclude(Include.NON_NULL)
public class PersonDetails {
	
	/** The user id. */
	private Integer userId;
	
	/** The email addr. */
	private String emailAddr;

	/**
	 * Gets the email addr.
	 *
	 * @return the email addr
	 */
	public String getEmailAddr() {
		return emailAddr;
	}

	/**
	 * Sets the email addr.
	 *
	 * @param emailAddr the new email addr
	 */
	public void setEmailAddr(String emailAddr) {
		this.emailAddr = emailAddr;
	}

	/**
	 * Gets the user id.
	 *
	 * @return the user id
	 */
	public Integer getUserId() {
		return userId;
	}

	/**
	 * Sets the user id.
	 *
	 * @param userId the new user id
	 */
	public void setUserId(Integer userId) {
		this.userId = userId;
	}

}
