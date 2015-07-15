package com.wiley.gr.ace.staticcontentservices.model.external;

import java.util.List;



/**
 * The Class UIMessageCatalogDotcmsResponse.
 */
public class StatusCatalogDotcmsResponse {

    /** The contentlets. */
    List<StatusCatalog> contentlets;

    /**
     * Gets the contentlets.
     *
     * @return the contentlets
     */
    public final List<StatusCatalog> getContentlets() {
        return contentlets;
    }

    /**
     * Sets the contentlets.
     *
     * @param contentlets the new contentlets
     */
    public final void setContentlets(List<StatusCatalog> contentlets) {
        this.contentlets = contentlets;
    }
}
