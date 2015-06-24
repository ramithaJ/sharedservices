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

import java.io.IOException;

import org.jose4j.jwt.consumer.InvalidJwtException;
import org.jose4j.lang.JoseException;

import com.wiley.gr.ace.auth.security.model.TokenRequest;

/**
 * @author Virtusa
 *
 */
public interface TokenService {

	/**
	 * This method generates the Token
	 *
	 * @param tokenRequest
	 * @return String
	 * @throws JoseException
	 *             String
	 */
	String generateToken(TokenRequest tokenRequest) throws JoseException;

	/**
	 * This method refresh the Token
	 *
	 * @param token
	 * @return String
	 * @throws IOException
	 * @throws JoseException
	 * @throws InvalidJwtException
	 *             String
	 */
	String refreshToken(String token) throws IOException, JoseException,
			InvalidJwtException;

	/**
	 * This method validates the Token
	 *
	 * @param token
	 * @return boolean
	 * @throws InvalidJwtException
	 * @throws JoseException
	 * @throws IOException
	 *             boolean
	 */
	boolean validateToken(String token) throws InvalidJwtException,
			JoseException, IOException;
}
