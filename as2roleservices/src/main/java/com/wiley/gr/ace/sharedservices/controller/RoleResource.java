package com.wiley.gr.ace.sharedservices.controller;

import org.springframework.hateoas.ResourceSupport;

public class RoleResource extends ResourceSupport {
    private int roleId;
    private String name;

    public RoleResource() {
    }

    public RoleResource(int roleId, String name) {
        this.roleId = roleId;
        this.name = name;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
