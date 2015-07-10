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

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * The Class ScheduleTemplateObj.
 */
@JsonInclude(Include.NON_NULL)
public class ScheduleTemplateObj {

	/** The template by onscreen tmpl id. */
	private String email;

	/** The template by email tmpl id. */
	private String onscreen;

	/** The delay. */
	private Integer delay;

	/**
	 * Gets the template by onscreen tmpl id.
	 *
	 * @return the template by onscreen tmpl id
	 */
	public String getOnscreen() {
		return onscreen;
	}

	/**
	 * Sets the template by onscreen tmpl id.
	 *
	 * @param templateByOnscreenTmplId
	 *            the new template by onscreen tmpl id
	 */
	public void setOnscreen(String onscreen) {
		this.onscreen = onscreen;
	}

	/**
	 * Gets the template by email tmpl id.
	 *
	 * @return the template by email tmpl id
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Sets the template by email tmpl id.
	 *
	 * @param templateByEmailTmplId
	 *            the new template by email tmpl id
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Gets the delay.
	 *
	 * @return the delay
	 */
	public Integer getDelay() {
		return delay;
	}

	/**
	 * Sets the delay.
	 *
	 * @param delay
	 *            the new delay
	 */
	public void setDelay(Integer delay) {
		this.delay = delay;
	}

}
