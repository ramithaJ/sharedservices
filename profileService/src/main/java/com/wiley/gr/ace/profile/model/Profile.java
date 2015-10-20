package com.wiley.gr.ace.profile.model;

import java.util.List;

public class Profile {

	
	String titleCode;
	String firstName;
	String middleName;
    String lastName;
    String suffixCode;
    String alternativeName;
    String industryCode;
    String jobCategoryCode;
    String sendEmail;
    String orcid;
    String primaryEmail;
	String recoveryEmail;
	String oldEmail;
	String authorFlag;
	List<String> interestList; 
	String registrantFlag;
	String userRole;
	String created;
	String modified;
	String state;
	String status;
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	
	public String getPrimaryEmail() {
		return primaryEmail;
	}
	public void setPrimaryEmail(String primaryEmail) {
		this.primaryEmail = primaryEmail;
	}
	public String getRecoveryEmail() {
		return recoveryEmail;
	}
	public void setRecoveryEmail(String recoveryEmail) {
		this.recoveryEmail = recoveryEmail;
	}
	public String getOldEmail() {
		return oldEmail;
	}
	public void setOldEmail(String oldEmail) {
		this.oldEmail = oldEmail;
	}
	public List<String> getInterestList() {
		return interestList;
	}
	public void setInterestList(List<String> interestList) {
		this.interestList = interestList;
	}
	public String getTitleCode() {
		return titleCode;
	}
	public void setTitleCode(String titleCode) {
		this.titleCode = titleCode;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getMiddleName() {
		return middleName;
	}
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getSuffixCode() {
		return suffixCode;
	}
	public void setSuffixCode(String suffixCode) {
		this.suffixCode = suffixCode;
	}
	public String getAlternativeName() {
		return alternativeName;
	}
	public void setAlternativeName(String alternativeName) {
		this.alternativeName = alternativeName;
	}
	public String getIndustryCode() {
		return industryCode;
	}
	public void setIndustryCode(String industryCode) {
		this.industryCode = industryCode;
	}
	public String getJobCategoryCode() {
		return jobCategoryCode;
	}
	public void setJobCategoryCode(String jobCategoryCode) {
		this.jobCategoryCode = jobCategoryCode;
	}
	public String getSendEmail() {
		return sendEmail;
	}
	public void setSendEmail(String sendEmail) {
		this.sendEmail = sendEmail;
	}
	public String getOrcid() {
		return orcid;
	}
	public void setOrcid(String orcid) {
		this.orcid = orcid;
	}
	public String getAuthorFlag() {
		return authorFlag;
	}
	public void setAuthorFlag(String authorFlag) {
		this.authorFlag = authorFlag;
	}
	public String getRegistrantFlag() {
		return registrantFlag;
	}
	public void setRegistrantFlag(String registrantFlag) {
		this.registrantFlag = registrantFlag;
	}
	public String getUserRole() {
		return userRole;
	}
	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}

	
	public String getCreated() {
		return created;
	}
	public void setCreated(String created) {
		this.created = created;
	}
	public String getModified() {
		return modified;
	}
	public void setModified(String modified) {
		this.modified = modified;
	}
}
