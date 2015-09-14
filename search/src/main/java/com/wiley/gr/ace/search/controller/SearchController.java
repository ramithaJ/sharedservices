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

import com.wiley.gr.ace.search.model.Response;
import com.wiley.gr.ace.search.model.SearchCriteria;
import com.wiley.gr.ace.search.service.SearchClientService;
import com.wiley.gr.ace.search.service.SearchService;
import org.elasticsearch.action.index.IndexResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/search")
public class SearchController {

    @Autowired
    private SearchService searchService;


    @Autowired
    private SearchClientService searchClientService;

    /**
     * Method create ES Index
     */
    @RequestMapping(method = {RequestMethod.POST}, value = {"/_index"})
    public void createIndex(@RequestBody String json, @RequestParam(value = "type", required = false) String type) {
//        IndexResponse response = client.prepareIndex("journals", "journal")
//                .setSource(json)
//                .execute()
//                .actionGet();

        IndexResponse response = searchClientService.getClient().prepareIndex("search", type)
                .setSource(json)
                .execute()
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
// Version (if it's the first time you index this document, you will get: 1)
        long _version = response.getVersion();
        System.out.println(_version);
// isCreated() is true if the document is a new one, false if it has been updated
        boolean created = response.isCreated();
        System.out.println(created);

        // on shutdown

        searchClientService.getClient().close();

    }

    @RequestMapping(method = {RequestMethod.POST}, value = {"/_get"}, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Response search(@RequestBody SearchCriteria criteria) {
        Response response = null;
        try {
            response = searchService.search(criteria);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }
}
