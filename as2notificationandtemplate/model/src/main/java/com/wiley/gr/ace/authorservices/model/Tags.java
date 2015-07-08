package com.wiley.gr.ace.authorservices.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
/**
 * The Class Tags.
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
	public List<String> getTag1List() {

		return tag1List;
	}

	/**
	 * Sets the tag1 list.
	 *
	 * @param tag1List the new tag1 list
	 */
	public void setTag1List(List<String> tag1List) {
		this.tag1List = tag1List;
	}
	
	/**
	 * Gets the tag2 list.
	 *
	 * @return the tag2 list
	 */
	public List<String> getTag2List() {
		return tag2List;
	}

	/**
	 * Sets the tag2 list.
	 *
	 * @param tag2List the new tag2 list
	 */
	public void setTag2List(List<String> tag2List) {
		this.tag2List = tag2List;
	}
	
}
