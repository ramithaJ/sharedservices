package com.wiley.gr.ace.authorservices.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * The Class ProductPersonRelationObj.
 */
@JsonInclude(Include.NON_NULL)
public class ProductPersonRelationObj {

	/** The dh id. */
	private Integer dhId;
	
	/** The user id. */
	private Integer userId;
	
	/** The email addr. */
	private String emailAddr;
	
	/** The co author list. */
	private List<PersonDetails> coAuthorList;
	
	/** The corresponding author. */
	private PersonDetails correspondingAuthor;

	/**
	 * Gets the user id.
	 *
	 * @return the user id
	 */
	public final Integer getUserId() {
		return userId;
	}

	/**
	 * Sets the user id.
	 *
	 * @param userId the new user id
	 */
	public final void setUserId(Integer userId) {
		this.userId = userId;
	}

	/**
	 * Gets the email addr.
	 *
	 * @return the email addr
	 */
	public final String getEmailAddr() {
		return emailAddr;
	}

	/**
	 * Sets the email addr.
	 *
	 * @param emailAddr the new email addr
	 */
	public final void setEmailAddr(String emailAddr) {
		this.emailAddr = emailAddr;
	}

	/**
	 * Gets the dh id.
	 *
	 * @return the dh id
	 */
	public Integer getDhId() {
		return dhId;
	}

	/**
	 * Sets the dh id.
	 *
	 * @param dhId the new dh id
	 */
	public void setDhId(Integer dhId) {
		this.dhId = dhId;
	}

	/**
	 * Gets the co author list.
	 *
	 * @return the co author list
	 */
	public final List<PersonDetails> getCoAuthorList() {
		return coAuthorList;
	}

	/**
	 * Sets the co author list.
	 *
	 * @param coAuthorList the new co author list
	 */
	public final void setCoAuthorList(List<PersonDetails> coAuthorList) {
		this.coAuthorList = coAuthorList;
	}

	/**
	 * Gets the corresponding author.
	 *
	 * @return the corresponding author
	 */
	public final PersonDetails getCorrespondingAuthor() {
		return correspondingAuthor;
	}

	/**
	 * Sets the corresponding author.
	 *
	 * @param correspondingAuthor the new corresponding author
	 */
	public final void setCorrespondingAuthor(PersonDetails correspondingAuthor) {
		this.correspondingAuthor = correspondingAuthor;
	}

}
