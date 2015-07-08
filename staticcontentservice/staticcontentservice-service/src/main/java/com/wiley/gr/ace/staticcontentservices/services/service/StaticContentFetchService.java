package com.wiley.gr.ace.staticcontentservices.services.service;

import com.wiley.gr.ace.staticcontentservices.model.UIMessageContent;

public interface StaticContentFetchService {

	UIMessageContent getUiMessageContent(String pageName, String locale)
			throws Exception;
}
