/**
 * ****************************************************************************
 * Copyright (c) 2015 John Wiley & Sons, Inc. All rights reserved.
 * <p>
 * All material contained herein is proprietary to John Wiley & Sons
 * and its third party suppliers, if any. The methods, techniques and
 * technical concepts contained herein are considered trade secrets
 * and confidential and may be protected by intellectual property laws.
 * Reproduction or distribution of this material, in whole or in part,
 * is strictly forbidden except by express prior written permission
 * of John Wiley & Sons.
 * *****************************************************************************
 */
package com.wiley.gr.ace.discount.controller;


import com.wiely.gr.ace.discount.common.CommonConstants;
import com.wiely.gr.ace.discount.common.CommonUtil;
import com.wiely.gr.ace.discount.common.PropertyUtils;
import com.wiely.gr.ace.discount.service.model.GetMaxDiscountRequest;
import com.wiely.gr.ace.discount.service.model.Service;
import com.wiles.gr.ace.discount.exception.SharedServiceException;
import com.wiley.gr.ace.discount.services.service.DiscountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;


/**
 * GetMaxDiscountRequest controller holds methods for getMaxDiscountRequest API
 *
 * @author virtusa version 1.0
 */
@RestController
@RequestMapping(CommonConstants.VERSION)
public class DiscountController extends PropertyUtils {

    /**
     * Logger for UserLoginController class.
     */
    private static final Logger LOGGER = LoggerFactory
            .getLogger(DiscountController.class);

    // GetMaxDiscountRequest Impl Service.
    @Autowired
    private DiscountService discountService;

    /**
     * Method calculates discounts based on the different category providers like GetSocietyResponse, Journal, GetInstitutionResponse
     * and will return the getMaxDiscountRequest which is maximum applicable for the user.
     *
     * @param getMaxDiscountRequest GetMaxDiscountRequest Request JSON Object
     * @return {@link Service}
     */

    @RequestMapping(value = CommonConstants.GET_DISCOUNT, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public final Service getDiscount(@RequestBody GetMaxDiscountRequest getMaxDiscountRequest) {
        try {

            //Validate baseprice from the input. If value is empty (or) null throw error.
            if (CommonUtil.validateBasePrice(getMaxDiscountRequest)) {
                return CommonUtil.setServiceMessage(CommonConstants.ERROR_CODE_101, PropertyUtils.errorcode101, CommonConstants.ERROR);
            }
            return discountService.getDiscount(getMaxDiscountRequest);
        } catch (SharedServiceException e) {
            LOGGER.error("Error Occurred in getGetMaxDiscountRequest", e);
            return CommonUtil.setServiceMessage(e.getErrorCode(), e.getMessage(), CommonConstants.ERROR);
        }
    }

    /**
     * Method to get society getMaxDiscountRequest of a particular Journal based on the Journal Acronym.
     * Or it will return all societies getMaxDiscountRequest information if Journal Acronym is not passed.
     *
     * @param journalAcronym Journal Acronym
     * @return {@link Service}
     */
    @RequestMapping(value = CommonConstants.GET_SOCIETY_DISCOUNTS, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public final Service getDiscountSocieties(@PathVariable(CommonConstants.JOURNAL_ACRONYM) String journalAcronym) {
        try {
            return discountService.getDiscountSocietiesForJournal(journalAcronym);
        } catch (SharedServiceException e) {
            LOGGER.error("Error Occurred in getDiscountSocieties", e);
            return CommonUtil.setServiceMessage(e.getErrorCode(), e.getMessage(), CommonConstants.ERROR);
        }
    }


    /**
     * Method to get institution getMaxDiscountRequest of a particular Article based on the GetInstitutionResponse Code.
     * Or it will return all institutions getMaxDiscountRequest information if GetInstitutionResponse Code is not passed.
     *
     * @param institutionCode GetInstitutionResponse Code
     * @return {@link Service}
     */
    @RequestMapping(value = CommonConstants.GET_INSTITUTIONS_DISCOUNTS, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public final Service getInstitutionsDiscount(@PathVariable(CommonConstants.INSTITUTION_CD) String institutionCode) {
        try {
            return discountService.getInstitutionsDiscount(institutionCode);
        } catch (SharedServiceException e) {
            LOGGER.error("Error Occurred in getDiscountSocieties", e);
            return CommonUtil.setServiceMessage(e.getErrorCode(), e.getMessage(), CommonConstants.ERROR);
        }
    }


}