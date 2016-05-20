package com.styleme.factories;

import java.util.*;

/**
 * @author Eibhlin McGeady
 *
 * Assigns colours to a clothing item based on shades in a clothing item's title/description
 *
 */
public class ColoursFactory {
    
    private Map<String, List<String>> colours;

    public ColoursFactory() {
        colours = new HashMap<>();
        colours.put("black", getBlackShades());
        colours.put("white", getWhiteShades());
        colours.put("red", getRedShades());
        colours.put("orange", getOrangeShades());
        colours.put("yellow", getYellowShades());
        colours.put("green", getGreenShades());
        colours.put("blue", getBlueShades());
        colours.put("purple", getPurpleShades());
        colours.put("pink", getPinkShades());
        colours.put("brown", getBrownShades());
        colours.put("beige", getBeigeShades());
        colours.put("metallic", getMetallicShades());
        colours.put("navy", getNavyShades());
        colours.put("grey", getGreyShades());
        colours.put("maroon", getMaroonShades());
        colours.put("cream", getCreamShades());
        colours.put("multi", getMultiPatterns());
    }
    
    public Set<String> getColoursFromDetails(String details) {
        Set<String> includedColours = new HashSet<>();
        details = details.toLowerCase();
        for(String colour : colours.keySet()) {
            for(String shade : colours.get(colour)) {
                if(details.contains(shade)) {
                    includedColours.add(colour);
                }
            }
        }
        return includedColours;
    }


    private List<String> getBlackShades() {
        return Arrays.asList("black", "monochrome", "zebra", "noir");
    }
    
    private List<String> getWhiteShades() {
        return Arrays.asList("white", "ivory", "monochrome", "zebra", "eggshell", "alabaster", "mastic", "blanc");
    }

    private List<String> getRedShades() {
        return Arrays.asList("red", "tomato", "crimson", "ruby", "rouge");
    }

    private List<String> getOrangeShades() {
        return Arrays.asList("orange", "rust", "teracotta", "copper", "coral", "apricot", "honey", "rouille", "clementine");
    }

    private List<String> getYellowShades() {
        return Arrays.asList("yellow", "mustard", "honey", "lemon", "gold", "heather", "jaune");
    }

    private List<String> getGreenShades() {
        return Arrays.asList("green", "khaki", "teal", "mint", "turquoise", "lime", "olive", "jungle", "kerry", "vert","leaf");
    }

    private List<String> getBlueShades() {
        return Arrays.asList("blue", "cobalt", "teal", "sapphire", "turquoise", "beetle", "royal", "bleu", "sax", "bugatti");
    }

    private List<String> getPurpleShades() {
        return Arrays.asList("purple", "lilac", "violet", "indigo", "muave", "plum");
    }

    private List<String> getPinkShades() {
        return Arrays.asList("pink", "blush", "coral", "peach", "rose", "raspberry", "salmon", "paprika");
    }

    private List<String> getBrownShades() {
        return Arrays.asList("brown","taupe", "mink", "bronze", "tan", "coffee", "chocolate", "tarmac", "vicuna", "marron");
    }

    private List<String> getBeigeShades() {
        return Arrays.asList("beige", "nude", "camel", "tan", "clay", "oatmeal", "sand");
    }

    private List<String> getMetallicShades() {
        return Arrays.asList("bronze", "silver", "gold", "metallic", "copper");
    }
    
    private List<String> getNavyShades() {
        return Arrays.asList("navy", "beetle", "marine", "anthracite");
    }

    private List<String> getGreyShades() {
        return Arrays.asList("gray", "stone", "charcoal", "grey", "silver", "gris");
    }
    
    private List<String> getMaroonShades() {
        return Arrays.asList("maroon", "wine", "burgundy", "oxblood", "bordeaux");
    }
    
    private List<String> getCreamShades() {
        return Arrays.asList("cream", "oatmeal", "ivory");
    }

    private List<String> getMultiPatterns() {
        return Arrays.asList("multi", "monochrome", "zebra", "floral", "aztec", "paisley", "polka", "stripe", "ecru");
    }

}
