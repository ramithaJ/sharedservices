package com.wiley.gr.ace.authorservices.persistence.entity;

// Generated May 29, 2015 5:33:29 PM by Hibernate Tools 3.4.0.CR1

import java.sql.Clob;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Template generated by hbm2java
 */
@Entity
@Table(name = "TEMPLATE")
public class Template implements java.io.Serializable {

	private String id;
	private String appId;
	private String description;
	private String tagl1;
	private String tagl2;
	private Clob body;
	private String createdBy;
	private String modifiedBy;
	private Date createdOn;
	private Date lastModifiedOn;

	public Template() {
	}

	public Template(String id) {
		this.id = id;
	}

	public Template(String id, String appId, String description, String tagl1,
			String tagl2, Clob body, String createdBy, String modifiedBy,
			Date createdOn, Date lastModifiedOn) {
		this.id = id;
		this.appId = appId;
		this.description = description;
		this.tagl1 = tagl1;
		this.tagl2 = tagl2;
		this.body = body;
		this.createdBy = createdBy;
		this.modifiedBy = modifiedBy;
		this.createdOn = createdOn;
		this.lastModifiedOn = lastModifiedOn;
	}

	@Id
	@Column(name = "ID", unique = true, nullable = false)
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "APP_ID", length = 25)
	public String getAppId() {
		return this.appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	@Column(name = "DESCRIPTION")
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "TAGL1")
	public String getTagl1() {
		return this.tagl1;
	}

	public void setTagl1(String tagl1) {
		this.tagl1 = tagl1;
	}

	@Column(name = "TAGL2")
	public String getTagl2() {
		return this.tagl2;
	}

	public void setTagl2(String tagl2) {
		this.tagl2 = tagl2;
	}

	@Column(name = "BODY")
	public Clob getBody() {
		return this.body;
	}

	public void setBody(Clob body) {
		this.body = body;
	}

	@Column(name = "CREATED_BY")
	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name = "MODIFIED_BY")
	public String getModifiedBy() {
		return this.modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "CREATED_ON", length = 7)
	public Date getCreatedOn() {
		return this.createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "LAST_MODIFIED_ON", length = 7)
	public Date getLastModifiedOn() {
		return this.lastModifiedOn;
	}

	public void setLastModifiedOn(Date lastModifiedOn) {
		this.lastModifiedOn = lastModifiedOn;
	}

}