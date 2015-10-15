package com.wiley.gr.ace.profile.service;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMethod;

import com.wiley.gr.ace.profile.exception.SharedServiceException;
import com.wiley.gr.ace.profile.model.Address;
import com.wiley.gr.ace.profile.model.AlertList;
import com.wiley.gr.ace.profile.model.Profile;

/**
 * Service interface for Orchestration service.
 * @author rsjayasekara
 *
 */
public interface OrchestrationService {
	
	/**
	 * Interface to process the profile data. Based on the request type this method execute create, update or delete operation.
	 * @param profile 
	 * @param requestType
	 * @param ptpId
	 * @param SourceSystem
	 * @throws SharedServiceException
	 */
	public void processProfile(Profile profile ,RequestMethod requestType,String ptpId,String SourceSystem) throws SharedServiceException;
	
	/**
	 * Interface to process the email data. Based on the request type this method execute create, update or delete operation.
	 * 
	 * @param profile
	 * @param requestType
	 * @param ptpId
	 * @param SourceSystem
	 * @throws SharedServiceException
	 */
	public void processEmail(Profile profile, RequestMethod requestType,String ptpId,String SourceSystem) throws SharedServiceException;
	
	/**
	 * Interface to process the address data. Based on the request type this method execute create, update or delete operation.
	 * 
	 * @param address
	 * @param requestType
	 * @param ptpId
	 * @param SourceSystem
	 * @throws SharedServiceException
	 */
	public void processAddress(Address address, RequestMethod requestType,String ptpId,String SourceSystem) throws SharedServiceException;
	
	/**
	 * Interface to process the favorite journal data. Based on the request type this method execute create, update or delete operation.
	 * 
	 * @param journalList
	 * @param requestType
	 * @param ptpId
	 * @throws SharedServiceException
	 */
	public void processFAVJournals(List<String> journalList, RequestMethod requestType,String ptpId) throws SharedServiceException;
	
	/**
	 * Interface to process the Alert data. based on the request type this method execute create, update or delete operation.
	 * 
	 * @param alertList
	 * @param requestType
	 * @param ptpId
	 * @throws SharedServiceException
	 */
	public void processAlert(AlertList alertList, RequestMethod requestType,String ptpId) throws SharedServiceException;
	
	
}
