package com.wiley.gr.ace.sharedservices.persistence.entity;

// Generated Jul 7, 2015 5:39:57 PM by Hibernate Tools 3.4.0.CR1

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * UserAffiliations generated by hbm2java
 */
@Entity
@Table(name = "USER_AFFILIATIONS")
public class UserAffiliations implements java.io.Serializable {

    private Integer affiliationId;
    private Users usersByCreatedBy;
    private Users usersByUpdatedBy;
    private UserProfile userProfile;
    private String institutionCd;
    private String institutionName;
    private String departmentCd;
    private String departmentName;
    private String townOrCityName;
    private String stateOrProvinceName;
    private Date startDt;
    private Date endDt;
    private Date createdDate;
    private Date updatedDate;

    public UserAffiliations() {
    }

    public UserAffiliations(Integer affiliationId) {
        this.affiliationId = affiliationId;
    }

    public UserAffiliations(Integer affiliationId, Users usersByCreatedBy,
            Users usersByUpdatedBy, UserProfile userProfile,
            String institutionCd, String institutionName, String departmentCd,
            String departmentName, String townOrCityName,
            String stateOrProvinceName, Date startDt, Date endDt,
            Date createdDate, Date updatedDate) {
        this.affiliationId = affiliationId;
        this.usersByCreatedBy = usersByCreatedBy;
        this.usersByUpdatedBy = usersByUpdatedBy;
        this.userProfile = userProfile;
        this.institutionCd = institutionCd;
        this.institutionName = institutionName;
        this.departmentCd = departmentCd;
        this.departmentName = departmentName;
        this.townOrCityName = townOrCityName;
        this.stateOrProvinceName = stateOrProvinceName;
        this.startDt = startDt;
        this.endDt = endDt;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
    }

    @Id
    @Column(name = "AFFILIATION_ID", unique = true, nullable = false, precision = 22, scale = 0)
    public Integer getAffiliationId() {
        return affiliationId;
    }

    public void setAffiliationId(Integer affiliationId) {
        this.affiliationId = affiliationId;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CREATED_BY")
    public Users getUsersByCreatedBy() {
        return usersByCreatedBy;
    }

    public void setUsersByCreatedBy(Users usersByCreatedBy) {
        this.usersByCreatedBy = usersByCreatedBy;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UPDATED_BY")
    public Users getUsersByUpdatedBy() {
        return usersByUpdatedBy;
    }

    public void setUsersByUpdatedBy(Users usersByUpdatedBy) {
        this.usersByUpdatedBy = usersByUpdatedBy;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    public UserProfile getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
    }

    @Column(name = "INSTITUTION_CD", length = 10)
    public String getInstitutionCd() {
        return institutionCd;
    }

    public void setInstitutionCd(String institutionCd) {
        this.institutionCd = institutionCd;
    }

    @Column(name = "INSTITUTION_NAME", length = 100)
    public String getInstitutionName() {
        return institutionName;
    }

    public void setInstitutionName(String institutionName) {
        this.institutionName = institutionName;
    }

    @Column(name = "DEPARTMENT_CD", length = 10)
    public String getDepartmentCd() {
        return departmentCd;
    }

    public void setDepartmentCd(String departmentCd) {
        this.departmentCd = departmentCd;
    }

    @Column(name = "DEPARTMENT_NAME", length = 100)
    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    @Column(name = "TOWN_OR_CITY_NAME", length = 200)
    public String getTownOrCityName() {
        return townOrCityName;
    }

    public void setTownOrCityName(String townOrCityName) {
        this.townOrCityName = townOrCityName;
    }

    @Column(name = "STATE_OR_PROVINCE_NAME", length = 200)
    public String getStateOrProvinceName() {
        return stateOrProvinceName;
    }

    public void setStateOrProvinceName(String stateOrProvinceName) {
        this.stateOrProvinceName = stateOrProvinceName;
    }

    @Column(name = "START_DT")
    public Date getStartDt() {
        return startDt;
    }

    public void setStartDt(Date startDt) {
        this.startDt = startDt;
    }

    @Column(name = "END_DT")
    public Date getEndDt() {
        return endDt;
    }

    public void setEndDt(Date endDt) {
        this.endDt = endDt;
    }

    @Column(name = "CREATED_DATE")
    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    @Column(name = "UPDATED_DATE")
    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

}
