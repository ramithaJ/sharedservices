package com.wiley.gr.ace.sharedservices.service;

import java.util.List;

/**
 * The Class Permission.
 */
public class Permission {

    /** The code. */
    private String code;

    /** The name. */
    private String name;

    /** The groups. */
    private List<String> groups;

    /**
     * Instantiates a new permission.
     *
     * @param code
     *            the code
     * @param name
     *            the name
     */
    public Permission(String code, String name) {
        this.code = code;
        this.name = name;
    }

    /**
     * Instantiates a new permission.
     *
     * @param code
     *            the code
     * @param name
     *            the name
     * @param groups
     *            the groups
     */
    public Permission(String code, String name, List<String> groups) {
        this.code = code;
        this.name = name;
        this.groups = groups;
    }

    /**
     * Gets the code.
     *
     * @return the code
     */
    public final String getCode() {
        return code;
    }

    /**
     * Gets the name.
     *
     * @return the name
     */
    public final String getName() {
        return name;
    }

    /**
     * Gets the groups.
     *
     * @return the groups
     */
    public final List<String> getGroups() {
        return groups;
    }

    /**
     * Sets the code.
     *
     * @param code
     *            the code to set
     */
    public final void setCode(String code) {
        this.code = code;
    }

    /**
     * Sets the name.
     *
     * @param name
     *            the name to set
     */
    public final void setName(String name) {
        this.name = name;
    }

    /**
     * Sets the groups.
     *
     * @param groups
     *            the groups to set
     */
    public final void setGroups(List<String> groups) {
        this.groups = groups;
    }

}
