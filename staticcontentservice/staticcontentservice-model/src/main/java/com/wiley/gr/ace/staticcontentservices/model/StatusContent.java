package com.wiley.gr.ace.staticcontentservices.model;

import java.util.HashMap;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;



/**
 * The Class ConfirmationContent.
 */
@JsonInclude(Include.NON_NULL)
public class StatusContent {

    /** The content title. */
    private String contentTitle;

    /** The status messageType. */
    private String statusMessageType;
    
    /** The locale. */
    private String locale;
    
    private HashMap<String, String> actionLables;
    
    

    /** The confirmation messages. */
    private HashMap<String, String> statusMessages;
    
        
    
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
     * @param contentTitle the new content title
     */
    public final void setContentTitle(String contentTitle) {
        this.contentTitle = contentTitle;
    }

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
     * @param statusMessageType the new status message type
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

    
    public final HashMap<String, String> getActionLables() {
        return actionLables;
    }

    public final void setActionLables(HashMap<String, String> actionLables) {
        this.actionLables = actionLables;
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
     * @param statusMessages the status messages
     */
    public final void setStatusMessages(HashMap<String, String> statusMessages) {
        this.statusMessages = statusMessages;
    }

}
