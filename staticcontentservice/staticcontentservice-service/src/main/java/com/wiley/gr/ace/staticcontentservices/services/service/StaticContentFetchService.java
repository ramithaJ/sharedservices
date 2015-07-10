package com.wiley.gr.ace.staticcontentservices.services.service;

import com.wiley.gr.ace.staticcontentservices.model.ConfirmationContent;
import com.wiley.gr.ace.staticcontentservices.model.UIMessageContent;
import com.wiley.gr.ace.staticcontentservices.model.StatusContent;


/**
 * The Interface StaticContentFetchService.
 */
public interface StaticContentFetchService {

	/**
	 * Gets the ui message content.
	 *
	 * @param pageName the page name
	 * @param locale the locale
	 * @return the ui message content
	 * @throws Exception the exception
	 */
	UIMessageContent getUiMessageContent(String pageName, String locale)
			throws Exception;
    
    /**
     * Gets the confirmation message content.
     *
     * @param moduleName the module name
     * @param locale the locale
     * @return the confirmation message content
     * @throws Exception the exception
     */
    ConfirmationContent getConfirmationMessageContent(String moduleName, String locale)
            throws Exception;
	
	/**
	 * Gets the status content.
	 *
	 * @param statusMessageType the status message type
	 * @param locale the locale
	 * @return the status content
	 * @throws Exception the exception
	 */
	StatusContent getStatusContent(String statusMessageType, String locale)
            throws Exception;
    
}
