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

import com.wiley.gr.ace.auth.security.model.TokenRequest;
import org.jose4j.jwt.consumer.InvalidJwtException;
import org.jose4j.lang.JoseException;

import java.io.IOException;

/**
 * Created by kkalyan on 5/18/2015.
 */
public interface TokenService {

    String generateToken(TokenRequest tokenRequest) throws JoseException;

    boolean validateToken(String token) throws InvalidJwtException, JoseException, IOException;

    String refreshToken(String token) throws IOException, JoseException, InvalidJwtException;
}
