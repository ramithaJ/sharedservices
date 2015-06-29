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
import com.wiley.gr.ace.authorservices.model.Tags;
import com.wiley.gr.ace.authorservices.model.TemplateDetails;
import com.wiley.gr.ace.authorservices.model.TemplateObj;
import com.wiley.gr.ace.authorservices.services.service.TemplateManagementService;


/**
 * The Class TemplateManagementController.
 *
 * @author tsandeepr
 */
@RestController
@RequestMapping("/v1/templates")
public class TemplateManagementController {

	/** The template management service. */
	@Autowired(required = true)
	TemplateManagementService templateManagementService;

	/**
	 * Gets the template tags.
	 *
	 * @param applicationId the application id
	 * @return the template tags
	 */
	@RequestMapping(value = "/{applicationId}/tags", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Service getTemplateTags(
			@PathVariable String applicationId) {
		Service service = new Service();
		Tags tags = null;
		try {
			tags = templateManagementService.getTemplateTags(applicationId);
			if (!StringUtils.isEmpty(tags)) {
				service.setStatus("SUCCESS");
				service.setPayload(tags);
			} else {
				ErrorPOJO error = new ErrorPOJO();
				error.setCode(201);
				error.setMessage("No tags found for the required criteria");
				service.setStatus("FAILURE");
				service.setError(error);
			}
		} catch (Exception e) {
			ErrorPOJO error = new ErrorPOJO();
			error.setCode(202);
			error.setMessage("Error Fetching Template");
			service.setStatus("ERROR");
			service.setError(error);
		}
		return service;
	}

	/**
	 * Gets the template.
	 *
	 * @param templateId the template id
	 * @param applicationId the application id
	 * @return the template
	 */
	@RequestMapping(value = "/{applicationId}/{templateId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Service getTemplate(
			@PathVariable("templateId") String templateId,
			@PathVariable("applicationId") String applicationId) {
		Service service = new Service();
		TemplateObj template = null;

		try {
			template = templateManagementService.getTemplate(templateId,
					applicationId);

			if (!StringUtils.isEmpty(template)) {
				service.setStatus("SUCCESS");
				service.setPayload(template);
			} else {
				ErrorPOJO error = new ErrorPOJO();
				error.setCode(203);
				error.setMessage("No tags found for the required criteria");
				service.setStatus("FAILURE");
				service.setError(error);
			}
		} catch (Exception e) {
			ErrorPOJO error = new ErrorPOJO();
			error.setCode(204);
			error.setMessage("Error Fetching Template");
			service.setStatus("ERROR");
			service.setError(error);
		}

		return service;
	}

	/**
	 * Creates the template.
	 *
	 * @param template the template
	 * @return the service
	 */
	@RequestMapping( method = RequestMethod.POST)
	public @ResponseBody Service createTemplate(
			@RequestBody TemplateObj template) {
		Service service = new Service();
		boolean isCreated = false;
		try {
			isCreated = templateManagementService.insertTemplate(template);
		} catch (Exception e) {
			ErrorPOJO error = new ErrorPOJO();
			error.setCode(205);
			error.setMessage("Error Fetching Template");
			service.setStatus("ERROR");
			service.setError(error);
		}
		if (isCreated) {
			service.setStatus("SUCCESS");
			service.setPayload(isCreated);
		} else {
			ErrorPOJO error = new ErrorPOJO();
			error.setCode(206);
			error.setMessage("No tags found for the required criteria");
			service.setStatus("FAILURE");
			service.setError(error);

		}
		return service;
	}

	/**
	 * Update template.
	 *
	 * @param templateId the template id
	 * @param applicationId the application id
	 * @param templateObj the template obj
	 * @return the service
	 */
	@RequestMapping(value = "/{applicationId}/{templateId}", method = RequestMethod.PUT)
	public @ResponseBody Service updateTemplate(
			@PathVariable("templateId") String templateId,
			@PathVariable("applicationId") String applicationId,
			@RequestBody TemplateObj templateObj) {
		Service service = new Service();
		boolean isUpdated = false;
		try {
			isUpdated = templateManagementService.updateTemplate(templateId,
					applicationId, templateObj);
		} catch (Exception e) {
			ErrorPOJO error = new ErrorPOJO();
			error.setCode(207);
			error.setMessage("Error Fetching Template");
			service.setStatus("ERROR");
			service.setError(error);
		}
		if (isUpdated) {
			service.setStatus("SUCCESS");
		}

		else {
			ErrorPOJO error = new ErrorPOJO();
			error.setCode(208);
			error.setMessage("No tags found for the required criteria");
			service.setStatus("FAILURE");
			service.setError(error);
		}
		return service;
	}

	/**
	 * Delete template.
	 *
	 * @param applicationId the application id
	 * @param templateId the template id
	 * @return the service
	 */
	@RequestMapping(value = "/{applicationId}/{templateId}", method = RequestMethod.DELETE)
	public @ResponseBody Service deleteTemplate(
			@PathVariable("templateId") String applicationId,
			@PathVariable("templateId") String templateId) {
		Service service = new Service();
		boolean isDeleted = false;
		try {
			isDeleted = templateManagementService.deleteTemplate(applicationId,
					templateId);
		} catch (Exception e) {
			ErrorPOJO error = new ErrorPOJO();
			error.setCode(209);
			error.setMessage("Error Fetching Template");
			service.setStatus("ERROR");
			service.setError(error);
		}
		if (isDeleted) {
			service.setStatus("SUCCESS");
			service.setPayload(isDeleted);
		}

		else {
			ErrorPOJO error = new ErrorPOJO();
			error.setCode(210);
			error.setMessage("No tags found for the required criteria");
			service.setStatus("FAILURE");
			service.setError(error);
		}
		return service;
	}

	/**
	 * Search template.
	 *
	 * @param applicationId the application id
	 * @param tagL1 the tag l1
	 * @param tagL2 the tag l2
	 * @return the service
	 */
	@RequestMapping(value = "/{applicationId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Service searchTemplate(
			@PathVariable("applicationId") String applicationId,
			@RequestParam(value = "tagL1") String tagL1,
			@RequestParam(value = "tagL2") String tagL2) {
		Service service = new Service();
		TemplateObj template = null;
		try {
			template = templateManagementService.searchTemplate(applicationId,
					tagL1, tagL2);
			if (!StringUtils.isEmpty(template)) {
				service.setStatus("SUCCESS");
				service.setPayload(template);
			} else {
				ErrorPOJO error = new ErrorPOJO();
				error.setCode(211);
				error.setMessage("No tags found for the required criteria");
				service.setStatus("FAILURE");
				service.setError(error);
			}
		} catch (Exception e) {
			ErrorPOJO error = new ErrorPOJO();
			error.setCode(212);
			error.setMessage("Error Fetching Template");
			service.setStatus("ERROR");
			service.setError(error);
		}
		return service;
	}

	/**
	 * Render template.
	 *
	 * @param templateId the template id
	 * @param applicationId the application id
	 * @param templateDetails the template details
	 * @return the service
	 */
	@RequestMapping(value = "/{applicationId}/{templateId}/render", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Service renderTemplate(
			@PathVariable("templateId") String templateId,
			@PathVariable("applicationId") String applicationId,
			@RequestBody TemplateDetails templateDetails) {
		TemplateObj template = null;
		Service service = new Service();
		try {

			template = templateManagementService.renderTemplate(applicationId,
					templateId, templateDetails);
			if (!StringUtils.isEmpty(template)) {
				service.setStatus("SUCCESS");
				service.setPayload(template);
			} else {
				ErrorPOJO error = new ErrorPOJO();
				error.setCode(213);
				error.setMessage("No tags found for the required criteria");
				service.setStatus("FAILURE");
				service.setError(error);
			}
		} catch (Exception e) {
			ErrorPOJO error = new ErrorPOJO();
			error.setCode(214);
			error.setMessage("Error Fetching Template");
			service.setStatus("ERROR");
			service.setError(error);
		}

		return service;

	}

}
