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
import java.util.Map;

/**
 * The Class Filter.
 *
 * @author virtusa version 1.0
 */
public class Filter {

    /** The map term. */
    private Map<String, List<String>> term;

    /**
     * @return the term
     */
    public Map<String, List<String>> getTerm() {
        return term;
    }

    /**
     * @param term
     *            the term to set
     */
    public void setTerm(Map<String, List<String>> term) {
        this.term = term;
    }

}
