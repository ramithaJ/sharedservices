package com.wiley.gr.ace.sharedservices.input;

/**
 * Created by kkalyan on 6/3/2015.
 */
public class ErrorDescription {

    private String stackTrace;
    private String msg;

    public ErrorDescription(String stackTrace, String msg) {
        this.stackTrace = stackTrace;
        this.msg = msg;
    }

    public String getStackTrace() {
        return stackTrace;
    }

    public void setStackTrace(String stackTrace) {
        this.stackTrace = stackTrace;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
