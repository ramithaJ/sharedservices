package com.wiley.gr.ace.auth.security.dao;

// Generated Oct 6, 2015 6:03:54 PM by Hibernate Tools 4.0.0

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * LockedAccountDetails generated by hbm2java
 */
@Entity
@Table(name = "locked_account_details", catalog = "wpp_auth_db")
public class LockedAccountDetails implements java.io.Serializable {

	private String userId;
	private Integer invalidLoginCount;
	private Date lockedTime;
	private String appKey;
	private Date loginAttemptTime;
	private Date createdDate;
	private Integer createdBy;
	private Date updatedDate;
	private Integer updatedBy;

	public LockedAccountDetails() {
	}

	public LockedAccountDetails(final String userId, final Date lockedTime,
			final Date loginAttemptTime, final Date createdDate, final Date updatedDate) {
		this.userId = userId;
		this.lockedTime = lockedTime;
		this.loginAttemptTime = loginAttemptTime;
		this.createdDate = createdDate;
		this.updatedDate = updatedDate;
	}

	public LockedAccountDetails(final String userId, final Integer invalidLoginCount,
			final Date lockedTime, final String appKey, final Date loginAttemptTime,
			final Date createdDate, final Integer createdBy, final Date updatedDate,
			final Integer updatedBy) {
		this.userId = userId;
		this.invalidLoginCount = invalidLoginCount;
		this.lockedTime = lockedTime;
		this.appKey = appKey;
		this.loginAttemptTime = loginAttemptTime;
		this.createdDate = createdDate;
		this.createdBy = createdBy;
		this.updatedDate = updatedDate;
		this.updatedBy = updatedBy;
	}

	@Id
	@Column(name = "USER_ID", unique = true, nullable = false)
	public String getUserId() {
		return this.userId;
	}

	public void setUserId(final String userId) {
		this.userId = userId;
	}

	@Column(name = "INVALID_LOGIN_COUNT")
	public Integer getInvalidLoginCount() {
		return this.invalidLoginCount;
	}

	public void setInvalidLoginCount(final Integer invalidLoginCount) {
		this.invalidLoginCount = invalidLoginCount;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "LOCKED_TIME", nullable = false, length = 19)
	public Date getLockedTime() {
		return this.lockedTime;
	}

	public void setLockedTime(final Date lockedTime) {
		this.lockedTime = lockedTime;
	}

	@Column(name = "APP_KEY")
	public String getAppKey() {
		return this.appKey;
	}

	public void setAppKey(final String appKey) {
		this.appKey = appKey;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "LOGIN_ATTEMPT_TIME", nullable = false, length = 19)
	public Date getLoginAttemptTime() {
		return this.loginAttemptTime;
	}

	public void setLoginAttemptTime(final Date loginAttemptTime) {
		this.loginAttemptTime = loginAttemptTime;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATED_DATE", nullable = false, length = 19)
	public Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(final Date createdDate) {
		this.createdDate = createdDate;
	}

	@Column(name = "CREATED_BY")
	public Integer getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(final Integer createdBy) {
		this.createdBy = createdBy;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UPDATED_DATE", nullable = false, length = 19)
	public Date getUpdatedDate() {
		return this.updatedDate;
	}

	public void setUpdatedDate(final Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	@Column(name = "UPDATED_BY")
	public Integer getUpdatedBy() {
		return this.updatedBy;
	}

	public void setUpdatedBy(final Integer updatedBy) {
		this.updatedBy = updatedBy;
	}

}
