package com.wiley.gr.ace.sharedservices.controller;

import com.wiley.gr.ace.sharedservices.service.Permission;
import com.wiley.gr.ace.sharedservices.service.Role;
import org.springframework.hateoas.ResourceSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * The Class RoleResource.
 */
public class RoleResource extends ResourceSupport {

    /**
     * The role id.
     */
    private int roleId;

    private String roleName;

    private String roleDescription;

    private String roleType;

    private List<Permission> permissions = new ArrayList<>();

    public RoleResource() {
    }

    public RoleResource(Role role) {
        if (null != role) {
            this.roleId = role.getRoleId();
            this.roleName = role.getRoleName();
            this.roleDescription = role.getRoleDescription();
            this.roleType = role.getRoleType();
            this.permissions = role.getPermissions();
        }
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleDescription() {
        return roleDescription;
    }

    public void setRoleDescription(String roleDescription) {
        this.roleDescription = roleDescription;
    }

    public String getRoleType() {
        return roleType;
    }

    public void setRoleType(String roleType) {
        this.roleType = roleType;
    }

    public List<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }
}
