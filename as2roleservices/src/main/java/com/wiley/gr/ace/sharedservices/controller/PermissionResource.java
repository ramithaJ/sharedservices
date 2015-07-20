package com.wiley.gr.ace.sharedservices.controller;

import java.util.List;

import org.springframework.hateoas.ResourceSupport;

/**
 * The Class PermissionResource.
 */
public class PermissionResource extends ResourceSupport {

    /**
     * The code.
     */
    private String permissionCd;

    /**
     * The permission name.
     */
    private String permissionName;

    /**
     * The groups.
     */
    private List<String> groups;

    /**
     * Gets the permission cd.
     *
     * @return the permission cd
     */
    public String getPermissionCd() {
        return permissionCd;
    }

    /**
     * Sets the permission cd.
     *
     * @param permissionCd
     *            the new permission cd
     */
    public void setPermissionCd(String permissionCd) {
        this.permissionCd = permissionCd;
    }

    /**
     * Gets the permission name.
     *
     * @return the permission name
     */
    public String getPermissionName() {
        return permissionName;
    }

    /**
     * Sets the permission name.
     *
     * @param permissionName
     *            the new permission name
     */
    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }

    /**
     * Gets the groups.
     *
     * @return the groups
     */
    public List<String> getGroups() {
        return groups;
    }

    /**
     * Sets the groups.
     *
     * @param groups
     *            the new groups
     */
    public void setGroups(List<String> groups) {
        this.groups = groups;
    }

}
