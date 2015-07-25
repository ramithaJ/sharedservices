/**
 * ****************************************************************************
 * Copyright (c) 2015 John Wiley & Sons, Inc. All rights reserved.
 * <p>
 * All material contained herein is proprietary to John Wiley & Sons
 * and its third party suppliers, if any. The methods, techniques and
 * technical concepts contained herein are considered trade secrets
 * and confidential and may be protected by intellectual property laws.
 * Reproduction or distribution of this material, in whole or in part,
 * is strictly forbidden except by express prior written permission
 * of John Wiley & Sons.
 * *****************************************************************************
 */
package com.wiley.gr.ace.sharedservices.profile;

import java.io.Serializable;
import java.util.List;

/**
 * @author kkalyan
 */
public class UserProfile implements Serializable {

    private String titleCd;
    private String firstName;
    private String middleName;
    private String lastName;
    private String suffixCd;
    private String alternativeName;
    private String industryCd;
    private String jobCategoryCd;
    private String orcidId;
    private String recieveEmailsFlag;
    private String ecid;
    private String password;
    private String profilePic;
    private char profileVisibleFlag;
    private String primaryEmailAddress;
    private String recoveryEmailAddress;
    private List<ProfileVisible> profileVisible;
    private List<Address> addresses;
    private List<Affiliation> affiliations;
    private List<Funder> funders;
    private List<Society> societies;
    private List<MyInterest> myInterests;
    private List<CoAuthor> coAuthors;
    private List<PreferredJournal> journals;
    private List<Alert> alerts;

    public String getTitleCd() {
        return titleCd;
    }

    public void setTitleCd(String titleCd) {
        this.titleCd = titleCd;
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

    public String getSuffixCd() {
        return suffixCd;
    }

    public void setSuffixCd(String suffixCd) {
        this.suffixCd = suffixCd;
    }

    public String getAlternativeName() {
        return alternativeName;
    }

    public void setAlternativeName(String alternativeName) {
        this.alternativeName = alternativeName;
    }

    public String getIndustryCd() {
        return industryCd;
    }

    public void setIndustryCd(String industryCd) {
        this.industryCd = industryCd;
    }

    public String getJobCategoryCd() {
        return jobCategoryCd;
    }

    public void setJobCategoryCd(String jobCategoryCd) {
        this.jobCategoryCd = jobCategoryCd;
    }

    public String getOrcidId() {
        return orcidId;
    }

    public void setOrcidId(String orcidId) {
        this.orcidId = orcidId;
    }

    public String getRecieveEmailsFlag() {
        return recieveEmailsFlag;
    }

    public void setRecieveEmailsFlag(String recieveEmailsFlag) {
        this.recieveEmailsFlag = recieveEmailsFlag;
    }

    public char getProfileVisibleFlag() {
        return profileVisibleFlag;
    }

    public void setProfileVisibleFlag(char profileVisibleFlag) {
        this.profileVisibleFlag = profileVisibleFlag;
    }

    public String getPrimaryEmailAddress() {
        return primaryEmailAddress;
    }

    public void setPrimaryEmailAddress(String primaryEmailAddress) {
        this.primaryEmailAddress = primaryEmailAddress;
    }

    public String getRecoveryEmailAddress() {
        return recoveryEmailAddress;
    }

    public void setRecoveryEmailAddress(String recoveryEmailAddress) {
        this.recoveryEmailAddress = recoveryEmailAddress;
    }

    public List<ProfileVisible> getProfileVisible() {
        return profileVisible;
    }

    public void setProfileVisible(List<ProfileVisible> profileVisible) {
        this.profileVisible = profileVisible;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }

    public List<Affiliation> getAffiliations() {
        return affiliations;
    }

    public void setAffiliations(List<Affiliation> affiliations) {
        this.affiliations = affiliations;
    }

    public List<Funder> getFunders() {
        return funders;
    }

    public void setFunders(List<Funder> funders) {
        this.funders = funders;
    }

    public List<Society> getSocieties() {
        return societies;
    }

    public void setSocieties(List<Society> societies) {
        this.societies = societies;
    }

    public List<MyInterest> getMyInterests() {
        return myInterests;
    }

    public void setMyInterests(List<MyInterest> myInterests) {
        this.myInterests = myInterests;
    }

    public List<CoAuthor> getCoAuthors() {
        return coAuthors;
    }

    public void setCoAuthors(List<CoAuthor> coAuthors) {
        this.coAuthors = coAuthors;
    }

    public List<PreferredJournal> getJournals() {
        return journals;
    }

    public void setJournals(List<PreferredJournal> journals) {
        this.journals = journals;
    }

    public List<Alert> getAlerts() {
        return alerts;
    }

    public void setAlerts(List<Alert> alerts) {
        this.alerts = alerts;
    }

    public String getEcid() {
        return ecid;
    }

    public void setEcid(String ecid) {
        this.ecid = ecid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    @Override
    public String toString() {
        return "UserProfile{" +
                "titleCd='" + titleCd + '\'' +
                ", firstName='" + firstName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", suffixCd='" + suffixCd + '\'' +
                ", alternativeName='" + alternativeName + '\'' +
                ", industryCd='" + industryCd + '\'' +
                ", jobCategoryCd='" + jobCategoryCd + '\'' +
                ", orcidId='" + orcidId + '\'' +
                ", recieveEmailsFlag='" + recieveEmailsFlag + '\'' +
                ", ecid='" + ecid + '\'' +
                ", password='" + password + '\'' +
                ", profilePic='" + profilePic + '\'' +
                ", profileVisibleFlag=" + profileVisibleFlag +
                ", primaryEmailAddress='" + primaryEmailAddress + '\'' +
                ", recoveryEmailAddress='" + recoveryEmailAddress + '\'' +
                ", profileVisible=" + profileVisible +
                ", addresses=" + addresses +
                ", affiliations=" + affiliations +
                ", funders=" + funders +
                ", societies=" + societies +
                ", myInterests=" + myInterests +
                ", coAuthors=" + coAuthors +
                ", journals=" + journals +
                ", alerts=" + alerts +
                '}';
    }
}
