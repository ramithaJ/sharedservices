package com.wiley.gr.ace.staticcontentservices.dotcms.service.impl;

import java.net.URI;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.yaml.snakeyaml.util.UriEncoder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wiley.gr.ace.staticcontentservices.dotcms.service.DotCMSDataService;
import com.wiley.gr.ace.staticcontentservices.model.external.ConfirmationCatalogDotcmsResponse;
import com.wiley.gr.ace.staticcontentservices.model.external.StaticCatalogDotcmsResponse;
import com.wiley.gr.ace.staticcontentservices.model.external.StatusCatalogDotcmsResponse;
import com.wiley.gr.ace.staticcontentservices.model.external.UIMessageCatalogDotcmsResponse;
import com.wiley.gr.ace.staticcontentservices.model.external.EmailCatalogDotcmsResponse;



/**
 * The Class DotCMSDataServiceImpl.
 */
public class DotCMSDataServiceImpl implements DotCMSDataService {

	/** The content url. */
	@Value("${dotcms-rest.url}")
	private String contentUrl;

	/* (non-Javadoc)
	 * @see com.wiley.gr.ace.staticcontentservices.dotcms.service.DotCMSDataService#getUiMessageCatalog(java.lang.String, java.lang.String)
	 */
	@Override
	public UIMessageCatalogDotcmsResponse getUiMessageCatalog(String pageName,
			String locale) throws Exception {

		String dotQuery = "+structureName:UiMessageCatalog +(conhost:941f9810-7fd0-49b8-83fd-dab4a90e493e conhost:SYSTEM_HOST) +UiMessageCatalog.pageName:*"
				+ pageName
				+ "* +UiMessageCatalog.locale:*"
				+ locale
				+ "* +languageId:1* +deleted:false +working:true";
		String dotUrl = contentUrl + dotQuery;
		String dotUrlEncoded = UriEncoder.encode(dotUrl);
		ResponseEntity<String> responseEntity = new RestTemplate()
				.getForEntity(new URI(dotUrlEncoded), String.class);
		String uiMessageCatalogDotcmsResponseJsonString = responseEntity
				.getBody();

		return new ObjectMapper().readValue(
                uiMessageCatalogDotcmsResponseJsonString,
                UIMessageCatalogDotcmsResponse.class);
	}

	
	/* (non-Javadoc)
	 * @see com.wiley.gr.ace.staticcontentservices.dotcms.service.DotCMSDataService#getConfirmationCatalog(java.lang.String, java.lang.String)
	 */
	@Override
    public ConfirmationCatalogDotcmsResponse getConfirmationCatalog(String contentTiltle,String moduleName,
            String locale) throws Exception {

      
        String dotQuery = "+structureName:ConfirmationTexts +(conhost:23836f6d-6a92-446f-b147-29e4724eedd8 conhost:SYSTEM_HOST) +ConfirmationTexts.contentTiltle:*"
                + contentTiltle
                +"+structureName:ConfirmationTexts +(conhost:23836f6d-6a92-446f-b147-29e4724eedd8 conhost:SYSTEM_HOST) +ConfirmationTexts.moduleName:*"
                + moduleName
                + "* +ConfirmationTexts.locale:*"
                + locale
                + "* +languageId:1* +deleted:false +working:true";
        String dotUrl = contentUrl + dotQuery;
        String dotUrlEncoded = UriEncoder.encode(dotUrl);
        ResponseEntity<String> responseEntity = new RestTemplate()
                .getForEntity(new URI(dotUrlEncoded), String.class);
        String confirmationCatalogDotcmsResponseJsonString = responseEntity
                .getBody();

        return new ObjectMapper().readValue(
                confirmationCatalogDotcmsResponseJsonString,
                ConfirmationCatalogDotcmsResponse.class);
    }
	
	/* (non-Javadoc)
	 * @see com.wiley.gr.ace.staticcontentservices.dotcms.service.DotCMSDataService#getStatusCatalog(java.lang.String, java.lang.String)
	 */
	@Override
    public StatusCatalogDotcmsResponse getStatusCatalog(String contentTitle, String statusMessageType,
            String locale) throws Exception {

       
        String dotQuery = "+structureName:StatusMessages +(conhost:23836f6d-6a92-446f-b147-29e4724eedd8 conhost:SYSTEM_HOST) +StatusMessages.contentTitle:*"
                + contentTitle
                +"*+StatusMessages.statusMessageType:*"
                + statusMessageType
                + "* +StatusMessages.locale:*"
                + locale
                + "* +languageId:1* +deleted:false +working:true";
        String dotUrl = contentUrl + dotQuery;
        String dotUrlEncoded = UriEncoder.encode(dotUrl);
        ResponseEntity<String> responseEntity = new RestTemplate()
                .getForEntity(new URI(dotUrlEncoded), String.class);
        String statusCatalogDotcmsResponseJsonString = responseEntity
                .getBody();

        return new ObjectMapper().readValue(
                statusCatalogDotcmsResponseJsonString,
                StatusCatalogDotcmsResponse.class);
    }
	
	/* (non-Javadoc)
	 * @see com.wiley.gr.ace.staticcontentservices.dotcms.service.DotCMSDataService#getEmailCatalog(java.lang.String)
	 */
	@Override
    public EmailCatalogDotcmsResponse getEmailCatalog(String contentTitle) throws Exception {

       
        String dotQuery = "+structureName:EmailSurveyLink +(conhost:23836f6d-6a92-446f-b147-29e4724eedd8 conhost:SYSTEM_HOST) +EmailSurveyLink.contentTitle:*"
                + contentTitle
                + "* +languageId:1* +deleted:false +working:true";
        String dotUrl = contentUrl + dotQuery;
        String dotUrlEncoded = UriEncoder.encode(dotUrl);
        ResponseEntity<String> responseEntity = new RestTemplate()
                .getForEntity(new URI(dotUrlEncoded), String.class);
        String emailCatalogDotcmsResponseJsonString = responseEntity
                .getBody();

        return new ObjectMapper().readValue(
                emailCatalogDotcmsResponseJsonString,
                EmailCatalogDotcmsResponse.class);
    }
	
	
	
	/* (non-Javadoc)
	 * @see com.wiley.gr.ace.staticcontentservices.dotcms.service.DotCMSDataService#getStaticCatalog(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
    public StaticCatalogDotcmsResponse getStaticCatalog(String contentTitle, String pageName,  String locale) throws Exception {

       
        String dotQuery = "+structureName:StaticAdBlock +(conhost:23836f6d-6a92-446f-b147-29e4724eedd8 conhost:SYSTEM_HOST) +StaticAdBlock.contentTitle:*"
                + contentTitle
                +"* +StaticAdBlock.pageName:*"
                +pageName
                +"* +StaticAdBlock.locale:*"
                +locale
                + "* +languageId:1* +deleted:false +working:true";
        String dotUrl = contentUrl + dotQuery;
        String dotUrlEncoded = UriEncoder.encode(dotUrl);
        ResponseEntity<String> responseEntity = new RestTemplate()
                .getForEntity(new URI(dotUrlEncoded), String.class);
        String staticCatalogDotcmsResponseJsonString = responseEntity
                .getBody();

        return new ObjectMapper().readValue(
                staticCatalogDotcmsResponseJsonString,
                StaticCatalogDotcmsResponse.class);
    }
}
