package com.wiley.gr.ace.staticcontentservices.model;

import java.util.HashMap;

//import com.wiley.gr.ace.staticcontentservices.model.external.ServerCatalog;


/**
 * The Class UIMessageContent.
 */
public class UIMessageContent {

	/** The page name. */
	private String pageName;
	
	/** The locale. */
	private String locale;

    /** The error messages. */
	private HashMap<String, String> errorMessages;
	
	/** The ui label messages. */
	private HashMap<String, String> uiLabelMessages;
	
	/** The inline help. */
	private HashMap<String, String> inlineHelp;

	/** The server content. */
	private ServerContent serverContent;
	
	private String uniqueKey;
	
    public String getUniqueKey() {
        return uniqueKey;
    }

    public void setUniqueKey(String uniqueKey) {
        this.uniqueKey = uniqueKey;
    }
	
	/**
	 * Gets the page name.
	 *
	 * @return the page name
	 */
	public final String getPageName() {
		return pageName;
	}

	/**
	 * Sets the page name.
	 *
	 * @param pageName the new page name
	 */
	public final void setPageName(String pageName) {
		this.pageName = pageName;
	}

	/**
	 * Gets the locale.
	 *
	 * @return the locale
	 */
	public final String getLocale() {
		return locale;
	}

	/**
	 * Sets the locale.
	 *
	 * @param locale the new locale
	 */
	public final void setLocale(String locale) {
		this.locale = locale;
	}

	/**
	 * Gets the error messages.
	 *
	 * @return the error messages
	 */
	public final HashMap<String, String> getErrorMessages() {
		return errorMessages;
	}

	/**
	 * Gets the ui label messages.
	 *
	 * @return the ui label messages
	 */
	public final HashMap<String, String> getUiLabelMessages() {
		return uiLabelMessages;
	}

	/**
	 * Sets the ui label messages.
	 *
	 * @param uiLabelMessages the ui label messages
	 */
	public final void setUiLabelMessages(HashMap<String, String> uiLabelMessages) {
		this.uiLabelMessages = uiLabelMessages;
	}

	/**
	 * Gets the inline help.
	 *
	 * @return the inline help
	 */
	public final HashMap<String, String> getInlineHelp() {
		return inlineHelp;
	}

	/**
	 * Sets the inline help.
	 *
	 * @param inlineHelp the inline help
	 */
	public final void setInlineHelp(HashMap<String, String> inlineHelp) {
		this.inlineHelp = inlineHelp;
	}

	/**
	 * Sets the error messages.
	 *
	 * @param errorMessages the error messages
	 */
	public final void setErrorMessages(HashMap<String, String> errorMessages) {
		this.errorMessages = errorMessages;
	}

	/**
	 * Gets the server content.
	 *
	 * @return the server content
	 */
	public final ServerContent getServerContent() {
		return serverContent;
	}

	/**
	 * Sets the server content.
	 *
	 * @param serverContent the new server content
	 */
	public final void setServerContent(ServerContent serverContent) {
		this.serverContent = serverContent;
	}


}
