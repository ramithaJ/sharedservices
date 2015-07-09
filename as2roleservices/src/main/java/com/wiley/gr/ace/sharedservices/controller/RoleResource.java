package com.wiley.gr.ace.sharedservices.controller;

import org.springframework.hateoas.ResourceSupport;

// TODO: Auto-generated Javadoc
/**
 * The Class RoleResource.
 */
public class RoleResource extends ResourceSupport {

    /** The role id. */
    private int roleId;

    /** The name. */
    private String name;

    /**
     * Instantiates a new role resource.
     */
    public RoleResource() {
    }

    /**
     * Instantiates a new role resource.
     *
     * @param roleId
     *            the role id
     * @param name
     *            the name
     */
    public RoleResource(int roleId, String name) {
        this.roleId = roleId;
        this.name = name;
    }

    /**
     * Gets the role id.
     *
     * @return the role id
     */
    public int getRoleId() {
        return roleId;
    }

    /**
     * Sets the role id.
     *
     * @param roleId
     *            the new role id
     */
    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    /**
     * Gets the name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name.
     *
     * @param name
     *            the new name
     */
    public void setName(String name) {
        this.name = name;
    }
}
