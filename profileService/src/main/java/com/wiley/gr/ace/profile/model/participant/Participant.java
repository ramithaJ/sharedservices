package com.wiley.gr.ace.profile.model.participant;

import java.util.*;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * Based on &#39;https://schema.org/Person&#39;
 **/
@JsonInclude(Include.NON_NULL)
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaClientCodegen", date = "2015-10-08T17:06:09.937Z")
public class Participant extends LocalizedObject  {
  
  private Object links = null;
  private String jobTitle = null;
  private String language = null;
  private String participantId = null;
  private String participantCountry = null;
  private String honorificSuffix = null;
  private String industryId = null;
  private String familyName = null;
  private List<String> openAccessAccounts = new ArrayList<String>();
  private String modified = null;
  private Date modifiedBy = null;
  private String state = null;
  private String recoveryEmail = null;
  private String email = null;
  private String created = null;
  private String givenName = null;
  private String honorificPrefix = null;
  private List<String> areasOfInterest = new ArrayList<String>();
  private String supersedingId = null;
  private String userName = null;
  private String userId = null;
  private List<Name> alternateNames = new ArrayList<Name>();
  private String createdBy = null;
  private List<String> alternateEmails = new ArrayList<String>();
  private String orcidId = null;
  private String name = null;
  private String personId = null;
  private String jobCategoryId = null;
  private String additionalName = null;

  
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
   * The participant's job title, such as Associate Professor. This is usually in the context of the participant's primary affiliation, and how the participant prefers to be referred to.
   **/
  @JsonProperty("jobTitle")
  public String getJobTitle() {
    return jobTitle;
  }
  public void setJobTitle(String jobTitle) {
    this.jobTitle = jobTitle;
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
   * The native identifier for the participant. System-generated only.
   **/
  @JsonProperty("participantId")
  public String getParticipantId() {
    return participantId;
  }
  public void setParticipantId(String participantId) {
    this.participantId = participantId;
  }

  
  /**
   * The country of the participant.
   **/
  @JsonProperty("participantCountry")
  public String getParticipantCountry() {
    return participantCountry;
  }
  public void setParticipantCountry(String participantCountry) {
    this.participantCountry = participantCountry;
  }

  
  /**
   * An honorific suffix following a participant's name such as M.D. /PhD/MSCSW
   **/
  @JsonProperty("honorificSuffix")
  public String getHonorificSuffix() {
    return honorificSuffix;
  }
  public void setHonorificSuffix(String honorificSuffix) {
    this.honorificSuffix = honorificSuffix;
  }

  
  /**
   * The Wiley industry identifier for the participant.\n** TODO confirm where these are sourced and maintained**
   **/
  @JsonProperty("industryId")
  public String getIndustryId() {
    return industryId;
  }
  public void setIndustryId(String industryId) {
    this.industryId = industryId;
  }

  
  /**
   * The participant's family name. In the U.S. this is also referred to as last name.
   **/
  @JsonProperty("familyName")
  public String getFamilyName() {
    return familyName;
  }
  public void setFamilyName(String familyName) {
    this.familyName = familyName;
  }

  
  /**
   * **TODO confirm the form of these and how they are used.**
   **/
  @JsonProperty("openAccessAccounts")
  public List<String> getOpenAccessAccounts() {
    return openAccessAccounts;
  }
  public void setOpenAccessAccounts(List<String> openAccessAccounts) {
    this.openAccessAccounts = openAccessAccounts;
  }

  
  /**
   * When the object was last modified in the system. System-generated only.
   **/
  @JsonProperty("modified")
  public String getModified() {
    return modified;
  }
  public void setModified(String modified) {
    this.modified = modified;
  }

  
  /**
   * The participant who modified the object. System-generated only.
   **/
  @JsonProperty("modifiedBy")
  public Date getModifiedBy() {
    return modifiedBy;
  }
  public void setModifiedBy(Date modifiedBy) {
    this.modifiedBy = modifiedBy;
  }

  
  /**
   **/
  @JsonProperty("state")
  public String getState() {
    return state;
  }
  public void setState(String state) {
    this.state = state;
  }

  
  /**
   * An alternate email address which can be used if the primary email address is unreachable.
   **/
  @JsonProperty("recoveryEmail")
  public String getRecoveryEmail() {
    return recoveryEmail;
  }
  public void setRecoveryEmail(String recoveryEmail) {
    this.recoveryEmail = recoveryEmail;
  }

  
  /**
   * The participant's primary email address.
   **/
  @JsonProperty("email")
  public String getEmail() {
    return email;
  }
  public void setEmail(String email) {
    this.email = email;
  }

  
  /**
   * When the object was added to the system. System-generated only.
   **/
  @JsonProperty("created")
  public String getCreated() {
    return created;
  }
  public void setCreated(String created) {
    this.created = created;
  }

  
  /**
   * The participant's given name. In the U.S. this is also referred to as first name.
   **/
  @JsonProperty("givenName")
  public String getGivenName() {
    return givenName;
  }
  public void setGivenName(String givenName) {
    this.givenName = givenName;
  }

  
  /**
   * An honorific prefix preceding the participant's name such as Dr/Mrs/Mr.
   **/
  @JsonProperty("honorificPrefix")
  public String getHonorificPrefix() {
    return honorificPrefix;
  }
  public void setHonorificPrefix(String honorificPrefix) {
    this.honorificPrefix = honorificPrefix;
  }

  
  /**
   * ** TODO should these just be codes? Are these Wiley subject codes or just marketing keywords? Ideally would be URIs in a standard taxonomy**
   **/
  @JsonProperty("areasOfInterest")
  public List<String> getAreasOfInterest() {
    return areasOfInterest;
  }
  public void setAreasOfInterest(List<String> areasOfInterest) {
    this.areasOfInterest = areasOfInterest;
  }

  
  /**
   * In the case of a superseded object, this points to the survivor
   **/
  @JsonProperty("supersedingId")
  public String getSupersedingId() {
    return supersedingId;
  }
  public void setSupersedingId(String supersedingId) {
    this.supersedingId = supersedingId;
  }

  
  /**
   * The user name. By default this is synchronized with `email`.
   **/
  @JsonProperty("userName")
  public String getUserName() {
    return userName;
  }
  public void setUserName(String userName) {
    this.userName = userName;
  }

  
  /**
   * The underlying user identifier for the participant
   **/
  @JsonProperty("userId")
  public String getUserId() {
    return userId;
  }
  public void setUserId(String userId) {
    this.userId = userId;
  }

  
  /**
   * Other names by which the participant is known. These may be used to aid disambiguation.
   **/
  @JsonProperty("alternateNames")
  public List<Name> getAlternateNames() {
    return alternateNames;
  }
  public void setAlternateNames(List<Name> alternateNames) {
    this.alternateNames = alternateNames;
  }

  
  /**
   * The participant who added the object. System-generated only.
   **/
  @JsonProperty("createdBy")
  public String getCreatedBy() {
    return createdBy;
  }
  public void setCreatedBy(String createdBy) {
    this.createdBy = createdBy;
  }

  
  /**
   * Other email addresses used by the participant. These may be used to aid in disambiguation.
   **/
  @JsonProperty("alternateEmails")
  public List<String> getAlternateEmails() {
    return alternateEmails;
  }
  public void setAlternateEmails(List<String> alternateEmails) {
    this.alternateEmails = alternateEmails;
  }

  
  /**
   * The participant's ORCID, as registered at http://orcid.org/.
   **/
  @JsonProperty("orcidId")
  public String getOrcidId() {
    return orcidId;
  }
  public void setOrcidId(String orcidId) {
    this.orcidId = orcidId;
  }

  
  /**
   * The participant's full name, in the form they prefer to be referred to. If not supplied this should be generated from givenName and lastName.
   **/
  @JsonProperty("name")
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }

  
  /**
   * The underlying person identifier for the participant
   **/
  @JsonProperty("personId")
  public String getPersonId() {
    return personId;
  }
  public void setPersonId(String personId) {
    this.personId = personId;
  }

  
  /**
   * The Wiley job category identifier for the participant.\n** TODO confirm where these are sourced and maintained**
   **/
  @JsonProperty("jobCategoryId")
  public String getJobCategoryId() {
    return jobCategoryId;
  }
  public void setJobCategoryId(String jobCategoryId) {
    this.jobCategoryId = jobCategoryId;
  }

  
  /**
   * An additional name for a participant, can be used for a middle name. Multiple middle names may be specified separated by spaces.
   **/
  @JsonProperty("additionalName")
  public String getAdditionalName() {
    return additionalName;
  }
  public void setAdditionalName(String additionalName) {
    this.additionalName = additionalName;
  }
}
