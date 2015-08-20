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

import com.fasterxml.jackson.annotation.JsonProperty;

public class ErrorPayLoad {

    @JsonProperty("ErrorCode")
    protected String errorCode;

    @JsonProperty("ErrorMessage")
    protected String errorMessage;

    /**
     * @return the errorCode
     */
    public final String getErrorCode() {
        return errorCode;
    }

    /**
     * @param errorCode
     *            the errorCode to set
     */
    public final void setErrorCode(final String errorCode) {
        this.errorCode = errorCode;
    }

    /**
     * @return the errorMessage
     */
    public final String getErrorMessage() {
        return errorMessage;
    }

    /**
     * @param errorMessage
     *            the errorMessage to set
     */
    public final void setErrorMessage(final String errorMessage) {
        this.errorMessage = errorMessage;
    }

}
