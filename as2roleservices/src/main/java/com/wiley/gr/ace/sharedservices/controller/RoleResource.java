package com.wiley.gr.ace.sharedservices.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.hateoas.ResourceSupport;

import com.wiley.gr.ace.sharedservices.service.Permission;
import com.wiley.gr.ace.sharedservices.service.Role;

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

    private List<UserRole> users = new ArrayList<>();

    public RoleResource() {
    }

    public RoleResource(Role role) {
        if (null != role) {
            roleId = role.getRoleId();
            roleName = role.getRoleName();
            roleDescription = role.getRoleDescription();
            roleType = role.getRoleType();
            permissions = role.getPermissions();
            users = role.getUsers();
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

    /**
     * @return the userRole
     */
    public List<UserRole> getUsers() {
        return users;
    }

    /**
     * @param userRole
     *            the userRole to set
     */
    public void setUsers(List<UserRole> userRole) {
        users = userRole;
    }
}
