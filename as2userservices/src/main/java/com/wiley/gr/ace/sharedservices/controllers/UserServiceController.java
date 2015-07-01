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
import com.wiley.gr.ace.sharedservices.common.Property;
import com.wiley.gr.ace.sharedservices.exceptions.SharedServiceException;
import com.wiley.gr.ace.sharedservices.helper.UserServiceHelper;
import com.wiley.gr.ace.sharedservices.payload.*;
import com.wiley.gr.ace.sharedservices.service.UserService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * @author kkalyan
 */
@RestController
@RequestMapping("/v1/")
public class UserServiceController extends Property {

    //Logger Instance
    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceController.class);

    @Autowired
    private UserService userService;

    /**
     * Method to Create User
     *
     * @param userServiceRequest
     */
    @RequestMapping(value = CommonConstants.USER_SERVICE_REQUEST_PATH, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Service createUserService(@RequestBody UserServiceRequest userServiceRequest) {
        Service service = new Service();
        try {
            String authorServicesUniqueIdentifier = userService.createUserService(userServiceRequest);
            service.setPayload(new UserId(authorServicesUniqueIdentifier));
        } catch (SharedServiceException e) {
            LOGGER.error("Error Occurred in Create User Service", e);
            return UserServiceHelper.setServiceMessage(e.getErrorCode(), e.getMessage(), CommonConstants.ERROR);
        }
        return service;
    }

    /**
     * Method to Delete User
     *
     * @param deleteProfileRequest
     */
    @RequestMapping(value = CommonConstants.USER_SERVICE_REQUEST_PATH, method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Service deleteUserService(@RequestBody DeleteProfileRequest deleteProfileRequest) {
        Service service = new Service();
        try {
            String authorServicesUniqueIdentifier = deleteProfileRequest.getUserProfile().getAsid();
            if (StringUtils.isEmpty(authorServicesUniqueIdentifier)) {
                throw new SharedServiceException(CommonConstants.ERROR_CODE_101, userServiceError101);
            }

            userService.deleteUserService(authorServicesUniqueIdentifier);
        } catch (SharedServiceException e) {
            LOGGER.error("Error Occurred in Create User Service", e);
            return UserServiceHelper.setServiceMessage(e.getErrorCode(), e.getMessage(), CommonConstants.ERROR);
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
                return UserServiceHelper.setServiceMessage(CommonConstants.ERROR_CODE_101, userServiceError101, CommonConstants.ERROR);
            }
            userService.updateUserProfileService(userServiceRequest, userId);
            LOGGER.debug("Update User Service:", userId);
        } catch (SharedServiceException e) {
            LOGGER.error("Error Occurred in Update User Service", e);
            return UserServiceHelper.setServiceMessage(e.getErrorCode(), e.getMessage(), CommonConstants.ERROR);
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
                return UserServiceHelper.setServiceMessage(CommonConstants.ERROR_CODE_101, userServiceError101, CommonConstants.ERROR);
            }
            LOGGER.debug("Get User Service:", userId);
            userServiceRequest = userService.getUserProfileService(userId);
        } catch (SharedServiceException e) {
            LOGGER.error("Error Occurred in Get User Service", e);
            return UserServiceHelper.setServiceMessage(e.getErrorCode(), e.getMessage(), CommonConstants.ERROR);
        }
        service.setPayload(userServiceRequest);
        return service;
    }

    /**
     * Lookup method for user
     *
     * @param lookUp
     * @return
     */
    @RequestMapping(value = CommonConstants.LOOK_UP, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Service lookUpAuthorService(@RequestBody LookupRequest lookUp) {
        Service service = new Service();
        try {
            //Check whether email address is null or empty. Throw error
            if (StringUtils.isEmpty(lookUp.getUserProfile().getEmailAddress())) {
                return UserServiceHelper.setServiceMessage(CommonConstants.ERROR_CODE_201, userlookUpServiceError201, CommonConstants.ERROR);
            }
            LookupResponse response = userService.userlookUpService(lookUp.getUserProfile().getFirstName(), lookUp.getUserProfile().getLastName(), lookUp.getUserProfile().getEmailAddress());
            service.setPayload(response);
        } catch (SharedServiceException e) {
            LOGGER.error("Error Occurred in Lookup User Service", e);
            return UserServiceHelper.setServiceMessage(e.getErrorCode(), e.getMessage(), CommonConstants.ERROR);
        }

        return service;
    }

    /**
     * User Search controller service.
     *
     * @param semail
     * @param fn
     * @param ln
     * @param oid
     * @return
     */
    @RequestMapping(value = CommonConstants.USER_SERVICE_REQUEST_PATH, method = RequestMethod.GET)
    @ResponseBody
    public Service searchUserService(@RequestParam(value = "semail", required = true) String semail, @RequestParam(value = "fn", required = true) String fn, @RequestParam(value = "ln", required = true) String ln, @RequestParam(value = "oid", required = true) String oid) {
        Service service = new Service();
        try {

            //Validate the input parameters.
            if (StringUtils.isEmpty(semail)) {
                return UserServiceHelper.setServiceMessage(CommonConstants.ERROR_CODE_201, userlookUpServiceError201, CommonConstants.ERROR);
            }
            if (StringUtils.isEmpty(fn)) {
                return UserServiceHelper.setServiceMessage(CommonConstants.ERROR_CODE_103, userlookUpServiceError103, CommonConstants.ERROR);
            }
            if (StringUtils.isEmpty(ln)) {
                return UserServiceHelper.setServiceMessage(CommonConstants.ERROR_CODE_104, userlookUpServiceError104, CommonConstants.ERROR);
            }
            if (StringUtils.isEmpty(oid)) {
                return UserServiceHelper.setServiceMessage(CommonConstants.ERROR_CODE_108, userlookUpServiceError108, CommonConstants.ERROR);
            }
            //Search user with the input parameters.
            service = userService.searchUserService(semail, fn, ln, oid);
        } catch (SharedServiceException e) {
            LOGGER.error("Error Occurred in Search User Service", e);
            return UserServiceHelper.setServiceMessage(e.getErrorCode(), e.getMessage(), CommonConstants.ERROR);
        }

        return service;
    }


}
