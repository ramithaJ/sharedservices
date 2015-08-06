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
public class MyInterest implements Serializable {

    private String id;

    private String areaofInterestCd;

    private String status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAreaofInterestCd() {
        return areaofInterestCd;
    }

    public void setAreaofInterestCd(String areaofInterestCd) {
        this.areaofInterestCd = areaofInterestCd;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "MyInterest{" +
                "id='" + id + '\'' +
                ", areaofInterestCd='" + areaofInterestCd + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
