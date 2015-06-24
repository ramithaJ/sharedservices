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
package com.wiley.gr.ace.sharedservices.service.impl;

import com.wiley.gr.ace.sharedservices.exceptions.SharedServiceException;
import com.wiley.gr.ace.sharedservices.payload.UserServiceRequest;
import com.wiley.gr.ace.sharedservices.repositories.UserRepository;
import com.wiley.gr.ace.sharedservices.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author kkalyan
 */
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;


    /**
     * Service method to create user.
     *
     * @param userServiceRequest
     * @return
     * @throws SharedServiceException
     */
    public String createUserService(UserServiceRequest userServiceRequest) throws SharedServiceException {
        return userRepository.createUserRepository(userServiceRequest);
    }

    /**
     * Service method to update user profile.
     *
     * @param userServiceRequest
     * @param userId
     * @throws SharedServiceException
     */
    public void updateUserProfileService(UserServiceRequest userServiceRequest, String userId) throws SharedServiceException {
        userRepository.updateUserRepository(userServiceRequest, userId);
    }

    /**
     * Service method to get user profile.
     *
     * @param userId
     * @return
     * @throws SharedServiceException
     */
    public UserServiceRequest getUserProfileService(String userId) throws SharedServiceException {
        return userRepository.getUserRepository(userId);
    }

    /**
     * Service method to delete user
     * @param userId
     * @throws SharedServiceException
     */
    public void deleteUserService(String userId) throws SharedServiceException {
        userRepository.deleteUserRepository(userId);
    }
}
