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
public class Funder implements Serializable {

    private String id;
    private String researchFunderCd;
    private List<GrantNumber> grantNumbers;

    public String getResearchFunderCd() {
        return researchFunderCd;
    }

    public void setResearchFunderCd(String researchFunderCd) {
        this.researchFunderCd = researchFunderCd;
    }

    public List<GrantNumber> getGrantNumbers() {
        return grantNumbers;
    }

    public void setGrantNumbers(List<GrantNumber> grantNumbers) {
        this.grantNumbers = grantNumbers;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Funder{" +
                "id='" + id + '\'' +
                ", researchFunderCd='" + researchFunderCd + '\'' +
                ", grantNumbers=" + grantNumbers +
                '}';
    }
}
