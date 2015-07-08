package com.wiley.gr.ace.staticcontentservices.model;

import java.util.HashMap;

public class UIMessageContent {

	private String pageName;
	private String locale;
	private HashMap<String, String> errorMessages;
	private HashMap<String, String> uiLabelMessages;
	private HashMap<String, String> inlineHelp;

	public final String getPageName() {
		return pageName;
	}

	public final void setPageName(String pageName) {
		this.pageName = pageName;
	}

	public final String getLocale() {
		return locale;
	}

	public final void setLocale(String locale) {
		this.locale = locale;
	}

	public final HashMap<String, String> getErrorMessages() {
		return errorMessages;
	}

	public final HashMap<String, String> getUiLabelMessages() {
		return uiLabelMessages;
	}

	public final void setUiLabelMessages(HashMap<String, String> uiLabelMessages) {
		this.uiLabelMessages = uiLabelMessages;
	}

	public final HashMap<String, String> getInlineHelp() {
		return inlineHelp;
	}

	public final void setInlineHelp(HashMap<String, String> inlineHelp) {
		this.inlineHelp = inlineHelp;
	}

	public final void setErrorMessages(HashMap<String, String> errorMessages) {
		this.errorMessages = errorMessages;
	}

}
