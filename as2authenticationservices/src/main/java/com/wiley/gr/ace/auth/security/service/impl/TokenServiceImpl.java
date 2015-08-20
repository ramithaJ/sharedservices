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
package com.wiley.gr.ace.auth.security.service.impl;

import com.nimbusds.jose.JOSEException;
import com.wiley.gr.ace.auth.security.handler.TokenHandler;
import com.wiley.gr.ace.auth.security.model.TokenRequest;
import com.wiley.gr.ace.auth.security.service.TokenService;
import org.jose4j.jwt.consumer.InvalidJwtException;
import org.jose4j.lang.JoseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.text.ParseException;

/**
 * @author Virtusa
 */
public class TokenServiceImpl implements TokenService {

    /**
     * This field holds the value of LOGGER
     */
    private static final Logger LOGGER = LoggerFactory
            .getLogger(TokenServiceImpl.class);

    /**
     * This field holds the value of tokenHandler
     */
    @Autowired
    private TokenHandler tokenHandler;

    /**
     * Service method to generate token.
     *
     * @param tokenRequest
     * @return String
     * @throws JoseException
     */
    @Override
    public String generateToken(final TokenRequest tokenRequest) {
        return this.tokenHandler.hmacTokenGenerator(tokenRequest);
    }

    /**
     * Method to refresh the token.
     *
     * @param token
     * @return String
     */
    @Override
    public String refreshToken(final String token) throws IOException,
            JoseException, InvalidJwtException {
        return this.tokenHandler.hmacRefreshToken(token);
    }

    /**
     * Service method to validate token.
     *
     * @param token
     * @return boolean
     * @throws InvalidJwtException
     * @throws JoseException
     * @throws IOException
     */
    @Override
    public boolean validateToken(final String token)
            throws InvalidJwtException, JoseException, IOException {
        try {
            return this.tokenHandler.hmacTokenVerifies(token);
        } catch (ParseException | JOSEException e) {
            LOGGER.error("Exception validateToken", e);
        }
        return false;
    }
}