package com.wiley.gr.ace.search.model;

/**
 * Created by KKALYAN on 7/2/2015.
 */
public class Journal {

    private String title;
    private String acronym;
    private String type;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAcronym() {
        return acronym;
    }

    public void setAcronym(String acronym) {
        this.acronym = acronym;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Journal{" +
                "title='" + title + '\'' +
                ", acronym='" + acronym + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
