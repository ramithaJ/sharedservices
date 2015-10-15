package com.wiley.gr.ace.profile.model.participant;

import java.util.*;

import com.fasterxml.jackson.annotation.JsonProperty;


@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaClientCodegen", date = "2015-10-08T17:06:09.937Z")
public class AddressArray extends ArrayObject  {
  
  private Object links = null;
  private List<Address> content = new ArrayList<Address>();

  
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
   **/
  @JsonProperty("content")
  public List<Address> getContent() {
    return content;
  }
  public void setContent(List<Address> content) {
    this.content = content;
  }

}
