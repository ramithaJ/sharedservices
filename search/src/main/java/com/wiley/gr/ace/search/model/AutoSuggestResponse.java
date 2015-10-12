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
 * The Class SuggestResponse.
 */
public class AutoSuggestResponse {
    
    /** The total. */
    private int total;
    
    /** The suggestions. */
    private List<String> suggestions;
    
    /**
     * Gets the total.
     *
     * @return the total
     */
    public int getTotal() {
        return total;
    }
    
    /**
     * Sets the total.
     *
     * @param total the new total
     */
    public void setTotal(int total) {
        this.total = total;
    }
    
    /**
     * Gets the suggestions.
     *
     * @return the suggestions
     */
    public List<String> getSuggestions() {
        return suggestions;
    }
    
    /**
     * Sets the suggestions.
     *
     * @param suggestions the new suggestions
     */
    public void setSuggestions(List<String> suggestions) {
        this.suggestions = suggestions;
    }
    
    
}
