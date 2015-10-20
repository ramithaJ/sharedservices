package com.wiley.gr.ace.profile.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.wiley.gr.ace.profile.common.CommonConstants;
import com.wiley.gr.ace.profile.model.AlertList;
import com.wiley.gr.ace.profile.model.Journal;
import com.wiley.gr.ace.profile.model.Profile;
import com.wiley.gr.ace.profile.model.participant.Address;
import com.wiley.gr.ace.profile.model.participant.Name;
import com.wiley.gr.ace.profile.model.participant.Participant;
import com.wiley.gr.ace.profile.model.participant.Preference;
import com.wiley.gr.ace.profile.model.participant.Relationship;
import com.wiley.gr.ace.profile.model.person.CreateContactRequestEBM;
import com.wiley.gr.ace.profile.model.person.CustomerDetails;
import com.wiley.gr.ace.profile.model.person.Person;
import com.wiley.gr.ace.profile.model.user.UpdateUserAttributes;
import com.wiley.gr.ace.profile.model.user.UpdateUserRequest;
import com.wiley.gr.ace.profile.model.user.UpdateUserSecurityAttributes;
import com.wiley.gr.ace.profile.model.user.UserAddress;
import com.wiley.gr.ace.profile.model.user.UserPayload;
import com.wiley.gr.ace.profile.model.user.UserSearchResponse;

public class ObjectUtil {

	/**
	 * Returns participant object.
	 * @param profile
	 * @return Participant
	 */
	public static Participant getParticipantObject(Profile profile){
		
		Participant participant = new Participant();
		
		Name name = new Name();
		name.setValue(profile.getAlternativeName());
		List<Name> alternativeNameList = new ArrayList<Name>();
		alternativeNameList.add(name);
		participant.setAlternateNames(alternativeNameList);
		participant.setGivenName(profile.getFirstName());
		participant.setFamilyName(profile.getLastName());
		participant.setName(profile.getFirstName() + profile.getLastName());
		participant.setIndustryId(profile.getIndustryCode());
		participant.setJobCategoryId(profile.getJobCategoryCode());
		participant.setOrcidId(profile.getOrcid());
        participant.setHonorificSuffix(profile.getSuffixCode());
        participant.setJobTitle(profile.getTitleCode());
        participant.setEmail(profile.getPrimaryEmail());
        participant.setRecoveryEmail(profile.getRecoveryEmail());
        participant.setAreasOfInterest(profile.getInterestList());
      //  participant.setCreated("1444176000000");
       // participant.setModified("1444830415000");
        participant.setState(profile.getState());
        
		return participant;
		
	}
	
/**
 * Return customer object.	
 * @param profile
 * @param participantId
 * @return Person
 */
public static Person getCustomerObject(Profile profile, String participantId){
	
	Person person = new Person();
	CreateContactRequestEBM createContactRequestEBM = new CreateContactRequestEBM();
	person.setCreateContactRequestEBM(createContactRequestEBM);
	CustomerDetails customerDetails = createContactRequestEBM.getContactEBM().getCustomerDetails();
	
	
	customerDetails.setAlternativeName(profile.getAlternativeName());
	customerDetails.setFirstName(profile.getFirstName());
	customerDetails.setIndustryCode(profile.getIndustryCode());
	customerDetails.setJobCategoryCode(profile.getJobCategoryCode());
	customerDetails.setLastName(profile.getLastName());
	customerDetails.setMiddleName(profile.getMiddleName());
	customerDetails.setORCID(profile.getOrcid());	
	customerDetails.setSuffix(profile.getSuffixCode());
	customerDetails.setASID(participantId);
	customerDetails.setAuthorFlag(profile.getAuthorFlag());
	customerDetails.setRegistrantFlag(profile.getRegistrantFlag());
	customerDetails.setPrimaryEmail(profile.getPrimaryEmail());
	customerDetails.setSecondaryEmail(profile.getRecoveryEmail());
	customerDetails.setSourceSystem(CommonConstants.SOURCE_SYSTEM);
	
	createContactRequestEBM.getContactEBM().getContactProfile().getContactIdentification().getSourceContactXREF().setSourceCustomerID(participantId);
	createContactRequestEBM.getContactEBM().getContactProfile().getContactIdentification().getSourceContactXREF().setSourceSystem(CommonConstants.SOURCE_SYSTEM);

	
	return person;
		
	}

/**
 * Return customer address object.
 * @param address
 * @param participantId
 * @return Person
 */
public static Person getCustomerAddressObject(com.wiley.gr.ace.profile.model.Address address, String participantId){
	
	Person person = new Person();
	com.wiley.gr.ace.profile.model.person.Address personAddress = new com.wiley.gr.ace.profile.model.person.Address();
	
	personAddress.setAddr_Type_CD(address.getAddressTypeCode());
	personAddress.setAddressLine1(address.getAddressLine1());
	personAddress.setAddressLine2(address.getAddressLine2());
	personAddress.setAddressStartDate(address.getAddressStartDate());
	personAddress.setAddressType(address.getAddressType());
	personAddress.setCity(address.getCity());
	personAddress.setCountryCode(address.getCountryCode());
	personAddress.setCountryName(address.getCountryName());
	personAddress.setState(address.getStateCode());
	personAddress.setDepartmentCd(address.getDepartmentCode());
	personAddress.setDepartmentName(address.getDepartmentName());
	personAddress.setInstitutionCd(address.getInstitutionCode());
	personAddress.setInstitutionName(address.getInstitutionName());
	personAddress.setPhoneNumber(address.getPhoneNumber());
	personAddress.setFirstName(address.getFullName());
	personAddress.setZipCode(address.getZipCode());
	
	List<com.wiley.gr.ace.profile.model.person.Address> addressList = new ArrayList<com.wiley.gr.ace.profile.model.person.Address>();
	addressList.add(personAddress);
	CreateContactRequestEBM createContactRequestEBM = new CreateContactRequestEBM();
	person.setCreateContactRequestEBM(createContactRequestEBM);
	createContactRequestEBM.getContactEBM().setAddress(addressList);
	
	createContactRequestEBM.getContactEBM().getContactProfile().getContactIdentification().getSourceContactXREF().setSourceCustomerID(participantId);
	createContactRequestEBM.getContactEBM().getContactProfile().getContactIdentification().getSourceContactXREF().setSourceSystem(CommonConstants.SOURCE_SYSTEM);

	return person;
}


/**
 * Return participant address object
 * @param address
 * @return Address
 */
public static Address getAddressObject(com.wiley.gr.ace.profile.model.Address address){
	
	Address participantAddress = new Address();
	
	participantAddress.setAddressCountry(address.getCountryCode());
	participantAddress.setAddressFunction(address.getAddressType());
	participantAddress.setAddressLocality(address.getCity());
	participantAddress.setAddressRegion(address.getStateCode());
	participantAddress.setAddressType(address.getAddressTypeCode());
	participantAddress.setDepartment(address.getDepartmentName());
	participantAddress.setDepartmentId(address.getDepartmentCode());
	participantAddress.setName(address.getFullName());
	participantAddress.setOrganization(address.getInstitutionName());
	participantAddress.setOrganizationId(address.getInstitutionCode());
	participantAddress.setPostalCode(address.getZipCode());
	participantAddress.setAddressId(address.getAddressId());
	
	List<String> streeAddress = new ArrayList<String>();
	streeAddress.add(address.getAddressLine1());
	streeAddress.add(address.getAddressLine2());
	participantAddress.setStreetAddress(streeAddress);
	
	
	return participantAddress; 
}

/**
 * Return relationship object.
 * @param object
 * @param role
 * @return Relationship
 */
public static Relationship getRelationshipObject(Object object, String role)
{
	Relationship relationship = new Relationship();

	if(role.equalsIgnoreCase(CommonConstants.ENTITY_TYPE_FAVJOURNAL)){
		
		Journal journal = ((Journal)object);
		relationship.setRoleId(CommonConstants.ENTITY_TYPE_FAVJOURNAL);
		relationship.setEntityId(journal.getJournalId());

	}
	return relationship;
}

/**
 * Return user object
 * @param object
 * @param entityType
 * @return UpdateUserAttributes
 */
public static UpdateUserRequest getUserObject(Object object, String entityType) {
	UpdateUserRequest updateUserRequest = new UpdateUserRequest();
	UpdateUserAttributes userAttributes = new UpdateUserAttributes();
	
	updateUserRequest.setUpdateUserAttributes(userAttributes);
	
	if(entityType.equalsIgnoreCase(CommonConstants.ENTITY_TYPE_PROFILE)){
		
		Profile profile = (Profile)object;
		userAttributes.setLastName(profile.getLastName());
		userAttributes.setFirstName(profile.getFirstName());
		userAttributes.setEmail(profile.getPrimaryEmail());
		userAttributes.seteCID(null);
		userAttributes.setUserStatus(profile.getStatus());
		userAttributes.setUserAddress(null);
		userAttributes.setCustomerType(null);
		userAttributes.settCFlag(null);
		userAttributes.setSendEmail(null);
		
	
	}else if(entityType.equalsIgnoreCase(CommonConstants.ENTITY_TYPE_ADDRESS)){
		com.wiley.gr.ace.profile.model.Address address = (com.wiley.gr.ace.profile.model.Address)object;
		
		userAttributes.setLastName(null);
		userAttributes.setFirstName(null);
		userAttributes.setEmail(address.getPrimaryEmail());
		userAttributes.seteCID(null);
		userAttributes.setUserStatus(null);		
		
		UserAddress userAddress = new UserAddress();
		userAddress.setAddress(address.getAddressLine1());
		userAddress.setStreet(address.getAddressLine2());
		userAddress.setState(address.getStateCode());
		userAddress.setCountry(address.getCountryCode());
		userAddress.setZipCode(address.getZipCode());
		userAttributes.setUserAddress(userAddress);
				userAttributes.setCustomerType(null);
		userAttributes.settCFlag(null);
		userAttributes.setSendEmail(null);
		
		
		
	}else if(entityType.equalsIgnoreCase(CommonConstants.ENTITY_TYPE_USER)){
		
		
		UserSearchResponse  user = (UserSearchResponse)object;
		UserPayload userPayload = user.getUserPayloads().getUserPayload().get(0);
		userAttributes.setLastName(userPayload.getLastName());
		userAttributes.setFirstName(userPayload.getFirstName());
		userAttributes.setEmail(userPayload.getEmail());
		
		updateUserRequest.setUpdateUserAttributes(userAttributes);
	}
	
	return updateUserRequest;
}

/**
 * Return user security object.
 * @param profile
 * @return
 */
public static UpdateUserSecurityAttributes getUserSecurityObject(Profile profile) {
	
	UpdateUserSecurityAttributes userSecurityAttributes = new UpdateUserSecurityAttributes();
	userSecurityAttributes.setExistingEmail(profile.getOldEmail());
	userSecurityAttributes.setNewEmail(profile.getPrimaryEmail());
	
	return userSecurityAttributes;
}

/**
 * Return preference object
 * @param object
 * @param entityType
 * @return Preference
 */
public static Preference getPreferenceObject(Object object,String entityType) {
	
	Preference preference = new Preference();
	
	if(entityType.equalsIgnoreCase(CommonConstants.ENTITY_TYPE_FAVJOURNAL)){
	
		List<Journal> journalList = (List<Journal>)object;
		preference.setPreferenceKey(CommonConstants.ENTITY_TYPE_FAVJOURNAL);
		preference.setPreferenceValue(journalList);	
	}if(entityType.equalsIgnoreCase(CommonConstants.ENTITY_TYPE_ALERT)){
		
		AlertList alertList = (AlertList)object;	
		preference.setPreferenceKey(CommonConstants.ENTITY_TYPE_ALERT);
		preference.setPreferenceValue(alertList);
		
	}
	
	
	return preference;
}

}
