package com.wiley.gr.ace.sharedservices.persistence.entity;

// Generated May 26, 2015 6:09:14 PM by Hibernate Tools 3.4.0.CR1

import javax.persistence.*;
import java.util.Date;

/**
 * AuthCoauthDetails generated by hbm2java
 */
@Entity
@Table(name = "AUTH_COAUTH_DETAILS")
public class AuthCoauthDetails implements java.io.Serializable {

	private Integer authCoauthId;
	private AuthorProfile authorProfileByAuthorId;
	private AuthorProfile authorProfileByCoauthUserId;
	private Users usersByCreatedBy;
	private Users usersByUpdatedBy;
	private String coauthTitle;
	private String coauthFirstName;
	private String coauthLastName;
	private String coauthEmailAddr;
	private String coauthPhone;
	private String coauthInstitutionCd;
	private String coauthInstitutionName;
	private String coauthDepartmentCd;
	private String coauthDeptName;
	private Date createdDate;
	private Date updatedDate;

	public AuthCoauthDetails() {
	}

	public AuthCoauthDetails(Integer authCoauthId) {
		this.authCoauthId = authCoauthId;
	}

	public AuthCoauthDetails(Integer authCoauthId,
			AuthorProfile authorProfileByAuthorId,
			AuthorProfile authorProfileByCoauthUserId, Users usersByCreatedBy,
			Users usersByUpdatedBy, String coauthTitle, String coauthFirstName,
			String coauthLastName, String coauthEmailAddr, String coauthPhone,
			String coauthInstitutionCd, String coauthInstitutionName,
			String coauthDepartmentCd, String coauthDeptName, Date createdDate,
			Date updatedDate) {
		this.authCoauthId = authCoauthId;
		this.authorProfileByAuthorId = authorProfileByAuthorId;
		this.authorProfileByCoauthUserId = authorProfileByCoauthUserId;
		this.usersByCreatedBy = usersByCreatedBy;
		this.usersByUpdatedBy = usersByUpdatedBy;
		this.coauthTitle = coauthTitle;
		this.coauthFirstName = coauthFirstName;
		this.coauthLastName = coauthLastName;
		this.coauthEmailAddr = coauthEmailAddr;
		this.coauthPhone = coauthPhone;
		this.coauthInstitutionCd = coauthInstitutionCd;
		this.coauthInstitutionName = coauthInstitutionName;
		this.coauthDepartmentCd = coauthDepartmentCd;
		this.coauthDeptName = coauthDeptName;
		this.createdDate = createdDate;
		this.updatedDate = updatedDate;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="AUTH_COAUTH_DETAILS_SEQ")
	@SequenceGenerator(name="AUTH_COAUTH_DETAILS_SEQ",sequenceName="AUTH_COAUTH_DETAILS_SEQ",allocationSize=1)
	@Column(name = "AUTH_COAUTH_ID", unique = true, nullable = false, precision = 22, scale = 0)
	public Integer getAuthCoauthId() {
		return this.authCoauthId;
	}

	public void setAuthCoauthId(Integer authCoauthId) {
		this.authCoauthId = authCoauthId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "AUTHOR_ID")
	public AuthorProfile getAuthorProfileByAuthorId() {
		return this.authorProfileByAuthorId;
	}

	public void setAuthorProfileByAuthorId(AuthorProfile authorProfileByAuthorId) {
		this.authorProfileByAuthorId = authorProfileByAuthorId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "COAUTH_USER_ID")
	public AuthorProfile getAuthorProfileByCoauthUserId() {
		return this.authorProfileByCoauthUserId;
	}

	public void setAuthorProfileByCoauthUserId(
			AuthorProfile authorProfileByCoauthUserId) {
		this.authorProfileByCoauthUserId = authorProfileByCoauthUserId;
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

	@Column(name = "COAUTH_TITLE", length = 50)
	public String getCoauthTitle() {
		return this.coauthTitle;
	}

	public void setCoauthTitle(String coauthTitle) {
		this.coauthTitle = coauthTitle;
	}

	@Column(name = "COAUTH_FIRST_NAME", length = 50)
	public String getCoauthFirstName() {
		return this.coauthFirstName;
	}

	public void setCoauthFirstName(String coauthFirstName) {
		this.coauthFirstName = coauthFirstName;
	}

	@Column(name = "COAUTH_LAST_NAME", length = 50)
	public String getCoauthLastName() {
		return this.coauthLastName;
	}

	public void setCoauthLastName(String coauthLastName) {
		this.coauthLastName = coauthLastName;
	}

	@Column(name = "COAUTH_EMAIL_ADDR", length = 250)
	public String getCoauthEmailAddr() {
		return this.coauthEmailAddr;
	}

	public void setCoauthEmailAddr(String coauthEmailAddr) {
		this.coauthEmailAddr = coauthEmailAddr;
	}

	@Column(name = "COAUTH_PHONE", length = 15)
	public String getCoauthPhone() {
		return this.coauthPhone;
	}

	public void setCoauthPhone(String coauthPhone) {
		this.coauthPhone = coauthPhone;
	}

	@Column(name = "COAUTH_INSTITUTION_CD", length = 10)
	public String getCoauthInstitutionCd() {
		return this.coauthInstitutionCd;
	}

	public void setCoauthInstitutionCd(String coauthInstitutionCd) {
		this.coauthInstitutionCd = coauthInstitutionCd;
	}

	@Column(name = "COAUTH_INSTITUTION_NAME", length = 100)
	public String getCoauthInstitutionName() {
		return this.coauthInstitutionName;
	}

	public void setCoauthInstitutionName(String coauthInstitutionName) {
		this.coauthInstitutionName = coauthInstitutionName;
	}

	@Column(name = "COAUTH_DEPARTMENT_CD", length = 10)
	public String getCoauthDepartmentCd() {
		return this.coauthDepartmentCd;
	}

	public void setCoauthDepartmentCd(String coauthDepartmentCd) {
		this.coauthDepartmentCd = coauthDepartmentCd;
	}

	@Column(name = "COAUTH_DEPT_NAME", length = 100)
	public String getCoauthDeptName() {
		return this.coauthDeptName;
	}

	public void setCoauthDeptName(String coauthDeptName) {
		this.coauthDeptName = coauthDeptName;
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
