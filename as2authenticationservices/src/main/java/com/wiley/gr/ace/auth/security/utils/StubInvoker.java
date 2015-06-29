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
package com.wiley.gr.ace.auth.security.utils;

import java.net.URI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.wiley.gr.ace.auth.security.model.SecurityResponse;

/**
 * @author Virtusa
 *
 */
public class StubInvoker {

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
			final HttpHeaders requestHeaders = new HttpHeaders();
			requestHeaders.setContentType(MediaType.APPLICATION_JSON);
			response = new RestTemplate().postForEntity(new URI(url),
					requestEntityClass, responseEntityClass);

			if (null == response) {
				return new SecurityResponse();
			}
		} catch (final Exception e) {

			StubInvoker.LOGGER.error("RestServiceInvoke stub exception", e);
		}

		return response;
	}

	/**
	 * This field holds the value of LOGGER
	 */
	private static final Logger LOGGER = LoggerFactory
			.getLogger(StubInvoker.class);
}
