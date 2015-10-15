package com.wiley.gr.ace.profile.model.participant;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * A single participant preference instance
 **/
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaClientCodegen", date = "2015-10-08T17:06:09.937Z")
public class Preference extends TransactionalObject  {
  
  private Object preferenceValue = null;
  private String createdBy = null;
  private Object links = null;
  private Date created = null;
  private Date modified = null;
  private Date modifiedBy = null;
  private String preferenceKey = null;

  
  /**
   **/
  @JsonProperty("preferenceValue")
  public Object getPreferenceValue() {
    return preferenceValue;
  }
  public void setPreferenceValue(Object preferenceValue) {
    this.preferenceValue = preferenceValue;
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
   **/
  @JsonProperty("_links")
  public Object getLinks() {
    return links;
  }
  public void setLinks(Object links) {
    this.links = links;
  }

  
  /**
   * When the object was added to the system. System-generated only.
   **/
  @JsonProperty("created")
  public Date getCreated() {
    return created;
  }
  public void setCreated(Date created) {
    this.created = created;
  }

  
  /**
   * When the object was last modified in the system. System-generated only.
   **/
  @JsonProperty("modified")
  public Date getModified() {
    return modified;
  }
  public void setModified(Date modified) {
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
   * The key that identifies the type of preference. The key includes the namespace.
   **/
  @JsonProperty("preferenceKey")
  public String getPreferenceKey() {
    return preferenceKey;
  }
  public void setPreferenceKey(String preferenceKey) {
    this.preferenceKey = preferenceKey;
  }
}
