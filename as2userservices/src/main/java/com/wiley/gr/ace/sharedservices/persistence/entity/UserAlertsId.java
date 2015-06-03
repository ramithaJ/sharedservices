package com.wiley.gr.ace.sharedservices.persistence.entity;

// Generated May 26, 2015 6:09:14 PM by Hibernate Tools 3.4.0.CR1

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * UserAlertsId generated by hbm2java
 */
@Embeddable
public class UserAlertsId implements java.io.Serializable {

	private int userId;
	private String alertCd;

	public UserAlertsId() {
	}

	public UserAlertsId(int userId, String alertCd) {
		this.userId = userId;
		this.alertCd = alertCd;
	}

	@Column(name = "USER_ID", nullable = false, precision = 22, scale = 0)
	public int getUserId() {
		return this.userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	@Column(name = "ALERT_CD", nullable = false, length = 15)
	public String getAlertCd() {
		return this.alertCd;
	}

	public void setAlertCd(String alertCd) {
		this.alertCd = alertCd;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof UserAlertsId))
			return false;
		UserAlertsId castOther = (UserAlertsId) other;

		return (this.getUserId() == castOther.getUserId())
				&& ((this.getAlertCd() == castOther.getAlertCd()) || (this
						.getAlertCd() != null && castOther.getAlertCd() != null && this
						.getAlertCd().equals(castOther.getAlertCd())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + this.getUserId();
		result = 37 * result
				+ (getAlertCd() == null ? 0 : this.getAlertCd().hashCode());
		return result;
	}

}
