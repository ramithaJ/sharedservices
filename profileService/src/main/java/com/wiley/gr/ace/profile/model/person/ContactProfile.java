package com.wiley.gr.ace.profile.model.person;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ContactProfile {

	
	
	private ContactIdentification ContactIdentification;

	@JsonProperty("ContactIdentification")
	public ContactIdentification getContactIdentification() {
		
		if(ContactIdentification == null){
			ContactIdentification = new ContactIdentification();
		}
		return ContactIdentification;
	}

	public void setContactIdentification(ContactIdentification contactIdentification) {
		this.ContactIdentification = contactIdentification;
	}
}
