package com.wiley.gr.ace.staticcontentservices.dotcms.service.impl;

import java.net.URI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import org.yaml.snakeyaml.util.UriEncoder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wiley.gr.ace.staticcontentservices.dotcms.service.DotCMSDataService;
import com.wiley.gr.ace.staticcontentservices.model.external.ConfirmationCatalogDotcmsResponse;
import com.wiley.gr.ace.staticcontentservices.model.external.ServerCatalogDotcmsResponse;
import com.wiley.gr.ace.staticcontentservices.model.external.StaticCatalogDotcmsResponse;
import com.wiley.gr.ace.staticcontentservices.model.external.StatusCatalogDotcmsResponse;
import com.wiley.gr.ace.staticcontentservices.model.external.UIMessageCatalogDotcmsResponse;
import com.wiley.gr.ace.staticcontentservices.model.external.EmailCatalogDotcmsResponse;

/**
 * The Class DotCMSDataServiceImpl.
 */
public class DotCMSDataServiceImpl implements DotCMSDataService {
    private static final Logger LOGGER = LoggerFactory
            .getLogger(DotCMSDataService.class);


    /** The content url. */
    @Value("${dotcms-rest.url}")
    private String contentUrl;

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.wiley.gr.ace.staticcontentservices.dotcms.service.DotCMSDataService
     * #getUiMessageCatalog(java.lang.String, java.lang.String)
     */
    @Override
    public UIMessageCatalogDotcmsResponse getUiMessageCatalog(String uniqueKey, String pageName,
            String locale) throws Exception {
        LOGGER.info("inside getUiMessageCatalog method of DotCMSDataService");
        String dotQuery = "+structureName:UiMessageCatalog +(conhost:941f9810-7fd0-49b8-83fd-dab4a90e493e conhost:SYSTEM_HOST)";
        
        if (!StringUtils.isEmpty(uniqueKey)) {
            dotQuery = dotQuery + " +UiMessageCatalog.uniqueKey:*" + uniqueKey
                    + "*";
        }        
        
        if (!StringUtils.isEmpty(pageName)) {
            dotQuery = dotQuery + " +UiMessageCatalog.pageName:*" + pageName
                    + "*";
        }

        if (!StringUtils.isEmpty(locale)) {
            dotQuery = dotQuery + " +UiMessageCatalog.locale:*" + locale + "*";
        }

        dotQuery = dotQuery + "* +languageId:1* +deleted:false +working:true";
        String dotUrl = contentUrl + dotQuery;
        String dotUrlEncoded = UriEncoder.encode(dotUrl);
        ResponseEntity<String> responseEntity = new RestTemplate()
                .getForEntity(new URI(dotUrlEncoded), String.class);
        String uiMessageCatalogDotcmsResponseJsonString = responseEntity
                .getBody();
        LOGGER.info("UI Message Catalog query executed successfully");
        return new ObjectMapper().readValue(
                uiMessageCatalogDotcmsResponseJsonString,
                UIMessageCatalogDotcmsResponse.class);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.wiley.gr.ace.staticcontentservices.dotcms.service.DotCMSDataService
     * #getConfirmationCatalog(java.lang.String, java.lang.String)
     */
    @Override
    public ConfirmationCatalogDotcmsResponse getConfirmationCatalog(
            String moduleName, String locale)
            throws Exception {
        LOGGER.info("inside getConfirmationCatalog method of DotCMSDataService");
        String dotQuery = "+structureName:ConfirmationTexts +(conhost:941f9810-7fd0-49b8-83fd-dab4a90e493e conhost:SYSTEM_HOST)";

       
        if (!StringUtils.isEmpty(moduleName)) {
            dotQuery = dotQuery + " +ConfirmationTexts.moduleName:*"
                    + moduleName + "*";
        }

        if (!StringUtils.isEmpty(locale)) {

            dotQuery = dotQuery + " +ConfirmationTexts.locale:*" + locale + "*";
        }

        dotQuery = dotQuery + "* +languageId:1* +deleted:false +working:true";
        String dotUrl = contentUrl + dotQuery;
        String dotUrlEncoded = UriEncoder.encode(dotUrl);
        ResponseEntity<String> responseEntity = new RestTemplate()
                .getForEntity(new URI(dotUrlEncoded), String.class);
        String confirmationCatalogDotcmsResponseJsonString = responseEntity
                .getBody();
        System.err.println(responseEntity.getBody());
        LOGGER.info("Confirmation Catalog query executed successfully");
        return new ObjectMapper().readValue(
                confirmationCatalogDotcmsResponseJsonString,
                ConfirmationCatalogDotcmsResponse.class);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.wiley.gr.ace.staticcontentservices.dotcms.service.DotCMSDataService
     * #getStatusCatalog(java.lang.String, java.lang.String)
     */
    @Override
    public StatusCatalogDotcmsResponse getStatusCatalog(String statusMessageType, String locale) throws Exception {
        
        LOGGER.info("inside getStatusCatalog method of DotCMSDataService");
        String dotQuery = "+structureName:StatusMessages +(conhost:941f9810-7fd0-49b8-83fd-dab4a90e493e conhost:SYSTEM_HOST)";

        if (!StringUtils.isEmpty(statusMessageType)) {
            dotQuery = dotQuery + " +StatusMessages.statusMessageType:"
                    + statusMessageType + "*";
        }

        if (!StringUtils.isEmpty(locale)) {
            dotQuery = dotQuery + " +StatusMessages.locale:*" + locale + "*";
        }

        dotQuery = dotQuery + "* +languageId:1* +deleted:false +working:true";

        String dotUrl = contentUrl + dotQuery;
        String dotUrlEncoded = UriEncoder.encode(dotUrl);
        ResponseEntity<String> responseEntity = new RestTemplate()
                .getForEntity(new URI(dotUrlEncoded), String.class);
        String statusCatalogDotcmsResponseJsonString = responseEntity.getBody();
        LOGGER.info("Status Catalog query executed successfully");
        return new ObjectMapper().readValue(
                statusCatalogDotcmsResponseJsonString,
                StatusCatalogDotcmsResponse.class);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.wiley.gr.ace.staticcontentservices.dotcms.service.DotCMSDataService
     * #getEmailCatalog(java.lang.String)
     */
    @Override
    public EmailCatalogDotcmsResponse getEmailCatalog(String contentTitle)
            throws Exception {
        
        LOGGER.info("inside getEmailCatalog method of DotCMSDataService");
        String dotQuery = "+structureName:EmailSurveyLink +(conhost:941f9810-7fd0-49b8-83fd-dab4a90e493e conhost:SYSTEM_HOST)";
        if (!StringUtils.isEmpty(contentTitle)) {
            dotQuery = dotQuery + " +EmailSurveyLink.contentTitle:*"
                    + contentTitle + "*";
        }

        dotQuery = dotQuery + "* +languageId:1* +deleted:false +working:true";
        String dotUrl = contentUrl + dotQuery;
        String dotUrlEncoded = UriEncoder.encode(dotUrl);
        ResponseEntity<String> responseEntity = new RestTemplate()
                .getForEntity(new URI(dotUrlEncoded), String.class);
        String emailCatalogDotcmsResponseJsonString = responseEntity.getBody();
        LOGGER.info("Email Catalog query executed successfully");
        return new ObjectMapper().readValue(
                emailCatalogDotcmsResponseJsonString,
                EmailCatalogDotcmsResponse.class);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.wiley.gr.ace.staticcontentservices.dotcms.service.DotCMSDataService
     * #getStaticCatalog(java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    public StaticCatalogDotcmsResponse getStaticCatalog(String pageName, String contentUniqueKey, String locale) throws Exception {
        
        LOGGER.info("inside getStaticCatalog method of DotCMSDataService");
        String dotQuery = "+structureName:StaticAdBlock +(conhost:941f9810-7fd0-49b8-83fd-dab4a90e493e conhost:SYSTEM_HOST)";

        if (!StringUtils.isEmpty(pageName)) {
            dotQuery = dotQuery + " +StaticAdBlock.pageName:*"
                    + pageName + "*";
        }

        if (!StringUtils.isEmpty(contentUniqueKey)) {
            dotQuery = dotQuery + " +StaticAdBlock.contentUniqueKey:*"
                    + contentUniqueKey + "*";
        }
        
        if (!StringUtils.isEmpty(locale)) {
            dotQuery = dotQuery + " +StaticAdBlock.locale:*"
                    + locale + "*";
        }
        
        dotQuery = dotQuery + " +languageId:1* +deleted:false +working:true";

        String dotUrl = contentUrl + dotQuery;
        String dotUrlEncoded = UriEncoder.encode(dotUrl);
        ResponseEntity<String> responseEntity = new RestTemplate()
                .getForEntity(new URI(dotUrlEncoded), String.class);
        String staticCatalogDotcmsResponseJsonString = responseEntity.getBody();
        LOGGER.info("Static Catalog query executed successfully");
        return new ObjectMapper().readValue(
                staticCatalogDotcmsResponseJsonString,
                StaticCatalogDotcmsResponse.class);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.wiley.gr.ace.staticcontentservices.dotcms.service.DotCMSDataService
     * #getRelatedServerApplicationMessage(java.lang.String)
     */
    @Override
    public ServerCatalogDotcmsResponse getRelatedServerApplicationMessage(
            String contentIdentifier) throws Exception {

        LOGGER.info("inside getRelatedServerApplicationMessage method of DotCMSDataService");
        String dotQuery = "+structureName:ServerApplicationMessages +Parent_UiMessageCatalog-Child_ServerApplicationMessages:"
                + contentIdentifier;

        String dotUrl = contentUrl + dotQuery;
        String dotUrlEncoded = UriEncoder.encode(dotUrl);
        ResponseEntity<String> responseEntity = new RestTemplate()
                .getForEntity(new URI(dotUrlEncoded), String.class);
        String staticCatalogDotcmsResponseJsonString = responseEntity.getBody();
        LOGGER.info("Server Catalog query executed successfully");
        return new ObjectMapper().readValue(
                staticCatalogDotcmsResponseJsonString,
                ServerCatalogDotcmsResponse.class);
    }

}
