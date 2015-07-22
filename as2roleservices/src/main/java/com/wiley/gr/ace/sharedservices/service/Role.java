package com.wiley.gr.ace.sharedservices.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.wiley.gr.ace.sharedservices.controller.UserRole;

/**
 * The Class Role.
 */
public class Role {

    /** The role id. */
    private int roleId;

    /** The role name. */
    private String roleName;

    /** The role description. */
    private String roleDescription;

    /** The role type. */
    private String roleType;

    /** The created date. */
    private Date createdDate;

    /** The updated date. */
    private Date updatedDate;

    /** The created by. */
    private int createdBy;

    /** The updated by. */
    private int updatedBy;

    /** The permissions. */
    private List<Permission> permissions = new ArrayList<Permission>();

    /** The permissions. */
    private List<UserRole> users = new ArrayList<UserRole>();

    /**
     * Instantiates a new role.
     */
    public Role() {
    }

    /**
     * Instantiates a new role.
     *
     * @param roleId
     *            the role id
     * @param roleName
     *            the role name
     * @param permissions
     *            the permissions
     */
    public Role(int roleId, String roleName, List<Permission> permissions,
            List<UserRole> usersList) {
        this.roleId = roleId;
        this.roleName = roleName;
        this.permissions = permissions;
        users = usersList;
    }

    /**
     * Instantiates a new role.
     *
     * @param roleId
     *            the role id
     * @param roleName
     *            the role name
     * @param permissions
     *            the permissions
     */
    public Role(int roleId, String roleName, List<Permission> permissions) {
        this.roleId = roleId;
        this.roleName = roleName;
        this.permissions = permissions;
    }

    /**
     * Gets the role id.
     *
     * @return the roleId
     */
    public int getRoleId() {
        return roleId;
    }

    /**
     * Gets the role name.
     *
     * @return the name
     */
    public String getRoleName() {
        return roleName;
    }

    /**
     * Gets the permissions.
     *
     * @return the permissions
     */
    public List<Permission> getPermissions() {
        return permissions;
    }

    /**
     * Sets the role id.
     *
     * @param roleId
     *            the roleId to set
     */
    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    /**
     * Sets the role name.
     *
     * @param roleName
     *            the new role name
     */
    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    /**
     * Gets the role description.
     *
     * @return the roleDescription
     */
    public final String getRoleDescription() {
        return roleDescription;
    }

    /**
     * Sets the role description.
     *
     * @param roleDescription
     *            the roleDescription to set
     */
    public final void setRoleDescription(String roleDescription) {
        this.roleDescription = roleDescription;
    }

    /**
     * Sets the permissions.
     *
     * @param permissions
     *            the permissions to set
     */
    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }

    /**
     * Gets the role type.
     *
     * @return the roleType
     */
    public final String getRoleType() {
        return roleType;
    }

    /**
     * Gets the created date.
     *
     * @return the createdDate
     */
    public final Date getCreatedDate() {
        return createdDate;
    }

    /**
     * Gets the updated date.
     *
     * @return the updatedDate
     */
    public final Date getUpdatedDate() {
        return updatedDate;
    }

    /**
     * Gets the created by.
     *
     * @return the createdBy
     */
    public final int getCreatedBy() {
        return createdBy;
    }

    /**
     * Gets the updated by.
     *
     * @return the updatedBy
     */
    public final int getUpdatedBy() {
        return updatedBy;
    }

    /**
     * Sets the role type.
     *
     * @param roleType
     *            the roleType to set
     */
    public final void setRoleType(String roleType) {
        this.roleType = roleType;
    }

    /**
     * Sets the created date.
     *
     * @param createdDate
     *            the createdDate to set
     */
    public final void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    /**
     * Sets the updated date.
     *
     * @param updatedDate
     *            the updatedDate to set
     */
    public final void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    /**
     * Sets the created by.
     *
     * @param createdBy
     *            the createdBy to set
     */
    public final void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * Sets the updated by.
     *
     * @param updatedBy
     *            the updatedBy to set
     */
    public final void setUpdatedBy(int updatedBy) {
        this.updatedBy = updatedBy;
    }

    /**
     * @return the usersList
     */
    public List<UserRole> getUsers() {
        return users;
    }

    /**
     * @param usersList
     *            the usersList to set
     */
    public void setUsers(List<UserRole> usersList) {
        users = usersList;
    }

}
