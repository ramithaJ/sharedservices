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
package com.wiley.gr.ace.discount.common;

import com.wiley.gr.ace.discount.exception.SharedServiceException;
import com.wiley.gr.ace.discount.model.Discount;
import com.wiley.gr.ace.discount.model.GetMaxDiscountRequest;
import com.wiley.gr.ace.discount.model.Service;
import com.wiley.gr.ace.discount.model.ErrorResponse;

public class CommonUtil {

    private CommonUtil(){

    }


    /**
     * Method to set code, message & status to Service.
     *
     * @param code    Code
     * @param message Message
     * @param status  Status
     * @return
     */
    public static Service setServiceMessage(String code, String message, String status) {
        Service service = new Service();
        ErrorResponse error = new ErrorResponse();
        error.setCode(Integer.parseInt(code));
        error.setMessage(message);
        service.setStatus(status);
        service.setError(error);
        return service;
    }

    /**
     * Util method to throw error.
     *
     * @param exception Exception
     * @throws SharedServiceException
     */
    public static void throwError(Exception exception) throws SharedServiceException {
        if (null != exception.getCause()) {
            throw new SharedServiceException(CommonConstants.ERROR_CODE_100, CommonConstants.ERROR_NOTE + exception.getCause().getMessage());
        } else {
            throw new SharedServiceException(CommonConstants.ERROR_CODE_100, CommonConstants.ERROR_NOTE + exception);
        }
    }

    /**
     * Util method to validate input base price.
     *
     * @param getMaxDiscountRequest GetMaxDiscountRequest Request JSON Object
     * @return boolean
     */
    public static Boolean validateBasePrice(GetMaxDiscountRequest getMaxDiscountRequest) {
        if (null == getMaxDiscountRequest || getMaxDiscountRequest.getBasePrice() <= 0) {
            return true;
        }
        return false;
    }

    /**
     * Method to set discount.
     *
     * @param discountCode      Discount Code
     * @param discountTypeName  Discount Type Name
     * @param promoCode         Promo Code
     * @param discountValueType Discount Value Type
     * @param discountValue     Discount Value
     * @return
     */
    public static Discount setDiscount(String discountCode, String discountTypeName, String promoCode, String discountValueType, String discountValue) {
        Discount discount = new Discount();
        discount.setDiscountCode(discountCode);
        discount.setDiscountTypeName(discountTypeName);
        discount.setPromoCode(promoCode);
        discount.setDiscountValueType(discountValueType);
        discount.setDiscountValue(discountValue);
        return discount;
    }


}
