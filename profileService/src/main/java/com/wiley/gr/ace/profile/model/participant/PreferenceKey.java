package com.wiley.gr.ace.profile.model.participant;
import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * A single preference key with qualifying metadata
 **/
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaClientCodegen", date = "2015-10-08T17:06:09.937Z")
public class PreferenceKey   {
  
  private String name = null;
  private String ownerId = null;

  
  /**
   * The key that identifies the type of preference. The key includes the namespace.
   **/
  @JsonProperty("name")
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }

  
  /**
   * The participant that initially created the preference
   **/
  @JsonProperty("ownerId")
  public String getOwnerId() {
    return ownerId;
  }
  public void setOwnerId(String ownerId) {
    this.ownerId = ownerId;
  }

}
