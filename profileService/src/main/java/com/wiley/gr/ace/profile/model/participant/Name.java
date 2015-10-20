package com.wiley.gr.ace.profile.model.participant;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;


/**
 * A container for localized, contextualized names
 **/
@JsonInclude(Include.NON_NULL)
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaClientCodegen", date = "2015-10-08T17:06:09.937Z")
public class Name   {
  
  private String nameType = null;
  private String language = null;
  private String value = null;

  
  /**
   * The type of name
   **/
  @JsonProperty("nameType")
  public String getNameType() {
    return nameType;
  }
  public void setNameType(String nameType) {
    this.nameType = nameType;
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
   **/
  @JsonProperty("value")
  public String getValue() {
    return value;
  }
  public void setValue(String value) {
    this.value = value;
  }

}
