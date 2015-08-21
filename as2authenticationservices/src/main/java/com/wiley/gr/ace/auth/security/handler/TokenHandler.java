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
package com.wiley.gr.ace.auth.security.handler;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.wiley.gr.ace.auth.security.constants.CommonConstant;
import com.wiley.gr.ace.auth.security.model.TokenRequest;
import com.wiley.gr.ace.auth.security.service.AuthenticationService;
import org.jose4j.jwk.RsaJsonWebKey;
import org.jose4j.jws.JsonWebSignature;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.consumer.InvalidJwtException;
import org.jose4j.lang.JoseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * @author Virtusa
 */
public class TokenHandler {

    /**
     * This field holds the value of LOGGER
     */
    private static final Logger LOGGER = LoggerFactory
            .getLogger(TokenHandler.class);
    /**
     * This field holds the value of keyMap
     */
    private static Map<String, RsaJsonWebKey> keyMap;
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
        final String refreshToken = this.hmacTokenGenerator(request);
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

    /**
     * Method to create hmac token.
     *
     * @param tokenRequest
     * @return
     */
    public String hmacTokenGenerator(final TokenRequest tokenRequest) {

        final String tokenKey = tokenRequest.getAppKey().toLowerCase()
                + CommonConstant.DOT;
        final String tokenSubject = this.applicationProperties
                .getProperty(tokenKey + CommonConstant.SUBJECT);
        final String claimKey = this.applicationProperties.getProperty(tokenKey
                + CommonConstant.CLAIM_KEY);
        String sharedSecretKey = this.sharedSecretKey(tokenRequest.getUserId());
        LOGGER.debug(sharedSecretKey);
        JWSSigner signer = new MACSigner(sharedSecretKey);

        JWTClaimsSet claimsSet = this.hmacClaimsSet(tokenRequest.getAppKey(),
                tokenRequest.getRoles(), tokenSubject, claimKey, tokenRequest.getUserId());

        SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256),
                claimsSet);
        try {
            signedJWT.sign(signer);
        } catch (JOSEException e) {
            LOGGER.error("Exception hmacTokenGenerator", e);
        }

        LOGGER.debug("HMAC Token     " + signedJWT.serialize());
        return signedJWT.serialize();
    }

    /**
     * @param appKey
     * @param roles
     * @param tokenSubject
     * @param claimKey
     * @param userId
     * @return
     */
    public JWTClaimsSet hmacClaimsSet(String appKey, List<String> roles,
                                      String tokenSubject, String claimKey, String userId) {

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet();
        // "AS Token"
        jwtClaimsSet.setSubject(tokenSubject);
        jwtClaimsSet.setAudience(roles);
        // "AS"
        jwtClaimsSet.setIssuer(appKey);
        jwtClaimsSet.setExpirationTime(new Date(
                new Date().getTime() + 60 * 1000));
        jwtClaimsSet
                .setNotBeforeTime(new Date(new Date().getTime() + 60 * 1000));
        // "userId", "8011047"
        jwtClaimsSet.setClaim(claimKey, userId);

        return jwtClaimsSet;
    }

    /**
     * @param hmacToken
     * @return
     * @throws ParseException
     * @throws JOSEException
     * @throws JoseException
     * @throws IOException
     */
    public boolean hmacTokenVerifies(String hmacToken) throws ParseException,
            JOSEException, JoseException, IOException {

        JsonWebSignature jws = new JsonWebSignature();
        jws.setCompactSerialization(hmacToken);
        final String userId = this.getTokenNodeValue(hmacToken,
                CommonConstant.USER_ID, jws);
        String sharedSecretKey = this.sharedSecretKey(userId);
        SignedJWT signedJWT = SignedJWT.parse(hmacToken);
        JWSVerifier verifier = new MACVerifier(sharedSecretKey);
        LOGGER.debug("verifier-------" + signedJWT.verify(verifier));
        if (signedJWT.verify(verifier)) {
            return true;
        }
        return false;
    }

    /**
     * @param userId
     * @return
     */
    private String sharedSecretKey(final String userId) {
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
            LOGGER.error("Exception sharedSecretKey", e);
        }
        return sharedSecretKey;
    }

    /**
     * @param token
     * @return
     * @throws JoseException
     * @throws InvalidJwtException
     * @throws IOException
     */
    public String hmacRefreshToken(final String token) throws JoseException,
            InvalidJwtException, IOException {

        TokenHandler.LOGGER.info("Refreshing Token...");
        JsonWebSignature jws = new JsonWebSignature();
        jws.setCompactSerialization(token);
        final String issuer = this.getTokenNodeValue(token, CommonConstant.ISS,
                jws);
        LOGGER.debug("issuer----------- " + issuer);
        final String userId = this.getTokenNodeValue(token,
                CommonConstant.USER_ID, jws);
        LOGGER.debug("userId----------- " + userId);
        final TokenRequest request = new TokenRequest();
        request.setUserId(userId);
        request.setAppKey(issuer);
        request.setRoles(this.authenticationService.getRoles(userId));
        final String refreshToken = this.hmacTokenGenerator(request);
        TokenHandler.LOGGER.debug("Refreshed Token..." + refreshToken);

        return refreshToken;
    }
}
