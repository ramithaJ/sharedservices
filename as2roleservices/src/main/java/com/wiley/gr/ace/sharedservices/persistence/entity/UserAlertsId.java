package com.wiley.gr.ace.sharedservices.persistence.entity;

// Generated Jul 7, 2015 5:39:57 PM by Hibernate Tools 3.4.0.CR1

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * UserAlertsId generated by hbm2java
 */
@Embeddable
public class UserAlertsId implements java.io.Serializable {

    private Integer userId;
    private String alertCd;

    public UserAlertsId() {
    }

    public UserAlertsId(Integer userId, String alertCd) {
        this.userId = userId;
        this.alertCd = alertCd;
    }

    @Column(name = "USER_ID", nullable = false, precision = 22, scale = 0)
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Column(name = "ALERT_CD", nullable = false, length = 15)
    public String getAlertCd() {
        return alertCd;
    }

    public void setAlertCd(String alertCd) {
        this.alertCd = alertCd;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null) {
            return false;
        }
        if (!(other instanceof UserAlertsId)) {
            return false;
        }
        final UserAlertsId castOther = (UserAlertsId) other;

        return (getUserId() == castOther.getUserId() || getUserId() != null
                && castOther.getUserId() != null
                && getUserId().equals(castOther.getUserId()))
                && (getAlertCd() == castOther.getAlertCd() || getAlertCd() != null
                        && castOther.getAlertCd() != null
                        && getAlertCd().equals(castOther.getAlertCd()));
    }

    @Override
    public int hashCode() {
        int result = 17;

        result = 37 * result
                + (getUserId() == null ? 0 : getUserId().hashCode());
        result = 37 * result
                + (getAlertCd() == null ? 0 : getAlertCd().hashCode());
        return result;
    }

}
