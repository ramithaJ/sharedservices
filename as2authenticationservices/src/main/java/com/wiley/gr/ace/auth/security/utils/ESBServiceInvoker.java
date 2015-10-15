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
package com.wiley.gr.ace.auth.security.utils;

import java.net.URI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.wiley.gr.ace.auth.security.constants.CommonConstant;
import com.wiley.gr.ace.auth.security.model.UserSearchResponse;
import com.wiley.gr.ace.auth.security.model.VerifyUserResponse;

/**
 * @author Virtusa
 */
public class ESBServiceInvoker {

	/**
	 * This field holds the value of LOGGER
	 */
	private static final Logger LOGGER = LoggerFactory
			.getLogger(ESBServiceInvoker.class);

	private ESBServiceInvoker() {

	}

	/**
	 * This method is restServiceInvoker
	 *
	 * @param url
	 * @param requestEntityClass
	 * @param responseEntityClass
	 * @return Object
	 */
	public static <T> Object restServiceInvoker(final String url,
			final Object requestEntityClass, final Class<T> responseEntityClass) {

		ResponseEntity<T> response = null;
		try {
			response = new RestTemplate().postForEntity(new URI(url),
					requestEntityClass, responseEntityClass);

		} catch (final Exception e) {
			ESBServiceInvoker.LOGGER.error("RestServiceInvoke stub exception",
					e);
		}
		return response.getBody();
	}

	/**
	 * 
	 * @param url the request value.
	 * @param emailId the request value
	 * @return boolean 
	 */
	public static boolean verifyEmail(final String url, final String emailId) {

		HttpHeaders httpHeaders = new HttpHeaders();
		try {
			httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
			httpHeaders.add(CommonConstant.EMAIL, emailId);
			HttpEntity<String> httpEntity = new HttpEntity<String>(httpHeaders);
			VerifyUserResponse response = new RestTemplate().exchange(url,
					HttpMethod.GET, httpEntity, VerifyUserResponse.class)
					.getBody();
			if (CommonConstant.SUCCESS_STATUS.equalsIgnoreCase(response.getStatus())) {
				return true;
			}
		} catch (final Exception e) {
			ESBServiceInvoker.LOGGER.error("RestServiceInvoke stub exception",
					e);
		}

		return false;

	}
	
	
	/**
	 * Search user in alm.
	 *
	 * @param url the url
	 * @param emailId the email id
	 * @return the string
	 */
	public static String searchUserInALM(final String url) {
		
		HttpHeaders httpHeaders = new HttpHeaders();
		try {
			httpHeaders.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<String> httpEntity = new HttpEntity<String>(httpHeaders);
			UserSearchResponse response = new RestTemplate().exchange(url,
					HttpMethod.GET, httpEntity, UserSearchResponse.class)
					.getBody();
			if (CommonConstant.SUCCESS_STATUS.equalsIgnoreCase(response.getStatus())) {
				return response.getUserData().getUserPayLoadList().get(0).getEcid();
			}
		} catch (final Exception e) {
			ESBServiceInvoker.LOGGER.error("RestServiceInvoke stub exception",
					e);
		}

		return null;
	}

}
