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

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.naming.directory.Attributes;

import org.apache.commons.lang.StringUtils;
import org.jose4j.lang.JoseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.ldap.AuthenticationException;
import org.springframework.ldap.NamingException;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.ldap.filter.AndFilter;
import org.springframework.ldap.filter.EqualsFilter;
import org.springframework.web.client.RestTemplate;

import com.wiley.gr.ace.auth.security.constants.CommonConstant;
import com.wiley.gr.ace.auth.security.dao.LockedAccountDetails;
import com.wiley.gr.ace.auth.security.dao.UserLoginDAO;
import com.wiley.gr.ace.auth.security.model.AuthenticateRequest;
import com.wiley.gr.ace.auth.security.model.Response;
import com.wiley.gr.ace.auth.security.model.SecurityRequest;
import com.wiley.gr.ace.auth.security.model.SecurityResponse;
import com.wiley.gr.ace.auth.security.model.TokenRequest;
import com.wiley.gr.ace.auth.security.model.User;
import com.wiley.gr.ace.auth.security.service.AuthenticationService;
import com.wiley.gr.ace.auth.security.service.TokenService;
import com.wiley.gr.ace.auth.security.utils.StubInvoker;

/**
 * Created by sripads on 5/16/2015.
 */
public class AuthenticationServiceImpl implements AuthenticationService {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(AuthenticationServiceImpl.class);

	@Autowired(required = true)
	private LdapTemplate ldapTemplate;

	@Autowired(required = true)
	private TokenService tokenService;

	@Autowired(required = true)
	private LdapContextSource contextSource;

	@Autowired(required = true)
	private UserLoginDAO userLoginDao;

	@Value("${directory.service.filter}")
	private String directoryServicefilterPath;

	@Value("${directory.service.filter.match}")
	private String directoryServicefilterMatch;

	@Value("${directory.service.url}")
	private String directoyServiceUrl;

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

	@Value("${as.lock.attempts}")
	private int lockAttempts;

	@Value("${as.unlock.time}")
	private int unlockTime;

	@Value("${as.lock.url}")
	private String lockUser;

	@Value("${as.unlock.url}")
	private String unlockUser;

	@Value("${as.AuthenticationType}")
	private String authenticationType;

	/**
	 * Method to validate user before authenticate the user. It takes the
	 * AuthenticateRequest object as input.
	 * 
	 * @return Respose object
	 */
	@Override
	public Response userLogin(AuthenticateRequest request) {

		// for AD users no need to check Pre-conditions of login.
		if (authenticationType
				.equalsIgnoreCase(request.getAuthenticationType())) {
			return authenticate(request.getUserId(), request.getPassword(),
					request.getAuthenticationType(), request.getAppKey());
		}
		// get the user details from the table by using userId.
		LockedAccountDetails lockedAccountDetails = userLoginDao
				.userAccountDetails(request.getUserId());
		Response response = new Response();
		// if record is not there in table.
		if (null == lockedAccountDetails) {
			return processAuthenticatedUser(request);
		}
		// if record is there in table, check the time stamp and find whether
		// time is elapsed or not.
		Date loginAttemptTime = new Date(lockedAccountDetails
				.getLoginAttemptTime().getTime());
		// if time elapsed we will unlock the user, remove the record in table
		// and proceed for authentication.
		long minutes = TimeUnit.MILLISECONDS
				.toMinutes((new Date().getTime() - loginAttemptTime.getTime()));
		if (unlockTime < minutes) {
			SecurityRequest requestEntityClass = new SecurityRequest();
			requestEntityClass.setUserId(request.getUserId());
			if (null != lockedAccountDetails.getLockedTime()) {
				StubInvoker.restServiceInvoker(unlockUser, requestEntityClass,
						SecurityResponse.class);
			}
			userLoginDao.removeUser(request.getUserId());
			return processAuthenticatedUser(request);
		}
		// if time not elapsed we will check the login failure count.
		// if count is 3 then we will lock the user and update the time stamp in
		// table.
		// if not we will proceed for authentication. if authentication fail we
		// will update the count in table.
		if (lockAttempts == lockedAccountDetails.getInvalidLoginCount()) {
			// lock user esb service
			SecurityRequest requestEntityClass = new SecurityRequest();
			requestEntityClass.setUserId(request.getUserId());
			StubInvoker.restServiceInvoker(lockUser, requestEntityClass,
					SecurityResponse.class);
			// update the locked time in the table
			userLoginDao.updateTimeStamp(request.getUserId());
			response.setStatus(String.valueOf(Response.STATUS.LOCKED));

		} else {

			response = authenticate(request.getUserId(), request.getPassword(),
					request.getAuthenticationType(), request.getAppKey());
			if (null == response) {
				userLoginDao.updateUser(request.getUserId());
			}
		}
		return response;
	}

	/**
	 * Method to authenticate the user. It takes the AuthenticateRequest object
	 * as input.
	 * 
	 * @return Response object
	 */
	private Response processAuthenticatedUser(AuthenticateRequest request) {

		Response response = new Response();
		response = authenticate(request.getUserId(), request.getPassword(),
				request.getAuthenticationType(), request.getAppKey());
		if (null == response) {
			userLoginDao.insertUser(request.getUserId(), request.getAppKey());
		}
		return response;
	}

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
	public Response authenticate(String userId, String password,
			String authenticationType, String appKey) {

		Response response = null;
		String filterMatch = directoryServicefilterMatch;
		String filterPath = directoryServicefilterPath;

		// If Authentication Type is provided as AUTO. Do a domain check.
		if (StringUtils.isNotEmpty(authenticationType)
				&& authenticationType.equalsIgnoreCase(CommonConstant.AUTO)
				&& !userId.contains(CommonConstant.WILEY_DOMAIN)) {
			// Set ldap server setting.
			setContext(ldapServiceUrl, ldapUser, ldapPassword);
			filterMatch = ldapFilterMatch;
			filterPath = ldapFilter;
		}
		// Set the LDAP Server Configurations to authenticate against ldap
		// server.
		else if (StringUtils.isNotEmpty(authenticationType)
				&& authenticationType.equalsIgnoreCase(CommonConstant.LDAP)) {
			setContext(ldapServiceUrl, ldapUser, ldapPassword);
			filterMatch = ldapFilterMatch;
			filterPath = ldapFilter;
		} else {
			setContext(directoyServiceUrl, userId, password);
		}

		// Apply the filter.
		AndFilter filter = new AndFilter();
		filter.and(new EqualsFilter(filterMatch, userId));
		// Authenticate the user credentials.

		boolean isAuthenticated = false;
		try {
			isAuthenticated = ldapTemplate.authenticate(filterPath,
					filter.toString(), password);
		} catch (AuthenticationException e) {
			return response;
		}
		if (isAuthenticated) {
			// Call Roles Service and get the Roles
			List<String> roles = getRoles(userId);
			TokenRequest tokenRequest = new TokenRequest();
			tokenRequest.setAppKey(appKey);
			tokenRequest.setRoles(roles);
			tokenRequest.setUserId(userId);

			try {
				response = new Response(CommonConstant.STATUS_CODE,
						tokenService.generateToken(tokenRequest),
						CommonConstant.SUCCESS_STATUS);
				return response;
			} catch (JoseException e) {
				LOGGER.error("Exception Occurred while authenticating..", e);
				response = new Response(CommonConstant.FAIL_CODE,
						"Authentication Fail", CommonConstant.FAILURE_STATUS);
				return response;
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
		// TODO: Invoke Actual Role Service here
		// Invoke User Role Service and the get user role.
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders roleheaders = new HttpHeaders();
		roleheaders.set("JsonStub-User-Key",
				"3a552133-3e2a-4fc8-9931-2d214a177688");
		roleheaders.set("JsonStub-Project-Key",
				"975a72f8-ed51-43cc-af1a-4dc10c24a127");
		HttpEntity<String> roleentity = new HttpEntity<String>("parameters",
				roleheaders);
		ResponseEntity roleresponse = restTemplate.postForEntity(
				"http://jsonstub.com/getRole", roleentity, String.class);
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
		if (null != user) {
			contextSource.setUserDn(user);
		}
		if (null != password) {
			contextSource.setPassword(password);
		}
		contextSource.afterPropertiesSet();
		ldapTemplate.setContextSource(contextSource);
	}

	@Override
	public <T> User searchUser(String userId) {

		LOGGER.info("Inside searchUser method");
		setContext(directoyServiceUrl, null, null);
		String filterMatch = directoryServicefilterMatch;
		String filterPath = directoryServicefilterPath;

		AndFilter filter = new AndFilter();
		filter.and(new EqualsFilter(filterMatch, userId));
		List<T> list = new ArrayList<T>();
		list = ldapTemplate.search(filterPath, filter.encode(),
				new AttributesMapper() {
					public Object mapFromAttributes(Attributes attrs)
							throws NamingException {
						List list = new ArrayList();
						list.add(attrs.get("sn"));
						list.add(attrs.get("cn"));
						return list;
					}
				});
		if (null == list) {
			LOGGER.error("List is empty / no records found ");
			return new User();
		}
		String[] string = list.get(0).toString().split(",");
		String first = string[1].substring(5);
		String lastName = string[0].substring(5);
		String firstName = first.substring(0,
				first.length() - lastName.length() - 2);
		User user = new User();
		user.setFirstName(firstName);
		user.setLastName(lastName);
		return user;
	}

}
