package com.wiley.gr.ace.sharedservices.persistence.entity;
// default package
// Generated Jun 23, 2015 4:17:36 PM by Hibernate Tools 3.4.0.CR1

import org.hibernate.annotations.Cascade;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * Societies generated by hbm2java
 */
@Entity
@Table(name = "SOCIETIES")
public class Societies implements java.io.Serializable {

	private Integer societyId;
	private Users usersByCreatedBy;
	private Users usersByUpdatedBy;
	private String societyCd;
	private String societyName;
	private Date createdDate;
	private Date updatedDate;
	private Set<UserSocietyDetails> userSocietyDetailses = new HashSet<UserSocietyDetails>(
			0);

	public Societies() {
	}

	public Societies(Integer societyId) {
		this.societyId = societyId;
	}

	public Societies(Integer societyId, Users usersByCreatedBy,
			Users usersByUpdatedBy, String societyCd, String societyName,
			Date createdDate, Date updatedDate,
			Set<UserSocietyDetails> userSocietyDetailses) {
		this.societyId = societyId;
		this.usersByCreatedBy = usersByCreatedBy;
		this.usersByUpdatedBy = usersByUpdatedBy;
		this.societyCd = societyCd;
		this.societyName = societyName;
		this.createdDate = createdDate;
		this.updatedDate = updatedDate;
		this.userSocietyDetailses = userSocietyDetailses;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="SOCIETIES_SEQ")
	@SequenceGenerator(name="SOCIETIES_SEQ",sequenceName="SOCIETIES_SEQ",allocationSize=1)
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

	@Column(name = "SOCIETY_CD", length = 15)
	public String getSocietyCd() {
		return this.societyCd;
	}

	public void setSocietyCd(String societyCd) {
		this.societyCd = societyCd;
	}

	@Column(name = "SOCIETY_NAME", length = 250)
	public String getSocietyName() {
		return this.societyName;
	}

	public void setSocietyName(String societyName) {
		this.societyName = societyName;
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

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "societies")
    @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
	public Set<UserSocietyDetails> getUserSocietyDetailses() {
		return this.userSocietyDetailses;
	}

	public void setUserSocietyDetailses(
			Set<UserSocietyDetails> userSocietyDetailses) {
		this.userSocietyDetailses = userSocietyDetailses;
	}

}