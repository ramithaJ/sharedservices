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

import com.wiley.gr.ace.sharedservices.input.ErrorDescription;
import com.wiley.gr.ace.sharedservices.input.ErrorResponse;
import com.wiley.gr.ace.sharedservices.input.UserServiceRequest;
import com.wiley.gr.ace.sharedservices.persistence.entity.Users;
import com.wiley.gr.ace.sharedservices.profile.*;
import com.wiley.gr.ace.sharedservices.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author kkalyan
 */
@RestController
@RequestMapping("/user/profile")
public class UserServiceController {

    @Autowired
    private UserService userService;

    /**
     * Method to Create User Build Profile.
     *
     * @param userServiceRequest
     */
    @RequestMapping(value = "/", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity createUserService(@RequestBody UserServiceRequest userServiceRequest) {
        try {
            userService.createUserService(userServiceRequest);
        } catch (Exception e) {
            return new ResponseEntity(new ErrorResponse("201", "CREATE", new ErrorDescription(e.getMessage(), e.getMessage())), null, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity("CREATED", null, HttpStatus.OK);
    }


    /**
     * Method to update User Build Profile.
     *
     * @param userServiceRequest
     * @param userId
     */
    @RequestMapping(value = "/{userId}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<ErrorResponse> updateUserService(@RequestBody UserServiceRequest userServiceRequest, @PathVariable("userId") String userId) {
        try {
            userService.updateUserService(userServiceRequest, userId);
        } catch (Exception e) {
            return new ResponseEntity<>(new ErrorResponse("202", "UPDATE", new ErrorDescription(e.getMessage(), e.getMessage())), null, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity("UPDATED", null, HttpStatus.OK);
    }

    /**
     * Method to get user build profile.
     *
     * @param userId
     */
    @RequestMapping(value = "/{userId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity getUserService(@PathVariable("userId") String userId) {
        UserServiceRequest userServiceRequest = null;
        try {
            userServiceRequest = userService.getUserService(userId);
        } catch (Exception e) {
            return new ResponseEntity(new ErrorResponse("203", "GET", new ErrorDescription(e.getMessage(), e.getMessage())), null, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(userServiceRequest, null, HttpStatus.OK);
    }

    /**
     * Method to delete any object inside user build profile.
     *
     * @param userServiceRequest
     * @param userId
     */
    @RequestMapping(value = "/{userId}", method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity deleteUserService(@RequestBody UserServiceRequest userServiceRequest, @PathVariable("userId") String userId) {
        try {
            userService.deleteUserService(userServiceRequest, userId);
        } catch (Exception e) {
            return new ResponseEntity<>(new ErrorResponse("204", "DELETE", new ErrorDescription(e.getMessage(), e.getMessage())), null, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity("DELETED", null, HttpStatus.OK);
    }

}
