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
