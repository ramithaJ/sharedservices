package com.wiley.gr.ace.sharedservices.persistence.entity;

// Generated Jul 7, 2015 5:39:57 PM by Hibernate Tools 3.4.0.CR1

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Address generated by hbm2java
 */
@Entity
@Table(name = "ADDRESS")
public class Address implements java.io.Serializable {

    private Integer addressId;
    private Users usersByCreatedBy;
    private Users usersByUpdatedBy;
    private String titleCd;
    private String firstName;
    private String lastName;
    private String suffixCd;
    private String addressLine1;
    private String addressLine2;
    private String city;
    private String state;
    private String countryCd;
    private String postalcode;
    private String phone;
    private String fax;
    private String institutionCd;
    private String institutionName;
    private String departmentCd;
    private String departmentName;
    private Date createdDate;
    private Date updatedDate;
    private Set<UserAddresses> userAddresseses = new HashSet<UserAddresses>(0);

    public Address() {
    }

    public Address(Integer addressId) {
        this.addressId = addressId;
    }

    public Address(Integer addressId, Users usersByCreatedBy,
            Users usersByUpdatedBy, String titleCd, String firstName,
            String lastName, String suffixCd, String addressLine1,
            String addressLine2, String city, String state, String countryCd,
            String postalcode, String phone, String fax, String institutionCd,
            String institutionName, String departmentCd, String departmentName,
            Date createdDate, Date updatedDate,
            Set<UserAddresses> userAddresseses) {
        this.addressId = addressId;
        this.usersByCreatedBy = usersByCreatedBy;
        this.usersByUpdatedBy = usersByUpdatedBy;
        this.titleCd = titleCd;
        this.firstName = firstName;
        this.lastName = lastName;
        this.suffixCd = suffixCd;
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
        this.city = city;
        this.state = state;
        this.countryCd = countryCd;
        this.postalcode = postalcode;
        this.phone = phone;
        this.fax = fax;
        this.institutionCd = institutionCd;
        this.institutionName = institutionName;
        this.departmentCd = departmentCd;
        this.departmentName = departmentName;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
        this.userAddresseses = userAddresseses;
    }

    @Id
    @Column(name = "ADDRESS_ID", unique = true, nullable = false, precision = 22, scale = 0)
    public Integer getAddressId() {
        return addressId;
    }

    public void setAddressId(Integer addressId) {
        this.addressId = addressId;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CREATED_BY")
    public Users getUsersByCreatedBy() {
        return usersByCreatedBy;
    }

    public void setUsersByCreatedBy(Users usersByCreatedBy) {
        this.usersByCreatedBy = usersByCreatedBy;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UPDATED_BY")
    public Users getUsersByUpdatedBy() {
        return usersByUpdatedBy;
    }

    public void setUsersByUpdatedBy(Users usersByUpdatedBy) {
        this.usersByUpdatedBy = usersByUpdatedBy;
    }

    @Column(name = "TITLE_CD", length = 50)
    public String getTitleCd() {
        return titleCd;
    }

    public void setTitleCd(String titleCd) {
        this.titleCd = titleCd;
    }

    @Column(name = "FIRST_NAME", length = 100)
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Column(name = "LAST_NAME", length = 100)
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Column(name = "SUFFIX_CD", length = 50)
    public String getSuffixCd() {
        return suffixCd;
    }

    public void setSuffixCd(String suffixCd) {
        this.suffixCd = suffixCd;
    }

    @Column(name = "ADDRESS_LINE_1", length = 200)
    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    @Column(name = "ADDRESS_LINE_2", length = 200)
    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    @Column(name = "CITY", length = 200)
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Column(name = "STATE", length = 250)
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Column(name = "COUNTRY_CD", length = 5)
    public String getCountryCd() {
        return countryCd;
    }

    public void setCountryCd(String countryCd) {
        this.countryCd = countryCd;
    }

    @Column(name = "POSTALCODE", length = 20)
    public String getPostalcode() {
        return postalcode;
    }

    public void setPostalcode(String postalcode) {
        this.postalcode = postalcode;
    }

    @Column(name = "PHONE", length = 60)
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Column(name = "FAX", length = 50)
    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    @Column(name = "INSTITUTION_CD", length = 10)
    public String getInstitutionCd() {
        return institutionCd;
    }

    public void setInstitutionCd(String institutionCd) {
        this.institutionCd = institutionCd;
    }

    @Column(name = "INSTITUTION_NAME", length = 100)
    public String getInstitutionName() {
        return institutionName;
    }

    public void setInstitutionName(String institutionName) {
        this.institutionName = institutionName;
    }

    @Column(name = "DEPARTMENT_CD", length = 10)
    public String getDepartmentCd() {
        return departmentCd;
    }

    public void setDepartmentCd(String departmentCd) {
        this.departmentCd = departmentCd;
    }

    @Column(name = "DEPARTMENT_NAME", length = 100)
    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    @Column(name = "CREATED_DATE")
    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    @Column(name = "UPDATED_DATE")
    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "address")
    public Set<UserAddresses> getUserAddresseses() {
        return userAddresseses;
    }

    public void setUserAddresseses(Set<UserAddresses> userAddresseses) {
        this.userAddresseses = userAddresseses;
    }

}
