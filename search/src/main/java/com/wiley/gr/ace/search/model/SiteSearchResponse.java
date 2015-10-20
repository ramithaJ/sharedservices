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

import java.io.Serializable;
import java.util.List;

/**
 * The Class SiteSearchResponse.
 */
public class SiteSearchResponse implements Serializable {
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    /** The response. */
    private List<Response> response;

    /**
     * Gets the response.
     *
     * @return the response
     */
    public List<Response> getResponse() {
        return response;
    }

    /**
     * Sets the response.
     *
     * @param response the new response
     */
    public void setResponse(List<Response> response) {
        this.response = response;
    }

}
