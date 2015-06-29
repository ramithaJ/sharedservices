package com.wiely.gr.ace.discount.service.model;

import java.util.ArrayList;
import java.util.List;

public class InstituteList {

	private List<Institution> instituteList;

	public List<Institution> getInstituteList() {
		if (instituteList == null) {
			instituteList = new ArrayList<Institution>();
		}

		return instituteList;
	}

}
