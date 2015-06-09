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
public class Address implements Serializable {

    private String id;
    private String titleCd;
    private String firstName;
    private String lastName;
    private String suffixCd;
    private String institutionCd;
    private String institutionName;
    private String departmentCd;
    private String departmentName;
    private String address_01;
    private String address_02;
    private String countryCd;
    private String city;
    private String stateCd;
    private String postalCd;
    private String phoneNo;
    private String faxNo;
    private String type;

    public String getTitleCd() {
        return titleCd;
    }

    public void setTitleCd(String titleCd) {
        this.titleCd = titleCd;
    }

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

    public String getSuffixCd() {
        return suffixCd;
    }

    public void setSuffixCd(String suffixCd) {
        this.suffixCd = suffixCd;
    }

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

    public String getAddress_01() {
        return address_01;
    }

    public void setAddress_01(String address_01) {
        this.address_01 = address_01;
    }

    public String getAddress_02() {
        return address_02;
    }

    public void setAddress_02(String address_02) {
        this.address_02 = address_02;
    }

    public String getCountryCd() {
        return countryCd;
    }

    public void setCountryCd(String countryCd) {
        this.countryCd = countryCd;
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

    public String getPostalCd() {
        return postalCd;
    }

    public void setPostalCd(String postalCd) {
        this.postalCd = postalCd;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getFaxNo() {
        return faxNo;
    }

    public void setFaxNo(String faxNo) {
        this.faxNo = faxNo;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Address{" +
                "id='" + id + '\'' +
                ", titleCd='" + titleCd + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", suffixCd='" + suffixCd + '\'' +
                ", institutionCd='" + institutionCd + '\'' +
                ", institutionName='" + institutionName + '\'' +
                ", departmentCd='" + departmentCd + '\'' +
                ", departmentName='" + departmentName + '\'' +
                ", address_01='" + address_01 + '\'' +
                ", address_02='" + address_02 + '\'' +
                ", countryCd='" + countryCd + '\'' +
                ", city='" + city + '\'' +
                ", stateCd='" + stateCd + '\'' +
                ", postalCd='" + postalCd + '\'' +
                ", phoneNo='" + phoneNo + '\'' +
                ", faxNo='" + faxNo + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
