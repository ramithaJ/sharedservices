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

	/** logger configured. */
	private static final Logger LOGGER = LoggerFactory
			.getLogger(CrossRefController.class);

	/** The get dh id code. */
	@Value("${CrossRefController.getNoDhId.code}")
	private String getNoDhIdErrorCode;

	/** The get dh id message. */
	@Value("${CrossRefController.getNoDhId.message}")
	private String getNoDhIdErrorMessage;

	/** The get dh id error code. */
	@Value("${CrossRefController.getDhIdError.code}")
	private String getDhIdErrorCode;

	/** The get dh id error message. */
	@Value("${CrossRefController.getDhIdError.message}")
	private String getDhIdErrorMessage;

	/** The get no value error code. */
	@Value("${CrossRefController.getNoValue.code}")
	private String getNoValueErrorCode;

	/** The get no value error message. */
	@Value("${CrossRefController.getNoValue.message}")
	private String getNoValueErrorMessage;

	/** The get value error code. */
	@Value("${CrossRefController.getValue.code}")
	private String getValueErrorCode;

	/** The get value error message. */
	@Value("${CrossRefController.getValue.message}")
	private String getValueErrorMessage;

	/** The create no value error code. */
	@Value("${CrossRefController.createNoValue.code}")
	private String createNoValueErrorCode;

	/** The create no value error message. */
	@Value("${CrossRefController.createNoValue.message}")
	private String createNoValueErrorMessage;

	/** The create value error code. */
	@Value("${CrossRefController.createValue.code}")
	private String createValueErrorCode;

	/** The create value error message. */
	@Value("${CrossRefController.createValue.message}")
	private String createValueErrorMessage;

	/** The update no value error code. */
	@Value("${CrossRefController.updateNoValue.code}")
	private String updateNoValueErrorCode;

	/** The update no value error message. */
	@Value("${CrossRefController.updateNoValue.message}")
	private String updateNoValueErrorMessage;

	/** The update value error code. */
	@Value("${CrossRefController.updateValue.code}")
	private String updateValueErrorCode;

	/** The update value error message. */
	@Value("${CrossRefController.updateValue.message}")
	private String updateValueErrorMessage;

	/** The delete no value error code. */
	@Value("${CrossRefController.deleteNoValue.code}")
	private String deleteNoValueErrorCode;

	/** The delete no value error message. */
	@Value("${CrossRefController.deleteNoValue.message}")
	private String deleteNoValueErrorMessage;

	/** The delete value error code. */
	@Value("${CrossRefController.deleteValue.code}")
	private String deleteValueErrorCode;

	/** The delete value error message. */
	@Value("${CrossRefController.deleteValue.message}")
	private String deleteValueErrorMessage;

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
	@RequestMapping(value = "/authors/{dhId}", method = RequestMethod.GET)
	public final Service getCrossRef(@PathVariable("dhId") final Integer dhId) {
		LOGGER.info("inside getCrossRef method of CrossRefController");
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
				error.setCode(Integer.parseInt(getNoDhIdErrorCode));
				error.setMessage(getNoDhIdErrorMessage);
				service.setStatus("FAILURE");
				service.setError(error);

			}
		} catch (final Exception e) {
			LOGGER.error("Print Stack Trace- ", e);
			final ErrorPOJO error = new ErrorPOJO();
			error.setCode(Integer.parseInt(getDhIdErrorCode));
			error.setMessage(getDhIdErrorMessage);
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
	@RequestMapping(value = "/articles", method = RequestMethod.GET)
	public final Service getCrossRefUserId(
			@RequestParam(value = "userId", required = false) final String userId,
			@RequestParam(value = "emailAddr", required = false) final String emailAddr) {
		LOGGER.info("inside getCrossRefUserId method of CrossRefController");
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
				error.setCode(Integer.parseInt(getNoValueErrorCode));
				error.setMessage(getNoValueErrorMessage);
				service.setStatus("ERROR");
				service.setError(error);
			}
		} catch (final Exception e) {
	
			LOGGER.error("Print Stack Trace- ", e);
			final ErrorPOJO error = new ErrorPOJO();
			error.setCode(Integer.parseInt(getValueErrorCode));
			error.setMessage(getValueErrorMessage);
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
	@RequestMapping(value = "/article/assign", method = RequestMethod.POST)
	public final Service createCrossRef(
			@RequestBody final ProductPersonRelationRequest productPersonRelation) {
		LOGGER.info("inside createCrossRef method of CrossRefController");
		Service service = new Service();
		boolean isCreated = false;
		try {
			System.err.println("-----------------------------");
			isCreated = crossRefService.createCrossRef(productPersonRelation);
		} catch (final Exception e) {
			LOGGER.error("Print Stack Trace- ", e);
			ErrorPOJO error = new ErrorPOJO();
			error.setCode(Integer.parseInt(createNoValueErrorCode));
			error.setMessage(createNoValueErrorMessage);
			service.setStatus("ERROR");
			service.setError(error);
		}
		if (isCreated) {
			service.setStatus("SUCCESS");
		} else {
			final ErrorPOJO error = new ErrorPOJO();
			error.setCode(Integer.parseInt(createValueErrorCode));
			error.setMessage(createValueErrorMessage);
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
	@RequestMapping(value = "/article/assign", method = RequestMethod.PUT)
	public final Service updateCrossRef(
			@RequestBody final ProductPersonRelationRequest productPersonRelation) {
		LOGGER.info("inside updateCrossRef method of CrossRefController");
		Service service = new Service();
		boolean isUpdated = false;
		try {
			isUpdated = crossRefService.updateCrossRef(productPersonRelation);
		} catch (final Exception e) {
			LOGGER.error("Print Stack Trace- ", e);
			ErrorPOJO error = new ErrorPOJO();
			error.setCode(Integer.parseInt(updateNoValueErrorCode));
			error.setMessage(updateNoValueErrorMessage);
			service.setStatus("ERROR");
			service.setError(error);
		}
		if (isUpdated) {
			service.setStatus("SUCCESS");
		} else {
			final ErrorPOJO error = new ErrorPOJO();
			error.setCode(Integer.parseInt(updateValueErrorCode));
			error.setMessage(updateValueErrorMessage);
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
	@RequestMapping(value = "/article/assign", method = RequestMethod.DELETE)
	public final Service deleteCrossRef(
			@RequestBody final ProductPersonRelationRequest productPersonRelation) {
		LOGGER.info("inside deleteCrossRef method of CrossRefController");
		Service service = new Service();
		boolean isDeleted = false;
		try {
			isDeleted = crossRefService.deleteCrossRef(productPersonRelation);
		} catch (final Exception e) {
			LOGGER.error("Print Stack Trace- ", e);
			ErrorPOJO error = new ErrorPOJO();
			error.setCode(Integer.parseInt(deleteNoValueErrorCode));
			error.setMessage(deleteNoValueErrorMessage);
			service.setStatus("ERROR");
			service.setError(error);
		}
		if (isDeleted) {
			service.setStatus("SUCCESS");
		} else {
			final ErrorPOJO error = new ErrorPOJO();
			error.setCode(Integer.parseInt(deleteValueErrorCode));
			error.setMessage(deleteValueErrorMessage);
			service.setStatus("FAILURE");
			service.setError(error);
		}
		return service;

	}
}
