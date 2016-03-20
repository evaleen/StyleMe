package com.styleme.selectors;

import com.styleme.pojos.Clothing;
import com.styleme.pojos.Style;

import java.util.*;

/**
 * @author Eibhlin McGeady
 */
public class StyleSelector {


    public StyleSelector() {
    }

    public List<Clothing> getClothingForStyle(Style style, List<Clothing> clothing) {
        TreeMap<Integer, Set<Clothing>> scores = new TreeMap<>();
        Set<Clothing> items;
        for(Clothing item : clothing) {
            String description = item.getName() + " " + item.getDescription();
            int score = getRelevanceScore(description, style.getTerms());
            if(scores.containsKey(score)) {
                items = scores.get(score);
            } else {
                items = new HashSet<>();
            }
            items.add(item);
            scores.put(score, items);
        }
        return getSortedClothesList(scores);
    }

    private int getRelevanceScore(String description, Set<String> styleTerms) {
        int score = 0;
        for(String term : styleTerms) {
            if(description.contains(term)) {
                score++;
            }
        }
        return score;
    }


    public List<Clothing> getSortedClothesList(TreeMap<Integer, Set<Clothing>> scores) {
        List<Clothing> sortedClothes = new ArrayList<>();
        for(int score : scores.keySet()) {
            for(Clothing item : scores.get(score)) {
                sortedClothes.add(item);
            }
        }
        Collections.reverse(sortedClothes);
        return sortedClothes;
    }
}
