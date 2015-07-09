package com.wiley.gr.ace.sharedservices.persistence.entity;

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
 * UserSecondaryEmailAddr generated by hbm2java
 */
@Entity
@Table(name = "USER_SECONDARY_EMAIL_ADDR")
public class UserSecondaryEmailAddr implements java.io.Serializable {

    private Integer userSecondaryEmailId;
    private Users usersByCreatedBy;
    private Users usersByUpdatedBy;
    private Users usersByUserId;
    private String secondaryEmailAddr;
    private Date createdDate;
    private Date updatedDate;
    private Set<UserProfile> userProfiles = new HashSet<UserProfile>(0);

    public UserSecondaryEmailAddr() {
    }

    public UserSecondaryEmailAddr(Integer userSecondaryEmailId) {
        this.userSecondaryEmailId = userSecondaryEmailId;
    }

    public UserSecondaryEmailAddr(Integer userSecondaryEmailId,
            Users usersByCreatedBy, Users usersByUpdatedBy,
            Users usersByUserId, String secondaryEmailAddr, Date createdDate,
            Date updatedDate, Set<UserProfile> userProfiles) {
        this.userSecondaryEmailId = userSecondaryEmailId;
        this.usersByCreatedBy = usersByCreatedBy;
        this.usersByUpdatedBy = usersByUpdatedBy;
        this.usersByUserId = usersByUserId;
        this.secondaryEmailAddr = secondaryEmailAddr;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
        this.userProfiles = userProfiles;
    }

    @Id
    @Column(name = "USER_SECONDARY_EMAIL_ID", unique = true, nullable = false, precision = 22, scale = 0)
    public Integer getUserSecondaryEmailId() {
        return userSecondaryEmailId;
    }

    public void setUserSecondaryEmailId(Integer userSecondaryEmailId) {
        this.userSecondaryEmailId = userSecondaryEmailId;
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
    public Users getUsersByUserId() {
        return usersByUserId;
    }

    public void setUsersByUserId(Users usersByUserId) {
        this.usersByUserId = usersByUserId;
    }

    @Column(name = "SECONDARY_EMAIL_ADDR", length = 100)
    public String getSecondaryEmailAddr() {
        return secondaryEmailAddr;
    }

    public void setSecondaryEmailAddr(String secondaryEmailAddr) {
        this.secondaryEmailAddr = secondaryEmailAddr;
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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "userSecondaryEmailAddr")
    public Set<UserProfile> getUserProfiles() {
        return userProfiles;
    }

    public void setUserProfiles(Set<UserProfile> userProfiles) {
        this.userProfiles = userProfiles;
    }

}
