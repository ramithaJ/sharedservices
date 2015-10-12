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
package com.wiley.gr.ace.search.model;

import java.util.List;

/**
 * The Class SiteSearchRequest.
 */
public class SiteSearchRequest {
    
    /** The app key. */
    private String appKey;
    
    /** The index. */
    private String index;
    
    /** The types. */
    private List<String> types;
    
    /** The query. */
    private String query;
    
    /** The search fields. */
    private List<String> searchFields;
    
    /** The response fields. */
    private List<String> responseFields;
    
    /**
     * Gets the app key.
     *
     * @return the app key
     */
    public String getAppKey() {
        return appKey;
    }
    
    /**
     * Sets the app key.
     *
     * @param appKey the new app key
     */
    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }
    
    /**
     * Gets the index.
     *
     * @return the index
     */
    public String getIndex() {
        return index;
    }
    
    /**
     * Sets the index.
     *
     * @param index the new index
     */
    public void setIndex(String index) {
        this.index = index;
    }
    
    /**
     * Gets the types.
     *
     * @return the types
     */
    public List<String> getTypes() {
        return types;
    }
    
    /**
     * Sets the types.
     *
     * @param types the new types
     */
    public void setTypes(List<String> types) {
        this.types = types;
    }
    
    /**
     * Gets the query.
     *
     * @return the query
     */
    public String getQuery() {
        return query;
    }
    
    /**
     * Sets the query.
     *
     * @param query the new query
     */
    public void setQuery(String query) {
        this.query = query;
    }
    
    /**
     * Gets the search fields.
     *
     * @return the search fields
     */
    public List<String> getSearchFields() {
        return searchFields;
    }
    
    /**
     * Sets the search fields.
     *
     * @param searchFields the new search fields
     */
    public void setSearchFields(List<String> searchFields) {
        this.searchFields = searchFields;
    }
    
    /**
     * Gets the response fields.
     *
     * @return the response fields
     */
    public List<String> getResponseFields() {
        return responseFields;
    }
    
    /**
     * Sets the response fields.
     *
     * @param responseFields the new response fields
     */
    public void setResponseFields(List<String> responseFields) {
        this.responseFields = responseFields;
    }
    
}
