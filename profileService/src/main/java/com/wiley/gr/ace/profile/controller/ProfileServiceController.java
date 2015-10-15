/**
 * ****************************************************************************
 * Copyright (c) 2015 John Wiley & Sons, Inc. All rights reserved.
 * <p>
 * All material contained herein is proprietary to John Wiley & Sons
 * and its third party suppliers, if any. The methods, techniques and
 * technical concepts contained herein are considered trade secrets
 * and confidential and may be protected by intellectual property laws.
 * Reproduction or distribution of this material, in whole or in part,
 * is strictly forbidden except by express prior written permission
 * of John Wiley & Sons.
 * *****************************************************************************
 */
package com.wiley.gr.ace.profile.controller;

import com.wiley.gr.ace.profile.common.CommonConstants;
import com.wiley.gr.ace.profile.common.CommonUtil;
import com.wiley.gr.ace.profile.exception.SharedServiceException;
import com.wiley.gr.ace.profile.model.Service;
import com.wiley.gr.ace.profile.model.updateProfileRequest;
import com.wiley.gr.ace.profile.model.updateProfileResponse;
import com.wiley.gr.ace.profile.service.OrchestrationService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * GetMaxDiscountRequest controller holds methods for getMaxDiscountRequest API
 *
 * @author virtusa version 1.0
 */
@RestController
@RequestMapping(CommonConstants.VERSION)
public class ProfileServiceController {

	
	/**
	 * Hold OrchestrationService instance. 
	 */
	@Autowired
	private OrchestrationService orchestrationService;

	/**
	 * Logger for ProfileServiceController class.
	 */
	private static final Logger LOGGER = LoggerFactory
			.getLogger(ProfileServiceController.class);

	/**
	 * Controller calls to handle all create entities. 
	 * @param updateProfileRequest to be set 
	 * @return  response service object.
	 */
	@RequestMapping(value = CommonConstants.PROFILE, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public final Service createProfile(
			@RequestBody updateProfileRequest updateProfileRequest) {
		Service service = new Service();

		updateProfileResponse updateProfileResponse = new updateProfileResponse();


		
		try {
			CommonUtil.validateEntity(updateProfileRequest);

			if (updateProfileRequest.getEntityType().equalsIgnoreCase(
					CommonConstants.ENTITY_TYPE_ADDRESS)) {

		      LOGGER.info("Recevied create request entity Type = "+ CommonConstants.ENTITY_TYPE_ADDRESS );
		
				orchestrationService.processAddress(updateProfileRequest
						.getEntityValue().getAddress(), RequestMethod.POST,
						updateProfileRequest.getEntityId(),updateProfileRequest.getSourceSystem());

				updateProfileResponse = new updateProfileResponse();
				service.setPayload(updateProfileResponse);
				service.setStatus(CommonConstants.SUCCESS);

			}
			
			
		} catch (SharedServiceException e) {

			LOGGER.error(e.getMessage());
			
			String errorCode = e.getErrorCode();
			String errorMessage = e.getMessage();

			service = CommonUtil.setServiceMessage(errorCode, errorMessage,
					CommonConstants.ERROR);

		}

		return service;

	}

	/**
	 * Controller method to handle all update entities
	 * @param updateProfileRequest to be set
	 * @return response service object.
	 */
	@RequestMapping(value = CommonConstants.PROFILE, method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public final Service updateProfile(
			@RequestBody updateProfileRequest updateProfileRequest) {
		
		
		Service service = new Service();
		updateProfileResponse updateProfileResponse = new updateProfileResponse();		
		service.setPayload(updateProfileResponse);


		try {
			CommonUtil.validateEntity(updateProfileRequest);

			if (updateProfileRequest.getEntityType().equalsIgnoreCase(
					CommonConstants.ENTITY_TYPE_PROFILE)) {

				  LOGGER.info("Recevied update request entity Type = "+ CommonConstants.ENTITY_TYPE_PROFILE);
					
				orchestrationService.processProfile(updateProfileRequest
						.getEntityValue().getProfile(), RequestMethod.PUT,
						updateProfileRequest.getEntityId(),updateProfileRequest.getSourceSystem());
				
				service.setStatus(CommonConstants.SUCCESS);


			}
			if (updateProfileRequest.getEntityType().equalsIgnoreCase(
					CommonConstants.ENTITY_TYPE_EMAIL)) {

				  LOGGER.info("Recevied update request entity Type = "+ CommonConstants.ENTITY_TYPE_EMAIL);
					
				orchestrationService.processEmail(updateProfileRequest.getEntityValue().getProfile(), 
						RequestMethod.PUT,updateProfileRequest.getEntityId(),updateProfileRequest.getSourceSystem());

				service.setStatus(CommonConstants.SUCCESS);

			}
			if (updateProfileRequest.getEntityType().equalsIgnoreCase(
					CommonConstants.ENTITY_TYPE_ADDRESS)) {

				  LOGGER.info("Recevied update request entity Type = "+ CommonConstants.ENTITY_TYPE_ADDRESS);
					
				orchestrationService.processAddress(updateProfileRequest
						.getEntityValue().getAddress(), RequestMethod.PUT,
						updateProfileRequest.getEntityId(),updateProfileRequest.getSourceSystem());
				
				service.setStatus(CommonConstants.SUCCESS);


			}
			if (updateProfileRequest.getEntityType().equalsIgnoreCase(
					CommonConstants.ENTITY_TYPE_FAVJOURNAL)) {
				
				 LOGGER.info("Recevied update request entity Type = "+ CommonConstants.ENTITY_TYPE_FAVJOURNAL);
					

				orchestrationService.processFAVJournals(updateProfileRequest
						.getEntityValue().getJournalList(),
						RequestMethod.PUT, updateProfileRequest.getEntityId());
				
				service.setStatus(CommonConstants.SUCCESS);

			}
				if (updateProfileRequest.getEntityType().equalsIgnoreCase(
					CommonConstants.ENTITY_TYPE_ALERT)) {
					
				LOGGER.info("Recevied update request entity Type = "+ CommonConstants.ENTITY_TYPE_ALERT);
					

				orchestrationService.processAlert(updateProfileRequest
						.getEntityValue().getAlertList(), RequestMethod.PUT,
						updateProfileRequest.getEntityId());
				
				service.setStatus(CommonConstants.SUCCESS);


			}

			
		

		} catch (SharedServiceException e) {

			LOGGER.error(e.getMessage());
			
			String errorCode = e.getErrorCode();
			String errorMessage = e.getMessage();

			service = CommonUtil.setServiceMessage(errorCode, errorMessage,
					CommonConstants.ERROR);

		}

		return service;
	}
	
	@RequestMapping(value = CommonConstants.PROFILE, method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public final Service deleteProfile(
			@RequestBody updateProfileRequest updateProfileRequest) {
		Service service = new Service();

		try {
			CommonUtil.validateEntity(updateProfileRequest);

						if (updateProfileRequest.getEntityType().equalsIgnoreCase(
					CommonConstants.ENTITY_TYPE_ADDRESS)) {

				orchestrationService.processAddress(updateProfileRequest
						.getEntityValue().getAddress(), RequestMethod.PUT,
						updateProfileRequest.getEntityId(),updateProfileRequest.getSourceSystem());

			}
			
			updateProfileResponse updateProfileResponse = new updateProfileResponse();
			updateProfileResponse.setPtpId("Test_ptpId_1234");
			updateProfileResponse.setUserId("Test_UserId_1234");

			service.setPayload(updateProfileResponse);
			service.setStatus(CommonConstants.SUCCESS);

		} catch (SharedServiceException e) {

			String errorCode = e.getErrorCode();
			String errorMessage = e.getMessage();

			service = CommonUtil.setServiceMessage(errorCode, errorMessage,
					CommonConstants.ERROR);

		}

		return service;
	}

	
	
	@RequestMapping(value = CommonConstants.PROFILE_STATUS, method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public final String checkStatus() {
		return CommonConstants.API_STATUS;
	}
		
}