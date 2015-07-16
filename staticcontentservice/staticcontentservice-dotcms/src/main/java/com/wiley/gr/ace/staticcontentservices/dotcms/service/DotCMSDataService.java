package com.wiley.gr.ace.staticcontentservices.dotcms.service;

import com.wiley.gr.ace.staticcontentservices.model.external.ConfirmationCatalogDotcmsResponse;
import com.wiley.gr.ace.staticcontentservices.model.external.StaticCatalogDotcmsResponse;
import com.wiley.gr.ace.staticcontentservices.model.external.StatusCatalogDotcmsResponse;
import com.wiley.gr.ace.staticcontentservices.model.external.UIMessageCatalogDotcmsResponse;
import com.wiley.gr.ace.staticcontentservices.model.external.EmailCatalogDotcmsResponse;



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
	 * @param contentTiltle the content title
	 * @param moduleName the module name
	 * @param locale the locale
	 * @return the confirmation catalog
	 * @throws Exception the exception
	 */
	ConfirmationCatalogDotcmsResponse getConfirmationCatalog(String contentTitle, String moduleName, String locale) throws Exception;

	/**
	 * Gets the status catalog.
	 *
	 * @param contentTitle the content title
	 * @param statusMessageType the status message type
	 * @param locale the locale
	 * @return the status catalog
	 * @throws Exception the exception
	 */
	StatusCatalogDotcmsResponse getStatusCatalog(String contentTitle, String statusMessageType, String locale) throws Exception;
	
	/**
	 * Gets the email catalog.
	 *
	 * @param contentTitle the content title
	 * @return the email catalog
	 * @throws Exception the exception
	 */
	EmailCatalogDotcmsResponse getEmailCatalog(String contentTitle) throws Exception;
	
	/**
	 * Gets the static catalog.
	 *
	 * @param contentTitle the content title
	 * @param pageName the page name
	 * @param locale the locale
	 * @return the static catalog
	 * @throws Exception the exception
	 */
	StaticCatalogDotcmsResponse getStaticCatalog(String contentUniqueKey, String pageName,  String locale) throws Exception;
}
