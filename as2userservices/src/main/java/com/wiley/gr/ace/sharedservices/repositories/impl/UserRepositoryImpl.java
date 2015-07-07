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
import com.wiley.gr.ace.sharedservices.common.Property;
import com.wiley.gr.ace.sharedservices.exceptions.SharedServiceException;
import com.wiley.gr.ace.sharedservices.helper.UserServiceHelper;
import com.wiley.gr.ace.sharedservices.payload.Error;
import com.wiley.gr.ace.sharedservices.payload.*;
import com.wiley.gr.ace.sharedservices.persistence.entity.*;
import com.wiley.gr.ace.sharedservices.persistence.entity.UserProfile;
import com.wiley.gr.ace.sharedservices.profile.Address;
import com.wiley.gr.ace.sharedservices.profile.*;
import com.wiley.gr.ace.sharedservices.repositories.UserRepository;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * @author kkalyan
 */
@Repository
public class UserRepositoryImpl extends Property implements UserRepository {

    //Logger Instance
    private static final Logger LOGGER = LoggerFactory.getLogger(UserRepositoryImpl.class);

    @Autowired
    private SessionFactory sessionFactory;

    /**
     * Repository method to create user.
     *
     * @param userServiceRequest Request Java Mapping Object
     * @return Returns Author Services User Unique id
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
            UserProfile userProfile = new UserProfile();
            LOGGER.info("Set Author Profile...");
            userProfile = UserServiceHelper.setAuthorProfile(userServiceRequest, userProfile);
            userProfile.setCreatedDate(UserServiceHelper.getDate());
            userProfile.setUsersByCreatedBy(user);
            userProfile.setUsersByUpdatedBy(user);
            userProfile.setUsersByUserId(user);
            session.save(userProfile);

            //Set Orcid id information
            if (!StringUtils.isEmpty(userServiceRequest.getUserProfile().getOrcidId())) {
                LOGGER.debug("Set User Reference Data...");
                addUserReferenceData(session, user, userServiceRequest);
            }

            //Set Profile Information
            List<ProfileVisible> profileVisibleList = userServiceRequest.getUserProfile().getProfileVisible();
            Set<UserProfileAttribVisible> userProfileAttribVisibleSet = new HashSet<>();
            if (null != profileVisibleList && profileVisibleList.size() > 0) {
                //Add Profile Visible.
                addProfileVisible(session, user, profileVisibleList, userProfile, userProfileAttribVisibleSet);
                if (userProfileAttribVisibleSet.size() > 0) {
                    userProfile.setUserProfileAttribVisibles(userProfileAttribVisibleSet);
                }
            }

            //Create User Secondary Email Address
            Set<UserSecondaryEmailAddr> userSecondaryEmailAddrSet = new HashSet<>();
            UserSecondaryEmailAddr secondaryEmailAddr = new UserSecondaryEmailAddr();
            secondaryEmailAddr = UserServiceHelper.setUserSecondaryEmailAddr(userServiceRequest, secondaryEmailAddr, user);
            if (null != secondaryEmailAddr) {
                LOGGER.debug("Set Secondary Email Addr...");
                secondaryEmailAddr.setCreatedDate(UserServiceHelper.getDate());
                secondaryEmailAddr.setUpdatedDate(UserServiceHelper.getDate());
                secondaryEmailAddr.setUsersByCreatedBy(user);
                secondaryEmailAddr.setUsersByUpdatedBy(user);
                userSecondaryEmailAddrSet.add(secondaryEmailAddr);
                //Set the secondary email address to the user object
                secondaryEmailAddr.setUsersByUserId(user);
                session.save(secondaryEmailAddr);
                userProfile.setUserSecondaryEmailAddr(secondaryEmailAddr);
            }


            //Create user Address Object
            List<Address> addressList = userServiceRequest.getUserProfile().getAddresses();
            if (null != addressList && addressList.size() > 0) {
                Set<UserAddresses> userAddressesSet = new HashSet<>();
                LOGGER.debug("Set User  Addr...");
                addAddress(session, user, userAddressesSet, addressList);
                if (userAddressesSet.size() > 0) {
                    user.setUserAddressesesForUserId(userAddressesSet);
                }
            }

            //Setting Affiliations
            List<Affiliation> affiliationList = userServiceRequest.getUserProfile().getAffiliations();
            UserAffiliations affiliations = null;
            if (null != affiliationList && affiliationList.size() > 0) {
                LOGGER.debug("Set Affiliation...");
                addAffiliation(session, user, userProfile, affiliationList);
            }

            //Set funder details
            List<Funder> funders = userServiceRequest.getUserProfile().getFunders();
            Set<UserFunders> grants = new HashSet<>();
            if (null != funders && funders.size() > 0) {
                LOGGER.debug("Set Funder...");
                addFunder(session, user, userProfile, funders, grants);
                if (grants.size() > 0) {
                    userProfile.setUserFunderses(grants);
                }

            }

            //Set Society Details
            List<Society> societyList = userServiceRequest.getUserProfile().getSocieties();
            if (null != societyList && societyList.size() > 0) {
                LOGGER.debug("Set Society...");
                Set<UserSocietyDetails> societyDetailsSet = new HashSet<>();
                addSociety(session, user, userProfile, societyList, societyDetailsSet);
                if (societyDetailsSet.size() > 0) {
                    userProfile.setUserSocietyDetailses(societyDetailsSet);
                }
            }

            //Set Area of interest
            List<MyInterest> myInterestList = userServiceRequest.getUserProfile().getMyInterests();

            if (null != myInterestList && myInterestList.size() > 0) {
                Set<UserAreaOfInterest> userAreaOfInterestHashSet = new HashSet<>();
                LOGGER.debug("Set MyInterest...");
                addInterest(session, user, userProfile, myInterestList, userAreaOfInterestHashSet);
                if (userAreaOfInterestHashSet.size() > 0) {
                    userProfile.setUserAreaOfInterests(userAreaOfInterestHashSet);
                }

            }

            //Set CoAuthor Details.
            List<CoAuthor> coAuthorList = userServiceRequest.getUserProfile().getCoAuthors();
            if (null != coAuthorList && coAuthorList.size() > 0) {
                LOGGER.debug("Set CoAuthor...");
                Set<AuthCoauthDetails> authCoauthDetailsSet = new HashSet<>();
                addAuthor(session, user, userProfile, coAuthorList, authCoauthDetailsSet);
                userProfile.setAuthCoauthDetailsesForAuthorUserId(authCoauthDetailsSet);
            }


            //Set Preferred Journals
            List<PreferredJournal> preferredJournalList = userServiceRequest.getUserProfile().getJournals();
            if (null != preferredJournalList && preferredJournalList.size() > 0) {
                LOGGER.info("Set PreferredJournal...");
                Set<UserPreferredJournals> userPreferredJournalsSet = new HashSet<>();
                addJournal(session, user, userProfile, preferredJournalList, userPreferredJournalsSet);
                if (userPreferredJournalsSet.size() > 0) {
                    userProfile.setUserPreferredJournalses(userPreferredJournalsSet);
                }
            }

            //Set Alerts
            List<Alert> alertList = userServiceRequest.getUserProfile().getAlerts();

            if (null != alertList && alertList.size() > 0) {
                LOGGER.info("Set Alerts...");
                Set<UserAlerts> userAlertsHashSet = new HashSet<>();
                addAlert(session, user, userProfile, alertList, userAlertsHashSet);
                if (userAlertsHashSet.size() > 0) {
                    userProfile.setUserAlertses(userAlertsHashSet);
                }
            }
            LOGGER.info("Saving the User in DB...");

            //Set Author Profile to user
            userProfile.setUsersByUserId(user);

            LOGGER.info("Saving the User Profile in DB...");
            session.save(userProfile);

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
                throw new SharedServiceException(CommonConstants.ERROR_CODE_100, "Error :" + e.getCause().getMessage());
            } else {
                throw new SharedServiceException(CommonConstants.ERROR_CODE_100, "Error :" + e);
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
     * Repository method to get user profile.
     *
     * @param userId Author Services Unique Id
     * @return Returns UserServuceRequest Object
     * @throws Exception
     */
    public UserServiceRequest getUserRepository(String userId) throws SharedServiceException {
        //Get the session from sessionFactory pool.
        Session session = null;
        UserServiceRequest userResponse = new UserServiceRequest();
        try {
            LOGGER.info("Get User Profile..");
            if (null == userId) {
                throw new SharedServiceException(CommonConstants.ERROR_CODE_101, userServiceError101);
            }
            Users user = (Users) getEntityById(CommonConstants.USER_ID, userId, Users.class);
            //Throw Error if user is not found.
            if (null == user) {
                throw new SharedServiceException(CommonConstants.ERROR_CODE_102, userServiceError102);
            }

            //Get User Profile info
            com.wiley.gr.ace.sharedservices.profile.UserProfile userProfile = UserServiceHelper.getUserProfileInfo(user);

            //Open Session
            session = sessionFactory.openSession();
            //Begin the transaction.
            session.beginTransaction();

            //TODO: Profile & Primary email

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
            if (null != user.getUserProfileByUserId()) {
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
            }

            //Get Funder
            if (null != user.getUserProfileByUserId()) {
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
            }

            //Get Society
            if (null != user.getUserProfileByUserId()) {
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
            }

            //Get MyInterest
            if (null != user.getUserProfileByUserId()) {
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
            if (null != user.getUserProfileByUserId()) {
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
            }

            //Get Alerts
            if (null != user.getUserProfileByUserId()) {
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
            }
            userResponse.setUserProfile(userProfile);

        } catch (Exception e) {
            //Rollback the session if any exception occurs.
            if (null != session) {
                session.getTransaction().rollback();
            }
            LOGGER.error("Exception Occurred during user profile creation...", e);
            if (null != e.getCause()) {
                throw new SharedServiceException(CommonConstants.ERROR_CODE_100, "Error :" + e.getCause().getMessage());
            } else {
                throw new SharedServiceException(CommonConstants.ERROR_CODE_100, "Error :" + e);
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
     * Repository method to delete user object.
     *
     * @param userId Author Services Unique id
     * @throws SharedServiceException
     */
    public void deleteUserRepository(String userId) throws SharedServiceException {
        //Get the session from sessionFactory pool.
        Session session = null;
        try {
            LOGGER.info("Deleting User ...");
            if (null == userId) {
                throw new SharedServiceException(CommonConstants.ERROR_CODE_101, userServiceError101);
            }

            Users user = (Users) getEntityById(CommonConstants.USER_ID, userId, Users.class);

            if (null == user) {
                throw new SharedServiceException(CommonConstants.ERROR_CODE_102, userServiceError102);
            }


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
                    session.delete(userAreaOfInterest);
                }


                //Delete Funders
                Set<UserFunders> userFundersSet = authorProfile.getUserFunderses();
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

                //Delete Affiliations
                Set<UserAffiliations> userAffiliationsSet = authorProfile.getUserAffiliationses();
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
                Set<UserSocietyDetails> userSocietyDetailsSet = authorProfile.getUserSocietyDetailses();
                for (UserSocietyDetails userSocietyDetails : userSocietyDetailsSet) {
                    session.delete(userSocietyDetails);
                }

                //Delete User Profile Visible.
                Set<UserProfileAttribVisible> userProfileAttribVisibles = authorProfile.getUserProfileAttribVisibles();
                for (UserProfileAttribVisible userProfileAttribVisible : userProfileAttribVisibles) {
                    session.delete(userProfileAttribVisible);
                }

                //Delete user reference data
                session.delete(authorProfile);
            }//End of if condition

            //Delete Reference data
            UserReferenceData userReferenceData = user.getUserReferenceDataByUserId();
            if (null != userReferenceData) {
                session.delete(userReferenceData);
            }

            //Delete Secondary email addrr
            Set<UserSecondaryEmailAddr> secondaryEmailAddrSet = user.getUserSecondaryEmailAddrsForUserId();
            for (UserSecondaryEmailAddr secondaryEmailAddr : secondaryEmailAddrSet) {
                session.delete(secondaryEmailAddr);
            }

            //Delete admin profile
            AdminProfile adminProfile = user.getAdminProfileByUserId();
            if (null != adminProfile) {
                session.delete(adminProfile);
            }

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
            LOGGER.error("Exception Occurred during user deletion...", e);
            if (null != e.getCause()) {
                throw new SharedServiceException(CommonConstants.ERROR_CODE_100, "Error :" + e.getCause().getMessage());
            } else {
                throw new SharedServiceException(CommonConstants.ERROR_CODE_100, "Error :" + e);
            }
        } finally {
            if (null != session) {
                //Close the session
                session.close();
            }
        }

    }


    /**
     * Repository method to update user.
     *
     * @param userServiceRequest
     * @param userId             Author Services Unique id
     * @throws SharedServiceException
     */

    public void updateUserRepository(UserServiceRequest userServiceRequest, String userId) throws SharedServiceException {
        //Get the session from sessionFactory pool.
        Session session = null;
        try {
            LOGGER.info("Updating User Profile..");
            if (null == userId) {
                throw new SharedServiceException(CommonConstants.ERROR_CODE_101, userServiceError101);
            }

            Users user = (Users) getEntityById(CommonConstants.USER_ID, userId, Users.class);

            //Throw Error if user is not found.
            if (null == user) {
                throw new SharedServiceException(CommonConstants.ERROR_CODE_102, userServiceError102);
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

            Set<UserSecondaryEmailAddr> secondaryEmailAddrs = user.getUserSecondaryEmailAddrsForUserId();
            if (null != secondaryEmailAddrs && secondaryEmailAddrs.size() > 0) {

                for (UserSecondaryEmailAddr secondaryEmailAddr : secondaryEmailAddrs) {
                    secondaryEmailAddr = UserServiceHelper.setUserSecondaryEmailAddr(userServiceRequest, secondaryEmailAddr, user);
                    if (null != secondaryEmailAddr) {
                        LOGGER.info("Set Secondary Email Addr...");
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
            UserAddresses userAddresses;
            if (null != addressList && addressList.size() > 0) {
                LOGGER.debug("Update addressList...");
                Set<UserAddresses> userAddressesSet = new HashSet<>();
                for (Address addressProfile : addressList) {
                    com.wiley.gr.ace.sharedservices.persistence.entity.Address addressObj;
                    if (null != addressProfile.getId() && addressProfile.getStatus().equalsIgnoreCase(CommonConstants.EDIT)) {
                        addressObj = (com.wiley.gr.ace.sharedservices.persistence.entity.Address) getEntityById(CommonConstants.ADDRESS_ID, addressProfile.getId(), com.wiley.gr.ace.sharedservices.persistence.entity.Address.class);
                        if (null != addressObj) {
                            addressObj = UserServiceHelper.setAddress(addressObj, addressProfile);
                            session.update(addressObj);
                        }
                    } else if (null != addressProfile.getId() && addressProfile.getStatus().equalsIgnoreCase(CommonConstants.DELETE)) {
                        addressObj = (com.wiley.gr.ace.sharedservices.persistence.entity.Address) getEntityById(CommonConstants.ADDRESS_ID, addressProfile.getId(), com.wiley.gr.ace.sharedservices.persistence.entity.Address.class);
                        if (null != addressObj) {
                            addressObj = UserServiceHelper.setAddress(addressObj, addressProfile);
                            session.delete(addressObj);
                        }
                    } else if (addressProfile.getStatus().equalsIgnoreCase(CommonConstants.ADD)) {
                        userAddresses = new UserAddresses();
                        com.wiley.gr.ace.sharedservices.persistence.entity.Address address = new com.wiley.gr.ace.sharedservices.persistence.entity.Address();
                        address = UserServiceHelper.setAddress(address, addressProfile);
                        address.setCreatedDate(UserServiceHelper.getDate());
                        address.setUpdatedDate(UserServiceHelper.getDate());
                        address.setUsersByUpdatedBy(user);
                        session.save(address);
                        AddressType addressType = (AddressType) getEntity(CommonConstants.ADDRESS_TYPE_CD, addressProfile.getType(), AddressType.class, false);
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
                    UserAffiliations userAffiliations;
                    if (null != affiliation.getId() && affiliation.getStatus().equalsIgnoreCase(CommonConstants.EDIT)) {
                        userAffiliations = (UserAffiliations) getEntityById(CommonConstants.AFFILIATION_ID, affiliation.getId(), UserAffiliations.class);
                        if (null != userAffiliations) {
                            userAffiliations = UserServiceHelper.setUserAffiliations(userAffiliations, affiliation);
                            session.update(userAffiliations);
                        }
                    } else if (null != affiliation.getId() && affiliation.getStatus().equalsIgnoreCase(CommonConstants.DELETE)) {
                        userAffiliations = (UserAffiliations) getEntityById(CommonConstants.AFFILIATION_ID, affiliation.getId(), UserAffiliations.class);
                        if (null != userAffiliations) {
                            userAffiliations = UserServiceHelper.setUserAffiliations(userAffiliations, affiliation);
                            session.delete(userAffiliations);
                        }
                    } else if (affiliation.getStatus().equalsIgnoreCase(CommonConstants.ADD)) {
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
                    UserSocietyDetails userSocietyDetails;
                    if (null != society.getId() && society.getStatus().equalsIgnoreCase(CommonConstants.EDIT)) {
                        userSocietyDetails = (UserSocietyDetails) getEntityById(CommonConstants.USER_SOCIETY_ID, society.getId(), UserSocietyDetails.class);
                        if (null != userSocietyDetails) {
                            userSocietyDetails = UserServiceHelper.setUserSocietyDetails(userSocietyDetails, society);
                            session.update(userSocietyDetails);
                        }
                    } else if (null != society.getId() && society.getStatus().equalsIgnoreCase(CommonConstants.DELETE)) {
                        userSocietyDetails = (UserSocietyDetails) getEntityById(CommonConstants.USER_SOCIETY_ID, society.getId(), UserSocietyDetails.class);
                        if (null != userSocietyDetails) {
                            userSocietyDetails = UserServiceHelper.setUserSocietyDetails(userSocietyDetails, society);
                            session.delete(userSocietyDetails);
                        }
                    } else if (null != society.getStatus() && society.getStatus().equalsIgnoreCase(CommonConstants.ADD)) {
                        //Get Societies
                        Societies societies = (Societies) getEntity(CommonConstants.SOCIETY_CD, society.getSocietyCd(), Societies.class, false);
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
                    if (null != coAuthor.getId() && coAuthor.getStatus().equalsIgnoreCase(CommonConstants.EDIT)) {
                        AuthCoauthDetails authCoauthDetails = (AuthCoauthDetails) getEntityById(CommonConstants.AUTH_COAUTH_ID, coAuthor.getId(), AuthCoauthDetails.class);
                        if (null != authCoauthDetails) {
                            authCoauthDetails = UserServiceHelper.setAuthCoauthDetails(authCoauthDetails, coAuthor);
                            authCoauthDetails.setUsersByUpdatedBy(user);
                            session.update(authCoauthDetails);
                        }
                    } else if (null != coAuthor.getId() && coAuthor.getStatus().equalsIgnoreCase(CommonConstants.DELETE)) {
                        AuthCoauthDetails authCoauthDetails = (AuthCoauthDetails) getEntityById(CommonConstants.AUTH_COAUTH_ID, coAuthor.getId(), AuthCoauthDetails.class);
                        if (null != authCoauthDetails) {
                            authCoauthDetails = UserServiceHelper.setAuthCoauthDetails(authCoauthDetails, coAuthor);
                            session.delete(authCoauthDetails);
                        }
                    } else if (null != coAuthor.getStatus() && coAuthor.getStatus().equalsIgnoreCase(CommonConstants.ADD)) {
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
            UserAlerts alerts;
            if (null != alertList && alertList.size() > 0) {
                LOGGER.info("Set Alerts...");
                for (Alert alert : alertList) {
                    List<AlertType> alertTypeList = alert.getAlertTypes();
                    if (null != alert.getId() && alert.getStatus().equalsIgnoreCase(CommonConstants.EDIT)) {
                        if (null != alertTypeList && alertTypeList.size() > 0) {
                            for (AlertType alertType : alertTypeList) {
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

                    } else if (null != alert.getId() && alert.getStatus().equalsIgnoreCase(CommonConstants.DELETE)) {
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
                    } else if (null != alert.getStatus() && alert.getStatus().equalsIgnoreCase(CommonConstants.ADD)) {
                        //Get Alerts Object.
                        Alerts alertsObj = (Alerts) getEntity(CommonConstants.ALERT_CD, alert.getAlertCd(), Alerts.class, false);
                        if (null != alertTypeList && alertTypeList.size() > 0) {
                            for (AlertType alertType : alertTypeList) {
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
                            }
                        }
                    }
                }
            }

            //Update Preferred Journals
            List<PreferredJournal> preferredJournalList = userServiceRequest.getUserProfile().getJournals();
            UserPreferredJournals userPreferredJournals;
            if (null != preferredJournalList && preferredJournalList.size() > 0) {
                LOGGER.debug("Update preferredJournalList...");
                Set<UserPreferredJournals> userPreferredJournalsSet = new HashSet<>();
                for (PreferredJournal preferredJournal : preferredJournalList) {
                    if (null != preferredJournal.getId() && preferredJournal.getStatus().equalsIgnoreCase(CommonConstants.EDIT)) {
                        UserPreferredJournalsId userPreferredJournalsId = new UserPreferredJournalsId();
                        userPreferredJournalsId.setUserId(user.getUserId());
                        userPreferredJournalsId.setJournalId(Integer.parseInt(preferredJournal.getId()));
                        UserPreferredJournals userPreferredJournal = (UserPreferredJournals) session.get(UserPreferredJournals.class, userPreferredJournalsId);
                        if (null != userPreferredJournal) {
                            session.update(userPreferredJournal);
                        }
                    } else if (null != preferredJournal.getId() && preferredJournal.getStatus().equalsIgnoreCase(CommonConstants.DELETE)) {
                        UserPreferredJournalsId userPreferredJournalsId = new UserPreferredJournalsId();
                        userPreferredJournalsId.setUserId(user.getUserId());
                        userPreferredJournalsId.setJournalId(Integer.parseInt(preferredJournal.getId()));
                        UserPreferredJournals userPreferredJournal = (UserPreferredJournals) session.get(UserPreferredJournals.class, userPreferredJournalsId);
                        if (null != userPreferredJournal) {
                            session.delete(userPreferredJournal);
                        }
                    } else if (null != preferredJournal.getStatus() && preferredJournal.getStatus().equalsIgnoreCase(CommonConstants.ADD)) {
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
                    if (null != myInterest.getId() && myInterest.getStatus().equalsIgnoreCase(CommonConstants.EDIT)) {
                        UserAreaOfInterestId userAreaOfInterestId = new UserAreaOfInterestId();
                        userAreaOfInterestId.setUserId(user.getUserId());
                        userAreaOfInterestId.setAreaOfInterestCd(myInterest.getId());
                        UserAreaOfInterestId userArea = (UserAreaOfInterestId) session.get(UserAreaOfInterestId.class, userAreaOfInterestId);
                        AreaOfInterest areaOfInterest = (AreaOfInterest) getEntity(CommonConstants.AREA_OF_INTEREST_CD, myInterest.getAreaofInterestCd(), AreaOfInterest.class, false);
                        if (null != areaOfInterest) {
                            userAreaOfInterest = UserServiceHelper.setUserAreaOfInterest(userAreaOfInterest, areaOfInterest);
                            session.update(userArea);
                        }

                    } else if (null != myInterest.getId() && myInterest.getStatus().equalsIgnoreCase(CommonConstants.DELETE)) {
                        UserAreaOfInterestId userAreaOfInterestId = new UserAreaOfInterestId();
                        userAreaOfInterestId.setUserId(user.getUserId());
                        userAreaOfInterestId.setAreaOfInterestCd(myInterest.getId());
                        UserAreaOfInterestId userArea = (UserAreaOfInterestId) session.get(UserAreaOfInterestId.class, userAreaOfInterestId);
                        session.update(userArea);
                    } else if (null != myInterest.getStatus() && myInterest.getStatus().equalsIgnoreCase(CommonConstants.ADD)) {
                        userAreaOfInterest = new UserAreaOfInterest();
                        AreaOfInterest areaOfInterest = (AreaOfInterest) getEntity(CommonConstants.AREA_OF_INTEREST_CD, myInterest.getAreaofInterestCd(), AreaOfInterest.class, false);
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
                    if (null != funder.getId() && funder.getStatus().equalsIgnoreCase(CommonConstants.EDIT)) {
                        List<GrantNumber> grantList = funder.getGrantNumbers();
                        ResearchFunders researchFunders = (ResearchFunders) getEntity(CommonConstants.FUNDER_NAME, funder.getResearchFunderName(), ResearchFunders.class, false);
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
                            UserFunderGrants userFunderGrants = (UserFunderGrants) getEntity(CommonConstants.FUNDER_NAME, grantNumber.getGrantNumber(), UserFunderGrants.class, false);
                            userFunderGrants.setCreatedDate(UserServiceHelper.getDate());
                            userFunderGrants.setUsersByCreatedBy(user);
                            userFunderGrants.setUsersByUpdatedBy(user);
                            userFunderGrants.setUserFunders(userFunders);
                            session.save(userFunderGrants);
                        }

                    } else if (null != funder.getId() && funder.getStatus().equalsIgnoreCase(CommonConstants.DELETE)) {
                        //TODO: Funder delete
                    } else if (null != funder.getStatus() && funder.getStatus().equalsIgnoreCase(CommonConstants.ADD)) {

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
                                //researchFunders.getUserFunderGrantses().add(userFunderGrants);
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
                throw new SharedServiceException(CommonConstants.ERROR_CODE_100, "Error :" + e.getCause().getMessage());
            } else {
                throw new SharedServiceException(CommonConstants.ERROR_CODE_100, "Error :" + e);
            }
        } finally {
            if (null != session) {
                //Close the session
                session.close();
            }
        }

    }


    /**
     * Repository method to do a lookup on user object.
     *
     * @param firstName
     * @param lastName
     * @param emailId
     * @return
     * @throws SharedServiceException
     */
    public LookupResponse userlookUpRepository(String firstName, String lastName, String emailId)
            throws SharedServiceException {
        LOGGER.info("Lookup User Profile..");
        LookupResponse response = new LookupResponse();
        Session session = null;
        List<Object> user = null;
        UserSecondaryEmailAddr userSecondaryEmailAddr;
        try {
            LOGGER.info("Getting Entity...");
            session = sessionFactory.openSession();
            // Begin the transaction.
            session.beginTransaction();
            // Get the user object.
            Criteria userCriteria = session.createCriteria(Users.class);
            // Set Projections
            userCriteria.setProjection(Projections.projectionList()
                    .add(Projections.property(CommonConstants.USER_ID))
                    .add(Projections.property(CommonConstants.PRIMARY_EMAIL_ID)));

            userCriteria.add(Restrictions.eq(CommonConstants.PRIMARY_EMAIL_ID,
                    emailId));
            //Apply firstName and lastName to criteria
            if (!StringUtils.isEmpty(firstName)
                    && !StringUtils.isEmpty(lastName)) {
                userCriteria.add(Restrictions.eq(CommonConstants.FIRST_NAME,
                        firstName));
                userCriteria.add(Restrictions.eq(CommonConstants.LAST_NAME,
                        lastName));
            }

            //If record found in user table
            if (userCriteria.list().size() > 0) {
                user = userCriteria.list();
                Object[] items = (Object[]) user.get(0);
                int id = (Integer) items[0];
                String pEmail = (String) items[1];
                response.setUserId("" + id);
                response.setPrimaryEmailAddress(pEmail);
                if (!StringUtils.isEmpty(pEmail)
                        && StringUtils.equalsIgnoreCase(emailId, pEmail)) {
                    response.setEmailType(CommonConstants.EMAIL_TYPE_PRIMARY);
                }
            } //Else Search with User Secondary Email Addrr Table.
            else {
                userCriteria = session
                        .createCriteria(UserSecondaryEmailAddr.class);
                userCriteria.add(Restrictions.eq(CommonConstants.SECONDARY_EMAIL_ID,
                        emailId));
                if (null != userCriteria.uniqueResult()) {
                    userSecondaryEmailAddr = (UserSecondaryEmailAddr) userCriteria
                            .uniqueResult();
                    response.setUserId(""
                            + userSecondaryEmailAddr.getUsersByUserId()
                            .getUserId());
                    response.setPrimaryEmailAddress(userSecondaryEmailAddr
                            .getUsersByUserId().getPrimaryEmailAddr());
                    response.setEmailType(CommonConstants.EMAIL_TYPE_SECONDARY);
                }

            }

            // Flush the session
            session.flush();
            // Clear session
            session.clear();
            // Commit the transaction.
            session.getTransaction().commit();
        } catch (Exception e) {
            LOGGER.error("Exception Occurred during Lookup user...", e);
            if (null != e.getCause()) {
                throw new SharedServiceException(CommonConstants.ERROR_CODE_100, "Error :" + e.getCause().getMessage());
            } else {
                throw new SharedServiceException(CommonConstants.ERROR_CODE_100, "Error :" + e);
            }
        } finally {
            // Close the session
            if (null != session) {
                session.close();
            }
        }
        return response;

    }

    /**
     * Repository method to search user.
     *
     * @param email
     * @param firstName
     * @param lastName
     * @param orcidId
     * @return
     * @throws SharedServiceException
     */
    public Service searchUserRepository(String email, String firstName, String lastName, String orcidId) throws SharedServiceException {
        LOGGER.info("Search User Profile..");
        Service service = new Service();
        UserSearchResponse response = new UserSearchResponse();
        Session session = null;
        try {

            session = sessionFactory.openSession();
            // Begin the transaction.
            session.beginTransaction();


            // Check whether primary email exists
            Criteria userCriteria = session.createCriteria(Users.class);
            // Set Projections
            userCriteria.setProjection(Projections.projectionList()
                    .add(Projections.property(CommonConstants.USER_ID))
                    .add(Projections.property(CommonConstants.PRIMARY_EMAIL_ID)));

            userCriteria.add(Restrictions.eq(CommonConstants.PRIMARY_EMAIL_ID,
                    email));

            //If Primary email found set error
            if (userCriteria.list().size() > 0) {
                service.setError(new Error(3001, userSearchServiceError301));
            }
            //If Secondary email found set error
            if (null == service.getError()) {
                userCriteria = session
                        .createCriteria(UserSecondaryEmailAddr.class);
                // Set Projections
                userCriteria.setProjection(Projections.projectionList()
                        .add(Projections.property(CommonConstants.USER_SECONDARY_EMAIL_ID)));
                userCriteria.add(Restrictions.eq(CommonConstants.SECONDARY_EMAIL_ID,
                        email));
                if (userCriteria.list().size() > 0) {
                    service.setError(new Error(3002, userSearchServiceError302));
                }
            }
            //If Orcid id found set error
            if (null == service.getError()) {
                userCriteria = session
                        .createCriteria(UserReferenceData.class);
                userCriteria.setProjection(Projections.projectionList()
                        .add(Projections.property(CommonConstants.ORCID_ID)));
                userCriteria.add(Restrictions.eq(CommonConstants.ORCID_ID,
                        orcidId));
                if (userCriteria.list().size() > 0) {
                    service.setError(new Error(3003, userSearchServiceError303));
                }
            }

            //If there is no error. Search with FN & LN.
            if (null == service.getError()) {
                // Get the user object.
                userCriteria = session.createCriteria(Users.class);
                userCriteria.add(Restrictions.eq(CommonConstants.FIRST_NAME,
                        firstName));
                userCriteria.add(Restrictions.eq(CommonConstants.LAST_NAME,
                        lastName));
                UserSearchResponse.UserSearchResults searchResults = response.new UserSearchResults();
                List<UserSearchResponse.UserSearchResults> searchResultsList = new LinkedList<>();
                if (userCriteria.list().size() > 0) {
                    List<Users> userlist = userCriteria.list();
                    for (Users user : userlist) {
                        searchResults.setFirstName(user.getFirstName());
                        searchResults.setLastName(user.getLastName());
                        searchResults.setSuffix(user.getUserProfileByUserId().getSuffixCd());
                        searchResults.setTitle(user.getUserProfileByUserId().getTitleCd());
                        searchResults.setMiddleName(user.getUserProfileByUserId().getMiddleName());
                        searchResults.setInstitution(user.getUserProfileByUserId().getIndustryCd());
                        searchResults.setOrcidId(user.getUserReferenceDataByUserId().getOrcidId());
                        searchResultsList.add(searchResults);
                    }
                }//End of if
                response.setSearchResults(searchResultsList);
                service.setPayload(response);
            }

            // Flush the session
            session.flush();
            // Clear session
            session.clear();
            // Commit the transaction.
            session.getTransaction().commit();
        } catch (Exception e) {
            LOGGER.error("Exception Occurred during search user...", e);
            if (null != e.getCause()) {
                throw new SharedServiceException(CommonConstants.ERROR_CODE_100, "Error :" + e.getCause().getMessage());
            } else {
                throw new SharedServiceException(CommonConstants.ERROR_CODE_100, "Error :" + e);
            }
        } finally {
            // Close the session
            if (null != session) {
                session.close();
            }
        }
        return service;
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
    private void validateRequest(UserServiceRequest userServiceRequest) throws SharedServiceException {
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
    private void addProfileVisible(Session session, Users user, List<ProfileVisible> profileVisibleList, UserProfile authorProfile, Set<UserProfileAttribVisible> userProfileAttribVisibleSet) throws SharedServiceException {
        LOGGER.debug("Set Profile Visible Id...");
        for (ProfileVisible profileVisible : profileVisibleList) {
            //Get the Profile Attribute List
            ProfileAttributeList profileAttributeList = (ProfileAttributeList) getEntity(CommonConstants.PROFILE_ATTR_CD, profileVisible.getTitleCd(), ProfileAttributeList.class, false);
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
    private void addUserReferenceData(Session session, Users user, UserServiceRequest userServiceRequest) throws SharedServiceException {
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
    private void addAddress(Session session, Users user, Set<UserAddresses> userAddressesSet, List<Address> addressList) throws SharedServiceException {
        for (Address addressProfile : addressList) {
            UserAddresses userAddresses = new UserAddresses();
            com.wiley.gr.ace.sharedservices.persistence.entity.Address address = new com.wiley.gr.ace.sharedservices.persistence.entity.Address();
            address = UserServiceHelper.setAddress(address, addressProfile);
            address.setCreatedDate(UserServiceHelper.getDate());
            address.setUpdatedDate(UserServiceHelper.getDate());
            address.setUsersByCreatedBy(user);
            address.setUsersByUpdatedBy(user);
            session.save(address);
            AddressType addressType = (AddressType) getEntity(CommonConstants.ADDRESS_TYPE_CD, addressProfile.getType(), AddressType.class, false);
            if (null != addressType) {
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
    private void addAffiliation(Session session, Users user, UserProfile userProfile, List<Affiliation> affiliationList) {
        if (null != affiliationList && affiliationList.size() > 0) {
            LOGGER.debug("Set Affiliation...");
            Set<UserAffiliations> userAffiliationsSet = new HashSet<>();
            for (Affiliation affiliation : affiliationList) {
                UserAffiliations affiliations = new UserAffiliations();
                affiliations = UserServiceHelper.setUserAffiliations(affiliations, affiliation);
                affiliations.setCreatedDate(UserServiceHelper.getDate());
                affiliations.setUsersByCreatedBy(user);
                affiliations.setUsersByUpdatedBy(user);
                affiliations.setUserProfile(userProfile);
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
    private void addFunder(Session session, Users user, UserProfile userProfile, List<Funder> funders, Set<UserFunders> grants) {
        for (Funder funder : funders) {
            List<GrantNumber> grantList = funder.getGrantNumbers();
            ResearchFunders researchFunders = new ResearchFunders();
            researchFunders = UserServiceHelper.setResearchFunders(researchFunders, funder);
            researchFunders.setCreatedDate(UserServiceHelper.getDate());
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
                userFunderGrants.setUsersByCreatedBy(user);
                userFunderGrants.setUsersByUpdatedBy(user);
                userFunderGrants.setUserFunders(userFunders);
                session.save(userFunderGrants);
                //researchFunders.getUserFunderGrantses().add(userFunderGrants);

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
    private void addSociety(Session session, Users user, UserProfile userProfile, List<Society> societyList, Set<UserSocietyDetails> societyDetailsSet) throws SharedServiceException {
        for (Society society : societyList) {
            //Get Societies
            Societies societies = (Societies) getEntity(CommonConstants.SOCIETY_CD, society.getSocietyCd(), Societies.class, false);
            if (null != societies) {
                UserSocietyDetails userSocietyDetails = new UserSocietyDetails();
                userSocietyDetails = UserServiceHelper.setUserSocietyDetails(userSocietyDetails, society);
                userSocietyDetails.setSocieties(societies);
                userSocietyDetails.setCreatedDate(UserServiceHelper.getDate());
                userSocietyDetails.setUsersByCreatedBy(user);
                userSocietyDetails.setUsersByUpdatedBy(user);
                userSocietyDetails.setUserProfile(userProfile);
                session.save(userSocietyDetails);
                societyDetailsSet.add(userSocietyDetails);
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
    private void addInterest(Session session, Users user, UserProfile userProfile, List<MyInterest> myInterestList, Set<UserAreaOfInterest> userAreaOfInterestHashSet) throws SharedServiceException {
        for (MyInterest myInterest : myInterestList) {
            UserAreaOfInterest userAreaOfInterest = new UserAreaOfInterest();
            AreaOfInterest areaOfInterest = (AreaOfInterest) getEntity("areaOfInterestCd", myInterest.getAreaofInterestCd(), AreaOfInterest.class, false);
            if (null != areaOfInterest) {
                UserAreaOfInterestId userAreaOfInterestId = new UserAreaOfInterestId();
                userAreaOfInterestId.setUserId(user.getUserId());
                userAreaOfInterestId.setAreaOfInterestCd(areaOfInterest.getAreaOfInterestCd());
                userAreaOfInterest = UserServiceHelper.setUserAreaOfInterest(userAreaOfInterest, areaOfInterest);
                userAreaOfInterest.setCreatedDate(UserServiceHelper.getDate());
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
    private void addAuthor(Session session, Users user, UserProfile userProfile, List<CoAuthor> coAuthorList, Set<AuthCoauthDetails> authCoauthDetailsSet) {
        for (CoAuthor coAuthor : coAuthorList) {
            AuthCoauthDetails authCoauthDetails = new AuthCoauthDetails();
            authCoauthDetails = UserServiceHelper.setAuthCoauthDetails(authCoauthDetails, coAuthor);
            authCoauthDetails.setCreatedDate(UserServiceHelper.getDate());
            authCoauthDetails.setUserProfileByAuthorUserId(userProfile);
            authCoauthDetails.setAuthCoauthId(user.getUserId());
            //TODO: Set CoAuthor ID
            authCoauthDetails.setUsersByCreatedBy(user);
            authCoauthDetails.setUsersByUpdatedBy(user);
            session.save(authCoauthDetails);
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
    private void addJournal(Session session, Users user, UserProfile userProfile, List<PreferredJournal> preferredJournalList, Set<UserPreferredJournals> userPreferredJournalsSet) throws SharedServiceException {
        for (PreferredJournal preferredJournal : preferredJournalList) {
            UserPreferredJournalsId userPreferredJournalsId = new UserPreferredJournalsId();
            UserPreferredJournals userPreferredJournals = new UserPreferredJournals();
            Journals journals = (Journals) getEntityById(CommonConstants.JOURNAL_ID, preferredJournal.getJournalId(), Journals.class);
            if (null != journals) {
                userPreferredJournals = UserServiceHelper.setUserPreferredJournals(userPreferredJournals, journals);
                userPreferredJournalsId.setUserId(user.getUserId());
                userPreferredJournalsId.setJournalId(journals.getJournalId());
                userPreferredJournals.setId(userPreferredJournalsId);
                userPreferredJournals.setUserProfile(userProfile);
                userPreferredJournals.setUsersByCreatedBy(user);
                userPreferredJournals.setUsersByUpdatedBy(user);
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
     * @param userProfile
     * @param alertList
     * @param userAlertsHashSet
     * @throws SharedServiceException
     */
    private void addAlert(Session session, Users user, UserProfile userProfile, List<Alert> alertList, Set<UserAlerts> userAlertsHashSet) throws SharedServiceException {
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
                    UserAlerts alerts = new UserAlerts();
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

}
