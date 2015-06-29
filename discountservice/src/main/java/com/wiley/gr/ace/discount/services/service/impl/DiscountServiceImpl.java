package com.wiley.gr.ace.discount.services.service.impl;

import java.util.Map;

import com.wiely.gr.ace.discount.service.model.Discount;
import com.wiely.gr.ace.discount.service.model.InstituteDiscountList;
import com.wiely.gr.ace.discount.service.model.InstituteList;
import com.wiely.gr.ace.discount.service.model.Institution;
import com.wiely.gr.ace.discount.service.model.Society;
import com.wiely.gr.ace.discount.service.model.SocietyList;
import com.wiles.gr.ace.discount.exception.ASException;
import com.wiley.gr.ace.discount.services.service.DiscountService;

public class DiscountServiceImpl implements DiscountService {

	@Override
	public Discount getHighestDiscount(Map<String, String> requestObject) {

		Discount discount = new Discount();
		discount.setDiscountCode("ABC");
		discount.setDiscountTypeName("Society");
		discount.setDiscountValue("500");
		discount.setPromoCode("ABC001");
		discount.setValueType("A");

		return discount;

	}

	@Override
	public SocietyList getDiscSocietiesForJournal(String journalCode){
		
		
		

		Society society = new Society();
		society.setSocietyName("ABC");
		society.setValue("1000");
		society.setValueType("A");

		
		SocietyList societyList = new SocietyList();

		
		if(!journalCode.isEmpty() ||journalCode!=null){
		
		societyList.getSocieties().add(society);
		
		}
		

		return societyList;
	}

	@Override
	public InstituteDiscountList getDiscForInstitutions(String institutionCode) {

		Institution institution = new Institution();
		institution.setInstituteCode("ABCD");
		institution.setInstituteName("INst");

		Discount discount1 = new Discount();
		discount1.setDiscountCode("ABC");
		discount1.setDiscountTypeName("Society");
		discount1.setDiscountValue("500");
		discount1.setPromoCode("ABC001");
		discount1.setValueType("A");

		Discount discount2 = new Discount();
		discount2.setDiscountCode("ABC");
		discount2.setDiscountTypeName("Journal");
		discount2.setDiscountValue("500");
		discount2.setPromoCode("ABC001");
		discount2.setValueType("A");

		InstituteDiscountList instituteDiscountList = new InstituteDiscountList();
		instituteDiscountList.setInstitution(institution);
		;
		instituteDiscountList.getDiscounts().add(discount1);
		instituteDiscountList.getDiscounts().add(discount2);

		return instituteDiscountList;

	}

	@Override
	public InstituteList getInstitutionList() {

		Institution institution = new Institution();
		institution.setInstituteCode("InstCODDE1");
		institution.setInstituteName("InstituteName");

		InstituteList instituteList = new InstituteList();
		instituteList.getInstituteList().add(institution);

		return instituteList;
	}


	@Override
	public Discount getDiscDetails(String discCode) {
		
		Discount discount = new Discount();
		discount.setDiscountCode("ABC");
		discount.setDiscountTypeName("Society");
		discount.setDiscountValue("500");
		discount.setPromoCode("ABC001");
		discount.setValueType("A");

		return discount;
		
	}

	
	
	@Override
	public void upload() {
		// TODO Auto-generated method stub

	}

	@Override
	public void download() {
		// TODO Auto-generated method stub

	}


}
