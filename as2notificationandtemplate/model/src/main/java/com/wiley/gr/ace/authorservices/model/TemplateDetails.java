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
 * The Class TemplateDetails.
 */
@JsonInclude(Include.NON_NULL)
public class TemplateDetails {

	/** The field list. */
	List<String> fieldList;

    /**
     * Gets the field list.
     *
     * @return the field list
     */
    public List<String> getFieldList() {
           return fieldList;
    }

    /**
     * Sets the field list.
     *
     * @param fieldList the new field list
     */
    public void setFieldList(List<String> fieldList) {
           this.fieldList = fieldList;
    }


	
}
