package com.wiley.gr.ace.authorservices.model;

public class NotificationDetails {

	private String from;
	private String to;
	private TemplateDetails templateDetails;

	public final String getFrom() {
		return from;
	}

	public final void setFrom(final String from) {
		this.from = from;
	}

	public final String getTo() {
		return to;
	}

	public final void setTo(final String to) {
		this.to = to;
	}

	public final TemplateDetails getTemplateDetails() {
		return templateDetails;
	}

	public final void setTemplateDetails(final TemplateDetails templateDetails) {
		this.templateDetails = templateDetails;
	}

}
