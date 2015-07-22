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
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.SecureRandom;
import java.text.ParseException;
import java.util.Date;
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
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
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

	public String hmacTokenGenerator(final TokenRequest tokenRequest) {

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

		/*
		 * String tokenSubject = "AS Token"; String tokenAudience = ""; String
		 * tokenExpirationTime = ""; String claimKey = "userId"; String
		 * minutesBefore = "";
		 */
		// here instead of generating complex value we can use any Sting value
		// like AuthorServices.
		String sharedSecretKey = this.sharedSecretKey(tokenRequest.getUserId());
		System.err.println(sharedSecretKey);
		JWSSigner signer = new MACSigner(sharedSecretKey);

		JWTClaimsSet claimsSet = this.hmacClaimsSet(tokenRequest.getAppKey(),
				tokenRequest.getRoles(), tokenSubject, tokenAudience,
				tokenExpirationTime, claimKey, tokenRequest.getUserId(),
				minutesBefore);

		SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256),
				claimsSet);
		try {
			signedJWT.sign(signer);
		} catch (JOSEException e) {
			e.printStackTrace();
		}

		System.err.println("HMAC Token     " + signedJWT.serialize());
		return signedJWT.serialize();
	}

	public JWTClaimsSet hmacClaimsSet(String appKey, List<String> roles,
			String tokenSubject, String tokenAudience,
			String tokenExpirationTime, String claimKey, String userId,
			String minutesBefore) {

		JWTClaimsSet jwtClaimsSet = new JWTClaimsSet();
		jwtClaimsSet.setSubject(tokenSubject);// "AS Token"
		jwtClaimsSet.setAudience(roles);
		jwtClaimsSet.setIssuer(appKey);// "AS"
		jwtClaimsSet.setExpirationTime(new Date(
				new Date().getTime() + 60 * 1000));
		jwtClaimsSet
				.setNotBeforeTime(new Date(new Date().getTime() + 60 * 1000));
		jwtClaimsSet.setClaim(claimKey, userId);// "userId", "8011047"
		return jwtClaimsSet;
	}

	public boolean hmacTokenVerifies(String hmacToken) throws ParseException,
			JOSEException, JoseException, IOException {

		JsonWebSignature jws = new JsonWebSignature();
		jws.setCompactSerialization(hmacToken);
		final String userId = this.getTokenNodeValue(hmacToken,
				CommonConstant.USER_ID, jws);
		System.err.println("userId " + userId);
		String SharedSeceretKey = this.sharedSecretKey(userId);
		SignedJWT signedJWT = SignedJWT.parse(hmacToken);
		JWSVerifier verifier = new MACVerifier(SharedSeceretKey);
		return signedJWT.verify(verifier);
	}

	/*public static void main(String[] args) throws JoseException, IOException {
		TokenHandler a = new TokenHandler();
		try {
			System.err
					.println(a
							.hmacTokenVerifies("eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJEQUFTIiwic3ViIjoiREFBUyBUb2tlbiIsImF1ZCI6W10sIm5iZiI6MTQzNzQ3NTQ1OSwiZXhwIjoxNDM3NDc1NDU5LCJ1c2VyaWQiOiJSYW1pdGhhLnN1QGdtYWlsLmNvbSJ9.8pD-bX46_IxDTGRgHwM9FJ87PoFCZuzIJt3kQ2ItyfY"));

		} catch (ParseException e) {
			e.printStackTrace();
		} catch (JOSEException e) {
			e.printStackTrace();
		}
	}
*/
	private String sharedSecretKey(final String userId) {

		/*
		 * SecureRandom random = new SecureRandom(); byte[] sharedSecret = new
		 * byte[32]; random.nextBytes(sharedSecret); String salt =
		 * sharedSecret.toString();
		 */
		String salt = "[B@5c5f0b5f";
		String sharedSecretKey = null;
		try {
			MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
			messageDigest.update(salt.getBytes());
			byte[] bytes = messageDigest.digest(userId.getBytes());
			StringBuilder stringBuilder = new StringBuilder();
			for (int i = 0; i < bytes.length; i++) {
				stringBuilder.append(Integer.toString(
						(bytes[i] & 0xff) + 0x100, 16).substring(1));
			}
			sharedSecretKey = stringBuilder.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		// System.err.println(sharedSecretKey);
		return sharedSecretKey;
	}

	/*
	 * public static void main(String[] args) { TokenHandler a = new
	 * TokenHandler(); a.sharedSecretKey("Ramitha.su@gmail.com"); }
	 */

	public String hmacRefreshToken(final String token) throws JoseException,
			InvalidJwtException, IOException {

		TokenHandler.LOGGER.info("Refreshing Token...");
		JsonWebSignature jws = new JsonWebSignature();
		jws.setCompactSerialization(token);
		final String issuer = this.getTokenNodeValue(token, CommonConstant.ISS,
				jws);
		System.err.println("issuer " + issuer);
		final String userId = this.getTokenNodeValue(token,
				CommonConstant.USER_ID, jws);
		System.err.println("userId " + userId);
		final TokenRequest request = new TokenRequest();
		request.setUserId(userId);
		request.setAppKey(issuer);
		request.setRoles(this.authenticationService.getRoles(userId));
		final String refreshToken = this.createToken(request);
		TokenHandler.LOGGER.debug("Refreshed Token..." + refreshToken);

		return refreshToken;
	}

	/*
	 * public static void main(String[] args) {
	 * 
	 * TokenHandler a = new TokenHandler(); try { a.hmacRefreshToken(
	 * "eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJEQUFTIiwic3ViIjoiREFBUyBUb2tlbiIsImF1ZCI6W10sIm5iZiI6MTQzNzQ1ODUyMSwiZXhwIjoxNDM3NDU4NTIxLCJ1c2VyaWQiOiJSYW1pdGhhLnN1QGdtYWlsLmNvbSJ9.r_dZFaCr-R8nAChGzkZk_4C9kQazShFyC9qmIJY-b6c"
	 * ); } catch (JoseException | InvalidJwtException | IOException e) {
	 * e.printStackTrace(); } }
	 */

}
