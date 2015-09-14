/*******************************************************************************
 * Copyright (c) 2015 John Wiley & Sons, Inc. All rights reserved.
 *
 * All material contained herein is proprietary to John Wiley & Sons 
 * and its third party suppliers, if any. The methods, techniques and 
 * technical concepts contained herein are considered trade secrets 
 * and confidential and may be protected by intellectual property laws.  
 * Reproduction or distribution of this material, in whole or in part, 
 * is strictly forbidden except by express prior written permission 
 * of John Wiley & Sons.
 *******************************************************************************/
package com.wiley.gr.ace.authorservices.web.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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
 * @author virtusa version 1.0
 */
@RestController
@RequestMapping("/v1/templates")
public class TemplateManagementController {

    /** logger configured. */
    private static final Logger LOGGER = LoggerFactory
            .getLogger(TemplateManagementController.class);

    /** The template management service. */
    @Autowired(required = true)
    private TemplateManagementService templateManagementService;

    /** value from props file configured. */
    @Value("${TemplateManagementController.noTemplateTags.code}")
    private int noTemplateTagsErrorCode;

    /** value from props file configured. */
    @Value("${TemplateManagementController.noTemplateTags.message}")
    private String noTemplateTagsErrorMessage;

    /** value from props file configured. */
    @Value("${TemplateManagementController.getTemplateTags.code}")
    private int getTemplateTagsErrorCode;

    /** value from props file configured. */
    @Value("${TemplateManagementController.getTemplateTags.message}")
    private String getTemplateTagsErrorMessage;

    /** value from props file configured. */
    @Value("${TemplateManagementController.noTemplate.code}")
    private int noTemplateErrorCode;

    /** value from props file configured. */
    @Value("${TemplateManagementController.noTemplate.message}")
    private String noTemplateErrorMessage;

    /** value from props file configured. */
    @Value("${TemplateManagementController.getTemplate.code}")
    private int getTemplateErrorCode;

    /** value from props file configured. */
    @Value("${TemplateManagementController.getTemplate.message}")
    private String getTemplateErrorMessage;

    /** value from props file configured. */
    @Value("${TemplateManagementController.noCreateTemplate.code}")
    private int noCreateTemplateErrorCode;

    /** value from props file configured. */
    @Value("${TemplateManagementController.noCreateTemplate.message}")
    private String noCreateTemplateErrorMessage;

    /** value from props file configured. */
    @Value("${TemplateManagementController.createTemplate.code}")
    private int createTemplateErrorCode;

    /** value from props file configured. */
    @Value("${TemplateManagementController.createTemplate.message}")
    private String createTemplateErrorMessage;

    /** value from props file configured. */
    @Value("${TemplateManagementController.noUpdateTemplate.code}")
    private int noUpdateTemplateErrorCode;

    /** value from props file configured. */
    @Value("${TemplateManagementController.noUpdateTemplate.message}")
    private String noUpdateTemplateErrorMessage;

    /** value from props file configured. */
    @Value("${TemplateManagementController.updateTemplate.code}")
    private int updateTemplateErrorCode;

    /** value from props file configured. */
    @Value("${TemplateManagementController.updateTemplate.message}")
    private String updateTemplateErrorMessage;

    /** value from props file configured. */
    @Value("${TemplateManagementController.noDeleteTemplate.code}")
    private int noDeleteTemplateErrorCode;

    /** value from props file configured. */
    @Value("${TemplateManagementController.noDeleteTemplate.message}")
    private String noDeleteTemplateErrorMessage;

    /** value from props file configured. */
    @Value("${TemplateManagementController.deleteTemplate.code}")
    private int deleteTemplateErrorCode;

    /** value from props file configured. */
    @Value("${TemplateManagementController.deleteTemplate.message}")
    private String deleteTemplateErrorMessage;

    /** value from props file configured. */
    @Value("${TemplateManagementController.noSearchTemplate.code}")
    private int noSearchTemplateErrorCode;

    /** value from props file configured. */
    @Value("${TemplateManagementController.noSearchTemplate.message}")
    private String noSearchTemplateErrorMessage;

    /** value from props file configured. */
    @Value("${TemplateManagementController.searchTemplate.code}")
    private int searchTemplateErrorCode;

    /** value from props file configured. */
    @Value("${TemplateManagementController.searchTemplate.message}")
    private String searchTemplateErrorMessage;

    /** value from props file configured. */
    @Value("${TemplateManagementController.noRenderTemplate.code}")
    private int noRenderTemplateErrorCode;

    /** value from props file configured. */
    @Value("${TemplateManagementController.noRenderTemplate.message}")
    private String noRenderTemplateErrorMessage;

    /** value from props file configured. */
    @Value("${TemplateManagementController.renderTemplate.code}")
    private int renderTemplateErrorCode;

    /** value from props file configured. */
    @Value("${TemplateManagementController.renderTemplate.message}")
    private String renderTemplateErrorMessage;

    /**
     * Gets the template tags.
     *
     * @param applicationId
     *            the application id
     * @return the template tags
     */
    @RequestMapping(value = "/{applicationId}/tags", method = RequestMethod.GET)
    public final Service getTemplateTags(
            @PathVariable final String applicationId) {
        LOGGER.info("inside getTemplateTags method of TemplateManagmentController");
        Service service = new Service();
        Tags tags = null;
        try {
            tags = templateManagementService.getTemplateTags(applicationId);
            if (!StringUtils.isEmpty(tags)) {
                service.setStatus("SUCCESS");
                service.setPayload(tags);
            } else {
                ErrorPOJO error = new ErrorPOJO();
                error.setCode(noTemplateTagsErrorCode);
                error.setMessage(noTemplateTagsErrorMessage);
                service.setStatus("FAILURE");
                service.setError(error);
            }
        } catch (Exception e) {
            LOGGER.error("Print Stack Trace- ", e);
            ErrorPOJO error = new ErrorPOJO();
            error.setCode(getTemplateTagsErrorCode);
            error.setMessage(getTemplateTagsErrorMessage);
            service.setStatus("ERROR");
            service.setError(error);
        }
        return service;
    }

    /**
     * Gets the template.
     *
     * @param templateId
     *            the template id
     * @param applicationId
     *            the application id
     * @return the template
     */
    @RequestMapping(value = "/{applicationId}/{templateId}", method = RequestMethod.GET)
    public final Service getTemplate(
            @PathVariable("templateId") final String templateId,
            @PathVariable("applicationId") final String applicationId) {
        LOGGER.info("inside getTemplate method of TemplateManagmentController");
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
                error.setCode(noTemplateErrorCode);
                error.setMessage(noTemplateErrorMessage);
                service.setStatus("FAILURE");
                service.setError(error);
            }
        } catch (Exception e) {
            LOGGER.error("Print Stack Trace- ", e);
            ErrorPOJO error = new ErrorPOJO();
            error.setCode(getTemplateErrorCode);
            error.setMessage(getTemplateErrorMessage);
            service.setStatus("ERROR");
            service.setError(error);
        }

        return service;
    }

    /**
     * Creates the template.
     *
     * @param template
     *            the template
     * @return the service
     */
    @RequestMapping(method = RequestMethod.POST)
    public final Service createTemplate(@RequestBody final TemplateObj template) {
        LOGGER.info("inside createTemplate method of TemplateManagmentController");
        Service service = new Service();
        boolean isCreated = false;
        try {
            isCreated = templateManagementService.insertTemplate(template);
        } catch (Exception e) {
            LOGGER.error("Print Stack Trace- ", e);
            ErrorPOJO error = new ErrorPOJO();
            error.setCode(createTemplateErrorCode);
            error.setMessage(createTemplateErrorMessage);
            service.setStatus("ERROR");
            service.setError(error);
        }
        if (isCreated) {
            service.setStatus("SUCCESS");
            service.setPayload(isCreated);
        } else {
            ErrorPOJO error = new ErrorPOJO();
            error.setCode(noCreateTemplateErrorCode);
            error.setMessage(noCreateTemplateErrorMessage);
            service.setStatus("FAILURE");
            service.setError(error);

        }
        return service;
    }

    /**
     * Update template.
     *
     * @param templateId
     *            the template id
     * @param applicationId
     *            the application id
     * @param templateObj
     *            the template obj
     * @return the service
     */
    @RequestMapping(value = "/{applicationId}/{templateId}", method = RequestMethod.PUT)
    public final Service updateTemplate(
            @PathVariable("templateId") final String templateId,
            @PathVariable("applicationId") final String applicationId,
            @RequestBody final TemplateObj templateObj) {
        LOGGER.info("inside updateTemplate method of TemplateManagmentController");
        Service service = new Service();
        boolean isUpdated = false;
        try {
            isUpdated = templateManagementService.updateTemplate(templateId,
                    applicationId, templateObj);
        } catch (Exception e) {
            LOGGER.error("Print Stack Trace- ", e);
            ErrorPOJO error = new ErrorPOJO();
            error.setCode(updateTemplateErrorCode);
            error.setMessage(updateTemplateErrorMessage);
            service.setStatus("ERROR");
            service.setError(error);
        }
        if (isUpdated) {
            service.setStatus("SUCCESS");
        } else {
            ErrorPOJO error = new ErrorPOJO();
            error.setCode(noUpdateTemplateErrorCode);
            error.setMessage(noUpdateTemplateErrorMessage);
            service.setStatus("FAILURE");
            service.setError(error);
        }
        return service;
    }

    /**
     * Delete template.
     *
     * @param applicationId
     *            the application id
     * @param templateId
     *            the template id
     * @return the service
     */
    @RequestMapping(value = "/{applicationId}/{templateId}", method = RequestMethod.DELETE)
    public final Service deleteTemplate(
            @PathVariable("templateId") final String applicationId,
            @PathVariable("templateId") final String templateId) {
        LOGGER.info("inside deleteTemplate method of TemplateManagmentController");
        Service service = new Service();
        boolean isDeleted = false;
        try {
            isDeleted = templateManagementService.deleteTemplate(applicationId,
                    templateId);
        } catch (Exception e) {
            LOGGER.error("Print Stack Trace- ", e);
            ErrorPOJO error = new ErrorPOJO();
            error.setCode(deleteTemplateErrorCode);
            error.setMessage(deleteTemplateErrorMessage);
            service.setStatus("ERROR");
            service.setError(error);
        }
        if (isDeleted) {
            service.setStatus("SUCCESS");
            service.setPayload(isDeleted);
        } else {
            ErrorPOJO error = new ErrorPOJO();
            error.setCode(noDeleteTemplateErrorCode);
            error.setMessage(noDeleteTemplateErrorMessage);
            service.setStatus("FAILURE");
            service.setError(error);
        }
        return service;
    }

    /**
     * Search template.
     *
     * @param applicationId
     *            the application id
     * @param tagL1
     *            the tag l1
     * @param tagL2
     *            the tag l2
     * @return the service
     */
    @RequestMapping(value = "/{applicationId}", method = RequestMethod.GET)
    public final Service searchTemplate(
            @PathVariable("applicationId") final String applicationId,
            @RequestParam(value = "tagL1") final String tagL1,
            @RequestParam(value = "tagL2") final String tagL2) {
        LOGGER.info("inside searchTemplate method of TemplateManagmentController");
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
                error.setCode(noSearchTemplateErrorCode);
                error.setMessage(noSearchTemplateErrorMessage);
                service.setStatus("FAILURE");
                service.setError(error);
            }
        } catch (Exception e) {
            LOGGER.error("Print Stack Trace- ", e);
            ErrorPOJO error = new ErrorPOJO();
            error.setCode(searchTemplateErrorCode);
            error.setMessage(searchTemplateErrorMessage);
            service.setStatus("ERROR");
            service.setError(error);
        }
        return service;
    }

    /**
     * Render template.
     *
     * @param templateId
     *            the template id
     * @param applicationId
     *            the application id
     * @param templateDetails
     *            the template details
     * @return the service
     */
    @RequestMapping(value = "/{applicationId}/{templateId}/render", method = RequestMethod.POST)
    public final Service renderTemplate(
            @PathVariable("templateId") final String templateId,
            @PathVariable("applicationId") final String applicationId,
            @RequestBody final TemplateDetails templateDetails) {
        LOGGER.info("inside renderTemplate method of TemplateManagmentController");
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
                error.setCode(noRenderTemplateErrorCode);
                error.setMessage(noRenderTemplateErrorMessage);
                service.setStatus("FAILURE");
                service.setError(error);
            }
        } catch (Exception e) {
            LOGGER.error("Print Stack Trace- ", e);
            ErrorPOJO error = new ErrorPOJO();
            error.setCode(renderTemplateErrorCode);
            error.setMessage(renderTemplateErrorMessage);
            service.setStatus("ERROR");
            service.setError(error);
        }
        return service;
    }

    /**
     * Gets the all template variables.
     *
     * @param templateId
     *            the template id
     * @param applicationId
     *            the application id
     * @return the all template variables
     */
    @RequestMapping(value = "/{applicationId}/{templateId}/variables", method = RequestMethod.POST)
    public final Service getAllTemplateVariables(
            @PathVariable("templateId") final String templateId,
            @PathVariable("applicationId") final String applicationId) {
        Service service = new Service();
        try {
            service.setPayload(templateManagementService.getVelocityVars(
                    applicationId, templateId));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return service;

    }
}
