package com.wiley.gr.ace.staticcontentservices.dotcms.service;

import com.wiley.gr.ace.staticcontentservices.model.external.ConfirmationCatalogDotcmsResponse;
import com.wiley.gr.ace.staticcontentservices.model.external.StatusCatalogDotcmsResponse;
import com.wiley.gr.ace.staticcontentservices.model.external.UIMessageCatalogDotcmsResponse;



/**
 * The Interface DotCMSDataService.
 */
public interface DotCMSDataService {

	/**
	 * Gets the ui message catalog.
	 *
	 * @param pageName the page name
	 * @param locale the locale
	 * @return the ui message catalog
	 * @throws Exception the exception
	 */
	UIMessageCatalogDotcmsResponse getUiMessageCatalog(String pageName, String locale) throws Exception;

	/**
	 * Gets the confirmation catalog.
	 *
	 * @param moduleName the module name
	 * @param locale the locale
	 * @return the confirmation catalog
	 * @throws Exception the exception
	 */
	ConfirmationCatalogDotcmsResponse getConfirmationCatalog(String moduleName, String locale) throws Exception;

	/**
	 * Gets the status catalog.
	 *
	 * @param statusMessageType the status message type
	 * @param locale the locale
	 * @return the status catalog
	 * @throws Exception the exception
	 */
	StatusCatalogDotcmsResponse getStatusCatalog(String statusMessageType, String locale) throws Exception;
}
