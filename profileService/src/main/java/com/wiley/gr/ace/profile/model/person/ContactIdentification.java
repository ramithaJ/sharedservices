package com.wiley.gr.ace.profile.model.person;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ContactIdentification {

	
	@JsonProperty("sourceContactXREF")
	public SourceContactXREF getSourceContactXREF() {
		
		if(sourceContactXREF ==null){
			sourceContactXREF = new SourceContactXREF();
		}
		return sourceContactXREF;
	}

	public void setSourceContactXREF(SourceContactXREF sourceContactXREF) {
		this.sourceContactXREF = sourceContactXREF;
	}

	private SourceContactXREF sourceContactXREF;

}
