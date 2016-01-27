package com.styleme.parsers;

import com.styleme.pojos.Clothing;
import com.styleme.submitters.ElasticsearchSubmitter;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *  @author Eibhlin McGeady
 *  This class parses the clothing HTML page,
 *  extracts the relvant information and puts it into Elasticsearch
 */
public class ClothingDescriptionParser {


    private ElasticsearchSubmitter elasticsearchSubmitter;
    public ClothingDescriptionParser() {
        elasticsearchSubmitter = new ElasticsearchSubmitter();

    }


    public void getDescription(String url, String image, Document document, String site, String title, String type) throws IOException {
        if(site.equals("topshop")) {
            getTopshopDescription(url, image, document, title, type);
        } else if(site.equals("asos")) {
            getAsosDescription(url, image, document, title, type);
        } else if(site.equals("motel")) {
            getMotelDescription(url, image, document, title, type);
        }
    }

    private void getMotelDescription(String url, String image, Document document, String title, String type) {
        //get description
        Element details = document.getElementById("Details");
        Element span = details.child(1);
        String description = span.text();
        //get price
        Elements prices = document.getElementsByClass("ProductPrice");
        String price = prices.first().text();
        String currency = getCurrency(price);
        double priceNum = convertPrice(price);
        //get colours
        Set<String> colours = new HashSet<>();
        Element colourDiv = document.getElementById("colourswatch");
        if(colourDiv != null) {
            for(Element child : colourDiv.children()) {
                String colour = child.attr("alt");
                if(colour.equals("")) colour = "multi";
                colours.add(colour);
            }
        }
        //put into ES
        insertClothingItemIntoES("motel", title, type, url, image, description, priceNum, currency, colours);

    }

    private void getAsosDescription(String url, String image, Document document, String title, String type) {
        //get description
        Elements descDiv = document.getElementsByClass("product-description");
        String description = descDiv.first().text();
        //get colours
        Elements coloursOptions = document.getElementById("ctl00_ContentMainPage_ctlSeparateProduct_drpdwnColour").children();
        Set<String> colours = new HashSet<>();
        for(Element el : coloursOptions) {
            String colour = el.attr("value");
            if(!colour.equals("-1") && !colour.equals("")) {
                colours.add(colour);
            }
        }
        //get price
        Elements priceDiv = document.getElementsByClass("product_price_details");
        String price = priceDiv.first().text();
        String currency = getCurrency(price);
        double priceNum = convertPrice(price);
        //put into ES
        insertClothingItemIntoES("asos", title, type, url, image, description, priceNum, currency, colours);
    }

    public void getTopshopDescription(String url, String image, Document document, String title, String type) {
        //get description
        String description = document.getElementsByClass("product_description").text();
        //get colours
        Set<String> colours = new HashSet<>();
        String colour = document.getElementsByClass("product_colour").text();
        colour = colour.replace("Colour:", "").replaceAll("\\s+","");
        colours.add(colour);
        //get price
        String price = document.getElementsByClass("product_price").text();
        price = price.replace("Price:", "").replaceAll("\\s+","");
        String currency = getCurrency(price);
        double priceNum = convertPrice(price);
        //put into ES
        insertClothingItemIntoES("topshop", title, type, url, image, description, priceNum, currency, colours);
    }

    private void insertClothingItemIntoES(String site, String title, String type, String url, String image, String description, double price, String currency, Set<String> colours) {
        Clothing item = new Clothing(convertToId(title), title, type, description, price, currency, url, image, colours);
        elasticsearchSubmitter.postClothing(site, item);
    }

    private String convertToId(String title) {
        return title.replaceAll(" ", "_");
    }

    private String getCurrency(String price) {
        if(price.contains("£")) return "GBP";
        else if (price.contains("€")) return "EUR";
        else if (price.contains("$")) return "USD";
        else return "";
    }

    private double convertPrice(String price) {
        price = price.replaceAll("[^0-9.]", "");
        return Double.parseDouble(price);
    }

}
