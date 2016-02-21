package com.styleme.pojos;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashSet;
import java.util.Set;


/**
 *
 * @author Eibhlin McGeady
 *
 * Encapsulates a Style Object
 */
public class Style implements Comparable<Style> {

    private String style;
    private Set<String> terms;


    @JsonCreator
    public Style(@JsonProperty("style") String style, @JsonProperty("terms") Set<String> terms) {
        this.style = style;
        this.terms = terms;
    }

    public Style() {}

    @Override
    public int compareTo(Style style) {
        return this.style.compareTo(style.getStyle());
    }

    public String getStyle() {
        return this.style;
    }

    public void setStyle(String style) {
        this.style = style;
    }


    public Set<String> getTerms() {
        return terms;
    }

    public void setTerms(Set<String> terms) {
        this.terms = new HashSet<>(terms);
    }

}

