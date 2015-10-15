package com.wiley.gr.ace.profile.model.person;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ContactEBM {

	
	private ContactProfile ContactProfile;
	private List<Address> Address;
	private CustomerDetails CustomerDetails;

	@JsonProperty("ContactProfile")
	public ContactProfile getContactProfile() {
		
		if(ContactProfile == null){
			ContactProfile = new ContactProfile();
		}
		return ContactProfile;
	}
	
	@JsonProperty("Address")
	public List<Address> getAddress() {
		return Address;
	}
	public void setAddress(List<Address> address) {
		Address = address;
	}
	public void setContactProfile(ContactProfile contactProfile) {
		this.ContactProfile = contactProfile;
	}
	
	@JsonProperty("CustomerDetails")
	public CustomerDetails getCustomerDetails() {
		
		if(CustomerDetails ==null){
			CustomerDetails = new CustomerDetails();
		}
		return CustomerDetails;
	}
	public void setCustomerDetails(CustomerDetails customerDetails) {
		this.CustomerDetails = customerDetails;
	}
	
	
}
