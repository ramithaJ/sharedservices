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

/**
 * Created by kkalyan on 9/8/2015.
 */
public class Response implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private long took;

    private long total;

    @JsonProperty("timed_out")
    private boolean timedOut;

    @JsonProperty("max_score")
    private float maxScore;

    private List<Hits> hits;

    private Facets facets;

    public long getTook() {
        return took;
    }

    public void setTook(long took) {
        this.took = took;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public boolean isTimedOut() {
        return timedOut;
    }

    public void setTimedOut(boolean timedOut) {
        this.timedOut = timedOut;
    }

    public float getMaxScore() {
        return maxScore;
    }

    public void setMaxScore(float maxScore) {
        this.maxScore = maxScore;
    }

    public List<Hits> getHits() {
        return hits;
    }

    public void setHits(List<Hits> hits) {
        this.hits = hits;
    }

    public Facets getFacets() {
        return facets;
    }

    public void setFacets(Facets facets) {
        this.facets = facets;
    }
}
