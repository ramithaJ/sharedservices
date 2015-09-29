package com.wiley.gr.ace.staticcontentservices.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;


/**
 * The Class StaticContent.
 */
@JsonInclude(Include.NON_NULL)
public class StaticContent {
    
    
    /** The content title. */
    private String contentTitle;
    
    /** The page name. */
    private String pageName;
    
    /** The locale. */
    private String locale;
    
    private String adBlockBody;
    
    
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

    public final String getAdBlockBody() {
        return adBlockBody;
    }

    public final void setAdBlockBody(String adBlockBody) {
        this.adBlockBody = adBlockBody;
    }
    
    

}
