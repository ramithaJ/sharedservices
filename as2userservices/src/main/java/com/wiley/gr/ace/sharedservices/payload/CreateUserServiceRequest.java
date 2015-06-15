package com.wiley.gr.ace.sharedservices.payload;

/**
 * Created by kkalyan on 6/15/2015.
 */
public class CreateUserServiceRequest {

    private UserProfile userProfile;

    public UserProfile getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
    }

    public class UserProfile {

        private String firstName;
        private String lastName;
        private String countryCd;
        private String primaryEmailAddress;
        private String optInFlag;

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getCountryCd() {
            return countryCd;
        }

        public void setCountryCd(String countryCd) {
            this.countryCd = countryCd;
        }

        public String getPrimaryEmailAddress() {
            return primaryEmailAddress;
        }

        public void setPrimaryEmailAddress(String primaryEmailAddress) {
            this.primaryEmailAddress = primaryEmailAddress;
        }

        public String getOptInFlag() {
            return optInFlag;
        }

        public void setOptInFlag(String optInFlag) {
            this.optInFlag = optInFlag;
        }

        @Override
        public String toString() {
            return "UserProfile{" +
                    "firstName='" + firstName + '\'' +
                    ", lastName='" + lastName + '\'' +
                    ", countryCd='" + countryCd + '\'' +
                    ", primaryEmailAddress='" + primaryEmailAddress + '\'' +
                    ", optInFlag='" + optInFlag + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "CreateUserServiceRequest{" +
                "userProfile=" + userProfile +
                '}';
    }
}
