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
import java.util.List;

/**
 * @author kkalyan
 */
public class Alert implements Serializable {

    private String id;
    private String alertCd;
    private String alertName;
    private List<AlertType> alertTypes;

    public String getAlertCd() {
        return alertCd;
    }

    public void setAlertCd(String alertCd) {
        this.alertCd = alertCd;
    }

    public List<AlertType> getAlertTypes() {
        return alertTypes;
    }

    public void setAlertTypes(List<AlertType> alertTypes) {
        this.alertTypes = alertTypes;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAlertName() {
        return alertName;
    }

    public void setAlertName(String alertName) {
        this.alertName = alertName;
    }

    @Override
    public String toString() {
        return "Alert{" +
                "alertCd='" + alertCd + '\'' +
                ", alertTypes=" + alertTypes +
                '}';
    }
}
