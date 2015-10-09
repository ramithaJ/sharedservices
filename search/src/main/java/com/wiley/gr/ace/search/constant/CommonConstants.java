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

/**
 * The interface CommonConstants.
 *
 * @author virtusa version 1.0
 */
public interface CommonConstants {

    public static final String CLUSTER_NAME = "cluster.name";
    public static final String CLUSTER_TRANSPORT_SNIFF = "client.transport.sniff";
    public static final String ROLES = "ROLES";
    public static final String ROLE_ADMIN = "ADMIN";
    public static final String ROLE_REGISTERED_USER = "REGISTERED";
    public static final String ROLE_GUEST_USER = "GUEST";

    public static final String SEPERATOR = "#################################";
    public static final String CREATE_INDEX = "CREATE_INDEX";
    public static final String SEARCH_SERVICE = "SEARCH";
    public static final String COLON = ":";
    public static final String COMMA = ",";
    public static final String UNDERSCORE = "_";

    public static final String YYYY_MM_DD_HH_MM_SS = "yyyy/MM/dd HH:mm:ss";

    public static final String ERROR_NOTE = "Error :";
    public static final String ERROR_CODE_100 = "1000";
    public static final String ERROR_CODE_101 = "1001";
    public static final String ERROR_CODE_102 = "1002";
    public static final String ERROR_CODE_103 = "1003";
    public static final String ERROR_CODE_104 = "1004";
    public static final String ERROR_CODE_105 = "1005";
    public static final String ERROR_CODE_107 = "1007";
    public static final String ERROR_CODE_109 = "1009";
    public static final String ERROR_CODE_110 = "1010";
    public static final String ERROR_CODE_111 = "1011";
    public static final String ERROR_CODE_112 = "1012";
    public static final String ERROR_CODE_113 = "1013";
    public static final String ERROR_CODE_114 = "1014";
    public static final String ERROR_CODE_115 = "1015";
    public static final String ERROR_CODE_116 = "1016";
    public static final String ERROR_CODE_117 = "1017";

    public static final String INTERNAL_SERVER_ERROR = "Internal Server Error - Please check the logs";

    public static final String SEARCH_CONTROLLER_REQUEST_MAPPING = "/v1";
    public static final String SEARCH_URL_VALUE = "/api/_search";
    public static final String SEARCH_AUTO_SUGGEST_URL_VALUE = "/_get";
    public static final String SCORE = "_score";
    public static final String ASC = "ASC";
    public static final String DESC = "DESC";

    public static final String SEARCH_TYPE_JOURNAL = "journal";
    public static final String SEARCH_TYPE_ARTICLE = "article";

    public static final String SEARCH_JOURNAL_FACET_ATTRIBUTES = "JOURNAL_FACET_ATTRIBUTES";
    public static final String SEARCH_ARTICLE_FACET_ATTRIBUTES = "ARTICLE_FACET_ATTRIBUTES";
    public static final String SEARCH_FACET_ATTRIBUTES = "_FACET_ATTRIBUTES";

    public static final String SEARCH_SEARCH_ATTR_KEY_STRING = "_SEARCH_ATTRIBUTES";
    public static final String SEARCH_RESULT_ATTR_KEY_STRING = "_SEARCH_RESULT_ATTRIBUTES";
    
    public static final int SEARCH_CRITERIA_DEFAULT_OFFSET = 0;
    public static final int SEARCH_CRITERIA_DEFAULT_ROWS = 10;
    
    public static final String AUTOCOMPLETE_ANALYZER= "completion";


}
