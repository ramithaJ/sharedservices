/*******************************************************************************
 * Copyright (c) 2015 John Wiley & Sons, Inc. All rights reserved.
 *
 * All material contained herein is proprietary to John Wiley & Sons 
 * and its third party suppliers, if any. The methods, techniques and 
 * technical concepts contained herein are considered trade secrets 
 * and confidential and may be protected by intellectual property laws.  
 * Reproduction or distribution of this material, in whole or in part, 
 * is strictly forbidden except by express prior written permission 
 * of John Wiley & Sons.
 *******************************************************************************/
package com.wiley.gr.ace.discount.controller;


import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.wiles.gr.ace.discount.exception.ASException;
import com.wiles.gr.ace.discount.exception.ASExceptionController;
import com.wiley.gr.ace.discount.services.service.DiscountService;
import com.wiley.gr.ace.authorservices.model.Service;

/**
 * Discount controller holds methods for discount API
 * @author virtusa version 1.0
 */
@RestController
@RequestMapping("/v1/")
public class DiscountController extends ASExceptionController {
    
    /**
     * Logger for UserLoginController class.
     */
    private static final Logger LOGGER = LoggerFactory
            .getLogger(DiscountController.class);
    
    /**
     * injecting UserLoginService bean.
     */
    @Autowired(required = true)
    private DiscountService discountService;
   
    
    
    /** Error code From Props File. */
    @Value("${journalCode.required}")
    private String errorcode;
    /** Error message From Props File. */
    @Value("${adminnotexist.message}")
    private String errormessage;
  
    
    
 /**
  * Method returns the available highest discount value for given parameters. 
  * service accept any number of parameters as key value pair. 
  * @param requestObject to be set.
  * @return {@link Service}
  */
    @RequestMapping(value = "/discounts/", method = RequestMethod.POST)
    public final Service getHighestDiscount(@Valid @RequestBody final Map<String,String> requestObject) {
    	  
        
        Service service = new Service();
        service.setPayload(discountService.getHighestDiscount(requestObject));
        
        LOGGER.info("Return highest discount sucessfully");
		return service;
    }

    /**
     * Method returns discounted societies for given journal code. 
     * @param journalCode to be set
     * @return {@link Service}
     */
    
	@RequestMapping(value = "/discounts/society/{journalCode}", method = RequestMethod.GET)
	public final Service getDiscSocietiesForJournal(
			@Valid @PathVariable("journalCode") String journalCode) {

		Service service = new Service();

		if (!journalCode.isEmpty() && journalCode != null){

			discountService.getDiscSocietiesForJournal(journalCode);

			service.setPayload(discountService
					.getDiscSocietiesForJournal(journalCode));
			
		}
		LOGGER.info("Return discounted societies for jurnal" + journalCode
				+ " sucessfully");
		return service;
	}

    /**
     * Method returns available discounts for given institution code.   
     * @param institutionCode to be set
     * @return {@link Service}
     */
    @RequestMapping(value = "/discounts/institution/{institutionCode}", method = RequestMethod.GET)
    public final Service getDiscForInstitutions(@PathVariable("institutionCode") String institutionCode) {
        
        Service service = new Service();
        
    	if (!institutionCode.isEmpty() && institutionCode != null){
    	
        service.setPayload(discountService.getDiscForInstitutions(institutionCode));
        
        LOGGER.info("Return discounts for institute code"+ institutionCode +" sucessfully");
    	}
		return service;
    }
    
    /**
     * Method returns discounted institution list.
     * @return {@link Service}
     */
    @RequestMapping(value = "/discounts/institutions/", method = RequestMethod.GET)
    public final Service getInstitutions() {
        
        Service service = new Service();
        service.setPayload(discountService.getInstitutionList());
        
        LOGGER.info("Return institution list sucessfully");
    	
		return service;
    }
    
    /**
     * Method returns discount details for given discoout code. 
     * @param discountCode to be set.
     * @return {@link Service}
     */
    @RequestMapping(value = "/discount/{discountCode}", method = RequestMethod.GET)
    public final Service getDiscDetails(@PathVariable("discountCode") String discountCode) {
        
    	
        Service service = new Service();
        service.setPayload(discountService.getDiscDetails(discountCode));
        
        LOGGER.info("Return discount details for discount code " + discountCode+ " sucessfully");
        
		return service;
    }
    
    /**
     * Service to upload the discount data  csv file.
     * @return {@link Service}
     */
    @RequestMapping(value = "/discounts/file", method = RequestMethod.POST)
    public final Service upload() {
        
        Service service = new Service();
		return service;
    }
    
    /**
     * Service to downlaod the discount data csv file
     * @return 
     */
    @RequestMapping(value = "/discounts/file", method = RequestMethod.GET)
    public final Service downlaod() {
        
        Service service = new Service();
		return service;
    }
    
    
}