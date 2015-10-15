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
package com.wiley.gr.ace.search.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wiley.gr.ace.search.constant.CommonConstants;
import com.wiley.gr.ace.search.model.SearchCriteria;

/**
 * The Class SearchUtil.
 *
 * @author virtusa version 1.0
 */
public class SearchUtil {
    private static final Logger LOGGER = LoggerFactory
            .getLogger(SearchUtil.class);

    private SearchUtil() {
        super();
    }

    /**
     * Util method to convert List to String.
     *
     * @param listOfValues
     *            - the input value
     * @return string
     */
    public static String convertListintoString(List<String> listOfValues) {
        StringBuilder builder = new StringBuilder();
        for (String value : listOfValues) {
            builder.append("\"");
            builder.append(value);
            builder.append("\",");

        }
        return builder.toString().replaceAll(",$", "");
    }

    /**
     * Method to log the input request in the log file.
     *
     * @param inputRequest
     *            Input Request Obj
     */
    public static void logInputRequest(SearchCriteria inputRequest,
            String request, String serviceCall, String role) {

        if (inputRequest != null) {
            LOGGER.info(CommonConstants.SEPERATOR + serviceCall
                    + CommonConstants.COLON + getDate()
                    + inputRequest.getAppKey() + CommonConstants.COLON + role
                    + CommonConstants.SEPERATOR);
            LOGGER.info(inputRequest.toString());

        } else if (request != null) {
            LOGGER.info(CommonConstants.SEPERATOR + serviceCall
                    + CommonConstants.COLON + getDate() + CommonConstants.COLON
                    + role + CommonConstants.SEPERATOR);
            LOGGER.info(request);
        }

        LOGGER.info(CommonConstants.SEPERATOR + serviceCall
                + CommonConstants.COLON + getDate() + CommonConstants.SEPERATOR);

    }

    /**
     * Method to get date for logging purpose.
     *
     * @return
     */
    public static String getDate() {
        DateFormat dateFormat = new SimpleDateFormat(
                CommonConstants.YYYY_MM_DD_HH_MM_SS);
        LOGGER.info("getDate() -----");
        Date date = new Date();
        return dateFormat.format(date);
    }

}
