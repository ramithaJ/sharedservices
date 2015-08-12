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
import com.wiley.gr.ace.sharedservices.common.Property;
import com.wiley.gr.ace.sharedservices.exceptions.SharedServiceException;
import com.wiley.gr.ace.sharedservices.payload.UserServiceRequest;
import com.wiley.gr.ace.sharedservices.persistence.entity.*;
import com.wiley.gr.ace.sharedservices.persistence.entity.UserProfile;
import com.wiley.gr.ace.sharedservices.profile.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by KKALYAN on 8/4/2015.
 */
public class UserRepositoryHelper extends Property {

    //Logger Instance
    private static final Logger LOGGER = LoggerFactory.getLogger(UserRepositoryHelper.class);

    @Autowired
    private SessionFactory sessionFactory;

    /**
     * Generic method to get Entity Object from the DB primary id.
     *
     * @param columnName
     * @param primaryId
     * @param entityClass
     * @param <T>
     * @return
     * @throws SharedServiceException
     */
    public <T> Object getEntityById(String columnName, String primaryId, Class<T> entityClass) throws SharedServiceException {
        return getEntity(columnName, primaryId, entityClass, true);
    }

    /**
     * Generic method to get Entity Object from the DB primary id.
     *
     * @param columnName
     * @param primaryId
     * @param entityClass
     * @param typeCast
     * @param <T>
     * @return
     * @throws SharedServiceException
     */
    public <T> Object getEntity(String columnName, String primaryId, Class<T> entityClass, Boolean typeCast) throws SharedServiceException {
        //Get the session from sessionFactory pool.
        Session session = null;
        Object classObj = null;
        try {
            LOGGER.info("Getting Entity...");
            session = sessionFactory.openSession();
            //Begin the transaction.
            session.beginTransaction();
            //Get the user role object.
            Criteria criteria = session.createCriteria(entityClass);
            //Check if typcast is true. Then typecast the input parameter.
            if (typeCast) {
                criteria.add(Restrictions.eq(columnName, Integer.parseInt(primaryId)));
            } else {
                criteria.add(Restrictions.eq(columnName, primaryId));
            }
            classObj = criteria.uniqueResult();

            //Flush the session
            session.flush();
            //Clear session
            session.clear();
            //Commit the transaction.
            session.getTransaction().commit();
        } catch (Exception e) {
            LOGGER.error("Exception Occurred during get entity...", e);
            throw new SharedServiceException(CommonConstants.ERROR_CODE_100, "Error :" + e);
        } finally {
            //Close the session
            if (null != session) {
                session.close();
            }
        }
        return classObj;
    }

    /**
     * Repository method to validate the request parameters.
     *
     * @param userServiceRequest
     * @throws SharedServiceException
     */
    public void validateRequest(UserServiceRequest userServiceRequest) throws SharedServiceException {
        if (StringUtils.isEmpty(userServiceRequest.getUserProfile().getFirstName())) {
            throw new SharedServiceException(CommonConstants.ERROR_CODE_103, userServiceError103);
        }
        if (StringUtils.isEmpty(userServiceRequest.getUserProfile().getLastName())) {
            throw new SharedServiceException(CommonConstants.ERROR_CODE_104, userServiceError104);
        }
        if (StringUtils.isEmpty(userServiceRequest.getUserProfile().getPrimaryEmailAddress())) {
            throw new SharedServiceException(CommonConstants.ERROR_CODE_105, userServiceError105);
        }
        if (StringUtils.isEmpty(userServiceRequest.getUserProfile().getPassword())) {
            throw new SharedServiceException(CommonConstants.ERROR_CODE_107, userServiceError107);
        }
    }

    /**
     * Repository method to add Profile Visible list.
     *
     * @param session
     * @param user
     * @param profileVisibleList
     * @param authorProfile
     * @param userProfileAttribVisibleSet
     * @return
     * @throws SharedServiceException
     */
    public void addProfileVisible(Session session, Users user, List<ProfileVisible> profileVisibleList, UserProfile authorProfile, Set<UserProfileAttribVisible> userProfileAttribVisibleSet) throws SharedServiceException {
        LOGGER.debug("Set Profile Visible Id...");
        for (ProfileVisible profileVisible : profileVisibleList) {
            //Get the Profile Attribute List
            ProfileAttributeList profileAttributeList = (ProfileAttributeList) getEntity(CommonConstants.PROFILE_ATTR_CD, profileVisible.getTitleCd(), ProfileAttributeList.class, false);
            if (null == profileAttributeList) {
                throw new SharedServiceException(CommonConstants.ERROR_CODE_114, profileVisible.getTitleCd() + "-" + userServiceError114);
            } else {
                UserProfileAttribVisibleId userProfileAttribVisibleId = new UserProfileAttribVisibleId();
                userProfileAttribVisibleId.setUserId(user.getUserId());
                userProfileAttribVisibleId.setProfileAttribCd(profileVisible.getTitleCd());

                //Set User Profile Attribute Visible
                UserProfileAttribVisible userProfileAttribVisible = new UserProfileAttribVisible();
                userProfileAttribVisible = UserServiceHelper.setProfileAttributeList(profileAttributeList, authorProfile, userProfileAttribVisible);
                userProfileAttribVisible.setId(userProfileAttribVisibleId);
                userProfileAttribVisible.setProfileVisibilityFlg(profileVisible.getTitleValue());
                userProfileAttribVisible.setCreatedDate(UserServiceHelper.getDate());
                userProfileAttribVisible.setUpdatedDate(UserServiceHelper.getDate());
                userProfileAttribVisible.setUsersByCreatedBy(user);
                userProfileAttribVisible.setUsersByUpdatedBy(user);
                session.save(userProfileAttribVisible);
                userProfileAttribVisibleSet.add(userProfileAttribVisible);
            }
        }
    }

    /**
     * Repository method to add User Reference data.
     *
     * @param session
     * @param user
     * @return
     */
    public void addUserReferenceData(Session session, Users user, UserServiceRequest userServiceRequest) throws SharedServiceException {
        UserReferenceData userReferenceData = new UserReferenceData();
        userReferenceData = UserServiceHelper.setUserReference(userReferenceData, userServiceRequest);
        userReferenceData.setCreatedDate(UserServiceHelper.getDate());
        userReferenceData.setUsersByCreatedBy(user);
        userReferenceData.setUsersByUpdatedBy(user);
        userReferenceData.setUsersByUserId(user);
        session.save(userReferenceData);
    }

    /**
     * Repository method to add address.
     *
     * @param session
     * @param user
     * @param userAddressesSet
     * @param addressList
     * @throws SharedServiceException
     */
    public void addAddress(Session session, Users user, Set<UserAddresses> userAddressesSet, List<com.wiley.gr.ace.sharedservices.profile.Address> addressList) throws SharedServiceException {
        for (com.wiley.gr.ace.sharedservices.profile.Address addressProfile : addressList) {
            if (StringUtils.isEmpty(addressProfile.getType())) {
                throw new SharedServiceException(CommonConstants.ERROR_CODE_115, userServiceError115);
            }
            UserAddresses userAddresses = new UserAddresses();
            com.wiley.gr.ace.sharedservices.persistence.entity.Address address = new com.wiley.gr.ace.sharedservices.persistence.entity.Address();
            address = UserServiceHelper.setAddress(address, addressProfile);
            address.setCreatedDate(UserServiceHelper.getDate());
            address.setUpdatedDate(UserServiceHelper.getDate());
            address.setUsersByCreatedBy(user);
            address.setUsersByUpdatedBy(user);
            session.save(address);
            AddressType addressType = (AddressType) getEntity(CommonConstants.ADDRESS_TYPE_CD, addressProfile.getType(), AddressType.class, false);
            if (null == addressType) {
                throw new SharedServiceException(CommonConstants.ERROR_CODE_109, addressProfile.getType() + "-" + userServiceError109);
            } else {
                userAddresses.setAddressType(addressType);
                userAddresses.setAddress(address);
                userAddresses.setUsersByUserId(user);
                userAddresses.setCreatedDate(UserServiceHelper.getDate());
                userAddresses.setUpdatedDate(UserServiceHelper.getDate());
                userAddresses.setUsersByCreatedBy(user);
                userAddresses.setUsersByUpdatedBy(user);
                session.save(userAddresses);
                userAddressesSet.add(userAddresses);
            }
        }
    }

    /**
     * Repository method to add affiliations.
     *
     * @param session
     * @param user
     * @param userProfile
     * @param affiliationList
     */
    public void setAffiliation(Session session, Users user, UserProfile userProfile, List<Affiliation> affiliationList) {
        if (null != affiliationList && affiliationList.size() > 0) {
            LOGGER.debug("Set Affiliation...");
            Set<UserAffiliations> userAffiliationsSet = new HashSet<>();
            for (Affiliation affiliation : affiliationList) {
                UserAffiliations affiliations = addAffiliation(session, affiliation, user, userProfile);
                session.save(affiliations);
                userAffiliationsSet.add(affiliations);
            }
            //Set User Affiliations
            userProfile.setUserAffiliationses(userAffiliationsSet);
        }
    }

    /**
     * Method to add funder.
     *
     * @param session
     * @param user
     * @param userProfile
     * @param funders
     * @param grants
     */
    public void addFunders(Session session, Users user, UserProfile userProfile, List<Funder> funders, Set<UserFunders> grants) {
        for (Funder funder : funders) {
            List<GrantNumber> grantList = funder.getGrantNumbers();
            ResearchFunders researchFunders = new ResearchFunders();
            researchFunders = UserServiceHelper.setResearchFunders(researchFunders, funder);
            researchFunders.setCreatedDate(UserServiceHelper.getDate());
            researchFunders.setUpdatedDate(UserServiceHelper.getDate());
            researchFunders.setUsersByCreatedBy(user);
            researchFunders.setUsersByUpdatedBy(user);
            session.save(researchFunders);

            UserFunders userFunders = new UserFunders();
            userFunders.setResearchFunders(researchFunders);
            userFunders.setUserProfile(userProfile);
            userFunders.setCreatedDate(UserServiceHelper.getDate());
            userFunders.setUpdatedDate(UserServiceHelper.getDate());
            userFunders.setUsersByCreatedBy(user);
            userFunders.setUsersByUpdatedBy(user);
            userFunders.setUserProfile(userProfile);
            session.save(userFunders);

            for (GrantNumber grantNumber : grantList) {
                UserFunderGrants userFunderGrants = new UserFunderGrants();
                userFunderGrants = UserServiceHelper.setUserFunderGrants(userFunderGrants, grantNumber.getGrantNumber());
                userFunderGrants.setCreatedDate(UserServiceHelper.getDate());
                userFunderGrants.setUpdatedDate(UserServiceHelper.getDate());
                userFunderGrants.setUsersByCreatedBy(user);
                userFunderGrants.setUsersByUpdatedBy(user);
                userFunderGrants.setUserFunders(userFunders);
                session.save(userFunderGrants);
            }
            grants.add(userFunders);
        }
    }

    /**
     * Method to save society.
     *
     * @param session
     * @param user
     * @param userProfile
     * @param societyList
     * @param societyDetailsSet
     * @throws SharedServiceException
     */
    public void setSociety(Session session, Users user, UserProfile userProfile, List<Society> societyList, Set<UserSocietyDetails> societyDetailsSet) throws SharedServiceException {
        for (Society society : societyList) {
            //Get Societies
            Societies societies = (Societies) getEntity(CommonConstants.SOCIETY_CD, society.getSocietyCd(), Societies.class, false);
            if (null == societies) {
                throw new SharedServiceException(CommonConstants.ERROR_CODE_110, society.getSocietyCd() + "-" + userServiceError110);
            } else {
                UserSocietyDetails userSocietyDetail = addUserSociety(session, user, userProfile, society, societies);
                societyDetailsSet.add(userSocietyDetail);
            }
        }
    }

    /**
     * Method to add interest.
     *
     * @param session
     * @param user
     * @param userProfile
     * @param myInterestList
     * @param userAreaOfInterestHashSet
     * @throws SharedServiceException
     */
    public void addInterest(Session session, Users user, UserProfile userProfile, List<MyInterest> myInterestList, Set<UserAreaOfInterest> userAreaOfInterestHashSet) throws SharedServiceException {
        for (MyInterest myInterest : myInterestList) {
            UserAreaOfInterest userAreaOfInterest = new UserAreaOfInterest();
            AreaOfInterest areaOfInterest = (AreaOfInterest) getEntity(CommonConstants.AREA_OF_INTEREST_CD, myInterest.getAreaofInterestCd(), AreaOfInterest.class, false);
            if (null == areaOfInterest) {
                throw new SharedServiceException(CommonConstants.ERROR_CODE_111, myInterest.getAreaofInterestCd() + "-" + userServiceError111);
            } else {
                UserAreaOfInterestId userAreaOfInterestId = new UserAreaOfInterestId();
                userAreaOfInterestId.setUserId(user.getUserId());
                userAreaOfInterestId.setAreaOfInterestCd(areaOfInterest.getAreaOfInterestCd());
                userAreaOfInterest = UserServiceHelper.setUserAreaOfInterest(userAreaOfInterest, areaOfInterest);
                userAreaOfInterest.setCreatedDate(UserServiceHelper.getDate());
                userAreaOfInterest.setUpdatedDate(UserServiceHelper.getDate());
                userAreaOfInterest.setUsersByCreatedBy(user);
                userAreaOfInterest.setUsersByUpdatedBy(user);
                userAreaOfInterest.setAreaOfInterest(areaOfInterest);
                userAreaOfInterest.setUserProfile(userProfile);
                userAreaOfInterest.setId(userAreaOfInterestId);
                session.save(userAreaOfInterest);
                userAreaOfInterestHashSet.add(userAreaOfInterest);
            }
        }

    }

    /**
     * Method to add author.
     *
     * @param session
     * @param user
     * @param userProfile
     * @param coAuthorList
     * @param authCoauthDetailsSet
     */
    public void setAuthor(Session session, Users user, UserProfile userProfile, List<CoAuthor> coAuthorList, Set<AuthCoauthDetails> authCoauthDetailsSet) {
        for (CoAuthor coAuthor : coAuthorList) {
            AuthCoauthDetails authCoauthDetails = addAuthCoauthDetails(session, coAuthor, user, userProfile);
            authCoauthDetailsSet.add(authCoauthDetails);
        }
    }

    /**
     * Method to add journal.
     *
     * @param session
     * @param user
     * @param userProfile
     * @param preferredJournalList
     * @param userPreferredJournalsSet
     * @throws SharedServiceException
     */
    public void addJournal(Session session, Users user, UserProfile userProfile, List<PreferredJournal> preferredJournalList, Set<UserPreferredJournals> userPreferredJournalsSet) throws SharedServiceException {
        for (PreferredJournal preferredJournal : preferredJournalList) {
            UserPreferredJournalsId userPreferredJournalsId = new UserPreferredJournalsId();
            UserPreferredJournals userPreferredJournals = new UserPreferredJournals();
            Journals journals = (Journals) getEntityById(CommonConstants.JOURNAL_ID, preferredJournal.getJournalId(), Journals.class);
            if (null == journals) {
                throw new SharedServiceException(CommonConstants.ERROR_CODE_112, preferredJournal.getJournalId() + "-" + userServiceError112);
            } else {
                userPreferredJournals = UserServiceHelper.setUserPreferredJournals(userPreferredJournals, journals);
                userPreferredJournalsId.setUserId(user.getUserId());
                userPreferredJournalsId.setJournalId(journals.getJournalId());
                userPreferredJournals.setId(userPreferredJournalsId);
                userPreferredJournals.setUserProfile(userProfile);
                userPreferredJournals.setUsersByCreatedBy(user);
                userPreferredJournals.setUsersByUpdatedBy(user);
                userPreferredJournals.setCreatedDate(UserServiceHelper.getDate());
                userPreferredJournals.setUpdatedDate(UserServiceHelper.getDate());
                session.save(userPreferredJournals);
                userPreferredJournalsSet.add(userPreferredJournals);
            }
        }
    }

    /**
     * Method to add alert.
     *
     * @param session
     * @param user
     * @param alertList
     * @param userAlertsHashSet
     * @throws SharedServiceException
     */
    public void setAlert(Session session, Users user, List<Alert> alertList, Set<UserAlerts> userAlertsHashSet) throws SharedServiceException {
        for (Alert alert : alertList) {
            List<AlertType> alertTypeList = alert.getAlertTypes();
            //Get Alerts Object.
            Alerts alertsObj = (Alerts) getEntity(CommonConstants.ALERT_CD, alert.getAlertCd(), Alerts.class, false);

            if (null == alertsObj) {
                throw new SharedServiceException(CommonConstants.ERROR_CODE_113, alert.getAlertCd() + "-" + userServiceError113);
            }

            if (null != alertTypeList && alertTypeList.size() > 0) {
                int alertListSize = alertTypeList.size();
                for (int j = 0; j < alertListSize; j++) {
                    AlertType alertType = alertTypeList.get(j);
                    UserAlerts alerts = addAlert(session, user, alertsObj, alert, alertType);
                    userAlertsHashSet.add(alerts);
                }
            }
        }
    }

    /**
     * Edit Address Profile Information.
     *
     * @param addressProfile
     * @param session
     * @param userId
     * @throws SharedServiceException
     */
    public void editAddressProfile(com.wiley.gr.ace.sharedservices.profile.Address addressProfile, Session session, String userId) throws SharedServiceException {
        com.wiley.gr.ace.sharedservices.persistence.entity.Address addressObj = (com.wiley.gr.ace.sharedservices.persistence.entity.Address) getEntityById(CommonConstants.ADDRESS_ID, addressProfile.getId(), com.wiley.gr.ace.sharedservices.persistence.entity.Address.class);
        if (null != addressObj) {
            Criteria addrCriteria = session.createCriteria(UserAddresses.class, CommonConstants.USER_ADDRESS);
            addrCriteria.createAlias(CommonConstants.USER_ADDRESS_ADDRESS, CommonConstants.ADDRESS);
            addrCriteria.createAlias(CommonConstants.USER_BY_USER_ID, CommonConstants.PROFILE);
            addrCriteria.add(Restrictions.eq(CommonConstants.ADDRESS_ADDRESSID, Integer.parseInt(addressProfile.getId())));
            addrCriteria.add(Restrictions.eq(CommonConstants.PROFILE_USERID, Integer.parseInt(userId)));
            UserAddresses userAddress = (UserAddresses) addrCriteria.uniqueResult();
            if (null != userAddress) {
                AddressType addressType = (AddressType) getEntity(CommonConstants.ADDRESS_TYPE_CD, addressProfile.getType(), AddressType.class, false);
                if (null == addressType) {
                    throw new SharedServiceException(CommonConstants.ERROR_CODE_109, addressProfile.getType() + "-" + userServiceError109);
                }
                userAddress.setAddressType(addressType);
                session.update(userAddress);
            }
            session.flush();
            session.clear();
            addressObj = UserServiceHelper.setAddress(addressObj, addressProfile);
            session.update(addressObj);
        }
    }

    /**
     * Delete funder based on reasearch funder id.
     *
     * @param session
     * @param funder
     */
    public void deleteFunders(Session session, Funder funder) {
        Criteria fundCriteria = session.createCriteria(ResearchFunders.class, CommonConstants.RESEARCH_FUNDERS);
        fundCriteria.createAlias(CommonConstants.RESEARCH_FUNDERS_USER_FUNDERSES, CommonConstants.USER_FUNDERSES);
        fundCriteria.add(Restrictions.eq(CommonConstants.RESEARCH_FUNDERS_RESEARCH_FUNDERID, Integer.parseInt(funder.getId())));
        ResearchFunders researchFund = (ResearchFunders) fundCriteria.uniqueResult();
        if (null != researchFund) {
            Set<UserFunders> userFundersSet = researchFund.getUserFunderses();
            for (UserFunders userFunders : userFundersSet) {
                ResearchFunders researchFunders = userFunders.getResearchFunders();
                if (null != researchFunders) {
                    session.delete(researchFunders);
                }

                Set<UserFunderGrants> userFunderGrantsSet = userFunders.getUserFunderGrantses();
                for (UserFunderGrants userFunderGrants : userFunderGrantsSet) {
                    session.delete(userFunderGrants);
                }
                session.delete(userFunders);
            }
        }
    }

    /**
     * Method to add funder.
     *
     * @param session
     * @param funder
     * @param user
     * @param authorProfile
     */
    public void addFunder(Session session, Funder funder, Users user, UserProfile authorProfile) {
        List<GrantNumber> grantList = funder.getGrantNumbers();
        ResearchFunders researchFunders = new ResearchFunders();
        researchFunders.setFunderName(funder.getResearchFunderName());
        researchFunders.setFunderDoi(funder.getResearchFunderDoi());
        researchFunders.setCreatedDate(UserServiceHelper.getDate());
        researchFunders.setUpdatedDate(UserServiceHelper.getDate());
        session.save(researchFunders);
        if (null != researchFunders) {
            UserFunders userFunders = new UserFunders();
            userFunders.setResearchFunders(researchFunders);
            userFunders.setUserProfile(authorProfile);
            userFunders.setCreatedDate(UserServiceHelper.getDate());
            userFunders.setUpdatedDate(UserServiceHelper.getDate());
            userFunders.setUsersByCreatedBy(user);
            userFunders.setUsersByUpdatedBy(user);
            userFunders.setUserProfile(authorProfile);
            session.save(userFunders);

            for (GrantNumber grantNumber : grantList) {
                UserFunderGrants userFunderGrants = new UserFunderGrants();
                userFunderGrants = UserServiceHelper.setUserFunderGrants(userFunderGrants, grantNumber.getGrantNumber());
                userFunderGrants.setCreatedDate(UserServiceHelper.getDate());
                userFunderGrants.setUsersByCreatedBy(user);
                userFunderGrants.setUsersByUpdatedBy(user);
                userFunderGrants.setUserFunders(userFunders);
                session.save(userFunderGrants);
            }
        }
    }

    /**
     * Method to add area of interest.
     *
     * @param session
     * @param user
     * @param authorProfile
     * @param areaOfInterest
     * @return
     */
    public UserAreaOfInterest addAreaOfInterest(Session session, Users user, UserProfile authorProfile, AreaOfInterest areaOfInterest) {
        UserAreaOfInterest userAreaOfInterest = new UserAreaOfInterest();
        UserAreaOfInterestId userAreaOfInterestId = new UserAreaOfInterestId();
        userAreaOfInterestId.setUserId(user.getUserId());
        userAreaOfInterestId.setAreaOfInterestCd(areaOfInterest.getAreaOfInterestCd());
        userAreaOfInterest = UserServiceHelper.setUserAreaOfInterest(userAreaOfInterest, areaOfInterest);
        userAreaOfInterest.setUpdatedDate(UserServiceHelper.getDate());
        userAreaOfInterest.setUsersByCreatedBy(user);
        userAreaOfInterest.setUsersByUpdatedBy(user);
        userAreaOfInterest.setAreaOfInterest(areaOfInterest);
        userAreaOfInterest.setUserProfile(authorProfile);
        userAreaOfInterest.setId(userAreaOfInterestId);
        session.save(userAreaOfInterest);
        return userAreaOfInterest;
    }


    /**
     * Delete Area of Interest.
     *
     * @param session
     * @param myInterest
     * @param userId
     */
    public void deleteAreaofInterest(Session session, MyInterest myInterest, String userId) {
        Criteria areaCriteria = session.createCriteria(UserAreaOfInterest.class, CommonConstants.USER_AREA_OF_INTEREST);
        areaCriteria.createAlias(CommonConstants.USER_AREA_OF_INTEREST_AREAOFINTEREST, CommonConstants.AREA_OF_INTEREST);
        areaCriteria.createAlias(CommonConstants.USER_AREA_OF_INTEREST_USER_PROFILE, CommonConstants.PROFILE);
        areaCriteria.add(Restrictions.eq(CommonConstants.AREA_OF_INTEREST_AREAOFINTERESTCD, myInterest.getId()));
        areaCriteria.add(Restrictions.eq(CommonConstants.PROFILE_USERID, Integer.parseInt(userId)));
        UserAreaOfInterest userAreaInt = (UserAreaOfInterest) areaCriteria.uniqueResult();
        if (null != userAreaInt) {
            session.delete(userAreaInt);
        }
    }

    /**
     * Delete Alerts.
     *
     * @param session
     * @param alert
     * @param userId
     */
    public void deleteAlerts(Session session, Alert alert, String userId) {
        Criteria alertCriteria = session.createCriteria(UserAlerts.class, CommonConstants.USER_ALERTS);
        alertCriteria.createAlias(CommonConstants.USER_ALERTS_ALERTS, CommonConstants.ALERTS);
        alertCriteria.createAlias(CommonConstants.USER_ALERTS_USER_PROFILE, CommonConstants.PROFILE);
        alertCriteria.add(Restrictions.eq(CommonConstants.ALERTS_ALERTS_CD, alert.getId()));
        alertCriteria.add(Restrictions.eq(CommonConstants.PROFILE_USERID, Integer.parseInt(userId)));
        UserAlerts userAlerts = (UserAlerts) alertCriteria.uniqueResult();
        if (null != userAlerts) {
            session.delete(userAlerts);
        }
    }

    /**
     * Update Alerts.
     *
     * @param session
     * @param alert
     * @param alertTypeList
     * @param user
     * @param userId
     */
    public void updateAlerts(Session session, Alert alert, List<AlertType> alertTypeList, Users user, String userId) {
        Criteria alertCriteria = session.createCriteria(UserAlerts.class, CommonConstants.USER_ALERTS);
        alertCriteria.createAlias(CommonConstants.USER_ALERTS_ALERTS, CommonConstants.ALERTS);
        alertCriteria.createAlias(CommonConstants.USER_ALERTS_USER_PROFILE, CommonConstants.PROFILE);
        alertCriteria.add(Restrictions.eq(CommonConstants.ALERTS_ALERTS_CD, alert.getAlertCd()));
        alertCriteria.add(Restrictions.eq(CommonConstants.PROFILE_USERID, Integer.parseInt(userId)));
        UserAlerts userAlerts = (UserAlerts) alertCriteria.uniqueResult();
        if (null != userAlerts && !CollectionUtils.isEmpty(alertTypeList)) {
            for (AlertType alertType : alertTypeList) {
                userAlerts.setEmailFlg(alertType.getEmail());
                userAlerts.setOnScreenFlg(alertType.getOnScreen());
            }
            userAlerts.setUsersByUpdatedBy(user);
            userAlerts.setUpdatedDate(UserServiceHelper.getDate());
            session.update(userAlerts);
        }

    }

    /**
     * Delete Address.
     *
     * @param session
     * @param addressProfile
     */
    public void deleteAddress(Session session, com.wiley.gr.ace.sharedservices.profile.Address addressProfile) {
        Criteria addrCriteria = session.createCriteria(UserAddresses.class, CommonConstants.USER_ADDRESS);
        addrCriteria.createAlias(CommonConstants.USER_ADDRESS_ADDRESS, CommonConstants.ADDRESS);
        addrCriteria.add(Restrictions.eq(CommonConstants.ADDRESS_ADDRESSID, Integer.parseInt(addressProfile.getId())));
        UserAddresses userAddress = (UserAddresses) addrCriteria.uniqueResult();
        if (null != userAddress) {
            session.delete(userAddress);
        }
    }

    /**
     * Delete Affiliation.
     *
     * @param session
     * @param affiliation
     * @param userId
     */
    public void deleteAffiliation(Session session, Affiliation affiliation, String userId) {
        Criteria affCriteria = session.createCriteria(UserAffiliations.class, CommonConstants.USER_AFFILIATIONS);
        affCriteria.createAlias(CommonConstants.USERAFFILIATIONS_USERPROFILE, CommonConstants.PROFILE);
        affCriteria.add(Restrictions.eq(CommonConstants.USERAFFILIATIONS_AFFILIATION_ID, Integer.parseInt(affiliation.getId())));
        affCriteria.add(Restrictions.eq(CommonConstants.PROFILE_USERID, Integer.parseInt(userId)));
        UserAffiliations userAffiliationsDelete = (UserAffiliations) affCriteria.uniqueResult();
        if (null != userAffiliationsDelete) {
            session.delete(userAffiliationsDelete);
        }
    }

    /**
     * Delete Society.
     *
     * @param session
     * @param society
     * @param userId
     */
    public void deleteSociety(Session session, Society society, String userId) {
        Criteria addrCriteria = session.createCriteria(UserSocietyDetails.class, CommonConstants.USERSOCIETYDETAILS);
        addrCriteria.createAlias(CommonConstants.USER_SOCIETY_DETAILS_SOCIETIES, CommonConstants.SOCIETY);
        addrCriteria.createAlias(CommonConstants.USER_SOCIETY_DETAILS_USERPROFILE, CommonConstants.PROFILE);
        addrCriteria.add(Restrictions.eq(CommonConstants.SOCIETY_BY_ID, Integer.parseInt(society.getId())));
        addrCriteria.add(Restrictions.eq(CommonConstants.PROFILE_USERID, Integer.parseInt(userId)));
        UserSocietyDetails userSociety = (UserSocietyDetails) addrCriteria.uniqueResult();
        if (null != userSociety) {
            session.delete(userSociety);
        }
    }

    /**
     * Delete CoAuthor.
     *
     * @param session
     * @param coAuthor
     * @param userId
     */
    public void deleteCoAuthor(Session session, CoAuthor coAuthor, String userId) {
        Criteria addrCriteria = session.createCriteria(AuthCoauthDetails.class, CommonConstants.AUTHCOAUTHDETAILS);
        addrCriteria.createAlias(CommonConstants.AUTH_COAUTH_PROFILE_BY_ID, CommonConstants.PROFILE);
        addrCriteria.add(Restrictions.eq(CommonConstants.AUTH_COAUTH_BY_ID, Integer.parseInt(coAuthor.getId())));
        addrCriteria.add(Restrictions.eq(CommonConstants.PROFILE_USERID, Integer.parseInt(userId)));
        AuthCoauthDetails authCoauthDetails = (AuthCoauthDetails) addrCriteria.uniqueResult();
        if (null != authCoauthDetails) {
            session.delete(authCoauthDetails);
        }
    }

    /**
     * Delete Journal.
     *
     * @param session
     * @param preferredJournal
     * @param user
     */
    public void deleteJournal(Session session, PreferredJournal preferredJournal, Users user) {
        UserPreferredJournalsId userPreferredJournalsId = new UserPreferredJournalsId();
        userPreferredJournalsId.setUserId(user.getUserId());
        userPreferredJournalsId.setJournalId(Integer.parseInt(preferredJournal.getId()));
        UserPreferredJournals userPreferredJournal = (UserPreferredJournals) session.get(UserPreferredJournals.class, userPreferredJournalsId);
        if (null != userPreferredJournal) {
            session.delete(userPreferredJournal);
        }
    }

    /**
     * Add Affiliation.
     *
     * @param session
     * @param affiliation
     * @param user
     * @param authorProfile
     * @return
     */
    public UserAffiliations addAffiliation(Session session, Affiliation affiliation, Users user, UserProfile authorProfile) {
        UserAffiliations userAffiliations = new UserAffiliations();
        userAffiliations = UserServiceHelper.setUserAffiliations(userAffiliations, affiliation);
        userAffiliations.setCreatedDate(UserServiceHelper.getDate());
        userAffiliations.setUsersByUpdatedBy(user);
        userAffiliations.setUserProfile(authorProfile);
        session.save(userAffiliations);
        return userAffiliations;
    }

    /**
     * Add UserSocietyDetails.
     *
     * @param session
     * @param user
     * @param userProfile
     * @param society
     * @param societies
     * @return
     */
    public UserSocietyDetails addUserSociety(Session session, Users user, UserProfile userProfile, Society society, Societies societies) {
        UserSocietyDetails userSocietyDetails = new UserSocietyDetails();
        userSocietyDetails = UserServiceHelper.setUserSocietyDetails(userSocietyDetails, society);
        userSocietyDetails.setSocieties(societies);
        userSocietyDetails.setCreatedDate(UserServiceHelper.getDate());
        userSocietyDetails.setUpdatedDate(UserServiceHelper.getDate());
        userSocietyDetails.setUsersByCreatedBy(user);
        userSocietyDetails.setUsersByUpdatedBy(user);
        userSocietyDetails.setUserProfile(userProfile);
        session.save(userSocietyDetails);
        return userSocietyDetails;
    }

    /**
     * Add AuthCoauthDetails.
     *
     * @param session
     * @param coAuthor
     * @param user
     * @param userProfile
     * @return
     */
    public AuthCoauthDetails addAuthCoauthDetails(Session session, CoAuthor coAuthor, Users user, UserProfile userProfile) {
        AuthCoauthDetails authCoauthDetails = new AuthCoauthDetails();
        authCoauthDetails = UserServiceHelper.setAuthCoauthDetails(authCoauthDetails, coAuthor);
        authCoauthDetails.setCreatedDate(UserServiceHelper.getDate());
        authCoauthDetails.setUpdatedDate(UserServiceHelper.getDate());
        authCoauthDetails.setUserProfileByAuthorUserId(userProfile);
        authCoauthDetails.setAuthCoauthId(user.getUserId());
        authCoauthDetails.setUsersByCreatedBy(user);
        authCoauthDetails.setUsersByUpdatedBy(user);
        session.save(authCoauthDetails);
        return authCoauthDetails;
    }

    /**
     * Add Alerts.
     *
     * @param session
     * @param user
     * @param alertsObj
     * @param alert
     * @param alertType
     * @return
     */
    public UserAlerts addAlert(Session session, Users user, Alerts alertsObj, Alert alert, AlertType alertType) {
        UserAlertsId userAlertsId = new UserAlertsId();
        userAlertsId.setUserId(user.getUserId());
        userAlertsId.setAlertCd(alert.getAlertCd());
        UserAlerts alerts = new UserAlerts();
        alerts.setId(userAlertsId);
        alerts.setEmailFlg(alertType.getEmail());
        alerts.setOnScreenFlg(alertType.getOnScreen());
        alerts.setAlerts(alertsObj);
        alerts.setUsersByCreatedBy(user);
        alerts.setUsersByUpdatedBy(user);
        alerts.setCreatedDate(UserServiceHelper.getDate());
        alerts.setUpdatedDate(UserServiceHelper.getDate());
        session.save(alerts);
        return alerts;
    }
}
