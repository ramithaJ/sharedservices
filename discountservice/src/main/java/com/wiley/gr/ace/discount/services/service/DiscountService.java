package com.wiley.gr.ace.discount.services.service;

import com.wiely.gr.ace.discount.service.model.GetMaxDiscountRequest;
import com.wiely.gr.ace.discount.service.model.Service;
import com.wiles.gr.ace.discount.exception.SharedServiceException;


public interface DiscountService {

    Service getDiscount(GetMaxDiscountRequest getMaxDiscountRequest) throws SharedServiceException;

    Service getDiscountSocietiesForJournal(String journalAcronym) throws SharedServiceException;

    Service getInstitutionsDiscount(String institution) throws SharedServiceException;
}
