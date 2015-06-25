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
import org.springframework.beans.factory.annotation.Value;

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

    @Value("${USER_SERVICE_ERROR_101}")
    private String userServiceError101;

    @Value("${USER_SERVICE_ERROR_102}")
    private String userServiceError102;

    @Value("${USER_SERVICE_ERROR_103}")
    private String userServiceError103;

    @Value("${USER_SERVICE_ERROR_104}")
    private String userServiceError104;

    @Value("${USER_SERVICE_ERROR_105}")
    private String userServiceError105;

    @Value("${USER_SERVICE_ERROR_106}")
    private String userServiceError106;

    @Value("${USER_SERVICE_ERROR_107}")
    private String userServiceError107;


    /**
     * Method to validate the request parameters.
     *
     * @param userServiceRequest
     * @throws SharedServiceException
     */
    private void validateRequest(UserServiceRequest userServiceRequest) throws SharedServiceException {
        if (!StringUtils.isEmpty(userServiceRequest.getUserProfile().getFirstName())) {
            throw new SharedServiceException(messageProp.getProperty(userServiceError103));
        }
        if (!StringUtils.isEmpty(userServiceRequest.getUserProfile().getLastName())) {
            throw new SharedServiceException(messageProp.getProperty(userServiceError104));
        }
        if (!StringUtils.isEmpty(userServiceRequest.getUserProfile().getPrimaryEmailAddress())) {
            throw new SharedServiceException(messageProp.getProperty(userServiceError105));
        }
        if (!StringUtils.isEmpty(userServiceRequest.getUserProfile().getEcid())) {
            throw new SharedServiceException(messageProp.getProperty(userServiceError106));
        }
        if (!StringUtils.isEmpty(userServiceRequest.getUserProfile().getPassword())) {
            throw new SharedServiceException(messageProp.getProperty(userServiceError107));
        }
    }

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

            //Validate the user request
            validateRequest(userServiceRequest);

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
                    if (null != profileAttributeList) {
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
                if (userProfileAttribVisibleSet.size() > 0) {
                    authorProfile.setUserProfileAttribVisibles(userProfileAttribVisibleSet);
                }
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
                    if (null != addressType) {
                        userAddresses.setAddressType(addressType);
                        userAddresses.setAddress(address);
                        userAddresses.setUsersByCreatedBy(user);
                        userAddresses.setUsersByUserId(user);
                        userAddresses.setUsersByCreatedBy(user);
                        userAddresses.setUsersByUpdatedBy(user);
                        session.save(userAddresses);
                        userAddressesSet.add(userAddresses);
                    }
                }
                if (userAddressesSet.size() > 0) {
                    user.setUserAddressesesForUserId(userAddressesSet);
                }
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
                    researchFunders = UserServiceHelper.setResearchFunders(researchFunders, funder);
                    researchFunders.setCreatedDate(UserServiceHelper.getDate());
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
                    AreaOfInterest areaOfInterest = (AreaOfInterest) getEntity("areaOfInterestCd", myInterest.getAreaofInterestCd(), AreaOfInterest.class, false);
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
                    authCoauthDetails.setAuthCoauthId(user.getUserId());
                    //TODO: Set CoAuthor ID
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
                    Journals journals = (Journals) getEntityById(CommonConstants.JOURNAL_ID, preferredJournal.getJournalId(), Journals.class);
                    if (null != journals) {
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
                }
                if (userPreferredJournalsSet.size() > 0) {
                    authorProfile.setUserPreferredJournalses(userPreferredJournalsSet);
                }
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
                            alerts = UserServiceHelper.setUserAlerts(alerts, alertType);
                            alerts.setId(userAlertsId);
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
            if (null != e.getCause()) {
                throw new SharedServiceException("Error :" + e.getCause().getMessage());
            } else {
                throw new SharedServiceException("Error :" + e);
            }
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
                throw new SharedServiceException(messageProp.getProperty(userServiceError101));
            }
            Users user = (Users) getEntityById(CommonConstants.USER_ID, userId, Users.class);
            //Throw Error if user is not found.
            if (null == user) {
                throw new SharedServiceException(messageProp.getProperty(userServiceError102));
            }

            //Get User Profile info
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


            //Get CoAuthor Details.
            List<CoAuthor> coAuthorList = new LinkedList<>();
            Set<AuthCoauthDetails> authCoauthDetailsSet = user.getAuthCoauthDetailsesForCreatedBy();
            if (null != authCoauthDetailsSet && authCoauthDetailsSet.size() > 0) {
                LOGGER.debug("Get CoAuthor...");
                for (AuthCoauthDetails authCoauthDetails : authCoauthDetailsSet) {
                    CoAuthor coAuthor = new CoAuthor();
                    coAuthor = UserServiceHelper.getAuthCoauthDetails(authCoauthDetails, coAuthor);
                    coAuthorList.add(coAuthor);
                }
                userProfile.setCoAuthors(coAuthorList);
            }

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
            if (null != e.getCause()) {
                throw new SharedServiceException("Error :" + e.getCause().getMessage());
            } else {
                throw new SharedServiceException("Error :" + e);
            }
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
            LOGGER.info("Deleting User ...");
            if (null == userId) {
                throw new SharedServiceException(messageProp.getProperty(userServiceError101));
            }

            Users user = (Users) getEntityById(CommonConstants.USER_ID, userId, Users.class);

            //Open Session.
            session = sessionFactory.openSession();
            //Begin the transaction.
            session.beginTransaction();


            //Get Profile
            UserProfile authorProfile = user.getUserProfileByUserId();

            if (null != authorProfile) {

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
                    AreaOfInterest areaOfInterest = userAreaOfInterest.getAreaOfInterest();
                    if (null != areaOfInterest) {
                        session.delete(areaOfInterest);
                    }
                    session.delete(userAreaOfInterest);
                }


                //Delete Funders
                Set<UserFunders> userFundersSet = user.getUserProfileByUserId().getUserFunderses();
                for (UserFunders userFunders : userFundersSet) {
                    Set<UserFunderGrants> userFunderGrantsSet = userFunders.getUserFunderGrantses();
                    ResearchFunders researchFunders = userFunders.getResearchFunders();
                    if (null != researchFunders) {
                        session.delete(researchFunders);
                    }
                    for (UserFunderGrants userFunderGrant : userFunderGrantsSet) {
                        session.delete(userFunderGrant);
                    }
                    session.delete(userFunders);
                }

                //Delete Affiliations
                Set<UserAffiliations> userAffiliationsSet = user.getUserProfileByUserId().getUserAffiliationses();
                for (UserAffiliations userAffiliations : userAffiliationsSet) {
                    session.delete(userAffiliations);
                }


                //Delete Addresses
                Set<UserAddresses> userAddressesesForUserId = user.getUserAddressesesForUserId();
                for (UserAddresses userAddresses : userAddressesesForUserId) {
                    com.wiley.gr.ace.sharedservices.persistence.entity.Address address = userAddresses.getAddress();
                    if (null != address) {
                        session.delete(address);
                    }
                    session.delete(userAddresses);
                }

                //Delete Society
                Set<UserSocietyDetails> userSocietyDetailsSet = user.getUserProfileByUserId().getUserSocietyDetailses();
                for (UserSocietyDetails userSocietyDetails : userSocietyDetailsSet) {
                    Societies societies = userSocietyDetails.getSocieties();
                    if (null != societies) {
                        session.delete(societies);
                    }
                    session.delete(userSocietyDetails);
                }

                //Delete User Profile Visible.
                Set<UserProfileAttribVisible> userProfileAttribVisibles = user.getUserProfileByUserId().getUserProfileAttribVisibles();
                for (UserProfileAttribVisible userProfileAttribVisible : userProfileAttribVisibles) {
                    ProfileAttributeList profileAttributeList = userProfileAttribVisible.getProfileAttributeList();
                    if (null != profileAttributeList) {
                        session.delete(profileAttributeList);
                    }
                    session.delete(userProfileAttribVisible);
                }

                //Delete user reference data
                session.delete(authorProfile);
                session.delete(user);

                LOGGER.info("Flush...");
                session.flush();
                LOGGER.info("Clear...");
                session.clear();
                LOGGER.info("Commit...");
                session.getTransaction().commit();
            }

        } catch (Exception e) {
            //Rollback the session if any exception occurs.
            if (null != session) {
                session.getTransaction().rollback();
            }
            LOGGER.error("Exception Occurred during user deletion...", e);
            if (null != e.getCause()) {
                throw new SharedServiceException("Error :" + e.getCause().getMessage());
            } else {
                throw new SharedServiceException("Error :" + e);
            }
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
                throw new SharedServiceException(messageProp.getProperty(userServiceError101));
            }

            Users user = (Users) getEntityById(CommonConstants.USER_ID, userId, Users.class);

            //Throw Error if user is not found.
            if (null == user) {
                throw new SharedServiceException(messageProp.getProperty(userServiceError102));
            }

            session = sessionFactory.openSession();
            //Begin the transaction.
            session.beginTransaction();


            user = UserServiceHelper.setUserProfileInformation(userServiceRequest, user);
            //Create AuthorProfile Object
            UserProfile authorProfile = user.getUserProfileByUserId();
            LOGGER.info("Update Author Profile...");
            authorProfile = UserServiceHelper.setAuthorProfile(userServiceRequest, authorProfile);


            //Update User Secondary Email Address
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
                        session.update(secondaryEmailAddr);
                    }

                }
            }

            //Set Orcid id information
            UserReferenceData userReferenceData = user.getUserReferenceDataByUserId();
            if (null != userReferenceData) {
                LOGGER.debug("Update UserReferenceData...");
                userReferenceData = UserServiceHelper.setUserReference(userReferenceData, userServiceRequest);
                userReferenceData.setUsersByUserId(user);
                session.update(userReferenceData);
            }

            //Update user Address Object
            List<Address> addressList = userServiceRequest.getUserProfile().getAddresses();
            UserAddresses userAddresses = null;
            if (null != addressList && addressList.size() > 0) {
                LOGGER.debug("Update addressList...");
                Set<UserAddresses> userAddressesSet = new HashSet<>();
                for (Address addressProfile : addressList) {
                    com.wiley.gr.ace.sharedservices.persistence.entity.Address addressObj = null;
                    if (null != addressProfile.getId() && addressProfile.getStatus().equalsIgnoreCase("edit")) {
                        addressObj = (com.wiley.gr.ace.sharedservices.persistence.entity.Address) getEntityById("addressId", addressProfile.getId(), com.wiley.gr.ace.sharedservices.persistence.entity.Address.class);
                        if (null != addressObj) {
                            addressObj = UserServiceHelper.setAddress(addressObj, addressProfile);
                            session.update(addressObj);
                        }
                    } else if (null != addressProfile.getId() && addressProfile.getStatus().equalsIgnoreCase("delete")) {
                        addressObj = (com.wiley.gr.ace.sharedservices.persistence.entity.Address) getEntityById("addressId", addressProfile.getId(), com.wiley.gr.ace.sharedservices.persistence.entity.Address.class);
                        if (null != addressObj) {
                            addressObj = UserServiceHelper.setAddress(addressObj, addressProfile);
                            session.delete(addressObj);
                        }
                    } else if (addressProfile.getStatus().equalsIgnoreCase("add")) {
                        userAddresses = new UserAddresses();
                        com.wiley.gr.ace.sharedservices.persistence.entity.Address address = new com.wiley.gr.ace.sharedservices.persistence.entity.Address();
                        address = UserServiceHelper.setAddress(address, addressProfile);
                        address.setCreatedDate(UserServiceHelper.getDate());
                        address.setUpdatedDate(UserServiceHelper.getDate());
                        address.setUsersByUpdatedBy(user);
                        session.save(address);
                        AddressType addressType = (AddressType) getEntity("addressTypeCd", addressProfile.getType(), AddressType.class, false);
                        userAddresses.setAddressType(addressType);
                        userAddresses.setAddress(address);
                        userAddresses.setUsersByCreatedBy(user);
                        userAddresses.setUsersByUpdatedBy(user);
                        userAddresses.setUsersByUserId(user);
                        session.save(userAddresses);
                        userAddressesSet.add(userAddresses);
                    }
                    //Set only if there are new address are getting added.
                    if (userAddressesSet.size() > 0) {
                        user.setUserAddressesesForUserId(userAddressesSet);
                    }
                }
            }

            //Update Affiliations
            List<Affiliation> affiliationList = userServiceRequest.getUserProfile().getAffiliations();
            if (null != affiliationList && affiliationList.size() > 0) {
                LOGGER.debug("Update affiliationList...");
                Set<UserAffiliations> userAffiliationsSet = new HashSet<>();
                for (Affiliation affiliation : affiliationList) {
                    UserAffiliations userAffiliations = null;
                    if (null != affiliation.getId() && affiliation.getStatus().equalsIgnoreCase("edit")) {
                        userAffiliations = (UserAffiliations) getEntityById("affiliationId", affiliation.getId(), UserAffiliations.class);
                        if (null != userAffiliations) {
                            userAffiliations = UserServiceHelper.setUserAffiliations(userAffiliations, affiliation);
                            session.update(userAffiliations);
                        }
                    } else if (null != affiliation.getId() && affiliation.getStatus().equalsIgnoreCase("delete")) {
                        userAffiliations = (UserAffiliations) getEntityById("affiliationId", affiliation.getId(), UserAffiliations.class);
                        if (null != userAffiliations) {
                            userAffiliations = UserServiceHelper.setUserAffiliations(userAffiliations, affiliation);
                            session.delete(userAffiliations);
                        }
                    } else if (affiliation.getStatus().equalsIgnoreCase("add")) {
                        userAffiliations = new UserAffiliations();
                        userAffiliations = UserServiceHelper.setUserAffiliations(userAffiliations, affiliation);
                        userAffiliations.setCreatedDate(UserServiceHelper.getDate());
                        userAffiliations.setUsersByUpdatedBy(user);
                        userAffiliations.setUserProfile(authorProfile);
                        session.save(userAffiliations);
                        userAffiliationsSet.add(userAffiliations);
                    }
                }
                if (userAffiliationsSet.size() > 0) {
                    //Set User Affiliations
                    authorProfile.setUserAffiliationses(userAffiliationsSet);
                }
            }

            //Update Societies
            List<Society> societyList = userServiceRequest.getUserProfile().getSocieties();
            if (null != societyList && societyList.size() > 0) {
                LOGGER.debug("Update societyList...");
                Set<UserSocietyDetails> societyDetailsSet = new HashSet<>();
                for (Society society : societyList) {
                    UserSocietyDetails userSocietyDetails = null;
                    if (null != society.getId() && society.getStatus().equalsIgnoreCase("edit")) {
                        userSocietyDetails = (UserSocietyDetails) getEntityById("userSocietyId", society.getId(), UserSocietyDetails.class);
                        if (null != userSocietyDetails) {
                            userSocietyDetails = UserServiceHelper.setUserSocietyDetails(userSocietyDetails, society);
                            session.update(userSocietyDetails);
                        }
                    } else if (null != society.getId() && society.getStatus().equalsIgnoreCase("delete")) {
                        userSocietyDetails = (UserSocietyDetails) getEntityById("userSocietyId", society.getId(), UserSocietyDetails.class);
                        if (null != userSocietyDetails) {
                            userSocietyDetails = UserServiceHelper.setUserSocietyDetails(userSocietyDetails, society);
                            session.delete(userSocietyDetails);
                        }
                    } else if (null != society.getStatus() && society.getStatus().equalsIgnoreCase("add")) {
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
                }//End of for loop
                if (societyDetailsSet.size() > 0) {
                    authorProfile.setUserSocietyDetailses(societyDetailsSet);
                }
            }

            //Update CoAuthors
            List<CoAuthor> coAuthorList = userServiceRequest.getUserProfile().getCoAuthors();
            if (null != coAuthorList && coAuthorList.size() > 0) {
                LOGGER.debug("Update CoAuthor...");
                Set<AuthCoauthDetails> authCoauthDetailsSet = new HashSet<>();
                for (CoAuthor coAuthor : coAuthorList) {
                    if (null != coAuthor.getId() && coAuthor.getStatus().equalsIgnoreCase("edit")) {
                        AuthCoauthDetails authCoauthDetails = (AuthCoauthDetails) getEntityById("authCoauthId", coAuthor.getId(), AuthCoauthDetails.class);
                        if (null != authCoauthDetails) {
                            authCoauthDetails = UserServiceHelper.setAuthCoauthDetails(authCoauthDetails, coAuthor);
                            authCoauthDetails.setUsersByUpdatedBy(user);
                            session.update(authCoauthDetails);
                        }
                    } else if (null != coAuthor.getId() && coAuthor.getStatus().equalsIgnoreCase("delete")) {
                        AuthCoauthDetails authCoauthDetails = (AuthCoauthDetails) getEntityById("authCoauthId", coAuthor.getId(), AuthCoauthDetails.class);
                        if (null != authCoauthDetails) {
                            authCoauthDetails = UserServiceHelper.setAuthCoauthDetails(authCoauthDetails, coAuthor);
                            session.delete(authCoauthDetails);
                        }
                    } else if (null != coAuthor.getStatus() && coAuthor.getStatus().equalsIgnoreCase("add")) {
                        AuthCoauthDetails authCoauthDetails = new AuthCoauthDetails();
                        authCoauthDetails = UserServiceHelper.setAuthCoauthDetails(authCoauthDetails, coAuthor);
                        authCoauthDetails.setCreatedDate(UserServiceHelper.getDate());
                        authCoauthDetails.setUserProfileByAuthorUserId(authorProfile);
                        authCoauthDetails.setAuthCoauthId(user.getUserId());
                        //TODO: Set CoAuthor ID
                        authCoauthDetails.setUsersByCreatedBy(user);
                        authCoauthDetails.setUsersByUpdatedBy(user);
                        session.save(authCoauthDetails);
                        authCoauthDetailsSet.add(authCoauthDetails);
                    }
                }//End of for loop
                if (authCoauthDetailsSet.size() > 0) {
                    authorProfile.setAuthCoauthDetailsesForAuthorUserId(authCoauthDetailsSet);
                }
            }

            //Update Alerts
            List<Alert> alertList = userServiceRequest.getUserProfile().getAlerts();
            UserAlerts alerts = null;
            if (null != alertList && alertList.size() > 0) {
                LOGGER.info("Set Alerts...");
                Set<UserAlerts> userAlertsHashSet = new HashSet<>();
                for (Alert alert : alertList) {
                    List<AlertType> alertTypeList = alert.getAlertTypes();
                    if (null != alert.getId() && alert.getStatus().equalsIgnoreCase("edit")) {
                        if (null != alertTypeList && alertTypeList.size() > 0) {
                            int alertListSize = alertTypeList.size();
                            for (int j = 0; j < alertListSize; j++) {
                                AlertType alertType = alertTypeList.get(j);
                                UserAlertsId userAlertsId = new UserAlertsId();
                                userAlertsId.setUserId(user.getUserId());
                                userAlertsId.setAlertCd(alert.getAlertCd());
                                UserAlerts userAlerts = (UserAlerts) session.get(UserAlerts.class, userAlertsId);
                                userAlerts.setEmailFlg(alertType.getEmail());
                                userAlerts.setOnScreenFlg(alertType.getOnScreen());
                                userAlerts.setUsersByUpdatedBy(user);
                                userAlerts.setUpdatedDate(UserServiceHelper.getDate());
                                if (null != userAlerts) {
                                    session.update(userAlerts);
                                }
                            }
                        }

                    } else if (null != alert.getId() && alert.getStatus().equalsIgnoreCase("delete")) {
                        if (null != alertTypeList && alertTypeList.size() > 0) {
                            int alertListSize = alertTypeList.size();
                            for (int j = 0; j < alertListSize; j++) {
                                UserAlertsId userAlertsId = new UserAlertsId();
                                userAlertsId.setUserId(user.getUserId());
                                userAlertsId.setAlertCd(alert.getAlertCd());
                                UserAlerts userAlerts = (UserAlerts) session.get(UserAlerts.class, userAlertsId);
                                if (null != userAlerts) {
                                    session.delete(userAlerts);
                                }
                            }
                        }
                    } else if (null != alert.getStatus() && alert.getStatus().equalsIgnoreCase("add")) {
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
                                alerts.setCreatedDate(UserServiceHelper.getDate());
                                alerts.setUpdatedDate(UserServiceHelper.getDate());
                                session.save(alerts);
                                userAlertsHashSet.add(alerts);
                            }
                        }
                    }
                }
            }

            //Update Preferred Journals
            List<PreferredJournal> preferredJournalList = userServiceRequest.getUserProfile().getJournals();
            UserPreferredJournals userPreferredJournals = null;
            if (null != preferredJournalList && preferredJournalList.size() > 0) {
                LOGGER.debug("Update preferredJournalList...");
                Set<UserPreferredJournals> userPreferredJournalsSet = new HashSet<>();
                for (PreferredJournal preferredJournal : preferredJournalList) {
                    if (null != preferredJournal.getId() && preferredJournal.getStatus().equalsIgnoreCase("edit")) {
                        UserPreferredJournalsId userPreferredJournalsId = new UserPreferredJournalsId();
                        userPreferredJournalsId.setUserId(user.getUserId());
                        userPreferredJournalsId.setJournalId(Integer.parseInt(preferredJournal.getId()));
                        UserPreferredJournals userPreferredJournal = (UserPreferredJournals) session.get(UserPreferredJournals.class, userPreferredJournalsId);
                        if (null != userPreferredJournal) {
                            session.update(userPreferredJournal);
                        }
                    } else if (null != preferredJournal.getId() && preferredJournal.getStatus().equalsIgnoreCase("delete")) {
                        UserPreferredJournalsId userPreferredJournalsId = new UserPreferredJournalsId();
                        userPreferredJournalsId.setUserId(user.getUserId());
                        userPreferredJournalsId.setJournalId(Integer.parseInt(preferredJournal.getId()));
                        UserPreferredJournals userPreferredJournal = (UserPreferredJournals) session.get(UserPreferredJournals.class, userPreferredJournalsId);
                        if (null != userPreferredJournal) {
                            session.delete(userPreferredJournal);
                        }
                    } else if (null != preferredJournal.getStatus() && preferredJournal.getStatus().equalsIgnoreCase("add")) {
                        UserPreferredJournalsId userPreferredJournalsId = new UserPreferredJournalsId();
                        userPreferredJournals = new UserPreferredJournals();
                        Journals journals = new Journals();
                        journals.setJouTitle(preferredJournal.getJournalTitle());
                        journals.setCreatedDate(UserServiceHelper.getDate());
                        journals.setUpdatedDate(UserServiceHelper.getDate());
                        session.save(journals);
                        userPreferredJournals = UserServiceHelper.setUserPreferredJournals(userPreferredJournals, journals);
                        userPreferredJournalsId.setUserId(user.getUserId());
                        userPreferredJournalsId.setJournalId(journals.getJournalId());
                        userPreferredJournals.setId(userPreferredJournalsId);
                        userPreferredJournals.setUserProfile(authorProfile);
                        userPreferredJournals.setUsersByCreatedBy(user);
                        userPreferredJournals.setUsersByUpdatedBy(user);
                        userPreferredJournals.setCreatedDate(UserServiceHelper.getDate());
                        userPreferredJournals.setUpdatedDate(UserServiceHelper.getDate());
                        session.save(userPreferredJournals);
                        userPreferredJournalsSet.add(userPreferredJournals);
                    }
                }//End of For Loop
                if (userPreferredJournalsSet.size() > 0) {
                    authorProfile.setUserPreferredJournalses(userPreferredJournalsSet);
                }
            }


            //Update Area of interest
            List<MyInterest> myInterestList = userServiceRequest.getUserProfile().getMyInterests();
            UserAreaOfInterest userAreaOfInterest = null;
            if (null != myInterestList && myInterestList.size() > 0) {
                Set<UserAreaOfInterest> userAreaOfInterestHashSet = new HashSet<>();
                LOGGER.debug("Set MyInterest...");
                for (MyInterest myInterest : myInterestList) {
                    if (null != myInterest.getId() && myInterest.getStatus().equalsIgnoreCase("edit")) {
                        UserAreaOfInterestId userAreaOfInterestId = new UserAreaOfInterestId();
                        userAreaOfInterestId.setUserId(user.getUserId());
                        userAreaOfInterestId.setAreaOfInterestCd(myInterest.getId());
                        UserAreaOfInterestId userArea = (UserAreaOfInterestId) session.get(UserAreaOfInterestId.class, userAreaOfInterestId);
                        AreaOfInterest areaOfInterest = (AreaOfInterest) getEntity("areaOfInterestCd", myInterest.getAreaofInterestCd(), AreaOfInterest.class, false);
                        if (null != areaOfInterest) {
                            userAreaOfInterest = UserServiceHelper.setUserAreaOfInterest(userAreaOfInterest, areaOfInterest);
                            session.update(userArea);
                        }

                    } else if (null != myInterest.getId() && myInterest.getStatus().equalsIgnoreCase("delete")) {
                        UserAreaOfInterestId userAreaOfInterestId = new UserAreaOfInterestId();
                        userAreaOfInterestId.setUserId(user.getUserId());
                        userAreaOfInterestId.setAreaOfInterestCd(myInterest.getId());
                        UserAreaOfInterestId userArea = (UserAreaOfInterestId) session.get(UserAreaOfInterestId.class, userAreaOfInterestId);
                        session.update(userArea);
                    } else if (null != myInterest.getStatus() && myInterest.getStatus().equalsIgnoreCase("add")) {
                        userAreaOfInterest = new UserAreaOfInterest();
                        AreaOfInterest areaOfInterest = (AreaOfInterest) getEntity("areaOfInterestCd", myInterest.getAreaofInterestCd(), AreaOfInterest.class, false);
                        if (null != areaOfInterest) {
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
                            userAreaOfInterestHashSet.add(userAreaOfInterest);
                        }
                    }
                }
                if (userAreaOfInterestHashSet.size() > 0) {
                    authorProfile.setUserAreaOfInterests(userAreaOfInterestHashSet);
                }

            }

            //Update funder details
            List<Funder> funders = userServiceRequest.getUserProfile().getFunders();
            if (null != funders && funders.size() > 0) {
                LOGGER.debug("Update Funder...");
                for (Funder funder : funders) {
                    if (null != funder.getId() && funder.getStatus().equalsIgnoreCase("edit")) {

                        Set<UserFunderGrants> grants = new HashSet<>();
                        List<GrantNumber> grantList = funder.getGrantNumbers();
                        int grantListSize = grantList.size();
                        ResearchFunders researchFunders = (ResearchFunders) getEntity("funderName", funder.getResearchFunderName(), ResearchFunders.class, false);
                        UserFunders userFunders = new UserFunders();
                        userFunders.setResearchFunders(researchFunders);
                        userFunders.setUserProfile(authorProfile);
                        userFunders.setCreatedDate(UserServiceHelper.getDate());
                        userFunders.setUpdatedDate(UserServiceHelper.getDate());
                        userFunders.setUsersByCreatedBy(user);
                        userFunders.setUsersByUpdatedBy(user);
                        userFunders.setUserProfile(authorProfile);
                        session.save(userFunders);

                        for (int i = 0; i < grantListSize; i++) {
                            UserFunderGrants userFunderGrants = (UserFunderGrants) getEntity("funderName", ((GrantNumber) grantList.get(i)).getGrantNumber(), UserFunderGrants.class, false);
                            userFunderGrants.setCreatedDate(UserServiceHelper.getDate());
                            userFunderGrants.setUsersByCreatedBy(user);
                            userFunderGrants.setUsersByUpdatedBy(user);
                            userFunderGrants.setUserFunders(userFunders);
                            session.save(userFunderGrants);
                            grants.add(userFunderGrants);
                        }

                    } else if (null != funder.getId() && funder.getStatus().equalsIgnoreCase("delete")) {

                    } else if (null != funder.getStatus() && funder.getStatus().equalsIgnoreCase("add")) {
                        Set<UserFunderGrants> grants = new HashSet<>();
                        List<GrantNumber> grantList = funder.getGrantNumbers();
                        int grantListSize = grantList.size();
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

                            for (int i = 0; i < grantListSize; i++) {
                                UserFunderGrants userFunderGrants = new UserFunderGrants();
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
                }

            }


            //Set Author Profile to user
            authorProfile.setUsersByUserId(user);
            LOGGER.debug("Update session...");
            session.saveOrUpdate(user);
            session.saveOrUpdate(authorProfile);
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
            if (null != e.getCause()) {
                throw new SharedServiceException("Error :" + e.getCause().getMessage());
            } else {
                throw new SharedServiceException("Error :" + e);
            }
        } finally {
            if (null != session) {
                //Close the session
                session.close();
            }
        }

    }

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
    private <T> Object getEntityById(String columnName, String primaryId, Class<T> entityClass) throws SharedServiceException {
        return getEntity(columnName, primaryId, entityClass, true);
    }

    /**
     * @param firstColumnName
     * @param firstValue
     * @param secondColumnName
     * @param secondValue
     * @param entityClass
     * @param <T>
     * @return
     * @throws SharedServiceException
     */
    private <T> Object getEntity(String firstColumnName, String firstValue, String secondColumnName, String secondValue, Class<T> entityClass) throws SharedServiceException {
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
            criteria.add(Restrictions.eq(firstColumnName, Integer.parseInt(firstValue)));
            criteria.add(Restrictions.eq(secondColumnName, secondValue));
            classObj = criteria.uniqueResult();
            //Flush the session
            session.flush();
            //Clear session
            session.clear();
            //Commit the transaction.
            session.getTransaction().commit();
        } catch (Exception e) {
            LOGGER.error("Exception Occurred during get entity...", e);
            throw new SharedServiceException("Error :" + e);
        } finally {
            //Close the session
            if (null != session) {
                session.close();
            }
        }
        return classObj;
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
            throw new SharedServiceException("Error :" + e);
        } finally {
            //Close the session
            if (null != session) {
                session.close();
            }
        }
        return classObj;
    }


}
