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

import org.apache.commons.lang.StringUtils;
import org.jose4j.lang.JoseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.wiley.gr.ace.auth.security.constants.CommonConstant;
import com.wiley.gr.ace.auth.security.model.Response;
import com.wiley.gr.ace.auth.security.model.TokenRequest;
import com.wiley.gr.ace.auth.security.service.TokenService;

/**
 * @author Virtusa
 *
 */
@RestController
@RequestMapping("/")
public class TokenController {

	/**
	 * This field holds the value of LOGGER
	 */
	private static final Logger LOGGER = LoggerFactory
			.getLogger(TokenController.class);

	/**
	 * This field holds the value of tokenMessage001
	 */
	@Value("${token.message.001}")
	private String tokenMessage001;

	/**
	 * This field holds the value of tokenMessage002
	 */
	@Value("${token.message.002}")
	private String tokenMessage002;

	/**
	 * This field holds the value of tokenMessage003
	 */
	@Value("${token.message.003}")
	private String tokenMessage003;

	/**
	 * This field holds the value of tokenMessage004
	 */
	@Value("${token.message.004}")
	private String tokenMessage004;

	/**
	 * This field holds the value of tokenMessage005
	 */
	@Value("${token.message.005}")
	private String tokenMessage005;

	/**
	 * This field holds the value of tokenMessage006
	 */
	@Value("${token.message.006}")
	private String tokenMessage006;

	/**
	 * This field holds the value of tokenMessage007
	 */
	@Value("${token.message.007}")
	private String tokenMessage007;

	/**
	 * This field holds the value of tokenMessage008
	 */
	@Value("${token.message.008}")
	private String tokenMessage008;

	/**
	 * This field holds the value of tokenMessage009
	 */
	@Value("${token.message.009}")
	private String tokenMessage009;

	/**
	 * This field holds the value of messageProp
	 */
	@Autowired
	@Qualifier(value = "messageProperties")
	private Properties messageProp;

	/**
	 * This field holds the value of tokenService
	 */
	@Autowired(required = true)
	private TokenService tokenService;

	/**
	 * This field holds the value of AUTH_HEADER_NAME
	 */
	public static String AUTH_HEADER_NAME = "X-AS2-AUTH-TOKEN";

	/**
	 * Method to create a token and respond back the token in the header.
	 *
	 * @param tokenRequest
	 * @return ResponseEntity<Response>
	 */
	@RequestMapping(value = CommonConstant.TOKEN_SERVICE_URL, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<String> createToken(
			@RequestBody final TokenRequest tokenRequest) {
		TokenController.LOGGER.info("Creating Token...");
		// Validate the input request.
		try {
			if (StringUtils.isBlank(tokenRequest.getUserId())) {
				return new ResponseEntity<String>(
						this.messageProp.getProperty(this.tokenMessage001),
						null, HttpStatus.UNAUTHORIZED);
			}

			TokenController.LOGGER.info("Create Token Request..."
					+ tokenRequest.toString());

			if (tokenRequest.getRoles().size() == 0) {
				return new ResponseEntity<String>(
						this.messageProp.getProperty(this.tokenMessage002),
						null, HttpStatus.UNAUTHORIZED);
			}

			if (StringUtils.isBlank(tokenRequest.getAppKey())) {
				return new ResponseEntity<String>(
						this.messageProp.getProperty(this.tokenMessage003),
						null, HttpStatus.UNAUTHORIZED);
			}

			String token = null;
			try {
				token = this.tokenService.generateToken(tokenRequest);
				TokenController.LOGGER.debug("Token...", token);
			} catch (final JoseException e) {
				TokenController.LOGGER.error(
						"Exception Occurred during token creation...", e);
				return new ResponseEntity<String>(
						this.messageProp.getProperty(this.tokenMessage004),
						null, HttpStatus.UNAUTHORIZED);
			}
			// Set the token to the response header.
			final HttpHeaders responseHeaders = new HttpHeaders();
			responseHeaders.set(TokenController.AUTH_HEADER_NAME, token);
			responseHeaders.setContentType(MediaType.APPLICATION_JSON);
			return new ResponseEntity<String>(
					this.messageProp.getProperty(this.tokenMessage005),
					responseHeaders, HttpStatus.OK);
		} catch (final Exception e) {
			TokenController.LOGGER.error(
					"Exception Occurred during token creation...", e);
			return new ResponseEntity<String>(CommonConstant.EXCEPTION, null,
					HttpStatus.UNAUTHORIZED);
		}

	}

	/**
	 * This method will refresh the token with expiration time.
	 *
	 * @param token
	 * @return ResponseEntity<Response>
	 */
	@RequestMapping(value = CommonConstant.TOKEN_REFRESH_SERVICE_URL, method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<Response> refreshToken(
			@RequestHeader(value = "X-AS2-AUTH-TOKEN") final String token) {
		TokenController.LOGGER.info("Refreshing Token...");
		try {
			if (this.tokenService.validateToken(token)) {
				TokenController.LOGGER.info("Validated Token...");
				final String refreshToken = this.tokenService
						.refreshToken(token);
				final HttpHeaders responseHeaders = new HttpHeaders();
				responseHeaders.set(TokenController.AUTH_HEADER_NAME,
						refreshToken);
				responseHeaders.setContentType(MediaType.APPLICATION_JSON);
				return new ResponseEntity<>(new Response(
						this.messageProp.getProperty(this.tokenMessage008)),
						responseHeaders, HttpStatus.CREATED);
			} else {
				return new ResponseEntity<>(new Response(
						this.messageProp.getProperty(this.tokenMessage009)),
						null, HttpStatus.CREATED);
			}
		} catch (final Exception e) {
			TokenController.LOGGER.error(
					"Exception Occurred during refresh token...", e);
			return new ResponseEntity<>(new Response(
					this.messageProp.getProperty(this.tokenMessage009)), null,
					HttpStatus.CONFLICT);
		}
	}

	/**
	 * This method to validate token.
	 *
	 * @return ResponseEntity<Response>
	 * @throws InterruptedException
	 */
	@RequestMapping(value = CommonConstant.TOKEN_SERVICE_URL, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<String> validateToken(
			@RequestHeader(value = "X-AS2-AUTH-TOKEN") final String token) {
		TokenController.LOGGER.info("Validating Token...");
		try {
			// Verify whether the token is valid or not.

			if (this.tokenService.validateToken(token)) {
				TokenController.LOGGER.info("Token Validated...");
				TokenController.LOGGER.debug("Token", token);
				// Set it back in the header response.
				final HttpHeaders responseHeaders = new HttpHeaders();
				responseHeaders.set(TokenController.AUTH_HEADER_NAME, token);
				responseHeaders.setContentType(MediaType.APPLICATION_JSON);
				return new ResponseEntity<String>(
						this.messageProp.getProperty(this.tokenMessage006),
						responseHeaders, HttpStatus.OK);
			}
		} catch (final Exception e) {
			TokenController.LOGGER.error(
					"Exception Occurred during token validation...", e);
			return new ResponseEntity<String>(
					this.messageProp.getProperty(this.tokenMessage007), null,
					HttpStatus.OK);
		}
		return new ResponseEntity<String>(
				this.messageProp.getProperty(this.tokenMessage007), null,
				HttpStatus.OK);
	}
}
