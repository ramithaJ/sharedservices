package com.wiley.gr.ace.profile.model.participant;

import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * Use to qualify objects that can have multiple states
 **/
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaClientCodegen", date = "2015-10-08T17:06:09.937Z")
public class StatefulObject   {
  
  private State state = null;
  private String supersedingId = null;

  
  /**
   **/
  @JsonProperty("state")
  public State getState() {
    return state;
  }
  public void setState(State state) {
    this.state = state;
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

}
