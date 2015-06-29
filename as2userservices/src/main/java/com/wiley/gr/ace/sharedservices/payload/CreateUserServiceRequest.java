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
