package com.wiley.gr.ace.authorservices.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.wiley.gr.ace.authorservices.model.ErrorPOJO;
import com.wiley.gr.ace.authorservices.model.ProductPersonRelationRequest;
import com.wiley.gr.ace.authorservices.model.Service;
import com.wiley.gr.ace.authorservices.services.service.CrossRefService1;

@RestController
@RequestMapping("/v1")
public class CrossRefController1 {
	
	@Autowired(required = true)
	CrossRefService1 crossRefService;
	
	
	@RequestMapping(value = "/article/assign", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Service createCrossRef(@RequestBody ProductPersonRelationRequest productPersonRelation) {
		Service service = new Service();
		boolean isCreated = false;
		try {
			isCreated = crossRefService.createCrossRef(productPersonRelation);
			}catch (final Exception e) {
	        ErrorPOJO error = new ErrorPOJO();
			error.setCode(400);
			error.setMessage("Error inserting Cross ref");
			service.setStatus("ERROR");
			service.setError(error);
		}
		if (isCreated) {
			service.setStatus("SUCCESS");
		}
		else {
			final ErrorPOJO error = new ErrorPOJO();
			error.setCode(401);
			error.setMessage("Enter valid inputs");
			service.setStatus("FAILURE");
			service.setError(error);
		}
		return service;
	}
	@RequestMapping(value = "/article/assign", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Service updateCrossRef(@RequestBody ProductPersonRelationRequest productPersonRelation) {
		return new Service();

	}
	@RequestMapping(value = "/article/assign", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Service deleteCrossRef(@RequestBody ProductPersonRelationRequest productPersonRelation) {
		Service service = new Service();
		boolean isDeleted = false;
		try {
			isDeleted = crossRefService.deleteCrossRef(productPersonRelation);
			}catch (final Exception e) {
	        ErrorPOJO error = new ErrorPOJO();
			error.setCode(404);
			error.setMessage("Error getting Cross ref");
			service.setStatus("ERROR");
			service.setError(error);
		}
		if (isDeleted) {
			service.setStatus("SUCCESS");
		}
		else {
			final ErrorPOJO error = new ErrorPOJO();
			error.setCode(405);
			error.setMessage("Enter valid inputs");
			service.setStatus("FAILURE");
			service.setError(error);
		}
		return service;

	}
}
