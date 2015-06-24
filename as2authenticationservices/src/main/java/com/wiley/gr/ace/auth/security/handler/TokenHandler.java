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
package com.wiley.gr.ace.auth.security.handler;

import java.io.IOException;
import java.security.PrivateKey;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.jose4j.jwk.RsaJsonWebKey;
import org.jose4j.jwk.RsaJwkGenerator;
import org.jose4j.jws.AlgorithmIdentifiers;
import org.jose4j.jws.JsonWebSignature;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.consumer.InvalidJwtException;
import org.jose4j.lang.JoseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wiley.gr.ace.auth.security.constants.CommonConstant;
import com.wiley.gr.ace.auth.security.model.TokenRequest;
import com.wiley.gr.ace.auth.security.service.AuthenticationService;

/**
 * @author Virtusa
 *
 */
public class TokenHandler {

	/**
	 * This field holds the value of LOGGER
	 */
	private static final Logger LOGGER = LoggerFactory
			.getLogger(TokenHandler.class);

	/**
	 * This field holds the value of applicationProperties
	 */
	@Autowired
	@Qualifier(value = "applicationProperties")
	private Properties applicationProperties;

	/**
	 * This field holds the value of authenticationService
	 */
	@Autowired(required = true)
	private AuthenticationService authenticationService;

	/**
	 * This field holds the value of keyMap
	 */
	private static HashMap<String, RsaJsonWebKey> keyMap;

	/**
	 * Method to create claims.
	 *
	 * @param tokenIssuer
	 * @param roles
	 * @param tokenSubject
	 * @param tokenAudience
	 * @param tokenExpirationTime
	 * @param claimKey
	 * @param userId
	 * @param minutesBefore
	 * @return JwtClaims
	 */
	private JwtClaims createClaims(final String tokenIssuer,
			final List<String> roles, final String tokenSubject,
			final String tokenAudience, final String tokenExpirationTime,
			final String claimKey, final String userId,
			final String minutesBefore) {
		TokenHandler.LOGGER.info("Creating Claims...");
		final JwtClaims claims = new JwtClaims();
		claims.setIssuer(tokenIssuer);
		claims.setAudience(tokenAudience);
		claims.setExpirationTimeMinutesInTheFuture(new Integer(
				tokenExpirationTime));
		claims.setGeneratedJwtId();
		claims.setIssuedAtToNow();
		claims.setNotBeforeMinutesInThePast(new Integer(minutesBefore));
		claims.setSubject(tokenSubject);
		claims.setClaim(claimKey, userId);
		claims.setStringListClaim(CommonConstant.ROLES, roles);
		TokenHandler.LOGGER.debug("Claims..." + claims.toJson());
		return claims;
	}

	/**
	 * Method to create token.
	 *
	 * @param tokenRequest
	 * @return String
	 * @throws JoseException
	 */
	public String createToken(final TokenRequest tokenRequest)
			throws JoseException {
		TokenHandler.LOGGER.info("Creating Token...");
		RsaJsonWebKey rsaJsonWebKey = null;

		final String tokenKey = tokenRequest.getAppKey().toLowerCase()
				+ CommonConstant.DOT;
		final String tokenSubject = this.applicationProperties
				.getProperty(tokenKey + CommonConstant.SUBJECT);
		final String tokenAudience = this.applicationProperties
				.getProperty(tokenKey + CommonConstant.AUDIENCE);
		final String tokenExpirationTime = this.applicationProperties
				.getProperty(tokenKey + CommonConstant.EXPIRATION_TIME);
		final String claimKey = this.applicationProperties.getProperty(tokenKey
				+ CommonConstant.CLAIM_KEY);
		final String minutesBefore = this.applicationProperties
				.getProperty(tokenKey + CommonConstant.TIME_BEFORE);

		if (null == TokenHandler.keyMap) {
			TokenHandler.LOGGER.info("Checking Key Map...");
			TokenHandler.keyMap = new HashMap<>();
		}

		if (null != TokenHandler.keyMap
				&& !TokenHandler.keyMap.containsKey(tokenRequest.getAppKey())) {
			TokenHandler.LOGGER.info("Generating Private and Public keys...");
			final String tokenKeyId = this.applicationProperties
					.getProperty(tokenKey + CommonConstant.KEY_ID);
			rsaJsonWebKey = RsaJwkGenerator.generateJwk(2048);
			rsaJsonWebKey.setKeyId(tokenKeyId);
			TokenHandler.keyMap.put(tokenRequest.getAppKey(), rsaJsonWebKey);
		}

		final JwtClaims claims = this.createClaims(tokenRequest.getAppKey(),
				tokenRequest.getRoles(), tokenSubject, tokenAudience,
				tokenExpirationTime, claimKey, tokenRequest.getUserId(),
				minutesBefore);
		final String token = this
				.jwsSignature(claims,
						TokenHandler.keyMap.get(tokenRequest.getAppKey())
								.getPrivateKey(),
						AlgorithmIdentifiers.RSA_USING_SHA256);

		return token;
	}

	/**
	 * Method to extract values from the token.
	 *
	 * @param token
	 * @param key
	 * @param jws
	 * @return String
	 * @throws IOException
	 */
	private String getTokenNodeValue(final String token, final String key,
			final JsonWebSignature jws) throws IOException {

		final ObjectMapper mapper = new ObjectMapper();
		JsonNode actualObj = null;
		actualObj = mapper.readTree(jws.getUnverifiedPayload());
		final JsonNode jsonNode = actualObj.get(key);

		return jsonNode.textValue();
	}

	/**
	 * Method to create JWS Signature
	 *
	 * @param claims
	 * @param key
	 * @param algorithm
	 * @return String
	 * @throws JoseException
	 */
	private String jwsSignature(final JwtClaims claims, final PrivateKey key,
			final String algorithm) throws JoseException {
		TokenHandler.LOGGER.info("Creating JWS Signature...");

		final JsonWebSignature jws = new JsonWebSignature();
		jws.setPayload(claims.toJson());
		jws.setKey(key);

		jws.setAlgorithmHeaderValue(algorithm);

		return jws.getCompactSerialization();
	}

	/**
	 * Method to refresh token.
	 *
	 * @param token
	 * @return
	 */
	public String refreshToken(final String token) throws JoseException,
			InvalidJwtException, IOException {
		TokenHandler.LOGGER.info("Refreshing Token...");
		final JsonWebSignature jws = new JsonWebSignature();
		jws.setCompactSerialization(token);
		final String issuer = this.getTokenNodeValue(token, CommonConstant.ISS,
				jws);
		final String userId = this.getTokenNodeValue(token,
				CommonConstant.USER_ID, jws);

		final TokenRequest request = new TokenRequest();
		request.setUserId(userId);
		request.setAppKey(issuer);
		request.setRoles(this.authenticationService.getRoles(userId));
		final String refreshToken = this.createToken(request);
		TokenHandler.LOGGER.debug("Refreshed Token..." + refreshToken);

		return refreshToken;
	}

	/**
	 * Validate the token
	 *
	 * @param token
	 * @return boolean
	 * @throws JoseException
	 */
	public boolean validateToken(final String token) throws JoseException,
			InvalidJwtException, IOException {
		TokenHandler.LOGGER.info("Validating Token...");
		final JsonWebSignature jws = new JsonWebSignature();
		jws.setCompactSerialization(token);
		final String issuer = this.getTokenNodeValue(token, CommonConstant.ISS,
				jws);
		TokenHandler.LOGGER.debug("Validating Token Issuer..." + issuer);
		if (null == TokenHandler.keyMap) {
			return false;
		}

		if (!TokenHandler.keyMap.containsKey(issuer)) {
			return false;
		}

		if (null != issuer) {
			jws.setKey(TokenHandler.keyMap.get(issuer).getPublicKey());
		}

		return jws.verifySignature();
	}
}
