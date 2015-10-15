package com.wiley.gr.ace.profile.model.participant;



import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * A HATEAOS-compliant self-referencing link.
 **/
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaClientCodegen", date = "2015-10-08T17:06:09.937Z")
public class SelfObject   {
  
  private Object links = null;

  
  @JsonProperty("_links")
  public Object getLinks() {
    return links;
  }
  public void setLinks(Object links) {
    this.links = links;
  }

}
