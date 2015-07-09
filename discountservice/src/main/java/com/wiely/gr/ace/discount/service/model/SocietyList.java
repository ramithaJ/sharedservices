package com.wiely.gr.ace.discount.service.model;

import java.util.ArrayList;
import java.util.List;

public class SocietyList {

	private List<Society> societyDiscounts;

	public List<Society> getSocieties() {
		if (societyDiscounts == null) {
			societyDiscounts = new ArrayList<Society>();
		}
		return societyDiscounts;
	}

}
