package com.wiley.gr.ace.staticcontentservices.model.external;

import java.util.List;


/**
 * The Class StaticCatalogDotcmsResponse.
 */
public class StaticCatalogDotcmsResponse {
    
    /** The contentlets. */
    List<StaticCatalog> contentlets;

    /**
     * Gets the contentlets.
     *
     * @return the contentlets
     */
    public final List<StaticCatalog> getContentlets() {
        return contentlets;
    }

    /**
     * Sets the contentlets.
     *
     * @param contentlets the new contentlets
     */
    public final void setContentlets(List<StaticCatalog> contentlets) {
        this.contentlets = contentlets;
    }

}
