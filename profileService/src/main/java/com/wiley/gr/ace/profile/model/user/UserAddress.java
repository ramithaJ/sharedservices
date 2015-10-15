package com.wiley.gr.ace.profile.model.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;


@JsonIgnoreProperties(ignoreUnknown = true)
public class UserAddress {

	@JsonProperty("Address")
	private String Address;
	
	@JsonProperty("Street")
	private String Street;
	
	@JsonProperty("State")
	private String State;
	
	@JsonProperty("Country")
	private String Country;
	
	@JsonProperty("ZipCode")
	private String ZipCode;
	
	
	public String getAddress() {
		return Address;
	}
	public void setAddress(String address) {
		Address = address;
	}
	public String getStreet() {
		return Street;
	}
	public void setStreet(String street) {
		Street = street;
	}
	public String getState() {
		return State;
	}
	public void setState(String state) {
		State = state;
	}
	public String getCountry() {
		return Country;
	}
	public void setCountry(String country) {
		Country = country;
	}
	public String getZipCode() {
		return ZipCode;
	}
	public void setZipCode(String zipCode) {
		ZipCode = zipCode;
	}
	
}
