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

import com.wiley.gr.ace.sharedservices.input.ErrorPOJO;
import com.wiley.gr.ace.sharedservices.input.Service;
import com.wiley.gr.ace.sharedservices.input.UserServiceRequest;
import com.wiley.gr.ace.sharedservices.service.UserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author kkalyan
 */
@RestController
@RequestMapping("/v1/")
public class UserServiceController {

    @Autowired
    private UserService userService;

    /**
     * Method to Create User Build Profile.
     *
     * @param userServiceRequest
     */
    @RequestMapping(value = "/profile/{userId}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Service createUserService(@RequestBody UserServiceRequest userServiceRequest, @PathVariable("userId") String userId) {
        Service service = new Service();
        try {
            if (StringUtils.isEmpty(userId)) {
                ErrorPOJO error = new ErrorPOJO();
                error.setCode(-101);
                error.setMessage("UserID is empty (or) null");
                service.setStatus("error");
                service.setError(error);
                return service;
            }
            userService.createUserService(userServiceRequest, userId);
        } catch (Exception e) {
            ErrorPOJO error = new ErrorPOJO();
            error.setCode(-101);
            error.setMessage("Error while create user profile service");
            service.setStatus("error");
            service.setError(error);
            return service;
        }
        return service;
    }


    /**
     * Method to update User Build Profile.
     *
     * @param userServiceRequest
     * @param userId
     */
    @RequestMapping(value = "/profile/{userId}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Service updateUserService(@RequestBody UserServiceRequest userServiceRequest, @PathVariable("userId") String userId) {
        Service service = new Service();
        try {
            if (StringUtils.isEmpty(userId)) {
                ErrorPOJO error = new ErrorPOJO();
                error.setCode(-101);
                error.setMessage("UserID is empty (or) null");
                service.setStatus("error");
                service.setError(error);
                return service;
            }
            userService.updateUserService(userServiceRequest, userId);
        } catch (Exception e) {
            ErrorPOJO error = new ErrorPOJO();
            error.setCode(-101);
            error.setMessage("Error occurred while update user profile service");
            service.setStatus("error");
            service.setError(error);
            return service;
        }
        return service;
    }

    /**
     * Method to get user build profile.
     *
     * @param userId
     */
    @RequestMapping(value = "/profile/{userId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Service getUserService(@PathVariable("userId") String userId) {
        Service service = new Service();
        UserServiceRequest userServiceRequest = null;
        try {
            if (StringUtils.isEmpty(userId)) {
                ErrorPOJO error = new ErrorPOJO();
                error.setCode(-101);
                error.setMessage("UserID is empty (or) null");
                service.setStatus("error");
                service.setError(error);
                return service;
            }
            userServiceRequest = userService.getUserService(userId);
        } catch (Exception e) {
            ErrorPOJO error = new ErrorPOJO();
            error.setCode(-101);
            error.setMessage("Error occurred while get user profile service");
            service.setStatus("error");
            service.setError(error);
            return service;
        }
        service.setPayload(userServiceRequest);
        return service;
    }

    /**
     * Method to delete any object inside user build profile.
     *
     * @param userServiceRequest
     * @param userId
     */
    @RequestMapping(value = "/profile/{userId}", method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Service deleteUserService(@RequestBody UserServiceRequest userServiceRequest, @PathVariable("userId") String userId) {
        Service service = new Service();
        try {
            if (StringUtils.isEmpty(userId)) {
                ErrorPOJO error = new ErrorPOJO();
                error.setCode(-101);
                error.setMessage("UserID is empty (or) null");
                service.setStatus("error");
                service.setError(error);
                return service;
            }
            userService.deleteUserService(userServiceRequest, userId);
        } catch (Exception e) {
            ErrorPOJO error = new ErrorPOJO();
            error.setCode(-101);
            error.setMessage("Error occurred while delete user profile service");
            service.setStatus("error");
            service.setError(error);
            return service;
        }
        return service;
    }

}
