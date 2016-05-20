package com.styleme.pojos;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashSet;
import java.util.Set;


/**
 *
 * @author Eibhlin McGeady
 *
 * Encapsulates a Clothing object
 * Creates a JSON object of the Clothing object
 *
 */
public class Clothing implements Comparable<Clothing>{

    private String id;
    private String name;
    private String type;
    private String description;
    private double price;
    private String currency;
    private String url;
    private String image;
    private Set<String> colours;
    private Set<String> terms;
    private int score;

    public Clothing(String id) {
        this.id = id;
        terms = new HashSet<>();
    }

    @JsonCreator
    public Clothing(@JsonProperty("id") String id,@JsonProperty("name") String name, @JsonProperty("type") String type, @JsonProperty("description") String description,
                    @JsonProperty("price") double price, @JsonProperty("currency") String currency, @JsonProperty("url") String url,
                    @JsonProperty("image") String image, @JsonProperty("colours") Set<String> colours) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.description = description;
        this.price = price;
        this.currency = currency;
        this.url = url;
        this.image = image;
        this.colours = colours;
        this.terms = new HashSet<>();
        this.score = 0;
    }

    public Clothing() {}

    @Override
    public int compareTo(Clothing clothing) {
        return this.id.compareTo(clothing.getId());
    }

    @Override
    public boolean equals(Object o){
        if(o != null && o instanceof Clothing) {
            return this.id.equals(((Clothing) o).getId());
        } else return false;
    }

    public String getId() { return this.id; }

    public void setId(String id) { this.id = id; }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCurrency() { return this.currency; }

    public void setCurrency(String currency) { this.currency = currency; }

    public double getPrice() {
        return this.price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getUrl() { return this.url; }

    public void setUrl(String url) { this.url = url; }

    public String getImage() { return this.image; }

    public void setImage(String image) { this.image = image; }

    public Set<String> getColours() {
        return colours;
    }

    public void setColours(Set<String> colours) { this.colours = new HashSet<>(colours); }

    public Set<String> getTerms() { return this.terms; }

    public void setTerms(Set<String> terms) { this.terms = terms; }

    public void addTerm(String term) { this.terms.add(term); }

    public void setScore(int score) { this.score = score; }

    public int getScore() { return this.score; }
}