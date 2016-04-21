package com.styleme.pojos;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.*;


/**
 *
 * @author Eibhlin McGeady
 *
 * Encapsulates a Style Object
 */
public class Style {

    private String style;
    private Set<Map<String, Integer>> terms;


    @JsonCreator
    public Style(@JsonProperty("style") String style, @JsonProperty("terms") ArrayList<HashMap<String, Integer>> terms) {
        this.style = style;
        this.terms = new HashSet<Map<String, Integer>>(terms);
    }

    public Style(String style, Set<String> terms) {
        this.style = style;
        this.terms = new HashSet<>();
        addTermsFromSet(terms);
    }

    public Style() {
        this.terms = new HashSet<>();
    }

    @Override
    public boolean equals(Object o){
        return o != null && o instanceof Style && this.style.equals(((Style) o).getStyle());
    }

    public String getStyle() {
        return this.style;
    }

    public void setStyle(String style) {
        this.style = style;
    }


    public Set<Map<String, Integer>> getTerms() {
        return terms;
    }

    public void addTermsFromSet(Set<String> terms) {
        for(String name : terms) {
            Map<String, Integer> term = new HashMap<>();
            term.put(name, 1);
            this.terms.add(term);
        }
    }

    public void setTerms(Set<Map<String, Integer>> terms) {
        this.terms = terms;
    }
}

