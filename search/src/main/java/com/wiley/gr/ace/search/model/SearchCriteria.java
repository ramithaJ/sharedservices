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

import com.wiley.gr.ace.search.constant.CommonConstants;

/**
 * The Class SearchCriteria.
 *
 * @author virtusa version 1.0
 */
public class SearchCriteria {

    /**
     * The appKey.
     */
    private String appKey;

    /** the index. */
    private String index;

    /**
     * The types.
     */
    private List<String> types;

    /**
     * The advanced query.
     */
    private String simpleQuery;

    /**
     * The simple query.
     */
    private List<AdvanceQuery> advanceQuery;

    /**
     * The sort.
     */
    private List<Sorting> sort;

    /**
     * The filters.
     */
    private Filter filters;

    /**
     * The offset.
     */
    private int offset = CommonConstants.SEARCH_CRITERIA_DEFAULT_OFFSET;

    /**
     * The rows.
     */
    private int rows = CommonConstants.SEARCH_CRITERIA_DEFAULT_ROWS;

    /** The enable facets. */
    private boolean enableFacets;

    /**
     * Gets the app key.
     *
     * @return the appKey
     */
    public String getAppKey() {
        return appKey;
    }

    /**
     * Sets the app key.
     *
     * @param appKey            the appKey to set
     */
    public void setAppKey(String appKey) {
        this.appKey = appKey;
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
     * @param types            the types to set
     */
    public void setTypes(List<String> types) {
        this.types = types;
    }

    /**
     * Gets the simple query.
     *
     * @return the simpleQuery
     */
    public String getSimpleQuery() {
        return simpleQuery;
    }

    /**
     * Sets the simple query.
     *
     * @param simpleQuery            the simpleQuery to set
     */
    public void setSimpleQuery(String simpleQuery) {
        this.simpleQuery = simpleQuery;
    }

    /**
     * Gets the advance query.
     *
     * @return the advanceQuery
     */
    public List<AdvanceQuery> getAdvanceQuery() {
        return advanceQuery;
    }

    /**
     * Sets the advance query.
     *
     * @param advanceQuery            the advanceQuery to set
     */
    public void setAdvanceQuery(List<AdvanceQuery> advanceQuery) {
        this.advanceQuery = advanceQuery;
    }

    /**
     * Gets the sort.
     *
     * @return the sort
     */
    public List<Sorting> getSort() {
        return sort;
    }

    /**
     * Sets the sort.
     *
     * @param sort            the sort to set
     */
    public void setSort(List<Sorting> sort) {
        this.sort = sort;
    }

    /**
     * Gets the filters.
     *
     * @return the filters
     */
    public Filter getFilters() {
        return filters;
    }

    /**
     * Sets the filters.
     *
     * @param filters            the filters to set
     */
    public void setFilters(Filter filters) {
        this.filters = filters;
    }

    /**
     * Gets the offset.
     *
     * @return the offset
     */
    public int getOffset() {
        return offset;
    }

    /**
     * Sets the offset.
     *
     * @param offset            the offset to set
     */
    public void setOffset(int offset) {
        this.offset = offset;
    }

    /**
     * Gets the rows.
     *
     * @return the rows
     */
    public int getRows() {
        return rows;
    }

    /**
     * Sets the rows.
     *
     * @param rows            the rows to set
     */
    public void setRows(int rows) {
        this.rows = rows;
    }

    /**
     * Checks if is enable facets.
     *
     * @return the enableFacets
     */
    public boolean isEnableFacets() {
        return enableFacets;
    }

    /**
     * Sets the enable facets.
     *
     * @param enableFacets            the enableFacets to set
     */
    public void setEnableFacets(boolean enableFacets) {
        this.enableFacets = enableFacets;
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
     * @param index            the index to set
     */
    public void setIndex(String index) {
        this.index = index;
    }

    /**
     * toString() method.
     *
     * @return the string
     */
    @Override
    public String toString() {
        return "SearchCriteria [appKey=" + appKey + ", index=" + index
                + ", types=" + types + ", simpleQuery=" + simpleQuery
                + ", advanceQuery=" + advanceQuery + ", sort=" + sort
                + ", filters=" + filters + ", offset=" + offset + ", rows="
                + rows + ", enableFacets=" + enableFacets + "]";
    }

}
