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
package com.wiley.gr.ace.search.constant;

import org.springframework.beans.factory.annotation.Value;

/**
 * The Class Property.
 *
 * @author virtusa version 1.0
 */
public class Property {

    @Value("${SEARCH_SERVICE_ERROR_100}")
    public String searchServiceError100;

    @Value("${SEARCH_SERVICE_ERROR_101}")
    public String searchServiceError101;

    @Value("${SEARCH_SERVICE_ERROR_102}")
    public String searchServiceError102;

    @Value("${SEARCH_SERVICE_ERROR_103}")
    public String searchServiceError103;

}
