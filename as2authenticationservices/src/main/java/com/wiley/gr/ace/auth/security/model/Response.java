/**
 * ****************************************************************************
 * Copyright (c) 2015 John Wiley & Sons, Inc. All rights reserved.
 * <p/>
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

public class Response {

    private String code;
    private String message;
    private String status;

    public Response(String code, String message, String status) {
        this.code = code;
        this.message = message;
        this.status = status;
    }

    public Response(String message, String status) {
        this.code = CommonConstant.STATUS_CODE;
        this.message = message;
        this.status = status;
    }

    public Response(String message) {
        this.code = CommonConstant.STATUS_CODE;
        this.message = message;
        this.status = CommonConstant.SUCCESS_STATUS;
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    @Override
    public String toString() {
        return "AuthenticateResponse{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", status='" + status + '\'' +
                '}';
    }

    public static enum STATUS {ERROR, SUCCESS, FAILURE;}
}

