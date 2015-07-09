package com.wiely.gr.ace.discount.service.model;

import java.util.ArrayList;
import java.util.List;

public class InstituteList {

	private List<Institution> institutes;

	public List<Institution> getinstitutes() {
		if (institutes == null) {
			institutes = new ArrayList<Institution>();
		}

		return institutes;
	}

}
