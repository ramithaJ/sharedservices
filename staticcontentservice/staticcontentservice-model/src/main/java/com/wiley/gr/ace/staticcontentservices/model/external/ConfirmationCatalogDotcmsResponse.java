package com.wiley.gr.ace.staticcontentservices.model.external;

import java.util.List;



/**
 * The Class ConfirmationCatalogDotcmsResponse.
 */
public class ConfirmationCatalogDotcmsResponse {

	/** The contentlets. */
	List<ConfirmationCatalog> contentlets;

	/**
	 * Gets the contentlets.
	 *
	 * @return the contentlets
	 */
	public final List<ConfirmationCatalog> getContentlets() {
		return contentlets;
	}

	/**
	 * Sets the contentlets.
	 *
	 * @param contentlets the new contentlets
	 */
	public final void setContentlets(List<ConfirmationCatalog> contentlets) {
		this.contentlets = contentlets;
	}
}
