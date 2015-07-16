package com.wiley.gr.ace.sharedservices.controller;

import org.springframework.hateoas.ResourceSupport;

import java.util.List;

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
     * The role.
     */
    private RoleResource role;

    public String getPermissionCd() {
        return permissionCd;
    }

    public void setPermissionCd(String permissionCd) {
        this.permissionCd = permissionCd;
    }

    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }

    public List<String> getGroups() {
        return groups;
    }

    public void setGroups(List<String> groups) {
        this.groups = groups;
    }

    public RoleResource getRole() {
        return role;
    }

    public void setRole(RoleResource role) {
        this.role = role;
    }
}
