package com.wiley.gr.ace.sharedservices.payload;

/**
 * Created by kkalyan on 6/15/2015.
 */
public class CreateUserResponse {

    private String asid;

    public CreateUserResponse(String asid) {
        this.asid = asid;
    }

    public String getAsid() {
        return asid;
    }

    public void setAsid(String asid) {
        this.asid = asid;
    }

    @Override
    public String toString() {
        return "CreateUserResponse{" +
                "asid='" + asid + '\'' +
                '}';
    }
}
