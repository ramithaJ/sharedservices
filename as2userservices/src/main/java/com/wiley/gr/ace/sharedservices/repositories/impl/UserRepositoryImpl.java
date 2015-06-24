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
import com.wiley.gr.ace.sharedservices.persistence.entity.UserProfile;
import com.wiley.gr.ace.sharedservices.profile.Address;
import com.wiley.gr.ace.sharedservices.profile.*;
import com.wiley.gr.ace.sharedservices.repositories.UserRepository;
import com.wiley.gr.ace.sharedservices.service.UserService;
import org.apache.commons.lang.StringUtils;
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
     * Method to create user.
     *
     * @param userServiceRequest
     * @return
     * @throws SharedServiceException
     */
    public String createUserRepository(UserServiceRequest userServiceRequest) throws SharedServiceException {
        //Get the session from sessionFactory pool.
        Session session = null;
        Users user = null;
        try {
            LOGGER.info("Creating User ...");

            //Validate the crete user request
            if (StringUtils.isEmpty(userServiceRequest.getUserProfile().getFirstName())) {
                throw new SharedServiceException(messageProp.getProperty(CommonConstants.ERROR_CODE_103));
            }
            if (StringUtils.isEmpty(userServiceRequest.getUserProfile().getLastName())) {
                throw new SharedServiceException(messageProp.getProperty(CommonConstants.ERROR_CODE_104));
            }
            if (StringUtils.isEmpty(userServiceRequest.getUserProfile().getPrimaryEmailAddress())) {
                throw new SharedServiceException(messageProp.getProperty(CommonConstants.ERROR_CODE_105));
            }
            if (StringUtils.isEmpty(userServiceRequest.getUserProfile().getEcid())) {
                throw new SharedServiceException(messageProp.getProperty(CommonConstants.ERROR_CODE_106));
            }
            if (StringUtils.isEmpty(userServiceRequest.getUserProfile().getPassword())) {
                throw new SharedServiceException(messageProp.getProperty(CommonConstants.ERROR_CODE_107));
            }

            //Create user.
            user = new Users();
            //Open the session.
            session = sessionFactory.openSession();
            //Begin the transaction.
            session.beginTransaction();

            LOGGER.info("Set User Profile...");
            user = UserServiceHelper.setUserProfileInformation(userServiceRequest, user);
            user.setCreatedDate(UserServiceHelper.getDate());
            session.save(user);
            user.setUsersByCreatedBy(user);
            user.setUsersByUpdatedBy(user);


            //Create AuthorProfile Object
            UserProfile authorProfile = new UserProfile();
            LOGGER.info("Set Author Profile...");
            authorProfile = UserServiceHelper.setAuthorProfile(userServiceRequest, authorProfile);
            authorProfile.setCreatedDate(UserServiceHelper.getDate());
            authorProfile.setUsersByCreatedBy(user);
            authorProfile.setUsersByUpdatedBy(user);
            authorProfile.setUsersByUserId(user);
            session.save(authorProfile);

            //Set Orcid id information
            if (!StringUtils.isEmpty(userServiceRequest.getUserProfile().getOrcidId())) {
                LOGGER.debug("Set User Reference Data...");
                UserReferenceData userReferenceData = new UserReferenceData();
                userReferenceData = UserServiceHelper.setUserReference(userReferenceData, userServiceRequest);
                userReferenceData.setCreatedDate(UserServiceHelper.getDate());
                userReferenceData.setUsersByCreatedBy(user);
                userReferenceData.setUsersByUpdatedBy(user);
                userReferenceData.setUsersByUserId(user);
                session.save(userReferenceData);
            }

            //Set Profile Information
            List<ProfileVisible> profileVisibleList = userServiceRequest.getUserProfile().getProfileVisible();
            Set<UserProfileAttribVisible> userProfileAttribVisibleSet = new HashSet<>();
            if (null != profileVisibleList && profileVisibleList.size() > 0) {
                LOGGER.debug("Set Profile Visible Id...");
                for (ProfileVisible profileVisible : profileVisibleList) {
                    //Get the Profile Attribute List
                    ProfileAttributeList profileAttributeList = (ProfileAttributeList) getEntity("profileAttribCd", profileVisible.getTitleCd(), ProfileAttributeList.class, false);

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
                authorProfile.setUserProfileAttribVisibles(userProfileAttribVisibleSet);
            }

            //Create User Secondary Email Address
            Set<UserSecondaryEmailAddr> userSecondaryEmailAddrSet = new HashSet<>();
            UserSecondaryEmailAddr secondaryEmailAddr = new UserSecondaryEmailAddr();
            secondaryEmailAddr = UserServiceHelper.setUserSecondaryEmailAddr(userServiceRequest, secondaryEmailAddr, user);
            if (null != secondaryEmailAddr) {
                LOGGER.debug("Set Secondary Email Addr...");
                secondaryEmailAddr.setCreatedDate(UserServiceHelper.getDate());
                secondaryEmailAddr.setUsersByCreatedBy(user);
                secondaryEmailAddr.setUsersByUpdatedBy(user);
                userSecondaryEmailAddrSet.add(secondaryEmailAddr);
                //Set the secondary email address to the user object
                secondaryEmailAddr.setUsersByUserId(user);
            }


            //Create user Address Object
            List<Address> addressList = userServiceRequest.getUserProfile().getAddresses();
            UserAddresses userAddresses = null;
            if (null != addressList && addressList.size() > 0) {
                Set<UserAddresses> userAddressesSet = new HashSet<>();
                LOGGER.debug("Set User  Addr...");
                for (Address addressProfile : addressList) {
                    userAddresses = new UserAddresses();
                    com.wiley.gr.ace.sharedservices.persistence.entity.Address address = new com.wiley.gr.ace.sharedservices.persistence.entity.Address();
                    address = UserServiceHelper.setAddress(address, addressProfile);
                    address.setCreatedDate(UserServiceHelper.getDate());
                    address.setUsersByCreatedBy(user);
                    address.setUsersByUpdatedBy(user);
                    session.save(address);
                    AddressType addressType = (AddressType) getEntity("addressTypeCd", addressProfile.getType(), AddressType.class, false);
                    userAddresses.setAddress(address);
                    userAddresses.setAddressType(addressType);
                    userAddresses.setAddress(address);
                    userAddresses.setUsersByCreatedBy(user);
                    userAddresses.setUsersByUserId(user);
                    userAddresses.setUsersByCreatedBy(user);
                    userAddresses.setUsersByUpdatedBy(user);
                    session.save(userAddresses);
                    userAddressesSet.add(userAddresses);
                }
                user.setUserAddressesesForUserId(userAddressesSet);
            }

            //Setting Affiliations
            List<Affiliation> affiliationList = userServiceRequest.getUserProfile().getAffiliations();
            UserAffiliations affiliations = null;
            if (null != affiliationList && affiliationList.size() > 0) {
                LOGGER.debug("Set Affiliation...");
                Set<UserAffiliations> userAffiliationsSet = new HashSet<>();
                for (Affiliation affiliation : affiliationList) {
                    affiliations = new UserAffiliations();
                    affiliations = UserServiceHelper.setUserAffiliations(affiliations, affiliation);
                    affiliations.setCreatedDate(UserServiceHelper.getDate());
                    affiliations.setUsersByCreatedBy(user);
                    affiliations.setUsersByUpdatedBy(user);
                    affiliations.setUserProfile(authorProfile);
                    session.save(affiliations);
                    userAffiliationsSet.add(affiliations);
                }

                //Set User Affiliations
                authorProfile.setUserAffiliationses(userAffiliationsSet);
            }

            //Set funder details
            List<Funder> funders = userServiceRequest.getUserProfile().getFunders();
            UserFunderGrants userFunderGrants = null;
            UserFunders userFunders = null;
            ResearchFunders researchFunders = null;
            if (null != funders && funders.size() > 0) {
                LOGGER.debug("Set Funder...");
                for (Funder funder : funders) {
                    Set<UserFunderGrants> grants = new HashSet<>();
                    List<GrantNumber> grantList = funder.getGrantNumbers();
                    int grantListSize = grantList.size();
                    researchFunders = new ResearchFunders();
                    researchFunders.setFunderName(funder.getResearchFunderName());
                    researchFunders.setFunderDoi(funder.getResearchFunderDoi());
                    researchFunders.setCreatedDate(UserServiceHelper.getDate());
                    researchFunders.setUpdatedDate(UserServiceHelper.getDate());
                    researchFunders.setUsersByCreatedBy(user);
                    researchFunders.setUsersByUpdatedBy(user);
                    session.save(researchFunders);

                    userFunders = new UserFunders();
                    userFunders.setResearchFunders(researchFunders);
                    userFunders.setUserProfile(authorProfile);
                    userFunders.setCreatedDate(UserServiceHelper.getDate());
                    userFunders.setUpdatedDate(UserServiceHelper.getDate());
                    userFunders.setUsersByCreatedBy(user);
                    userFunders.setUsersByUpdatedBy(user);
                    userFunders.setUserProfile(authorProfile);
                    session.save(userFunders);

                    for (int i = 0; i < grantListSize; i++) {
                        userFunderGrants = new UserFunderGrants();
                        userFunderGrants = UserServiceHelper.setUserFunderGrants(userFunderGrants, ((GrantNumber) grantList.get(i)).getGrantNumber());
                        userFunderGrants.setCreatedDate(UserServiceHelper.getDate());
                        userFunderGrants.setUsersByCreatedBy(user);
                        userFunderGrants.setUsersByUpdatedBy(user);
                        userFunderGrants.setUserFunders(userFunders);
                        session.save(userFunderGrants);
                        //researchFunders.getUserFunderGrantses().add(userFunderGrants);
                        grants.add(userFunderGrants);
                    }
                }

            }

            //TODO:Set Wiley Open Access Account is not part of WP1 Scope.

            //Set Society Details
            List<Society> societyList = userServiceRequest.getUserProfile().getSocieties();
            UserSocietyDetails userSocietyDetails = null;
            if (null != societyList && societyList.size() > 0) {
                LOGGER.debug("Set Society...");
                Set<UserSocietyDetails> societyDetailsSet = new HashSet<>();
                for (Society society : societyList) {
                    //Get Societies
                    Societies societies = (Societies) getEntity("societyCd", society.getSocietyCd(), Societies.class, false);
                    if (null != societies) {
                        userSocietyDetails = new UserSocietyDetails();
                        userSocietyDetails = UserServiceHelper.setUserSocietyDetails(userSocietyDetails, society);
                        userSocietyDetails.setSocieties(societies);
                        userSocietyDetails.setCreatedDate(UserServiceHelper.getDate());
                        userSocietyDetails.setUsersByCreatedBy(user);
                        userSocietyDetails.setUsersByUpdatedBy(user);
                        userSocietyDetails.setUserProfile(authorProfile);
                        session.save(userSocietyDetails);
                        societyDetailsSet.add(userSocietyDetails);
                    }
                }
                authorProfile.setUserSocietyDetailses(societyDetailsSet);
            }

            //Set Area of interest
            List<MyInterest> myInterestList = userServiceRequest.getUserProfile().getMyInterests();
            UserAreaOfInterest userAreaOfInterest = null;

            if (null != myInterestList && myInterestList.size() > 0) {
                Set<UserAreaOfInterest> userAreaOfInterestHashSet = new HashSet<>();
                LOGGER.debug("Set MyInterest...");
                for (MyInterest myInterest : myInterestList) {
                    userAreaOfInterest = new UserAreaOfInterest();
                    AreaOfInterest areaOfInterest = new AreaOfInterest();
                    areaOfInterest = UserServiceHelper.setAreaOfInterest(areaOfInterest, myInterest);
                    areaOfInterest.setCreatedDate(UserServiceHelper.getDate());
                    areaOfInterest.setUsersByCreatedBy(user);
                    areaOfInterest.setUsersByUpdatedBy(user);
                    session.save(areaOfInterest);
                    UserAreaOfInterestId userAreaOfInterestId = new UserAreaOfInterestId();
                    userAreaOfInterestId.setUserId(user.getUserId());
                    userAreaOfInterestId.setAreaOfInterestCd(areaOfInterest.getAreaOfInterestCd());
                    userAreaOfInterest = UserServiceHelper.setUserAreaOfInterest(userAreaOfInterest, areaOfInterest);
                    userAreaOfInterest.setCreatedDate(UserServiceHelper.getDate());
                    userAreaOfInterest.setUsersByCreatedBy(user);
                    userAreaOfInterest.setUsersByUpdatedBy(user);
                    userAreaOfInterest.setAreaOfInterest(areaOfInterest);
                    userAreaOfInterest.setUserProfile(authorProfile);
                    userAreaOfInterest.setId(userAreaOfInterestId);
                    session.save(userAreaOfInterest);
                    userAreaOfInterestHashSet.add(userAreaOfInterest);
                }
                authorProfile.setUserAreaOfInterests(userAreaOfInterestHashSet);

            }

            //Set CoAuthor Details.
            List<CoAuthor> coAuthorList = userServiceRequest.getUserProfile().getCoAuthors();
            if (null != coAuthorList && coAuthorList.size() > 0) {
                LOGGER.debug("Set CoAuthor...");
                Set<AuthCoauthDetails> authCoauthDetailsSet = new HashSet<>();
                for (CoAuthor coAuthor : coAuthorList) {
                    AuthCoauthDetails authCoauthDetails = new AuthCoauthDetails();
                    authCoauthDetails = UserServiceHelper.setAuthCoauthDetails(authCoauthDetails, coAuthor);
                    authCoauthDetails.setCreatedDate(UserServiceHelper.getDate());
                    authCoauthDetails.setUserProfileByAuthorUserId(authorProfile);
                    authCoauthDetails.setUsersByCreatedBy(user);
                    authCoauthDetails.setUsersByUpdatedBy(user);
                    session.save(authCoauthDetails);
                    authCoauthDetailsSet.add(authCoauthDetails);
                }
                authorProfile.setAuthCoauthDetailsesForAuthorUserId(authCoauthDetailsSet);
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
                    journals.setCreatedDate(UserServiceHelper.getDate());
                    journals.setUsersByCreatedBy(user);
                    journals.setUsersByUpdatedBy(user);
                    session.save(journals);
                    userPreferredJournals = UserServiceHelper.setUserPreferredJournals(userPreferredJournals, journals);
                    userPreferredJournalsId.setUserId(user.getUserId());
                    userPreferredJournalsId.setJournalId(journals.getJournalId());
                    userPreferredJournals.setId(userPreferredJournalsId);
                    userPreferredJournals.setUserProfile(authorProfile);
                    userPreferredJournals.setUsersByCreatedBy(user);
                    userPreferredJournals.setUsersByUpdatedBy(user);
                    session.save(userPreferredJournals);
                    userPreferredJournalsSet.add(userPreferredJournals);
                }
                authorProfile.setUserPreferredJournalses(userPreferredJournalsSet);
            }

            //Set Alerts
            List<Alert> alertList = userServiceRequest.getUserProfile().getAlerts();
            UserAlerts alerts = null;
            if (null != alertList && alertList.size() > 0) {
                LOGGER.info("Set Alerts...");
                Set<UserAlerts> userAlertsHashSet = new HashSet<>();
                for (Alert alert : alertList) {
                    List<AlertType> alertTypeList = alert.getAlertTypes();

                    //Get Alerts Object.
                    Alerts alertsObj = (Alerts) getEntity("alertCd", alert.getAlertCd(), Alerts.class, false);

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
                            alerts.setUsersByCreatedBy(user);
                            alerts.setUsersByUpdatedBy(user);
                            session.save(alerts);
                            userAlertsHashSet.add(alerts);
                        }
                    }
                }
            }
            LOGGER.info("Saving the User in DB...");

            //Set Author Profile to user
            authorProfile.setUsersByUserId(user);

            LOGGER.info("Saving the User Profile in DB...");
            session.save(authorProfile);
            if (null != secondaryEmailAddr) {
                LOGGER.info("Saving the secondaryEmailAddr in DB...");
                session.save(secondaryEmailAddr);
            }
            if (null != userAddresses) {
                LOGGER.info("Saving the userAddresses in DB...");
                session.save(userAddresses);
            }
            if (null != affiliations) {
                LOGGER.info("Saving the affiliations in DB...");
                session.save(affiliations);
            }
            if (null != userFunderGrants) {
                LOGGER.info("Saving the userFunderGrants in DB...");
                session.save(userFunderGrants);
            }
            if (null != userSocietyDetails) {
                LOGGER.info("Saving the userSocietyDetails in DB...");
                session.save(userSocietyDetails);
            }
            if (null != userAreaOfInterest) {
                LOGGER.info("Saving the userAreaOfInterest in DB...");
                session.save(userAreaOfInterest);
            }
            if (null != userPreferredJournals) {
                LOGGER.info("Saving the userPreferredJournals in DB...");
                session.save(userPreferredJournals);
            }
            if (null != alerts) {
                LOGGER.info("Saving the alerts in DB...");
                session.save(alerts);
            }

            LOGGER.info("Flush...");
            //Flush the session.
            session.flush();
            LOGGER.info("Clear...");
            //Clear the session object.
            session.clear();
            LOGGER.info("Commit...");
            //Commit the transaction.
            session.getTransaction().commit();

        } catch (Exception e) {
            //Rollback the session if any exception occurs.
            if (null != session) {
                LOGGER.info("Rolling back...");
                session.getTransaction().rollback();
            }
            LOGGER.error("Exception Occurred during user profile creation...", e);
            throw new SharedServiceException("Error :" + e.getCause().getMessage());
        } finally {
            if (null != session) {
                LOGGER.info("Closing session...");
                //Close the session
                session.close();
            }
        }
        return "" + user.getUserId();
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
            LOGGER.info("Get User Profile..");
            if (null == userId) {
                throw new SharedServiceException(messageProp.getProperty(CommonConstants.ERROR_CODE_101));
            }
            Users user = (Users) getEntityById(CommonConstants.USER_ID, userId, Users.class);
            //Throw Error if user is not found.
            if (null == user) {
                throw new SharedServiceException(messageProp.getProperty(CommonConstants.ERROR_CODE_102));
            }

            //Set to response pojo
            com.wiley.gr.ace.sharedservices.profile.UserProfile userProfile = UserServiceHelper.getUserProfileInfo(user);

            //Open Session
            session = sessionFactory.openSession();
            //Begin the transaction.
            session.beginTransaction();

            //Get Address and set to the response.
            List<Address> addressList = new LinkedList<>();
            Set<UserAddresses> userAddressesesForUserId = user.getUserAddressesesForUserId();
            if (null != userAddressesesForUserId && userAddressesesForUserId.size() > 0) {
                LOGGER.debug("Get UserAddresses...");
                for (UserAddresses userAddresses : userAddressesesForUserId) {
                    com.wiley.gr.ace.sharedservices.persistence.entity.Address addressEntity = userAddresses.getAddress();
                    Address address = UserServiceHelper.getAddressInfo(addressEntity);
                    addressList.add(address);
                }
                userProfile.setAddresses(addressList);
            }

            //Get Affiliations
            List<Affiliation> affiliationList = new LinkedList<>();
            Set<UserAffiliations> userAffiliationsSet = user.getUserProfileByUserId().getUserAffiliationses();
            if (null != userAffiliationsSet && userAffiliationsSet.size() > 0) {
                LOGGER.debug("Get UserAffiliations...");
                for (UserAffiliations userAffiliations : userAffiliationsSet) {
                    Affiliation affiliation = UserServiceHelper.getAffiliation(userAffiliations);
                    affiliationList.add(affiliation);
                }
                userProfile.setAffiliations(affiliationList);
            }

            //Get Funder
            List<Funder> funders = new LinkedList<>();
            Set<UserFunders> userFundersSet = user.getUserProfileByUserId().getUserFunderses();
            if (null != userFundersSet && userFundersSet.size() > 0) {
                LOGGER.debug("Get UserFunderGrants...");
                List<GrantNumber> grantNumbers = new LinkedList<>();
                for (UserFunders userFunders : userFundersSet) {
                    ResearchFunders researchFunders = userFunders.getResearchFunders();
                    Funder funder = new Funder();
                    funder.setId("" + researchFunders.getResfunderid());
                    funder.setResearchFunderName(researchFunders.getFunderName());
                    funder.setResearchFunderDoi(researchFunders.getFunderDoi());
                    Set<UserFunderGrants> userFunderGrantsSet = userFunders.getUserFunderGrantses();
                    for (UserFunderGrants userFunderGrants : userFunderGrantsSet) {
                        GrantNumber grantNumber = new GrantNumber();
                        grantNumber.setGrantNumber(userFunderGrants.getGrantNum());
                        grantNumbers.add(grantNumber);
                    }
                    funder.setGrantNumbers(grantNumbers);
                    funders.add(funder);
                }
                userProfile.setFunders(funders);
            }

            //Get Society
            List<Society> societies = new LinkedList<>();
            Set<UserSocietyDetails> userSocietyDetailsSet = user.getUserProfileByUserId().getUserSocietyDetailses();
            if (null != userSocietyDetailsSet && userSocietyDetailsSet.size() > 0) {
                LOGGER.debug("Get UserSocietyDetails...");
                for (UserSocietyDetails userSocietyDetails : userSocietyDetailsSet) {
                    Society society = UserServiceHelper.getSociety(userSocietyDetails);
                    societies.add(society);
                }
                userProfile.setSocieties(societies);
            }

            //Get MyInterest
            List<MyInterest> myInterests = new LinkedList<>();
            Set<UserAreaOfInterest> userAreaOfInterestSet = user.getUserProfileByUserId().getUserAreaOfInterests();
            if (null != userAreaOfInterestSet && userAreaOfInterestSet.size() > 0) {
                LOGGER.debug("Get UserAreaOfInterest...");
                for (UserAreaOfInterest userAreaOfInterest : userAreaOfInterestSet) {
                    AreaOfInterest areaOfInterest = userAreaOfInterest.getAreaOfInterest();
                    MyInterest myInterest = UserServiceHelper.getMyInterest(areaOfInterest);
                    myInterests.add(myInterest);
                }
                userProfile.setMyInterests(myInterests);
            }

            //TODO: Coauthor get

            //Get PreferredJournal
            List<PreferredJournal> journalsList = new LinkedList<>();
            Set<UserPreferredJournals> userPreferredJournalsSet = user.getUserProfileByUserId().getUserPreferredJournalses();
            if (null != userPreferredJournalsSet && userPreferredJournalsSet.size() > 0) {
                LOGGER.debug("Get UserPreferredJournals...");
                for (UserPreferredJournals userPreferredJournals : userPreferredJournalsSet) {
                    Journals journal = userPreferredJournals.getJournals();
                    PreferredJournal preferredJournal = UserServiceHelper.getPreferredJournal(journal);
                    journalsList.add(preferredJournal);
                }
                userProfile.setJournals(journalsList);
            }

            //Get Alerts
            List<Alert> alerts = new LinkedList<>();
            Set<UserAlerts> alertsSet = user.getUserProfileByUserId().getUserAlertses();
            if (null != alertsSet && alertsSet.size() > 0) {
                LOGGER.debug("Get UserAlerts...");
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
     * Method to delete user object.
     *
     * @param userId
     * @throws SharedServiceException
     */
    public void deleteUserRepository(String userId) throws SharedServiceException {
        //Get the session from sessionFactory pool.
        Session session = null;
        try {
            LOGGER.info("Deleting User Profile..");
            if (null == userId) {
                throw new SharedServiceException(messageProp.getProperty(CommonConstants.ERROR_CODE_101));
            }

            Users user = (Users) getEntityById(CommonConstants.USER_ID, userId, Users.class);

            //Open Session.
            session = sessionFactory.openSession();
            //Begin the transaction.
            session.beginTransaction();


            //Get Profile
            UserProfile authorProfile = user.getUserProfileByUserId();

            //Delete Alerts
            Set<UserAlerts> userAlertsSet = authorProfile.getUserAlertses();
            for (UserAlerts userAlerts : userAlertsSet) {
                session.delete(userAlerts);
            }

            //Delete Preferred Journals
            Set<UserPreferredJournals> userPreferredJournalsSet = authorProfile.getUserPreferredJournalses();
            for (UserPreferredJournals userPreferredJournals : userPreferredJournalsSet) {
                session.delete(userPreferredJournals);
            }

            //Delete CoAuthors
            Set<AuthCoauthDetails> authCoauthDetailsSet = authorProfile.getAuthCoauthDetailsesForAuthorUserId();
            for (AuthCoauthDetails authCoauthDetails : authCoauthDetailsSet) {
                session.delete(authCoauthDetails);
            }

            //Delete MyInterests
            Set<UserAreaOfInterest> userAreaOfInterestSet = authorProfile.getUserAreaOfInterests();
            for (UserAreaOfInterest userAreaOfInterest : userAreaOfInterestSet) {
                session.delete(userAreaOfInterest);
            }

            //Delete Funders

            //Delete Affiliations
            //Delete Addresses
            //Delete user reference data

            session.delete(authorProfile);


            session.delete(user);

            LOGGER.info("Flush...");
            session.flush();
            LOGGER.info("Clear...");
            session.clear();
            LOGGER.info("Commit...");
            session.getTransaction().commit();

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
     * Method to update user
     *
     * @param userServiceRequest
     * @param userId
     * @throws SharedServiceException
     */

    public void updateUserRepository(UserServiceRequest userServiceRequest, String userId) throws SharedServiceException {
        //Get the session from sessionFactory pool.
        Session session = null;
        try {
            LOGGER.info("Updating User Profile..");
            if (null == userId) {
                throw new SharedServiceException(messageProp.getProperty(CommonConstants.ERROR_CODE_101));
            }

            Users user = (Users) getEntityById(CommonConstants.USER_ID, userId, Users.class);

            //Throw Error if user is not found.
            if (null == user) {
                throw new SharedServiceException(messageProp.getProperty(CommonConstants.ERROR_CODE_102));
            }

            session = sessionFactory.openSession();
            //Begin the transaction.
            session.beginTransaction();


            user = UserServiceHelper.setUserProfileInformation(userServiceRequest, user);
            //Create AuthorProfile Object
            UserProfile authorProfile = user.getUserProfileByUserId();
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
            UserReferenceData userReferenceData = user.getUserReferenceDataByUserId();
            LOGGER.debug("Update UserReferenceData...");
            userReferenceData = UserServiceHelper.setUserReference(userReferenceData, userServiceRequest);
            userReferenceData.setUsersByUserId(user);
            session.update(userReferenceData);


            // Profile Visible
            List<ProfileVisible> profileVisibleList = userServiceRequest.getUserProfile().getProfileVisible();
            Set<UserProfileAttribVisible> userProfileAttribVisibleSet = user.getUserProfileByUserId().getUserProfileAttribVisibles();
            if (null != profileVisibleList && profileVisibleList.size() > 0) {
                LOGGER.debug("Update UserProfileAttribVisible...");
                for (ProfileVisible profileVisible : profileVisibleList) {
                   /* ProfileAttributeList profileAttributeList = new ProfileAttributeList();
                    profileAttributeList = UserServiceHelper.setProfileAttributeList(profileAttributeList, profileVisible);
                    session.save(profileAttributeList);
                    UserProfileAttribVisibleId userProfileAttribVisibleId = new UserProfileAttribVisibleId();
                    userProfileAttribVisibleId.setProfileAttribCd(profileVisible.getTitleCd());
                    userProfileAttribVisibleId.setUserId(Integer.parseInt(userId));
                    //userProfileAttribVisibleId.setProfileVisibilityFlg('1');
                    UserProfileAttribVisible userProfileAttribVisible = new UserProfileAttribVisible();
                    userProfileAttribVisible.setUserProfile(authorProfile);
                    userProfileAttribVisible.setId(userProfileAttribVisibleId);
                    userProfileAttribVisible.setProfileAttributeList(profileAttributeList);
                    session.update(userProfileAttribVisible);
                    userProfileAttribVisibleSet.add(userProfileAttribVisible);*/
                }


                authorProfile.setUserProfileAttribVisibles(userProfileAttribVisibleSet);
            }


            //Update user Address Object
            List<Address> addressList = userServiceRequest.getUserProfile().getAddresses();
            if (null != addressList && addressList.size() > 0) {
                LOGGER.debug("Update addressList...");
                for (Address address : addressList) {
                    com.wiley.gr.ace.sharedservices.persistence.entity.Address addressObj = null;
                    if (null != address.getId()) {
                        addressObj = (com.wiley.gr.ace.sharedservices.persistence.entity.Address) getEntityById("addressId", address.getId(), com.wiley.gr.ace.sharedservices.persistence.entity.Address.class);
                        addressObj = UserServiceHelper.setAddress(addressObj, address);
                        session.update(addressObj);
                    }
                }
            }

            //Update Affiliations
            List<Affiliation> affiliationList = userServiceRequest.getUserProfile().getAffiliations();
            if (null != affiliationList && affiliationList.size() > 0) {
                LOGGER.debug("Update affiliationList...");
                for (Affiliation affiliation : affiliationList) {
                    UserAffiliations userAffiliations = null;
                    if (null != affiliation.getId()) {
                        userAffiliations = (UserAffiliations) getEntityById("affiliationId", affiliation.getId(), UserAffiliations.class);
                        userAffiliations = UserServiceHelper.setUserAffiliations(userAffiliations, affiliation);
                        session.update(userAffiliations);
                    }
                }
            }

            //TODO:Update Funders
            List<Funder> funders = userServiceRequest.getUserProfile().getFunders();
            if (null != funders && funders.size() > 0) {
                LOGGER.debug("Update funders...");
                for (Funder funder : funders) {


                }
            }

            //Update Societies
            List<Society> societyList = userServiceRequest.getUserProfile().getSocieties();
            if (null != societyList && societyList.size() > 0) {
                LOGGER.debug("Update societyList...");
                for (Society society : societyList) {
                    UserSocietyDetails userSocietyDetails = null;
                    userSocietyDetails = (UserSocietyDetails) getEntityById("societyId", society.getId(), UserSocietyDetails.class);
                    userSocietyDetails = UserServiceHelper.setUserSocietyDetails(userSocietyDetails, society);
                    session.update(userSocietyDetails);
                }
            }

            //Update My Interests
            List<MyInterest> myInterestList = userServiceRequest.getUserProfile().getMyInterests();
            if (null != myInterestList && myInterestList.size() > 0) {
                LOGGER.debug("Update myInterestList...");
                for (MyInterest myInterest : myInterestList) {
                    AreaOfInterest areaOfInterest = null;
                    areaOfInterest = (AreaOfInterest) getEntityById("areaOfInterestCd", myInterest.getAreaofInterestCd(), AreaOfInterest.class);
                    areaOfInterest = UserServiceHelper.setAreaOfInterest(areaOfInterest, myInterest);
                    session.update(areaOfInterest);
                }
            }

            //TODO:Coauthor update option.

            //Update Preferred Journals
            List<PreferredJournal> preferredJournalList = userServiceRequest.getUserProfile().getJournals();
            if (null != preferredJournalList && preferredJournalList.size() > 0) {
                LOGGER.debug("Update preferredJournalList...");
                for (PreferredJournal preferredJournal : preferredJournalList) {
                    Journals journals = null;
                    journals = (Journals) getEntityById(CommonConstants.JOURNAL_ID, preferredJournal.getId(), Journals.class);
                    journals = UserServiceHelper.setJournals(journals, preferredJournal.getJournalTitle());
                    session.update(journals);
                }
            }

            //TODO:Update Alerts
            List<Alert> alertList = userServiceRequest.getUserProfile().getAlerts();
            if (null != alertList && alertList.size() > 0) {
                LOGGER.debug("Update alertList...");
                for (Alert alert : alertList) {

                }
            }

            //Set Author Profile to user
            authorProfile.setUsersByUserId(user);
            LOGGER.debug("Update session...");
            session.update(user);
            session.update(authorProfile);
            LOGGER.debug("Flush...");
            session.flush();
            LOGGER.debug("Clear...");
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
     * Class to get Entity Object from the DB primary id.
     *
     * @param columnName
     * @param primaryId
     * @param entityClass
     * @param <T>
     * @return
     * @throws SharedServiceException
     */
    private <T> Object getEntityById(String columnName, String primaryId, Class<T> entityClass) throws SharedServiceException {
        return getEntity(columnName, primaryId, entityClass, true);
    }

    /**
     * Class to get Entity Object from the DB primary id.
     *
     * @param columnName
     * @param primaryId
     * @param entityClass
     * @param typeCast
     * @param <T>
     * @return
     * @throws SharedServiceException
     */
    private <T> Object getEntity(String columnName, String primaryId, Class<T> entityClass, Boolean typeCast) throws SharedServiceException {
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
            throw new SharedServiceException("Error :" + e.toString());
        } finally {
            //Close the session
            if (null != session) {
                session.close();
            }
        }
        return classObj;
    }


}
