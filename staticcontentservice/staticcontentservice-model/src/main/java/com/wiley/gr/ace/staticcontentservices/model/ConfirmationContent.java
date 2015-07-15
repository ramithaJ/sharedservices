package com.wiley.gr.ace.staticcontentservices.model;

import java.util.HashMap;



/**
 * The Class ConfirmationContent.
 */
public class ConfirmationContent {

    /** The content tiltle. */
    private String contentTiltle;
    
    
	/**
	 * Gets the content tiltle.
	 *
	 * @return the content tiltle
	 */
	public final String getContentTiltle() {
        return contentTiltle;
    }

    /**
     * Sets the content tiltle.
     *
     * @param contentTiltle the new content tiltle
     */
    public final void setContentTiltle(String contentTiltle) {
        this.contentTiltle = contentTiltle;
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
