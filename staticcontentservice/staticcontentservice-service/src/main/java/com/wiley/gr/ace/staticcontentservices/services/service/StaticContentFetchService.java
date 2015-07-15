package com.wiley.gr.ace.staticcontentservices.services.service;

import com.wiley.gr.ace.staticcontentservices.model.ConfirmationContent;
import com.wiley.gr.ace.staticcontentservices.model.StaticContent;
import com.wiley.gr.ace.staticcontentservices.model.UIMessageContent;
import com.wiley.gr.ace.staticcontentservices.model.StatusContent;
import com.wiley.gr.ace.staticcontentservices.model.EmailContent;


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
     * @param contentTiltle the content tiltle
     * @param moduleName the module name
     * @param locale the locale
     * @return the confirmation message content
     * @throws Exception the exception
     */
    ConfirmationContent getConfirmationMessageContent(String contentTiltle, String moduleName, String locale)
            throws Exception;
	
	/**
	 * Gets the status content.
	 *
	 * @param contentTitle the content title
	 * @param statusMessageType the status message type
	 * @param locale the locale
	 * @return the status content
	 * @throws Exception the exception
	 */
	StatusContent getStatusContent(String contentTitle, String statusMessageType, String locale)
            throws Exception;
	
	/**
	 * Gets the email content.
	 *
	 * @param contentTitle the content title
	 * @return the email content
	 * @throws Exception the exception
	 */
	EmailContent getEmailContent(String contentTitle)
            throws Exception;
    
	
	/**
	 * Gets the static content.
	 *
	 * @param contentTitle the content title
	 * @param pageName the page name
	 * @param locale the locale
	 * @return the static content
	 * @throws Exception the exception
	 */
	StaticContent getStaticContent (String contentTitle, String pageName,  String locale) throws Exception;
}
