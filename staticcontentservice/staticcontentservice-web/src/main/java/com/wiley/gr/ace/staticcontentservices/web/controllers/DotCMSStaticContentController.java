package com.wiley.gr.ace.staticcontentservices.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.wiley.gr.ace.staticcontentservices.model.ErrorPOJO;
import com.wiley.gr.ace.staticcontentservices.model.Service;
import com.wiley.gr.ace.staticcontentservices.model.UIMessageContent;
import com.wiley.gr.ace.staticcontentservices.services.service.StaticContentFetchService;

// TODO: Auto-generated Javadoc
/**
 * The Class DotCMSStaticContentController.
 */
@RestController
@RequestMapping("/staticcontent/get")
public class DotCMSStaticContentController {

	/** The static content fetch service. */
	@Autowired(required = true)
	private StaticContentFetchService staticContentFetchService;

	/**
	 * Gets the ui message catalog.
	 *
	 * @param pageName the page name
	 * @param locale the locale
	 * @return the ui message catalog
	 */
	@RequestMapping(value = "/uimessage/{pageName}/{locale}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Service getUiMessageCatalog(
			@PathVariable("pageName") String pageName,
			@PathVariable("locale") String locale) {
		UIMessageContent uiMessageContent = null;
		Service service = new Service();

		try {
			uiMessageContent = staticContentFetchService.getUiMessageContent(
					pageName, locale);
			if (!StringUtils.isEmpty(uiMessageContent)) {
				service.setStatus("SUCCESS");
				service.setPayload(uiMessageContent);
			} else {
				service.setStatus("FAILURE");
			}
		} catch (Exception e) {
			service.setStatus("ERROR");
			ErrorPOJO err = new ErrorPOJO();
			err.setCode(209);
			err.setMessage("Fetch failed");
			service.setError(err);
		}

		return service;
	}
}
