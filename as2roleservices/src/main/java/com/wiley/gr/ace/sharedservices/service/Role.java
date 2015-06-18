package com.wiley.gr.ace.sharedservices.service;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Role {
    private final int roleId;
    private final String name;
    private final List<Permission> permissions = new CopyOnWriteArrayList<Permission>();

    public Role(int roleId, String name) {
        this.roleId = roleId;
        this.name = name;
    }

    public int getRoleId() {
        return roleId;
    }

    public String getName() {
        return name;
    }

    public List<Permission> getPermissions() {
        return permissions;
    }
}
