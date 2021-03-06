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
 * UserFunderGrants generated by hbm2java
 */
@Entity
@Table(name = "USER_FUNDER_GRANTS")
public class UserFunderGrants implements java.io.Serializable {

    private Integer funderGrantId;
    private Users usersByCreatedBy;
    private Users usersByUpdatedBy;
    private UserFunders userFunders;
    private String grantNum;
    private Date createdDate;
    private Date updatedDate;

    public UserFunderGrants() {
    }

    public UserFunderGrants(Integer funderGrantId) {
        this.funderGrantId = funderGrantId;
    }

    public UserFunderGrants(Integer funderGrantId, Users usersByCreatedBy,
            Users usersByUpdatedBy, UserFunders userFunders, String grantNum,
            Date createdDate, Date updatedDate) {
        this.funderGrantId = funderGrantId;
        this.usersByCreatedBy = usersByCreatedBy;
        this.usersByUpdatedBy = usersByUpdatedBy;
        this.userFunders = userFunders;
        this.grantNum = grantNum;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
    }

    @Id
    @Column(name = "FUNDER_GRANT_ID", unique = true, nullable = false, precision = 22, scale = 0)
    public Integer getFunderGrantId() {
        return funderGrantId;
    }

    public void setFunderGrantId(Integer funderGrantId) {
        this.funderGrantId = funderGrantId;
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
    @JoinColumn(name = "USER_FUNDER_ID")
    public UserFunders getUserFunders() {
        return userFunders;
    }

    public void setUserFunders(UserFunders userFunders) {
        this.userFunders = userFunders;
    }

    @Column(name = "GRANT_NUM", length = 100)
    public String getGrantNum() {
        return grantNum;
    }

    public void setGrantNum(String grantNum) {
        this.grantNum = grantNum;
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
