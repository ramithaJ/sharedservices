package com.wiley.gr.ace.search.model;

/**
 * Created by KKALYAN on 9/13/2015.
 */
public class Items {

    private String field;
    private String term;
    private long count;

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }
}
