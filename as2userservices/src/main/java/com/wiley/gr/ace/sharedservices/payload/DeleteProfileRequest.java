package com.wiley.gr.ace.sharedservices.payload;

/**
 * Created by kkalyan on 6/15/2015.
 */
public class DeleteProfileRequest {


    private UserProfile userProfile;

    public class UserProfile {

        private String asid;

        public String getAsid() {
            return asid;
        }

        public void setAsid(String asid) {
            this.asid = asid;
        }
    }

    public UserProfile getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
    }

    @Override
    public String toString() {
        return "DeleteProfileRequest{" +
                "userProfile=" + userProfile +
                '}';
    }
}
