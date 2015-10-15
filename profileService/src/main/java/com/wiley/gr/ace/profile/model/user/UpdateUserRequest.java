package com.wiley.gr.ace.profile.model.user;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UpdateUserRequest implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	UpdateUserAttributes UpdateUserAttributes;

	
	@JsonProperty("UpdateUserAttributes")
	public UpdateUserAttributes getUpdateUserAttributes() {
		return UpdateUserAttributes;
	}

	public void setUpdateUserAttributes(UpdateUserAttributes updateUserAttributes) {
		UpdateUserAttributes = updateUserAttributes;
	}
}
