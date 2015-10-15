package com.wiley.gr.ace.profile.model.user;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;


@JsonInclude(Include.NON_NULL)
public class UpdateUserAttributes implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@JsonProperty("LastName")
	private String lastName;
	
	
	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String geteCID() {
		return eCID;
	}

	public void seteCID(String eCID) {
		this.eCID = eCID;
	}

	public String getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(String userStatus) {
		this.userStatus = userStatus;
	}

	public String getSourceSystem() {
		return sourceSystem;
	}

	public void setSourceSystem(String sourceSystem) {
		this.sourceSystem = sourceSystem;
	}

	public UserAddress getUserAddress() {
		return userAddress;
	}

	public void setUserAddress(UserAddress userAddress) {
		this.userAddress = userAddress;
	}

	public String getCustomerType() {
		return customerType;
	}

	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}

	public String gettCFlag() {
		return tCFlag;
	}

	public void settCFlag(String tCFlag) {
		this.tCFlag = tCFlag;
	}

	public String getSendEmail() {
		return sendEmail;
	}

	public void setSendEmail(String sendEmail) {
		this.sendEmail = sendEmail;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@JsonProperty("FirstName")
	private String firstName;
	
	
	@JsonProperty("Email")
	private String email;
	
	@JsonProperty("ECID")
	private String eCID;
	
	@JsonProperty("UserStatus")
	private String userStatus;
	
	@JsonProperty("SourceSystem")
	private String sourceSystem;
	
	@JsonProperty("UserAddress")
	private UserAddress userAddress;
	
	@JsonProperty("CustomerType")
	private String customerType;
	
	@JsonProperty("TCFlag")
	private String tCFlag;
	
	@JsonProperty("SendEmail")
	private String sendEmail;

				
}
