package com.wiley.gr.ace.auth.security.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserServiceRequest {

    /**
     * value of updateUserSecurityAttributes
     */
    @JsonProperty("UpdateUserSecurityAttributes")
    private UserSecurityAttributes updateUserSecurityAttributes;

    /**
     * @return the updateUserSecurityAttributes
     */
    public final UserSecurityAttributes getUpdateUserSecurityAttributes() {
        return updateUserSecurityAttributes;
    }

    /**
     * @param updateUserSecurityAttributes the updateUserSecurityAttributes to set
     */
    public final void setUpdateUserSecurityAttributes(
            UserSecurityAttributes updateUserSecurityAttributes) {
        this.updateUserSecurityAttributes = updateUserSecurityAttributes;
    }


}
