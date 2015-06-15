/**
 * ****************************************************************************
 * Copyright (c) 2015 John Wiley & Sons, Inc. All rights reserved.
 * <p/>
 * All material contained herein is proprietary to John Wiley & Sons
 * and its third party suppliers, if any. The methods, techniques and
 * technical concepts contained herein are considered trade secrets
 * and confidential and may be protected by intellectual property laws.
 * Reproduction or distribution of this material, in whole or in part,
 * is strictly forbidden except by express prior written permission
 * of John Wiley & Sons.
 * *****************************************************************************
 */
package com.wiley.gr.ace.auth.security.web.controllers;

import java.util.Properties;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wiley.gr.ace.auth.security.constants.CommonConstant;
import com.wiley.gr.ace.auth.security.model.AuthenticateRequest;
import com.wiley.gr.ace.auth.security.model.Response;
import com.wiley.gr.ace.auth.security.model.User;
import com.wiley.gr.ace.auth.security.service.AuthenticationService;

@Controller
@RequestMapping("/")
public class AuthenticationController {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(AuthenticationController.class);

	@Autowired
	@Qualifier(value = "messageProperties")
	private Properties messageProp;

	@Autowired(required = true)
	private AuthenticationService authenticationService;

	// Variable to hold the header name of the token.
	public static String AUTH_HEADER_NAME = "X-AS2-AUTH-TOKEN";

	/**
	 * Method to authenticate the user against Wiley AD (or) LDAP server based
	 * on the input authentication type parameter. If the authentication type
	 * parameter is passed as "AUTO" it will pick the corresponding
	 * authentication server details based on domain name in the userId value.
	 *
	 * @param request
	 *            Request Object which will contain userId, Password,
	 *            ApplicationKey & Authentication Type
	 * @return
	 */
	@RequestMapping(value = CommonConstant.AUTHENTICATE_SERVICE_URL, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<Response> authenticateUser(
			@Valid @RequestBody AuthenticateRequest request) {
		LOGGER.info("Authenticating User...");
		Response authResponse = null;
		try {

			// Validate the input request.
			if (null == request) {
				return new ResponseEntity<>(new Response(
						messageProp.getProperty(CommonConstant.AUTH_001)),
						null, HttpStatus.UNAUTHORIZED);
			}

			authResponse = authenticationService.userLogin(request);
			
			if(null != authResponse
					&& authResponse.getStatus().equalsIgnoreCase(
					String.valueOf(Response.STATUS.LOCKED))) {
				
				return new ResponseEntity<>(new Response(
						CommonConstant.FAIL_CODE,
						messageProp.getProperty(CommonConstant.AUTH_007),
						String.valueOf(Response.STATUS.LOCKED)), null,
						HttpStatus.OK);
			}
			
			if (null == authResponse
					|| authResponse.getStatus().equalsIgnoreCase(
							String.valueOf(Response.STATUS.FAILURE))) {
				
				LOGGER.info("Authentication Response..." + authResponse);

				return new ResponseEntity<>(new Response(
						CommonConstant.FAIL_CODE,
						messageProp.getProperty(CommonConstant.AUTH_005),
						String.valueOf(Response.STATUS.FAILURE)), null,
						HttpStatus.UNAUTHORIZED);
			}
			// Set the token in the response headers.
			HttpHeaders responseHeaders = new HttpHeaders();
			responseHeaders.set(AUTH_HEADER_NAME, authResponse.getMessage());
			responseHeaders.setContentType(MediaType.APPLICATION_JSON);

			return new ResponseEntity<>(new Response(
					CommonConstant.STATUS_CODE,
					messageProp.getProperty(CommonConstant.AUTH_006),
					String.valueOf(Response.STATUS.SUCCESS)), responseHeaders,
					HttpStatus.OK);
		} catch (Exception e) {
			LOGGER.error("Exception Occurred during user authentication...", e);
			return new ResponseEntity<>(new Response(CommonConstant.EXCEPTION),
					null, HttpStatus.UNAUTHORIZED);
		}
	}
	
	@RequestMapping(value = CommonConstant.SEARCH_USER_URL, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody User searchUser(
			@Valid @RequestBody AuthenticateRequest request) {
				
		return authenticationService.searchUser(request.getUserId());
		
	}

}