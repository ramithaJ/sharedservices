package com.wiley.gr.ace.sharedservices.payload;

/**
 * Created by KKALYAN on 6/26/2015.
 */
public class LookupResponse {

    private String userId;
    private String emailType;
    private String primaryEmailAddress;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEmailType() {
        return emailType;
    }

    public void setEmailType(String emailType) {
        this.emailType = emailType;
    }

    public String getPrimaryEmailAddress() {
        return primaryEmailAddress;
    }

    public void setPrimaryEmailAddress(String primaryEmailAddress) {
        this.primaryEmailAddress = primaryEmailAddress;
    }
}
