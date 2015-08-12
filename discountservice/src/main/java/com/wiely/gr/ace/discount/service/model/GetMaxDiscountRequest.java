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
package com.wiely.gr.ace.discount.service.model;

import java.io.Serializable;

public class GetMaxDiscountRequest implements Serializable {

    private String societyPromoCode;
    private String societyCode;
    private String institutionCode;
    private String countryCode;
    private String journalAcronym;
    private float basePrice;
    private String otherPromoCodes;

    public String getSocietyPromoCode() {
        return societyPromoCode;
    }

    public void setSocietyPromoCode(String societyPromoCode) {
        this.societyPromoCode = societyPromoCode;
    }

    public String getSocietyCode() {
        return societyCode;
    }

    public void setSocietyCode(String societyCode) {
        this.societyCode = societyCode;
    }

    public String getInstitutionCode() {
        return institutionCode;
    }

    public void setInstitutionCode(String institutionCode) {
        this.institutionCode = institutionCode;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getJournalAcronym() {
        return journalAcronym;
    }

    public void setJournalAcronym(String journalAcronym) {
        this.journalAcronym = journalAcronym;
    }

    public float getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(float basePrice) {
        this.basePrice = basePrice;
    }

    public String getOtherPromoCodes() {
        return otherPromoCodes;
    }

    public void setOtherPromoCodes(String otherPromoCodes) {
        this.otherPromoCodes = otherPromoCodes;
    }

    @Override
    public String toString() {
        return "GetMaxDiscountRequest{" +
                "societyPromoCode='" + societyPromoCode + '\'' +
                ", societyCode='" + societyCode + '\'' +
                ", institutionCode='" + institutionCode + '\'' +
                ", countryCode='" + countryCode + '\'' +
                ", journalAcronym='" + journalAcronym + '\'' +
                ", basePrice='" + basePrice + '\'' +
                ", otherPromoCodes='" + otherPromoCodes + '\'' +
                '}';
    }
}
