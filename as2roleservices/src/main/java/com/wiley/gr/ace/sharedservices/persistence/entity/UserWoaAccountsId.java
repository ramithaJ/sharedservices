package com.wiley.gr.ace.sharedservices.persistence.entity;

// Generated May 26, 2015 6:09:14 PM by Hibernate Tools 3.4.0.CR1

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * UserWoaAccountsId generated by hbm2java
 */
@Embeddable
public class UserWoaAccountsId implements java.io.Serializable {

    private int userId;
    private String woaaccntCd;

    public UserWoaAccountsId() {
    }

    public UserWoaAccountsId(int userId, String woaaccntCd) {
        this.userId = userId;
        this.woaaccntCd = woaaccntCd;
    }

    @Column(name = "USER_ID", nullable = false, precision = 22, scale = 0)
    public int getUserId() {
        return this.userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Column(name = "WOAACCNT_CD", nullable = false, length = 15)
    public String getWoaaccntCd() {
        return this.woaaccntCd;
    }

    public void setWoaaccntCd(String woaaccntCd) {
        this.woaaccntCd = woaaccntCd;
    }

    public boolean equals(Object other) {
        if ((this == other))
            return true;
        if ((other == null))
            return false;
        if (!(other instanceof UserWoaAccountsId))
            return false;
        UserWoaAccountsId castOther = (UserWoaAccountsId) other;

        return (this.getUserId() == castOther.getUserId())
                && ((this.getWoaaccntCd() == castOther.getWoaaccntCd()) || (this
                .getWoaaccntCd() != null
                && castOther.getWoaaccntCd() != null && this
                .getWoaaccntCd().equals(castOther.getWoaaccntCd())));
    }

    public int hashCode() {
        int result = 17;

        result = 37 * result + this.getUserId();
        result = 37
                * result
                + (getWoaaccntCd() == null ? 0 : this.getWoaaccntCd()
                .hashCode());
        return result;
    }

}
