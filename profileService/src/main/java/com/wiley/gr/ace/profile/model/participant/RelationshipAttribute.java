package com.wiley.gr.ace.profile.model.participant;


import com.fasterxml.jackson.annotation.JsonProperty;


@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaClientCodegen", date = "2015-10-08T17:06:09.937Z")
public class RelationshipAttribute   {
  
  private String attributeKey = null;
  private Object attributeValue = null;

  
  /**
   * The key to identify the attribute type.
   **/
  @JsonProperty("attributeKey")
  public String getAttributeKey() {
    return attributeKey;
  }
  public void setAttributeKey(String attributeKey) {
    this.attributeKey = attributeKey;
  }

  
  /**
   **/
  @JsonProperty("attributeValue")
  public Object getAttributeValue() {
    return attributeValue;
  }
  public void setAttributeValue(Object attributeValue) {
    this.attributeValue = attributeValue;
  }

}
