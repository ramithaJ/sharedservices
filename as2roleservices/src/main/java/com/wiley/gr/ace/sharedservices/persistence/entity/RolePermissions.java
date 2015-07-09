package com.wiley.gr.ace.sharedservices.persistence.entity;

// Generated Jul 7, 2015 5:39:57 PM by Hibernate Tools 3.4.0.CR1

import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * RolePermissions generated by hbm2java
 */
@Entity
@Table(name = "ROLE_PERMISSIONS")
public class RolePermissions implements java.io.Serializable {

    private RolePermissionsId id;
    private Users usersByCreatedBy;
    private Roles roles;
    private Users usersByUpdatedBy;
    private Permissions permissions;
    private String roleLevelDataAccess;
    private Date createdDate;
    private Date updatedDate;

    public RolePermissions() {
    }

    public RolePermissions(RolePermissionsId id, Roles roles,
            Permissions permissions) {
        this.id = id;
        this.roles = roles;
        this.permissions = permissions;
    }

    public RolePermissions(RolePermissionsId id, Users usersByCreatedBy,
            Roles roles, Users usersByUpdatedBy, Permissions permissions,
            String roleLevelDataAccess, Date createdDate, Date updatedDate) {
        this.id = id;
        this.usersByCreatedBy = usersByCreatedBy;
        this.roles = roles;
        this.usersByUpdatedBy = usersByUpdatedBy;
        this.permissions = permissions;
        this.roleLevelDataAccess = roleLevelDataAccess;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
    }

    @EmbeddedId
    @AttributeOverrides({
            @AttributeOverride(name = "roleId", column = @Column(name = "ROLE_ID", nullable = false, precision = 22, scale = 0)),
            @AttributeOverride(name = "permissionCd", column = @Column(name = "PERMISSION_CD", nullable = false, length = 25)) })
    public RolePermissionsId getId() {
        return id;
    }

    public void setId(RolePermissionsId id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CREATED_BY")
    public Users getUsersByCreatedBy() {
        return usersByCreatedBy;
    }

    public void setUsersByCreatedBy(Users usersByCreatedBy) {
        this.usersByCreatedBy = usersByCreatedBy;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ROLE_ID", nullable = false, insertable = false, updatable = false)
    public Roles getRoles() {
        return roles;
    }

    public void setRoles(Roles roles) {
        this.roles = roles;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UPDATED_BY")
    public Users getUsersByUpdatedBy() {
        return usersByUpdatedBy;
    }

    public void setUsersByUpdatedBy(Users usersByUpdatedBy) {
        this.usersByUpdatedBy = usersByUpdatedBy;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PERMISSION_CD", nullable = false, insertable = false, updatable = false)
    public Permissions getPermissions() {
        return permissions;
    }

    public void setPermissions(Permissions permissions) {
        this.permissions = permissions;
    }

    @Column(name = "ROLE_LEVEL_DATA_ACCESS", length = 500)
    public String getRoleLevelDataAccess() {
        return roleLevelDataAccess;
    }

    public void setRoleLevelDataAccess(String roleLevelDataAccess) {
        this.roleLevelDataAccess = roleLevelDataAccess;
    }

    @Column(name = "CREATED_DATE")
    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    @Column(name = "UPDATED_DATE")
    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

}
