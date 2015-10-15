package com.wiley.gr.ace.profile.model.participant;




import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * A minimal participant object. This is used when returning arrays, with the &#39;self&#39; link pointing to the full object, and includes sufficient information to display in a user interface.
 **/
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaClientCodegen", date = "2015-10-08T17:06:09.937Z")
public class ParticipantStub extends SelfObject  {
  
  private String participantId = null;
  private String name = null;
  private String personId = null;
  private String userName = null;
  private Object links = null;
  private String userId = null;

  
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
   * The participant's full name, in the form they prefer to be referred to. Unless overridden by the participant, this is generated from givenName and lastName.
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
   **/
  @JsonProperty("_links")
  public Object getLinks() {
    return links;
  }
  public void setLinks(Object links) {
    this.links = links;
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

}
