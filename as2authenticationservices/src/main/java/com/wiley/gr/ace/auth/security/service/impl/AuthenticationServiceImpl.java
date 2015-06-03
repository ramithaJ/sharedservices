/**
 * ****************************************************************************
 * Copyright (c) 2015 John Wiley & Sons, Inc. All rights reserved.
 * <p/>
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

import com.wiley.gr.ace.auth.security.constants.CommonConstant;
import com.wiley.gr.ace.auth.security.model.Response;
import com.wiley.gr.ace.auth.security.model.Response.STATUS;
import com.wiley.gr.ace.auth.security.model.TokenRequest;
import com.wiley.gr.ace.auth.security.service.AuthenticationService;
import com.wiley.gr.ace.auth.security.service.TokenService;
import org.apache.commons.lang.StringUtils;
import org.jose4j.lang.JoseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.ldap.filter.AndFilter;
import org.springframework.ldap.filter.EqualsFilter;
import org.springframework.web.client.RestTemplate;

import java.util.LinkedList;
import java.util.List;


/**
 * Created by sripads on 5/16/2015.
 */
public class AuthenticationServiceImpl implements AuthenticationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationServiceImpl.class);

    protected Response response = null;

    @Autowired(required = true)
    private LdapTemplate ldapTemplate;

    @Autowired(required = true)
    private TokenService tokenService;

    @Autowired(required = true)
    private LdapContextSource contextSource;


    @Value("${directory.service.filter}")
    private String directoryServicefilterPath;

    @Value("${directory.service.filter.match}")
    private String directoryServicefilterMatch;

    @Value("${directory.service.url}")
    private String directoyServiceUrl;

    @Value("${directory.service.user}")
    private String directoryUser;

    @Value("${directory.service.password}")
    private String directoryPassword;


    @Value("${directory.service.filter}")
    private String directoryFilter;


    @Value("${directory.service.filter.match}")
    private String directoryFilterMatch;


    @Value("${ldap.service.url}")
    private String ldapServiceUrl;

    @Value("${ldap.service.user}")
    private String ldapUser;

    @Value("${ldap.service.password}")
    private String ldapPassword;


    @Value("${ldap.service.filter}")
    private String ldapFilter;


    @Value("${ldap.service.filter.match}")
    private String ldapFilterMatch;

    /**
     * Method to authenticate the user.
     *
     * @param userId
     * @param password
     * @param authenticationType
     * @param appKey
     * @return
     */
    @Override
    public Response authenticate(String userId, String password, String authenticationType, String appKey) {

        String filterMatch = directoryServicefilterMatch;
        String filterPath = directoryServicefilterPath;

        //If Authentication Type is provided as AUTO. Do a domain check.
        if (StringUtils.isNotEmpty(authenticationType) && authenticationType.equalsIgnoreCase(CommonConstant.AUTO) && !userId.contains(CommonConstant.WILEY_DOMAIN)) {
            //Set ldap server setting.
            setContext(ldapServiceUrl, ldapUser, ldapPassword);
            filterMatch = ldapFilterMatch;
            filterPath = ldapFilter;
        }
        //Set the LDAP Server Configurations to authenticate against ldap server.
        else if (StringUtils.isNotEmpty(authenticationType) && authenticationType.equalsIgnoreCase(CommonConstant.LDAP)) {
            setContext(ldapServiceUrl, ldapUser, ldapPassword);
            filterMatch = ldapFilterMatch;
            filterPath = ldapFilter;
        } else {
            setContext(directoyServiceUrl, directoryUser, directoryPassword);
        }

        //Apply the filter.
        AndFilter filter = new AndFilter();
        filter.and(new EqualsFilter(filterMatch, userId));
        //Authenticate the user credentials.
        boolean isAuthenticated = ldapTemplate
                .authenticate(filterPath,
                        filter.toString(), password);
        if (isAuthenticated) {
            //Call Roles Service and get the Roles
            List<String> roles = getRoles(userId);
            TokenRequest tokenRequest = new TokenRequest();
            tokenRequest.setAppKey(appKey);
            tokenRequest.setRoles(roles);
            tokenRequest.setUserId(userId);

            try {
                response = new Response(CommonConstant.STATUS_CODE,
                        tokenService.generateToken(tokenRequest), CommonConstant.SUCCESS_STATUS);
            } catch (JoseException e) {
                LOGGER.error("Exception Occurred while authenticating..", e);
                response = new Response(CommonConstant.FAIL_CODE, "Authentication Fail",
                        CommonConstant.FAILURE_STATUS);
            }

        }
        return response;
    }

    /**
     * Method to get user roles.
     *
     * @param userId
     * @return
     */
    @Override
    public List<String> getRoles(String userId) {
        //TODO: Invoke Actual Role Service here
        //Invoke User Role Service and the get user role.
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders roleheaders = new HttpHeaders();
        roleheaders.set("JsonStub-User-Key", "3a552133-3e2a-4fc8-9931-2d214a177688");
        roleheaders.set("JsonStub-Project-Key",
                "975a72f8-ed51-43cc-af1a-4dc10c24a127");
        HttpEntity<String> roleentity = new HttpEntity<String>("parameters",
                roleheaders);
        ResponseEntity roleresponse = restTemplate.postForEntity("http://jsonstub.com/getRole", roleentity, String.class);
        return new LinkedList<>();
    }


    /**
     * Method to set the context object.
     *
     * @param serviceUrl
     * @param user
     * @param password
     */
    private void setContext(String serviceUrl, String user, String password) {
        contextSource.setUrl(serviceUrl);
        contextSource.setUserDn(user);
        contextSource.setPassword(password);
        contextSource.afterPropertiesSet();
        ldapTemplate.setContextSource(contextSource);
    }
}
