package com.wiley.gr.ace.search.model;

import java.io.Serializable;
import java.util.List;

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

    private boolean timed_out;

    private float max_score;

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

    public boolean isTimed_out() {
        return timed_out;
    }

    public void setTimed_out(boolean timed_out) {
        this.timed_out = timed_out;
    }

    public float getMax_score() {
        return max_score;
    }

    public void setMax_score(float max_score) {
        this.max_score = max_score;
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
