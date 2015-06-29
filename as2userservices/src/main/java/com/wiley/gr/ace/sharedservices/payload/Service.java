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

package com.wiley.gr.ace.sharedservices.payload;

/**
 * Created by kkalyan on 6/3/2015.
 */
public class Service {

    private String status = "SUCCESS";

    private Object payload;

    private Error error;

    public Service(String status) {
        this.status = status;
    }

    public Service (){

    }

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
    public Error getError() {
        return error;
    }

    /**
     * @param error
     */
    public void setError(Error error) {
        this.error = error;
    }
}
