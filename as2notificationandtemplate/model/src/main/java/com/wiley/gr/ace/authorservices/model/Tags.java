/*******************************************************************************
 * Copyright (c) 2015 John Wiley & Sons, Inc. All rights reserved.
 *
 * All material contained herein is proprietary to John Wiley & Sons 
 * and its third party suppliers, if any. The methods, techniques and 
 * technical concepts contained herein are considered trade secrets 
 * and confidential and may be protected by intellectual property laws.  
 * Reproduction or distribution of this material, in whole or in part, 
 * is strictly forbidden except by express prior written permission 
 * of John Wiley & Sons.
 *******************************************************************************/
package com.wiley.gr.ace.authorservices.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * The Class Tags.
 * 
 * @author virtusa version 1.0
 */
@JsonInclude(Include.NON_NULL)
public class Tags {

    /** The tag1 list. */
    private List<String> tag1List;

    /** The tag2 list. */
    private List<String> tag2List;

    /**
     * Gets the tag1 list.
     *
     * @return the tag1 list
     */
    public final List<String> getTag1List() {

        return tag1List;
    }

    /**
     * Sets the tag1 list.
     *
     * @param tag1List
     *            the new tag1 list
     */
    public final void setTag1List(final List<String> tag1List) {
        this.tag1List = tag1List;
    }

    /**
     * Gets the tag2 list.
     *
     * @return the tag2 list
     */
    public final List<String> getTag2List() {
        return tag2List;
    }

    /**
     * Sets the tag2 list.
     *
     * @param tag2List
     *            the new tag2 list
     */
    public final void setTag2List(final List<String> tag2List) {
        this.tag2List = tag2List;
    }

}
