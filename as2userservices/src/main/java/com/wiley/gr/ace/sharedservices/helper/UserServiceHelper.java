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
package com.wiley.gr.ace.sharedservices.helper;

import com.wiley.gr.ace.sharedservices.common.CommonConstants;
import com.wiley.gr.ace.sharedservices.payload.*;
import com.wiley.gr.ace.sharedservices.payload.Error;
import com.wiley.gr.ace.sharedservices.persistence.entity.*;
import com.wiley.gr.ace.sharedservices.persistence.entity.Address;
import com.wiley.gr.ace.sharedservices.profile.*;
import org.apache.commons.lang.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

/**
 * @author kkalyan
 */
public class UserServiceHelper {

    /**
     * Setter method for User Profile Information.
     *
     * @param userServiceRequest Request JSON Information
     * @param user               Entity Object
     * @return user entity object
     */
    public static Users setUserProfileInformation(UserServiceRequest userServiceRequest, Users user) {
        if (!StringUtils.isEmpty(userServiceRequest.getUserProfile().getFirstName())) {
            user.setFirstName(userServiceRequest.getUserProfile().getFirstName());
        }
        if (!StringUtils.isEmpty(userServiceRequest.getUserProfile().getLastName())) {
            user.setLastName(userServiceRequest.getUserProfile().getLastName());
        }
        if (!StringUtils.isEmpty(userServiceRequest.getUserProfile().getPrimaryEmailAddress())) {
            user.setPrimaryEmailAddr(userServiceRequest.getUserProfile().getPrimaryEmailAddress());
        }
        user.setCreatedDate(getDate());
        user.setUpdatedDate(getDate());
        return user;
    }

    /**
     * Setter method for Author Profile data.
     *
     * @param userServiceRequest Request JSON Information
     * @param authorProfile      Entity Object
     * @return authorProfile entity
     */
    public static AuthorProfile setAuthorProfile(UserServiceRequest userServiceRequest, AuthorProfile authorProfile) {

        if (!StringUtils.isEmpty(userServiceRequest.getUserProfile().getTitleCd())) {
            authorProfile.setTitleCd(userServiceRequest.getUserProfile().getTitleCd());
        }
        if (!StringUtils.isEmpty(userServiceRequest.getUserProfile().getMiddleName())) {
            authorProfile.setMiddleName(userServiceRequest.getUserProfile().getMiddleName());
        }
        if (!StringUtils.isEmpty(userServiceRequest.getUserProfile().getSuffixCd())) {
            authorProfile.setSuffixCd(userServiceRequest.getUserProfile().getSuffixCd());
        }
        if (!StringUtils.isEmpty(userServiceRequest.getUserProfile().getAlternativeName())) {
            authorProfile.setAlternativeName(userServiceRequest.getUserProfile().getAlternativeName());
        }
        if (!StringUtils.isEmpty(userServiceRequest.getUserProfile().getIndustryCd())) {
            authorProfile.setIndustryCd(userServiceRequest.getUserProfile().getIndustryCd());
        }
        if (!StringUtils.isEmpty(userServiceRequest.getUserProfile().getJobCategoryCd())) {
            authorProfile.setJobCategoryCd(userServiceRequest.getUserProfile().getJobCategoryCd());
        }
        if (userServiceRequest.getUserProfile().getProfileVisibleFlag() != ' ') {
            authorProfile.setProfileVisibleFlg(userServiceRequest.getUserProfile().getProfileVisibleFlag());
        }
        if (!StringUtils.isEmpty(userServiceRequest.getUserProfile().getRecieveEmailsFlag())) {
            authorProfile.setOptInPromoteFlg(userServiceRequest.getUserProfile().getRecieveEmailsFlag());
        }

        authorProfile.setCreatedDate(getDate());
        authorProfile.setUpdatedDate(getDate());
        return authorProfile;
    }

    /**
     * Setter method for UserSecondaryEmailAddr data.
     *
     * @param userServiceRequest Request JSON Information
     * @param secondaryEmailAddr Entity Object
     * @param user               Entity Object
     * @return secondaryEmailAddr entity object
     */
    public static UserSecondaryEmailAddr setUserSecondaryEmailAddr(UserServiceRequest userServiceRequest, UserSecondaryEmailAddr secondaryEmailAddr, Users user) {
        if (!StringUtils.isEmpty(userServiceRequest.getUserProfile().getRecoveryEmailAddress())) {
            secondaryEmailAddr.setSecondaryEmailAddr(userServiceRequest.getUserProfile().getRecoveryEmailAddress());
            secondaryEmailAddr.setCreatedDate(getDate());
            secondaryEmailAddr.setUpdatedDate(getDate());
            secondaryEmailAddr.setUsersByUserId(user);
            return secondaryEmailAddr;
        } else {
            return null;
        }

    }

    /**
     * Setter method for Address data.
     *
     * @param address        Entity Object
     * @param addressProfile Request JSON Information
     * @return address entity object
     */
    public static Address setAddress(Address address, com.wiley.gr.ace.sharedservices.profile.Address addressProfile) {
        if (!StringUtils.isEmpty(addressProfile.getTitleCd())) {
            address.setTitleCd(addressProfile.getTitleCd());
        }
        if (!StringUtils.isEmpty(addressProfile.getFirstName())) {
            address.setFirstName(addressProfile.getFirstName());
        }
        if (!StringUtils.isEmpty(addressProfile.getLastName())) {
            address.setLastName(addressProfile.getLastName());
        }
        if (!StringUtils.isEmpty(addressProfile.getSuffixCd())) {
            address.setSuffixCd(addressProfile.getSuffixCd());
        }
        if (!StringUtils.isEmpty(addressProfile.getInstitutionCd())) {
            address.setInstitutionCd(addressProfile.getInstitutionCd());
        }
        if (!StringUtils.isEmpty(addressProfile.getDepartmentCd())) {
            address.setDepartmentCd(addressProfile.getDepartmentCd());
        }
        if (!StringUtils.isEmpty(addressProfile.getAddress_01())) {
            address.setAddressLine1(addressProfile.getAddress_01());
        }
        if (!StringUtils.isEmpty(addressProfile.getAddress_02())) {
            address.setAddressLine2(addressProfile.getAddress_02());
        }
        if (!StringUtils.isEmpty(addressProfile.getCity())) {
            address.setCity(addressProfile.getCity());
        }
        if (!StringUtils.isEmpty(addressProfile.getStateCd())) {
            address.setState(addressProfile.getStateCd());
        }
        if (!StringUtils.isEmpty(addressProfile.getPostalCd())) {
            address.setPostalcode(addressProfile.getPostalCd());
        }
        if (!StringUtils.isEmpty(addressProfile.getCountryCd())) {
            address.setCountryCd(addressProfile.getCountryCd());
        }
        if (!StringUtils.isEmpty(addressProfile.getPhoneNo())) {
            address.setPhone(addressProfile.getPhoneNo());
        }
        if (!StringUtils.isEmpty(addressProfile.getFaxNo())) {
            address.setFax(addressProfile.getFaxNo());
        }

        address.setCreatedDate(getDate());
        address.setUpdatedDate(getDate());
        return address;
    }

    /**
     * Setter method for UserAffiliations data.
     *
     * @param affiliations Entity Object
     * @param affiliation  Request JSON Information
     * @return affiliations entity object
     */
    public static UserAffiliations setUserAffiliations(UserAffiliations affiliations, Affiliation affiliation) {
        if (!StringUtils.isEmpty(affiliation.getInstitutionCd())) {
            affiliations.setInstitutionCd(affiliation.getInstitutionCd());
        }
        if (!StringUtils.isEmpty(affiliation.getInstitutionName())) {
            affiliations.setInstitutionCd(affiliation.getInstitutionName());
        }
        if (!StringUtils.isEmpty(affiliation.getDepartmentCd())) {
            affiliations.setDepartmentCd(affiliation.getDepartmentCd());
        }
        if (!StringUtils.isEmpty(affiliation.getDepartmentName())) {
            affiliations.setDepartmentCd(affiliation.getDepartmentName());
        }
        if (!StringUtils.isEmpty(affiliation.getCity())) {
            affiliations.setTownOrCityName(affiliation.getCity());
        }
        if (!StringUtils.isEmpty(affiliation.getStateCd())) {
            affiliations.setStateOrProvinceName(affiliation.getStateCd());
        }
        if (!StringUtils.isEmpty(affiliation.getFromDate())) {
            affiliations.setStartDt(convertStringToDate(affiliation.getFromDate()));
        }
        if (!StringUtils.isEmpty(affiliation.getToDate())) {
            affiliations.setEndDt(convertStringToDate(affiliation.getToDate()));
        }

        affiliations.setCratedDate(getDate());
        affiliations.setUpdatedDate(getDate());
        return affiliations;
    }

    /**
     * Setter method for UserFunderGrants data.
     *
     * @param grant    Entity Object
     * @param grantNum Request JSON Information
     * @return grant entity object
     */
    public static UserFunderGrants setUserFunderGrants(UserFunderGrants grant, String grantNum) {
        if (!StringUtils.isEmpty(grantNum)) {
            grant.setGrantNum(grantNum);
        }
        grant.setCreatedDate(getDate());
        grant.setUpdatedDate(getDate());
        return grant;
    }

    /**
     * Setter method for UserSocietyDetails data.
     *
     * @param userSocietyDetails Entity Object
     * @param society            Request JSON Information
     * @return userSocietyDetails entity object
     */
    public static UserSocietyDetails setUserSocietyDetails(UserSocietyDetails userSocietyDetails, Society society) {
        if (!StringUtils.isEmpty(society.getSocietyCd())) {
            userSocietyDetails.setSocietyCd(society.getSocietyCd());
        }
        if (!StringUtils.isEmpty(society.getSocietyName())) {
            userSocietyDetails.setSocietyName(society.getSocietyName());
        }
        if (!StringUtils.isEmpty(society.getMembershipNumber())) {
            userSocietyDetails.setMembershipNo(society.getMembershipNumber());
        }
        if (!StringUtils.isEmpty(society.getPromotionCode())) {
            userSocietyDetails.setPromoCode(society.getPromotionCode());
        }
        if (!StringUtils.isEmpty(society.getFromDate())) {
            userSocietyDetails.setStartDt(convertStringToDate(society.getFromDate()));
        }
        if (!StringUtils.isEmpty(society.getToDate())) {
            userSocietyDetails.setEndDt(convertStringToDate(society.getToDate()));
        }
        userSocietyDetails.setCreatedDate(getDate());
        userSocietyDetails.setUpdatedDate(getDate());
        return userSocietyDetails;
    }

    /**
     * Setter method for AreaOfInterest data.
     *
     * @param areaOfInterest Entity Object
     * @param myInterest     Request JSON Information
     * @return areaOfInterest entity object
     */
    public static AreaOfInterest setAreaOfInterest(AreaOfInterest areaOfInterest, MyInterest myInterest) {
        if (!StringUtils.isEmpty(myInterest.getAreaofInterestCd())) {
            areaOfInterest.setAreaOfInterestCd(myInterest.getAreaofInterestCd());
        }
        if (!StringUtils.isEmpty(myInterest.getInterestName())) {
            areaOfInterest.setInterestName(myInterest.getInterestName());
        }
        areaOfInterest.setCreatedDate(getDate());
        areaOfInterest.setUpdatedDate(getDate());
        return areaOfInterest;
    }

    /**
     * Setter method for UserAreaOfInterest data.
     *
     * @param userAreaOfInterest Entity Object
     * @param areaOfInterest     Request JSON Information
     * @return userAreaOfInterest entity object
     */
    public static UserAreaOfInterest setUserAreaOfInterest(UserAreaOfInterest userAreaOfInterest, AreaOfInterest areaOfInterest) {
        userAreaOfInterest.setAreaOfInterest(areaOfInterest);
        userAreaOfInterest.setCreatedDate(getDate());
        userAreaOfInterest.setUpdatedDate(getDate());
        return userAreaOfInterest;
    }

    /**
     * Setter method for Journals data.
     *
     * @param journals     Entity Object
     * @param journalTitle Request JSON Information
     * @return journals entity object
     */
    public static Journals setJournals(Journals journals, String journalTitle) {
        if (!StringUtils.isEmpty(journalTitle)) {
            journals.setJouTitle(journalTitle);
        }
        journals.setCreatedDate(getDate());
        journals.setUpdatedDate(getDate());
        return journals;
    }

    /**
     * Setter method for UserPreferredJournals data.
     *
     * @param userPreferredJournals Entity Object
     * @param journals              Request JSON Information
     * @return userPreferredJournals entity object
     */
    public static UserPreferredJournals setUserPreferredJournals(UserPreferredJournals userPreferredJournals, Journals journals) {
        userPreferredJournals.setJournals(journals);
        return userPreferredJournals;
    }

    /**
     * Method to set user reference data
     *
     * @param userReferenceData  Entity Object
     * @param userServiceRequest Request JSON Information
     * @return
     */
    public static UserReferenceData setUserReference(UserReferenceData userReferenceData, UserServiceRequest userServiceRequest) {
        if (!StringUtils.isEmpty(userServiceRequest.getUserProfile().getOrcidId())) {
            userReferenceData.setOrcidId(userServiceRequest.getUserProfile().getOrcidId());
            userReferenceData.setCreatedDate(getDate());
            userReferenceData.setUpdatedDate(getDate());
        }

        return userReferenceData;
    }

    /**
     * Method to set ProfileAttributeList
     *
     * @param profileAttributeList
     * @param profileVisible
     * @return
     */
    public static ProfileAttributeList setProfileAttributeList(ProfileAttributeList profileAttributeList, ProfileVisible profileVisible) {
        profileAttributeList.setProfileAttribCd(profileVisible.getTitleCd());
        profileAttributeList.setDisplayName(profileVisible.getTitleValue());
        profileAttributeList.setCreatedDate(getDate());
        profileAttributeList.setUpdatedDate(getDate());
        return profileAttributeList;
    }

    /**
     * To get the date.
     *
     * @return
     */
    private static Date getDate() {
        return new Date();
    }

    /**
     * Method to convert String date to Date obj.
     *
     * @param date Date
     * @return
     */
    private static Date convertStringToDate(String date) {
        Date convertedDate = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(CommonConstants.DATE_FORMAT);
            convertedDate = sdf.parse(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return convertedDate;
    }

    /**
     * Method to set code, message & status to Service.
     *
     * @param code    Code
     * @param message Message
     * @param status  Status
     * @return
     */
    public static Service setServiceMessage(String code, String message, String status) {
        Service service = new Service();
        com.wiley.gr.ace.sharedservices.payload.Error error = new Error();
        error.setCode(Integer.parseInt(code));
        error.setMessage(message);
        service.setStatus(status);
        service.setError(error);
        return service;
    }

    /**
     * Method to set the user profile information and return back.
     *
     * @param user
     * @return
     */
    public static UserProfile getUserProfileInfo(Users user) {
        UserProfile userProfile = new UserProfile();
        if (null != user.getAuthorProfileByUserId()) {
            if (!StringUtils.isEmpty(user.getAuthorProfileByUserId().getTitleCd())) {
                userProfile.setTitleCd(user.getAuthorProfileByUserId().getTitleCd());
            }
            if (!StringUtils.isEmpty(user.getFirstName())) {
                userProfile.setFirstName(user.getFirstName());
            }
            if (!StringUtils.isEmpty(user.getAuthorProfileByUserId().getMiddleName())) {
                userProfile.setMiddleName(user.getAuthorProfileByUserId().getMiddleName());
            }
            if (!StringUtils.isEmpty(user.getLastName())) {
                userProfile.setLastName(user.getLastName());
            }
            if (!StringUtils.isEmpty(user.getAuthorProfileByUserId().getSuffixCd())) {
                userProfile.setSuffixCd(user.getAuthorProfileByUserId().getSuffixCd());
            }
            if (!StringUtils.isEmpty(user.getAuthorProfileByUserId().getAlternativeName())) {
                userProfile.setAlternativeName(user.getAuthorProfileByUserId().getAlternativeName());
            }
            if (!StringUtils.isEmpty(user.getAuthorProfileByUserId().getIndustryCd())) {
                userProfile.setIndustryCd(user.getAuthorProfileByUserId().getIndustryCd());
            }
            if (!StringUtils.isEmpty(user.getAuthorProfileByUserId().getJobCategoryCd())) {
                userProfile.setJobCategoryCd(user.getAuthorProfileByUserId().getJobCategoryCd());
            }

            Set<UserReferenceData> userReferenceDatas = user.getUserReferenceDatasForUserId();
            for (UserReferenceData userReferenceData : userReferenceDatas) {
                userProfile.setOrcidId(userReferenceData.getOrcidId());
            }
            if (!StringUtils.isEmpty(user.getAuthorProfileByUserId().getOptInPromoteFlg())) {
                userProfile.setRecieveEmailsFlag(user.getAuthorProfileByUserId().getOptInPromoteFlg());
            }
            if (user.getAuthorProfileByUserId().getProfileVisibleFlg() != null) {
                userProfile.setProfileVisibleFlag(user.getAuthorProfileByUserId().getProfileVisibleFlg());
            }
            if (!StringUtils.isEmpty(user.getPrimaryEmailAddr())) {
                userProfile.setPrimaryEmailAddress(user.getPrimaryEmailAddr());
            }
            if (null != user.getAuthorProfileByUserId() && null != user.getAuthorProfileByUserId().getUserSecondaryEmailAddr()) {
                userProfile.setRecoveryEmailAddress(user.getAuthorProfileByUserId().getUserSecondaryEmailAddr().getSecondaryEmailAddr());
            }
        }
        return userProfile;
    }

    /**
     * Method to get address info and set back to pojo.
     *
     * @param addressEntity
     * @return
     */
    public static com.wiley.gr.ace.sharedservices.profile.Address getAddressInfo(Address addressEntity) {
        com.wiley.gr.ace.sharedservices.profile.Address address = new com.wiley.gr.ace.sharedservices.profile.Address();
        if (null != addressEntity.getAddressId() && addressEntity.getAddressId() > 0) {
            address.setId("" + addressEntity.getAddressId());
        }
        if (!StringUtils.isEmpty(addressEntity.getTitleCd())) {
            address.setTitleCd(addressEntity.getTitleCd());
        }
        if (!StringUtils.isEmpty(addressEntity.getFirstName())) {
            address.setFirstName(addressEntity.getFirstName());
        }
        if (!StringUtils.isEmpty(addressEntity.getLastName())) {
            address.setLastName(addressEntity.getLastName());
        }
        if (!StringUtils.isEmpty(addressEntity.getSuffixCd())) {
            address.setSuffixCd(addressEntity.getSuffixCd());
        }
        if (!StringUtils.isEmpty(addressEntity.getInstitutionCd())) {
            address.setInstitutionCd(addressEntity.getInstitutionCd());
        }
        if (!StringUtils.isEmpty(addressEntity.getInstitutionName())) {
            address.setInstitutionName(addressEntity.getInstitutionName());
        }
        if (!StringUtils.isEmpty(addressEntity.getDepartmentCd())) {
            address.setDepartmentCd(addressEntity.getDepartmentCd());
        }
        if (!StringUtils.isEmpty(addressEntity.getDepartmentName())) {
            address.setDepartmentName(addressEntity.getDepartmentName());
        }
        if (!StringUtils.isEmpty(addressEntity.getAddressLine1())) {
            address.setAddress_01(addressEntity.getAddressLine1());
        }
        if (!StringUtils.isEmpty(addressEntity.getAddressLine2())) {
            address.setAddress_02(addressEntity.getAddressLine2());
        }
        if (!StringUtils.isEmpty(addressEntity.getCity())) {
            address.setCity(addressEntity.getCity());
        }
        if (!StringUtils.isEmpty(addressEntity.getState())) {
            address.setStateCd(addressEntity.getState());
        }
        if (!StringUtils.isEmpty(addressEntity.getCountryCd())) {
            address.setCountryCd(addressEntity.getCountryCd());
        }
        if (!StringUtils.isEmpty(addressEntity.getPostalcode())) {
            address.setPostalCd(addressEntity.getPostalcode());
        }
        if (!StringUtils.isEmpty(addressEntity.getPhone())) {
            address.setPhoneNo(addressEntity.getPhone());
        }
        if (!StringUtils.isEmpty(addressEntity.getFax())) {
            address.setFaxNo(addressEntity.getFax());
        }
        if (!StringUtils.isEmpty(addressEntity.getInstitutionCd())) {
            address.setInstitutionCd(addressEntity.getInstitutionCd());
        }
        if (!StringUtils.isEmpty(addressEntity.getInstitutionName())) {
            address.setInstitutionName(addressEntity.getInstitutionName());
        }
        if (!StringUtils.isEmpty(addressEntity.getDepartmentCd())) {
            address.setDepartmentCd(addressEntity.getDepartmentCd());
        }
        if (!StringUtils.isEmpty(addressEntity.getDepartmentName())) {
            address.setDepartmentName(addressEntity.getDepartmentName());
        }
        if (!StringUtils.isEmpty(addressEntity.getSuffixCd())) {
            address.setSuffixCd(addressEntity.getSuffixCd());
        }
        return address;
    }

    /**
     * Method to get Affiliations and set it to pojo.
     *
     * @param userAffiliations
     * @return
     */
    public static Affiliation getAffiliation(UserAffiliations userAffiliations) {
        Affiliation affiliation = new Affiliation();
        if (null != userAffiliations.getAffiliationId() && userAffiliations.getAffiliationId() > 0) {
            affiliation.setId("" + userAffiliations.getAffiliationId());
        }
        if (!StringUtils.isEmpty(userAffiliations.getInstitutionCd())) {
            affiliation.setInstitutionCd(userAffiliations.getInstitutionCd());
        }
        if (!StringUtils.isEmpty(userAffiliations.getInstitutionName())) {
            affiliation.setInstitutionName(userAffiliations.getInstitutionName());
        }
        if (!StringUtils.isEmpty(userAffiliations.getDepartmentCd())) {
            affiliation.setDepartmentCd(userAffiliations.getDepartmentCd());
        }
        if (!StringUtils.isEmpty(userAffiliations.getDepartmentName())) {
            affiliation.setDepartmentName(userAffiliations.getDepartmentName());
        }
        if (!StringUtils.isEmpty(userAffiliations.getTownOrCityName())) {
            affiliation.setCity(userAffiliations.getTownOrCityName());
        }
        if (!StringUtils.isEmpty(userAffiliations.getStateOrProvinceName())) {
            affiliation.setCountryCd(userAffiliations.getStateOrProvinceName());
        }
        if (null != userAffiliations.getStartDt()) {
            affiliation.setFromDate("" + userAffiliations.getStartDt());
        }
        if (null != userAffiliations.getEndDt()) {
            affiliation.setToDate("" + userAffiliations.getEndDt());
        }
        return affiliation;
    }

    /**
     * Method to get Society info and set it to pojo.
     *
     * @param userSocietyDetails
     * @return
     */
    public static Society getSociety(UserSocietyDetails userSocietyDetails) {
        Society society = new Society();
        if (null != userSocietyDetails.getSocietyId() && userSocietyDetails.getSocietyId() > 0) {
            society.setId("" + userSocietyDetails.getSocietyId());
        }
        if (!StringUtils.isEmpty(userSocietyDetails.getSocietyName())) {
            society.setMembershipNumber(userSocietyDetails.getSocietyName());
        }
        if (!StringUtils.isEmpty(userSocietyDetails.getMembershipNo())) {
            society.setMembershipNumber(userSocietyDetails.getMembershipNo());
        }
        if (!StringUtils.isEmpty(userSocietyDetails.getPromoCode())) {
            society.setPromotionCode(userSocietyDetails.getPromoCode());
        }
        if (!StringUtils.isEmpty(userSocietyDetails.getSocietyCd())) {
            society.setSocietyCd(userSocietyDetails.getSocietyCd());
        }
        if (null != userSocietyDetails.getStartDt()) {
            society.setFromDate("" + userSocietyDetails.getStartDt());
        }
        if (null != userSocietyDetails.getEndDt()) {
            society.setToDate("" + userSocietyDetails.getEndDt());
        }
        return society;
    }

    /**
     * Method to get interests and set it to pojo.
     *
     * @param areaOfInterest
     * @return
     */
    public static MyInterest getMyInterest(AreaOfInterest areaOfInterest) {
        MyInterest myInterest = new MyInterest();
        if (!StringUtils.isEmpty(areaOfInterest.getAreaOfInterestCd())) {
            myInterest.setId(areaOfInterest.getAreaOfInterestCd());
            myInterest.setAreaofInterestCd(areaOfInterest.getAreaOfInterestCd());
        }
        if (!StringUtils.isEmpty(areaOfInterest.getInterestName())) {
            myInterest.setInterestName(areaOfInterest.getInterestName());
        }
        return myInterest;
    }

    public static PreferredJournal getPreferredJournal(Journals journal) {
        PreferredJournal preferredJournal = new PreferredJournal();
        preferredJournal.setId("" + journal.getJournalId());
        preferredJournal.setJournalTitle(journal.getJouTitle());
        //preferredJournal.setJournalId(journal.getJournalId());
        return preferredJournal;
    }

    public static Alert getAlert(Alerts userAlert) {
        Alert alert = new Alert();
        alert.setId(userAlert.getAlertCd());
        alert.setAlertName(userAlert.getAlertName());
        return alert;
    }

}
