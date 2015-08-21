package com.wiley.gr.ace.discount.service;

import com.wiley.gr.ace.discount.model.GetMaxDiscountRequest;
import com.wiley.gr.ace.discount.model.Service;
import com.wiley.gr.ace.discount.exception.SharedServiceException;


public interface DiscountService {

    Service getDiscount(GetMaxDiscountRequest getMaxDiscountRequest) throws SharedServiceException;

    Service getDiscountSocietiesForJournal(String journalAcronym) throws SharedServiceException;

    Service getInstitutionsDiscount(String institution) throws SharedServiceException;
}
