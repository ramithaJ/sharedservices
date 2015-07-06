package com.wiley.gr.ace.authorservices.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * The Class ProductPersonRelationRequest.
 */
@JsonInclude(Include.NON_NULL)
public class ProductPersonRelationRequest {
	
	/** The user id. */
	String userId;
	
	/** The email. */
	String email;
	
	/** The dh id. */
	String dhId;
	
	/** The role. */
	String role;
	
	/**
	 * Gets the user id.
	 *
	 * @return the user id
	 */
	public String getUserId() {
		return userId;
	}
	
	/**
	 * Sets the user id.
	 *
	 * @param userId the new user id
	 */
	public void setUserId(String userId) {
		this.userId = userId;
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
	 * Gets the dh id.
	 *
	 * @return the dh id
	 */
	public String getDhId() {
		return dhId;
	}
	
	/**
	 * Sets the dh id.
	 *
	 * @param dhId the new dh id
	 */
	public void setDhId(String dhId) {
		this.dhId = dhId;
	}
	
	/**
	 * Gets the role.
	 *
	 * @return the role
	 */
	public String getRole() {
		return role;
	}
	
	/**
	 * Sets the role.
	 *
	 * @param role the new role
	 */
	public void setRole(String role) {
		this.role = role;
	}
}
