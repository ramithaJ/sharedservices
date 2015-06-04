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
package com.wiley.gr.ace.sharedservices.profile;

import java.io.Serializable;

/**
 * @author kkalyan
 */
public class AlertType implements Serializable {

    private String id;
    private char onScreen;
    private char email;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public char getOnScreen() {
        return onScreen;
    }

    public void setOnScreen(char onScreen) {
        this.onScreen = onScreen;
    }

    public char getEmail() {
        return email;
    }

    public void setEmail(char email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "AlertType{" +
                "id='" + id + '\'' +
                ", onScreen=" + onScreen +
                ", email=" + email +
                '}';
    }
}
