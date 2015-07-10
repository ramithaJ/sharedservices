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

import com.wiley.gr.ace.authorservices.model.AuthorDetails;
import com.wiley.gr.ace.authorservices.model.ErrorPOJO;
import com.wiley.gr.ace.authorservices.model.ProductPersonRelationObj;
import com.wiley.gr.ace.authorservices.model.ProductPersonRelationRequest;
import com.wiley.gr.ace.authorservices.model.Service;
import com.wiley.gr.ace.authorservices.services.service.CrossRefService;

/**
 * The Class CrossRefController.
 * 
 * @author virtusa version 1.0
 */
@RestController
@RequestMapping("/v1")
public class CrossRefController {

    /** The cross ref service. */
    @Autowired(required = true)
    private CrossRefService crossRefService;

    /**
     * Gets the cross ref.
     *
     * @param dhId
     *            the dh id
     * @return the cross ref
     */
    @RequestMapping(value = "/authors/{dhId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody final Service getCrossRef(
            @PathVariable("dhId") final Integer dhId) {
        Service service = new Service();
        ProductPersonRelationObj productPersonRelationObj = null;
        try {

            productPersonRelationObj = crossRefService
                    .getProductPersonRelationObj(dhId);
            if (!StringUtils.isEmpty(productPersonRelationObj)) {
                service.setStatus("SUCCESS");
                service.setPayload(productPersonRelationObj);
            } else {
                final ErrorPOJO error = new ErrorPOJO();
                error.setCode(309);
                error.setMessage("No records found for the required criteria");
                service.setStatus("FAILURE");
                service.setError(error);

            }
        } catch (final Exception e) {
            final ErrorPOJO error = new ErrorPOJO();
            error.setCode(316);
            error.setMessage("Error Fetching Cross ref");
            service.setStatus("ERROR");
            service.setError(error);
        }
        return service;
    }

    /**
     * Gets the cross ref user id.
     *
     * @param userId
     *            the user id
     * @param emailAddr
     *            the email addr
     * @return the cross ref user id
     */
    @RequestMapping(value = "/articles", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody final Service getCrossRefUserId(
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
            } else {
                final ErrorPOJO error = new ErrorPOJO();
                error.setCode(315);
                error.setMessage("No criteria");
                service.setStatus("ERROR");
                service.setError(error);
            }
        } catch (final Exception e) {
            final ErrorPOJO error = new ErrorPOJO();
            error.setCode(316);
            error.setMessage("Error Fetching Value");
            service.setStatus("ERROR");
            service.setError(error);
        }
        return service;
    }

    /**
     * Creates the cross ref.
     *
     * @param productPersonRelation
     *            the product person relation
     * @return the service
     */
    @RequestMapping(value = "/article/assign", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody final Service createCrossRef(
            @RequestBody final ProductPersonRelationRequest productPersonRelation) {
        Service service = new Service();
        boolean isCreated = false;
        try {
            isCreated = crossRefService.createCrossRef(productPersonRelation);
        } catch (final Exception e) {
            ErrorPOJO error = new ErrorPOJO();
            error.setCode(400);
            error.setMessage("Error inserting Cross ref");
            service.setStatus("ERROR");
            service.setError(error);
        }
        if (isCreated) {
            service.setStatus("SUCCESS");
        } else {
            final ErrorPOJO error = new ErrorPOJO();
            error.setCode(401);
            error.setMessage("Enter valid inputs");
            service.setStatus("FAILURE");
            service.setError(error);
        }
        return service;
    }

    /**
     * Update cross ref.
     *
     * @param productPersonRelation
     *            the product person relation
     * @return the service
     */
    @RequestMapping(value = "/article/assign", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody final Service updateCrossRef(
            @RequestBody final ProductPersonRelationRequest productPersonRelation) {
        Service service = new Service();
        boolean isUpdated = false;
        try {
            isUpdated = crossRefService.updateCrossRef(productPersonRelation);
        } catch (final Exception e) {
            ErrorPOJO error = new ErrorPOJO();
            error.setCode(402);
            error.setMessage("Error getting Cross ref");
            service.setStatus("ERROR");
            service.setError(error);
        }
        if (isUpdated) {
            service.setStatus("SUCCESS");
        } else {
            final ErrorPOJO error = new ErrorPOJO();
            error.setCode(403);
            error.setMessage("Enter valid inputs");
            service.setStatus("FAILURE");
            service.setError(error);
        }
        return service;

    }

    /**
     * Delete cross ref.
     *
     * @param productPersonRelation
     *            the product person relation
     * @return the service
     */
    @RequestMapping(value = "/article/assign", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody final Service deleteCrossRef(
            @RequestBody final ProductPersonRelationRequest productPersonRelation) {
        Service service = new Service();
        boolean isDeleted = false;
        try {
            isDeleted = crossRefService.deleteCrossRef(productPersonRelation);
        } catch (final Exception e) {
            ErrorPOJO error = new ErrorPOJO();
            error.setCode(404);
            error.setMessage("Error getting Cross ref");
            service.setStatus("ERROR");
            service.setError(error);
        }
        if (isDeleted) {
            service.setStatus("SUCCESS");
        } else {
            final ErrorPOJO error = new ErrorPOJO();
            error.setCode(405);
            error.setMessage("Enter valid inputs");
            service.setStatus("FAILURE");
            service.setError(error);
        }
        return service;

    }
}
