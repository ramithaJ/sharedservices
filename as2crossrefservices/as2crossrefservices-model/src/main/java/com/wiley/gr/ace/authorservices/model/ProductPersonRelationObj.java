package com.wiley.gr.ace.authorservices.model;


public class ProductPersonRelationObj {
	
	private Integer dhId;
    private PersonDetails personDetails;
	public PersonDetails getPersonDetails() {
		return personDetails;
	}

	public void setPersonDetails(PersonDetails personDetails) {
		this.personDetails = personDetails;
	}

	public Integer getDhId() {
		return dhId;
	}

	public void setDhId(Integer dhId) {
		this.dhId = dhId;
	}
	
}
