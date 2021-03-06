/*******************************************************************************
 * Copyright (c) 2015 John Wiley & Sons, Inc. All rights reserved.
 *
 * All material contained herein is proprietary to John Wiley & Sons 
 * and its third party suppliers, if any. The methods, techniques and 
 * technical concepts contained herein are considered trade secrets 
 * and confidential and may be protected by intellectual property laws.  
 * Reproduction or distribution of this material, in whole or in part, 
 * is strictly forbidden except by express prior written permission 
 * of John Wiley & Sons.
 *******************************************************************************/
package com.wiley.gr.ace.authorservices.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * The Class TemplateObj.
 * 
 * @author virtusa version 1.0
 */
@JsonInclude(Include.NON_NULL)
public class TemplateObj {

    /** The id. */
    private String id;

    /** The app id. */
    private String appId;

    /** The description. */
    private String description;

    /** The tagl1. */
    private String tagl1;

    /** The tagl2. */
    private String tagl2;

    /** The body. */
    private String body;

    /** The created by. */
    private String createdBy;

    /** The modified by. */
    private String modifiedBy;

    /** The created on. */
    private String createdOn;

    /** The last modified on. */
    private String lastModifiedOn;

    /**
     * Gets the id.
     *
     * @return the id
     */
    public final String getId() {
        return id;
    }

    /**
     * Sets the id.
     *
     * @param id
     *            the new id
     */
    public final void setId(final String id) {
        this.id = id;
    }

    /**
     * Gets the app id.
     *
     * @return the app id
     */
    public final String getAppId() {
        return appId;
    }

    /**
     * Sets the app id.
     *
     * @param appId
     *            the new app id
     */
    public final void setAppId(final String appId) {
        this.appId = appId;
    }

    /**
     * Gets the description.
     *
     * @return the description
     */
    public final String getDescription() {
        return description;
    }

    /**
     * Sets the description.
     *
     * @param description
     *            the new description
     */
    public final void setDescription(final String description) {
        this.description = description;
    }

    /**
     * Gets the tagl1.
     *
     * @return the tagl1
     */
    public final String getTagl1() {
        return tagl1;
    }

    /**
     * Sets the tagl1.
     *
     * @param tagl1
     *            the new tagl1
     */
    public final void setTagl1(final String tagl1) {
        this.tagl1 = tagl1;
    }

    /**
     * Gets the tagl2.
     *
     * @return the tagl2
     */
    public final String getTagl2() {
        return tagl2;
    }

    /**
     * Sets the tagl2.
     *
     * @param tagl2
     *            the new tagl2
     */
    public final void setTagl2(final String tagl2) {
        this.tagl2 = tagl2;
    }

    /**
     * Gets the body.
     *
     * @return the body
     */
    public final String getBody() {
        return body;
    }

    /**
     * Sets the body.
     *
     * @param body
     *            the new body
     */
    public final void setBody(final String body) {
        this.body = body;
    }

    /**
     * Gets the created by.
     *
     * @return the created by
     */
    public final String getCreatedBy() {
        return createdBy;
    }

    /**
     * Sets the created by.
     *
     * @param createdBy
     *            the new created by
     */
    public final void setCreatedBy(final String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * Gets the created on.
     *
     * @return the created on
     */
    public final String getCreatedOn() {
        return createdOn;
    }

    /**
     * Sets the created on.
     *
     * @param createdOn
     *            the new created on
     */
    public final void setCreatedOn(final String createdOn) {
        this.createdOn = createdOn;
    }

    /**
     * Gets the last modified on.
     *
     * @return the last modified on
     */
    public final String getLastModifiedOn() {
        return lastModifiedOn;
    }

    /**
     * Sets the last modified on.
     *
     * @param lastModifiedOn
     *            the new last modified on
     */
    public final void setLastModifiedOn(final String lastModifiedOn) {
        this.lastModifiedOn = lastModifiedOn;
    }

    /**
     * Gets the modified by.
     *
     * @return the modified by
     */
    public final String getModifiedBy() {
        return modifiedBy;
    }

    /**
     * Sets the modified by.
     *
     * @param modifiedBy
     *            the new modified by
     */
    public final void setModifiedBy(final String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

}
