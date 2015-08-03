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

/**
 * The Class StaticContentFetchServiceImpl.
 */
public class StaticContentFetchServiceImpl implements StaticContentFetchService {

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
    public UIMessageContent getUiMessageContent(String pageName, String locale,
            String fetchServerMessages) throws Exception {

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
            String contentTitle, String moduleName, String locale)
            throws Exception {

        ConfirmationCatalogDotcmsResponse confirmationCatalogDotcmsResponse = dotCMSDataService
                .getConfirmationCatalog(contentTitle, moduleName, locale);
        ConfirmationContent confirmationContent = null;
            if (!StringUtils.isEmpty(confirmationCatalogDotcmsResponse)) {
            confirmationContent = new ConfirmationContent();
            
      
            ConfirmationCatalog confirmationCatalog = confirmationCatalogDotcmsResponse
                    .getContentlets().get(0);
            confirmationContent.setConfirmationMessages(confirmationCatalog
                    .getConfirmationMessages());
            confirmationContent.setContentTiltle(contentTitle);
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
    public StatusContent getStatusContent(String contentTitle,
            String statusMessageType, String locale) throws Exception {

        StatusCatalogDotcmsResponse statusCatalogDotcmsResponse = dotCMSDataService
                .getStatusCatalog(contentTitle, statusMessageType, locale);

        
        StatusContent statusContent = null;
        if (!StringUtils.isEmpty(statusCatalogDotcmsResponse)) {
            statusContent = new StatusContent();
     
            StatusCatalog statusCatalog = statusCatalogDotcmsResponse
                    .getContentlets().get(0);
         
            statusContent.setStatusMessages(statusCatalog.getStatusMessages());
            statusContent.setContentTitle(contentTitle);
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
        System.out.println("You were first first here");
        EmailCatalogDotcmsResponse emailCatalogDotcmsResponse = dotCMSDataService
                .getEmailCatalog(contentTitle);
   
        System.out.println("You were first here");
        EmailContent emailContent = null;
        if (!StringUtils.isEmpty(emailCatalogDotcmsResponse)) {
            emailContent = new EmailContent();
         System.out.println("You were later here");
            EmailCatalog emailCatalog = emailCatalogDotcmsResponse
                    .getContentlets().get(0);
           System.out.println("You were then here");
            emailContent.setContentTitle(emailCatalog.getContentTitle());

            emailContent.setContentTitle(contentTitle);

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
    public StaticContent getStaticContent(String contentUniqueKey,
            String pageName, String locale) throws Exception {

        StaticCatalogDotcmsResponse staticCatalogDotcmsResponse = dotCMSDataService
                .getStaticCatalog(contentUniqueKey, pageName, locale);
        StaticContent staticContent = null;
        if (!StringUtils.isEmpty(staticCatalogDotcmsResponse)) {
            staticContent = new StaticContent();
            StaticCatalog staticCatalog = staticCatalogDotcmsResponse
                    .getContentlets().get(0);
            staticContent.setContentTitle(staticCatalog.getContentTitle());

            staticContent.setContentTitle(contentUniqueKey);
            staticContent.setPageName(pageName);
            staticContent.setLocale(locale);
            staticContent.setAdBlockBody(staticCatalog.getAdBlockContent());

        }

        return staticContent;
    }
}
