package com.wiley.gr.ace.authorservices.model;

import java.util.Date;

public class ScheduleObj {
	
	private String id;
	private String appId;
	private String description;
	private String createdBy;
	private String modifiedBy;
	private Date createdOn;
	private Date lastModifiedOn;
	private ScheduleTemplateObj scheduleTemplate;

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public Date getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
	public Date getLastModifiedOn() {
		return lastModifiedOn;
	}
	public void setLastModifiedOn(Date lastModifiedOn) {
		this.lastModifiedOn = lastModifiedOn;
	}
	public ScheduleTemplateObj getScheduleTemplate() {
		return scheduleTemplate;
	}
	public void setScheduleTemplate(ScheduleTemplateObj scheduleTemplate) {
		this.scheduleTemplate = scheduleTemplate;
	}

}
