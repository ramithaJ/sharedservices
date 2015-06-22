package com.wiley.gr.ace.authorservices.model;


public class ScheduleTemplateObj {
	private String scheduleId;
	private String templateByOnscreenTmplId;
	private String templateByEmailTmplId;
	private Integer delay;
	public String getScheduleId() {
		return scheduleId;
	}
	public void setScheduleId(String scheduleId) {
		this.scheduleId = scheduleId;
	}
	
	public String getTemplateByOnscreenTmplId() {
		return templateByOnscreenTmplId;
	}
	public void setTemplateByOnscreenTmplId(String templateByOnscreenTmplId) {
		this.templateByOnscreenTmplId = templateByOnscreenTmplId;
	}
	public String getTemplateByEmailTmplId() {
		return templateByEmailTmplId;
	}
	public void setTemplateByEmailTmplId(String templateByEmailTmplId) {
		this.templateByEmailTmplId = templateByEmailTmplId;
	}
	public Integer getDelay() {
		return delay;
	}
	public void setDelay(Integer delay) {
		this.delay = delay;
	}

}
