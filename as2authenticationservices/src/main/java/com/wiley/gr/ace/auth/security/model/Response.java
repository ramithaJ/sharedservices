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
package com.wiley.gr.ace.auth.security.model;

import com.wiley.gr.ace.auth.security.constants.CommonConstant;

/**
 * @author Virtusa
 *
 */
public class Response {

    /**
     * This field holds the value of code
     */
    private String code;
    /**
     * This field holds the value of message
     */
    private String message;
    /**
     * This field holds the value of status
     */
    private String status;

    /**
     * Default constructor
     */
    public Response() {

    }

    /**
     * Constructor
     *
     * @param message
     */
    public Response(final String message) {
        this.code = CommonConstant.STATUS_CODE;
        this.message = message;
        this.status = CommonConstant.SUCCESS_STATUS;
    }

    /**
     * Constructor
     *
     * @param message
     * @param status
     */
    public Response(final String message, final String status) {
        this.code = CommonConstant.STATUS_CODE;
        this.message = message;
        this.status = status;
    }

    /**
     * Constructor
     *
     * @param code
     * @param message
     * @param status
     */
    public Response(final String code, final String message, final String status) {
        this.code = code;
        this.message = message;
        this.status = status;
    }

    /**
     * This method gets Code
     *
     * @return String
     */
    public String getCode() {
        return this.code;
    }

    /**
     * This method sets Code
     *
     * @param code
     */
    public void setCode(final String code) {
        this.code = code;
    }

    /**
     * This method gets Message
     *
     * @return String
     */
    public String getMessage() {
        return this.message;
    }

    /**
     * This method sets Message
     *
     * @param message
     */
    public void setMessage(final String message) {
        this.message = message;
    }

    /**
     * This method gets Status
     *
     * @return String
     */
    public String getStatus() {
        return this.status;
    }

    /**
     * This method sets Status
     *
     * @param status
     */
    public void setStatus(final String status) {
        this.status = status;
    }

    /**
     * This method toString
     *
     * @return String
     */
    @Override
    public String toString() {
        return "AuthenticateResponse{" + "code='" + this.code + '\''
                + ", message='" + this.message + '\'' + ", status='"
                + this.status + '\'' + '}';
    }

    public static enum STATUS {
        ERROR, SUCCESS, FAILURE, LOCKED, UNREGISTERED;
    }
}