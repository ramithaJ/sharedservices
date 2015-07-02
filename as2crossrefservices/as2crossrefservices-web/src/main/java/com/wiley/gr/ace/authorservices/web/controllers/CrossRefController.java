package com.wiley.gr.ace.authorservices.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.wiley.gr.ace.authorservices.model.AuthorDetails;
import com.wiley.gr.ace.authorservices.model.ErrorPOJO;
import com.wiley.gr.ace.authorservices.model.ProductPersonRelationObj;
import com.wiley.gr.ace.authorservices.model.Service;
import com.wiley.gr.ace.authorservices.services.service.CrossRefService;

@RestController
@RequestMapping("/v1")
public class CrossRefController {
	@Autowired(required = true)
	CrossRefService crossRefService;

	@RequestMapping(value = "/authors/{dhId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Service getCrossRef(
			@PathVariable("dhId") final Integer dhId) {
		Service service = new Service();

		System.err.println("Controller");
		ProductPersonRelationObj productPersonRelationObj = null;
		try {

			productPersonRelationObj = crossRefService
					.getProductPersonRelationObj(dhId);
			if (!StringUtils.isEmpty(productPersonRelationObj)) {
				service.setStatus("SUCCESS");
				service.setPayload(productPersonRelationObj);
			}

			else {
				final ErrorPOJO error = new ErrorPOJO();
				error.setCode(309);
				error.setMessage("No records found for the required criteria");
				service.setStatus("FAILURE");
				service.setError(error);

			}
		} catch (final Exception e) {
			e.printStackTrace();
			final ErrorPOJO error = new ErrorPOJO();
			error.setCode(316);
			error.setMessage("Error Fetching Cross ref");
			service.setStatus("ERROR");
			service.setError(error);
		}
		return service;
	}

	@RequestMapping(value = "/articles", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Service getCrossRefUserId(
			@RequestParam(value = "userId") final String userId,
			@RequestParam(value = "emailAddr") final String emailAddr) {
		Service service = new Service();
		AuthorDetails authorDetails = null;
		try {

			if (!StringUtils.isEmpty(userId)) {
				authorDetails = crossRefService.getAuthorDetailsById(userId);
			} else if (!StringUtils.isEmpty(emailAddr)) {
				authorDetails = crossRefService
						.getAuthorDetailsByEmail(emailAddr);
			}
				if (!StringUtils.isEmpty(authorDetails)) {
					service.setStatus("SUCCESS");

					service.setPayload(authorDetails);
				}
			

			else {
				final ErrorPOJO error = new ErrorPOJO();
				error.setCode(315);
				error.setMessage("No criteria");
				service.setStatus("ERROR");
				service.setError(error);
			}
		} catch (final Exception e) {
			e.printStackTrace();
			final ErrorPOJO error = new ErrorPOJO();
			error.setCode(316);
			error.setMessage("Error Fetching Value");
			service.setStatus("ERROR");
			service.setError(error);
		}
		return service;
	}

}
