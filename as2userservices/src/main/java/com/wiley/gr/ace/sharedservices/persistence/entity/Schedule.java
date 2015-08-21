package com.wiley.gr.ace.sharedservices.persistence.entity;
// default package
// Generated Jun 23, 2015 4:17:36 PM by Hibernate Tools 3.4.0.CR1

import javax.persistence.*;
import java.util.Date;

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
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Column(name = "APP_ID", length = 25)
    public String getAppId() {
        return this.appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    @Column(name = "DESCRIPTION")
    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "CREATED_BY")
    public String getCreatedBy() {
        return this.createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    @Column(name = "MODIFIED_BY")
    public String getModifiedBy() {
        return this.modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "CREATED_ON", length = 7)
    public Date getCreatedOn() {
        return this.createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "LAST_MODIFIED_ON", length = 7)
    public Date getLastModifiedOn() {
        return this.lastModifiedOn;
    }

    public void setLastModifiedOn(Date lastModifiedOn) {
        this.lastModifiedOn = lastModifiedOn;
    }

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "schedule")
    public ScheduleTemplate getScheduleTemplate() {
        return this.scheduleTemplate;
    }

    public void setScheduleTemplate(ScheduleTemplate scheduleTemplate) {
        this.scheduleTemplate = scheduleTemplate;
    }

}
