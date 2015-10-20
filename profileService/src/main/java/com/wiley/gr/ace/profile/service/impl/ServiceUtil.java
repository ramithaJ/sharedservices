package com.wiley.gr.ace.profile.service.impl;


import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.wiley.gr.ace.profile.exception.SharedServiceException;
public class ServiceUtil {


	/**
	 * Logger for Service util class.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(ServiceUtil.class);

	
	/**
	 * Method to execute Get service.
	 * @param url
	 * @param responseEntityClass
	 * @return
	 * @throws SharedServiceException
	 */
	public static <T> Object getData(final String url,final Class<T> responseEntityClass) throws SharedServiceException {
		
		 ResponseEntity<T> response =null;
		try{

			LOGGER.info("Execute Get for" + url);
			
	    response =  new RestTemplate().getForEntity(url, responseEntityClass);
	    
		   LOGGER.info("Responce reviced for" + url);
			    
		} catch(Exception e){
			
			LOGGER.error(e.getMessage());
			
	    	throw new SharedServiceException(null, e.getMessage());
	    
		}	
	    
		return response.getBody();

	}


	/**
	 * Method to execute POST service.
	 * @param url
	 * @param requestEntityClass
	 * @param responseEntityClass
	 * @return
	 * @throws SharedServiceException
	 */
	public static <T> Object postData(final String url,
			final Object requestEntityClass, final Class<T> responseEntityClass) throws SharedServiceException {
		
		 ResponseEntity<T> response =null;
		 
			
		try{

			ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
			try {
				String json = ow.writeValueAsString(requestEntityClass);
			//	LOGGER.debug(json);
				System.out.println(json);
				
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				LOGGER.info(e.getMessage());
			}
					
	
			 response = new RestTemplate().postForEntity(url, requestEntityClass, responseEntityClass);
				
	    
		} catch(Exception e){
	    	throw new SharedServiceException(null, e.getMessage());
	    
		}	
	    
		return response.getBody();

	}

	/**
	 * Method to execute PUT service.
	 * @param url
	 * @param requestEntityClass
	 * @throws SharedServiceException
	 */
	public static <T> void putData(final String url,final Object requestEntityClass) throws SharedServiceException {
		
		try{

			   logRequest(requestEntityClass);
								
				RestTemplate restTemplate = new RestTemplate();
				
				ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
				try {
					String json = ow.writeValueAsString(requestEntityClass);
				//	LOGGER.debug(json);
					System.out.println(json);
					
				} catch (JsonProcessingException e) {
					// TODO Auto-generated catch block
					LOGGER.info(e.getMessage());
				}
			
				restTemplate.put(url, getHeaderDetails(requestEntityClass), requestEntityClass.getClass());
				
			}catch(RestClientException e){
				
				LOGGER.error(e.getMessage());
			throw new SharedServiceException(null, e.getMessage());
		}

	}
	
/**
 * Method to execute delete service.
 * @param url
 */
	public static <T> void deleteData(final String url) {

	     new RestTemplate().delete(url);

	}

	private static HttpEntity<Object>  getHeaderDetails(Object  object){
		
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		HttpEntity<Object> entity = new HttpEntity<Object>(object, headers);
		
		return entity;
	
	}

	/**
	 * Method to log request json string
	 * @param object
	 */
	private static void logRequest(Object object){
		
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		try {
			String json = ow.writeValueAsString(object);
			LOGGER.debug(json);
			System.out.println(json);
			
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}