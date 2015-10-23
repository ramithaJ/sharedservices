/**
 * ****************************************************************************
 * Copyright (c) 2015 John Wiley & Sons, Inc. All rights reserved.
 * <p>
 * All material contained herein is proprietary to John Wiley & Sons
 * and its third party suppliers, if any. The methods, techniques and
 * technical concepts contained herein are considered trade secrets
 * and confidential and may be protected by intellectual property laws.
 * Reproduction or distribution of this material, in whole or in part,
 * is strictly forbidden except by express prior written permission
 * of John Wiley & Sons.
 * *****************************************************************************
 */
package com.wiley.gr.ace.search.model;

import java.io.Serializable;
import java.util.List;

/**
 * The Class TopBarResponse.
 */
public class TopBarResponse implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The total. */
    private long journalTotal;

    /** The article total. */
    private long articleTotal;

    /** The hits. */
    private List<Hits> hits;

    /**
     * Gets the journal total.
     *
     * @return the journal total
     */
    public long getJournalTotal() {
        return journalTotal;
    }

    /**
     * Sets the journal total.
     *
     * @param journalTotal
     *            the new journal total
     */
    public void setJournalTotal(long journalTotal) {
        this.journalTotal = journalTotal;
    }

    /**
     * Gets the article total.
     *
     * @return the article total
     */
    public long getArticleTotal() {
        return articleTotal;
    }

    /**
     * Sets the article total.
     *
     * @param articleTotal
     *            the new article total
     */
    public void setArticleTotal(long articleTotal) {
        this.articleTotal = articleTotal;
    }

    /**
     * Gets the hits.
     *
     * @return the hits
     */
    public List<Hits> getHits() {
        return hits;
    }

    /**
     * Sets the hits.
     *
     * @param hits
     *            the new hits
     */
    public void setHits(List<Hits> hits) {
        this.hits = hits;
    }

}
