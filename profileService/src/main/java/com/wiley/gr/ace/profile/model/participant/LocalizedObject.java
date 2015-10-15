package com.wiley.gr.ace.profile.model.participant;

import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * Use to qualify localized data (default is &#39;en&#39;); language preferences should be specified with the appropriate HTTP header
 **/
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaClientCodegen", date = "2015-10-08T17:06:09.937Z")
public class LocalizedObject   {
  
  private String language = null;

  
  /**
   **/
  @JsonProperty("language")
  public String getLanguage() {
    return language;
  }
  public void setLanguage(String language) {
    this.language = language;
  }

}
