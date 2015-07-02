package com.wiley.gr.ace.authorservices.model;

public class ProductPersonRelationRequest {
	String UserId;
	String email;
	String dhId;
	String role;
	public String getUserId() {
		return UserId;
	}
	public void setUserId(String userId) {
		UserId = userId;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getDhId() {
		return dhId;
	}
	public void setDhId(String dhId) {
		this.dhId = dhId;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
}
