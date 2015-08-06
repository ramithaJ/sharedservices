package com.wiley.gr.ace.sharedservices.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class StatusPayload {

    public enum Status {
        SUCCESS, FAILURE
    }

    private String status;
    private String message;

    public StatusPayload() {
        status = Status.SUCCESS.name();
    }

    public StatusPayload(Status status, String payload) {
        super();
        if (status != null) {
            this.status = status.name();
        }
        message = payload;
    }

    /**
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status
     *            the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return the payload
     */

    public String getPayload() {
        return message;
    }

    /**
     * @param payload
     *            the payload to set
     */
    public void setPayload(String payload) {
        message = payload;
    }

}
