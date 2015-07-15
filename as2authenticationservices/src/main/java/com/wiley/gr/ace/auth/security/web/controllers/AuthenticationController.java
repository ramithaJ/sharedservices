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

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ldap.NameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wiley.gr.ace.auth.security.constants.CommonConstant;
import com.wiley.gr.ace.auth.security.model.AuthenticateRequest;
import com.wiley.gr.ace.auth.security.model.Response;
import com.wiley.gr.ace.auth.security.model.TokenRequest;
import com.wiley.gr.ace.auth.security.model.User;
import com.wiley.gr.ace.auth.security.service.AuthenticationService;

/**
 * @author Virtusa
 *
 */
@Controller
@RequestMapping("/")
public class AuthenticationController {

	/**
	 * This field holds the value of LOGGER
	 */
	private static final Logger LOGGER = LoggerFactory
			.getLogger(AuthenticationController.class);

	/**
	 * This field holds the value of authMessage001
	 */
	@Value("${auth.message.001}")
	private String authMessage001;

	/**
	 * This field holds the value of authMessage002
	 */
	@Value("${auth.message.002}")
	private String authMessage002;

	/**
	 * This field holds the value of authMessage003
	 */
	@Value("${auth.message.003}")
	private String authMessage003;

	/**
	 * This field holds the value of authMessage004
	 */
	@Value("${auth.message.004}")
	private String authMessage004;

	/**
	 * This field holds the value of authMessage005
	 */
	@Value("${auth.message.005}")
	private String authMessage005;

	/**
	 * This field holds the value of authMessage006
	 */
	@Value("${auth.message.006}")
	private String authMessage006;

	/**
	 * This field holds the value of authMessage007
	 */
	@Value("${auth.message.007}")
	private String authMessage007;

	/**
	 * This field holds the value of authMessage007
	 */
	@Value("${auth.message.008}")
	private String authMessage008;

	/**
	 * This field holds the value of authenticationService
	 */
	@Autowired(required = true)
	private AuthenticationService authenticationService;

	/**
	 * This field holds the value of AUTH_HEADER_NAME
	 */
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
	 * @return ResponseEntity<Response>
	 */
	@RequestMapping(value = CommonConstant.AUTHENTICATE_SERVICE_URL, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<Response> authenticateUser(
			@Valid @RequestBody final AuthenticateRequest request) {
		AuthenticationController.LOGGER.info("Authenticating User...");
		Response authResponse = null;
		try {

			// Validate the input request.
			if (null == request) {
				return new ResponseEntity<>(new Response(this.authMessage001),
						null, HttpStatus.OK);
			}

			authResponse = this.authenticationService.userLogin(request);

			if (null != authResponse
					&& authResponse.getStatus().equalsIgnoreCase(
							String.valueOf(Response.STATUS.LOCKED))) {

				return new ResponseEntity<>(new Response(
						CommonConstant.LOCKED_CODE, this.authMessage007,
						authResponse.getStatus()), null, HttpStatus.LOCKED);
			}

			if (null == authResponse
					|| authResponse.getStatus().equalsIgnoreCase(
							String.valueOf(Response.STATUS.FAILURE))) {

				AuthenticationController.LOGGER
						.info("Authentication Response..." + authResponse);

				return new ResponseEntity<>(new Response(
						CommonConstant.FAIL_CODE, this.authMessage005,
						String.valueOf(Response.STATUS.FAILURE)), null,
						HttpStatus.UNAUTHORIZED);
			}
			// Set the token in the response headers.
			final HttpHeaders responseHeaders = new HttpHeaders();
			responseHeaders.set(AuthenticationController.AUTH_HEADER_NAME,
					authResponse.getMessage());
			responseHeaders.setContentType(MediaType.APPLICATION_JSON);

			return new ResponseEntity<>(new Response(
					CommonConstant.STATUS_CODE, this.authMessage006,
					String.valueOf(Response.STATUS.SUCCESS)), responseHeaders,
					HttpStatus.OK);
		} catch (final Exception e) {
			AuthenticationController.LOGGER.error(
					"Exception Occurred during user authentication...", e);
			return new ResponseEntity<>(new Response(CommonConstant.EXCEPTION),
					null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = CommonConstant.SEARCH_USER_URL, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<User> searchUser(
			@Valid @RequestBody final TokenRequest request) {

		try {
			User user = this.authenticationService.searchUser(request
					.getUserId());
			return new ResponseEntity<>(user, null, HttpStatus.OK);
		} catch (NameNotFoundException nameNotFoundException) {
			return new ResponseEntity<>(new User(CommonConstant.FAIL_CODE,
					this.authMessage008, CommonConstant.FAILURE_STATUS), null,
					HttpStatus.UNAUTHORIZED);
		}
	}
}