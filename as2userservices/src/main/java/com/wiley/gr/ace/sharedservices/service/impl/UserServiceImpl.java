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
import com.wiley.gr.ace.sharedservices.payload.LookupResponse;
import com.wiley.gr.ace.sharedservices.payload.Service;
import com.wiley.gr.ace.sharedservices.payload.UserServiceRequest;
import com.wiley.gr.ace.sharedservices.repositories.UserRepository;
import com.wiley.gr.ace.sharedservices.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author kkalyan
 */
@org.springframework.stereotype.Service
public class UserServiceImpl implements UserService {

    //User Repository Implementation Class
    @Autowired
    private UserRepository userRepository;


    /**
     * Service method to create user.
     *
     * @param userServiceRequest Request Java Mapping Object
     * @return Returns User Id
     * @throws SharedServiceException
     */
    public String createUserService(UserServiceRequest userServiceRequest) throws SharedServiceException {
        return userRepository.createUserRepository(userServiceRequest);
    }

    /**
     * Service method to update user profile.
     *
     * @param userServiceRequest Request Java Mapping Object
     * @param userId             Author Services Unique Id
     * @throws SharedServiceException
     */
    public UserServiceRequest updateUserProfileService(UserServiceRequest userServiceRequest, String userId) throws SharedServiceException {
        return userRepository.updateUserRepository(userServiceRequest, userId);
    }

    /**
     * Service method to get user profile.
     *
     * @param userId Author Services Unique Id
     * @return Returns Service response Object
     * @throws SharedServiceException
     */
    public UserServiceRequest getUserProfileService(String userId) throws SharedServiceException {
        return userRepository.getUserRepository(userId);
    }

    /**
     * Service method to delete user.
     *
     * @param userId Author Services Unique Id
     * @throws SharedServiceException
     */
    public void deleteUserService(String userId) throws SharedServiceException {
        userRepository.deleteUserRepository(userId);
    }

    /**
     * Service method to user lookup.
     *
     * @param firstName FirstName of the user
     * @param lastName  LastName of the user
     * @param emailId   Primary (or) Secondary Email Address of the user
     * @return Returns LookupResponse Object
     * @throws SharedServiceException
     */
    public LookupResponse userlookUpService(String firstName, String lastName, String emailId)
            throws SharedServiceException {
        return userRepository.userlookUpRepository(firstName, lastName, emailId);
    }

    /**
     * Service method to user search.
     *
     * @param email     Primary (or) Secondary Email Address of the user
     * @param firstName FirstName of the user
     * @param lastName  LastName of the user
     * @param orcidId   Orcid Id of the user
     * @return Returns Service response Object
     * @throws SharedServiceException
     */
    public Service searchUserService(String email, String firstName, String lastName, String orcidId) throws SharedServiceException {
        return userRepository.searchUserRepository(email, firstName, lastName, orcidId);
    }
}
