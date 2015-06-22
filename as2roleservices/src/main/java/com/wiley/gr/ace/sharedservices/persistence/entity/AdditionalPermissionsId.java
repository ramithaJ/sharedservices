package com.wiley.gr.ace.sharedservices.persistence.entity;

// Generated May 26, 2015 6:09:14 PM by Hibernate Tools 3.4.0.CR1

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * AdditionalPermissionsId generated by hbm2java
 */
@Embeddable
public class AdditionalPermissionsId implements java.io.Serializable {

    private Integer userId;
    private Integer articleId;
    private String permissionCd;

    public AdditionalPermissionsId() {
    }

    public AdditionalPermissionsId(Integer userId, Integer articleId,
                                   String permissionCd) {
        this.userId = userId;
        this.articleId = articleId;
        this.permissionCd = permissionCd;
    }

    @Column(name = "USER_ID", nullable = false, precision = 22, scale = 0)
    public Integer getUserId() {
        return this.userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Column(name = "ARTICLE_ID", nullable = false, precision = 22, scale = 0)
    public Integer getArticleId() {
        return this.articleId;
    }

    public void setArticleId(Integer articleId) {
        this.articleId = articleId;
    }

    @Column(name = "PERMISSION_CD", nullable = false, length = 25)
    public String getPermissionCd() {
        return this.permissionCd;
    }

    public void setPermissionCd(String permissionCd) {
        this.permissionCd = permissionCd;
    }

    public boolean equals(Object other) {
        if ((this == other))
            return true;
        if ((other == null))
            return false;
        if (!(other instanceof AdditionalPermissionsId))
            return false;
        AdditionalPermissionsId castOther = (AdditionalPermissionsId) other;

        return ((this.getUserId() == castOther.getUserId()) || (this
                .getUserId() != null && castOther.getUserId() != null && this
                .getUserId().equals(castOther.getUserId())))
                && ((this.getArticleId() == castOther.getArticleId()) || (this
                .getArticleId() != null
                && castOther.getArticleId() != null && this
                .getArticleId().equals(castOther.getArticleId())))
                && ((this.getPermissionCd() == castOther.getPermissionCd()) || (this
                .getPermissionCd() != null
                && castOther.getPermissionCd() != null && this
                .getPermissionCd().equals(castOther.getPermissionCd())));
    }

    public int hashCode() {
        int result = 17;

        result = 37 * result
                + (getUserId() == null ? 0 : this.getUserId().hashCode());
        result = 37 * result
                + (getArticleId() == null ? 0 : this.getArticleId().hashCode());
        result = 37
                * result
                + (getPermissionCd() == null ? 0 : this.getPermissionCd()
                .hashCode());
        return result;
    }

}