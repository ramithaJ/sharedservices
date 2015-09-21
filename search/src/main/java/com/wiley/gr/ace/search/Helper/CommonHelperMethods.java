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
package com.wiley.gr.ace.search.Helper;

import java.util.List;

/**
 * @author rajatg
 *
 */
public class CommonHelperMethods {
	/**
	 * Util method.
	 *
	 * @param listOfValues
	 * @return
	 */
	private String convertListintoString(List<String> listOfValues) {
		StringBuilder builder = new StringBuilder();
		for (String value : listOfValues) {
			builder.append("\"");
			builder.append(value);
			builder.append("\",");

		}
		return builder.toString().replaceAll(",$", "");
	}
	
}
