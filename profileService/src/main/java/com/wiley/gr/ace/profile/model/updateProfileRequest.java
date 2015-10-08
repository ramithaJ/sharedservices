package com.wiley.gr.ace.profile.model;




public class updateProfileRequest {
	
	private Object entityValue;
	
	private String sourceSystem;
	private String entityId;
	private String entityType;
	
	
	
	public Object getEntityValue() {
		return entityValue;
	}
	public void setEntityValue(Object entityValue) {
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
