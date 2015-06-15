/**
 * 
 */
package com.wiley.gr.ace.authorservices.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.wiley.gr.ace.authorservices.model.ErrorPOJO;
import com.wiley.gr.ace.authorservices.model.Service;
import com.wiley.gr.ace.authorservices.model.TemplateDetails;
import com.wiley.gr.ace.authorservices.model.TemplateVO;
import com.wiley.gr.ace.authorservices.services.service.TemplateManagementService;

/**
 * @author tsandeepr
 *
 */
@RestController
@RequestMapping("/v1/templates")
public class TemplateManagementController {

	@Autowired(required = true)
	TemplateManagementService templateManagementService;

	@RequestMapping(value = "/$applicationId/tags", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Service getTemplateTags() {
		Service service = new Service();
		return service;
	}

	@RequestMapping(value = "/{applicationId}/{templateId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Service getTemplate(
			@PathVariable("templateId") String templateId,
			@PathVariable("applicationId") String applicationId) {
		Service service = new Service();
		TemplateVO template = null;
		try {
			template = templateManagementService.getTemplate(templateId,
					applicationId);
			if (!StringUtils.isEmpty(template)) {
				service.setStatus("SUCCESS");
				service.setPayload(template);
			}
		} catch (Exception e) {
			ErrorPOJO error = new ErrorPOJO();
			error.setCode(205);
			error.setMessage("Error Fetching Template");
			service.setStatus("ERROR");
			service.setError(error);
		}
		return service;
	}

	@RequestMapping(value = "applicationId/$templateId", method = RequestMethod.PUT)
	public @ResponseBody Service insertTemplateId() {
		Service service = new Service();
		return service;
	}

	@RequestMapping(value = "/$applicationId/$templateId", method = RequestMethod.POST)
	public @ResponseBody Service updateTemplateId() {
		Service service = new Service();
		return service;
	}

	@RequestMapping(value = "/{applicationId}/{templateId}", method = RequestMethod.DELETE)
	public @ResponseBody Service deleteTemplateId(
			@PathVariable("templateId") String templateId,
			@PathVariable("applicationId") String applicationId) {
		Service service = new Service();
		boolean isDeleted = templateManagementService.deleteTemplate(
				templateId, applicationId);
		if (isDeleted) {
			service.setStatus("SUCCESS");
			service.setPayload(isDeleted);
		}

		else {
			service.setStatus("FAILURE");
			service.setPayload(isDeleted);

		}
		return service;
	}

	@RequestMapping(value = "{applicationId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Service searchTemplate(
			@PathVariable("applicationId") String applicationId,
			@RequestParam(value = "tagL1") String tagL1,
			@RequestParam(value = "tagL2") String tagL2) {
		Service service = new Service();
		TemplateVO template = null;
		try {
			template = templateManagementService.searchTemplate(applicationId,
					tagL1, tagL2);
			if (!StringUtils.isEmpty(template)) {
				service.setStatus("SUCCESS");
				service.setPayload(template);
			}
		} catch (Exception e) {
			ErrorPOJO error = new ErrorPOJO();
			error.setCode(205);
			error.setMessage("Error Fetching Template");
			service.setStatus("ERROR");
			service.setError(error);
		}
		return service;
	}

	@RequestMapping(value = "/{applicationId}/{templateId}/render", method = RequestMethod.POST)
	public @ResponseBody Service renderTemplate(
			@PathVariable("templateId") String templateId,
			@PathVariable("applicationId") String applicationId,
			@RequestBody TemplateDetails templateDetails) {
		TemplateVO template = null;
		Service service = new Service();
		try {
			template = templateManagementService.renderTemplate(applicationId,
					templateId, templateDetails);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (!StringUtils.isEmpty(template)) {
			service = new Service();
			service.setStatus("SUCCCESS");
			service.setPayload(template);
		}
		return service;

	}

}
