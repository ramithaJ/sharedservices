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

    //User Service Implementation
    @Autowired
    private UserService userService;

    /**
     * Controller method to create user.
     *
     * @param userServiceRequest Request Java Mapping Object
     * @return Returns Service response Object
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
     * Controller method for user delete.
     *
     * @param userId Author Services Unique Id
     * @return Returns Service response Object
     */
    @RequestMapping(value = CommonConstants.REQUEST_PATH, method = RequestMethod.DELETE)
    @ResponseBody
    public Service deleteUserService(@PathVariable(CommonConstants.USER_ID) String userId) {
        Service service = new Service();
        try {
            //Validate user id
            if (StringUtils.isEmpty(userId)) {
                throw new SharedServiceException(CommonConstants.ERROR_CODE_101, userServiceError101);
            }
            userService.deleteUserService(userId);
        } catch (SharedServiceException e) {
            LOGGER.error("Error Occurred in Create User Service", e);
            return UserServiceHelper.setServiceMessage(e.getErrorCode(), e.getMessage(), CommonConstants.ERROR);
        }
        return service;
    }


    /**
     * Controller method to update user.
     *
     * @param userServiceRequest Request Java Mapping Object
     * @param userId             Author Services Unique id of the User
     * @return Returns Service response Object
     */
    @RequestMapping(value = CommonConstants.REQUEST_PATH, method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Service updateUserService(@RequestBody UserServiceRequest userServiceRequest, @PathVariable(CommonConstants.USER_ID) String userId) {
        Service service = new Service();
        try {
            UserServiceRequest userServiceResponse = null;
            //Validate user id
            if (StringUtils.isEmpty(userId)) {
                return UserServiceHelper.setServiceMessage(CommonConstants.ERROR_CODE_101, userServiceError101, CommonConstants.ERROR);
            }
            userServiceResponse = userService.updateUserProfileService(userServiceRequest, userId);
            service.setPayload(userServiceResponse);
            LOGGER.debug("Update User Service:", userId);
        } catch (SharedServiceException e) {
            LOGGER.error("Error Occurred in Update User Service", e);
            return UserServiceHelper.setServiceMessage(e.getErrorCode(), e.getMessage(), CommonConstants.ERROR);
        }
        return service;
    }

    /**
     * Controller method for user lookup.
     *
     * @param emailAddress Email Address - Primary (or) Secondary
     * @param firstName    FirstName of the user
     * @param lastName     LastName of the user
     * @return Returns Service response Object
     */
    @RequestMapping(value = CommonConstants.LOOK_UP, method = RequestMethod.GET)
    @ResponseBody
    public Service lookUpAuthorService(@RequestParam(value = "emailAddress", required = false) String emailAddress, @RequestParam(value = "firstName", required = false) String firstName, @RequestParam(value = "lastName", required = false) String lastName) {
        Service service = new Service();
        try {
            //Check whether email address is null or empty. Throw error if it is null (or) emtpy
            if (StringUtils.isEmpty(emailAddress)) {
                return UserServiceHelper.setServiceMessage(CommonConstants.ERROR_CODE_201, userlookUpServiceError201, CommonConstants.ERROR);
            }
            LookupResponse response = userService.userlookUpService(firstName, lastName, emailAddress);
            service.setPayload(response);
        } catch (SharedServiceException e) {
            LOGGER.error("Error Occurred in Lookup User Service", e);
            return UserServiceHelper.setServiceMessage(e.getErrorCode(), e.getMessage(), CommonConstants.ERROR);
        }

        return service;
    }

    /**
     * Controller method for search user.
     *
     * @param semail Either Primary Email (or) Secondary Email of the user
     * @param userId User Id (or) User Primary Email Address
     * @param fn     FirstName
     * @param ln     LastName
     * @param oid    Orcid Id
     * @return Returns Service response Object
     */
    @RequestMapping(value = CommonConstants.USER_SERVICE_REQUEST_PATH, method = RequestMethod.GET)
    @ResponseBody
    public Service searchUserService(@RequestParam(value = CommonConstants.USER_ID, required = false) String userId, @RequestParam(value = "semail", required = false) String semail, @RequestParam(value = "fn", required = false) String fn, @RequestParam(value = "ln", required = false) String ln, @RequestParam(value = "oid", required = false) String oid) {
        Service service = new Service();


        if (!StringUtils.isEmpty(userId)) {
            UserServiceRequest userServiceRequest = null;
            try {
                //Validate user id
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
        } else {
            try {

                //Check whether atleast one input parameter is
                if (StringUtils.isEmpty(semail) && StringUtils.isEmpty(fn) && StringUtils.isEmpty(ln) && StringUtils.isEmpty(oid)) {
                    return UserServiceHelper.setServiceMessage(CommonConstants.ERROR_CODE_304, userSearchServiceError304, CommonConstants.ERROR);
                }

                //Search user with the input parameters.
                service = userService.searchUserService(semail, fn, ln, oid);

            } catch (SharedServiceException e) {
                LOGGER.error("Error Occurred in Search User Service", e);
                return UserServiceHelper.setServiceMessage(e.getErrorCode(), e.getMessage(), CommonConstants.ERROR);
            }
        }

        return service;
    }


}
