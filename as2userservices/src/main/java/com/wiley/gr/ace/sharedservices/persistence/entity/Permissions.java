package com.wiley.gr.ace.sharedservices.persistence.entity;
// default package
// Generated Jun 23, 2015 4:17:36 PM by Hibernate Tools 3.4.0.CR1

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Permissions generated by hbm2java
 */
@Entity
@Table(name = "PERMISSIONS")
public class Permissions implements java.io.Serializable {

    private String permissionCd;
    private Users usersByCreatedBy;
    private Users usersByUpdatedBy;
    private String permissionName;
    private Date createdDate;
    private Date updatedDate;
    private Set<PermissionGroups> permissionGroupses = new HashSet<PermissionGroups>(
            0);
    private Set<RolePermissions> rolePermissionses = new HashSet<RolePermissions>(
            0);
    private Set<AdditionalPermissions> additionalPermissionses = new HashSet<AdditionalPermissions>(
            0);

    public Permissions() {
    }

    public Permissions(String permissionCd) {
        this.permissionCd = permissionCd;
    }

    public Permissions(String permissionCd, Users usersByCreatedBy,
                       Users usersByUpdatedBy, String permissionName, Date createdDate,
                       Date updatedDate, Set<PermissionGroups> permissionGroupses,
                       Set<RolePermissions> rolePermissionses,
                       Set<AdditionalPermissions> additionalPermissionses) {
        this.permissionCd = permissionCd;
        this.usersByCreatedBy = usersByCreatedBy;
        this.usersByUpdatedBy = usersByUpdatedBy;
        this.permissionName = permissionName;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
        this.permissionGroupses = permissionGroupses;
        this.rolePermissionses = rolePermissionses;
        this.additionalPermissionses = additionalPermissionses;
    }

    @Id
    @Column(name = "PERMISSION_CD", unique = true, nullable = false, length = 25)
    public String getPermissionCd() {
        return this.permissionCd;
    }

    public void setPermissionCd(String permissionCd) {
        this.permissionCd = permissionCd;
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

    @Column(name = "PERMISSION_NAME", length = 250)
    public String getPermissionName() {
        return this.permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "permissions")
    public Set<PermissionGroups> getPermissionGroupses() {
        return this.permissionGroupses;
    }

    public void setPermissionGroupses(Set<PermissionGroups> permissionGroupses) {
        this.permissionGroupses = permissionGroupses;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "permissions")
    public Set<RolePermissions> getRolePermissionses() {
        return this.rolePermissionses;
    }

    public void setRolePermissionses(Set<RolePermissions> rolePermissionses) {
        this.rolePermissionses = rolePermissionses;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "permissions")
    public Set<AdditionalPermissions> getAdditionalPermissionses() {
        return this.additionalPermissionses;
    }

    public void setAdditionalPermissionses(
            Set<AdditionalPermissions> additionalPermissionses) {
        this.additionalPermissionses = additionalPermissionses;
    }

}
