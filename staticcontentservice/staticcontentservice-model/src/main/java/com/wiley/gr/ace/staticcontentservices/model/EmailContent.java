package com.wiley.gr.ace.staticcontentservices.model;

import java.util.HashMap;


/**
 * The Class EmailContent.
 */
public class EmailContent {

    /** The content title. */
    private String contentTitle;

    /** The survey link. */
    private HashMap<String, String> surveyLink;
    

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
     * Gets the survey link.
     *
     * @return the survey link
     */
    public final HashMap<String, String> getSurveyLink() {
        return surveyLink;
    }

  
    /**
     * Sets the survey link.
     *
     * @param surveyLink the survey link
     */
    public final void setSurveyLink(HashMap<String, String> surveyLink) {
        this.surveyLink = surveyLink;
    }
    
}
