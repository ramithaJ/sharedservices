package com.wiley.gr.ace.profile.model.person;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CreateContactRequestEBM {

	
	private ContactEBM ContactEBM;
	
	@JsonProperty("ContactEBM")
	public ContactEBM getContactEBM() {
		
		if(ContactEBM== null){
			ContactEBM = new ContactEBM();
		}
		return ContactEBM;
	}


	public void setContactEBM(ContactEBM contactEBM) {
		this.ContactEBM = contactEBM;
	}
}
