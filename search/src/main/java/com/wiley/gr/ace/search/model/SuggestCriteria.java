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

/**
 * The Class SuggestCriteria.
 */
public class SuggestCriteria {

    /** The app key. */
    private String appKey;

    /** the index. */
    private String index;

    /** The auto complete query. */
    private AutoCompleteQuery autoCompleteQuery;

    /** The size. */
    private int size = Integer.MAX_VALUE;

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
     * @param appKey
     *            the new app key
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
     * @param index
     *            the new index
     */
    public void setIndex(String index) {
        this.index = index;
    }

    /**
     * Gets the auto complete query.
     *
     * @return the auto complete query
     */
    public AutoCompleteQuery getAutoCompleteQuery() {
        return autoCompleteQuery;
    }

    /**
     * Sets the auto complete query.
     *
     * @param autoCompleteQuery
     *            the new auto complete query
     */
    public void setAutoCompleteQuery(AutoCompleteQuery autoCompleteQuery) {
        this.autoCompleteQuery = autoCompleteQuery;
    }

    /**
     * Gets the size.
     *
     * @return the size
     */
    public int getSize() {
        return size;
    }

    /**
     * Sets the size.
     *
     * @param size
     *            the new size
     */
    public void setSize(int size) {
        this.size = size;
    }

}
