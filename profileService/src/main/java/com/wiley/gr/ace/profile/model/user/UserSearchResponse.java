package com.wiley.gr.ace.profile.model.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.wiley.gr.ace.profile.model.ErrorResponse;


@JsonIgnoreProperties(ignoreUnknown = true)
public class UserSearchResponse{
	
	
	@JsonProperty("Status")
	private String Status;
	
	@JsonProperty("UserPayloads")
	private UserPayloads UserPayloads;
	
	
	@JsonProperty("Error")
	private ErrorResponse Error;
	
	public ErrorResponse getError() {
		return Error;
	}
	public void setError(ErrorResponse error) {
		Error = error;
	}
	public String getStatus() {
		return Status;
	}
	public void setStatus(String status) {
		Status = status;
	}
	public UserPayloads getUserPayloads() {
		return UserPayloads;
	}
	public void setUserPayloads(UserPayloads userPayloads) {
		UserPayloads = userPayloads;
	}
	
	
}
