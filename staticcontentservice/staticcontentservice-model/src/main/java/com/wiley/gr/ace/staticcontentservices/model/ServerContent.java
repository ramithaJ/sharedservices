package com.wiley.gr.ace.staticcontentservices.model;

import java.util.HashMap;

public class ServerContent {
    
    private String contentTitle;
    
    
    /** The module name. */
    private String moduleName;
    
    /** The locale. */
    private String locale;
    
    private HashMap<String, String> serverMessages;

    public final String getContentTitle() {
        return contentTitle;
    }

    public final void setContentTitle(String contentTitle) {
        this.contentTitle = contentTitle;
    }

    public final String getModuleName() {
        return moduleName;
    }

    public final void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public final String getLocale() {
        return locale;
    }

    public final void setLocale(String locale) {
        this.locale = locale;
    }

    public final HashMap<String, String> getServerMessages() {
        return serverMessages;
    }

    public final void setServerMessages(HashMap<String, String> serverMessages) {
        this.serverMessages = serverMessages;
    }

}
