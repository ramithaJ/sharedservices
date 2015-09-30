package com.wiley.gr.ace.auth.security.dao;

// Generated Jul 7, 2015 5:39:57 PM by Hibernate Tools 3.4.0.CR1

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * ObjectTypes generated by hbm2java
 */
@Entity
@Table(name = "OBJECT_TYPES")
public class ObjectTypes implements java.io.Serializable {

	private String objectTypeCd;
	private Users usersByCreatedBy;
	private Users usersByUpdatedBy;
	private String objectType;
	private Date createdDate;
	private Date updatedDate;
	private Set<AdditionalPermissions> additionalPermissionses = new HashSet<AdditionalPermissions>(
			0);

	public ObjectTypes() {
	}

	public ObjectTypes(String objectTypeCd) {
		this.objectTypeCd = objectTypeCd;
	}

	public ObjectTypes(String objectTypeCd, Users usersByCreatedBy,
			Users usersByUpdatedBy, String objectType, Date createdDate,
			Date updatedDate, Set<AdditionalPermissions> additionalPermissionses) {
		this.objectTypeCd = objectTypeCd;
		this.usersByCreatedBy = usersByCreatedBy;
		this.usersByUpdatedBy = usersByUpdatedBy;
		this.objectType = objectType;
		this.createdDate = createdDate;
		this.updatedDate = updatedDate;
		this.additionalPermissionses = additionalPermissionses;
	}

	@Id
	@Column(name = "OBJECT_TYPE_CD", unique = true, nullable = false, length = 15)
	public String getObjectTypeCd() {
		return this.objectTypeCd;
	}

	public void setObjectTypeCd(String objectTypeCd) {
		this.objectTypeCd = objectTypeCd;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CREATED_BY")
	public Users getUsersByCreatedBy() {
		return this.usersByCreatedBy;
	}

	public void setUsersByCreatedBy(Users usersByCreatedBy) {
		this.usersByCreatedBy = usersByCreatedBy;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "UPDATED_BY")
	public Users getUsersByUpdatedBy() {
		return this.usersByUpdatedBy;
	}

	public void setUsersByUpdatedBy(Users usersByUpdatedBy) {
		this.usersByUpdatedBy = usersByUpdatedBy;
	}

	@Column(name = "OBJECT_TYPE", length = 50)
	public String getObjectType() {
		return this.objectType;
	}

	public void setObjectType(String objectType) {
		this.objectType = objectType;
	}

	@Column(name = "CREATED_DATE")
	public Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	@Column(name = "UPDATED_DATE")
	public Date getUpdatedDate() {
		return this.updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "objectTypes")
	public Set<AdditionalPermissions> getAdditionalPermissionses() {
		return this.additionalPermissionses;
	}

	public void setAdditionalPermissionses(
			Set<AdditionalPermissions> additionalPermissionses) {
		this.additionalPermissionses = additionalPermissionses;
	}

}
