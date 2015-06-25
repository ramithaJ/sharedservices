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
package com.wiley.gr.ace.sharedservices.controllers;

import com.wiley.gr.ace.sharedservices.common.CommonConstants;
import com.wiley.gr.ace.sharedservices.exceptions.SharedServiceException;
import com.wiley.gr.ace.sharedservices.helper.UserServiceHelper;
import com.wiley.gr.ace.sharedservices.payload.*;
import com.wiley.gr.ace.sharedservices.service.UserService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Properties;

/**
 * @author kkalyan
 */
@RestController
@RequestMapping("/v1/")
public class UserServiceController {

    //Logger Instance
    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceController.class);

    @Autowired
    private UserService userService;

    @Autowired
    @Qualifier(value = "messageProperties")
    private Properties messageProp;

    @Value("${USER_SERVICE_ERROR_101}")
    private String userServiceError101;

    /**
     * Method to Create User
     *
     * @param userServiceRequest
     */
    @RequestMapping(value = CommonConstants.CREATE_USER_REQUEST_PATH, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Service createUserService(@RequestBody UserServiceRequest userServiceRequest) {
        Service service = new Service();
        try {
            String authorServicesUniqueIdentifier = userService.createUserService(userServiceRequest);
            service.setPayload(new UserId(authorServicesUniqueIdentifier));
        } catch (SharedServiceException e) {
            LOGGER.error("Error Occurred in Create User Service", e);
            return UserServiceHelper.setServiceMessage("10001", e.getMessage(), CommonConstants.ERROR);
        }
        return service;
    }

    /**
     * Method to Delete User
     *
     * @param deleteProfileRequest
     */
    @RequestMapping(value = CommonConstants.CREATE_USER_REQUEST_PATH, method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Service deleteUserService(@RequestBody DeleteProfileRequest deleteProfileRequest) {
        Service service = new Service();
        try {
            String authorServicesUniqueIdentifier = deleteProfileRequest.getUserProfile().getAsid();
            if (StringUtils.isEmpty(authorServicesUniqueIdentifier)) {
                throw new SharedServiceException(messageProp.getProperty(userServiceError101));
            }

            userService.deleteUserService(authorServicesUniqueIdentifier);
        } catch (SharedServiceException e) {
            LOGGER.error("Error Occurred in Create User Service", e);
            return UserServiceHelper.setServiceMessage("10001", e.getMessage(), CommonConstants.ERROR);
        }
        return service;
    }


    /**
     * Method to update User.
     *
     * @param userServiceRequest
     * @param userId
     */
    @RequestMapping(value = CommonConstants.REQUEST_PATH, method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Service updateUserService(@RequestBody UserServiceRequest userServiceRequest, @PathVariable(CommonConstants.USER_ID) String userId) {
        try {
            if (StringUtils.isEmpty(userId)) {
                return UserServiceHelper.setServiceMessage(userServiceError101, messageProp.getProperty(userServiceError101), CommonConstants.ERROR);
            }
            userService.updateUserProfileService(userServiceRequest, userId);
            LOGGER.debug("Update User Service:", userId);
        } catch (SharedServiceException e) {
            LOGGER.error("Error Occurred in Update User Service", e);
            return UserServiceHelper.setServiceMessage("10001", e.getMessage(), CommonConstants.ERROR);
        }
        return new Service();
    }

    /**
     * Method to get user.
     *
     * @param userId
     */
    @RequestMapping(value = CommonConstants.REQUEST_PATH, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Service getUserService(@PathVariable(CommonConstants.USER_ID) String userId) {
        Service service = new Service();
        UserServiceRequest userServiceRequest = null;
        try {
            if (StringUtils.isEmpty(userId)) {
                return UserServiceHelper.setServiceMessage(userServiceError101, messageProp.getProperty(userServiceError101), CommonConstants.ERROR);
            }
            LOGGER.debug("Get User Service:", userId);
            userServiceRequest = userService.getUserProfileService(userId);
        } catch (SharedServiceException e) {
            LOGGER.error("Error Occurred in Get User Service", e);
            return UserServiceHelper.setServiceMessage("10001", e.getMessage(), CommonConstants.ERROR);
        }
        service.setPayload(userServiceRequest);
        return service;
    }


}
