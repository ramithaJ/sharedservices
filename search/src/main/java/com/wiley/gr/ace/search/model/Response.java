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

import com.fasterxml.jackson.annotation.JsonProperty;

// TODO: Auto-generated Javadoc
/**
 * Created by kkalyan on 9/8/2015.
 */
public class Response implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The took. */
    private long took;

    /** The total. */
    private long total;

    /** The timed out. */
    @JsonProperty("timed_out")
    private boolean timedOut;

    /** The max score. */
    @JsonProperty("max_score")
    private float maxScore;

    /** The hits. */
    private List<Hits> hits;

    /** The facets. */
    private Facets facets;

    /**
     * Gets the took.
     *
     * @return the took
     */
    public long getTook() {
        return took;
    }

    /**
     * Sets the took.
     *
     * @param took
     *            the new took
     */
    public void setTook(long took) {
        this.took = took;
    }

    /**
     * Gets the total.
     *
     * @return the total
     */
    public long getTotal() {
        return total;
    }

    /**
     * Sets the total.
     *
     * @param total
     *            the new total
     */
    public void setTotal(long total) {
        this.total = total;
    }

    /**
     * Checks if is timed out.
     *
     * @return true, if is timed out
     */
    public boolean isTimedOut() {
        return timedOut;
    }

    /**
     * Sets the timed out.
     *
     * @param timedOut
     *            the new timed out
     */
    public void setTimedOut(boolean timedOut) {
        this.timedOut = timedOut;
    }

    /**
     * Gets the max score.
     *
     * @return the max score
     */
    public float getMaxScore() {
        return maxScore;
    }

    /**
     * Sets the max score.
     *
     * @param maxScore
     *            the new max score
     */
    public void setMaxScore(float maxScore) {
        this.maxScore = maxScore;
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

    /**
     * Gets the facets.
     *
     * @return the facets
     */
    public Facets getFacets() {
        return facets;
    }

    /**
     * Sets the facets.
     *
     * @param facets
     *            the new facets
     */
    public void setFacets(Facets facets) {
        this.facets = facets;
    }
}
