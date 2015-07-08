package com.wiley.gr.ace.staticcontentservices.dotcms.service;

import com.wiley.gr.ace.staticcontentservices.model.external.UIMessageCatalogDotcmsResponse;

public interface DotCMSDataService {

	UIMessageCatalogDotcmsResponse getUiMessageCatalog(String pageName, String locale) throws Exception;
}
