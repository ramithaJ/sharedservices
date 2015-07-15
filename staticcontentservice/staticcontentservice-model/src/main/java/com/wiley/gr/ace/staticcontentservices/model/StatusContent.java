package com.wiley.gr.ace.staticcontentservices.model;

import java.util.HashMap;


/**
 * The Class ConfirmationContent.
 */
public class StatusContent {

    /** The page name. */
    private String statusMessageType;
    
    /** The locale. */
    private String locale;
    
    /** The confirmation messages. */
    private HashMap<String, String> statusMessages;
    
        

    /**
     * Gets the status message.
     *
     * @return the status message
     */
    public final String getStatusMessageType() {
        return statusMessageType;
    }

    /**
     * Sets the page name.
     *
     * @param pageName the new page name
     */
    public final void setStatusMessageType(String statusMessageType) {
        this.statusMessageType = statusMessageType;
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
    public final HashMap<String, String> getStatusMessages() {
        return statusMessages;
    }

    /**
     * Sets the confirmation messages.
     *
     * @param confirmationMessages the messages
     */
    public final void setStatusMessages(HashMap<String, String> statusMessages) {
        this.statusMessages = statusMessages;
    }

}
