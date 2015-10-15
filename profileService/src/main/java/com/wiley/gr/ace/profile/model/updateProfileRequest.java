package com.wiley.gr.ace.profile.model;

import java.io.Serializable;




public class updateProfileRequest{
	
	private EntityValue entityValue;
	
	private String sourceSystem;
	private String entityId;
	private String entityType;
	
	
	
	public EntityValue getEntityValue() {
		return entityValue;
	}
	public void setEntityValue(EntityValue entityValue) {
		this.entityValue = entityValue;
	}
	public String getEntityType() {
		return entityType;
	}
	public void setEntityType(String entityType) {
		this.entityType = entityType;
	}
	
	public String getSourceSystem() {
		return sourceSystem;
	}
	public void setSourceSystem(String sourceSystem) {
		this.sourceSystem = sourceSystem;
	}
	public String getEntityId() {
		return entityId;
	}
	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}

}
