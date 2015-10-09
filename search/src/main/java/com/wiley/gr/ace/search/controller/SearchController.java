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
package com.wiley.gr.ace.search.controller;

import java.util.List;

import org.elasticsearch.action.index.IndexResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.wiley.gr.ace.search.constant.CommonConstants;
import com.wiley.gr.ace.search.exception.SharedSearchException;
import com.wiley.gr.ace.search.model.Response;
import com.wiley.gr.ace.search.model.SearchCriteria;
import com.wiley.gr.ace.search.model.SuggestCriteria;
import com.wiley.gr.ace.search.service.SearchClientService;
import com.wiley.gr.ace.search.service.SearchService;
import com.wiley.gr.ace.search.service.impl.SearchServiceImpl;
import com.wiley.gr.ace.search.util.SearchUtil;

/**
 * The Class SearchController.
 *
 * @author virtusa version 1.0
 */
@RestController
@RequestMapping(CommonConstants.SEARCH_CONTROLLER_REQUEST_MAPPING)
public class SearchController {

    /** The search service. */
    @Autowired
    private SearchService searchService;

    /** The search client service. */
    @Autowired
    private SearchClientService searchClientService;

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory
            .getLogger(SearchServiceImpl.class);

    /**
     * Method create ES Index.
     *
     * @param json the json
     * @param type the type
     */
    @RequestMapping(method = { RequestMethod.POST }, value = { "/_index" })
    public void createIndex(@RequestBody String json,
            @RequestParam(value = "type", required = false) String type) {
        IndexResponse response = searchClientService.getClient()
                .prepareIndex("search", type).setSource(json).execute()
                .actionGet();

        // Index name
        String _index = response.getIndex();
        System.out.println(_index);
        // Type name
        String _type = response.getType();
        System.out.println(_type);
        // Document ID (generated or not)
        String _id = response.getId();
        System.out.println(_id);
        // Version (if it's the first time you index this document, you will
        // get: 1)
        long _version = response.getVersion();
        System.out.println(_version);
        // isCreated() is true if the document is a new one, false if it has
        // been updated
        boolean created = response.isCreated();
        System.out.println(created);

    }

    /**
     * Method to search the required data.
     *
     * @param criteria the criteria
     * @param role the role
     * @return the response
     */
    @RequestMapping(method = { RequestMethod.POST }, value = { CommonConstants.SEARCH_URL_VALUE }, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Response search(@RequestBody SearchCriteria criteria,
    		@RequestHeader(value="role") String role) {
        Response response = null;
        try {
            SearchUtil.logInputRequest(criteria, null,
                    CommonConstants.SEARCH_SERVICE, role);
            response = searchService.search(criteria, role);
        } catch (SharedSearchException e) {
            LOGGER.error("Error Occurred while searching", e);
        }
        return response;
    }

    /**
     * Method to support Auto complete feature.
     *
     * @param criteria the criteria
     * @return the list
     */
    @RequestMapping(method = { RequestMethod.POST }, value = { CommonConstants.SEARCH_AUTO_SUGGEST_URL_VALUE }, produces = MediaType.APPLICATION_JSON_VALUE)
//    @ResponseBody
    public List<String> autoSearch(@RequestBody SuggestCriteria criteria) {
    	List<String> response = null;
        try {
            response = searchService.autoComplete(criteria);
        } catch (SharedSearchException e) {
            LOGGER.error("Error Occurred while autoSuggest", e);
        }
        return response;
    }

}
