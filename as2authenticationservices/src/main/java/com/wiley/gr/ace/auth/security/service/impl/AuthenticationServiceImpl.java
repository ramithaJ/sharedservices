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

import com.wiley.gr.ace.auth.security.constants.CommonConstant;
import com.wiley.gr.ace.auth.security.dao.LockedAccountDetails;
import com.wiley.gr.ace.auth.security.dao.UserLoginDAO;
import com.wiley.gr.ace.auth.security.model.*;
import com.wiley.gr.ace.auth.security.service.AuthenticationService;
import com.wiley.gr.ace.auth.security.service.TokenService;
import com.wiley.gr.ace.auth.security.utils.ESBServiceInvoker;
import org.apache.commons.lang.StringUtils;
import org.jose4j.lang.JoseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.ldap.AuthenticationException;
import org.springframework.ldap.NameNotFoundException;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.ldap.filter.AndFilter;
import org.springframework.ldap.filter.EqualsFilter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author Virtusa
 */
public class AuthenticationServiceImpl implements AuthenticationService {

	/**
	 * This field holds the value of LOGGER
	 */
	private static final Logger LOGGER = LoggerFactory
			.getLogger(AuthenticationServiceImpl.class);

	/**
	 * This field holds the value of ldapTemplate
	 */
	@Autowired(required = true)
	private LdapTemplate ldapTemplate;

	/**
	 * This field holds the value of tokenService
	 */
	@Autowired(required = true)
	private TokenService tokenService;

	/**
	 * This field holds the value of contextSource
	 */
	@Autowired(required = true)
	private LdapContextSource contextSource;

	/**
	 * This field holds the value of userLoginDao
	 */
	@Autowired(required = true)
	private UserLoginDAO userLoginDao;

	/**
	 * This field holds the value of directoryServicefilterPath
	 */
	@Value("${directory.service.filter}")
	private String directoryServicefilterPath;

	/**
	 * This field holds the value of directoryServicefilterMatch
	 */
	@Value("${directory.service.filter.match}")
	private String directoryServicefilterMatch;

	/**
	 * This field holds the value of directoyServiceUrl
	 */
	@Value("${directory.service.url}")
	private String directoyServiceUrl;

	/**
	 * This field holds the value of ldapServiceUrl
	 */
	@Value("${ldap.service.url}")
	private String ldapServiceUrl;

	/**
	 * This field holds the value of ldapUser
	 */
	@Value("${ldap.service.user}")
	private String ldapUser;

	/**
	 * This field holds the value of ldapPassword
	 */
	@Value("${ldap.service.password}")
	private String ldapPassword;

	/**
	 * This field holds the value of ldapFilter
	 */
	@Value("${ldap.service.filter}")
	private String ldapFilter;

	/**
	 * This field holds the value of ldapFilterMatch
	 */
	@Value("${ldap.service.filter.match}")
	private String ldapFilterMatch;

	/**
	 * This field holds the value of lockAttempts
	 */
	@Value("${as.lock.attempts}")
	private int lockAttempts;

	/**
	 * This field holds the value of unlockTime
	 */
	@Value("${as.unlock.time}")
	private int unlockTime;

	/**
	 * This field holds the value of lockUser
	 */
	@Value("${as.lock.url}")
	private String lockUserurl;

	/**
	 * This field holds the value of unlockUser
	 */
	@Value("${as.unlock.url}")
	private String unlockUserurl;

	/**
	 * This field holds the value of authenticationType
	 */
	@Value("${as.AuthenticationType}")
	private String authenticationType;

	/**
	 * This field holds the value of verifyEmailurl.
	 */
	@Value("${as.verifyemail.url}")
	private String verifyEmailurl;
	
	@Value("${as.almsearch.url}")
	private String almSearchUrl;

	/**
	 * Method to authenticate the user.
	 *
	 * @param userId
	 * @param password
	 * @param authenticationType
	 * @param appKey
	 * @return Response
	 */
	@Override
	public Response authenticate(final String userId, final String password,
			final String authenticationType, final String appKey) {

		Response response = null;
		String filterPath = null;
		AndFilter filter = new AndFilter();

		// If Authentication Type is provided as AUTO. Do a domain check.
		if (StringUtils.isNotEmpty(authenticationType)
				&& authenticationType.equalsIgnoreCase(CommonConstant.AUTO)
				&& !userId.contains(CommonConstant.WILEY_DOMAIN)) {
			// Set ldap server setting.
			this.setContext(this.ldapServiceUrl, this.ldapUser,
					this.ldapPassword);
			// Apply the filter.
			filter.and(new EqualsFilter(this.ldapFilterMatch, userId));
			filterPath = this.ldapFilter;
		} else if (StringUtils.isNotEmpty(authenticationType)
				&& authenticationType.equalsIgnoreCase(CommonConstant.LDAP)) {
			this.setContext(this.ldapServiceUrl, this.ldapUser,
					this.ldapPassword);
			// Set the LDAP Server Configurations to authenticate against ldap
			// server.
			// Apply the filter.
			filter.and(new EqualsFilter(this.ldapFilterMatch, userId));
			filterPath = this.ldapFilter;
		} else {
			this.setContext(this.directoyServiceUrl, userId, password);
			// Apply the filter.
			filter.and(new EqualsFilter(this.directoryServicefilterMatch,
					userId));
			filterPath = this.directoryServicefilterPath;
		}

		// Authenticate the user credentials.

		boolean isAuthenticated = false;
		try {
			isAuthenticated = this.ldapTemplate.authenticate(filterPath,
					filter.toString(), password);
		} catch (final AuthenticationException e) {
			LOGGER.error("Error Occured during authentication", e);
			return response;
		}
		if (isAuthenticated) {
			return setResponse(userId, appKey);
		}

		return response;
	}

	/**
	 * Method to set Authenticated response.
	 *
	 * @param userId
	 * @param appKey
	 * @return
	 */
	private Response setResponse(String userId, String appKey) {
		Response response = null;
		// Call Roles Service and get the Roles
		final List<String> roles = this.getRoles(userId);
		final TokenRequest tokenRequest = new TokenRequest();
		tokenRequest.setAppKey(appKey);
		tokenRequest.setRoles(roles);
		tokenRequest.setUserId(userId);

		try {
			response = new Response(CommonConstant.STATUS_CODE,
					this.tokenService.generateToken(tokenRequest),
					CommonConstant.SUCCESS_STATUS);
			return response;
		} catch (final JoseException e) {
			LOGGER.error("Exception Occurred while authenticating..", e);
			response = new Response(CommonConstant.FAIL_CODE,
					"Authentication Fail", CommonConstant.FAILURE_STATUS);
			return response;
		}
	}

	/**
	 * Method to get user roles.
	 *
	 * @param userId
	 * @return List<String>
	 */
	@Override
	public List<String> getRoles(final String userId) {
		// Invoke User Role Service and the get user role.
		final RestTemplate restTemplate = new RestTemplate();
		final HttpHeaders roleheaders = new HttpHeaders();
		roleheaders.set("JsonStub-User-Key",
				"3a552133-3e2a-4fc8-9931-2d214a177688");
		roleheaders.set("JsonStub-Project-Key",
				"975a72f8-ed51-43cc-af1a-4dc10c24a127");
		final HttpEntity<String> roleentity = new HttpEntity<String>(
				"parameters", roleheaders);
		restTemplate.postForEntity("http://jsonstub.com/getRole", roleentity,
				String.class);
		return new LinkedList<>();
	}

	/**
	 * Method to authenticate the user. It takes the AuthenticateRequest object
	 * as input.
	 *
	 * @param request
	 * @return Response
	 */
	private Response processAuthenticatedUser(final AuthenticateRequest request) {

		boolean status = false;
		Response response = this.authenticate(request.getUserId(),
				request.getPassword(), request.getAuthenticationType(),
				request.getAppKey());
		if (null == response) {
//			status = ESBServiceInvoker.verifyEmail(verifyEmailurl,
//					request.getUserId());
			String ecid = ESBServiceInvoker.searchUserInALM(almSearchUrl+request.getUserId());
			if (ecid != null) {
				this.userLoginDao.insertUser(request.getUserId(),
						request.getAppKey());
				Response responseStatus = new Response();
				responseStatus.setStatus(String.valueOf(Response.STATUS.FAILURE));
				return responseStatus;
				
			} else {
				Response responseStatus = new Response();
				responseStatus.setStatus(String.valueOf(Response.STATUS.UNREGISTERED));
				return responseStatus;
			}
		}
		return response;
	}

	/**
	 * This method searches User
	 *
	 * @param userId
	 * @return <T>
	 */
	@Override
	public <T> User searchUser(final String userId)
			throws NameNotFoundException, IndexOutOfBoundsException,
			AuthenticationException {

		LOGGER.info("Inside searchUser method");
		this.setContext(this.directoyServiceUrl, null, null);
		final String filterMatch = this.directoryServicefilterMatch;
		final String filterPath = this.directoryServicefilterPath;

		final AndFilter filter = new AndFilter();
		filter.and(new EqualsFilter(filterMatch, userId));
		List<T> list = new ArrayList<T>();
		User user = null;
		list = this.ldapTemplate.search(filterPath, filter.encode(),
				(AttributesMapper) attrs -> {
					final List list1 = new ArrayList();
					list1.add(attrs.get("sn"));
					list1.add(attrs.get("cn"));
					return list1;
				});
		final String[] string = list.get(0).toString().split(",");
		final String first = string[1].substring(5);
		final String lastName = string[0].substring(5);
		final String firstName = first.substring(0,
				first.length() - lastName.length() - 2);
		user = new User();
		user.setFirstName(firstName);
		user.setLastName(lastName);
		return user;
	}

	/**
	 * Method to set the context object.
	 *
	 * @param serviceUrl
	 * @param user
	 * @param password
	 */
	private void setContext(final String serviceUrl, final String user,
			final String password) {
		this.contextSource.setUrl(serviceUrl);
		if (null != user) {
			this.contextSource.setUserDn(user);
		}
		if (null != password) {
			this.contextSource.setPassword(password);
		}
		this.contextSource.afterPropertiesSet();
		this.ldapTemplate.setContextSource(this.contextSource);
	}

	/**
	 * Method to validate user before authenticate the user. It takes the
	 * AuthenticateRequest object as input.
	 *
	 * @param request
	 * @return Response
	 */
	@Override
	public Response userLogin(final AuthenticateRequest request) {

		// for AD users no need to check Pre-conditions of login.
		if (this.authenticationType.equalsIgnoreCase(request
				.getAuthenticationType())) {
			return activeDirectoryUsers(request.getUserId(),
					request.getPassword(), request.getAuthenticationType(),
					request.getAppKey());
		}
		// get the user details from the table by using userId.
		final LockedAccountDetails lockedAccountDetails = this.userLoginDao
				.userAccountDetails(request.getUserId());
		Response response = new Response();
		// if record is not there in table.
		if (null == lockedAccountDetails) {
			return this.processAuthenticatedUser(request);
		}

		Response lockResponse = validateLockTime(request, response,
				lockedAccountDetails);
		
		if (null != lockResponse) {
			return lockResponse;
		}

		// if time not elapsed we will check the login failure count.
		// if count is 3 then we will lock the user and update the time stamp in
		// table.
		// if not we will proceed for authentication. if authentication fail we
		// will update the count in table.
		if (this.lockAttempts == lockedAccountDetails.getInvalidLoginCount()) {
			return failedAttempts(request, response);
		}
		response = this.authenticate(request.getUserId(),
				request.getPassword(), request.getAuthenticationType(),
				request.getAppKey());
		if (null == response) {
			this.userLoginDao.updateUser(request.getUserId());
		}

		return response;
	}

	/**
	 * Method to check lock time of the user.
	 *
	 * @param request
	 * @param response
	 * @param lockedAccountDetails
	 * @return
	 */
	private Response validateLockTime(AuthenticateRequest request,
			Response response, LockedAccountDetails lockedAccountDetails) {
		// if record is there in table, check the time stamp and find whether
		// time is elapsed or not.
		final Date loginAttemptTime = new Date(lockedAccountDetails
				.getLoginAttemptTime().getTime());
		// if time elapsed we will unlock the user, remove the record in table
		// and proceed for authentication.
		final long minutes = TimeUnit.MILLISECONDS.toMinutes(new Date()
				.getTime() - loginAttemptTime.getTime());
		if (this.unlockTime < minutes) {
			return checkUserUnlockCondition(request, lockedAccountDetails);
		}
		if (this.lockAttempts < lockedAccountDetails.getInvalidLoginCount()) {
			response.setStatus(String.valueOf(Response.STATUS.LOCKED));
			return response;
		}
		return null;
	}

	/**
	 * Method to validate failed login attempts.
	 *
	 * @param request
	 * @param response
	 * @return
	 */
	private Response failedAttempts(AuthenticateRequest request,
			Response response) {
		// calling the Lock ALM service
		String status = this.lockUnlockUser(request.getUserId(), lockUserurl);
		if (CommonConstant.SUCCESS_STATUS.equalsIgnoreCase(status)) {
			// update the locked time in the table
			this.userLoginDao.updateTimeStamp(request.getUserId());
			response.setStatus(String.valueOf(Response.STATUS.LOCKED));
			return response;
		} else {
			return new Response(CommonConstant.LOCK_UNLOCK_FAIL_CODE,
					CommonConstant.INTERNAL_SERVER_ERROR,
					CommonConstant.FAILURE_STATUS);
		}
		
	}

	/**
	 * For AD Users.
	 *
	 * @param userId
	 * @param password
	 * @param authenticationType
	 * @param appKey
	 * @return
	 */
	private Response activeDirectoryUsers(String userId, String password,
			String authenticationType, String appKey) {
		return this.authenticate(userId, password, authenticationType, appKey);
	}

	/**
	 * Check User Lock Condition.
	 *
	 * @param request
	 * @param lockedAccountDetails
	 * @return
	 */
	private Response checkUserUnlockCondition(AuthenticateRequest request,
			LockedAccountDetails lockedAccountDetails) {
		// if user is Locked
		if (null != lockedAccountDetails.getLockedTime()) {
			// call the unLock external ALM service
			String status = this.lockUnlockUser(request.getUserId(),
					unlockUserurl);
			if (CommonConstant.SUCCESS_STATUS.equalsIgnoreCase(status)) {
				// removing the record in the temporary table.
				this.userLoginDao.removeUser(request.getUserId());
			} else {
				return new Response(CommonConstant.LOCK_UNLOCK_FAIL_CODE,
						CommonConstant.INTERNAL_SERVER_ERROR,
						CommonConstant.FAILURE_STATUS);
			}
		} else {
			this.userLoginDao.removeUser(request.getUserId());
		}
		return this.processAuthenticatedUser(request);
	}

	/**
	 * @param userId
	 * @param url
	 */
	private String lockUnlockUser(String userId, String url) {

		UserServiceRequest userServiceRequest = new UserServiceRequest();
		UserSecurityAttributes userSecurityAttributes = new UserSecurityAttributes();

		userSecurityAttributes.setExistingEmail(userId);
		userSecurityAttributes.setSourceSystem(CommonConstant.SOURCESYSTEM);

		userServiceRequest
				.setUpdateUserSecurityAttributes(userSecurityAttributes);

		ResponseStatus responseStatus = (ResponseStatus) ESBServiceInvoker
				.restServiceInvoker(url, userServiceRequest,
						ResponseStatus.class);
		return responseStatus.getStatus();
	}

}
