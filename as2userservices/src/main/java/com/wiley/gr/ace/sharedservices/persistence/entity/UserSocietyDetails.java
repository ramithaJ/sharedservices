package com.wiley.gr.ace.sharedservices.persistence.entity;

// Generated May 26, 2015 6:09:14 PM by Hibernate Tools 3.4.0.CR1

import javax.persistence.*;
import java.util.Date;

/**
 * UserSocietyDetails generated by hbm2java
 */
@Entity
@Table(name = "USER_SOCIETY_DETAILS")
public class UserSocietyDetails implements java.io.Serializable {

	private Integer societyId;
	private Users usersByCreatedBy;
	private Users usersByUpdatedBy;
	private AuthorProfile authorProfile;
	private String societyCd;
	private String societyName;
	private String membershipNo;
	private String promoCode;
	private String discountFlgOoOrOaPricing;
	private Date startDt;
	private Date endDt;
	private Date createdDate;
	private Date updatedDate;

	public UserSocietyDetails() {
	}

	public UserSocietyDetails(Integer societyId) {
		this.societyId = societyId;
	}

	public UserSocietyDetails(Integer societyId, Users usersByCreatedBy,
			Users usersByUpdatedBy, AuthorProfile authorProfile,
			String societyCd, String societyName, String membershipNo,
			String promoCode, String discountFlgOoOrOaPricing, Date startDt,
			Date endDt, Date createdDate, Date updatedDate) {
		this.societyId = societyId;
		this.usersByCreatedBy = usersByCreatedBy;
		this.usersByUpdatedBy = usersByUpdatedBy;
		this.authorProfile = authorProfile;
		this.societyCd = societyCd;
		this.societyName = societyName;
		this.membershipNo = membershipNo;
		this.promoCode = promoCode;
		this.discountFlgOoOrOaPricing = discountFlgOoOrOaPricing;
		this.startDt = startDt;
		this.endDt = endDt;
		this.createdDate = createdDate;
		this.updatedDate = updatedDate;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="SOCIETY_SEQ")
	@SequenceGenerator(name="SOCIETY_SEQ",sequenceName="AS2APPUSER.SOCIETY_SEQ",allocationSize=1)
	@Column(name = "SOCIETY_ID", unique = true, nullable = false, precision = 22, scale = 0)
	public Integer getSocietyId() {
		return this.societyId;
	}

	public void setSocietyId(Integer societyId) {
		this.societyId = societyId;
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
	@JoinColumn(name = "USER_ID")
	public AuthorProfile getAuthorProfile() {
		return this.authorProfile;
	}

	public void setAuthorProfile(AuthorProfile authorProfile) {
		this.authorProfile = authorProfile;
	}

	@Column(name = "SOCIETY_CD", length = 14)
	public String getSocietyCd() {
		return this.societyCd;
	}

	public void setSocietyCd(String societyCd) {
		this.societyCd = societyCd;
	}

	@Column(name = "SOCIETY_NAME", length = 1000)
	public String getSocietyName() {
		return this.societyName;
	}

	public void setSocietyName(String societyName) {
		this.societyName = societyName;
	}

	@Column(name = "MEMBERSHIP_NO", length = 50)
	public String getMembershipNo() {
		return this.membershipNo;
	}

	public void setMembershipNo(String membershipNo) {
		this.membershipNo = membershipNo;
	}

	@Column(name = "PROMO_CODE", length = 50)
	public String getPromoCode() {
		return this.promoCode;
	}

	public void setPromoCode(String promoCode) {
		this.promoCode = promoCode;
	}

	@Column(name = "DISCOUNT_FLG_OO_OR_OA_PRICING", length = 2)
	public String getDiscountFlgOoOrOaPricing() {
		return this.discountFlgOoOrOaPricing;
	}

	public void setDiscountFlgOoOrOaPricing(String discountFlgOoOrOaPricing) {
		this.discountFlgOoOrOaPricing = discountFlgOoOrOaPricing;
	}

	@Column(name = "START_DT")
	public Date getStartDt() {
		return this.startDt;
	}

	public void setStartDt(Date startDt) {
		this.startDt = startDt;
	}

	@Column(name = "END_DT")
	public Date getEndDt() {
		return this.endDt;
	}

	public void setEndDt(Date endDt) {
		this.endDt = endDt;
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
