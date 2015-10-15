package com.wiley.gr.ace.profile.service;

import com.wiley.gr.ace.profile.exception.SharedServiceException;
import com.wiley.gr.ace.profile.model.person.CreateContactRequestEBM;
import com.wiley.gr.ace.profile.model.person.Person;

public interface PersonService {
	
		
	public void updatePerson(Person person) throws SharedServiceException;

}
