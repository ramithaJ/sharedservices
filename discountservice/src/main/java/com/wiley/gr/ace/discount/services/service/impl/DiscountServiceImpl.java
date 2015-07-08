package com.wiley.gr.ace.discount.services.service.impl;

import java.util.Map;

import com.wiely.gr.ace.discount.service.model.Discount;
import com.wiely.gr.ace.discount.service.model.InstituteList;
import com.wiely.gr.ace.discount.service.model.Institution;
import com.wiely.gr.ace.discount.service.model.Society;
import com.wiely.gr.ace.discount.service.model.SocietyList;
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
		discount.setDiscountPercentage(10.5);

		for (String Values : requestObject.values()) {

			if (Values.contains(",")) {
				String[] valueList = Values.split(",");
			}

		}

		return discount;

	}

	@Override
	public SocietyList getDiscSocietiesForJournal(String journalCode) {

		Society society = new Society();
		society.setSocietyName("ABC");
		society.setSocietyCode("SCO123");
		society.setValue("1000");
		society.setValueType("A");

		SocietyList societyList = new SocietyList();

		if (!journalCode.isEmpty() || journalCode != null) {

			societyList.getSocieties().add(society);

		}

		return societyList;
	}

	@Override
	public InstituteList getDiscForInstitutions(String institutionCode) {

		Institution institution = new Institution();
		institution.setInstituteCode("ABCD");
		institution.setInstituteName("INst");

		Discount discount1 = new Discount();
		discount1.setDiscountCode("ABC");
		discount1.setDiscountTypeName("Society");
		discount1.setDiscountValue("500");
		discount1.setPromoCode("ABC001");
		discount1.setValueType("A");

		institution.setDiscount(discount1);

		InstituteList instituteList = new InstituteList();
		instituteList.getinstitutes().add(institution);

		return instituteList;

	}

	@Override
	public InstituteList getInstitutionList() {

		Institution institution1 = new Institution();
		institution1.setInstituteCode("ABCD");
		institution1.setInstituteName("INst");

		Discount discount1 = new Discount();
		discount1.setDiscountCode("ABC");
		discount1.setDiscountTypeName("Society");
		discount1.setDiscountValue("500");
		discount1.setPromoCode("ABC001");
		discount1.setValueType("A");

		institution1.setDiscount(discount1);

		Institution institution2 = new Institution();
		institution2.setInstituteCode("ABCD");
		institution2.setInstituteName("INst");

		Discount discount2 = new Discount();
		discount2.setDiscountCode("ABC");
		discount2.setDiscountTypeName("Journal");
		discount2.setDiscountValue("500");
		discount2.setPromoCode("ABC001");
		discount2.setValueType("A");

		institution2.setDiscount(discount2);

		InstituteList instituteList = new InstituteList();
		instituteList.getinstitutes().add(institution1);
		instituteList.getinstitutes().add(institution2);

		return instituteList;
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
