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
    private String researchFunderName;
    private String researchFunderDoi;
    private List<GrantNumber> grantNumbers;
    private String status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getResearchFunderName() {
        return researchFunderName;
    }

    public void setResearchFunderName(String researchFunderName) {
        this.researchFunderName = researchFunderName;
    }

    public String getResearchFunderDoi() {
        return researchFunderDoi;
    }

    public void setResearchFunderDoi(String researchFunderDoi) {
        this.researchFunderDoi = researchFunderDoi;
    }

    public List<GrantNumber> getGrantNumbers() {
        return grantNumbers;
    }

    public void setGrantNumbers(List<GrantNumber> grantNumbers) {
        this.grantNumbers = grantNumbers;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Funder{" +
                "id='" + id + '\'' +
                ", researchFunderName='" + researchFunderName + '\'' +
                ", researchFunderDoi='" + researchFunderDoi + '\'' +
                ", grantNumbers=" + grantNumbers +
                '}';
    }
}
