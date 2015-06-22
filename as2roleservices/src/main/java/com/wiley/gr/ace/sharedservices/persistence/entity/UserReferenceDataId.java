package com.wiley.gr.ace.sharedservices.persistence.entity;

// Generated May 26, 2015 6:09:14 PM by Hibernate Tools 3.4.0.CR1

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * UserReferenceDataId generated by hbm2java
 */
@Embeddable
public class UserReferenceDataId implements java.io.Serializable {

    private int userId;
    private String ecid;

    public UserReferenceDataId() {
    }

    public UserReferenceDataId(int userId, String ecid) {
        this.userId = userId;
        this.ecid = ecid;
    }

    @Column(name = "USER_ID", nullable = false, precision = 22, scale = 0)
    public int getUserId() {
        return this.userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Column(name = "ECID", nullable = false, length = 14)
    public String getEcid() {
        return this.ecid;
    }

    public void setEcid(String ecid) {
        this.ecid = ecid;
    }

    public boolean equals(Object other) {
        if ((this == other))
            return true;
        if ((other == null))
            return false;
        if (!(other instanceof UserReferenceDataId))
            return false;
        UserReferenceDataId castOther = (UserReferenceDataId) other;

        return (this.getUserId() == castOther.getUserId())
                && ((this.getEcid() == castOther.getEcid()) || (this.getEcid() != null
                && castOther.getEcid() != null && this.getEcid()
                .equals(castOther.getEcid())));
    }

    public int hashCode() {
        int result = 17;

        result = 37 * result + this.getUserId();
        result = 37 * result
                + (getEcid() == null ? 0 : this.getEcid().hashCode());
        return result;
    }

}