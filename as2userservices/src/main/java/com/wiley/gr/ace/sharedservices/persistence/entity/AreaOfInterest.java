package com.wiley.gr.ace.sharedservices.persistence.entity;

// Generated May 26, 2015 6:09:14 PM by Hibernate Tools 3.4.0.CR1

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * AreaOfInterest generated by hbm2java
 */
@Entity
@Table(name = "AREA_OF_INTEREST")
public class AreaOfInterest implements java.io.Serializable {

	private String areaOfInterestCd;
	private Users usersByCreatedBy;
	private Users usersByUpdatedBy;
	private String interestName;
	private Date createdDate;
	private Date updatedDate;
	private Set<UserAreaOfInterest> userAreaOfInterests = new HashSet<UserAreaOfInterest>(
			0);

	public AreaOfInterest() {
	}

	public AreaOfInterest(String areaOfInterestCd) {
		this.areaOfInterestCd = areaOfInterestCd;
	}

	public AreaOfInterest(String areaOfInterestCd, Users usersByCreatedBy,
			Users usersByUpdatedBy, String interestName, Date createdDate,
			Date updatedDate, Set<UserAreaOfInterest> userAreaOfInterests) {
		this.areaOfInterestCd = areaOfInterestCd;
		this.usersByCreatedBy = usersByCreatedBy;
		this.usersByUpdatedBy = usersByUpdatedBy;
		this.interestName = interestName;
		this.createdDate = createdDate;
		this.updatedDate = updatedDate;
		this.userAreaOfInterests = userAreaOfInterests;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="USER_SECONDARY_EMAIL_ADDR_SEQ")
	@SequenceGenerator(name="USER_SECONDARY_EMAIL_ADDR_SEQ",sequenceName="AS2APPUSER.USER_SECONDARY_EMAIL_ADDR_SEQ",allocationSize=1)
	@Column(name = "AREA_OF_INTEREST_CD", unique = true, nullable = false, length = 15)
	public String getAreaOfInterestCd() {
		return this.areaOfInterestCd;
	}

	public void setAreaOfInterestCd(String areaOfInterestCd) {
		this.areaOfInterestCd = areaOfInterestCd;
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

	@Column(name = "INTEREST_NAME", length = 250)
	public String getInterestName() {
		return this.interestName;
	}

	public void setInterestName(String interestName) {
		this.interestName = interestName;
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

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "areaOfInterest")
	public Set<UserAreaOfInterest> getUserAreaOfInterests() {
		return this.userAreaOfInterests;
	}

	public void setUserAreaOfInterests(
			Set<UserAreaOfInterest> userAreaOfInterests) {
		this.userAreaOfInterests = userAreaOfInterests;
	}

}
