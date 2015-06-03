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

import com.wiley.gr.ace.sharedservices.input.UserServiceRequest;
import com.wiley.gr.ace.sharedservices.persistence.entity.Users;

/**
 * @author kkalyan
 */
public interface UserRepository {

    void createUserRepository(UserServiceRequest userServiceRequest) throws Exception;

    void updateUserRepository(UserServiceRequest userServiceRequest, String userId) throws Exception;

    void deleteUserRepository(UserServiceRequest userServiceRequest, String userId) throws Exception;

    UserServiceRequest getUserRepository(String userId) throws Exception;

}
