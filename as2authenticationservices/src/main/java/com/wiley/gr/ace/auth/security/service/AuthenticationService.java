/*******************************************************************************
 * Copyright (c) 2015 John Wiley & Sons, Inc. All rights reserved.
 *
 * All material contained herein is proprietary to John Wiley & Sons
 * and its third party suppliers, if any. The methods, techniques and
 * technical concepts contained herein are considered trade secrets
 * and confidential and may be protected by intellectual property laws.
 * Reproduction or distribution of this material, in whole or in part,
 * is strictly forbidden except by express prior written permission
 * of John Wiley & Sons.
 *******************************************************************************/
package com.wiley.gr.ace.auth.security.service;

import com.wiley.gr.ace.auth.security.model.Response;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by sripads on 5/16/2015.
 */
@Service(value = "authenticationService")
public interface AuthenticationService {

    Response authenticate(String username, String password, String authenticationType, String appKey);

    List<String> getRoles(String userId);
}
