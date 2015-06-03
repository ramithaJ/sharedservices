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
public class Expertise implements Serializable {

    private String id;

    private String areaofInterestCd;

    private String interestName;

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

    public String getInterestName() {
        return interestName;
    }

    public void setInterestName(String interestName) {
        this.interestName = interestName;
    }

    @Override
    public String toString() {
        return "Expertise{" +
                "id='" + id + '\'' +
                ", areaofInterestCd='" + areaofInterestCd + '\'' +
                ", interestName='" + interestName + '\'' +
                '}';
    }
}
