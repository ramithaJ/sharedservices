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
package com.wiley.gr.ace.discount.model;

import java.io.Serializable;

/**
 * This is the generic errorVO returned by the service.
 *
 * @author virtusa
 *         version 1.0
 */
public class ErrorResponse implements Serializable {

    public int code;

    public String message;

    public ErrorResponse(String code, String message) {
        this.code = Integer.parseInt(code);
        this.message = message;
    }

    public ErrorResponse() {

    }


    /**
     * @param code
     */
    public void setCode(int code) {
        this.code = code;
    }


    /**
     * @param message
     */
    public void setMessage(String message) {
        this.message = message;
    }

}
