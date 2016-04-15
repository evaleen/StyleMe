package com.styleme.selectors;

import com.styleme.pojos.Clothing;
import com.styleme.pojos.Style;

import java.util.*;

/**
 * @author Eibhlin McGeady
 */
public class StyleSelector {

    private List<Clothing> selectedClothing;

    public StyleSelector() {
        selectedClothing = new ArrayList<>();
    }

    public List<Clothing> getClothingSuggestionsForStyle(Style style, List<Clothing> clothing) {
        TreeMap<Integer, Set<Clothing>> scores = new TreeMap<>();
        Set<Clothing> items;
        for(Clothing item : clothing) {
            item = getClothingScore(item, style.getStyle(), style.getTerms());
            if(scores.containsKey(item.getScore())) {
                items = scores.get(item.getScore());
            } else {
                items = new HashSet<>();
            }
            items.add(item);
            scores.put(item.getScore(), items);
        }
        selectedClothing = getSortedClothesList(scores);
        return getTop(selectedClothing, 3);
    }

    public List<Clothing> getClothingWithUpdatedScores(List<String> incTerms, List<String> decTerms) {
        TreeMap<Integer, Set<Clothing>> scores = new TreeMap<>();
        Set<Clothing> items;
        for(Clothing item : selectedClothing) {
            item = updateClothingScore(item, incTerms, decTerms);
            if(scores.containsKey(item.getScore())) {
                items = scores.get(item.getScore());
            } else {
                items = new HashSet<>();
            }
            items.add(item);
            scores.put(item.getScore(), items);
        }
        return getSortedClothesList(scores);
    }

    private Clothing updateClothingScore(Clothing item, List<String> incTerms, List<String> decTerms) {
        String description = item.getName() + " " + item.getDescription();
        List<String> descriptionWords = Arrays.asList(description.split(" "));
        int score = item.getScore();
        for(String term : incTerms) {
                if(descriptionWords.contains(term)) {
                    score++;
                }
        }
        for(String term : decTerms) {
            if(descriptionWords.contains(term) && score > 0) {
                score--;
            }
        }
        item.setScore(score);
        return item;
    }

    private Clothing getClothingScore(Clothing item, String styleName, Set<Map<String, Integer>> styleTerms) {
        String description = item.getName() + " " + item.getDescription();
        List<String> descriptionWords = Arrays.asList(description.split(" "));
        int score = 0;
        if (description.contains(styleName)) score = score + 3;
        for(Map<String, Integer> term : styleTerms) {
            for(String name : term.keySet())
                if(descriptionWords.contains(name)) {
                    score = score + term.get(name);
                    item.addTerm(name);
                }
        }
        item.setScore(score);
        return item;
    }

    private List<Clothing> getSortedClothesList(TreeMap<Integer, Set<Clothing>> items) {
        List<Clothing> sortedClothes = new ArrayList<>();
        for(int score : items.keySet()) {
            if(score > 0) {
                for(Clothing item : items.get(score)) {
                    sortedClothes.add(item);
                }
            }
        }
        Collections.reverse(sortedClothes);
        return sortedClothes;
    }

    private List<Clothing> getTop(List<Clothing> clothing, int num) {
        List<Clothing> topThree = new ArrayList<>();
        if(clothing.size() < num) num = clothing.size();
        for(int i = 0; i < num; i++) {
            topThree.add(clothing.get(i));
        }
        return topThree;
    }
}
