package com.wiley.gr.ace.sharedservices.input;

/**
 * Created by kkalyan on 6/3/2015.
 */
public class ErrorResponse {

    private String errorCode;
    private String errorType;
    private ErrorDescription errorDescription;

    public ErrorResponse(String errorCode, String errorType, ErrorDescription errorDescription) {
        this.errorCode = errorCode;
        this.errorType = errorType;
        this.errorDescription = errorDescription;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorType() {
        return errorType;
    }

    public void setErrorType(String errorType) {
        this.errorType = errorType;
    }

    public ErrorDescription getErrorDescription() {
        return errorDescription;
    }

    public void setErrorDescription(ErrorDescription errorDescription) {
        this.errorDescription = errorDescription;
    }

    @Override
    public String toString() {
        return "ErrorResponse{" +
                "errorCode='" + errorCode + '\'' +
                ", errorType='" + errorType + '\'' +
                ", errorDescription=" + errorDescription +
                '}';
    }
}
