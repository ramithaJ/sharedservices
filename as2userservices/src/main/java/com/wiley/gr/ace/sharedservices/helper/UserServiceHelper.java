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
import com.wiley.gr.ace.sharedservices.payload.Error;
import com.wiley.gr.ace.sharedservices.payload.Service;
import com.wiley.gr.ace.sharedservices.payload.UserServiceRequest;
import com.wiley.gr.ace.sharedservices.persistence.entity.Address;
import com.wiley.gr.ace.sharedservices.persistence.entity.*;
import com.wiley.gr.ace.sharedservices.persistence.entity.UserProfile;
import com.wiley.gr.ace.sharedservices.profile.*;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * @author kkalyan
 */
public class UserServiceHelper {


    //Logger Instance
    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceHelper.class);

    private UserServiceHelper() {

    }

    /**
     * Setter method for User Profile Information.
     *
     * @param userServiceRequest Request JSON Information
     * @param user               Entity Object
     * @return user entity object
     */
    public static Users setUserInformation(UserServiceRequest userServiceRequest, Users user) {
        user.setFirstName(userServiceRequest.getUserProfile().getFirstName());
        user.setLastName(userServiceRequest.getUserProfile().getLastName());
        user.setPrimaryEmailAddr(userServiceRequest.getUserProfile().getPrimaryEmailAddress());
        user.setUpdatedDate(getDate());
        return user;
    }

    /**
     * Setter method for Author Profile data.
     *
     * @param userServiceRequest Request JSON Information
     * @param userProfile        Entity Object
     * @return userProfile entity
     */
    public static UserProfile setUserProfileInformation(UserServiceRequest userServiceRequest, UserProfile userProfile) {

        userProfile.setTitleCd(userServiceRequest.getUserProfile().getTitleCd());

        //TODO: Profile Pic

        userProfile.setMiddleName(userServiceRequest.getUserProfile().getMiddleName());
        userProfile.setSuffixCd(userServiceRequest.getUserProfile().getSuffixCd());
        userProfile.setAlternativeName(userServiceRequest.getUserProfile().getAlternativeName());
        userProfile.setIndustryCd(userServiceRequest.getUserProfile().getIndustryCd());
        userProfile.setJobCategoryCd(userServiceRequest.getUserProfile().getJobCategoryCd());
        userProfile.setProfileVisibleFlg(userServiceRequest.getUserProfile().getProfileVisibleFlag());
        userProfile.setOptInPromoteFlg(userServiceRequest.getUserProfile().getRecieveEmailsFlag());


        userProfile.setUpdatedDate(getDate());
        return userProfile;
    }


    private static boolean isUserProfileValidate1(UserServiceRequest userServiceRequest) {
        if (!StringUtils.isEmpty(userServiceRequest.getUserProfile().getTitleCd()) || !StringUtils.isEmpty(userServiceRequest.getUserProfile().getMiddleName()) || StringUtils.isEmpty(userServiceRequest.getUserProfile().getSuffixCd())) {
            return true;
        }
        return false;
    }

    private static boolean isUserProfileValidate2(UserServiceRequest userServiceRequest) {
        if (!StringUtils.isEmpty(userServiceRequest.getUserProfile().getAlternativeName()) || StringUtils.isEmpty(userServiceRequest.getUserProfile().getIndustryCd()) || StringUtils.isEmpty(userServiceRequest.getUserProfile().getJobCategoryCd())) {
            return true;
        }
        return false;
    }

    private static boolean isUserProfileValidate3(UserServiceRequest userServiceRequest) {
        if ((userServiceRequest.getUserProfile().getProfileVisibleFlag() != ' ') ||
                !StringUtils.isEmpty(userServiceRequest.getUserProfile().getRecieveEmailsFlag())) {
            return true;
        }
        return false;
    }


    /**
     * Method to validate the request against user profile.
     *
     * @param userServiceRequest
     * @return
     */
    public static boolean isUserProfileDataExists(UserServiceRequest userServiceRequest) {
        boolean isDataExists = false;
        if (isUserProfileValidate1(userServiceRequest) || isUserProfileValidate2(userServiceRequest) || isUserProfileValidate3(userServiceRequest)) {
            isDataExists = true;
        }
        return isDataExists;
    }

    /**
     * Setter method for UserSecondaryEmailAddr data.
     *
     * @param emailId            emailId
     * @param secondaryEmailAddr Entity Object
     * @param user               Entity Object
     * @return secondaryEmailAddr entity object
     */
    public static UserSecondaryEmailAddr setUserSecondaryEmailAddr(String emailId, UserSecondaryEmailAddr secondaryEmailAddr, Users user) {
        if (null != emailId) {
            secondaryEmailAddr.setSecondaryEmailAddr(emailId);
            secondaryEmailAddr.setUpdatedDate(getDate());
            secondaryEmailAddr.setUsersByUserId(user);
            return secondaryEmailAddr;
        } else {
            return null;
        }

    }

    /**
     * Setter method to UserAlerts data.
     *
     * @param alerts
     * @param alertType
     * @return
     */
    public static UserAlerts setUserAlerts(UserAlerts alerts, AlertType alertType) {
        if (!Character.isWhitespace(alertType.getEmail())) {
            alerts.setEmailFlg(alertType.getEmail());
        }
        if (!Character.isWhitespace(alertType.getOnScreen())) {
            alerts.setOnScreenFlg(alertType.getOnScreen());
        }
        return alerts;
    }

    /**
     * Setter method for ResearchFunders data.
     *
     * @param researchFunders
     * @param funder
     * @return
     */
    public static ResearchFunders setResearchFunders(ResearchFunders researchFunders, Funder funder) {
        if (!StringUtils.isEmpty(funder.getResearchFunderName())) {
            researchFunders.setFunderName(funder.getResearchFunderName());
        }
        if (!StringUtils.isEmpty(funder.getResearchFunderDoi())) {
            researchFunders.setFunderDoi(funder.getResearchFunderDoi());
        }
        researchFunders.setUpdatedDate(UserServiceHelper.getDate());
        return researchFunders;
    }

    /**
     * Setter method for Address data.
     *
     * @param address        Entity Object
     * @param addressProfile Request JSON Information
     * @return address entity object
     */
    public static Address setAddress(Address address, com.wiley.gr.ace.sharedservices.profile.Address addressProfile) {
        address.setTitleCd(addressProfile.getTitleCd());
        address.setFirstName(addressProfile.getFirstName());
        address.setLastName(addressProfile.getLastName());
        address.setSuffixCd(addressProfile.getSuffixCd());
        address.setInstitutionCd(addressProfile.getInstitutionCd());
        address.setInstitutionName(addressProfile.getInstitutionName());
        address.setDepartmentCd(addressProfile.getDepartmentCd());
        address.setDepartmentName(addressProfile.getDepartmentName());
        address.setAddressLine1(addressProfile.getAddress01());
        address.setAddressLine2(addressProfile.getAddress02());
        address.setCity(addressProfile.getCity());
        address.setState(addressProfile.getStateCd());
        address.setPostalcode(addressProfile.getPostalCd());
        address.setCountryCd(addressProfile.getCountryCd());
        address.setFax(addressProfile.getFaxNo());
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
        if (!StringUtils.isEmpty(affiliation.getCountryCd())) {
            affiliations.setCountryCd(affiliation.getCountryCd());
        }
        if (!StringUtils.isEmpty(affiliation.getInstitutionCd())) {
            affiliations.setInstitutionCd(affiliation.getInstitutionCd());
        }
        if (!StringUtils.isEmpty(affiliation.getInstitutionName())) {
            affiliations.setInstitutionName(affiliation.getInstitutionName());
        }
        if (!StringUtils.isEmpty(affiliation.getDepartmentCd())) {
            affiliations.setDepartmentCd(affiliation.getDepartmentCd());
        }
        if (!StringUtils.isEmpty(affiliation.getDepartmentName())) {
            affiliations.setDepartmentName(affiliation.getDepartmentName());
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
        userReferenceData.setOrcidId(userServiceRequest.getUserProfile().getOrcidId());
        userReferenceData.setEcid(userServiceRequest.getUserProfile().getEcid());
        userReferenceData.setUpdatedDate(getDate());
        return userReferenceData;
    }

    /**
     * Method to set coauthor details.
     *
     * @param authCoauthDetails
     * @param coAuthor
     * @return
     */
    public static AuthCoauthDetails setAuthCoauthDetails(AuthCoauthDetails authCoauthDetails, CoAuthor coAuthor) {
        if (!StringUtils.isEmpty(coAuthor.getTitle())) {
            authCoauthDetails.setCoauthTitle(coAuthor.getTitle());
        }
        if (!StringUtils.isEmpty(coAuthor.getFirstName())) {
            authCoauthDetails.setCoauthFirstName(coAuthor.getFirstName());
        }
        if (!StringUtils.isEmpty(coAuthor.getLastName())) {
            authCoauthDetails.setCoauthLastName(coAuthor.getLastName());
        }
        if (!StringUtils.isEmpty(coAuthor.getPhone())) {
            authCoauthDetails.setCoauthPhone(coAuthor.getPhone());
        }
        if (!StringUtils.isEmpty(coAuthor.getEmail())) {
            authCoauthDetails.setCoauthEmailAddr(coAuthor.getEmail());
        }
        if (!StringUtils.isEmpty(coAuthor.getDepartmentCd())) {
            authCoauthDetails.setCoauthDepartmentCd(coAuthor.getDepartmentCd());
        }
        if (!StringUtils.isEmpty(coAuthor.getDepartmentName())) {
            authCoauthDetails.setCoauthDeptName(coAuthor.getDepartmentName());
        }
        if (!StringUtils.isEmpty(coAuthor.getInstitutionCd())) {
            authCoauthDetails.setCoauthInstitutionCd(coAuthor.getInstitutionCd());
        }
        if (!StringUtils.isEmpty(coAuthor.getInstitutionName())) {
            authCoauthDetails.setCoauthInstitutionName(coAuthor.getInstitutionName());
        }
        authCoauthDetails.setUpdatedDate(getDate());
        return authCoauthDetails;
    }

    /**
     * Method to get coauthor details.
     *
     * @param authCoauthDetails
     * @param coAuthor
     * @return
     */
    public static CoAuthor getAuthCoauthDetails(AuthCoauthDetails authCoauthDetails, CoAuthor coAuthor) {

        if (authCoauthDetails.getAuthCoauthId() > 0) {
            coAuthor.setId("" + authCoauthDetails.getAuthCoauthId());
        }
        if (!StringUtils.isEmpty(authCoauthDetails.getCoauthTitle())) {
            coAuthor.setTitle(authCoauthDetails.getCoauthTitle());
        }
        if (!StringUtils.isEmpty(authCoauthDetails.getCoauthFirstName())) {
            coAuthor.setFirstName(authCoauthDetails.getCoauthFirstName());
        }
        if (!StringUtils.isEmpty(authCoauthDetails.getCoauthLastName())) {
            coAuthor.setLastName(authCoauthDetails.getCoauthLastName());
        }
        if (!StringUtils.isEmpty(authCoauthDetails.getCoauthPhone())) {
            coAuthor.setPhone(authCoauthDetails.getCoauthPhone());
        }
        if (!StringUtils.isEmpty(authCoauthDetails.getCoauthEmailAddr())) {
            coAuthor.setEmail(authCoauthDetails.getCoauthEmailAddr());
        }
        if (!StringUtils.isEmpty(authCoauthDetails.getCoauthDepartmentCd())) {
            coAuthor.setDepartmentCd(authCoauthDetails.getCoauthDepartmentCd());
        }
        if (!StringUtils.isEmpty(authCoauthDetails.getCoauthDeptName())) {
            coAuthor.setDepartmentName(authCoauthDetails.getCoauthDeptName());
        }
        if (!StringUtils.isEmpty(authCoauthDetails.getCoauthInstitutionCd())) {
            coAuthor.setInstitutionCd(authCoauthDetails.getCoauthInstitutionCd());
        }
        if (!StringUtils.isEmpty(authCoauthDetails.getCoauthInstitutionName())) {
            coAuthor.setInstitutionName(authCoauthDetails.getCoauthInstitutionName());
        }

        return coAuthor;
    }

    /**
     * Method to set User Profile Attributes.
     *
     * @param profileAttributeList
     * @param authorProfile
     * @param userProfileAttribVisible
     * @return
     */
    public static UserProfileAttribVisible setProfileAttributeList(ProfileAttributeList profileAttributeList, UserProfile authorProfile, UserProfileAttribVisible userProfileAttribVisible) {
        userProfileAttribVisible.setProfileAttributeList(profileAttributeList);
        userProfileAttribVisible.setUserProfile(authorProfile);
        userProfileAttribVisible.setUpdatedDate(getDate());
        return userProfileAttribVisible;
    }

    /**
     * To get the date.
     *
     * @return
     */
    public static Date getDate() {
        return new Date();
    }

    /**
     * Method to convert String date to Date obj.
     *
     * @param date Date
     * @return
     */
    public static Date convertStringToDate(String date) {
        Date convertedDate = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(CommonConstants.DATE_FORMAT);
            convertedDate = sdf.parse(date);
        } catch (Exception e) {
            LOGGER.error("Exception Occurred during convertStringToDate...", e);
        }
        return convertedDate;
    }

    public static String convertDateToString(Date date) {
        String convertedDate = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(CommonConstants.DATE_FORMAT);
            convertedDate = sdf.format(date);
        } catch (Exception e) {
            LOGGER.error("Exception Occurred during convertStringToDate...", e);
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
    public static com.wiley.gr.ace.sharedservices.profile.UserProfile getUserProfileInfo(Users user) {
        com.wiley.gr.ace.sharedservices.profile.UserProfile userProfile = new com.wiley.gr.ace.sharedservices.profile.UserProfile();

        if (!StringUtils.isEmpty(user.getFirstName())) {
            userProfile.setFirstName(user.getFirstName());
        }
        if (!StringUtils.isEmpty(user.getLastName())) {
            userProfile.setLastName(user.getLastName());
        }
        if (!StringUtils.isEmpty(user.getPrimaryEmailAddr())) {
            userProfile.setPrimaryEmailAddress(user.getPrimaryEmailAddr());
        }

        if (null != user.getUserProfileByUserId()) {
            if (!StringUtils.isEmpty(user.getUserProfileByUserId().getTitleCd())) {
                userProfile.setTitleCd(user.getUserProfileByUserId().getTitleCd());
            }

            if (!StringUtils.isEmpty(user.getUserProfileByUserId().getMiddleName())) {
                userProfile.setMiddleName(user.getUserProfileByUserId().getMiddleName());
            }

            if (!StringUtils.isEmpty(user.getUserProfileByUserId().getSuffixCd())) {
                userProfile.setSuffixCd(user.getUserProfileByUserId().getSuffixCd());
            }
            if (!StringUtils.isEmpty(user.getUserProfileByUserId().getAlternativeName())) {
                userProfile.setAlternativeName(user.getUserProfileByUserId().getAlternativeName());
            }
            if (!StringUtils.isEmpty(user.getUserProfileByUserId().getIndustryCd())) {
                userProfile.setIndustryCd(user.getUserProfileByUserId().getIndustryCd());
            }
            if (!StringUtils.isEmpty(user.getUserProfileByUserId().getJobCategoryCd())) {
                userProfile.setJobCategoryCd(user.getUserProfileByUserId().getJobCategoryCd());
            }

            if (!StringUtils.isEmpty(user.getUserProfileByUserId().getOptInPromoteFlg())) {
                userProfile.setRecieveEmailsFlag(user.getUserProfileByUserId().getOptInPromoteFlg());
            }
            if (user.getUserProfileByUserId().getProfileVisibleFlg() != null) {
                userProfile.setProfileVisibleFlag(user.getUserProfileByUserId().getProfileVisibleFlg());
            }

            if (null != user.getUserProfileByUserId()) {
                Set<UserSecondaryEmailAddr> userSecondaryEmailAddrsForUserId = user.getUserSecondaryEmailAddrsForUserId();
                List<String> secondaryEmail = new LinkedList();
                for (UserSecondaryEmailAddr userSecondaryEmailAddr : userSecondaryEmailAddrsForUserId) {
                    secondaryEmail.add(userSecondaryEmailAddr.getSecondaryEmailAddr());
                }
                userProfile.setRecoveryEmailAddress(StringUtils.join(secondaryEmail, CommonConstants.COMMA));
            }
        }

        UserReferenceData userReferenceData = user.getUserReferenceDataByUserId();
        if (null != userReferenceData) {
            if (!StringUtils.isEmpty(userReferenceData.getOrcidId())) {
                userProfile.setOrcidId(userReferenceData.getOrcidId());
            }
            if (!StringUtils.isEmpty(userReferenceData.getEcid())) {
                userProfile.setEcid(userReferenceData.getEcid());
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
            address.setAddress01(addressEntity.getAddressLine1());
        }
        if (!StringUtils.isEmpty(addressEntity.getAddressLine2())) {
            address.setAddress02(addressEntity.getAddressLine2());
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
        if (!StringUtils.isEmpty(userAffiliations.getCountryCd())) {
            affiliation.setCountryCd(userAffiliations.getCountryCd());
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
            affiliation.setStateCd(userAffiliations.getStateOrProvinceName());
        }
        if (null != userAffiliations.getStartDt()) {
            affiliation.setFromDate(convertDateToString(userAffiliations.getStartDt()));
        }
        if (null != userAffiliations.getEndDt()) {
            affiliation.setToDate(convertDateToString(userAffiliations.getEndDt()));
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
        if (null != userSocietyDetails.getSocieties().getSocietyId() && userSocietyDetails.getSocieties().getSocietyId() > 0) {
            society.setId("" + userSocietyDetails.getSocieties().getSocietyId());
        }
        if (!StringUtils.isEmpty(userSocietyDetails.getSocieties().getSocietyName())) {
            society.setSocietyName(userSocietyDetails.getSocieties().getSocietyName());
        }
        if (!StringUtils.isEmpty(userSocietyDetails.getMembershipNo())) {
            society.setMembershipNumber(userSocietyDetails.getMembershipNo());
        }
        if (!StringUtils.isEmpty(userSocietyDetails.getPromoCode())) {
            society.setPromotionCode(userSocietyDetails.getPromoCode());
        }
        if (!StringUtils.isEmpty(userSocietyDetails.getSocieties().getSocietyCd())) {
            society.setSocietyCd(userSocietyDetails.getSocieties().getSocietyCd());
        }
        if (null != userSocietyDetails.getStartDt()) {
            society.setFromDate(convertDateToString(userSocietyDetails.getStartDt()));
        }
        if (null != userSocietyDetails.getEndDt()) {
            society.setToDate(convertDateToString(userSocietyDetails.getEndDt()));
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

        return myInterest;
    }

    /**
     * Get PreferredJournals
     *
     * @param journal
     * @return
     */
    public static PreferredJournal getPreferredJournal(Journals journal) {
        PreferredJournal preferredJournal = new PreferredJournal();
        preferredJournal.setId("" + journal.getJournalId());
        preferredJournal.setJournalTitle(journal.getJouTitle());
        preferredJournal.setJournalId("" + journal.getJournalId());
        return preferredJournal;
    }

    /**
     * Get Alerts
     *
     * @param userAlert
     * @return
     */
    public static Alert getAlert(Alerts userAlert) {
        Alert alert = new Alert();
        alert.setId(userAlert.getAlertCd());
        alert.setAlertName(userAlert.getAlertName());
        alert.setAlertCd(userAlert.getAlertCd());
        return alert;
    }

    /**
     * Helper method to check whether String is number or not.
     *
     * @param str
     * @return
     */
    public static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }


}
