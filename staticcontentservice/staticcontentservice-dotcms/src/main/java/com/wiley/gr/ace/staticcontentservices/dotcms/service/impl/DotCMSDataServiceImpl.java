package com.wiley.gr.ace.staticcontentservices.dotcms.service.impl;

import java.net.URI;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.yaml.snakeyaml.util.UriEncoder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wiley.gr.ace.staticcontentservices.dotcms.service.DotCMSDataService;
import com.wiley.gr.ace.staticcontentservices.model.external.UIMessageCatalogDotcmsResponse;

// TODO: Auto-generated Javadoc
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

		UIMessageCatalogDotcmsResponse uiMessageCatalogDotcmsResponse = new UIMessageCatalogDotcmsResponse();
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
		uiMessageCatalogDotcmsResponse = new ObjectMapper().readValue(
				uiMessageCatalogDotcmsResponseJsonString,
				UIMessageCatalogDotcmsResponse.class);

		return uiMessageCatalogDotcmsResponse;
	}

}
