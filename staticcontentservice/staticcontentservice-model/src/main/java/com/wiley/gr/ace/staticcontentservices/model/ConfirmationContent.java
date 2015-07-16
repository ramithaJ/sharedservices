package com.wiley.gr.ace.staticcontentservices.model;

import java.util.HashMap;



/**
 * The Class ConfirmationContent.
 */
public class ConfirmationContent {

    /** The content title. */
    private String contentTitle;
    
    
	/**
	 * Gets the content title.
	 *
	 * @return the content title
	 */
	public final String getContentTitle() {
        return contentTitle;
    }

    /**
     * Sets the content title.
     *
     * @param contentTiltle the new content title
     */
    public final void setContentTiltle(String contentTitle) {
        this.contentTitle = contentTitle;
    }

    /** The module name. */
	private String moduleName;
	
	/** The locale. */
	private String locale;
	
	/** The confirmation messages. */
	private HashMap<String, String> confirmationMessages;
	
		

	/**
	 * Gets the module name.
	 *
	 * @return the module name
	 */
	public final String getModuleName() {
		return moduleName;
	}

	/**
	 * Sets the page name.
	 *
	 * @param moduleName the new module name
	 */
	public final void setModuleName(String moduleName) {
		this.moduleName = moduleName;
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
	 * Gets the confirmation messages.
	 *
	 * @return the confirmation messages
	 */
	public final HashMap<String, String> getConfirmationMessages() {
		return confirmationMessages;
	}

	/**
	 * Sets the confirmation messages.
	 *
	 * @param confirmationMessages the messages
	 */
	public final void setConfirmationMessages(HashMap<String, String> confirmationMessages) {
		this.confirmationMessages = confirmationMessages;
	}

}
