package com.wiley.gr.ace.sharedservices.service;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * The Class Role.
 */
@JsonInclude()
public class Role {

    /** The role id. */
    private int roleId;

    /** The role name. */
    private String roleName;

    /** The role description. */
    private String roleDescription;

    /** The permissions. */
    private List<Permission> permissions = new ArrayList<Permission>();

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

}
