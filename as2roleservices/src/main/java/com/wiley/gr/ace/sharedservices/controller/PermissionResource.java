package com.wiley.gr.ace.sharedservices.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.hateoas.ResourceSupport;

/**
 * The Class PermissionResource.
 */
public class PermissionResource extends ResourceSupport {

    /** The code. */
    private String code;

    /** The name. */
    private String name;

    /** The group. */
    private String group;

    /** The publishing date. */
    private String publishingDate;

    /** The role. */
    private RoleResource role;

    /**
     * Gets the code.
     *
     * @return the code
     */
    public String getCode() {
        return code;
    }

    /**
     * Sets the code.
     *
     * @param isbn
     *            the new code
     */
    public void setCode(String isbn) {
        code = isbn;
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

    /**
     * Gets the group.
     *
     * @return the group
     */
    public String getGroup() {
        return group;
    }

    /**
     * Sets the group.
     *
     * @param publisher
     *            the new group
     */
    public void setGroup(String publisher) {
        group = publisher;
    }

    /**
     * Gets the publishing date.
     *
     * @return the publishing date
     */
    public String getPublishingDate() {
        return publishingDate;
    }

    /**
     * Sets the publishing date.
     *
     * @param publishingDate
     *            the new publishing date
     */
    public void setPublishingDate(Date publishingDate) {
        this.publishingDate = new SimpleDateFormat("MMM dd, yyyy")
        .format(publishingDate);
    }

    /**
     * Sets the publishing date.
     *
     * @param publishingDate
     *            the new publishing date
     */
    public void setPublishingDate(String publishingDate) {
        this.publishingDate = publishingDate;
    }

    /**
     * Gets the role.
     *
     * @return the role
     */
    public RoleResource getRole() {
        return role;
    }

    /**
     * Sets the role.
     *
     * @param role
     *            the new role
     */
    public void setRole(RoleResource role) {
        this.role = role;
    }
}
