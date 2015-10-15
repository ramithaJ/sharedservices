package com.wiley.gr.ace.profile.model.participant;

import java.util.*;
import java.util.Date;



import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * Based on &#39;https://schema.org/PostalAddress&#39;\nThis model is intended to reflect addresses that personally apply to the participant. \n** TODO should consider non-postal addresses **\n** TODO Once polymorphism supported in main swagger branch, implement BillingAddress with name override. **
 **/
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaClientCodegen", date = "2015-10-08T17:06:09.937Z")
public class Address extends LocalizedObject  {
  
  private String addressCountry = null;
  private String website = null;
  private Object links = null;
  private String addressType = null;
  private String postalCode = null;
  private String departmentId = null;
  private String language = null;
  private String telephone = null;
  private Date validFrom = null;
  private String addressId = null;
  private String organizationId = null;
  private List<String> streetAddress = new ArrayList<String>();
  private String organization = null;
  private String name = null;
  private String faxNumber = null;
  private String addressLocality = null;
  private String addressFunction = null;
  private String addressRegion = null;
  private String department = null;
  private String email = null;
  private Date validTo = null;

  
  /**
   * The country of the address.
   **/
  @JsonProperty("addressCountry")
  public String getAddressCountry() {
    return addressCountry;
  }
  public void setAddressCountry(String addressCountry) {
    this.addressCountry = addressCountry;
  }

  
  /**
   * The website associated with this address for the participant, such as the participant's home page at their institution.
   **/
  @JsonProperty("website")
  public String getWebsite() {
    return website;
  }
  public void setWebsite(String website) {
    this.website = website;
  }

  
  /**
   **/
  @JsonProperty("_links")
  public Object getLinks() {
    return links;
  }
  public void setLinks(Object links) {
    this.links = links;
  }

  
  /**
   * The type of address, for example, Home, Office, Correspondence, Billing.
   **/
  @JsonProperty("addressType")
  public String getAddressType() {
    return addressType;
  }
  public void setAddressType(String addressType) {
    this.addressType = addressType;
  }

  
  /**
   * The postal code (ZIP in the U.S.) for the address.
   **/
  @JsonProperty("postalCode")
  public String getPostalCode() {
    return postalCode;
  }
  public void setPostalCode(String postalCode) {
    this.postalCode = postalCode;
  }

  
  /**
   * The departmental organization id associated with this address.
   **/
  @JsonProperty("departmentId")
  public String getDepartmentId() {
    return departmentId;
  }
  public void setDepartmentId(String departmentId) {
    this.departmentId = departmentId;
  }

  
  /**
   **/
  @JsonProperty("language")
  public String getLanguage() {
    return language;
  }
  public void setLanguage(String language) {
    this.language = language;
  }

  
  /**
   * The phone number associated with this address for the participant.
   **/
  @JsonProperty("telephone")
  public String getTelephone() {
    return telephone;
  }
  public void setTelephone(String telephone) {
    this.telephone = telephone;
  }

  
  /**
   * When the object became active in the real world.
   **/
  @JsonProperty("validFrom")
  public Date getValidFrom() {
    return validFrom;
  }
  public void setValidFrom(Date validFrom) {
    this.validFrom = validFrom;
  }

  
  /**
   * System-generated identifier for this relationship instance.
   **/
  @JsonProperty("addressId")
  public String getAddressId() {
    return addressId;
  }
  public void setAddressId(String addressId) {
    this.addressId = addressId;
  }

  
  /**
   * The institutional organization id associated with this address.
   **/
  @JsonProperty("organizationId")
  public String getOrganizationId() {
    return organizationId;
  }
  public void setOrganizationId(String organizationId) {
    this.organizationId = organizationId;
  }

  
  /**
   * In order, the lines of the street address.
   **/
  @JsonProperty("streetAddress")
  public List<String> getStreetAddress() {
    return streetAddress;
  }
  public void setStreetAddress(List<String> streetAddress) {
    this.streetAddress = streetAddress;
  }

  
  /**
   * Convenience name of the organization solely in the context of this address and localization
   **/
  @JsonProperty("organization")
  public String getOrganization() {
    return organization;
  }
  public void setOrganization(String organization) {
    this.organization = organization;
  }

  
  /**
   * Convenience name for the participant solely in the context of this address and localization. For example, billing may be to a different name. If not present, the participant's name is used.
   **/
  @JsonProperty("name")
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }

  
  /**
   * The fax number associated with this address for the participant.
   **/
  @JsonProperty("faxNumber")
  public String getFaxNumber() {
    return faxNumber;
  }
  public void setFaxNumber(String faxNumber) {
    this.faxNumber = faxNumber;
  }

  
  /**
   * The town or city of the address.
   **/
  @JsonProperty("addressLocality")
  public String getAddressLocality() {
    return addressLocality;
  }
  public void setAddressLocality(String addressLocality) {
    this.addressLocality = addressLocality;
  }

  
  /**
   * The function of the address in the context of the participant. For example, Primary, Alternate. There can only be one primary address.
   **/
  @JsonProperty("addressFunction")
  public String getAddressFunction() {
    return addressFunction;
  }
  public void setAddressFunction(String addressFunction) {
    this.addressFunction = addressFunction;
  }

  
  /**
   * The region, or state of the address.
   **/
  @JsonProperty("addressRegion")
  public String getAddressRegion() {
    return addressRegion;
  }
  public void setAddressRegion(String addressRegion) {
    this.addressRegion = addressRegion;
  }

  
  /**
   * Convenience name of the department solely in the context of this address and localization
   **/
  @JsonProperty("department")
  public String getDepartment() {
    return department;
  }
  public void setDepartment(String department) {
    this.department = department;
  }

  
  /**
   * The email address associated with this address for the participant.
   **/
  @JsonProperty("email")
  public String getEmail() {
    return email;
  }
  public void setEmail(String email) {
    this.email = email;
  }

  
  /**
   * When the object ceased to be active in the real world.
   **/
  @JsonProperty("validTo")
  public Date getValidTo() {
    return validTo;
  }
  public void setValidTo(Date validTo) {
    this.validTo = validTo;
  }
}
