package com.wiley.gr.ace.sharedservices.persistence.entity;
// default package
// Generated Jun 23, 2015 4:17:36 PM by Hibernate Tools 3.4.0.CR1

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import java.util.Date;

/**
 * AdminProfile generated by hbm2java
 */
@Entity
@Table(name = "ADMIN_PROFILE")
public class AdminProfile implements java.io.Serializable {

    private Integer adminUserId;
    private Users usersByCreatedBy;
    private Users usersByUpdatedBy;
    private Users usersByUserId;
    private Date createdDate;
    private Date updatedDate;

    public AdminProfile() {
    }

    public AdminProfile(Users usersByUserId) {
        this.usersByUserId = usersByUserId;
    }

    public AdminProfile(Users usersByCreatedBy, Users usersByUpdatedBy,
                        Users usersByUserId, Date createdDate, Date updatedDate) {
        this.usersByCreatedBy = usersByCreatedBy;
        this.usersByUpdatedBy = usersByUpdatedBy;
        this.usersByUserId = usersByUserId;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
    }

    @GenericGenerator(name = "generator", strategy = "foreign", parameters = @Parameter(name = "property", value = "usersByUserId"))
    @Id
    @GeneratedValue(generator = "generator")
    @Column(name = "ADMIN_USER_ID", unique = true, nullable = false, precision = 22, scale = 0)
    public Integer getAdminUserId() {
        return this.adminUserId;
    }

    public void setAdminUserId(Integer adminUserId) {
        this.adminUserId = adminUserId;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CREATED_BY")
    public Users getUsersByCreatedBy() {
        return this.usersByCreatedBy;
    }

    public void setUsersByCreatedBy(Users usersByCreatedBy) {
        this.usersByCreatedBy = usersByCreatedBy;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UPDATED_BY")
    public Users getUsersByUpdatedBy() {
        return this.usersByUpdatedBy;
    }

    public void setUsersByUpdatedBy(Users usersByUpdatedBy) {
        this.usersByUpdatedBy = usersByUpdatedBy;
    }

    @OneToOne(fetch = FetchType.LAZY)
    @PrimaryKeyJoinColumn
    public Users getUsersByUserId() {
        return this.usersByUserId;
    }

    public void setUsersByUserId(Users usersByUserId) {
        this.usersByUserId = usersByUserId;
    }

    @Column(name = "CREATED_DATE")
    public Date getCreatedDate() {
        return this.createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    @Column(name = "UPDATED_DATE")
    public Date getUpdatedDate() {
        return this.updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

}
