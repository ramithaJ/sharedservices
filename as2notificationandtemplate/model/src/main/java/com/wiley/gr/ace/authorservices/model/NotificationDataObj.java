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

/**
 * The Class NotificationDataObj.
 * 
 * @author virtusa version 1.0
 */
public class NotificationDataObj {

    /** The data item key. */
    private String dataItemKey;

    /** The data item value. */
    private String dataItemValue;

    /**
     * Gets the data item key.
     *
     * @return the data item key
     */
    public final String getDataItemKey() {
        return dataItemKey;
    }

    /**
     * Sets the data item key.
     *
     * @param dataItemKey
     *            the new data item key
     */
    public final void setDataItemKey(final String dataItemKey) {
        this.dataItemKey = dataItemKey;
    }

    /**
     * Gets the data item value.
     *
     * @return the data item value
     */
    public final String getDataItemValue() {
        return dataItemValue;
    }

    /**
     * Sets the data item value.
     *
     * @param dataItemValue
     *            the new data item value
     */
    public final void setDataItemValue(final String dataItemValue) {
        this.dataItemValue = dataItemValue;
    }
}
