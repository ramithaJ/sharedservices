package com.wiley.gr.ace.authorservices.model;

import java.util.Date;


/**
 * The Class ScheduleObj.
 */
public class ScheduleObj {
	
	/** The id. */
	private String id;
	
	/** The app id. */
	private String appId;
	
	/** The description. */
	private String description;
	
	/** The created by. */
	private String createdBy;
	
	/** The modified by. */
	private String modifiedBy;
	
	/** The created on. */
	private Date createdOn;
	
	/** The last modified on. */
	private Date lastModifiedOn;
	
	/** The schedule template. */
	private ScheduleTemplateObj scheduleTemplate;

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
	
	/**
	 * Gets the created on.
	 *
	 * @return the created on
	 */
	public Date getCreatedOn() {
		return new Date(createdOn.getTime());
	}
	
	/**
	 * Sets the created on.
	 *
	 * @param createdOn the new created on
	 */
	public void setCreatedOn(Date createdOn) {
		this.createdOn = new Date(createdOn.getTime());
	}
	
	/**
	 * Gets the last modified on.
	 *
	 * @return the last modified on
	 */
	public Date getLastModifiedOn() {
		return new Date(lastModifiedOn.getTime());
	}
	
	/**
	 * Sets the last modified on.
	 *
	 * @param lastModifiedOn the new last modified on
	 */
	public void setLastModifiedOn(Date lastModifiedOn) {
		this.lastModifiedOn = new Date(lastModifiedOn.getTime());
	}
	
	/**
	 * Gets the schedule template.
	 *
	 * @return the schedule template
	 */
	public ScheduleTemplateObj getScheduleTemplate() {
		return scheduleTemplate;
	}
	
	/**
	 * Sets the schedule template.
	 *
	 * @param scheduleTemplate the new schedule template
	 */
	public void setScheduleTemplate(ScheduleTemplateObj scheduleTemplate) {
		this.scheduleTemplate = scheduleTemplate;
	}

}
