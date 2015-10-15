package com.wiley.gr.ace.profile.service.impl;

import org.springframework.beans.factory.annotation.Value;

import com.wiley.gr.ace.profile.common.CommonConstants;
import com.wiley.gr.ace.profile.exception.SharedServiceException;
import com.wiley.gr.ace.profile.model.participant.Address;
import com.wiley.gr.ace.profile.model.participant.Participant;
import com.wiley.gr.ace.profile.model.participant.Preference;
import com.wiley.gr.ace.profile.model.participant.Relationship;
import com.wiley.gr.ace.profile.service.ParticipantService;

public class ParticipantServiceImpl implements ParticipantService {
	
	
	  /** participantDomain. */
    @Value("${participantDomain.value}")
    private String participantDomain;
    
    /**
     * Method to update participant
     */

	@Override
	public void updateParticipant(Participant participant) throws SharedServiceException {
			
		String url = participantDomain.concat(participant.getParticipantId());
		ServiceUtil.putData(url, participant);
		
	}

	/**
	 * Method to update address.
	 */
	@Override
	public void updateAddress(Address address, String participantId) throws SharedServiceException {
	
		String url = participantDomain+participantId+"/addresses/"+address.getAddressId();
		ServiceUtil.putData(url, address);
	
	}

	/**
	 * Method to add address.
	 */
	@Override
	public void addAddress(Address address, String participantId) throws SharedServiceException {

		String url = participantDomain+participantId+"/addresses";
		ServiceUtil.postData(url, address, Address.class);

	}

	/**
	 * Method to delete address.
	 */
	@Override
	public void deleteAddress(String addressId, String participantId) {
		
		String url = participantDomain + CommonConstants.PARTICIPANT+"/"+participantId+"/addresses/"+ addressId;
		ServiceUtil.deleteData(url);

	}

	/*@Override
	public void addPreferences(Preference preference, String participantId) throws SharedServiceException {
	
		String url = participantDomain +participantId+"/preferences";
		ServiceUtil.postData(url, preference, Preference.class);

	}
*/
	
	/**
	 * Method to add preferences
	 */
	@Override
	public void updatePreferences(Preference preference, String participantId) throws SharedServiceException {
		
		String url = participantDomain +participantId+"/preferences/"+preference.getPreferenceKey();
		ServiceUtil.putData(url,preference);
	
	}

/*	@Override
	public void deletePreferences(Preference preference, String participantId) {
		
		String url = participantDomain + CommonConstants.PARTICIPANT+"/"+participantId+"/preferences/"+preference.getPreferenceKey();
		ServiceUtil.deleteData(url);
	
	}

	@Override
	public void addRelationships(Relationship relationship) throws SharedServiceException{
		
		String url = participantDomain+"/"+relationship.getParticipantId()+"/relationships";
		ServiceUtil.postData(url, relationship, Relationship.class);
	
	}
*/
	/**
	 * Method to update relationship
	 */
	@Override
	public void updateRelationships(Relationship relationship) throws SharedServiceException {
		
		String url = participantDomain + CommonConstants.PARTICIPANT+"/"+relationship.getParticipantId()+"/relationships/"+relationship.getRelationshipId();
		
		ServiceUtil.putData(url, relationship);
	
	}

/*	@Override
	public void deleteRelationships(Relationship relationship) throws SharedServiceException {
		
		String url = participantDomain + CommonConstants.PARTICIPANT+"/"+relationship.getParticipantId()+"/relationships/"+relationship.getRelationshipId();
		ServiceUtil.deleteData(url);
		
	}
*/
}
