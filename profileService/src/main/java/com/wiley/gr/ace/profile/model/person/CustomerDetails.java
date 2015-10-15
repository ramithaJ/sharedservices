package com.wiley.gr.ace.profile.model.person;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CustomerDetails {

	
	private String AlternativeName;
	private String PrimaryEmail;
	private String SecondaryEmail;
	private String FirstName;
	private String LastName;
	private String MiddleName;
	private String Suffix;
	private String ECID;
	private String ASID;
	private String NickName;
	private String ORCID;
	private String JobCategoryCode;
	private String IndustryCode;
	private String ProfileVisibility;
	private String Title;
	private String AuthorFlag;
	private String RegistrantFlag;
	private String SourceSystem;

	
	
	@JsonProperty("AlternativeName")
	public String getAlternativeName() {
		return AlternativeName;
	}
	public void setAlternativeName(String alternativeName) {
		AlternativeName = alternativeName;
	}
	
	@JsonProperty("PrimaryEmail")
	public String getPrimaryEmail() {
		return PrimaryEmail;
	}
	public void setPrimaryEmail(String primaryEmail) {
		PrimaryEmail = primaryEmail;
	}
	
	@JsonProperty("SecondaryEmail")
	public String getSecondaryEmail() {
		return SecondaryEmail;
	}
	public void setSecondaryEmail(String secondaryEmail) {
		SecondaryEmail = secondaryEmail;
	}
	
	@JsonProperty("FirstName")
	public String getFirstName() {
		return FirstName;
	}
	public void setFirstName(String firstName) {
		FirstName = firstName;
	}
	
	@JsonProperty("LastName")
	public String getLastName() {
		return LastName;
	}
	public void setLastName(String lastName) {
		LastName = lastName;
	}
	
	@JsonProperty("MiddleName")
	public String getMiddleName() {
		return MiddleName;
	}
	public void setMiddleName(String middleName) {
		MiddleName = middleName;
	}
	
	@JsonProperty("Suffix")
	public String getSuffix() {
		return Suffix;
	}
	public void setSuffix(String suffix) {
		Suffix = suffix;
	}
	
	@JsonProperty("ECID")
	public String getECID() {
		return ECID;
	}
	public void setECID(String eCID) {
		ECID = eCID;
	}
	
	@JsonProperty("ASID")
	public String getASID() {
		return ASID;
	}
	public void setASID(String aSID) {
		ASID = aSID;
	}
	
	@JsonProperty("NickName")
	public String getNickName() {
		return NickName;
	}
	public void setNickName(String nickName) {
		NickName = nickName;
	}
	
	@JsonProperty("ORCID")
	public String getORCID() {
		return ORCID;
	}
	public void setORCID(String oRCID) {
		ORCID = oRCID;
	}
	
	@JsonProperty("JobCategoryCode")
	public String getJobCategoryCode() {
		return JobCategoryCode;
	}
	public void setJobCategoryCode(String jobCategoryCode) {
		JobCategoryCode = jobCategoryCode;
	}
	
	@JsonProperty("IndustryCode")
	public String getIndustryCode() {
		return IndustryCode;
	}
	public void setIndustryCode(String industryCode) {
		IndustryCode = industryCode;
	}
	
	@JsonProperty("ProfileVisibility")
	public String getProfileVisibility() {
		return ProfileVisibility;
	}
	public void setProfileVisibility(String profileVisibility) {
		ProfileVisibility = profileVisibility;
	}
	
	@JsonProperty("Title")
	public String getTitle() {
		return Title;
	}
	public void setTitle(String title) {
		Title = title;
	}
	
	@JsonProperty("AuthorFlag")
	public String getAuthorFlag() {
		return AuthorFlag;
	}
	public void setAuthorFlag(String authorFlag) {
		AuthorFlag = authorFlag;
	}
	
	@JsonProperty("RegistrantFlag")
	public String getRegistrantFlag() {
		return RegistrantFlag;
	}
	
	public void setRegistrantFlag(String registrantFlag) {
		RegistrantFlag = registrantFlag;
	}
	
	@JsonProperty("SourceSystem")
	public String getSourceSystem() {
		return SourceSystem;
	}
	public void setSourceSystem(String sourceSystem) {
		SourceSystem = sourceSystem;
	}

}
