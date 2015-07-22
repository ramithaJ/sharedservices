package com.wiley.gr.ace.staticcontentservices.model.external;

import java.util.List;

/**
 * The Class ServerCatalogDotcmsResponse.
 */
public class ServerCatalogDotcmsResponse {
    
    /** The contentlets. */
    List<ServerCatalog> contentlets;

    /**
     * Gets the contentlets.
     *
     * @return the contentlets
     */
    public final List<ServerCatalog> getContentlets() {
        return contentlets;
    }

    /**
     * Sets the contentlets.
     *
     * @param contentlets the new contentlets
     */
    public final void setContentlets(List<ServerCatalog> contentlets) {
        this.contentlets = contentlets;
    }

}
