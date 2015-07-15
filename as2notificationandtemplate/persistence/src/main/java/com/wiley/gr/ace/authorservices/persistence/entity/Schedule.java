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
package com.wiley.gr.ace.authorservices.persistence.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

/**
 * Schedule generated by hbm2java.
 * 
 * @author virtusa version 1.0
 */
@Entity
@Table(name = "SCHEDULE")
public class Schedule implements java.io.Serializable {

    /** The id. */
    private String id;

    /** The app id. */
    private String appId;

    /** The description. */
    private String description;

    /** The created by. */
    private String createdBy;

    /** The modified by. */
    private String modifiedBy;

    /** The created on. */
    private Date createdOn;

    /** The last modified on. */
    private Date lastModifiedOn;

    /** The schedule template. */

    private ScheduleTemplate scheduleTemplate;

    /**
     * Instantiates a new schedule.
     */
    public Schedule() {
    }

    /**
     * Instantiates a new schedule.
     *
     * @param id
     *            the id
     */
    public Schedule(String id) {
        this.id = id;
    }

    /**
     * Instantiates a new schedule.
     *
     * @param id
     *            the id
     * @param appId
     *            the app id
     * @param description
     *            the description
     * @param createdBy
     *            the created by
     * @param modifiedBy
     *            the modified by
     * @param createdOn
     *            the created on
     * @param lastModifiedOn
     *            the last modified on
     * @param scheduleTemplate
     *            the schedule template
     */
    public Schedule(String id, String appId, String description,
            String createdBy, String modifiedBy, Date createdOn,
            Date lastModifiedOn, ScheduleTemplate scheduleTemplate) {
        this.id = id;
        this.appId = appId;
        this.description = description;
        this.createdBy = createdBy;
        this.modifiedBy = modifiedBy;
        this.createdOn = createdOn;
        this.lastModifiedOn = lastModifiedOn;
        this.scheduleTemplate = scheduleTemplate;
    }

    /**
     * Gets the id.
     *
     * @return the id
     */
    @Id
    @Column(name = "ID", unique = true, nullable = false)
    public String getId() {
        return this.id;
    }

    /**
     * Sets the id.
     *
     * @param id
     *            the new id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets the app id.
     *
     * @return the app id
     */
    @Column(name = "APP_ID", length = 25)
    public String getAppId() {
        return this.appId;
    }

    /**
     * Sets the app id.
     *
     * @param appId
     *            the new app id
     */
    public void setAppId(String appId) {
        this.appId = appId;
    }

    /**
     * Gets the description.
     *
     * @return the description
     */
    @Column(name = "DESCRIPTION")
    public String getDescription() {
        return this.description;
    }

    /**
     * Sets the description.
     *
     * @param description
     *            the new description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the created by.
     *
     * @return the created by
     */
    @Column(name = "CREATED_BY")
    public String getCreatedBy() {
        return this.createdBy;
    }

    /**
     * Sets the created by.
     *
     * @param createdBy
     *            the new created by
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * Gets the modified by.
     *
     * @return the modified by
     */
    @Column(name = "MODIFIED_BY")
    public String getModifiedBy() {
        return this.modifiedBy;
    }

    /**
     * Sets the modified by.
     *
     * @param modifiedBy
     *            the new modified by
     */
    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    /**
     * Gets the created on.
     *
     * @return the created on
     */
    @Temporal(TemporalType.DATE)
    @Column(name = "CREATED_ON", length = 7)
    public Date getCreatedOn() {
        return this.createdOn;
    }

    /**
     * Sets the created on.
     *
     * @param createdOn
     *            the new created on
     */
    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    /**
     * Gets the last modified on.
     *
     * @return the last modified on
     */
    @Temporal(TemporalType.DATE)
    @Column(name = "LAST_MODIFIED_ON", length = 7)
    public Date getLastModifiedOn() {
        return this.lastModifiedOn;
    }

    /**
     * Sets the last modified on.
     *
     * @param lastModifiedOn
     *            the new last modified on
     */
    public void setLastModifiedOn(Date lastModifiedOn) {
        this.lastModifiedOn = lastModifiedOn;
    }

    /**
     * Gets the schedule template.
     *
     * @return the schedule template
     */

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "schedule", orphanRemoval = true)
    @Cascade(CascadeType.ALL)
    public ScheduleTemplate getScheduleTemplate() {
        return this.scheduleTemplate;
    }

    /**
     * Sets the schedule template.
     *
     * @param scheduleTemplate
     *            the new schedule template
     */
    public void setScheduleTemplate(ScheduleTemplate scheduleTemplate) {
        this.scheduleTemplate = scheduleTemplate;
    }

}
