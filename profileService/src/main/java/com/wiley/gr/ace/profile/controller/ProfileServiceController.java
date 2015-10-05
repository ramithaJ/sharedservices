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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	 * Logger for UserLoginController class.
	 */
	private static final Logger LOGGER = LoggerFactory
			.getLogger(ProfileServiceController.class);

	@RequestMapping(value = CommonConstants.PROFILE, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public final Service updateProfile(@RequestBody updateProfileRequest profile) {
		Service service = new Service();

		
		try{
			CommonUtil.validateEntity(profile);
			updateProfileResponse updateProfileResponse = new updateProfileResponse();
			updateProfileResponse.setPtpId("Test_ptpId_1234");
			updateProfileResponse.setUserId("Test_UserId_1234");
			
			service.setPayload(updateProfileResponse);
			service.setStatus(CommonConstants.SUCCESS);
				
		}catch(SharedServiceException e){
			
			String errorCode = e.getErrorCode();
			String errorMessage = e.getMessage();
			
			service = CommonUtil.setServiceMessage(errorCode, errorMessage,CommonConstants.ERROR);

		}
		
		return service;

	}

}