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
 * ProfileAttributeList generated by hbm2java
 */
@Entity
@Table(name = "PROFILE_ATTRIBUTE_LIST")
public class ProfileAttributeList implements java.io.Serializable {

    private String profileAttribCd;
    private Users usersByCreatedBy;
    private Users usersByUpdatedBy;
    private String objName;
    private String objAttribute;
    private String displayName;
    private Date createdDate;
    private Date updatedDate;
    private Set<UserProfileAttribVisible> userProfileAttribVisibles = new HashSet<UserProfileAttribVisible>(
            0);

    public ProfileAttributeList() {
    }

    public ProfileAttributeList(String profileAttribCd) {
        this.profileAttribCd = profileAttribCd;
    }

    public ProfileAttributeList(String profileAttribCd, Users usersByCreatedBy,
            Users usersByUpdatedBy, String objName, String objAttribute,
            String displayName, Date createdDate, Date updatedDate,
            Set<UserProfileAttribVisible> userProfileAttribVisibles) {
        this.profileAttribCd = profileAttribCd;
        this.usersByCreatedBy = usersByCreatedBy;
        this.usersByUpdatedBy = usersByUpdatedBy;
        this.objName = objName;
        this.objAttribute = objAttribute;
        this.displayName = displayName;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
        this.userProfileAttribVisibles = userProfileAttribVisibles;
    }

    @Id
    @Column(name = "PROFILE_ATTRIB_CD", unique = true, nullable = false, length = 15)
    public String getProfileAttribCd() {
        return profileAttribCd;
    }

    public void setProfileAttribCd(String profileAttribCd) {
        this.profileAttribCd = profileAttribCd;
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

    @Column(name = "OBJ_NAME", length = 50)
    public String getObjName() {
        return objName;
    }

    public void setObjName(String objName) {
        this.objName = objName;
    }

    @Column(name = "OBJ_ATTRIBUTE", length = 50)
    public String getObjAttribute() {
        return objAttribute;
    }

    public void setObjAttribute(String objAttribute) {
        this.objAttribute = objAttribute;
    }

    @Column(name = "DISPLAY_NAME", length = 250)
    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "profileAttributeList")
    public Set<UserProfileAttribVisible> getUserProfileAttribVisibles() {
        return userProfileAttribVisibles;
    }

    public void setUserProfileAttribVisibles(
            Set<UserProfileAttribVisible> userProfileAttribVisibles) {
        this.userProfileAttribVisibles = userProfileAttribVisibles;
    }

}
