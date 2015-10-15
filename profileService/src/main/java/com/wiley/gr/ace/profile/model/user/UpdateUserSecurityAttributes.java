package com.wiley.gr.ace.profile.model.user;

public class UpdateUserSecurityAttributes {
	
	public String getExistingEmail() {
		return ExistingEmail;
	}
	public void setExistingEmail(String existingEmail) {
		ExistingEmail = existingEmail;
	}
	public String getNewEmail() {
		return NewEmail;
	}
	public void setNewEmail(String newEmail) {
		NewEmail = newEmail;
	}
	public String getSourceSystem() {
		return SourceSystem;
	}
	public void setSourceSystem(String sourceSystem) {
		SourceSystem = sourceSystem;
	}
	private String ExistingEmail;
	private String NewEmail;
	private String SourceSystem;

}
