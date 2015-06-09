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
package com.wiley.gr.ace.sharedservices.service;

import com.wiley.gr.ace.sharedservices.exceptions.SharedServiceException;
import com.wiley.gr.ace.sharedservices.payload.UserServiceRequest;

/**
 * @author kkalyan
 */
public interface UserService {

    void createUserService(UserServiceRequest userServiceRequest, String userId) throws SharedServiceException;

    void updateUserService(UserServiceRequest userServiceRequest, String userId) throws SharedServiceException;

    void deleteUserService(UserServiceRequest userServiceRequest, String userId) throws SharedServiceException;

    UserServiceRequest getUserService(String userId) throws SharedServiceException;

}
