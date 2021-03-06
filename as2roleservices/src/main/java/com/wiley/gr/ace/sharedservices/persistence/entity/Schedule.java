package com.wiley.gr.ace.sharedservices.persistence.entity;

// Generated Jul 7, 2015 5:39:57 PM by Hibernate Tools 3.4.0.CR1

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Schedule generated by hbm2java
 */
@Entity
@Table(name = "SCHEDULE")
public class Schedule implements java.io.Serializable {

    private String id;
    private String appId;
    private String description;
    private String createdBy;
    private String modifiedBy;
    private Date createdOn;
    private Date lastModifiedOn;
    private ScheduleTemplate scheduleTemplate;

    public Schedule() {
    }

    public Schedule(String id) {
        this.id = id;
    }

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

    @Id
    @Column(name = "ID", unique = true, nullable = false)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Column(name = "APP_ID", length = 25)
    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    @Column(name = "DESCRIPTION")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "CREATED_BY")
    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    @Column(name = "MODIFIED_BY")
    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "CREATED_ON", length = 7)
    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "LAST_MODIFIED_ON", length = 7)
    public Date getLastModifiedOn() {
        return lastModifiedOn;
    }

    public void setLastModifiedOn(Date lastModifiedOn) {
        this.lastModifiedOn = lastModifiedOn;
    }

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "schedule")
    public ScheduleTemplate getScheduleTemplate() {
        return scheduleTemplate;
    }

    public void setScheduleTemplate(ScheduleTemplate scheduleTemplate) {
        this.scheduleTemplate = scheduleTemplate;
    }

}
