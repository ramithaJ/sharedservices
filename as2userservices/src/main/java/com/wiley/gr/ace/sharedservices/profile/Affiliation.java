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
 * Created by kkalyan on 5/27/2015.
 */
public class Affiliation implements Serializable {

    private String id;
    private String institutionCd;
    private String institutionName;
    private String departmentCd;
    private String departmentName;
    private String city;
    private String stateCd;
    private String countryCd;
    private String fromDate;
    private String toDate;

    public String getInstitutionCd() {
        return institutionCd;
    }

    public void setInstitutionCd(String institutionCd) {
        this.institutionCd = institutionCd;
    }

    public String getDepartmentCd() {
        return departmentCd;
    }

    public void setDepartmentCd(String departmentCd) {
        this.departmentCd = departmentCd;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStateCd() {
        return stateCd;
    }

    public void setStateCd(String stateCd) {
        this.stateCd = stateCd;
    }

    public String getCountryCd() {
        return countryCd;
    }

    public void setCountryCd(String countryCd) {
        this.countryCd = countryCd;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInstitutionName() {
        return institutionName;
    }

    public void setInstitutionName(String institutionName) {
        this.institutionName = institutionName;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    @Override
    public String toString() {
        return "Affiliation{" +
                "id='" + id + '\'' +
                ", institutionCd='" + institutionCd + '\'' +
                ", institutionName='" + institutionName + '\'' +
                ", departmentCd='" + departmentCd + '\'' +
                ", departmentName='" + departmentName + '\'' +
                ", city='" + city + '\'' +
                ", stateCd='" + stateCd + '\'' +
                ", countryCd='" + countryCd + '\'' +
                ", fromDate='" + fromDate + '\'' +
                ", toDate='" + toDate + '\'' +
                '}';
    }
}
