package com.wiley.gr.ace.profile.model.person;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Person {

	
	CreateContactRequestEBM CreateContactRequestEBM;

	@JsonProperty("CreateContactRequestEBM")
	public CreateContactRequestEBM getCreateContactRequestEBM() {
		return CreateContactRequestEBM;
	}

	public void setCreateContactRequestEBM(
			CreateContactRequestEBM createContactRequestEBM) {
		CreateContactRequestEBM = createContactRequestEBM;
	}

	
	
}
