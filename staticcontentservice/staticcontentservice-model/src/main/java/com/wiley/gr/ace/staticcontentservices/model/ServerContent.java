package com.wiley.gr.ace.staticcontentservices.model;

import java.util.HashMap;

// TODO: Auto-generated Javadoc
/**
 * The Class ServerContent.
 */
public class ServerContent {
    
    /** The content title. */
    private String contentTitle;
    
    
    /** The module name. */
    private String moduleName;
    
    /** The locale. */
    private String locale;
    
    /** The server messages. */
    private HashMap<String, String> serverMessages;

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
     * Gets the module name.
     *
     * @return the module name
     */
    public final String getModuleName() {
        return moduleName;
    }

    /**
     * Sets the module name.
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
     * Gets the server messages.
     *
     * @return the server messages
     */
    public final HashMap<String, String> getServerMessages() {
        return serverMessages;
    }

    /**
     * Sets the server messages.
     *
     * @param serverMessages the server messages
     */
    public final void setServerMessages(HashMap<String, String> serverMessages) {
        this.serverMessages = serverMessages;
    }

}
