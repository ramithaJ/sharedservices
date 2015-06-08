package com.wiley.gr.ace.sharedservices.payload;

/**
 * Created by kkalyan on 6/3/2015.
 */
public class Error {

    private int code;

    private String message;


    /**
     * @return
     */
    public int getCode() {
        return code;
    }

    /**
     * @param code
     */
    public void setCode(int code) {
        this.code = code;
    }

    /**
     * @return
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message
     */
    public void setMessage(String message) {
        this.message = message;
    }
}
