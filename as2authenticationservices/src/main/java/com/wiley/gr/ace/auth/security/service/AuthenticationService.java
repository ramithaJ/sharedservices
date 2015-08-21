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
package com.wiley.gr.ace.auth.security.service;

import com.wiley.gr.ace.auth.security.model.AuthenticateRequest;
import com.wiley.gr.ace.auth.security.model.Response;
import com.wiley.gr.ace.auth.security.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Virtusa
 *
 */
@Service(value = "authenticationService")
public interface AuthenticationService {

    /**
     * This method authenticates the user
     *
     * @param username
     * @param password
     * @param authenticationType
     * @param appKey
     * @return Response
     */
    Response authenticate(String username, String password,
                          String authenticationType, String appKey);

    /**
     * This method gets the list of Roles
     *
     * @param userId
     * @return List<String>
     */
    List<String> getRoles(String userId);

    /**
     * This method searchs User
     *
     * @param userId
     * @return User
     */
    <T> User searchUser(String userId);

    /**
     * This method checks user Login
     *
     * @param request
     * @return Response
     */
    Response userLogin(AuthenticateRequest request);
}