package com.wiley.gr.ace.sharedservices.service;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;
import java.util.List;

/**
 * The Class Permission.
 */
public class Permission {

    /** The code. */
    private String permissionCd;

    /** The permission name. */
    private String permissionName;

    /** The created on. */
    private Date createdOn;

    /** The updated on. */
    private Date updatedOn;

    /** The created by. */
    private Integer createdBy;

    /** The updated by. */
    private Integer updatedBy;

    /** The groups. */
    private List<String> groups;

    /**
     * Instantiates a new permission.
     */
    public Permission() {

    }

    /**
     * Instantiates a new permission.
     *
     * @param permissionCd
     *            the permission cd
     * @param permissionName
     *            the permission name
     */
    public Permission(String permissionCd, String permissionName) {
        this.permissionCd = permissionCd;
        this.permissionName = permissionName;
    }

    /**
     * Instantiates a new permission.
     *
     * @param permissionCd
     *            the permission cd
     * @param permissionName
     *            the permission name
     * @param groups
     *            the groups
     */
    public Permission(String permissionCd, String permissionName,
            List<String> groups) {
        this.permissionCd = permissionCd;
        this.permissionName = permissionName;
        this.groups = groups;
    }

    /**
     * Gets the code.
     *
     * @return the code
     */
    public final String getPermissionCd() {
        return permissionCd;
    }

    /**
     * Gets the name.
     *
     * @return the name
     */
    public final String getPermissionName() {
        return permissionName;
    }

    /**
     * Gets the groups.
     *
     * @return the groups
     */
    public final List<String> getGroups() {
        return groups;
    }

    /**
     * Sets the code.
     *
     * @param permissionCd
     *            the new permission cd
     */
    public final void setPermissionCd(String permissionCd) {
        this.permissionCd = permissionCd;
    }

    /**
     * Sets the name.
     *
     * @param permissionName
     *            the new permission name
     */
    public final void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }

    /**
     * Sets the groups.
     *
     * @param groups
     *            the groups to set
     */
    public final void setGroups(List<String> groups) {
        this.groups = groups;
    }

    /**
     * Gets the created on.
     *
     * @return the createdOn
     */
    @JsonIgnore
    public Date getCreatedOn() {
        return createdOn;
    }

    /**
     * Sets the created on.
     *
     * @param createdOn
     *            the createdOn to set
     */
    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    /**
     * Gets the updated on.
     *
     * @return the updatedOn
     */
    @JsonIgnore
    public Date getUpdatedOn() {
        return updatedOn;
    }

    /**
     * Sets the updated on.
     *
     * @param updatedOn
     *            the updatedOn to set
     */
    public void setUpdatedOn(Date updatedOn) {
        this.updatedOn = updatedOn;
    }

    /**
     * Gets the created by.
     *
     * @return the createdBy
     */
    @JsonIgnore
    public Integer getCreatedBy() {
        return createdBy;
    }

    /**
     * Sets the created by.
     *
     * @param createdBy
     *            the createdBy to set
     */
    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * Gets the updated by.
     *
     * @return the updatedBy
     */
    @JsonIgnore
    public Integer getUpdatedBy() {
        return updatedBy;
    }

    /**
     * Sets the updated by.
     *
     * @param updatedBy
     *            the updatedBy to set
     */
    public void setUpdatedBy(Integer updatedBy) {
        this.updatedBy = updatedBy;
    }

}
