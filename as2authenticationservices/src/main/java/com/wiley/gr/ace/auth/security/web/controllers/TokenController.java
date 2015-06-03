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

import com.wiley.gr.ace.auth.security.constants.CommonConstant;
import com.wiley.gr.ace.auth.security.model.Response;
import com.wiley.gr.ace.auth.security.model.TokenRequest;
import com.wiley.gr.ace.auth.security.service.TokenService;
import org.apache.commons.lang.StringUtils;
import org.jose4j.lang.JoseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Properties;

/**
 * Created by kkalyan on 5/27/2015.
 */
@RestController
@RequestMapping("/")
public class TokenController {


    private static final Logger LOGGER = LoggerFactory.getLogger(TokenController.class);

    @Autowired
    @Qualifier(value = "messageProperties")
    private Properties messageProp;


    @Autowired(required = true)
    private TokenService tokenService;

    //Variable to hold the header name of the token.
    public static String AUTH_HEADER_NAME = "X-AS2-AUTH-TOKEN";


    /**
     * Method to create a token and respond back the token in the header.
     *
     * @param tokenRequest
     * @return
     */
    @RequestMapping(value = CommonConstant.TOKEN_SERVICE_URL, method = RequestMethod.POST, consumes =
            MediaType.APPLICATION_JSON_VALUE)

    public
    @ResponseBody
    ResponseEntity<String> createToken(@RequestBody TokenRequest tokenRequest) {
        Response tokenResponse = null;
        LOGGER.info("Creating Token...");
        //Validate the input request.
        try {
            if (StringUtils.isBlank(tokenRequest.getUserId())) {
                return new ResponseEntity<String>(messageProp.getProperty(CommonConstant.TOK_001), null, HttpStatus.UNAUTHORIZED);
            }

            LOGGER.info("Create Token Request..." + tokenRequest.toString());

            if (tokenRequest.getRoles().size() == 0) {
                return new ResponseEntity<String>(messageProp.getProperty(CommonConstant.TOK_002), null, HttpStatus.UNAUTHORIZED);
            }

            if (StringUtils.isBlank(tokenRequest.getAppKey())) {
                return new ResponseEntity<String>(messageProp.getProperty(CommonConstant.TOK_003), null, HttpStatus.UNAUTHORIZED);
            }

            String token = null;
            try {
                token = tokenService.generateToken(tokenRequest);
                LOGGER.debug("Token...", token);
            } catch (JoseException e) {
                LOGGER.error("Exception Occurred during token creation...", e);
                return new ResponseEntity<String>(messageProp.getProperty(CommonConstant.TOK_004), null, HttpStatus.UNAUTHORIZED);
            }
            //Set the token to the response header.
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.set(AUTH_HEADER_NAME, token);
            responseHeaders.setContentType(MediaType.APPLICATION_JSON);
            return new ResponseEntity<String>(messageProp.getProperty(CommonConstant.TOK_005), responseHeaders, HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error("Exception Occurred during token creation...", e);
            return new ResponseEntity<String>(CommonConstant.EXCEPTION, null, HttpStatus.UNAUTHORIZED);
        }

    }

    /**
     * This method to validate token.
     *
     * @return the name of the JSP page
     * @throws InterruptedException
     */
    @RequestMapping(value = CommonConstant.TOKEN_SERVICE_URL, method = RequestMethod.GET, produces =
            MediaType.APPLICATION_JSON_VALUE)

    public
    @ResponseBody
    ResponseEntity<String> validateToken(@RequestHeader(value = "X-AS2-AUTH-TOKEN") String token) {
        LOGGER.info("Validating Token...");
        try {
            //Verify whether the token is valid or not.

            if (tokenService.validateToken(token)) {
                LOGGER.info("Token Validated...");
                LOGGER.debug("Token", token);
                //Set it back in the header response.
                HttpHeaders responseHeaders = new HttpHeaders();
                responseHeaders.set(AUTH_HEADER_NAME, token);
                responseHeaders.setContentType(MediaType.APPLICATION_JSON);
                return new ResponseEntity<String>(messageProp.getProperty(CommonConstant.TOK_006), responseHeaders, HttpStatus.OK);
            }
        } catch (Exception e) {
            LOGGER.error("Exception Occurred during token validation...", e);
            return new ResponseEntity<String>(messageProp.getProperty(CommonConstant.TOK_007), null, HttpStatus.OK);
        }
        return new ResponseEntity<String>(messageProp.getProperty(CommonConstant.TOK_007), null, HttpStatus.OK);
    }

    /**
     * This method will refresh the token with expiration time.
     *
     * @param token
     * @return
     */
    @RequestMapping(value = CommonConstant.TOKEN_REFRESH_SERVICE_URL, method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE
    )

    public
    @ResponseBody
    ResponseEntity<Response> refreshToken(@RequestHeader(value = "X-AS2-AUTH-TOKEN") String token) {
        LOGGER.info("Refreshing Token...");
        try {
            if (tokenService.validateToken(token)) {
                LOGGER.info("Validated Token...");
                String refreshToken = tokenService.refreshToken(token);
                HttpHeaders responseHeaders = new HttpHeaders();
                responseHeaders.set(AUTH_HEADER_NAME, refreshToken);
                responseHeaders.setContentType(MediaType.APPLICATION_JSON);
                return new ResponseEntity<>(new Response(messageProp.getProperty(CommonConstant.TOK_008)), responseHeaders, HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(new Response(messageProp.getProperty(CommonConstant.TOK_009)), null, HttpStatus.CONFLICT);
            }
        } catch (Exception e) {
            LOGGER.error("Exception Occurred during refresh token...", e);
            return new ResponseEntity<>(new Response(messageProp.getProperty(CommonConstant.TOK_009)), null, HttpStatus.CONFLICT);
        }
    }


}
