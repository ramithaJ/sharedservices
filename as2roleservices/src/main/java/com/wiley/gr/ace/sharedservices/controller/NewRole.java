package com.wiley.gr.ace.sharedservices.controller;

/**
 * The Class NewRole.
 */
public class NewRole {

    /** The name. */
    private String name;

    /**
     * Instantiates a new new role.
     */
    public NewRole() {
    }

    /**
     * Instantiates a new new role.
     *
     * @param name
     *            the name
     */
    public NewRole(String name) {
        this.name = name;
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
