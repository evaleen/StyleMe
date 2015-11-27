package com.styleme.pojos;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashSet;
import java.util.Set;


/**
 *
 * @author Eibhlin McGeady
 *
 * Encapsulates media object
 */
public class Clothing implements Comparable<Clothing> {

    private String name;
    private String type;
    private String description;
    private String price;
    private Set<String> colours;



    @JsonCreator
    public Clothing(@JsonProperty("name") String name, @JsonProperty("type") String type, @JsonProperty("description") String description,
                    @JsonProperty("price") String price, @JsonProperty("colours") Set<String> colours) {
        this.name = name;
        this.type = type;
        this.description = description;
        this.price = price;
        this.colours = colours;
    }

    public Clothing() {}

    @Override
    public int compareTo(Clothing clothing) {
        return this.name.compareTo(clothing.getName());
    }

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

    public String getPrice() {
        return this.price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Set<String> getColours() {
        return colours;
    }

    public void setColours(Set<String> colours) {
        this.colours = new HashSet<>(colours);
    }

    public String getId() {
        return this.name.replaceAll(" ", "_");
    }
}