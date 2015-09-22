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
package com.wiley.gr.ace.search.service;

import org.elasticsearch.action.suggest.SuggestResponse;

import com.wiley.gr.ace.search.exception.SharedSearchException;
import com.wiley.gr.ace.search.model.Response;
import com.wiley.gr.ace.search.model.SearchCriteria;

/**
 * The Interface SearchService.
 *
 * @author virtusa version 1.0
 */
public interface SearchService {

    /**
     * The interface method SearchService.
     * 
     * @param searchCriteria
     *            - the input value
     * @param role
     *            - the input value
     * @return response
     * @throws SharedSearchException
     */
    Response search(SearchCriteria searchCriteria, String role)
            throws SharedSearchException;

    /**
     * The interface method autoComplete.
     * 
     * @param criteria
     *            - the input value
     * @param role
     *            - the input value
     * @return suggestResponse
     */
    SuggestResponse autoComplete(SearchCriteria criteria, String role);
}

