package com.wiley.gr.ace.authorservices.persistence.entity;

// Generated Jun 26, 2015 10:24:59 AM by Hibernate Tools 4.0.0

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
		return this.userId;
	}

	public void setUserId(Integer userId) {
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

		return ((this.getUserId() == castOther.getUserId()) || (this
				.getUserId() != null && castOther.getUserId() != null && this
				.getUserId().equals(castOther.getUserId())))
				&& ((this.getAlertCd() == castOther.getAlertCd()) || (this
						.getAlertCd() != null && castOther.getAlertCd() != null && this
						.getAlertCd().equals(castOther.getAlertCd())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getUserId() == null ? 0 : this.getUserId().hashCode());
		result = 37 * result
				+ (getAlertCd() == null ? 0 : this.getAlertCd().hashCode());
		return result;
	}

}
