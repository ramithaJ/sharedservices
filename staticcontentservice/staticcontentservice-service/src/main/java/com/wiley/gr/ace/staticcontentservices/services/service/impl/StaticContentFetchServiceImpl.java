package com.wiley.gr.ace.staticcontentservices.services.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.wiley.gr.ace.staticcontentservices.dotcms.service.DotCMSDataService;
import com.wiley.gr.ace.staticcontentservices.model.ConfirmationContent;
import com.wiley.gr.ace.staticcontentservices.model.StatusContent;
import com.wiley.gr.ace.staticcontentservices.model.UIMessageContent;
import com.wiley.gr.ace.staticcontentservices.model.external.StatusCatalog;
import com.wiley.gr.ace.staticcontentservices.model.external.StatusCatalogDotcmsResponse;
import com.wiley.gr.ace.staticcontentservices.model.external.ConfirmationCatalog;
import com.wiley.gr.ace.staticcontentservices.model.external.ConfirmationCatalogDotcmsResponse;
import com.wiley.gr.ace.staticcontentservices.model.external.UIMessageCatalog;
import com.wiley.gr.ace.staticcontentservices.model.external.UIMessageCatalogDotcmsResponse;
import com.wiley.gr.ace.staticcontentservices.services.service.StaticContentFetchService;


//import com.wiley.gr.ace.staticcontentservices.model.ConfirmationContent;
//import com.wiley.gr.ace.staticcontentservices.model.external.ConfirmationCatalog;
//import com.wiley.gr.ace.staticcontentservices.model.external.ConfirmationCatalogDotcmsResponse;
//import com.wiley.gr.ace.staticcontentservices.services.service.ConfirmationFetchService;


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
	
	 /* (non-Javadoc)
 	 * @see com.wiley.gr.ace.staticcontentservices.services.service.StaticContentFetchService#getConfirmationMessageContent(java.lang.String, java.lang.String)
 	 */
 	@Override
	    public ConfirmationContent getConfirmationMessageContent(String moduleName, String locale)
	            throws Exception {

	        ConfirmationCatalogDotcmsResponse confirmationCatalogDotcmsResponse = dotCMSDataService
	                .getConfirmationCatalog(moduleName, locale);
	        ConfirmationContent confirmationContent = null;
	        if (!StringUtils.isEmpty(confirmationCatalogDotcmsResponse)) {
	            confirmationContent = new ConfirmationContent();
	            ConfirmationCatalog confirmationCatalog = confirmationCatalogDotcmsResponse
	                    .getContentlets().get(0);
	            confirmationContent.setConfirmationMessages(confirmationCatalog
	                    .getConfirmationMessages());

	            confirmationContent.setLocale(locale);
	            confirmationContent.setModuleName(moduleName);

	        }

	        return confirmationContent;
	    }
	
	    /* (non-Javadoc)
    	 * @see com.wiley.gr.ace.staticcontentservices.services.service.StaticContentFetchService#getStatusContent(java.lang.String, java.lang.String)
    	 */
    	@Override
	    public StatusContent getStatusContent(String statusMessageType, String locale)
                throws Exception {

            StatusCatalogDotcmsResponse statusCatalogDotcmsResponse = dotCMSDataService
                    .getStatusCatalog(statusMessageType, locale);
            StatusContent statusContent = null;
            if (!StringUtils.isEmpty(statusCatalogDotcmsResponse)) {
                statusContent = new StatusContent();
                StatusCatalog statusCatalog = statusCatalogDotcmsResponse
                        .getContentlets().get(0);
                statusContent.setStatusMessages(statusCatalog
                        .getStatusMessages());

                statusContent.setLocale(locale);
                statusContent.setStatusMessageType(statusMessageType);

            }

            return statusContent;
        }
}
