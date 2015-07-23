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
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

/**
 * @author Virtusa
 *
 */
public class StubInvoker {

	/**
	 * This field holds the value of LOGGER
	 */
	private static final Logger LOGGER = LoggerFactory
			.getLogger(StubInvoker.class);
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
			StubInvoker.LOGGER.error("RestServiceInvoke stub exception", e);
		}
		return response.getBody();
	}

}
