package com.wiley.gr.ace.staticcontentservices.services.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.wiley.gr.ace.staticcontentservices.dotcms.service.DotCMSDataService;
import com.wiley.gr.ace.staticcontentservices.model.ConfirmationContent;
import com.wiley.gr.ace.staticcontentservices.model.EmailContent;
import com.wiley.gr.ace.staticcontentservices.model.ServerContent;
import com.wiley.gr.ace.staticcontentservices.model.StaticContent;
import com.wiley.gr.ace.staticcontentservices.model.StatusContent;
import com.wiley.gr.ace.staticcontentservices.model.UIMessageContent;
import com.wiley.gr.ace.staticcontentservices.model.external.ConfirmationCatalog;
import com.wiley.gr.ace.staticcontentservices.model.external.ConfirmationCatalogDotcmsResponse;
import com.wiley.gr.ace.staticcontentservices.model.external.EmailCatalog;
import com.wiley.gr.ace.staticcontentservices.model.external.EmailCatalogDotcmsResponse;
import com.wiley.gr.ace.staticcontentservices.model.external.ServerCatalog;
import com.wiley.gr.ace.staticcontentservices.model.external.ServerCatalogDotcmsResponse;
import com.wiley.gr.ace.staticcontentservices.model.external.StaticCatalog;
import com.wiley.gr.ace.staticcontentservices.model.external.StaticCatalogDotcmsResponse;
import com.wiley.gr.ace.staticcontentservices.model.external.StatusCatalog;
import com.wiley.gr.ace.staticcontentservices.model.external.StatusCatalogDotcmsResponse;
import com.wiley.gr.ace.staticcontentservices.model.external.UIMessageCatalog;
import com.wiley.gr.ace.staticcontentservices.model.external.UIMessageCatalogDotcmsResponse;
import com.wiley.gr.ace.staticcontentservices.services.service.StaticContentFetchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class StaticContentFetchServiceImpl.
 */
public class StaticContentFetchServiceImpl implements StaticContentFetchService {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(StaticContentFetchService.class);

	/** The dot cms data service. */
	@Autowired(required = true)
	private DotCMSDataService dotCMSDataService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wiley.gr.ace.staticcontentservices.services.service.
	 * StaticContentFetchService#getUiMessageContent(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public UIMessageContent getUiMessageContent(String uniqueKey, String pageName, String locale,
			String fetchServerMessages) throws Exception {
	    LOGGER.info("inside getUiMessageContent method of StaticContentFetchService");
		UIMessageCatalogDotcmsResponse uiMessageCatalogDotcmsResponse = dotCMSDataService
				.getUiMessageCatalog(uniqueKey, pageName, locale);
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
			uiMessageCatalog.setUniqueKey(uniqueKey);
			uiMessageContent.setUiLabelMessages(uiMessageCatalog
					.getUiLabelMessages());

			if ("true".equalsIgnoreCase(fetchServerMessages)) {

				ServerCatalogDotcmsResponse serverCatalogDotcmsResponse = dotCMSDataService
						.getRelatedServerApplicationMessage(uiMessageCatalog
								.getIdentifier());
				ServerCatalog serverCatalog = serverCatalogDotcmsResponse
						.getContentlets().get(0);
				if (!StringUtils.isEmpty(serverCatalog)) {
					ServerContent serverContent = new ServerContent();
					serverContent.setContentTitle(serverCatalog
							.getContentTitle());
					serverContent.setLocale(serverCatalog.getLocale());
					serverContent.setModuleName(serverCatalog.getModuleName());
					serverContent.setServerMessages(serverCatalog
							.getServerMessages());
					uiMessageContent.setServerContent(serverContent);
				}
			}

		}

		return uiMessageContent;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wiley.gr.ace.staticcontentservices.services.service.
	 * StaticContentFetchService#getConfirmationMessageContent(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public ConfirmationContent getConfirmationMessageContent(
			String moduleName, String locale)
			throws Exception {
	    
	    LOGGER.info("inside getUiMessageContent method of StaticContentFetchService");
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wiley.gr.ace.staticcontentservices.services.service.
	 * StaticContentFetchService#getStatusContent(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public StatusContent getStatusContent(String statusMessageType, String locale) throws Exception {
	    
	    LOGGER.info("inside getUiMessageContent method of StaticContentFetchService");
		StatusCatalogDotcmsResponse statusCatalogDotcmsResponse = dotCMSDataService
				.getStatusCatalog(statusMessageType, locale);

		StatusContent statusContent = null;
		if (!StringUtils.isEmpty(statusCatalogDotcmsResponse)) {
			statusContent = new StatusContent();

			StatusCatalog statusCatalog = statusCatalogDotcmsResponse
					.getContentlets().get(0);
			statusContent.setActionLables(statusCatalog.getActionLables());
			statusContent.setStatusMessages(statusCatalog.getStatusMessages());
			statusContent.setLocale(locale);
			statusContent.setStatusMessageType(statusMessageType);

		}

		return statusContent;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wiley.gr.ace.staticcontentservices.services.service.
	 * StaticContentFetchService#getEmailContent(java.lang.String)
	 */
	@Override
	public EmailContent getEmailContent(String contentTitle) throws Exception {
		
	    LOGGER.info("inside getUiMessageContent method of StaticContentFetchService");
	    EmailCatalogDotcmsResponse emailCatalogDotcmsResponse = dotCMSDataService
				.getEmailCatalog(contentTitle);

		EmailContent emailContent = null;
		if (!StringUtils.isEmpty(emailCatalogDotcmsResponse)) {
			emailContent = new EmailContent();
			System.out.println("You were also here");
			EmailCatalog emailCatalog = emailCatalogDotcmsResponse
					.getContentlets().get(0);
			
			emailContent.setContentTitle(contentTitle);
			emailContent.setSurveyLink(emailCatalog.getSurveyLink());
		}

		return emailContent;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wiley.gr.ace.staticcontentservices.services.service.
	 * StaticContentFetchService#getStaticContent(java.lang.String,
	 * java.lang.String, java.lang.String)
	 */
	@Override
	public StaticContent getStaticContent(String pageName, String contentUniqueKey, String locale) throws Exception {

	    LOGGER.info("inside getStaticContent method of StaticContentFetchService");
		StaticCatalogDotcmsResponse staticCatalogDotcmsResponse = dotCMSDataService
				.getStaticCatalog(pageName,contentUniqueKey,locale);
		StaticContent staticContent = null;
		if (!StringUtils.isEmpty(staticCatalogDotcmsResponse)) {
			staticContent = new StaticContent();
			StaticCatalog staticCatalog = staticCatalogDotcmsResponse
					.getContentlets().get(0);
			staticContent.setPageName(pageName);
			staticContent.setContentTitle(contentUniqueKey);
			staticContent.setLocale(locale);
			staticContent.setAdBlockBody(staticCatalog.getAdBlockContent());

		}
		
		return staticContent;
	}
}
