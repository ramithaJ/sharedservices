package com.wiley.gr.ace.authorservices.model;

import java.util.Date;

public class TemplateObj {
	private String id;
	private String appId;
	private String description;
	private String tagl1;
	private String tagl2;
	private String body;
	private String createdBy;
	private String modifiedBy;
	private Date createdOn;
	private Date lastModifiedOn;

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

	public String getTagl1() {
		return tagl1;
	}

	public void setTagl1(String tagl1) {
		this.tagl1 = tagl1;
	}

	public String getTagl2() {
		return tagl2;
	}

	public void setTagl2(String tagl2) {
		this.tagl2 = tagl2;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
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
}
