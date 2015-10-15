package com.wiley.gr.ace.profile.service.impl;

import org.springframework.beans.factory.annotation.Value;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.wiley.gr.ace.profile.common.CommonConstants;
import com.wiley.gr.ace.profile.exception.SharedServiceException;
import com.wiley.gr.ace.profile.model.person.CreateContactRequestEBM;
import com.wiley.gr.ace.profile.model.person.Person;
import com.wiley.gr.ace.profile.service.PersonService;

public class PersonServiceImpl implements PersonService{

	
	  /** the industrieskey. */
    @Value("${personDomain.value}")
    private String personDomain;

	
	@Override
	public void updatePerson(Person person) throws SharedServiceException {
		
		String url = personDomain;

		ServiceUtil.postData(url, person, CreateContactRequestEBM.class);
	
		
	}


	
}
