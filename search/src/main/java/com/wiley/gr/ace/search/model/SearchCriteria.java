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
 * The Class SearchCriteria.
 *
 * @author virtusa version 1.0
 */
public class SearchCriteria {

    /**
     * The role.
     */
    private String role;

    /**
     * The appKey.
     */
    private String appKey;

    /**
     * The types.
     */
    private List<String> types;

    /**
     * The advanced query.
     */
    private String simpleQuery;

    /**
     * The simple query
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
    private int offset = 0;

    /**
     * The rows.
     */
    private int rows = 10;

    private boolean enableFacets;

    /**
     * @return the role
     */
    public String getRole() {
        return role;
    }

    /**
     * @param role the role to set
     */
    public void setRole(String role) {
        this.role = role;
    }

    /**
     * @return the appKey
     */
    public String getAppKey() {
        return appKey;
    }

    /**
     * @param appKey the appKey to set
     */
    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    /**
     * @return the types
     */
    public List<String> getTypes() {
        return types;
    }

    /**
     * @param types the types to set
     */
    public void setTypes(List<String> types) {
        this.types = types;
    }

    /**
     * @return the simpleQuery
     */
    public String getSimpleQuery() {
        return simpleQuery;
    }

    /**
     * @param simpleQuery the simpleQuery to set
     */
    public void setSimpleQuery(String simpleQuery) {
        this.simpleQuery = simpleQuery;
    }

    /**
     * @return the advanceQuery
     */
    public List<AdvanceQuery> getAdvanceQuery() {
        return advanceQuery;
    }

    /**
     * @param advanceQuery the advanceQuery to set
     */
    public void setAdvanceQuery(List<AdvanceQuery> advanceQuery) {
        this.advanceQuery = advanceQuery;
    }

    /**
     * @return the sort
     */
    public List<Sorting> getSort() {
        return sort;
    }

    /**
     * @param sort the sort to set
     */
    public void setSort(List<Sorting> sort) {
        this.sort = sort;
    }

    /**
     * @return the filters
     */
    public Filter getFilters() {
        return filters;
    }

    /**
     * @param filters the filters to set
     */
    public void setFilters(Filter filters) {
        this.filters = filters;
    }

    /**
     * @return the offset
     */
    public int getOffset() {
        return offset;
    }

    /**
     * @param offset the offset to set
     */
    public void setOffset(int offset) {
        this.offset = offset;
    }

    /**
     * @return the rows
     */
    public int getRows() {
        return rows;
    }

    /**
     * @param rows the rows to set
     */
    public void setRows(int rows) {
        this.rows = rows;
    }

    public boolean isEnableFacets() {
        return enableFacets;
    }

    public void setEnableFacets(boolean enableFacets) {
        this.enableFacets = enableFacets;
    }

    /* (non-Javadoc)
         * @see java.lang.Object#toString()
         */
    @Override
    public String toString() {
        return "SearchCriteria [role=" + role + ", appKey=" + appKey
                + ", types=" + types + ", simpleQuery=" + simpleQuery
                + ", advanceQuery=" + advanceQuery + ", sort=" + sort
                + ", filters=" + filters + ", offset=" + offset + ", rows="
                + rows + "]";
    }

}
