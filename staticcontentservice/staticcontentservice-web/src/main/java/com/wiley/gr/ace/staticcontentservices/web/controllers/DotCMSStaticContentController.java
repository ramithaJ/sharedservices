package com.wiley.gr.ace.staticcontentservices.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
//import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.wiley.gr.ace.staticcontentservices.model.ConfirmationContent;
import com.wiley.gr.ace.staticcontentservices.model.EmailContent;
import com.wiley.gr.ace.staticcontentservices.model.ErrorPOJO;
import com.wiley.gr.ace.staticcontentservices.model.Service;
import com.wiley.gr.ace.staticcontentservices.model.StaticContent;
import com.wiley.gr.ace.staticcontentservices.model.StatusContent;
import com.wiley.gr.ace.staticcontentservices.model.UIMessageContent;
import com.wiley.gr.ace.staticcontentservices.services.service.StaticContentFetchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class DotCMSStaticContentController.
 */
@RestController
@RequestMapping("/staticcontent/get")
public class DotCMSStaticContentController {
    
    private static final Logger LOGGER = LoggerFactory
            .getLogger(DotCMSStaticContentController.class);

	/** The static content fetch service. */
	@Autowired(required = true)
	private StaticContentFetchService staticContentFetchService;

	/**
	 * Gets the ui message catalog.
	 *
	 * @param pageName
	 *            the page name
	 * @param locale
	 *            the locale
	 * @return the ui message catalog
	 */
	@RequestMapping(value = "/uimessage", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Service getUiMessageCatalog(
	        @RequestParam("uniqueKey") String uniqueKey,
	        @RequestParam("pageName") String pageName,
			@RequestParam("locale") String locale,
			@RequestParam("fetchServerMessages") String fetchServerMessages) {
	    LOGGER.info("inside uimessage method of DotCMSStaticContentController");
	    UIMessageContent uiMessageContent = null;
		Service service = new Service();

		try {
		    
		    uiMessageContent = staticContentFetchService.getUiMessageContent(uniqueKey, pageName, locale, fetchServerMessages);
			if (!StringUtils.isEmpty(uiMessageContent)) {
			    LOGGER.info("UI Content Data is Found");
			    service.setStatus("SUCCESS");
				service.setPayload(uiMessageContent);
			} else {
			    LOGGER.info("UI Content Data is not Found");
				service.setStatus("FAILURE");
			}
		} catch (Exception e) {
		    LOGGER.error("Print Stack Trace- ", e);
			e.printStackTrace();
			service.setStatus("ERROR");
			ErrorPOJO err = new ErrorPOJO();
			err.setCode(209);
			err.setMessage("Fetch failed");
			service.setError(err);
		}

		return service;
	}

	/**
	 * Gets the confirmation catalog.
	 *
	 * @param contentTiltle
	 *            the content title
	 * @param moduleName
	 *            the module name
	 * @param locale
	 *            the locale
	 * @return the confirmation catalog
	 */
	@RequestMapping(value = "/confirmation", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Service getConfirmationCatalog(
			@RequestParam("moduleName") String moduleName,
			@RequestParam("locale") String locale) {
	    LOGGER.info("inside confirmation method of DotCMSStaticContentController");
	    ConfirmationContent confirmationContent = null;
		Service service = new Service();

		try {
			confirmationContent = staticContentFetchService
					.getConfirmationMessageContent(moduleName,
							locale);
			if (!StringUtils.isEmpty(confirmationContent)) {
			    LOGGER.info("Confirmation Content Data is Found");
				service.setStatus("SUCCESS");
				service.setPayload(confirmationContent);
			} else {
			    LOGGER.info("Confirmation Content Data is not Found");
				service.setStatus("FAILURE");
			}
		} catch (Exception e) {
		    LOGGER.error("Print Stack Trace- ", e);
		    e.printStackTrace();
			service.setStatus("ERROR");
			ErrorPOJO err = new ErrorPOJO();
			err.setCode(209);
			err.setMessage("Fetch failed");
			service.setError(err);
		}

		return service;
	}

	/**
	 * Gets the status content.
	 *
	 * @param contentTitle
	 *            the content title
	 * @param statusMessageType
	 *            the status message type
	 * @param locale
	 *            the locale
	 * @return the status content
	 */
	@RequestMapping(value = "/status", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Service getStatusContent(
			@RequestParam("statusMessageType") String statusMessageType,
			@RequestParam("locale") String locale) {
	    LOGGER.info("inside status method of DotCMSStaticContentController");
	    StatusContent statusContent = null;
		Service service = new Service();

		try {
			statusContent = staticContentFetchService.getStatusContent(
					statusMessageType, locale);
			if (!StringUtils.isEmpty(statusContent)) {
			    LOGGER.info("Status Content Data is Found");
				service.setStatus("SUCCESS");
				service.setPayload(statusContent);
			} else {
			    LOGGER.info("Status Content Data is not Found");
				service.setStatus("FAILURE");
			}
		} catch (Exception e) {
		    LOGGER.error("Print Stack Trace- ", e);
			service.setStatus("ERROR");
			ErrorPOJO err = new ErrorPOJO();
			err.setCode(209);
			err.setMessage("Fetch failed");
			service.setError(err);
		}

		return service;
	}

	/**
	 * Gets the email content.
	 *
	 * @param contentTitle
	 *            the content title
	 * @return the email content
	 */
	@RequestMapping(value = "/email", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Service getEmailContent(
	        @RequestParam("contentTitle") String contentTitle) {
	    LOGGER.info("inside email method of DotCMSStaticContentController");
	    EmailContent emailContent = null;
		Service service = new Service();

		try {
		    emailContent = staticContentFetchService
					.getEmailContent(contentTitle);
			
		    if (!StringUtils.isEmpty(emailContent)) {
		        LOGGER.info("Email Content Data is Found");
				service.setStatus("SUCCESS");
				service.setPayload(emailContent);
			} else {
			    LOGGER.info("Email Content Data is not Found");
				service.setStatus("FAILURE");
			}
		} catch (Exception e) {
		    LOGGER.error("Print Stack Trace- ", e);
			service.setStatus("ERROR");
			ErrorPOJO err = new ErrorPOJO();
			err.setCode(209);
			err.setMessage("Fetch failed");
			service.setError(err);
		}

		return service;
	}

	/**
	 * Gets the static content.
	 *
	 * @param contentUniqueKey
	 *            the content title
	 * @param pageName
	 *            the page name
	 * @param locale
	 *            the locale
	 * @return the static content
	 */
	@RequestMapping(value = "/static", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Service getStaticContent(
	        @RequestParam("pageName") String pageName,	        
			@RequestParam("contentUniqueKey") String contentUniqueKey,
			@RequestParam("locale") String locale) {
	    LOGGER.info("inside static method of DotCMSStaticContentController");
	    StaticContent staticContent = null;
		Service service = new Service();

		try {
			staticContent = staticContentFetchService.getStaticContent(pageName, contentUniqueKey, locale);
			if (!StringUtils.isEmpty(staticContent)) {
			    LOGGER.info("Static Content Data is Found");
				service.setStatus("SUCCESS");
				service.setPayload(staticContent);
			} else {
			    LOGGER.info("Static Content Data is not Found");
				service.setStatus("FAILURE");
			}
		} catch (Exception e) {
		    LOGGER.error("Print Stack Trace- ", e);
			service.setStatus("ERROR");
			ErrorPOJO err = new ErrorPOJO();
			err.setCode(209);
			err.setMessage("Fetch failed");
			service.setError(err);
		}

		return service;
	}

}
