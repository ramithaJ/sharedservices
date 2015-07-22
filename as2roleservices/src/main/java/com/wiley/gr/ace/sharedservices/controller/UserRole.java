package com.wiley.gr.ace.sharedservices.controller;

/**
 * The Class NewRole.
 */
public class UserRole {

    /** The name. */
    private int userId;

    /**
     * Instantiates a new new role.
     */
    public UserRole() {
    }

    /**
     * Instantiates a new new role.
     *
     * @param name
     *            the name
     */
    public UserRole(int userId) {
        this.userId = userId;
    }

    /**
     * Gets the name.
     *
     * @return the name
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Sets the name.
     *
     * @param name
     *            the new name
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }
}
