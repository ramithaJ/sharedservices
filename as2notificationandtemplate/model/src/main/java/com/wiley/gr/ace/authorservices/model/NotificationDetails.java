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
 * The Class NotificationDetails.
 * 
 * @author virtusa version 1.0
 */
@JsonInclude(Include.NON_NULL)
public class NotificationDetails {

	/** The from. */
	private String from;
	
	/** The to. */
	private String to;
	
	/** The template details. */
	private TemplateDetails templateDetails;

	/**
	 * Gets the from.
	 *
	 * @return the from
	 */
	public final String getFrom() {
		return from;
	}

	/**
	 * Sets the from.
	 *
	 * @param from the new from
	 */
	public final void setFrom(final String from) {
		this.from = from;
	}

	/**
	 * Gets the to.
	 *
	 * @return the to
	 */
	public final String getTo() {
		return to;
	}

	/**
	 * Sets the to.
	 *
	 * @param to the new to
	 */
	public final void setTo(final String to) {
		this.to = to;
	}

	/**
	 * Gets the template details.
	 *
	 * @return the template details
	 */
	public final TemplateDetails getTemplateDetails() {
		return templateDetails;
	}

	/**
	 * Sets the template details.
	 *
	 * @param templateDetails the new template details
	 */
	public final void setTemplateDetails(final TemplateDetails templateDetails) {
		this.templateDetails = templateDetails;
	}

}
