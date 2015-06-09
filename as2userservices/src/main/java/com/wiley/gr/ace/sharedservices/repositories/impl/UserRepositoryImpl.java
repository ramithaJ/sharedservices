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
package com.wiley.gr.ace.sharedservices.repositories.impl;

import com.wiley.gr.ace.sharedservices.common.CommonConstants;
import com.wiley.gr.ace.sharedservices.exceptions.SharedServiceException;
import com.wiley.gr.ace.sharedservices.helper.UserServiceHelper;
import com.wiley.gr.ace.sharedservices.payload.UserServiceRequest;
import com.wiley.gr.ace.sharedservices.persistence.entity.*;
import com.wiley.gr.ace.sharedservices.profile.Address;
import com.wiley.gr.ace.sharedservices.profile.*;
import com.wiley.gr.ace.sharedservices.repositories.UserRepository;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.*;

/**
 * @author kkalyan
 */
public class UserRepositoryImpl implements UserRepository {

    //Logger Instance
    private static final Logger LOGGER = LoggerFactory.getLogger(UserRepositoryImpl.class);

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    @Qualifier(value = "messageProperties")
    private Properties messageProp;


    /**
     * Method to create user profile in the DB.
     *
     * @param userServiceRequest Input Json Request Info
     */
    public void createUserRepository(UserServiceRequest userServiceRequest, String userId) throws SharedServiceException {
        //Get the session from sessionFactory pool.
        Session session = null;
        try {
            LOGGER.info("Creating User Profile..");

            Users user = (Users) getEntity(CommonConstants.USER_ID, userId, Users.class);

            //Throw Error if user is not found.
            if (null == user) {
                throw new SharedServiceException(messageProp.getProperty(CommonConstants.ERROR_CODE_102));
            }

            session = sessionFactory.openSession();
            //Begin the transaction.
            session.beginTransaction();

            LOGGER.info("Set User Profile...");
            user = UserServiceHelper.setUserProfileInformation(userServiceRequest, user);

            //Create AuthorProfile Object
            AuthorProfile authorProfile = user.getAuthorProfileByUserId();
            LOGGER.info("Set Author Profile...");
            authorProfile = UserServiceHelper.setAuthorProfile(userServiceRequest, authorProfile);

            //Set Orcid id information
            UserReferenceDataId userReferenceDataId = new UserReferenceDataId();
            userReferenceDataId.setUserId(user.getUserId());
            //TODO: Check how to get ecid. Temp fixed.
            userReferenceDataId.setEcid(authorProfile.getMiddleName());
            UserReferenceData userReferenceData = new UserReferenceData();
            userReferenceData = UserServiceHelper.setUserReference(userReferenceData, userServiceRequest);
            userReferenceData.setId(userReferenceDataId);
            userReferenceData.setUsersByUserId(user);
            session.save(userReferenceData);


            List<ProfileVisible> profileVisibleList = userServiceRequest.getUserProfile().getProfileVisible();
            Set<UserProfileAttribVisible> userProfileAttribVisibleSet = new HashSet<>();
            if (null != profileVisibleList && profileVisibleList.size() > 0) {
                for (ProfileVisible profileVisible : profileVisibleList) {
                    ProfileAttributeList profileAttributeList = new ProfileAttributeList();
                    profileAttributeList = UserServiceHelper.setProfileAttributeList(profileAttributeList, profileVisible);
                    session.save(profileAttributeList);
                    UserProfileAttribVisibleId userProfileAttribVisibleId = new UserProfileAttribVisibleId();
                    userProfileAttribVisibleId.setProfileAttribCd(profileVisible.getTitleCd());
                    userProfileAttribVisibleId.setUserId(Integer.parseInt(userId));
                    userProfileAttribVisibleId.setProfileVisibilityFlg('1');
                    UserProfileAttribVisible userProfileAttribVisible = new UserProfileAttribVisible();
                    userProfileAttribVisible.setAuthorProfile(authorProfile);
                    userProfileAttribVisible.setId(userProfileAttribVisibleId);
                    userProfileAttribVisible.setProfileAttributeList(profileAttributeList);
                    session.save(userProfileAttribVisible);
                    userProfileAttribVisibleSet.add(userProfileAttribVisible);
                }


                authorProfile.setUserProfileAttribVisibles(userProfileAttribVisibleSet);
            }

            //Create User Secondary Email Address
            Set<UserSecondaryEmailAddr> userSecondaryEmailAddrSet = new HashSet<>();
            UserSecondaryEmailAddr secondaryEmailAddr = new UserSecondaryEmailAddr();
            secondaryEmailAddr = UserServiceHelper.setUserSecondaryEmailAddr(userServiceRequest, secondaryEmailAddr, user);
            if (null != secondaryEmailAddr) {
                LOGGER.info("Set Secondary Email Addr...");
                userSecondaryEmailAddrSet.add(secondaryEmailAddr);
                //Set the secondary email address to the user object
                secondaryEmailAddr.setUsersByUserId(user);
            }


            //Create user Address Object
            List<Address> addressList = userServiceRequest.getUserProfile().getAddresses();
            UserAddresses userAddresses = null;
            if (null != addressList && addressList.size() > 0) {
                Set<UserAddresses> userAddressesSet = new HashSet<>();
                LOGGER.info("Set User  Addr...");
                for (Address addressProfile : addressList) {
                    userAddresses = new UserAddresses();
                    com.wiley.gr.ace.sharedservices.persistence.entity.Address address = new com.wiley.gr.ace.sharedservices.persistence.entity.Address();
                    address = UserServiceHelper.setAddress(address, addressProfile);
                    session.save(address);
                    UserAddressesId userAddressesId = new UserAddressesId();
                    userAddressesId.setAddressId(address.getAddressId());
                    userAddressesId.setUserId(Integer.parseInt(userId));
                    userAddressesId.setAddType(addressProfile.getType());
                    userAddresses.setId(userAddressesId);
                    userAddresses.setAddress(address);
                    userAddresses.setUsersByUserId(user);
                    userAddresses.setUsersByCreatedBy(user);
                    userAddresses.setUsersByUserId(user);
                    session.save(userAddresses);
                    userAddressesSet.add(userAddresses);
                }
                user.setUserAddressesesForUserId(userAddressesSet);

            }

            //Setting Affiliations
            List<Affiliation> affiliationList = userServiceRequest.getUserProfile().getAffiliations();
            UserAffiliations affiliations = null;
            if (null != affiliationList && affiliationList.size() > 0) {
                LOGGER.info("Set Affiliation...");
                Set<UserAffiliations> userAffiliationsSet = new HashSet<>();
                for (Affiliation affiliation : affiliationList) {
                    affiliations = new UserAffiliations();
                    affiliations = UserServiceHelper.setUserAffiliations(affiliations, affiliation);
                    affiliations.setAuthorProfile(authorProfile);
                    userAffiliationsSet.add(affiliations);
                }

                //Set User Affiliations
                authorProfile.setUserAffiliationses(userAffiliationsSet);
            }

            //Set funder details
            List<Funder> funders = userServiceRequest.getUserProfile().getFunders();
            UserFunderGrants userFunderGrants = null;
            ResearchFunders researchFunders = null;
            if (null != funders && funders.size() > 0) {
                LOGGER.info("Set Funder...");
                for (Funder funder : funders) {
                    Set<UserFunderGrants> grants = new HashSet<>();
                    List<GrantNumber> grantList = funder.getGrantNumbers();
                    int grantListSize = grantList.size();
                    researchFunders = new ResearchFunders();
                    researchFunders.setFunderName(funder.getResearchFunderCd());
                    for (int i = 0; i < grantListSize; i++) {
                        userFunderGrants = new UserFunderGrants();
                        userFunderGrants = UserServiceHelper.setUserFunderGrants(userFunderGrants, ((GrantNumber) grantList.get(i)).getGrantNumber());
                        userFunderGrants.setAuthorProfile(authorProfile);
                        userFunderGrants.setResearchFunders(researchFunders);
                        //researchFunders.getUserFunderGrantses().add(userFunderGrants);
                        grants.add(userFunderGrants);
                    }
                    researchFunders.setUserFunderGrantses(grants);
                    authorProfile.getUserFunderGrantses().addAll(grants);
                }

            }

            //TODO:Set Wiley Open Access Account is not part of WP1 Scope.

            //Set Society Details
            List<Society> societyList = userServiceRequest.getUserProfile().getSocieties();
            UserSocietyDetails userSocietyDetails = null;
            if (null != societyList && societyList.size() > 0) {
                LOGGER.info("Set Society...");
                Set<UserSocietyDetails> societyDetailsSet = new HashSet<>();
                for (Society society : societyList) {
                    userSocietyDetails = new UserSocietyDetails();
                    userSocietyDetails = UserServiceHelper.setUserSocietyDetails(userSocietyDetails, society);
                    userSocietyDetails.setAuthorProfile(authorProfile);
                    societyDetailsSet.add(userSocietyDetails);
                }
                authorProfile.setUserSocietyDetailses(societyDetailsSet);
            }

            //Set Area of interest
            List<MyInterest> myInterestList = userServiceRequest.getUserProfile().getMyInterests();
            UserAreaOfInterest userAreaOfInterest = null;

            if (null != myInterestList && myInterestList.size() > 0) {
                Set<UserAreaOfInterest> userAreaOfInterestHashSet = new HashSet<>();
                LOGGER.info("Set MyInterest...");
                for (MyInterest myInterest : myInterestList) {
                    userAreaOfInterest = new UserAreaOfInterest();
                    AreaOfInterest areaOfInterest = new AreaOfInterest();
                    areaOfInterest = UserServiceHelper.setAreaOfInterest(areaOfInterest, myInterest);
                    session.save(areaOfInterest);
                    UserAreaOfInterestId userAreaOfInterestId = new UserAreaOfInterestId();
                    userAreaOfInterestId.setUserId(user.getUserId());
                    userAreaOfInterestId.setAreaOfInterestCd(areaOfInterest.getAreaOfInterestCd());
                    userAreaOfInterest = UserServiceHelper.setUserAreaOfInterest(userAreaOfInterest, areaOfInterest);
                    userAreaOfInterest.setAreaOfInterest(areaOfInterest);
                    userAreaOfInterest.setAuthorProfile(authorProfile);
                    userAreaOfInterest.setId(userAreaOfInterestId);
                    userAreaOfInterestHashSet.add(userAreaOfInterest);
                }
                authorProfile.setUserAreaOfInterests(userAreaOfInterestHashSet);

            }


            //Set Preferred Journals
            List<PreferredJournal> preferredJournalList = userServiceRequest.getUserProfile().getJournals();
            UserPreferredJournals userPreferredJournals = null;

            if (null != preferredJournalList && preferredJournalList.size() > 0) {

                LOGGER.info("Set PreferredJournal...");
                Set<UserPreferredJournals> userPreferredJournalsSet = new HashSet<>();
                for (PreferredJournal preferredJournal : preferredJournalList) {
                    UserPreferredJournalsId userPreferredJournalsId = new UserPreferredJournalsId();
                    userPreferredJournals = new UserPreferredJournals();
                    Journals journals = new Journals();
                    journals = UserServiceHelper.setJournals(journals, preferredJournal.getJournalTitle());
                    session.save(journals);
                    userPreferredJournals = UserServiceHelper.setUserPreferredJournals(userPreferredJournals, journals);
                    userPreferredJournalsId.setUserId(user.getUserId());
                    userPreferredJournalsId.setJournalId(journals.getJournalId());
                    userPreferredJournals.setId(userPreferredJournalsId);
                    userPreferredJournals.setAuthorProfile(authorProfile);
                    userPreferredJournalsSet.add(userPreferredJournals);
                }
                authorProfile.setUserPreferredJournalses(userPreferredJournalsSet);
            }

            List<Alert> alertList = userServiceRequest.getUserProfile().getAlerts();
            UserAlerts alerts = null;
            if (null != alertList && alertList.size() > 0) {
                Set<UserAlerts> userAlertsHashSet = new HashSet<>();
                for (Alert alert : alertList) {
                    List<AlertType> alertTypeList = alert.getAlertTypes();
                    Alerts alertsObj = new Alerts();
                    alertsObj.setAlertCd(alert.getAlertCd());
                    alertsObj.setAlertName(alert.getAlertName());
                    session.save(alertsObj);
                    if (null != alertTypeList && alertTypeList.size() > 0) {
                        int alertListSize = alertTypeList.size();
                        for (int j = 0; j < alertListSize; j++) {
                            AlertType alertType = alertTypeList.get(j);
                            UserAlertsId userAlertsId = new UserAlertsId();
                            userAlertsId.setUserId(user.getUserId());
                            userAlertsId.setAlertCd(alert.getAlertCd());
                            alerts = new UserAlerts();
                            alerts.setId(userAlertsId);
                            alerts.setEmailFlg(alertType.getEmail());
                            alerts.setOnScreenFlg(alertType.getOnScreen());
                            alerts.setAlerts(alertsObj);
                            session.save(alerts);
                            userAlertsHashSet.add(alerts);
                        }
                    }
                }
            }


            //Set Author Profile to user
            authorProfile.setUsersByUserId(user);

            session.saveOrUpdate(user);
            session.saveOrUpdate(authorProfile);
            if (null != secondaryEmailAddr) {
                session.save(secondaryEmailAddr);
            }
            if (null != userAddresses) {
                //session.save(address);
                session.save(userAddresses);
            }
            if (null != affiliations) {
                session.save(affiliations);
            }
            if (null != userFunderGrants) {
                session.save(userFunderGrants);
            }
            if (null != userSocietyDetails) {
                session.save(userSocietyDetails);
            }
            if (null != userAreaOfInterest) {
                session.save(userAreaOfInterest);
            }
            if (null != userPreferredJournals) {
                session.save(userPreferredJournals);
            }
            if (null != alerts) {
                session.save(alerts);
            }

            //Flush the session.
            session.flush();
            //Clear the session object.
            session.clear();

            //Commit the transaction.
            session.getTransaction().commit();

        } catch (Exception e) {
            //Rollback the session if any exception occurs.
            if (null != session) {
                session.getTransaction().rollback();
            }
            LOGGER.error("Exception Occurred during user profile creation...", e);
            throw new SharedServiceException("Error :" + e.toString());
        } finally {
            if (null != session) {
                //Close the session
                session.close();
            }
        }


    }

    /**
     * Method to update user build profile in DB.
     *
     * @param userServiceRequest
     * @param userId
     */
    public void updateUserRepository(UserServiceRequest userServiceRequest, String userId) throws SharedServiceException {
        //Get the session from sessionFactory pool.
        Session session = null;
        try {
            LOGGER.info("Updating User Profile..");
            if (null == userId) {
                throw new SharedServiceException(messageProp.getProperty(CommonConstants.ERROR_CODE_101));
            }

            Users user = (Users) getEntity(CommonConstants.USER_ID, userId, Users.class);

            //Throw Error if user is not found.
            if (null == user) {
                throw new SharedServiceException(messageProp.getProperty(CommonConstants.ERROR_CODE_102));
            }

            session = sessionFactory.openSession();
            //Begin the transaction.
            session.beginTransaction();


            user = UserServiceHelper.setUserProfileInformation(userServiceRequest, user);
            //Create AuthorProfile Object
            AuthorProfile authorProfile = user.getAuthorProfileByUserId();
            LOGGER.info("Update Author Profile...");
            authorProfile = UserServiceHelper.setAuthorProfile(userServiceRequest, authorProfile);


            //Get User Secondary Email Address
            Set<UserSecondaryEmailAddr> userSecondaryEmailAddrSet = new HashSet<>();
            Set<UserSecondaryEmailAddr> secondaryEmailAddrs = user.getUserSecondaryEmailAddrsForUserId();
            if (null != secondaryEmailAddrs && secondaryEmailAddrs.size() > 0) {
                for (UserSecondaryEmailAddr secondaryEmailAddr : secondaryEmailAddrs) {
                    secondaryEmailAddr = UserServiceHelper.setUserSecondaryEmailAddr(userServiceRequest, secondaryEmailAddr, user);
                    if (null != secondaryEmailAddr) {
                        LOGGER.info("Set Secondary Email Addr...");
                        userSecondaryEmailAddrSet.add(secondaryEmailAddr);
                        //Set the secondary email address to the user object
                        secondaryEmailAddr.setUsersByUserId(user);
                    }
                    session.update(secondaryEmailAddr);
                }
            }

            //Set Orcid id information
            Set<UserReferenceData> userReferenceDataSet = user.getUserReferenceDatasForUserId();
            for (UserReferenceData userReferenceData : userReferenceDataSet) {
                userReferenceData = UserServiceHelper.setUserReference(userReferenceData, userServiceRequest);
                userReferenceData.setUsersByUserId(user);
                session.update(userReferenceData);
            }

            // Profile Visible
            List<ProfileVisible> profileVisibleList = userServiceRequest.getUserProfile().getProfileVisible();
            Set<UserProfileAttribVisible> userProfileAttribVisibleSet = user.getAuthorProfileByUserId().getUserProfileAttribVisibles();
            if (null != profileVisibleList && profileVisibleList.size() > 0) {
                for (ProfileVisible profileVisible : profileVisibleList) {
                    ProfileAttributeList profileAttributeList = new ProfileAttributeList();
                    profileAttributeList = UserServiceHelper.setProfileAttributeList(profileAttributeList, profileVisible);
                    session.save(profileAttributeList);
                    UserProfileAttribVisibleId userProfileAttribVisibleId = new UserProfileAttribVisibleId();
                    userProfileAttribVisibleId.setProfileAttribCd(profileVisible.getTitleCd());
                    userProfileAttribVisibleId.setUserId(Integer.parseInt(userId));
                    userProfileAttribVisibleId.setProfileVisibilityFlg('1');
                    UserProfileAttribVisible userProfileAttribVisible = new UserProfileAttribVisible();
                    userProfileAttribVisible.setAuthorProfile(authorProfile);
                    userProfileAttribVisible.setId(userProfileAttribVisibleId);
                    userProfileAttribVisible.setProfileAttributeList(profileAttributeList);
                    session.update(userProfileAttribVisible);
                    userProfileAttribVisibleSet.add(userProfileAttribVisible);
                }


                authorProfile.setUserProfileAttribVisibles(userProfileAttribVisibleSet);
            }


            //Update user Address Object
            List<Address> addressList = userServiceRequest.getUserProfile().getAddresses();
            if (null != addressList && addressList.size() > 0) {
                for (Address address : addressList) {
                    com.wiley.gr.ace.sharedservices.persistence.entity.Address addressObj = null;
                    if (null != address.getId()) {
                        addressObj = (com.wiley.gr.ace.sharedservices.persistence.entity.Address) getEntity("addressId", address.getId(), com.wiley.gr.ace.sharedservices.persistence.entity.Address.class);
                        addressObj = UserServiceHelper.setAddress(addressObj, address);
                        session.update(addressObj);
                    }
                }
            }

            //Update Affiliations
            List<Affiliation> affiliationList = userServiceRequest.getUserProfile().getAffiliations();
            if (null != affiliationList && affiliationList.size() > 0) {
                for (Affiliation affiliation : affiliationList) {
                    UserAffiliations userAffiliations = null;
                    if (null != affiliation.getId()) {
                        userAffiliations = (UserAffiliations) getEntity("affiliationId", affiliation.getId(), UserAffiliations.class);
                        userAffiliations = UserServiceHelper.setUserAffiliations(userAffiliations, affiliation);
                        session.update(userAffiliations);
                    }
                }
            }

            //TODO:Update Funders
            List<Funder> funders = userServiceRequest.getUserProfile().getFunders();
            if (null != funders && funders.size() > 0) {
                for (Funder funder : funders) {


                }
            }

            //Update Societies
            List<Society> societyList = userServiceRequest.getUserProfile().getSocieties();
            if (null != societyList && societyList.size() > 0) {
                for (Society society : societyList) {
                    UserSocietyDetails userSocietyDetails = null;
                    userSocietyDetails = (UserSocietyDetails) getEntity("societyId", society.getId(), UserSocietyDetails.class);
                    userSocietyDetails = UserServiceHelper.setUserSocietyDetails(userSocietyDetails, society);
                    session.update(userSocietyDetails);
                }
            }

            //Update My Interests
            List<MyInterest> myInterestList = userServiceRequest.getUserProfile().getMyInterests();
            if (null != myInterestList && myInterestList.size() > 0) {
                for (MyInterest myInterest : myInterestList) {
                    AreaOfInterest areaOfInterest = null;
                    areaOfInterest = (AreaOfInterest) getEntity("areaOfInterestCd", myInterest.getAreaofInterestCd(), AreaOfInterest.class);
                    areaOfInterest = UserServiceHelper.setAreaOfInterest(areaOfInterest, myInterest);
                    session.update(areaOfInterest);
                }
            }

            //TODO:Coauthor update option.

            //Update Preferred Journals
            List<PreferredJournal> preferredJournalList = userServiceRequest.getUserProfile().getJournals();
            if (null != preferredJournalList && preferredJournalList.size() > 0) {
                for (PreferredJournal preferredJournal : preferredJournalList) {
                    Journals journals = null;
                    journals = (Journals) getEntity(CommonConstants.JOURNAL_ID, preferredJournal.getId(), Journals.class);
                    journals = UserServiceHelper.setJournals(journals, preferredJournal.getJournalTitle());
                    session.update(journals);
                }
            }

            //TODO:Update Alerts
            List<Alert> alertList = userServiceRequest.getUserProfile().getAlerts();
            if (null != alertList && alertList.size() > 0) {
                for (Alert alert : alertList) {

                }
            }

            //Set Author Profile to user
            authorProfile.setUsersByUserId(user);
            session.update(user);
            session.update(authorProfile);
            session.flush();
            session.clear();


        } catch (Exception e) {
            //Rollback the session if any exception occurs.
            if (null != session) {
                session.getTransaction().rollback();
            }
            LOGGER.error("Exception Occurred during user profile updating...", e);
            throw new SharedServiceException("Error :" + e.toString());
        } finally {
            if (null != session) {
                //Close the session
                session.close();
            }
        }

    }

    /**
     * Method to delete user build profile in DB.
     *
     * @param userServiceRequest
     * @param userId
     */
    public void deleteUserRepository(UserServiceRequest userServiceRequest, String userId) throws SharedServiceException {
        //Get the session from sessionFactory pool.
        Session session = null;
        try {
            LOGGER.info("Updating User Profile..");
            if (null == userId) {
                throw new SharedServiceException(messageProp.getProperty(CommonConstants.ERROR_CODE_101));
            }

            Users user = (Users) getEntity(CommonConstants.USER_ID, userId, Users.class);

            //Throw Error if user is not found.
            if (null == user) {
                throw new SharedServiceException(messageProp.getProperty(CommonConstants.ERROR_CODE_102));
            }


            session = sessionFactory.openSession();
            //Begin the transaction.
            session.beginTransaction();


            user = UserServiceHelper.setUserProfileInformation(userServiceRequest, user);

            //Update user Address Object
            List<Address> addressList = userServiceRequest.getUserProfile().getAddresses();
            if (null != addressList && addressList.size() > 0) {
                for (Address address : addressList) {
                    if (null != address.getId()) {
                        com.wiley.gr.ace.sharedservices.persistence.entity.Address addressObj = (com.wiley.gr.ace.sharedservices.persistence.entity.Address) getEntity(CommonConstants.ADDRESS_ID, address.getId(), com.wiley.gr.ace.sharedservices.persistence.entity.Address.class);
                        if (null != addressObj) {
                            session.delete(addressObj);
                        }
                    }
                }
            }

            //Update Affiliations
            List<Affiliation> affiliationList = userServiceRequest.getUserProfile().getAffiliations();
            if (null != affiliationList && affiliationList.size() > 0) {
                for (Affiliation affiliation : affiliationList) {
                    if (null != affiliation.getId()) {
                        UserAffiliations userAffiliations = (UserAffiliations) getEntity(CommonConstants.AFFILIATION_ID, affiliation.getId(), UserAffiliations.class);
                        if (null != userAffiliations) {
                            session.delete(userAffiliations);
                        }
                    }
                }
            }

            //TODO:Update Funders
            List<Funder> funders = userServiceRequest.getUserProfile().getFunders();
            if (null != funders && funders.size() > 0) {
                for (Funder funder : funders) {


                }
            }

            //Update Societies
            List<Society> societyList = userServiceRequest.getUserProfile().getSocieties();
            if (null != societyList && societyList.size() > 0) {
                for (Society society : societyList) {
                    UserSocietyDetails userSocietyDetails = (UserSocietyDetails) getEntity(CommonConstants.SOCIETY_ID, society.getId(), UserSocietyDetails.class);
                    if (null != userSocietyDetails) {
                        session.delete(userSocietyDetails);
                    }
                }
            }

            //Update My Interests
            List<MyInterest> myInterestList = userServiceRequest.getUserProfile().getMyInterests();
            if (null != myInterestList && myInterestList.size() > 0) {
                for (MyInterest myInterest : myInterestList) {
                    AreaOfInterest areaOfInterest = (AreaOfInterest) getEntity(CommonConstants.AREA_OF_INTEREST_CD, myInterest.getAreaofInterestCd(), AreaOfInterest.class);
                    if (null != areaOfInterest) {
                        session.delete(areaOfInterest);
                    }
                }
            }

            //TODO:Coauthor update option.

            //Update Preferred Journals
            List<PreferredJournal> preferredJournalList = userServiceRequest.getUserProfile().getJournals();
            if (null != preferredJournalList && preferredJournalList.size() > 0) {
                for (PreferredJournal preferredJournal : preferredJournalList) {
                    Journals journals = (Journals) getEntity(CommonConstants.JOURNAL_ID, preferredJournal.getJournalId(), Journals.class);
                    if (null != journals) {
                        session.delete(journals);
                    }
                }
            }

            //TODO:Update Alerts
            List<Alert> alertList = userServiceRequest.getUserProfile().getAlerts();
            if (null != alertList && alertList.size() > 0) {
                for (Alert alert : alertList) {

                }
            }

            session.flush();
            session.clear();


        } catch (Exception e) {
            //Rollback the session if any exception occurs.
            if (null != session) {
                session.getTransaction().rollback();
            }
            LOGGER.error("Exception Occurred during user profile deletion...", e);
            throw new SharedServiceException("Error :" + e.toString());
        } finally {
            if (null != session) {
                //Close the session
                session.close();
            }
        }

    }

    /**
     * Method to get user profile.
     *
     * @param userId
     * @return
     * @throws Exception
     */
    public UserServiceRequest getUserRepository(String userId) throws SharedServiceException {
        //Get the session from sessionFactory pool.
        Session session = null;
        UserServiceRequest userResponse = new UserServiceRequest();
        try {
            LOGGER.info("Updating User Profile..");
            if (null == userId) {
                throw new SharedServiceException(messageProp.getProperty(CommonConstants.ERROR_CODE_101));
            }
            Users user = (Users) getEntity(CommonConstants.USER_ID, userId, Users.class);
            //Throw Error if user is not found.
            if (null == user) {
                throw new SharedServiceException(messageProp.getProperty(CommonConstants.ERROR_CODE_102));
            }

            //Set to response pojo
            UserProfile userProfile = UserServiceHelper.getUserProfileInfo(user);


            //Get Address and set to the response.
            List<Address> addressList = new LinkedList<>();
            Set<UserAddresses> userAddressesesForUserId = user.getUserAddressesesForUserId();
            if (null != userAddressesesForUserId && userAddressesesForUserId.size() > 0) {
                for (UserAddresses userAddresses : userAddressesesForUserId) {
                    com.wiley.gr.ace.sharedservices.persistence.entity.Address addressEntity = userAddresses.getAddress();
                    Address address = UserServiceHelper.getAddressInfo(addressEntity);
                    addressList.add(address);
                }
                userProfile.setAddresses(addressList);
            }

            //Get Affiliations
            List<Affiliation> affiliationList = new LinkedList<>();
            Set<UserAffiliations> userAffiliationsSet = user.getAuthorProfileByUserId().getUserAffiliationses();
            if (null != userAffiliationsSet && userAffiliationsSet.size() > 0) {
                for (UserAffiliations userAffiliations : userAffiliationsSet) {
                    Affiliation affiliation = UserServiceHelper.getAffiliation(userAffiliations);
                    affiliationList.add(affiliation);
                }
                userProfile.setAffiliations(affiliationList);
            }

            List<Funder> funders = new LinkedList<>();
            Set<UserFunderGrants> userFunderGrantsSet = user.getAuthorProfileByUserId().getUserFunderGrantses();
            if (null != userFunderGrantsSet && userAffiliationsSet.size() > 0) {
                List<GrantNumber> grantNumbers = new LinkedList<>();
                for (UserFunderGrants userFunderGrants : userFunderGrantsSet) {
                    ResearchFunders researchFunders = userFunderGrants.getResearchFunders();
                    Funder funder = new Funder();
                    funder.setId("" + researchFunders.getFundrefId());
                    funder.setResearchFunderCd(researchFunders.getFunderName());
                    GrantNumber grantNumber = new GrantNumber();
                    grantNumber.setGrantNumber(userFunderGrants.getGrantNum());
                    grantNumbers.add(grantNumber);
                    funder.setGrantNumbers(grantNumbers);
                    funders.add(funder);
                }
                userProfile.setFunders(funders);
            }

            List<Society> societies = new LinkedList<>();
            Set<UserSocietyDetails> userSocietyDetailsSet = user.getAuthorProfileByUserId().getUserSocietyDetailses();
            if (null != userSocietyDetailsSet && userSocietyDetailsSet.size() > 0) {
                for (UserSocietyDetails userSocietyDetails : userSocietyDetailsSet) {
                    Society society = UserServiceHelper.getSociety(userSocietyDetails);
                    societies.add(society);
                }
                userProfile.setSocieties(societies);
            }

            List<MyInterest> myInterests = new LinkedList<>();
            Set<UserAreaOfInterest> userAreaOfInterestSet = user.getAuthorProfileByUserId().getUserAreaOfInterests();
            if (null != myInterests && myInterests.size() > 0) {
                for (UserAreaOfInterest userAreaOfInterest : userAreaOfInterestSet) {
                    AreaOfInterest areaOfInterest = userAreaOfInterest.getAreaOfInterest();
                    MyInterest myInterest = UserServiceHelper.getMyInterest(areaOfInterest);
                    myInterests.add(myInterest);
                }
                userProfile.setMyInterests(myInterests);
            }

            //TODO: Coauthor get

            List<PreferredJournal> journalsList = new LinkedList<>();
            Set<UserPreferredJournals> userPreferredJournalsSet = user.getAuthorProfileByUserId().getUserPreferredJournalses();
            if (null != userPreferredJournalsSet && userPreferredJournalsSet.size() > 0) {
                for (UserPreferredJournals userPreferredJournals : userPreferredJournalsSet) {
                    Journals journal = userPreferredJournals.getJournals();
                    PreferredJournal preferredJournal = UserServiceHelper.getPreferredJournal(journal);
                    journalsList.add(preferredJournal);
                }
                userProfile.setJournals(journalsList);
            }

            List<Alert> alerts = new LinkedList<>();
            Set<UserAlerts> alertsSet = user.getAuthorProfileByUserId().getUserAlertses();
            if (null != alertsSet && alertsSet.size() > 0) {
                for (UserAlerts userAlerts : alertsSet) {
                    Alerts userAlert = userAlerts.getAlerts();
                    Alert alert = UserServiceHelper.getAlert(userAlert);
                    alerts.add(alert);
                }
                userProfile.setAlerts(alerts);
            }
            userResponse.setUserProfile(userProfile);

        } catch (Exception e) {
            //Rollback the session if any exception occurs.
            if (null != session) {
                session.getTransaction().rollback();
            }
            LOGGER.error("Exception Occurred during user profile creation...", e);
            throw new SharedServiceException("Error :" + e.toString());
        } finally {
            if (null != session) {
                //Close the session
                session.close();
            }
        }
        return userResponse;
    }

    /**
     * Class to get Entity Object from the DB primary id.
     *
     * @param columnName
     * @param primaryId
     * @param entityClass
     * @param <T>
     * @return
     */
    private <T> Object getEntity(String columnName, String primaryId, Class<T> entityClass) throws SharedServiceException {
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
            criteria.add(Restrictions.eq(columnName, Integer.valueOf(primaryId).intValue()));
            classObj = criteria.uniqueResult();
            session.flush();
            session.clear();
            //Commit the transaction.
            session.getTransaction().commit();
        } catch (Exception e) {
            LOGGER.error("Exception Occurred during get entity...", e);
            throw new SharedServiceException("Error :" + e.toString());
        } finally {
            if (null != session) {
                session.close();
            }
        }
        return classObj;
    }
}
