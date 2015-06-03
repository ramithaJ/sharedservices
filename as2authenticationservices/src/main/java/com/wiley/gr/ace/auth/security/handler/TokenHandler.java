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

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wiley.gr.ace.auth.security.constants.CommonConstant;
import com.wiley.gr.ace.auth.security.model.TokenRequest;
import com.wiley.gr.ace.auth.security.service.AuthenticationService;
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

import java.io.IOException;
import java.security.PrivateKey;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

/**
 * Created by kkalyan on 5/20/2015.
 */
public class TokenHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(TokenHandler.class);

    @Autowired
    @Qualifier(value = "applicationProperties")
    private Properties applicationProperties;

    @Autowired(required = true)
    private AuthenticationService authenticationService;


    private static HashMap<String, RsaJsonWebKey> keyMap;

    /**
     * Method to create token.
     *
     * @param tokenRequest
     * @return
     * @throws JoseException
     */
    public String createToken(TokenRequest tokenRequest) throws JoseException {
        LOGGER.info("Creating Token...");
        RsaJsonWebKey rsaJsonWebKey = null;

        String tokenKey = tokenRequest.getAppKey().toLowerCase() + CommonConstant.DOT;
        String tokenSubject = applicationProperties.getProperty(tokenKey + CommonConstant.SUBJECT);
        String tokenAudience = applicationProperties.getProperty(tokenKey + CommonConstant.AUDIENCE);
        String tokenExpirationTime = applicationProperties.getProperty(tokenKey + CommonConstant.EXPIRATION_TIME);
        String claimKey = applicationProperties.getProperty(tokenKey + CommonConstant.CLAIM_KEY);
        String minutesBefore = applicationProperties.getProperty(tokenKey + CommonConstant.TIME_BEFORE);

        //Check whether keyMap is Initialized or not. If not, initialize it.
        if (null == keyMap) {
            LOGGER.info("Checking Key Map...");
            keyMap = new HashMap<>();
        }

        //Check whether the private and public keys are generated for the application.
        if (null != keyMap && !keyMap.containsKey(tokenRequest.getAppKey())) {
            LOGGER.info("Generating Private and Public keys...");
            //Get the secret key of the application to generate.
            String tokenKeyId = applicationProperties.getProperty(tokenKey + CommonConstant.KEY_ID);
            //Create RSA Web Key
            rsaJsonWebKey = RsaJwkGenerator.generateJwk(2048);
            rsaJsonWebKey.setKeyId(tokenKeyId);
            keyMap.put(tokenRequest.getAppKey(), rsaJsonWebKey);
        }

        // Create Claims
        JwtClaims claims = createClaims(tokenRequest.getAppKey(), tokenRequest.getRoles(), tokenSubject, tokenAudience, tokenExpirationTime, claimKey, tokenRequest.getUserId(), minutesBefore);
        // Create JWS Signature and Sign tit.
        String token = jwsSignature(claims, keyMap.get(tokenRequest.getAppKey()).getPrivateKey(), AlgorithmIdentifiers.RSA_USING_SHA256);
        //Return the Token generated.
        return token;
    }

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
     * @return
     */
    private JwtClaims createClaims(String tokenIssuer, List<String> roles, String tokenSubject, String tokenAudience, String tokenExpirationTime, String claimKey, String userId, String minutesBefore) {
        LOGGER.info("Creating Claims...");
        // Create the Claims, which will be the content of the JWT
        JwtClaims claims = new JwtClaims();
        claims.setIssuer(tokenIssuer);  // who creates the token and signs it
        claims.setAudience(tokenAudience); // to whom the token is intended to be sent
        claims.setExpirationTimeMinutesInTheFuture(new Integer(tokenExpirationTime)); // time when the token will expire (10 minutes from now)
        claims.setGeneratedJwtId(); // a unique identifier for the token
        claims.setIssuedAtToNow();  // when the token was issued/created (now)
        claims.setNotBeforeMinutesInThePast(new Integer(minutesBefore)); // time before which the token is not yet valid (2 minutes ago)
        claims.setSubject(tokenSubject); // the subject/principal is whom the token is about
        claims.setClaim(claimKey, userId); // additional claims/attributes about the subject can be added
        claims.setStringListClaim(CommonConstant.ROLES, roles); // multi-valued claims work too and will end up as a JSON array
        LOGGER.debug("Claims..." + claims.toJson());
        return claims;
    }

    /**
     * Method to create JWS Signature
     *
     * @param claims
     * @param key
     * @param algorithm
     * @return
     * @throws JoseException
     */
    private String jwsSignature(JwtClaims claims, PrivateKey key, String algorithm) throws JoseException {
        LOGGER.info("Creating JWS Signature...");
        // A JWT is a JWS and/or a JWE with JSON claims as the payload.
        // Create a JsonWebSignature object.
        JsonWebSignature jws = new JsonWebSignature();
        // The payload of the JWS is JSON content of the JWT Claims
        jws.setPayload(claims.toJson());
        // The JWT is signed using the private key
        jws.setKey(key);

        //Set the header Key Value
        //jws.setKeyIdHeaderValue(keyId);

        // Set the signature algorithm on the JWT/JWS that will integrity protect the claims
        jws.setAlgorithmHeaderValue(algorithm);

        return jws.getCompactSerialization();
    }

    /**
     * Validate the token
     *
     * @param token
     * @return
     * @throws JoseException
     */
    public boolean validateToken(String token) throws JoseException, InvalidJwtException, IOException {
        LOGGER.info("Validating Token...");
        // Create a new JsonWebSignature
        JsonWebSignature jws = new JsonWebSignature();
        // Set the compact serialization on the JWS
        jws.setCompactSerialization(token);
        String issuer = getTokenNodeValue(token, CommonConstant.ISS, jws);
        LOGGER.debug("Validating Token Issuer..."+issuer);
        if (null == keyMap) {
            return false;
        }
        if (!keyMap.containsKey(issuer)) {
            return false;
        }

        if (null != issuer) {
            jws.setKey((keyMap.get(issuer)).getPublicKey());
        }
        // Check the signature
        return jws.verifySignature();
    }

    /**
     * Method to refresh token.
     *
     * @param token
     * @return
     */
    public String refreshToken(String token) throws JoseException, InvalidJwtException, IOException {
        LOGGER.info("Refreshing Token...");
        // Create a new JsonWebSignature
        JsonWebSignature jws = new JsonWebSignature();
        // Set the compact serialization on the JWS
        jws.setCompactSerialization(token);
        String issuer = getTokenNodeValue(token, CommonConstant.ISS, jws);
        String userId = getTokenNodeValue(token, CommonConstant.USER_ID, jws);

        //Create TokenRequest Object
        TokenRequest request = new TokenRequest();
        request.setUserId(userId);
        request.setAppKey(issuer);
        request.setRoles(authenticationService.getRoles(userId));
        String refreshToken = createToken(request);
        LOGGER.debug("Refreshed Token..."+refreshToken);
        return refreshToken;
    }

    /**
     * Method to extract values from the token.
     *
     * @param token
     * @param key
     * @param jws
     * @return
     * @throws IOException
     */
    private String getTokenNodeValue(String token, String key, JsonWebSignature jws) throws IOException {
        //Parse the Payload into JSON Object and get the application key
        ObjectMapper mapper = new ObjectMapper();
        JsonNode actualObj = null;
        actualObj = mapper.readTree(jws.getUnverifiedPayload());
        // When
        JsonNode jsonNode = actualObj.get(key);
        return jsonNode.textValue();
    }
}
