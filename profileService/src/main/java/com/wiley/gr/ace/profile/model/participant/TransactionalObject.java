package com.wiley.gr.ace.profile.model.participant;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * Use to qualify objects that can be transacted through the API
 **/
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaClientCodegen", date = "2015-10-08T17:06:09.937Z")
public class TransactionalObject   {
  
  private Date created = null;
  private String createdBy = null;
  private Date modified = null;
  private Date modifiedBy = null;

  
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

}
