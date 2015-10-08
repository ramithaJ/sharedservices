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
package com.wiley.gr.ace.profile.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * This is the generic service VO object which is returned by every service.
 * Data from the service is wrapped into this and returned.
 *
 * @author virtusa
 *         version 1.0
 */
@JsonInclude(Include.NON_NULL)
public class Service {

    private String status = "SUCCESS";

    private Object payload;

    private ErrorResponse error;


    /**
     * @return
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return
     */
    public Object getPayload() {
        return payload;
    }

    /**
     * @param payload
     */
    public void setPayload(Object payload) {
        this.payload = payload;
    }

    /**
     * @return
     */
    public ErrorResponse getError() {
        return error;
    }

    /**
     * @param error
     */
    public void setError(ErrorResponse error) {
        this.error = error;
    }

}
