package com.wiley.gr.ace.authorservices.model;

public class ProductPersonRelationRequest {
	int UserId;
	String email;
	int dhId;
	String role;
	public int getUserId() {
		return UserId;
	}
	public void setUserId(int userId) {
		UserId = userId;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getDhId() {
		return dhId;
	}
	public void setDhId(int dhId) {
		this.dhId = dhId;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
}
