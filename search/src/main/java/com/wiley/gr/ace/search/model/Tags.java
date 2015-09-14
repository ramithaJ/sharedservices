package com.wiley.gr.ace.search.model;

import java.util.List;

/**
 * Created by KKALYAN on 9/13/2015.
 */
public class Tags {

    private int total;
    private List<Items> terms;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<Items> getTerms() {
        return terms;
    }

    public void setTerms(List<Items> terms) {
        this.terms = terms;
    }
}
