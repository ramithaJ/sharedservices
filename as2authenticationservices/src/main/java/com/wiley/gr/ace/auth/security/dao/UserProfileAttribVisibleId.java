package com.wiley.gr.ace.auth.security.dao;

// Generated Jul 7, 2015 5:39:57 PM by Hibernate Tools 3.4.0.CR1

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * UserProfileAttribVisibleId generated by hbm2java
 */
@Embeddable
public class UserProfileAttribVisibleId implements java.io.Serializable {

	private int userId;
	private String profileAttribCd;

	public UserProfileAttribVisibleId() {
	}

	public UserProfileAttribVisibleId(int userId, String profileAttribCd) {
		this.userId = userId;
		this.profileAttribCd = profileAttribCd;
	}

	@Column(name = "USER_ID", nullable = false, precision = 22, scale = 0)
	public int getUserId() {
		return this.userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	@Column(name = "PROFILE_ATTRIB_CD", nullable = false, length = 15)
	public String getProfileAttribCd() {
		return this.profileAttribCd;
	}

	public void setProfileAttribCd(String profileAttribCd) {
		this.profileAttribCd = profileAttribCd;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof UserProfileAttribVisibleId))
			return false;
		UserProfileAttribVisibleId castOther = (UserProfileAttribVisibleId) other;

		return (this.getUserId() == castOther.getUserId())
				&& ((this.getProfileAttribCd() == castOther
						.getProfileAttribCd()) || (this.getProfileAttribCd() != null
						&& castOther.getProfileAttribCd() != null && this
						.getProfileAttribCd().equals(
								castOther.getProfileAttribCd())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + this.getUserId();
		result = 37
				* result
				+ (getProfileAttribCd() == null ? 0 : this.getProfileAttribCd()
						.hashCode());
		return result;
	}

}
