package com.wiley.gr.ace.sharedservices.persistence.entity;

// Generated Jul 7, 2015 5:39:57 PM by Hibernate Tools 3.4.0.CR1

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * RolePermissionsId generated by hbm2java
 */
@Embeddable
public class RolePermissionsId implements java.io.Serializable {

    private int roleId;
    private String permissionCd;

    public RolePermissionsId() {
    }

    public RolePermissionsId(int roleId, String permissionCd) {
        this.roleId = roleId;
        this.permissionCd = permissionCd;
    }

    @Column(name = "ROLE_ID", nullable = false, precision = 22, scale = 0)
    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    @Column(name = "PERMISSION_CD", nullable = false, length = 25)
    public String getPermissionCd() {
        return permissionCd;
    }

    public void setPermissionCd(String permissionCd) {
        this.permissionCd = permissionCd;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null) {
            return false;
        }
        if (!(other instanceof RolePermissionsId)) {
            return false;
        }
        final RolePermissionsId castOther = (RolePermissionsId) other;

        return getRoleId() == castOther.getRoleId()
                && (getPermissionCd() == castOther.getPermissionCd() || getPermissionCd() != null
                        && castOther.getPermissionCd() != null
                        && getPermissionCd()
                                .equals(castOther.getPermissionCd()));
    }

    @Override
    public int hashCode() {
        int result = 17;

        result = 37 * result + getRoleId();
        result = 37
                * result
                + (getPermissionCd() == null ? 0 : getPermissionCd().hashCode());
        return result;
    }

}
