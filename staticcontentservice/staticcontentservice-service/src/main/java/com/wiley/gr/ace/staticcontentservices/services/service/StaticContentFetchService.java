package com.wiley.gr.ace.staticcontentservices.services.service;

import com.wiley.gr.ace.staticcontentservices.model.UIMessageContent;

// TODO: Auto-generated Javadoc
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
}
