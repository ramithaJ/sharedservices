package com.wiley.gr.ace.auth.security.dao;

// Generated Jul 7, 2015 5:39:57 PM by Hibernate Tools 3.4.0.CR1

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * InviteResetpwdLog generated by hbm2java
 */
@Entity
@Table(name = "INVITE_RESETPWD_LOG")
public class InviteResetpwdLog implements java.io.Serializable {

	private String guid;
	private String firstName;
	private String lastName;
	private String emailAddress;
	private String userType;
	private String status;
	private String type;

	public InviteResetpwdLog() {
	}

	public InviteResetpwdLog(String guid, String emailAddress) {
		this.guid = guid;
		this.emailAddress = emailAddress;
	}

	public InviteResetpwdLog(String guid, String firstName, String lastName,
			String emailAddress, String userType, String status, String type) {
		this.guid = guid;
		this.firstName = firstName;
		this.lastName = lastName;
		this.emailAddress = emailAddress;
		this.userType = userType;
		this.status = status;
		this.type = type;
	}

	@Id
	@Column(name = "GUID", unique = true, nullable = false, length = 200)
	public String getGuid() {
		return this.guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	@Column(name = "FIRST_NAME", length = 100)
	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	@Column(name = "LAST_NAME", length = 100)
	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Column(name = "EMAIL_ADDRESS", nullable = false, length = 100)
	public String getEmailAddress() {
		return this.emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	@Column(name = "USER_TYPE", length = 20)
	public String getUserType() {
		return this.userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	@Column(name = "STATUS", length = 20)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "TYPE", length = 50)
	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
