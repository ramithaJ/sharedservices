package com.wiley.gr.ace.staticcontentservices.dotcms.service;

import com.wiley.gr.ace.staticcontentservices.model.external.UIMessageCatalogDotcmsResponse;

// TODO: Auto-generated Javadoc
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
}
