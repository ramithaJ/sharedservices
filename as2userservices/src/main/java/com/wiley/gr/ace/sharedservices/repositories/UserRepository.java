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
package com.wiley.gr.ace.sharedservices.repositories;

import com.wiley.gr.ace.sharedservices.exceptions.SharedServiceException;
import com.wiley.gr.ace.sharedservices.payload.LookupResponse;
import com.wiley.gr.ace.sharedservices.payload.Service;
import com.wiley.gr.ace.sharedservices.payload.UserServiceRequest;

/**
 * @author kkalyan
 */
public interface UserRepository {

    String createUserRepository(UserServiceRequest userServiceRequest) throws SharedServiceException;

    UserServiceRequest getUserRepository(String userId) throws SharedServiceException;

    void deleteUserRepository(String userId) throws SharedServiceException;

    UserServiceRequest updateUserRepository(UserServiceRequest userServiceRequest, String userId) throws SharedServiceException;

    LookupResponse userlookUpRepository(String firstName, String lastName, String emailId) throws SharedServiceException;

    Service searchUserRepository(String email, String firstName, String lastName, String orcidId) throws SharedServiceException;

}
