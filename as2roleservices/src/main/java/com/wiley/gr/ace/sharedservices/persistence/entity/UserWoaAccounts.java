package com.wiley.gr.ace.sharedservices.persistence.entity;

// Generated Jul 7, 2015 5:39:57 PM by Hibernate Tools 3.4.0.CR1

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
 * UserWoaAccounts generated by hbm2java
 */
@Entity
@Table(name = "USER_WOA_ACCOUNTS")
public class UserWoaAccounts implements java.io.Serializable {

    private UserWoaAccountsId id;
    private Users usersByCreatedBy;
    private Users usersByUpdatedBy;
    private UserProfile userProfile;
    private String woaInstitutionName;
    private Date createdDate;
    private Date updatedDate;

    public UserWoaAccounts() {
    }

    public UserWoaAccounts(UserWoaAccountsId id, UserProfile userProfile) {
        this.id = id;
        this.userProfile = userProfile;
    }

    public UserWoaAccounts(UserWoaAccountsId id, Users usersByCreatedBy,
            Users usersByUpdatedBy, UserProfile userProfile,
            String woaInstitutionName, Date createdDate, Date updatedDate) {
        this.id = id;
        this.usersByCreatedBy = usersByCreatedBy;
        this.usersByUpdatedBy = usersByUpdatedBy;
        this.userProfile = userProfile;
        this.woaInstitutionName = woaInstitutionName;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
    }

    @EmbeddedId
    @AttributeOverrides({
            @AttributeOverride(name = "userId", column = @Column(name = "USER_ID", nullable = false, precision = 22, scale = 0)),
            @AttributeOverride(name = "woaInstitutionCd", column = @Column(name = "WOA_INSTITUTION_CD", nullable = false, length = 15)) })
    public UserWoaAccountsId getId() {
        return id;
    }

    public void setId(UserWoaAccountsId id) {
        this.id = id;
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
    @JoinColumn(name = "USER_ID", nullable = false, insertable = false, updatable = false)
    public UserProfile getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
    }

    @Column(name = "WOA_INSTITUTION_NAME", length = 250)
    public String getWoaInstitutionName() {
        return woaInstitutionName;
    }

    public void setWoaInstitutionName(String woaInstitutionName) {
        this.woaInstitutionName = woaInstitutionName;
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
