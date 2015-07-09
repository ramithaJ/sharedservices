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
    @Column(name = "AREA_OF_INTEREST_CD", unique = true, nullable = false, length = 15)
    public String getAreaOfInterestCd() {
        return areaOfInterestCd;
    }

    public void setAreaOfInterestCd(String areaOfInterestCd) {
        this.areaOfInterestCd = areaOfInterestCd;
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

    @Column(name = "INTEREST_NAME", length = 250)
    public String getInterestName() {
        return interestName;
    }

    public void setInterestName(String interestName) {
        this.interestName = interestName;
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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "areaOfInterest")
    public Set<UserAreaOfInterest> getUserAreaOfInterests() {
        return userAreaOfInterests;
    }

    public void setUserAreaOfInterests(
            Set<UserAreaOfInterest> userAreaOfInterests) {
        this.userAreaOfInterests = userAreaOfInterests;
    }

}
