package com.wiley.gr.ace.staticcontentservices.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
   	 * @param contentTiltle the content title
   	 * @param moduleName the module name
   	 * @param locale the locale
   	 * @return the confirmation catalog
   	 */
   	@RequestMapping(value = "/confirmation/{contentTitle}/{moduleName}/{locale}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	    public @ResponseBody Service getConfirmationCatalog(
	            @PathVariable("contentTitle") String contentTitle,
	            @PathVariable("moduleName") String moduleName,
	            @PathVariable("locale") String locale) {
	        ConfirmationContent confirmationContent = null;
	        Service service = new Service();

	        try {
	            confirmationContent = staticContentFetchService.getConfirmationMessageContent(
	                    contentTitle,moduleName, locale);
	            if (!StringUtils.isEmpty(confirmationContent)) {
	                service.setStatus("SUCCESS");
	                service.setPayload(confirmationContent);
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
	   
	   /**
   	 * Gets the status content.
   	 *
   	 * @param contentTitle the content title
   	 * @param statusMessageType the status message type
   	 * @param locale the locale
   	 * @return the status content
   	 */
   	@RequestMapping(value = "/status/{contentTitle}/{statusMessageType}/{locale}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
       public @ResponseBody Service getStatusContent(
               @PathVariable("contentTitle") String contentTitle,
               @PathVariable("statusMessageType") String statusMessageType,
               @PathVariable("locale") String locale) {
           StatusContent statusContent = null;
           Service service = new Service();

           try {
               statusContent = staticContentFetchService.getStatusContent(
                       contentTitle,statusMessageType, locale);
               if (!StringUtils.isEmpty(statusContent)) {
                   service.setStatus("SUCCESS");
                   service.setPayload(statusContent);
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
   	
   	
   	/**
	    * Gets the email content.
	    *
	    * @param contentTitle the content title
	    * @return the email content
	    */
	   @RequestMapping(value = "/email/{contentTitle}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody Service getEmailContent(
            @PathVariable("contentTitle") String contentTitle) {
        EmailContent emailContent = null;
        Service service = new Service();

        try {
            emailContent = staticContentFetchService.getEmailContent(
                    contentTitle);
            if (!StringUtils.isEmpty(emailContent)) {
                service.setStatus("SUCCESS");
                service.setPayload(emailContent);
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
	   
	   
	   
	   /**
   	 * Gets the static content.
   	 *
   	 * @param contentTitle the content title
   	 * @param pageName the page name
   	 * @param locale the locale
   	 * @return the static content
   	 */
   	@RequestMapping(value = "/static/{contentTitle}/{pageName}/{locale}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	    public @ResponseBody Service getStaticContent(
	            @PathVariable("contentTitle") String contentTitle,
	            @PathVariable("pageName") String pageName,
	            @PathVariable("locale") String locale){
	        StaticContent staticContent = null;
	        Service service = new Service();

	        try {
	            staticContent = staticContentFetchService.getStaticContent(
	                    contentTitle,pageName,locale);
	            if (!StringUtils.isEmpty(staticContent)) {
	                service.setStatus("SUCCESS");
	                service.setPayload(staticContent);
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
