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
package com.wiley.gr.ace.auth.security.service.impl;

/**
 * Created by kkalyan on 5/18/2015.
 */

import com.wiley.gr.ace.auth.security.handler.TokenHandler;
import com.wiley.gr.ace.auth.security.model.TokenRequest;
import com.wiley.gr.ace.auth.security.service.TokenService;
import org.jose4j.jwt.consumer.InvalidJwtException;
import org.jose4j.lang.JoseException;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

public class TokenServiceImpl implements TokenService {

    @Autowired
    private TokenHandler tokenHandler;

    /**
     * Service method to generate token.
     *
     * @param tokenRequest
     * @return
     * @throws JoseException
     */
    @Override
    public String generateToken(TokenRequest tokenRequest) throws JoseException {
        return tokenHandler.createToken(tokenRequest);
    }

    /**
     * Service method to validate token.
     *
     * @param token
     * @return
     * @throws InvalidJwtException
     * @throws JoseException
     * @throws IOException
     */
    @Override
    public boolean validateToken(String token) throws InvalidJwtException, JoseException, IOException {
        return tokenHandler.validateToken(token);
    }

    /**
     * Method to refresh the token.
     *
     * @param token
     * @return
     */
    @Override
    public String refreshToken(String token) throws IOException, JoseException, InvalidJwtException {
        return tokenHandler.refreshToken(token);
    }
}
