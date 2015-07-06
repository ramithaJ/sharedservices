/*******************************************************************************
 * Copyright (c) 2015 John Wiley & Sons, Inc. All rights reserved.
 *
 * All material contained herein is proprietary to John Wiley & Sons 
 * and its third party suppliers, if any. The methods, techniques and 
 * technical concepts contained herein are considered trade secrets 
 * and confidential and may be protected by intellectual property laws.  
 * Reproduction or distribution of this material, in whole or in part, 
 * is strictly forbidden except by express prior written permission 
 * of John Wiley & Sons.
 *******************************************************************************/
package com.wiley.gr.ace.authorservices.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * This is the generic service VO object which is returned by every service.
 * Data from the service is wrapped into this and returned.
 * 
 * @author SarmaKumarap
 */
@JsonInclude(Include.NON_NULL)
public class Service {
    
    /** The status. */
    private String status = "SUCCESS";
    
    /** The payload. */
    private Object payload;
    
    /** The error. */
    private ErrorPOJO error;
    
    /**
     * Gets the status.
     *
     * @return the status
     */
    public String getStatus() {
        return status;
    }
    
    /**
     * Sets the status.
     *
     * @param status the new status
     */
    public void setStatus(String status) {
        this.status = status;
    }
    
    /**
     * Gets the payload.
     *
     * @return the payload
     */
    public Object getPayload() {
        return payload;
    }
    
    /**
     * Sets the payload.
     *
     * @param payload the new payload
     */
    public void setPayload(Object payload) {
        this.payload = payload;
    }
    
    /**
     * Gets the error.
     *
     * @return the error
     */
    public ErrorPOJO getError() {
        return error;
    }
    
    /**
     * Sets the error.
     *
     * @param error the new error
     */
    public void setError(ErrorPOJO error) {
        this.error = error;
    }
    
}
