package com.wiley.gr.ace.authorservices.model;



/**
 * The Class ScheduleTemplateObj.
 */
public class ScheduleTemplateObj {
	
	/** The schedule id. */
	private String scheduleId;
	
	/** The template by onscreen tmpl id. */
	private String templateByOnscreenTmplId;
	
	/** The template by email tmpl id. */
	private String templateByEmailTmplId;
	
	/** The delay. */
	private Integer delay;
	
	/**
	 * Gets the schedule id.
	 *
	 * @return the schedule id
	 */
	public String getScheduleId() {
		return scheduleId;
	}
	
	/**
	 * Sets the schedule id.
	 *
	 * @param scheduleId the new schedule id
	 */
	public void setScheduleId(String scheduleId) {
		this.scheduleId = scheduleId;
	}
	
	/**
	 * Gets the template by onscreen tmpl id.
	 *
	 * @return the template by onscreen tmpl id
	 */
	public String getTemplateByOnscreenTmplId() {
		return templateByOnscreenTmplId;
	}
	
	/**
	 * Sets the template by onscreen tmpl id.
	 *
	 * @param templateByOnscreenTmplId the new template by onscreen tmpl id
	 */
	public void setTemplateByOnscreenTmplId(String templateByOnscreenTmplId) {
		this.templateByOnscreenTmplId = templateByOnscreenTmplId;
	}
	
	/**
	 * Gets the template by email tmpl id.
	 *
	 * @return the template by email tmpl id
	 */
	public String getTemplateByEmailTmplId() {
		return templateByEmailTmplId;
	}
	
	/**
	 * Sets the template by email tmpl id.
	 *
	 * @param templateByEmailTmplId the new template by email tmpl id
	 */
	public void setTemplateByEmailTmplId(String templateByEmailTmplId) {
		this.templateByEmailTmplId = templateByEmailTmplId;
	}
	
	/**
	 * Gets the delay.
	 *
	 * @return the delay
	 */
	public Integer getDelay() {
		return delay;
	}
	
	/**
	 * Sets the delay.
	 *
	 * @param delay the new delay
	 */
	public void setDelay(Integer delay) {
		this.delay = delay;
	}

}
