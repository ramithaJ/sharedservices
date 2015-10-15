package com.wiley.gr.ace.profile.service;

import com.wiley.gr.ace.profile.exception.SharedServiceException;
import com.wiley.gr.ace.profile.model.participant.Address;
import com.wiley.gr.ace.profile.model.participant.Participant;
import com.wiley.gr.ace.profile.model.participant.Preference;
import com.wiley.gr.ace.profile.model.participant.Relationship;


/**
 * Service inteface to participant services
 * @author rsjayasekara
 *
 */
public interface ParticipantService {

    /**
     * Method to perform update participant details   
     * @param participant
     * @throws SharedServiceException
     */
	public void updateParticipant(Participant participant)throws SharedServiceException;

	/**
	 * Method to update the address in participant DB.
	 * @param address
	 * @param participantId
	 * @throws SharedServiceException
	 */
	public void updateAddress(Address address,String participantId) throws SharedServiceException;
	
	/**
	 * Method to add participant address into profile.
	 * @param address
	 * @param participantId
	 * @throws SharedServiceException
	 */
	public void addAddress(Address address, String participantId) throws SharedServiceException;
	
	/**
	 * Method to delete address from the Profile.
	 * @param addressId
	 * @param participantId
	 */
	public void deleteAddress(String addressId, String participantId);
	
	/**
	 * Method to update the preferences
	 * @param preference
	 * @param participantId
	 * @throws SharedServiceException
	 */
	public void updatePreferences(Preference preference, String participantId) throws SharedServiceException;
	
	/**
	 * Method to update relationships
	 * @param relationship
	 * @throws SharedServiceException
	 */
	public void updateRelationships(Relationship relationship) throws SharedServiceException;
	
	
		
  }
