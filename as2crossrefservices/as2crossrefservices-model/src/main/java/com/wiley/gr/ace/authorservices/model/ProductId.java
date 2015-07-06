package com.wiley.gr.ace.authorservices.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * The Class ProductId.
 */
@JsonInclude(Include.NON_NULL)
public class ProductId {
	
	/** The dh id. */
	private String dhId;

	/**
	 * Gets the dh id.
	 *
	 * @return the dh id
	 */
	public final String getDhId() {
		return dhId;
	}

	/**
	 * Sets the dh id.
	 *
	 * @param dhId the new dh id
	 */
	public final void setDhId(String dhId) {
		this.dhId = dhId;
	}
}
