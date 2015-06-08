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

    public void createUserService(UserServiceRequest userServiceRequest, String userId) throws Exception {
        userRepository.createUserRepository(userServiceRequest, userId);
    }

    public void updateUserService(UserServiceRequest userServiceRequest, String userId) throws Exception {
        userRepository.updateUserRepository(userServiceRequest, userId);
    }

    public void deleteUserService(UserServiceRequest userServiceRequest, String userId) throws Exception {
        userRepository.deleteUserRepository(userServiceRequest, userId);
    }

    public UserServiceRequest getUserService(String userId) throws Exception {
        return userRepository.getUserRepository(userId);
    }
}
