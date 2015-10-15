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
package com.wiley.gr.ace.profile.common;

public class CommonConstants {

    public static final String VERSION = "/v1/";


    public static final String PROFILE = "/profile";

    public static final String PROFILE_STATUS = "/profile/status";

    public static final String ERROR = "ERROR";

    public static final String SUCCESS = "SUCCESS";
    public static final String FAILURE = "FAILURE";
 
    public static final String API_STATUS = "OK";


   
    public static final String ERROR_CODE_400 = "400";

    public static final String ERROR_NOT_SUPPORT_ENTITY= "Entity type not support";
    public static final String ERROR_ENTITY_TYPE_REQUIRED= "Entity type required";
    public static final String ERROR_ENTITY_ID_REQUIRED= "Entity ID required";
    public static final String ERROR_ENTITY_VALUE_REQUIRED= "Entity Value required";
      
    public static final String ERROR_EAMIL_REQUIRED= "Old Email required";
     
    
    public static final String ENTITY_TYPE_PROFILE = "PROFILE";
    public static final String ENTITY_TYPE_ADDRESS = "ADDRESS";
    public static final String ENTITY_TYPE_EMAIL = "EMAIL";
    public static final String ENTITY_TYPE_INTEREST = "INTEREST";
    public static final String ENTITY_TYPE_FAVJOURNAL = "FAVJOURNAL";
    public static final String ENTITY_TYPE_ALERT = "ALERT";
	public static final String ENTITY_TYPE_USER = "USER";
    
    public static final String SOURCE_SYSTEM = "AS";
    
    
    public static final String  PARTICIPANT = "/participants/";
    public static final String  CDM_CREATE_SERVICE="/CDMService/CreateContact";
    public static final String  ALM_UPDATE_SERVICE="/ALMService/ALMUser/Update";


	

    
    
    
	

    private CommonConstants() {
    	
 
    }
}

