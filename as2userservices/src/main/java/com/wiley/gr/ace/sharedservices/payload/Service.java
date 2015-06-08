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