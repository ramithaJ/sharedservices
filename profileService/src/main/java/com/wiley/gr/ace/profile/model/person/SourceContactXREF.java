package com.wiley.gr.ace.profile.model.person;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SourceContactXREF {
	
	
	private String SourceCustomerID;
	
	@JsonProperty("SourceCustomerID")
	public String getSourceCustomerID() {
		return SourceCustomerID;
	}
	public void setSourceCustomerID(String sourceCustomerID) {
		SourceCustomerID = sourceCustomerID;
	}
	
	@JsonProperty("SourceSystem")
	public String getSourceSystem() {
		return SourceSystem;
	}
	public void setSourceSystem(String sourceSystem) {
		SourceSystem = sourceSystem;
	}
	private String SourceSystem;

	
	
}
