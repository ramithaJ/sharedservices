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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.suggest.SuggestResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.wiley.gr.ace.search.constant.CommonConstants;
import com.wiley.gr.ace.search.exception.SharedSearchException;
import com.wiley.gr.ace.search.model.Response;
import com.wiley.gr.ace.search.model.SearchCriteria;
import com.wiley.gr.ace.search.service.SearchClientService;
import com.wiley.gr.ace.search.service.SearchService;
import com.wiley.gr.ace.search.service.impl.SearchServiceImpl;

/**
 * @author KKALYAN
 *
 */
@RestController
@RequestMapping("/v1")
public class SearchController {

    @Autowired
    private SearchService searchService;

    @Autowired
    private SearchClientService searchClientService;

    private static final Logger LOGGER = LoggerFactory
            .getLogger(SearchServiceImpl.class);

    /**
     * Method create ES Index
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
     * Method to search the required data
     * 
     * @param criteria
     * @return
     */
    @RequestMapping(method = { RequestMethod.POST }, value = { "/api/_search" }, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Response search(@RequestBody SearchCriteria criteria) {
        Response response = null;
        try {
            logInputRequest(criteria, null, CommonConstants.CREATE_INDEX,
                    criteria.getRole());
            response = searchService.search(criteria, criteria.getRole());
        } catch (SharedSearchException e) {
            LOGGER.error("Error Occurred while searching", e);
        }
        return response;
    }

    /**
     * Method to support Auto complete feature
     * 
     * @param criteria
     * @return
     */
    @RequestMapping(method = { RequestMethod.POST }, value = { "/_get" }, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public SuggestResponse autoSearch(@RequestBody SearchCriteria criteria) {
        SuggestResponse response = null;
        try {
            response = searchService.autoComplete(criteria, criteria.getRole());
        } catch (SharedSearchException e) {
            LOGGER.error("Error Occurred while autoSuggest", e);
        }
        return response;
    }

    /**
     * Method to log the input request in the log file.
     *
     * @param inputRequest
     *            Input Request Obj
     */
    private void logInputRequest(SearchCriteria inputRequest, String request,
            String serviceCall, String role) {

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
    private String getDate() {
        DateFormat dateFormat = new SimpleDateFormat(
                CommonConstants.YYYY_MM_DD_HH_MM_SS);
        LOGGER.info("getDate() -----");
        Date date = new Date();
        return dateFormat.format(date);
    }

}
