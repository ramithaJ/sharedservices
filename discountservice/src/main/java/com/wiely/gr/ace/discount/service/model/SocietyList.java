package com.wiely.gr.ace.discount.service.model;

import java.util.ArrayList;
import java.util.List;

public class SocietyList {

	private List<Society> societies;

	public List<Society> getSocieties() {
		if (societies == null) {
			societies = new ArrayList<Society>();
		}
		return societies;
	}

}
