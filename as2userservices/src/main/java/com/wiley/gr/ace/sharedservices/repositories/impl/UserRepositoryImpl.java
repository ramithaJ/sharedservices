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

import com.wiley.gr.ace.sharedservices.helper.UserServiceHelper;
import com.wiley.gr.ace.sharedservices.input.UserServiceRequest;
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

import java.util.*;

/**
 * @author kkalyan
 */
public class UserRepositoryImpl implements UserRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserRepositoryImpl.class);

    @Autowired
    private SessionFactory sessionFactory;


    /**
     * Method to create user profile in the DB.
     *
     * @param userServiceRequest Input Json Request Info
     */
    public void createUserRepository(UserServiceRequest userServiceRequest) {
        //Get the session from sessionFactory pool.
        Session session = null;
        try {
            LOGGER.info("Creating User Profile..");
            session = sessionFactory.openSession();
            //Begin the transaction.
            session.beginTransaction();

            Users user = new Users();
            LOGGER.info("Set User Profile...");
            user = UserServiceHelper.setUserProfileInformation(userServiceRequest, user);

            //Create AuthorProfile Object
            AuthorProfile authorProfile = new AuthorProfile();
            LOGGER.info("Set Author Profile...");
            authorProfile = UserServiceHelper.setAuthorProfile(userServiceRequest, authorProfile);
            //TODO:Save orcid & recieve emails is missing


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
            com.wiley.gr.ace.sharedservices.persistence.entity.Address address = null;
            if (null != addressList && addressList.size() > 0) {
                Set<UserAddresses> userAddressesSet = new HashSet<>();
                LOGGER.info("Set User  Addr...");
                for (Address addressProfile : addressList) {
                    userAddresses = new UserAddresses();
                    address = new com.wiley.gr.ace.sharedservices.persistence.entity.Address();
                    address = UserServiceHelper.setAddress(address, addressProfile);
                    userAddresses.setAddress(address);
                    userAddresses.setUsersByUserId(user);
                    userAddressesSet.add(userAddresses);
                }

                userAddresses.setUsersByUserId(user);
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

            //TODO:Set Wiley Open Access Account

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
            List<Expertise> expertiseList = userServiceRequest.getUserProfile().getExpertises();
            UserAreaOfInterest userAreaOfInterest = null;
            AreaOfInterest areaOfInterest = null;
            if (null != expertiseList && expertiseList.size() > 0) {
                Set<UserAreaOfInterest> userAreaOfInterestHashSet = new HashSet<>();
                LOGGER.info("Set Expertise...");
                for (Expertise expertise : expertiseList) {
                    userAreaOfInterest = new UserAreaOfInterest();
                    areaOfInterest = new AreaOfInterest();
                    areaOfInterest = UserServiceHelper.setAreaOfInterest(areaOfInterest, expertise);
                    userAreaOfInterest = UserServiceHelper.setUserAreaOfInterest(userAreaOfInterest, areaOfInterest);
                    userAreaOfInterest.setAreaOfInterest(areaOfInterest);
                    userAreaOfInterest.setAuthorProfile(authorProfile);
                    userAreaOfInterestHashSet.add(userAreaOfInterest);
                }
                authorProfile.setUserAreaOfInterests(userAreaOfInterestHashSet);

            }
            //TODO:Coauthor only has delete option.

            //Set Preferred Journals
            List<PreferredJournal> preferredJournalList = userServiceRequest.getUserProfile().getJournals();
            UserPreferredJournals userPreferredJournals = null;
            Journals journals = null;
            if (null != preferredJournalList && preferredJournalList.size() > 0) {

                LOGGER.info("Set PreferredJournal...");
                Set<UserPreferredJournals> userPreferredJournalsSet = new HashSet<>();
                for (PreferredJournal preferredJournal : preferredJournalList) {
                    UserPreferredJournalsId userPreferredJournalsId = new UserPreferredJournalsId();
                    userPreferredJournals = new UserPreferredJournals();
                    journals = new Journals();
                    journals = UserServiceHelper.setJournals(journals, preferredJournal.getJournalId());
                    userPreferredJournals = UserServiceHelper.setUserPreferredJournals(userPreferredJournals, journals);
                    session.persist(user);
                    session.persist(journals);
                    userPreferredJournalsId.setUserId(user.getUserId());
                    userPreferredJournalsId.setJournalId(journals.getJournalId());
                    userPreferredJournals.setId(userPreferredJournalsId);
                    userPreferredJournals.setAuthorProfile(authorProfile);
                    session.persist(userPreferredJournals);
                    userPreferredJournalsSet.add(userPreferredJournals);
                }
                authorProfile.setUserPreferredJournalses(userPreferredJournalsSet);
            }
            //Set Author Profile to user
            authorProfile.setUsersByUserId(user);

            session.save(user);
            session.save(authorProfile);
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
            session.flush();
            session.clear();

        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
            LOGGER.error("Exception Occurred during user profile creation...", e);
        } finally {
            //Commit the transaction.
            session.getTransaction().commit();
            if (null != session) {
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
    public void updateUserRepository(UserServiceRequest userServiceRequest, String userId) {
        //Get the session from sessionFactory pool.
        Session session = null;
        try {
            LOGGER.info("Updating User Profile..");
            if (null == userId) {
                throw new Exception("User not found");
            }

            Users user = (Users) getEntity("userId", userId, Users.class);

            session = sessionFactory.openSession();
            //Begin the transaction.
            session.beginTransaction();

            LOGGER.info("Update User Profile...");
            user = UserServiceHelper.setUserProfileInformation(userServiceRequest, user);
            //Create AuthorProfile Object
            AuthorProfile authorProfile = user.getAuthorProfileByUserId();
            LOGGER.info("Update Author Profile...");
            authorProfile = UserServiceHelper.setAuthorProfile(userServiceRequest, authorProfile);


            //Get User Secondary Email Address
            Set<UserSecondaryEmailAddr> userSecondaryEmailAddrSet = new HashSet<>();
            UserSecondaryEmailAddr secondaryEmailAddr = null;
            secondaryEmailAddr = UserServiceHelper.setUserSecondaryEmailAddr(userServiceRequest, secondaryEmailAddr, user);
            if (null != secondaryEmailAddr) {
                LOGGER.info("Set Secondary Email Addr...");
                userSecondaryEmailAddrSet.add(secondaryEmailAddr);
                //Set the secondary email address to the user object
                secondaryEmailAddr.setUsersByUserId(user);
            }


            //Set Author Profile to user
            authorProfile.setUsersByUserId(user);
            session.update(user);
            session.flush();
            session.clear();


        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("Exception Occurred during user profile creation...", e);
        } finally {
            if (null != session) {
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
    public void deleteUserRepository(UserServiceRequest userServiceRequest, String userId) {

    }

    public UserServiceRequest getUserRepository(String userId) {
        //Get the session from sessionFactory pool.
        Session session = null;
        UserServiceRequest userResponse = new UserServiceRequest();
        try {
            LOGGER.info("Updating User Profile..");
            if (null == userId) {
                throw new Exception("User not found");
            }
            Users user = (Users) getEntity("userId", userId, Users.class);
            UserProfile userProfile = new UserProfile();
            userProfile.setTitleCd(user.getAuthorProfileByUserId().getTitleCd());
            userProfile.setFirstName(user.getFirstName());
            userProfile.setMiddleName(user.getAuthorProfileByUserId().getMiddleName());
            userProfile.setLastName(user.getLastName());
            userProfile.setSuffixCd(user.getAuthorProfileByUserId().getSuffixCd());
            userProfile.setAlternativeName(user.getAuthorProfileByUserId().getAlternativeName());
            userProfile.setIndustryCd(user.getAuthorProfileByUserId().getIndustryCd());
            userProfile.setJobCategoryCd(user.getAuthorProfileByUserId().getJobCategoryCd());
            userProfile.setOrcidId("TODO");
            userProfile.setRecieveEmailsFlag("TODO");
            userProfile.setProfileVisibleFlag(user.getAuthorProfileByUserId().getProfileVisibleFlg());
            userProfile.setPrimaryEmailAddress(user.getPrimaryEmailAddr());
            userProfile.setRecoveryEmailAddress("TODO");

            //Get Address and set to the response.
            List<Address> addressList = new LinkedList<>();
            Set<UserAddresses> userAddressesesForUserId = user.getUserAddressesesForUserId();
            if (null != userAddressesesForUserId && userAddressesesForUserId.size() > 0) {
                for (UserAddresses userAddresses : userAddressesesForUserId) {
                    com.wiley.gr.ace.sharedservices.persistence.entity.Address addressEntity = userAddresses.getAddress();
                    Address address = new Address();
                    address.setId("" + addressEntity.getAddressId());
                    address.setTitleCd(addressEntity.getTitleCd());
                    address.setFirstName(addressEntity.getFirstName());
                    address.setLastName(addressEntity.getLastName());
                    address.setSuffixCd(addressEntity.getSuffixCd());
                    address.setInstitutionCd(addressEntity.getInstitutionCd());
                    address.setInstitutionName(addressEntity.getInstitutionName());
                    address.setDepartmentCd(addressEntity.getDepartmentCd());
                    address.setDepartmentName(addressEntity.getDepartmentName());
                    address.setAddress_01(addressEntity.getAddressLine1());
                    address.setAddress_02(addressEntity.getAddressLine2());
                    address.setCity(addressEntity.getCity());
                    address.setStateCd(addressEntity.getState());
                    address.setCountryCd(addressEntity.getCountryCd());
                    address.setPostalCd(addressEntity.getPostalcode());
                    address.setPhoneNo(addressEntity.getPhone());
                    address.setFaxNo(addressEntity.getFax());
                    address.setInstitutionCd(addressEntity.getInstitutionCd());
                    address.setInstitutionName(addressEntity.getInstitutionName());
                    address.setDepartmentCd(addressEntity.getDepartmentCd());
                    address.setDepartmentName(addressEntity.getDepartmentName());
                    address.setSuffixCd(addressEntity.getSuffixCd());
                    addressList.add(address);
                }
                userProfile.setAddresses(addressList);
            }

            //Get Affiliations
            List<Affiliation> affiliationList = new LinkedList<>();
            Set<UserAffiliations> userAffiliationsSet = user.getAuthorProfileByUserId().getUserAffiliationses();
            if (null != userAffiliationsSet && userAffiliationsSet.size() > 0) {
                for (UserAffiliations userAffiliations : userAffiliationsSet) {
                    Affiliation affiliation = new Affiliation();
                    affiliation.setId("" + userAffiliations.getAffiliationId());
                    affiliation.setInstitutionCd(userAffiliations.getInstitutionCd());
                    affiliation.setInstitutionName(userAffiliations.getInstitutionName());
                    affiliation.setDepartmentCd(userAffiliations.getDepartmentCd());
                    affiliation.setDepartmentName(userAffiliations.getDepartmentName());
                    affiliation.setCity(userAffiliations.getTownOrCityName());
                    affiliation.setCountryCd(userAffiliations.getStateOrProvinceName());
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
                    Society society = new Society();
                    society.setId("" + userSocietyDetails.getSocietyId());
                    society.setMembershipNumber(userSocietyDetails.getMembershipNo());
                    society.setPromotionCode(userSocietyDetails.getPromoCode());
                    society.setSocietyCd(userSocietyDetails.getSocietyCd());
                    societies.add(society);
                }
                userProfile.setSocieties(societies);
            }

            List<Expertise> expertises = new LinkedList<>();
            Set<UserAreaOfInterest> userAreaOfInterestSet = user.getAuthorProfileByUserId().getUserAreaOfInterests();
            if (null != expertises && expertises.size() > 0) {
                for (UserAreaOfInterest userAreaOfInterest : userAreaOfInterestSet) {
                    AreaOfInterest areaOfInterest = userAreaOfInterest.getAreaOfInterest();
                    Expertise expertise = new Expertise();
                    expertise.setId(areaOfInterest.getAreaOfInterestCd());
                    expertise.setAreaofInterestCd(areaOfInterest.getAreaOfInterestCd());
                    expertise.setInterestName(areaOfInterest.getInterestName());
                    expertises.add(expertise);
                }
                userProfile.setExpertises(expertises);
            }

            List<PreferredJournal> journalsList = new LinkedList<>();
            Set<UserPreferredJournals> userPreferredJournalsSet = user.getAuthorProfileByUserId().getUserPreferredJournalses();
            if (null != userPreferredJournalsSet && userPreferredJournalsSet.size() > 0) {
                for (UserPreferredJournals userPreferredJournals : userPreferredJournalsSet) {
                    Journals journal = userPreferredJournals.getJournals();
                    PreferredJournal preferredJournal = new PreferredJournal();
                    preferredJournal.setId("" + journal.getJournalId());
                    preferredJournal.setJournalTitle(journal.getJouTitle());
                    //preferredJournal.setJournalId(journal.getJournalId());
                    journalsList.add(preferredJournal);
                }
                userProfile.setJournals(journalsList);
            }

            List<Alert> alerts = new LinkedList<>();
            Set<UserAlerts> alertsSet = user.getAuthorProfileByUserId().getUserAlertses();
            if (null != alertsSet && alertsSet.size() > 0) {
                for (UserAlerts userAlerts : alertsSet) {
                    Alerts userAlert = userAlerts.getAlerts();
                    Alert alert = new Alert();
                    alert.setId(userAlert.getAlertCd());
                    alert.setAlertName(userAlert.getAlertName());
                    alerts.add(alert);
                }
                userProfile.setAlerts(alerts);
            }

            userResponse.setUserProfile(userProfile);

        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("Exception Occurred during user profile creation...", e);
        } finally {
            if (null != session) {
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
    private <T> Object getEntity(String columnName, String primaryId, Class<T> entityClass) {
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
            e.printStackTrace();
            LOGGER.error("Exception Occurred during get entity...", e);
        } finally {
            if (null != session) {
                session.close();
            }
        }
        return classObj;
    }
}
