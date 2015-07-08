package com.wiley.gr.ace.staticcontentservices.services.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.wiley.gr.ace.staticcontentservices.dotcms.service.DotCMSDataService;
import com.wiley.gr.ace.staticcontentservices.model.UIMessageContent;
import com.wiley.gr.ace.staticcontentservices.model.external.UIMessageCatalog;
import com.wiley.gr.ace.staticcontentservices.model.external.UIMessageCatalogDotcmsResponse;
import com.wiley.gr.ace.staticcontentservices.services.service.StaticContentFetchService;

// TODO: Auto-generated Javadoc
/**
 * The Class StaticContentFetchServiceImpl.
 */
public class StaticContentFetchServiceImpl implements StaticContentFetchService {

	/** The dot cms data service. */
	@Autowired(required = true)
	private DotCMSDataService dotCMSDataService;

	/* (non-Javadoc)
	 * @see com.wiley.gr.ace.staticcontentservices.services.service.StaticContentFetchService#getUiMessageContent(java.lang.String, java.lang.String)
	 */
	@Override
	public UIMessageContent getUiMessageContent(String pageName, String locale)
			throws Exception {

		UIMessageCatalogDotcmsResponse uiMessageCatalogDotcmsResponse = dotCMSDataService
				.getUiMessageCatalog(pageName, locale);
		UIMessageContent uiMessageContent = null;
		if (!StringUtils.isEmpty(uiMessageCatalogDotcmsResponse)) {
			uiMessageContent = new UIMessageContent();
			UIMessageCatalog uiMessageCatalog = uiMessageCatalogDotcmsResponse
					.getContentlets().get(0);
			uiMessageContent.setErrorMessages(uiMessageCatalog
					.getErrorMessagesOption2());
			uiMessageContent.setInlineHelp(uiMessageCatalog
					.getInlineHelpOption2());
			uiMessageContent.setLocale(locale);
			uiMessageContent.setPageName(pageName);
			uiMessageContent.setUiLabelMessages(uiMessageCatalog
					.getUiLabelMessages());
		}

		return uiMessageContent;
	}
}
