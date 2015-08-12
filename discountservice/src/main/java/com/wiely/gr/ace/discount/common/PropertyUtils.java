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
package com.wiely.gr.ace.discount.common;

import org.springframework.beans.factory.annotation.Value;

public class PropertyUtils {

    /**
     * Error code From Props File.
     */
    @Value("${DISCOUNT_SERVICE_ERROR_101}")
    public static String errorcode101;

}
