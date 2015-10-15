package com.wiley.gr.ace.profile.model;

import java.util.List;

public class AlertList {

	String preferredEmailId;
	List<Alert> alert;

	
	public String getPreferredEmailId() {
		return preferredEmailId;
	}

	public void setPreferredEmailId(String preferredEmailId) {
		this.preferredEmailId = preferredEmailId;
	}

	public List<Alert> getAlert() {
		return alert;
	}

	public void setAlert(List<Alert> alert) {
		this.alert = alert;
	}


	
}
