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
import com.wiley.gr.ace.auth.security.model.AuthenticateRequest;
import com.wiley.gr.ace.auth.security.model.Response;
import com.wiley.gr.ace.auth.security.model.TokenRequest;
import com.wiley.gr.ace.auth.security.service.AuthenticationService;
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

@RestController
@RequestMapping("/")
public class AuthenticationController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationController.class);

    @Autowired
    @Qualifier(value = "messageProperties")
    private Properties messageProp;

    @Autowired(required = true)
    private AuthenticationService authenticationService;


    //Variable to hold the header name of the token.
    public static String AUTH_HEADER_NAME = "X-AS2-AUTH-TOKEN";

    /**
     * Method to authenticate the user against Wiley AD (or) LDAP server based on the input authentication type parameter. If the authentication type parameter is passed as "AUTO" it will pick the corresponding authentication server details based on domain name in the userId value.
     *
     * @param request Request Object which will contain userId, Password, ApplicationKey & Authentication Type
     * @return
     */
    @RequestMapping(value = CommonConstant.AUTHENTICATE_SERVICE_URL, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)

    public
    @ResponseBody
    ResponseEntity<Response> authenticateUser(
            @RequestBody AuthenticateRequest request) {
        LOGGER.info("Authenticating User...");
        Response authResponse = null;
        try {

            //Validate the input request.
            if (null == request) {
                return new ResponseEntity<>(new Response(messageProp.getProperty(CommonConstant.AUTH_001)), null, HttpStatus.UNAUTHORIZED);
            }
            LOGGER.info("Authentication Request..." + request.toString());
            if (StringUtils.isBlank(request.getUserId())) {
                return new ResponseEntity<>(new Response(messageProp.getProperty(CommonConstant.AUTH_002)), null, HttpStatus.UNAUTHORIZED);
            }

            if (StringUtils.isBlank(request.getPassword())) {
                return new ResponseEntity<>(new Response(messageProp.getProperty(CommonConstant.AUTH_003)), null, HttpStatus.UNAUTHORIZED);
            }

            if (StringUtils.isBlank(request.getAppKey())) {
                return new ResponseEntity<>(new Response(messageProp.getProperty(CommonConstant.AUTH_004)), null, HttpStatus.UNAUTHORIZED);
            }

            authResponse = authenticationService.authenticate(request.getUserId(), request.getPassword(), request.getAuthenticationType(), request.getAppKey());
            if (null != authResponse && authResponse.getStatus().equalsIgnoreCase(String.valueOf(Response.STATUS.FAILURE))) {
                LOGGER.info("Authentication Response..." + authResponse);
                return new ResponseEntity<>(new Response(messageProp.getProperty(CommonConstant.AUTH_005)), null, HttpStatus.UNAUTHORIZED);
            }
            //Set the token in the response headers.
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.set(AUTH_HEADER_NAME, authResponse.getMessage());
            responseHeaders.setContentType(MediaType.APPLICATION_JSON);
            return new ResponseEntity<>(new Response(messageProp.getProperty(CommonConstant.AUTH_006)), responseHeaders, HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error("Exception Occurred during user authentication...", e);
            return new ResponseEntity<>(new Response(CommonConstant.EXCEPTION), null, HttpStatus.UNAUTHORIZED);
        }
    }


}