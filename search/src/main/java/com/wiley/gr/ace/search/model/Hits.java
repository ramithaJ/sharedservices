package com.wiley.gr.ace.search.model;

import java.util.Map;

/**
 * Created by KKALYAN on 9/13/2015.
 */
public class Hits {

    private float score;
    private String id;
    private Map<String, Object> source;
    private String type;

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Map<String, Object> getSource() {
        return source;
    }

    public void setSource(Map<String, Object> source) {
        this.source = source;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
