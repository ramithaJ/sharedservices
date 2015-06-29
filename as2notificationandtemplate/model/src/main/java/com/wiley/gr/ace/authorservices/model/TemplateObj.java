package com.wiley.gr.ace.authorservices.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;


// TODO: Auto-generated Javadoc
/**
 * The Class TemplateObj.
 */
@JsonInclude(Include.NON_NULL)
public class TemplateObj {
	
	/** The id. */
	private String id;
	
	/** The app id. */
	private String appId;
	
	/** The description. */
	private String description;
	
	/** The tagl1. */
	private String tagl1;
	
	/** The tagl2. */
	private String tagl2;
	
	/** The body. */
	private String body;
	
	/** The created by. */
	private String createdBy;
	
	/** The modified by. */
	private String modifiedBy;
	
	/** The created on. */
	private String createdOn;
	
	/** The last modified on. */
	private String lastModifiedOn;

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Gets the app id.
	 *
	 * @return the app id
	 */
	public String getAppId() {
		return appId;
	}

	/**
	 * Sets the app id.
	 *
	 * @param appId the new app id
	 */
	public void setAppId(String appId) {
		this.appId = appId;
	}

	/**
	 * Gets the description.
	 *
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the description.
	 *
	 * @param description the new description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Gets the tagl1.
	 *
	 * @return the tagl1
	 */
	public String getTagl1() {
		return tagl1;
	}

	/**
	 * Sets the tagl1.
	 *
	 * @param tagl1 the new tagl1
	 */
	public void setTagl1(String tagl1) {
		this.tagl1 = tagl1;
	}

	/**
	 * Gets the tagl2.
	 *
	 * @return the tagl2
	 */
	public String getTagl2() {
		return tagl2;
	}

	/**
	 * Sets the tagl2.
	 *
	 * @param tagl2 the new tagl2
	 */
	public void setTagl2(String tagl2) {
		this.tagl2 = tagl2;
	}

	/**
	 * Gets the body.
	 *
	 * @return the body
	 */
	public String getBody() {
		return body;
	}

	/**
	 * Sets the body.
	 *
	 * @param body the new body
	 */
	public void setBody(String body) {
		this.body = body;
	}

	/**
	 * Gets the created by.
	 *
	 * @return the created by
	 */
	public String getCreatedBy() {
		return createdBy;
	}

	/**
	 * Sets the created by.
	 *
	 * @param createdBy the new created by
	 */
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * Gets the created on.
	 *
	 * @return the created on
	 */
	public String getCreatedOn() {
		return createdOn;
	}

	/**
	 * Sets the created on.
	 *
	 * @param createdOn the new created on
	 */
	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
	}

	/**
	 * Gets the last modified on.
	 *
	 * @return the last modified on
	 */
	public String getLastModifiedOn() {
		return lastModifiedOn;
	}

	/**
	 * Sets the last modified on.
	 *
	 * @param lastModifiedOn the new last modified on
	 */
	public void setLastModifiedOn(String lastModifiedOn) {
		this.lastModifiedOn = lastModifiedOn;
	}

	/**
	 * Gets the modified by.
	 *
	 * @return the modified by
	 */
	public String getModifiedBy() {
		return modifiedBy;
	}

	/**
	 * Sets the modified by.
	 *
	 * @param modifiedBy the new modified by
	 */
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}


}
