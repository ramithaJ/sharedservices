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
package com.wiley.gr.ace.search.exception;

/**
 * The Class SharedSearchException.
 *
 * @author virtusa version 1.0
 */
public class SharedSearchException extends Exception {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /** The error code. **/
    private final String errorCode;

    /**
     * Constructor method.
     * 
     * @param errorCode
     *            - the input value
     * @param message
     *            - the input value
     */
    public SharedSearchException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    /**
     * The getter method.
     * 
     * @return errorCode.
     */
    public String getErrorCode() {
        return errorCode;
    }

}
