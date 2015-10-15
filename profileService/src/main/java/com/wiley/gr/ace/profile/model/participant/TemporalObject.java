package com.wiley.gr.ace.profile.model.participant;

import java.util.Date;



import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * Use to qualify objects that have a temporal aspect
 **/
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaClientCodegen", date = "2015-10-08T17:06:09.937Z")
public class TemporalObject   {
  
  private Date validFrom = null;
  private Date validTo = null;

  
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
