package com.wiley.gr.ace.profile.model.participant;

import java.util.*;
import java.util.Date;



import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * A participant&#39;s relationship to an entity in a specific role.
 **/
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaClientCodegen", date = "2015-10-08T17:06:09.937Z")
public class Relationship extends TransactionalObject  {
  
  private Object links = null;
  private Date created = null;
  private String roleId = null;
  private String entityId = null;
  private Date validFrom = null;
  private String relationshipId = null;
  private String participantId = null;
  private String createdBy = null;
  private Date modified = null;
  private Date modifiedBy = null;
  private String entityTypeId = null;
  private List<RelationshipAttribute> attribute = new ArrayList<RelationshipAttribute>();
  private Date validTo = null;

  
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
   * The role of the participant in the relationship. (In other words, the relationship type in the direction `Participant` -> `Entity`.
   **/
  @JsonProperty("roleId")
  public String getRoleId() {
    return roleId;
  }
  public void setRoleId(String roleId) {
    this.roleId = roleId;
  }

  
  /**
   * The entity related to by the participant in the specified role.
   **/
  @JsonProperty("entityId")
  public String getEntityId() {
    return entityId;
  }
  public void setEntityId(String entityId) {
    this.entityId = entityId;
  }

  
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
   * System-generated identifier for this relationship instance.
   **/
  @JsonProperty("relationshipId")
  public String getRelationshipId() {
    return relationshipId;
  }
  public void setRelationshipId(String relationshipId) {
    this.relationshipId = relationshipId;
  }

  
  /**
   * The participant acting in the specific role as they relate to the specified entity.
   **/
  @JsonProperty("participantId")
  public String getParticipantId() {
    return participantId;
  }
  public void setParticipantId(String participantId) {
    this.participantId = participantId;
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

  
  /**
   * The underlying type of the entity. `EntityId`s are guaranteed to be unique across Wiley, so this is not required for uniqueness, but some relationships may be reused across entity types, and this allows disambiguation.
   **/
  @JsonProperty("entityTypeId")
  public String getEntityTypeId() {
    return entityTypeId;
  }
  public void setEntityTypeId(String entityTypeId) {
    this.entityTypeId = entityTypeId;
  }

  
  /**
   * Additional attributes pertinent to the relationship between participant and entity.
   **/
  @JsonProperty("attribute")
  public List<RelationshipAttribute> getAttribute() {
    return attribute;
  }
  public void setAttribute(List<RelationshipAttribute> attribute) {
    this.attribute = attribute;
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
