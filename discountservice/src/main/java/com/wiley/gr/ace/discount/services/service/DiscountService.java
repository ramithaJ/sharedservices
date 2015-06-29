package com.wiley.gr.ace.discount.services.service;

import java.util.Map;

import com.wiely.gr.ace.discount.service.model.Discount;
import com.wiely.gr.ace.discount.service.model.InstituteDiscountList;
import com.wiely.gr.ace.discount.service.model.InstituteList;
import com.wiely.gr.ace.discount.service.model.SocietyList;
import com.wiles.gr.ace.discount.exception.ASException;


public interface DiscountService {
	
	public Discount getHighestDiscount(Map<String, String> requestObject) throws ASException;
	public SocietyList getDiscSocietiesForJournal(String journalCode)throws ASException;
	public InstituteDiscountList getDiscForInstitutions(String institutionCode)throws ASException;;
	public InstituteList getInstitutionList()throws ASException;;
	public Discount getDiscDetails(String discCode)throws ASException;;
	public void upload()throws ASException;;
	public void download()throws ASException;

}
