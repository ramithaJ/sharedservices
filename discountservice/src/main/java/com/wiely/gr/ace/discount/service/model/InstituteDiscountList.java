package com.wiely.gr.ace.discount.service.model;

import java.util.ArrayList;
import java.util.List;

public class InstituteDiscountList {
	
	private Institution institution;
	private List<Discount> discounts;
	
	
	public Institution getInstitution() {
		return institution;
	}
	
	public void setInstitution(Institution institution) {
		this.institution = institution;
	}
	
	

	public List<Discount> getDiscounts() {
		if(discounts ==null){
			discounts = new ArrayList<Discount>();
		}
		return discounts;	}


}
