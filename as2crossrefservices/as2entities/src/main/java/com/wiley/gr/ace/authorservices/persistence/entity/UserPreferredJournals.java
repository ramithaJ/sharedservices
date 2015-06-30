package com.wiley.gr.ace.authorservices.persistence.entity;

// Generated Jun 26, 2015 10:24:59 AM by Hibernate Tools 4.0.0

import java.util.Date;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * UserPreferredJournals generated by hbm2java
 */
@Entity
@Table(name = "USER_PREFERRED_JOURNALS")
public class UserPreferredJournals implements java.io.Serializable {

	private UserPreferredJournalsId id;
	private Users usersByCreatedBy;
	private Users usersByUpdatedBy;
	private Products products;
	private UserProfile userProfile;
	private Date createdDate;
	private Date updatedDate;

	public UserPreferredJournals() {
	}

	public UserPreferredJournals(UserPreferredJournalsId id, Products products,
			UserProfile userProfile) {
		this.id = id;
		this.products = products;
		this.userProfile = userProfile;
	}

	public UserPreferredJournals(UserPreferredJournalsId id,
			Users usersByCreatedBy, Users usersByUpdatedBy, Products products,
			UserProfile userProfile, Date createdDate, Date updatedDate) {
		this.id = id;
		this.usersByCreatedBy = usersByCreatedBy;
		this.usersByUpdatedBy = usersByUpdatedBy;
		this.products = products;
		this.userProfile = userProfile;
		this.createdDate = createdDate;
		this.updatedDate = updatedDate;
	}

	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "userId", column = @Column(name = "USER_ID", nullable = false, precision = 22, scale = 0)),
			@AttributeOverride(name = "dhId", column = @Column(name = "DH_ID", nullable = false, precision = 22, scale = 0)) })
	public UserPreferredJournalsId getId() {
		return this.id;
	}

	public void setId(UserPreferredJournalsId id) {
		this.id = id;
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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "DH_ID", nullable = false, insertable = false, updatable = false)
	public Products getProducts() {
		return this.products;
	}

	public void setProducts(Products products) {
		this.products = products;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_ID", nullable = false, insertable = false, updatable = false)
	public UserProfile getUserProfile() {
		return this.userProfile;
	}

	public void setUserProfile(UserProfile userProfile) {
		this.userProfile = userProfile;
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

}