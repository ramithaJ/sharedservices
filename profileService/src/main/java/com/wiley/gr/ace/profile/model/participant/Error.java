package com.wiley.gr.ace.profile.model.participant;




import com.fasterxml.jackson.annotation.JsonProperty;


@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaClientCodegen", date = "2015-10-08T17:06:09.937Z")
public class Error   {
  
  private Integer code = null;
  private String message = null;
  private String fields = null;

  
  /**
   **/
  @JsonProperty("code")
  public Integer getCode() {
    return code;
  }
  public void setCode(Integer code) {
    this.code = code;
  }

  
  /**
   **/
  @JsonProperty("message")
  public String getMessage() {
    return message;
  }
  public void setMessage(String message) {
    this.message = message;
  }

  
  /**
   **/
  @JsonProperty("fields")
  public String getFields() {
    return fields;
  }
  public void setFields(String fields) {
    this.fields = fields;
  }

   
}
