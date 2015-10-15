package com.wiley.gr.ace.profile.model.user;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserPayloads {

	@JsonProperty("UserPayload")
	List<UserPayload> UserPayload;

	public List<UserPayload> getUserPayload() {
		return UserPayload;
	}

	public void setUserPayload(List<UserPayload> userPayload) {
		UserPayload = userPayload;
	}
}
