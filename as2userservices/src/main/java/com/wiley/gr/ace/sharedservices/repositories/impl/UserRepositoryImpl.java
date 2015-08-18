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
import com.wiley.gr.ace.sharedservices.helper.UserRepositoryHelper;
import com.wiley.gr.ace.sharedservices.helper.UserServiceHelper;
import com.wiley.gr.ace.sharedservices.payload.*;
import com.wiley.gr.ace.sharedservices.persistence.entity.*;
import com.wiley.gr.ace.sharedservices.persistence.entity.UserProfile;
import com.wiley.gr.ace.sharedservices.profile.Address;
import com.wiley.gr.ace.sharedservices.profile.*;
import com.wiley.gr.ace.sharedservices.repositories.UserRepository;
import oracle.jdbc.OracleTypes;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
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
    UserRepositoryHelper userRepositoryHelper;

    @Autowired
    private SessionFactory sessionFactory;

    /**
     * This field is use to hold the value of jdbcTemplate.
     */
    @Autowired
    private JdbcTemplate jdbcTemplate;

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
            userRepositoryHelper.validateRequest(userServiceRequest);

            //TODO: Log4j Logs

            //Create user.
            user = new Users();
            //Open the session.
            session = sessionFactory.openSession();
            //Begin the transaction.
            session.beginTransaction();

            LOGGER.info("Set User Profile...");
            user = UserServiceHelper.setUserInformation(userServiceRequest, user);
            user.setCreatedDate(UserServiceHelper.getDate());
            session.save(user);
            user.setUsersByCreatedBy(user);
            user.setUsersByUpdatedBy(user);


            //Create AuthorProfile Object
            UserProfile userProfile = new UserProfile();
            LOGGER.info("Set Author Profile...");
            userProfile = UserServiceHelper.setUserProfileInformation(userServiceRequest, userProfile);
            userProfile.setCreatedDate(UserServiceHelper.getDate());
            userProfile.setUsersByCreatedBy(user);
            userProfile.setUsersByUpdatedBy(user);
            userProfile.setUsersByUserId(user);
            //Make account Active.
            userProfile.setIsAccountActive('1');
            session.save(userProfile);

            //Set Orcid id information
            if (!StringUtils.isEmpty(userServiceRequest.getUserProfile().getOrcidId())) {
                LOGGER.debug("Set User Reference Data...");
                userRepositoryHelper.addUserReferenceData(session, user, userServiceRequest);
            }

            //Set Profile Information
            List<ProfileVisible> profileVisibleList = userServiceRequest.getUserProfile().getProfileVisible();
            if (!CollectionUtils.isEmpty(profileVisibleList)) {
                Set<UserProfileAttribVisible> userProfileAttribVisibleSet = new HashSet<>();
                //Add Profile Visible.
                userRepositoryHelper.addProfileVisible(session, user, profileVisibleList, userProfile, userProfileAttribVisibleSet);
                if (!CollectionUtils.isEmpty(userProfileAttribVisibleSet)) {
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
            if (!CollectionUtils.isEmpty(addressList)) {
                Set<UserAddresses> userAddressesSet = new HashSet<>();
                LOGGER.debug("Set User  Addr...");
                userRepositoryHelper.addAddress(session, user, userAddressesSet, addressList);
                if (!CollectionUtils.isEmpty(userAddressesSet)) {
                    user.setUserAddressesesForUserId(userAddressesSet);
                }
            }

            //Setting Affiliations
            List<Affiliation> affiliationList = userServiceRequest.getUserProfile().getAffiliations();
            if (!CollectionUtils.isEmpty(affiliationList)) {
                LOGGER.debug("Set Affiliation...");
                userRepositoryHelper.setAffiliation(session, user, userProfile, affiliationList);
            }

            //Set funder details
            List<Funder> funders = userServiceRequest.getUserProfile().getFunders();
            Set<UserFunders> grants = new HashSet<>();
            if (!CollectionUtils.isEmpty(funders)) {
                LOGGER.debug("Set Funder...");
                userRepositoryHelper.addFunders(session, user, userProfile, funders, grants);
                if (!CollectionUtils.isEmpty(grants)) {
                    userProfile.setUserFunderses(grants);
                }

            }

            //Set Society Details
            List<Society> societyList = userServiceRequest.getUserProfile().getSocieties();
            if (!CollectionUtils.isEmpty(societyList)) {
                LOGGER.debug("Set Society...");
                Set<UserSocietyDetails> societyDetailsSet = new HashSet<>();
                userRepositoryHelper.setSociety(session, user, userProfile, societyList, societyDetailsSet);
                if (!CollectionUtils.isEmpty(societyDetailsSet)) {
                    userProfile.setUserSocietyDetailses(societyDetailsSet);
                }
            }

            //Set Area of interest
            List<MyInterest> myInterestList = userServiceRequest.getUserProfile().getMyInterests();

            if (!CollectionUtils.isEmpty(myInterestList)) {
                Set<UserAreaOfInterest> userAreaOfInterestHashSet = new HashSet<>();
                LOGGER.debug("Set MyInterest...");
                userRepositoryHelper.addInterest(session, user, userProfile, myInterestList, userAreaOfInterestHashSet);
                if (!CollectionUtils.isEmpty(userAreaOfInterestHashSet)) {
                    userProfile.setUserAreaOfInterests(userAreaOfInterestHashSet);
                }

            }

            //Set CoAuthor Details.
            List<CoAuthor> coAuthorList = userServiceRequest.getUserProfile().getCoAuthors();
            if (!CollectionUtils.isEmpty(coAuthorList)) {
                LOGGER.debug("Set CoAuthor...");
                Set<AuthCoauthDetails> authCoauthDetailsSet = new HashSet<>();
                userRepositoryHelper.setAuthor(session, user, userProfile, coAuthorList, authCoauthDetailsSet);
                userProfile.setAuthCoauthDetailsesForAuthorUserId(authCoauthDetailsSet);
            }


            //Set Preferred Journals
            List<PreferredJournal> preferredJournalList = userServiceRequest.getUserProfile().getJournals();
            if (!CollectionUtils.isEmpty(preferredJournalList)) {
                LOGGER.info("Set PreferredJournal...");
                Set<UserPreferredJournals> userPreferredJournalsSet = new HashSet<>();
                userRepositoryHelper.addJournal(session, user, userProfile, preferredJournalList, userPreferredJournalsSet);
                if (!CollectionUtils.isEmpty(userPreferredJournalsSet)) {
                    userProfile.setUserPreferredJournalses(userPreferredJournalsSet);
                }
            }

            //Set Alerts
            List<Alert> alertList = userServiceRequest.getUserProfile().getAlerts();

            if (!CollectionUtils.isEmpty(alertList)) {
                LOGGER.info("Set Alerts...");
                Set<UserAlerts> userAlertsHashSet = new HashSet<>();
                userRepositoryHelper.setAlert(session, user, alertList, userAlertsHashSet);
                if (!CollectionUtils.isEmpty(userAlertsHashSet)) {
                    userProfile.setUserAlertses(userAlertsHashSet);
                }
            }
            LOGGER.info("Saving the User in DB...");

            //Set Author Profile to user
            userProfile.setUsersByUserId(user);

            LOGGER.info("Saving the User Profile in DB...");
            session.save(userProfile);

            LOGGER.info(CommonConstants.FLUSH);
            //Flush the session.
            session.flush();
            LOGGER.info(CommonConstants.CLEAR);
            //Clear the session object.
            session.clear();
            LOGGER.info(CommonConstants.COMMIT);
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
                throw new SharedServiceException(CommonConstants.ERROR_CODE_100, CommonConstants.ERROR_NOTE + e.getCause().getMessage());
            } else {
                throw new SharedServiceException(CommonConstants.ERROR_CODE_100, CommonConstants.ERROR_NOTE + e);
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
        Users user = null;
        UserServiceRequest userResponse = new UserServiceRequest();
        try {
            LOGGER.info("Get User Profile..");
            if (null == userId) {
                throw new SharedServiceException(CommonConstants.ERROR_CODE_101, userServiceError101);
            }

            if (UserServiceHelper.isNumeric(userId)) {
                //Check with user id first
                user = (Users) userRepositoryHelper.getEntityById(CommonConstants.USER_ID, userId, Users.class);
            }
            if (null == user) {
                //Check with email address
                user = (Users) userRepositoryHelper.getEntity(CommonConstants.PRIMARY_EMAIL_ID, userId, Users.class, false);
            }

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


            if (null != user.getUserProfileByUserId()) {
                //Get Profile Visible list.
                getUserProfileVisibleData(user, userProfile);

                //Get Affiliations
                getUserAffiliationsData(user, userProfile);

                //Get Funder
                getUserFundersData(user, userProfile);

                //Get Society
                getUserSocietyDetailsData(user, userProfile);

                //Get MyInterest
                getUserAreaOfInterestData(user, userProfile);

                //Get PreferredJournal
                getUserPreferredJournalsData(user, userProfile);

                //Get Alerts
                getUserAlertsData(user, userProfile);
            }

            //Get Address and set to the response.
            getUserAddressesData(user, userProfile);

            //Get CoAuthor Details.
            getAuthCoauthDetailsData(user, userProfile);

            userResponse.setUserProfile(userProfile);

        } catch (Exception e) {
            //Rollback the session if any exception occurs.
            if (null != session) {
                session.getTransaction().rollback();
            }
            LOGGER.error("Exception Occurred during user profile creation...", e);
            if (null != e.getCause()) {
                throw new SharedServiceException(CommonConstants.ERROR_CODE_100, CommonConstants.ERROR_NOTE + e.getCause().getMessage());
            } else {
                throw new SharedServiceException(CommonConstants.ERROR_CODE_100, CommonConstants.ERROR_NOTE + e);
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

            Users user = (Users) userRepositoryHelper.getEntityById(CommonConstants.USER_ID, userId, Users.class);

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
                deleteUserFundersData(session, authorProfile);

                //Delete Affiliations
                Set<UserAffiliations> userAffiliationsSet = authorProfile.getUserAffiliationses();
                for (UserAffiliations userAffiliations : userAffiliationsSet) {
                    session.delete(userAffiliations);
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

            LOGGER.info(CommonConstants.FLUSH);
            session.flush();
            LOGGER.info(CommonConstants.CLEAR);
            session.clear();
            LOGGER.info(CommonConstants.COMMIT);
            session.getTransaction().commit();

        } catch (Exception e) {
            //Rollback the session if any exception occurs.
            if (null != session) {
                session.getTransaction().rollback();
            }
            LOGGER.error("Exception Occurred during user deletion...", e);
            if (null != e.getCause()) {
                throw new SharedServiceException(CommonConstants.ERROR_CODE_100, CommonConstants.ERROR_NOTE + e.getCause().getMessage());
            } else {
                throw new SharedServiceException(CommonConstants.ERROR_CODE_100, CommonConstants.ERROR_NOTE + e);
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

    public UserServiceRequest updateUserRepository(UserServiceRequest userServiceRequest, String userId) throws SharedServiceException {
        //Get the session from sessionFactory pool.
        Session session = null;
        try {
            LOGGER.info("Updating User Profile..");
            if (null == userId) {
                throw new SharedServiceException(CommonConstants.ERROR_CODE_101, userServiceError101);
            }

            Users user = (Users) userRepositoryHelper.getEntityById(CommonConstants.USER_ID, userId, Users.class);

            //Throw Error if user is not found.
            if (null == user) {
                throw new SharedServiceException(CommonConstants.ERROR_CODE_102, userServiceError102);
            }

            session = sessionFactory.openSession();
            //Begin the transaction.
            session.beginTransaction();

            if ((!StringUtils.isEmpty(userServiceRequest.getUserProfile().getFirstName())) || (!StringUtils.isEmpty(userServiceRequest.getUserProfile().getLastName())) || (!StringUtils.isEmpty(userServiceRequest.getUserProfile().getPrimaryEmailAddress()))) {
                user = UserServiceHelper.setUserInformation(userServiceRequest, user);
            }
            //Get AuthorProfile Object
            UserProfile authorProfile = user.getUserProfileByUserId();


            //Update User Secondary Email Address
            if (!StringUtils.isEmpty(userServiceRequest.getUserProfile().getRecoveryEmailAddress())) {
                Set<UserSecondaryEmailAddr> secondaryEmailAddrs = user.getUserSecondaryEmailAddrsForUserId();
                if (!CollectionUtils.isEmpty(secondaryEmailAddrs)) {
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
            }

            //Update Orcid id & Ecid information
            if ((!StringUtils.isEmpty(userServiceRequest.getUserProfile().getOrcidId())) || (!StringUtils.isEmpty(userServiceRequest.getUserProfile().getEcid()))) {
                UserReferenceData userReferenceData = user.getUserReferenceDataByUserId();
                if (null != userReferenceData) {
                    LOGGER.debug("Update UserReferenceData...");
                    userReferenceData = UserServiceHelper.setUserReference(userReferenceData, userServiceRequest);
                    userReferenceData.setUsersByUserId(user);
                    session.update(userReferenceData);
                }
            }

            //Update user Address Object
            List<Address> addressList = userServiceRequest.getUserProfile().getAddresses();
            UserAddresses userAddresses;
            if (!CollectionUtils.isEmpty(addressList)) {
                LOGGER.debug("Update addressList...");
                Set<UserAddresses> userAddressesSet = new HashSet<>();
                for (Address addressProfile : addressList) {
                    if (null != addressProfile.getId() && null != addressProfile.getStatus()) {

                        String status = addressProfile.getStatus();
                        switch (status.toLowerCase()) {
                            case CommonConstants.EDIT:
                                userRepositoryHelper.editAddressProfile(addressProfile, session, userId);
                                break;
                            case CommonConstants.DELETE:
                                userRepositoryHelper.deleteAddress(session, addressProfile);
                                break;
                            case CommonConstants.ADD:
                                userAddresses = new UserAddresses();
                                com.wiley.gr.ace.sharedservices.persistence.entity.Address address = new com.wiley.gr.ace.sharedservices.persistence.entity.Address();
                                address = UserServiceHelper.setAddress(address, addressProfile);
                                address.setCreatedDate(UserServiceHelper.getDate());
                                address.setUpdatedDate(UserServiceHelper.getDate());
                                address.setUsersByUpdatedBy(user);
                                session.save(address);
                                AddressType addressType = (AddressType) userRepositoryHelper.getEntity(CommonConstants.ADDRESS_TYPE_CD, addressProfile.getType(), AddressType.class, false);
                                if (null == addressType) {
                                    throw new SharedServiceException(CommonConstants.ERROR_CODE_109, addressProfile.getType() + "-" + userServiceError109);
                                }
                                userAddresses.setAddressType(addressType);
                                userAddresses.setAddress(address);
                                userAddresses.setUsersByCreatedBy(user);
                                userAddresses.setUsersByUpdatedBy(user);
                                userAddresses.setUsersByUserId(user);
                                session.save(userAddresses);
                                userAddressesSet.add(userAddresses);
                                break;
                            default:
                                throw new IllegalArgumentException(CommonConstants.INVALID_STATUS + status);
                        }
                    }
                    //Set only if there are new address are getting added.
                    if (!CollectionUtils.isEmpty(userAddressesSet)) {
                        user.setUserAddressesesForUserId(userAddressesSet);
                    }
                }
            }

            //Update Affiliations
            List<Affiliation> affiliationList = userServiceRequest.getUserProfile().getAffiliations();
            if (!CollectionUtils.isEmpty(affiliationList)) {
                LOGGER.debug("Update affiliationList...");
                Set<UserAffiliations> userAffiliationsSet = new HashSet<>();
                for (Affiliation affiliation : affiliationList) {
                    UserAffiliations userAffiliations;
                    if (null != affiliation.getId() && null != affiliation.getStatus()) {
                        String status = affiliation.getStatus();
                        switch (status.toLowerCase()) {
                            case CommonConstants.EDIT:
                                userAffiliations = (UserAffiliations) userRepositoryHelper.getEntityById(CommonConstants.AFFILIATION_ID, affiliation.getId(), UserAffiliations.class);
                                if (null != userAffiliations) {
                                    userAffiliations = UserServiceHelper.setUserAffiliations(userAffiliations, affiliation);
                                    session.update(userAffiliations);
                                }
                                break;
                            case CommonConstants.DELETE:
                                userRepositoryHelper.deleteAffiliation(session, affiliation, userId);
                                break;
                            case CommonConstants.ADD:
                                UserAffiliations userAffiliation = userRepositoryHelper.addAffiliation(session, affiliation, user, authorProfile);
                                userAffiliationsSet.add(userAffiliation);
                                break;
                            default:
                                throw new IllegalArgumentException(CommonConstants.INVALID_STATUS + status);
                        }
                    }
                }
                if (!CollectionUtils.isEmpty(userAffiliationsSet)) {
                    //Set User Affiliations
                    authorProfile.setUserAffiliationses(userAffiliationsSet);
                }
            }

            //Update Societies
            List<Society> societyList = userServiceRequest.getUserProfile().getSocieties();
            if (!CollectionUtils.isEmpty(societyList)) {
                LOGGER.debug("Update societyList...");
                Set<UserSocietyDetails> societyDetailsSet = new HashSet<>();
                for (Society society : societyList) {
                    UserSocietyDetails userSocietyDetails;
                    if (null != society.getId() && null != society.getStatus()) {
                        String status = society.getStatus();
                        switch (status.toLowerCase()) {
                            case CommonConstants.EDIT:
                                userSocietyDetails = (UserSocietyDetails) userRepositoryHelper.getEntityById(CommonConstants.USER_SOCIETY_ID, society.getId(), UserSocietyDetails.class);
                                if (null != userSocietyDetails) {
                                    userSocietyDetails = UserServiceHelper.setUserSocietyDetails(userSocietyDetails, society);
                                    session.update(userSocietyDetails);
                                }
                                break;
                            case CommonConstants.DELETE:
                                session.clear();
                                session.flush();
                                userRepositoryHelper.deleteSociety(session, society, userId);
                                break;
                            case CommonConstants.ADD:
                                //Get Societies
                                Societies societies = (Societies) userRepositoryHelper.getEntity(CommonConstants.SOCIETY_CD, society.getSocietyCd(), Societies.class, false);
                                if (null == societies) {
                                    throw new SharedServiceException(CommonConstants.ERROR_CODE_110, society.getSocietyCd() + "-" + userServiceError110);
                                } else {
                                    UserSocietyDetails userSocietyDetail = userRepositoryHelper.addUserSociety(session, user, authorProfile, society, societies);
                                    societyDetailsSet.add(userSocietyDetail);
                                }
                                break;
                            default:
                                throw new IllegalArgumentException(CommonConstants.INVALID_STATUS + status);
                        }
                    }
                }
                if (!CollectionUtils.isEmpty(societyDetailsSet)) {
                    authorProfile.setUserSocietyDetailses(societyDetailsSet);
                }
            }

            //Update CoAuthors
            List<CoAuthor> coAuthorList = userServiceRequest.getUserProfile().getCoAuthors();
            if (!CollectionUtils.isEmpty(coAuthorList)) {
                LOGGER.debug("Update CoAuthor...");
                Set<AuthCoauthDetails> authCoauthDetailsSet = new HashSet<>();
                for (CoAuthor coAuthor : coAuthorList) {

                    if (null != coAuthor.getId() && null != coAuthor.getStatus()) {
                        String status = coAuthor.getStatus();
                        switch (status.toLowerCase()) {
                            case CommonConstants.EDIT:
                                AuthCoauthDetails authCoauthDetails = (AuthCoauthDetails) userRepositoryHelper.getEntityById(CommonConstants.AUTH_COAUTH_ID, coAuthor.getId(), AuthCoauthDetails.class);
                                if (null != authCoauthDetails) {
                                    authCoauthDetails = UserServiceHelper.setAuthCoauthDetails(authCoauthDetails, coAuthor);
                                    authCoauthDetails.setUsersByUpdatedBy(user);
                                    session.update(authCoauthDetails);
                                }
                                break;
                            case CommonConstants.DELETE:
                                session.clear();
                                session.flush();
                                userRepositoryHelper.deleteCoAuthor(session, coAuthor, userId);
                                break;
                            case CommonConstants.ADD:
                                AuthCoauthDetails addAuthCoauthDetails = userRepositoryHelper.addAuthCoauthDetails(session, coAuthor, user, authorProfile);
                                authCoauthDetailsSet.add(addAuthCoauthDetails);
                                break;
                            default:
                                throw new IllegalArgumentException(CommonConstants.INVALID_STATUS + status);
                        }
                    }
                }
                if (!CollectionUtils.isEmpty(authCoauthDetailsSet)) {
                    authorProfile.setAuthCoauthDetailsesForAuthorUserId(authCoauthDetailsSet);
                }
            }

            //Update Alerts
            List<Alert> alertList = userServiceRequest.getUserProfile().getAlerts();
            if (!CollectionUtils.isEmpty(alertList)) {
                LOGGER.info("Set Alerts...");
                for (Alert alert : alertList) {
                    List<AlertType> alertTypeList = alert.getAlertTypes();
                    if (null != alert.getId() && null != alert.getStatus()) {
                        String status = alert.getStatus();
                        switch (status.toLowerCase()) {
                            case CommonConstants.EDIT:
                                userRepositoryHelper.updateAlerts(session, alert, alertTypeList, user, userId);
                                break;
                            case CommonConstants.DELETE:
                                userRepositoryHelper.deleteAlerts(session, alert, userId);
                                break;
                            case CommonConstants.ADD:
                                //Get Alerts Object.
                                Alerts alertsObj = (Alerts) userRepositoryHelper.getEntity(CommonConstants.ALERT_CD, alert.getAlertCd(), Alerts.class, false);
                                if (null == alertsObj) {
                                    throw new SharedServiceException(CommonConstants.ERROR_CODE_113, alert.getAlertCd() + "-" + userServiceError113);
                                }
                                if (!CollectionUtils.isEmpty(alertTypeList)) {
                                    for (AlertType alertType : alertTypeList) {
                                        userRepositoryHelper.addAlert(session, user, alertsObj, alert, alertType);
                                    }
                                }
                                break;
                            default:
                                throw new IllegalArgumentException(CommonConstants.INVALID_STATUS + status);
                        }
                    }
                }
            }

            //Update Preferred Journals
            List<PreferredJournal> preferredJournalList = userServiceRequest.getUserProfile().getJournals();
            UserPreferredJournals userPreferredJournals;
            if (!CollectionUtils.isEmpty(preferredJournalList)) {
                LOGGER.debug("Update preferredJournalList...");
                Set<UserPreferredJournals> userPreferredJournalsSet = new HashSet<>();
                for (PreferredJournal preferredJournal : preferredJournalList) {
                    if (null != preferredJournal.getId() && null != preferredJournal.getStatus()) {
                        String status = preferredJournal.getStatus();
                        switch (status.toLowerCase()) {
                            case CommonConstants.EDIT:
                                UserPreferredJournalsId userPreferredJournalsId = new UserPreferredJournalsId();
                                userPreferredJournalsId.setUserId(user.getUserId());
                                userPreferredJournalsId.setJournalId(Integer.parseInt(preferredJournal.getId()));
                                UserPreferredJournals userPreferredJournal = (UserPreferredJournals) session.get(UserPreferredJournals.class, userPreferredJournalsId);
                                if (null != userPreferredJournal) {
                                    session.update(userPreferredJournal);
                                }
                                break;
                            case CommonConstants.DELETE:
                                userRepositoryHelper.deleteJournal(session, preferredJournal, user);
                                break;
                            case CommonConstants.ADD:
                                UserPreferredJournalsId addUserPreferredJournalsId = new UserPreferredJournalsId();
                                userPreferredJournals = new UserPreferredJournals();
                                Journals journals = new Journals();
                                journals.setJouTitle(preferredJournal.getJournalTitle());
                                journals.setCreatedDate(UserServiceHelper.getDate());
                                journals.setUpdatedDate(UserServiceHelper.getDate());
                                session.save(journals);
                                userPreferredJournals = UserServiceHelper.setUserPreferredJournals(userPreferredJournals, journals);
                                addUserPreferredJournalsId.setUserId(user.getUserId());
                                addUserPreferredJournalsId.setJournalId(journals.getJournalId());
                                userPreferredJournals.setId(addUserPreferredJournalsId);
                                userPreferredJournals.setUserProfile(authorProfile);
                                userPreferredJournals.setUsersByCreatedBy(user);
                                userPreferredJournals.setUsersByUpdatedBy(user);
                                userPreferredJournals.setCreatedDate(UserServiceHelper.getDate());
                                userPreferredJournals.setUpdatedDate(UserServiceHelper.getDate());
                                session.save(userPreferredJournals);
                                userPreferredJournalsSet.add(userPreferredJournals);
                                break;
                            default:
                                throw new IllegalArgumentException(CommonConstants.INVALID_STATUS + status);
                        }
                    }
                }
                if (!CollectionUtils.isEmpty(userPreferredJournalsSet)) {
                    authorProfile.setUserPreferredJournalses(userPreferredJournalsSet);
                }
            }


            //Update Area of interest
            List<MyInterest> myInterestList = userServiceRequest.getUserProfile().getMyInterests();
            if (!CollectionUtils.isEmpty(myInterestList)) {
                Set<UserAreaOfInterest> userAreaOfInterestHashSet = new HashSet<>();
                LOGGER.debug("Set MyInterest...");
                for (MyInterest myInterest : myInterestList) {
                    if (null != myInterest.getId() && null != myInterest.getStatus()) {
                        String status = myInterest.getStatus();
                        switch (status.toLowerCase()) {
                            case CommonConstants.EDIT:
                                //Delete MyInterests
                                Set<UserAreaOfInterest> userAreaOfInterestSet = authorProfile.getUserAreaOfInterests();
                                for (UserAreaOfInterest userAreaOfInterestDelete : userAreaOfInterestSet) {
                                    session.delete(userAreaOfInterestDelete);
                                }
                                session.flush();
                                session.clear();
                                AreaOfInterest areaOfInterest = (AreaOfInterest) userRepositoryHelper.getEntity(CommonConstants.AREA_OF_INTEREST_CD, myInterest.getId(), AreaOfInterest.class, false);
                                if (null == areaOfInterest) {
                                    throw new SharedServiceException(CommonConstants.ERROR_CODE_111, myInterest.getId() + "-" + userServiceError111);
                                } else {
                                    UserAreaOfInterest userAreaOfInt = userRepositoryHelper.addAreaOfInterest(session, user, authorProfile, areaOfInterest);
                                    userAreaOfInterestHashSet.add(userAreaOfInt);
                                }
                                break;
                            case CommonConstants.DELETE:
                                userRepositoryHelper.deleteAreaofInterest(session, myInterest, userId);
                                break;
                            case CommonConstants.ADD:
                                AreaOfInterest addAreaOfInterest = (AreaOfInterest) userRepositoryHelper.getEntity(CommonConstants.AREA_OF_INTEREST_CD, myInterest.getAreaofInterestCd(), AreaOfInterest.class, false);
                                if (null == addAreaOfInterest) {
                                    throw new SharedServiceException(CommonConstants.ERROR_CODE_111, myInterest.getAreaofInterestCd() + "-" + userServiceError111);
                                } else {
                                    UserAreaOfInterest userAreaOfInt = userRepositoryHelper.addAreaOfInterest(session, user, authorProfile, addAreaOfInterest);
                                    userAreaOfInterestHashSet.add(userAreaOfInt);
                                }
                                break;
                            default:
                                throw new IllegalArgumentException(CommonConstants.INVALID_STATUS + status);
                        }
                    }
                }
                if (!CollectionUtils.isEmpty(userAreaOfInterestHashSet)) {
                    authorProfile.setUserAreaOfInterests(userAreaOfInterestHashSet);
                }

            }

            //Update funder details
            List<Funder> funders = userServiceRequest.getUserProfile().getFunders();
            if (!CollectionUtils.isEmpty(funders)) {
                LOGGER.debug("Update Funder...");
                for (Funder funder : funders) {
                    if (null != funder.getId() && null != funder.getStatus()) {
                        String status = funder.getStatus();
                        switch (status.toLowerCase()) {
                            case CommonConstants.EDIT:
                                //Delete Funders
                                userRepositoryHelper.deleteFunders(session, funder);
                                session.flush();
                                session.clear();
                                userRepositoryHelper.addFunder(session, funder, user, authorProfile);
                                break;
                            case CommonConstants.DELETE:
                                //Delete Funders
                                userRepositoryHelper.deleteFunders(session, funder);
                                break;
                            case CommonConstants.ADD:
                                //Delete Funders
                                userRepositoryHelper.addFunder(session, funder, user, authorProfile);
                                break;
                            default:
                                throw new IllegalArgumentException(CommonConstants.INVALID_STATUS + status);
                        }
                    }
                }

            }

            LOGGER.info("Update Author Profile...");
            if (UserServiceHelper.isUserProfileDataExists(userServiceRequest)) {
                session.flush();
                session.clear();
                authorProfile = UserServiceHelper.setUserProfileInformation(userServiceRequest, authorProfile);
                session.saveOrUpdate(authorProfile);
            }


            //Set Author Profile to user
            authorProfile.setUsersByUserId(user);
            LOGGER.debug("Update session...");
            session.saveOrUpdate(user);
            LOGGER.debug(CommonConstants.FLUSH);
            session.flush();
            LOGGER.debug(CommonConstants.CLEAR);
            session.clear();


        } catch (Exception e) {
            //Rollback the session if any exception occurs.
            if (null != session) {
                session.getTransaction().rollback();
            }
            LOGGER.error("Exception Occurred during user profile updating...", e);
            if (null != e.getCause()) {
                throw new SharedServiceException(CommonConstants.ERROR_CODE_100, CommonConstants.ERROR_NOTE + e.getCause().getMessage());
            } else {
                throw new SharedServiceException(CommonConstants.ERROR_CODE_100, CommonConstants.ERROR_NOTE + e);
            }
        } finally {
            if (null != session) {
                //Close the session
                session.close();
            }
        }

        return getUserRepository(userId);

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
            if (userCriteria.list().isEmpty()) {
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
            } else {
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
                throw new SharedServiceException(CommonConstants.ERROR_CODE_100, CommonConstants.ERROR_NOTE + e.getCause().getMessage());
            } else {
                throw new SharedServiceException(CommonConstants.ERROR_CODE_100, CommonConstants.ERROR_NOTE + e);
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
    public Service searchUserRepository(final String email, final String firstName, final String lastName, final String orcidId) throws SharedServiceException {
        LOGGER.info("Search User Profile..");
        Service service = new Service();
        Session session = null;
        UserSearchResponse response = new UserSearchResponse();
        List<UserSearchResults> responseResultList = new LinkedList<>();
        List<UserSearchResults> searchResultsList = new LinkedList<>();
        try {

            session = sessionFactory.openSession();
            // Begin the transaction.
            session.beginTransaction();

            Connection con = jdbcTemplate.getDataSource().getConnection();

            CallableStatement cs = con.prepareCall("{call usersearch(?,?,?,?,?)}");
            cs.registerOutParameter(1, OracleTypes.CURSOR);
            cs.setString(2, firstName);
            cs.setString(3, lastName);
            cs.setString(4, orcidId);
            cs.setString(5, email);

            cs.execute();
            ResultSet cursor = (ResultSet) cs.getObject(1);
            UserSearchResults userSearchResult = null;
            while (cursor.next()) {
                userSearchResult = new UserSearchResults();
                userSearchResult.setUserId(Integer.parseInt(cursor.getString(1)));
                userSearchResult.setFirstName(cursor.getString(2));
                userSearchResult.setLastName(cursor.getString(3));
                userSearchResult.setPrimaryEmailAddr(cursor.getString(4));
                userSearchResult.setSuffix(cursor.getString(5));
                userSearchResult.setTitle(cursor.getString(6));
                userSearchResult.setMiddleName(cursor.getString(7));
                userSearchResult.setOrcidId(cursor.getString(8));
                userSearchResult.setInstitution(cursor.getString(9));
                searchResultsList.add(userSearchResult);
            }

            cursor.close();
            cs.close();


            for (UserSearchResults userSearchResults : searchResultsList) {
                if (userSearchResults.getUserId() > 0) {
                    List<Object[]> userAddresses = null;
                    List<com.wiley.gr.ace.sharedservices.payload.Address> addPayload = new LinkedList<>();
                    // Get the user object.
                    Criteria userCriteria = session.createCriteria(UserAddresses.class, "userAddresses");
                    userCriteria.createAlias("userAddresses.address", "address");
                    userCriteria.createAlias("userAddresses.usersByUserId", "usersAddr");
                    userCriteria.createAlias("userAddresses.addressType", "addrType");
                    userCriteria.add(Restrictions.eq("usersAddr.userId", userSearchResults.getUserId()));
                    // Set Projections
                    userCriteria.setProjection(Projections.projectionList()
                            .add(Projections.property("address.city"), "city")
                            .add(Projections.property("address.state"), "state")
                            .add(Projections.property("address.countryCd"), "countryCd")
                            .add(Projections.property("addrType.name"), "type")
                            .add(Projections.property("addrType.addressTypeCd"), "addressTypeCd"));
                    if (userCriteria.list().isEmpty()) {
                        userAddresses = userCriteria.list();
                        for (Object[] userAdd : userAddresses) {
                            com.wiley.gr.ace.sharedservices.payload.Address addrObj = new com.wiley.gr.ace.sharedservices.payload.Address();
                            Object[] items = userAdd;
                            String city = (String) items[0];
                            addrObj.setCity(city);
                            String state = (String) items[1];
                            addrObj.setState(state);
                            String countryCd = (String) items[2];
                            addrObj.setCountryCd(countryCd);
                            String type = (String) items[3];
                            addrObj.setType(type);
                            String addressTypeCd = (String) items[4];
                            addrObj.setAddressTypeCd(addressTypeCd);
                            addPayload.add(addrObj);
                        }

                    }
                    userSearchResults.setAddresses(addPayload);
                    responseResultList.add(userSearchResults);
                }
            }


            response.setSearchResults(responseResultList);
            service.setPayload(response);


            // Flush the session
            session.flush();
            // Clear session
            session.clear();
            // Commit the transaction.
            session.getTransaction().commit();
        } catch (Exception e) {
            LOGGER.error("Exception Occurred during search user...", e);
            if (null != e.getCause()) {
                throw new SharedServiceException(CommonConstants.ERROR_CODE_100, CommonConstants.ERROR_NOTE + e.getCause().getMessage());
            } else {
                throw new SharedServiceException(CommonConstants.ERROR_CODE_100, CommonConstants.ERROR_NOTE + e);
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
     * Get method for UserProfileAttribVisible data.
     *
     * @param user
     * @param userProfile
     */
    private void getUserProfileVisibleData(Users user, com.wiley.gr.ace.sharedservices.profile.UserProfile userProfile) {
        Set<UserProfileAttribVisible> userProfileAttributeVisibleSet = user.getUserProfileByUserId().getUserProfileAttribVisibles();
        List<ProfileVisible> profileVisibleSet = new LinkedList<>();
        for (UserProfileAttribVisible userProfileAttribVisible : userProfileAttributeVisibleSet) {
            ProfileVisible profileVisible = new ProfileVisible();
            profileVisible.setId(userProfileAttribVisible.getProfileAttributeList().getProfileAttribCd());
            profileVisible.setTitleCd(userProfileAttribVisible.getProfileAttributeList().getProfileAttribCd());
            profileVisible.setTitleValue(userProfileAttribVisible.getProfileVisibilityFlg());
            profileVisibleSet.add(profileVisible);
        }
        userProfile.setProfileVisible(profileVisibleSet);
    }

    /**
     * Get method for UserAffiliations data.
     *
     * @param user
     * @param userProfile
     */
    private void getUserAffiliationsData(Users user, com.wiley.gr.ace.sharedservices.profile.UserProfile userProfile) {
        Set<UserAffiliations> userAffiliationsSet = user.getUserProfileByUserId().getUserAffiliationses();
        if (!CollectionUtils.isEmpty(userAffiliationsSet)) {
            List<Affiliation> affiliationList = new LinkedList<>();
            LOGGER.debug("Get UserAffiliations...");
            for (UserAffiliations userAffiliations : userAffiliationsSet) {
                Affiliation affiliation = UserServiceHelper.getAffiliation(userAffiliations);
                affiliationList.add(affiliation);
            }
            userProfile.setAffiliations(affiliationList);
        }
    }

    /**
     * Get method for UserFunders data.
     *
     * @param user
     * @param userProfile
     */
    private void getUserFundersData(Users user, com.wiley.gr.ace.sharedservices.profile.UserProfile userProfile) {
        Set<UserFunders> userFundersSet = user.getUserProfileByUserId().getUserFunderses();
        if (!CollectionUtils.isEmpty(userFundersSet)) {
            List<Funder> funders = new LinkedList<>();
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

    /**
     * Get method for UserSocietyDetails data.
     *
     * @param user
     * @param userProfile
     */
    private void getUserSocietyDetailsData(Users user, com.wiley.gr.ace.sharedservices.profile.UserProfile userProfile) {
        Set<UserSocietyDetails> userSocietyDetailsSet = user.getUserProfileByUserId().getUserSocietyDetailses();
        if (!CollectionUtils.isEmpty(userSocietyDetailsSet)) {
            List<Society> societies = new LinkedList<>();
            LOGGER.debug("Get UserSocietyDetails...");
            for (UserSocietyDetails userSocietyDetails : userSocietyDetailsSet) {
                Society society = UserServiceHelper.getSociety(userSocietyDetails);
                societies.add(society);
            }
            userProfile.setSocieties(societies);
        }

    }

    /**
     * Get method for UserAreaOfInterest data.
     *
     * @param user
     * @param userProfile
     */
    private void getUserAreaOfInterestData(Users user, com.wiley.gr.ace.sharedservices.profile.UserProfile userProfile) {
        Set<UserAreaOfInterest> userAreaOfInterestSet = user.getUserProfileByUserId().getUserAreaOfInterests();
        if (!CollectionUtils.isEmpty(userAreaOfInterestSet)) {
            List<MyInterest> myInterests = new LinkedList<>();
            LOGGER.debug("Get UserAreaOfInterest...");
            for (UserAreaOfInterest userAreaOfInterest : userAreaOfInterestSet) {
                AreaOfInterest areaOfInterest = userAreaOfInterest.getAreaOfInterest();
                MyInterest myInterest = UserServiceHelper.getMyInterest(areaOfInterest);
                myInterests.add(myInterest);
            }
            userProfile.setMyInterests(myInterests);
        }
    }

    /**
     * Get method for UserPreferredJournals data.
     *
     * @param user
     * @param userProfile
     */
    private void getUserPreferredJournalsData(Users user, com.wiley.gr.ace.sharedservices.profile.UserProfile userProfile) {
        Set<UserPreferredJournals> userPreferredJournalsSet = user.getUserProfileByUserId().getUserPreferredJournalses();
        if (!CollectionUtils.isEmpty(userPreferredJournalsSet)) {
            List<PreferredJournal> journalsList = new LinkedList<>();
            LOGGER.debug("Get UserPreferredJournals...");
            for (UserPreferredJournals userPreferredJournals : userPreferredJournalsSet) {
                Journals journal = userPreferredJournals.getJournals();
                PreferredJournal preferredJournal = UserServiceHelper.getPreferredJournal(journal);
                journalsList.add(preferredJournal);
            }
            userProfile.setJournals(journalsList);
        }
    }

    /**
     * Get method for UserAlerts data.
     *
     * @param user
     * @param userProfile
     */
    private void getUserAlertsData(Users user, com.wiley.gr.ace.sharedservices.profile.UserProfile userProfile) {
        Set<UserAlerts> alertsSet = user.getUserProfileByUserId().getUserAlertses();
        if (!CollectionUtils.isEmpty(alertsSet)) {
            List<Alert> alerts = new LinkedList<>();
            LOGGER.debug("Get UserAlerts...");
            for (UserAlerts userAlerts : alertsSet) {
                List<AlertType> alertTypeList = new LinkedList<>();
                Alerts userAlert = userAlerts.getAlerts();
                Alert alert = UserServiceHelper.getAlert(userAlert);
                AlertType alertType = new AlertType();
                alertType.setEmail(userAlerts.getEmailFlg());
                alertType.setOnScreen(userAlerts.getOnScreenFlg());
                alertTypeList.add(alertType);
                alert.setAlertTypes(alertTypeList);
                alerts.add(alert);
            }
            userProfile.setAlerts(alerts);
        }
    }

    /**
     * Get method for UserAddresses data.
     *
     * @param user
     * @param userProfile
     */
    private void getUserAddressesData(Users user, com.wiley.gr.ace.sharedservices.profile.UserProfile userProfile) {
        Set<UserAddresses> userAddressesesForUserId = user.getUserAddressesesForUserId();
        if (!CollectionUtils.isEmpty(userAddressesesForUserId)) {
            List<Address> addressList = new LinkedList<>();
            LOGGER.debug("Get UserAddresses...");
            for (UserAddresses userAddresses : userAddressesesForUserId) {
                com.wiley.gr.ace.sharedservices.persistence.entity.Address addressEntity = userAddresses.getAddress();
                Address address = UserServiceHelper.getAddressInfo(addressEntity);
                AddressType addressType = userAddresses.getAddressType();
                if (null != addressType) {
                    address.setType(addressType.getAddressTypeCd());
                }
                addressList.add(address);
            }
            userProfile.setAddresses(addressList);
        }
    }

    /**
     * Get method for AuthCoauthDetails data.
     *
     * @param user
     * @param userProfile
     */
    private void getAuthCoauthDetailsData(Users user, com.wiley.gr.ace.sharedservices.profile.UserProfile userProfile) {
        Set<AuthCoauthDetails> authCoauthDetailsSet = user.getAuthCoauthDetailsesForCreatedBy();
        if (!CollectionUtils.isEmpty(authCoauthDetailsSet)) {
            List<CoAuthor> coAuthorList = new LinkedList<>();
            LOGGER.debug("Get CoAuthor...");
            for (AuthCoauthDetails authCoauthDetails : authCoauthDetailsSet) {
                CoAuthor coAuthor = new CoAuthor();
                coAuthor = UserServiceHelper.getAuthCoauthDetails(authCoauthDetails, coAuthor);
                coAuthorList.add(coAuthor);
            }
            userProfile.setCoAuthors(coAuthorList);
        }
    }

    /**
     * Delete UserFunders data.
     *
     * @param session
     * @param authorProfile
     */
    private void deleteUserFundersData(Session session, UserProfile authorProfile) {
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
    }

}
