package com.wiley.gr.ace.profile.model;

import java.util.List;


public class EntityValue{
	
	Profile profile;
	Address address;
	Email email;
	List<String> journalList;
	
	public List<String> getJournalList() {
		return journalList;
	}
	public void setJournalList(List<String> journalList) {
		this.journalList = journalList;
	}
	public Email getEmail() {
		return email;
	}
	public void setEmail(Email email) {
		this.email = email;
	}
	
	
	AlertList alertList;

	public AlertList getAlertList() {
		return alertList;
	}
	public void setAlertList(AlertList alertList) {
		this.alertList = alertList;
	}
	
	public Profile getProfile() {
		return profile;
	}
	public void setProfile(Profile profile) {
		this.profile = profile;
	}
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	

}
