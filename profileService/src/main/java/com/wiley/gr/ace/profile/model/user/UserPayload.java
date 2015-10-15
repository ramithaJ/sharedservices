package com.wiley.gr.ace.profile.model.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserPayload {
	
    @JsonProperty("LastName")
	private String LastName;
    
    @JsonProperty("FirstName")
	private String FirstName;
    
    @JsonProperty("Email")    
	private String Email;
    
    @JsonProperty("ECID")
	private String ECID;
    
    @JsonProperty("UserStatus")
	private String UserStatus;
    
    @JsonProperty("SourceSystem")
	private String SourceSystem;
    
    @JsonProperty("CustomerType")
	private String CustomerType;
    
    @JsonProperty("TCFlag")
	private String TCFlag;
    
    @JsonProperty("AlmInternalKey")
	private String AlmInternalKey;
    
    @JsonProperty("UserAddress")
	private UserAddress UserAddress;
	
	public String getAlmInternalKey() {
		return AlmInternalKey;
	}
	public void setAlmInternalKey(String almInternalKey) {
		AlmInternalKey = almInternalKey;
	}
	
	public UserAddress getUserAddress() {
		return UserAddress;
	}
	public void setUserAddress(UserAddress userAddress) {
		UserAddress = userAddress;
	}
	public String getLastName() {
		return LastName;
	}
	public void setLastName(String lastName) {
		LastName = lastName;
	}
	public String getFirstName() {
		return FirstName;
	}
	public void setFirstName(String firstName) {
		FirstName = firstName;
	}
	public String getEmail() {
		return Email;
	}
	public void setEmail(String email) {
		Email = email;
	}
	public String getECID() {
		return ECID;
	}
	public void setECID(String eCID) {
		ECID = eCID;
	}
	public String getUserStatus() {
		return UserStatus;
	}
	public void setUserStatus(String userStatus) {
		UserStatus = userStatus;
	}
	public String getSourceSystem() {
		return SourceSystem;
	}
	public void setSourceSystem(String sourceSystem) {
		SourceSystem = sourceSystem;
	}
	public String getCustomerType() {
		return CustomerType;
	}
	public void setCustomerType(String customerType) {
		CustomerType = customerType;
	}
	public String getTCFlag() {
		return TCFlag;
	}
	public void setTCFlag(String tCFlag) {
		TCFlag = tCFlag;
	}
	

}
