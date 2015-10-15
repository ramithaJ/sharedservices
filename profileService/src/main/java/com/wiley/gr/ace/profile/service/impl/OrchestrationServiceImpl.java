package com.wiley.gr.ace.profile.service.impl;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMethod;

import com.wiley.gr.ace.profile.common.CommonConstants;
import com.wiley.gr.ace.profile.controller.ProfileServiceController;
import com.wiley.gr.ace.profile.exception.SharedServiceException;
import com.wiley.gr.ace.profile.model.Address;
import com.wiley.gr.ace.profile.model.AlertList;
import com.wiley.gr.ace.profile.model.Profile;
import com.wiley.gr.ace.profile.model.participant.Participant;
import com.wiley.gr.ace.profile.model.participant.Preference;
import com.wiley.gr.ace.profile.model.person.Person;
import com.wiley.gr.ace.profile.model.user.UpdateUserRequest;
import com.wiley.gr.ace.profile.model.user.UpdateUserResponse;
import com.wiley.gr.ace.profile.model.user.UpdateUserSecurityAttributes;
import com.wiley.gr.ace.profile.model.user.UserPayload;
import com.wiley.gr.ace.profile.model.user.UserSearchResponse;
import com.wiley.gr.ace.profile.service.OrchestrationService;
import com.wiley.gr.ace.profile.service.ParticipantService;
import com.wiley.gr.ace.profile.service.PersonService;
import com.wiley.gr.ace.profile.service.UserService;

/**
 * 
 * @author rsjayasekara
 *
 */
public class OrchestrationServiceImpl implements OrchestrationService {
	
	/**
	 * Logger for OrchestrationServiceImpl class.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(ProfileServiceController.class);

	
	/**
	 * Hold participant service instances.
	 */
	@Autowired
	ParticipantService participantService;
	
	/**
	 * Hold person service instance.
	 */
	@Autowired
	PersonService personService; 

	/**
	 * Hold user service instance.
	 */
	@Autowired
	UserService userService; 
	
	/**
	 * Holed user object.
	 */
	UserSearchResponse user;
	
	/**
	 * Flag to indicate wheather required to update user master.
	 */
	boolean doUpdateUser = false;

	
	/**
	 * Implementation logic for orchestration between user master, person master and participant master.  
	 * Based on the request type method update the profile object 
	 */
	@Override
	public void processProfile(Profile profile, RequestMethod requestType,String ptpId,String SourceSystem) throws SharedServiceException {
		
		if(requestType.equals(RequestMethod.PUT)){
			
			
			user = userService.searchUser((profile.getPrimaryEmail()));
			
				if(CommonConstants.FAILURE.equalsIgnoreCase(user.getStatus())){
			
				throw new SharedServiceException(user.getError().getErrorCode(), user.getError().getErrorMessage());
			}
			
			LOGGER.info("User obejct recived from User master");
			
			updateUser(profile,CommonConstants.ENTITY_TYPE_PROFILE,SourceSystem);
		
			LOGGER.info("Profile data sucessfully update in User master");
			
			try{
				
			Participant participant = ObjectUtil.getParticipantObject(profile);
			participant.setParticipantId(ptpId);
			
			participantService.updateParticipant(participant);
		    
			LOGGER.info("Profile data sucessfully update in particiapnt master");
			
			}catch(SharedServiceException e){
				
				if(doUpdateUser){
					
				//Roll back ALM update	
				UpdateUserRequest updateUserRequest = ObjectUtil.getUserObject(user,CommonConstants.ENTITY_TYPE_USER);
				userService.updateUser(updateUserRequest);
				
				LOGGER.info("Profile role back sucessful in User master");
						
				}
		
				throw new SharedServiceException(e.getErrorCode(), e.getMessage());
			} 
			
			Person person = ObjectUtil.getCustomerObject(profile,ptpId);
			
			personService.updatePerson(person);
			
			LOGGER.info("Profile data sucessfully update in person master");
			
		}
		
		
	}

	/**
	 * Method to update user in user master.
	 * @param object
	 * @param entityType
	 * @param sourceSystem
	 * @throws SharedServiceException
	 */
	private void updateUser(Object object, String entityType,String sourceSystem) throws SharedServiceException {
		
		
		if(user !=null && user.getStatus().equalsIgnoreCase(CommonConstants.SUCCESS)){
		
			checkUpdate(object, user,entityType);
		}
		
		if(doUpdateUser){
			
			UpdateUserRequest updateUserRequest = ObjectUtil.getUserObject(object,entityType);
			updateUserRequest.getUpdateUserAttributes().setSourceSystem(sourceSystem);
			
			UpdateUserResponse updateUserResponse= userService.updateUser(updateUserRequest);
			
			
			if(CommonConstants.FAILURE.equalsIgnoreCase(updateUserResponse.getStatus())){
				
				throw new SharedServiceException(updateUserResponse.getError().getErrorCode(),updateUserResponse.getError().getErrorMessage());
			}
		}
	}

	/**
	 * Method to update user name in User master.
	 * @param object
	 * @param entityType
	 * @param sourceSystem
	 * @throws SharedServiceException
	 */
	private void updateUserName(Object object, String entityType,String sourceSystem) throws SharedServiceException {
		
		
		if(user !=null && user.getStatus().equalsIgnoreCase(CommonConstants.SUCCESS)){
		
			checkUpdate(object, user,entityType);
		}
		
		if(doUpdateUser){
			Profile profile = (Profile)object;
			
			UpdateUserSecurityAttributes updateUserSecurityAttributes = ObjectUtil.getUserSecurityObject(profile);
			updateUserSecurityAttributes.setSourceSystem(sourceSystem);
			
			userService.updateUserName(updateUserSecurityAttributes);
		}
	}

	
	/**
	 * Method to update the email address
	 */
	@Override
	public void processEmail(Profile profile, RequestMethod requestType,String ptpId,String sourceSystem) throws SharedServiceException {
		
		if(requestType.equals(RequestMethod.POST)){
		
			Participant participant = ObjectUtil.getParticipantObject(profile);
			participant.setParticipantId(ptpId);
			participantService.updateParticipant(participant);
			
			LOGGER.info("Profile sucessfully update with recovery email address");
			
			
		}else if(requestType.equals(RequestMethod.PUT)){
			
	       user = userService.searchUser((profile.getOldEmail()));
	       
	       if(CommonConstants.FAILURE.equals(user.getStatus())){
	    	   
	    	   throw new SharedServiceException(user.getError().getErrorCode(), user.getError().getErrorMessage());
	       }
			
		   updateUserName(profile,CommonConstants.ENTITY_TYPE_EMAIL,sourceSystem);
		
			LOGGER.info("User name updated in user master");
			
			try{
			
			Participant participant = ObjectUtil.getParticipantObject(profile);
			participant.setParticipantId(ptpId);
			participantService.updateParticipant(participant);
			
			LOGGER.info("User name updated in participant master");
				
			
			}catch(SharedServiceException e){
				
				LOGGER.error(e.getMessage());
				
				
				// roll back to previous user id
				UpdateUserSecurityAttributes updateUserSecurityAttributes = new UpdateUserSecurityAttributes();
			//	updateUserSecurityAttributes.setExistingEmail(user.getUserPayloads().getUserPayload().get(0).getEmail());
				updateUserSecurityAttributes.setNewEmail(profile.getOldEmail());
				updateUserSecurityAttributes.setSourceSystem(sourceSystem);
				
				userService.updateUserName(updateUserSecurityAttributes);
				
				LOGGER.info("User name rollback sucessfull in user master");
				
				
			    throw new SharedServiceException(e.getErrorCode(), e.getMessage());	
				
			}
		
			
		}
			
		
	}

	/**
	 * Method to process the address data. based on the request type method will execute the create,update, delete operation. 
	 */
	@Override
	public void processAddress(Address address, RequestMethod requestType,String ptpId,String SourceSystem) throws SharedServiceException {
	
		com.wiley.gr.ace.profile.model.participant.Address participantAddress = ObjectUtil.getAddressObject(address);
		
		if(requestType.equals(RequestMethod.POST)){
		
		participantService.addAddress(participantAddress, ptpId);
		
		LOGGER.info("Add address data to profile sucessfully in participant master");
		
		
		//TODO: Need to get a participant id for address and send to CDM;
		
		Person person = ObjectUtil.getCustomerAddressObject(address, ptpId);
	    //personService.updatePerson(person);
	
		LOGGER.info("update address data in person master sucessfull");
		
		
		}else if(requestType.equals(RequestMethod.PUT)){
			
	     user = userService.searchUser((address.getPrimaryEmail()));
			
		 updateUser(address, CommonConstants.ENTITY_TYPE_ADDRESS,SourceSystem);
		
			LOGGER.info("update address data in user master sucessfull");
			
		
		try{
			participantService.updateAddress(participantAddress, ptpId);
			
			LOGGER.info("update address data in participant master sucessfull");
			
			Person person = ObjectUtil.getCustomerAddressObject(address, ptpId);
			personService.updatePerson(person);
		
					
		}catch(SharedServiceException e){
			
			//Roll back ALM update	
			UpdateUserRequest updateUserRequest = ObjectUtil.getUserObject(user,CommonConstants.ENTITY_TYPE_USER);
			userService.updateUser(updateUserRequest);
			
			throw new SharedServiceException(e.getErrorCode(), e.getMessage());
		}
			
		}else if(requestType.equals(RequestMethod.DELETE)){
			
			
			participantService.deleteAddress(participantAddress.getAddressId(), ptpId);
			
			LOGGER.info("delete address data in participant master sucessfull");
			
			Person person = ObjectUtil.getCustomerAddressObject(address, ptpId);
			//personService.updatePerson(person);
			
			LOGGER.info("delete address data in person master sucessfull");
			
					
		}
		
	}

	/**
	 *  Method to update favorite journals in particiapnt master.
	 */
	@Override
	public void processFAVJournals(List<String> journnalList,
			RequestMethod requestType,String ptpId) throws SharedServiceException {
		
	/*	if(requestType.equals(RequestMethod.POST)){
			
			Preference preference = ObjectUtil.getPreferenceObject(journnalList,CommonConstants.ENTITY_TYPE_FAVJOURNAL);
			participantService.addPreferences(preference, ptpId);
						
		}*/if(requestType.equals(RequestMethod.PUT)){
		
			Preference preference = ObjectUtil.getPreferenceObject(journnalList,CommonConstants.ENTITY_TYPE_FAVJOURNAL);
			participantService.updatePreferences(preference,ptpId);
			
			LOGGER.info("Favorite journal details successfully updated in participant master");
			
		}
		
	}


	/**
	 * Method to update alert information
	 */
	@Override
	public void processAlert(AlertList alertList, RequestMethod requestType,String ptpId) throws SharedServiceException {
	
		Preference preference = ObjectUtil.getPreferenceObject(alertList,CommonConstants.ENTITY_TYPE_ALERT);
		
		/*if(requestType.equals(RequestMethod.POST)){
		
		participantService.addPreferences(preference, ptpId);
		
		}else */if(requestType.equals(RequestMethod.PUT)){
		
		participantService.updatePreferences(preference, ptpId);
		
		LOGGER.info("Alert details successfully updated in participant master");
			
		}
	} 

		
	/**
	 * Method checks required attributes to update in User master
	 * @param object
	 * @param userSearch
	 * @param entityType
	 * @return doUpdateUser
	 */
	
	private boolean checkUpdate(Object object, UserSearchResponse userSearch,String entityType){
		
		UserPayload userPayload = userSearch.getUserPayloads().getUserPayload().get(0);
		
		if(entityType.equalsIgnoreCase(CommonConstants.ENTITY_TYPE_PROFILE)){
			Profile profile = (Profile)object;
			if(!profile.getFirstName().equalsIgnoreCase(userPayload.getFirstName())){
				
				doUpdateUser = true;
				
			}else if(!profile.getLastName().equalsIgnoreCase(userPayload.getLastName())){
				doUpdateUser = true;
			}
		}else if(entityType.equalsIgnoreCase(CommonConstants.ENTITY_TYPE_ADDRESS)){
			
			Address address = (Address)object;
			if(!address.getCountryCode().equalsIgnoreCase(userPayload.getUserAddress().getCountry())){
				
				doUpdateUser = true;
				
			}
		}else if(entityType.equalsIgnoreCase(CommonConstants.ENTITY_TYPE_EMAIL)){
			
			Profile profile = (Profile)object;
			if(!profile.getPrimaryEmail().equalsIgnoreCase(userPayload.getEmail())){
				
				doUpdateUser = true;
			}
		}
		
		
		
		return doUpdateUser;
	}
	
	public UserSearchResponse getUser() {
		return user;
	}

}
