package com.styleme.parsers;

import com.styleme.factories.ColoursFactory;
import com.styleme.pojos.Clothing;
import com.styleme.submitters.ElasticsearchSubmitter;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.*;

/**
 *  @author Eibhlin McGeady
 *
 *  Parses the clothing HTML page,
 *  extracts the relvant information and puts it into Elasticsearch
 *
 */
public class ClothingDescriptionParser {


    private ElasticsearchSubmitter elasticsearchSubmitter;
    private ColoursFactory coloursFactory;

    public ClothingDescriptionParser() {
        elasticsearchSubmitter = new ElasticsearchSubmitter();
        coloursFactory = new ColoursFactory();
    }

    public void getDescription(String url, String image, Document document, String site, String title, String type) {
        switch (site) {
            case "topshop" :   getTopshopDescription(url, image, document, title, type);
                               break;
            case "newlook" :   getNewLookDescription(url, image, document, title, type);
                               break;
            case "motel"   :   getMotelDescription(url, image, document, title, type);
                               break;
            case "missguided": getMissguidedDescription(url, image, document, title, type);
                               break;
            case "nastygal":   getNastyGalDescription(url, image, document, title, type);
        }
    }

    private void getNastyGalDescription(String url, String image, Document document, String title, String type) {
        try {
            String description = document.select("div.product-description").first().text();
            String price = document.select("span.current-price").first().text();
            String currency = getCurrency(price);
            double priceNum = convertPrice(price);
            String colour = document.getElementsByAttributeValue("property", "og:color").first().attr("content");
            Set<String> colours = coloursFactory.getColoursFromDetails(title + " " + description + " " + colour);
            insertClothingItemIntoES("nastygal", title, type, url, image, description, priceNum, currency, colours);
        } catch (NullPointerException e) {
            System.err.println("Error extracting information from " + url);
        }
    }

    private void getMissguidedDescription(String url, String image, Document document, String title, String type) {
        try {
            Element detailsDiv = document.select("div.product-essential__description").first();
            String description = detailsDiv.text();
            String price = document.select("span.price").first().text();
            String currency = getCurrency(price);
            double priceNum = convertPrice(price);
            Set<String> colours = coloursFactory.getColoursFromDetails(title + " " + description);
            insertClothingItemIntoES("missguided", title, type, url, image, description, priceNum, currency, colours);
        } catch (NullPointerException e) {
            System.err.println("Error extracting information from " + url);
        }
    }

    private void getMotelDescription(String url, String image, Document document, String title, String type) {
        try {
            String description = document.getElementById("Details").child(1).text();
            String price = document.getElementsByClass("ProductPrice").first().text();
            String currency = getCurrency(price);
            double priceNum = convertPrice(price);
            Set<String> colours = coloursFactory.getColoursFromDetails(title + " " + description);
            insertClothingItemIntoES("motel", title, type, url, image, description, priceNum, currency, colours);
        } catch (NullPointerException e) {
            System.err.println("Error extracting information from " + url);
        }
    }

    private void getNewLookDescription(String url, String image, Document document, String title, String type) {
        try {
            String description = document.select("div.information-section").first().text();
            Element priceSpan = document.getElementsByClass("salePrice").first();
            if (priceSpan == null) {
                priceSpan = document.getElementsByClass("productPrice").first();
            }
            String price = priceSpan.text();
            String currency = getCurrency(price);
            double priceNum = convertPrice(price);
            Set<String> colours = coloursFactory.getColoursFromDetails(title + " " + description);
            insertClothingItemIntoES("newlook", title, type, url, image, description, priceNum, currency, colours);
        } catch (NullPointerException e) {
            System.err.println("Error extracting information from " + url);
        }
    }

    public void getTopshopDescription(String url, String image, Document document, String title, String type) {
        try {
            String description = document.getElementById("productInfo").select("p").text();
            String colour = document.getElementsByClass("product_colour").text();
            Set<String> colours = coloursFactory.getColoursFromDetails(colour);
            String price = document.getElementsByClass("product_price").text();
            price = price.replace("Price:", "").replaceAll("\\s+","");
            String currency = getCurrency(price);
            double priceNum = convertPrice(price);
            insertClothingItemIntoES("topshop", title, type, url, image, description, priceNum, currency, colours);
        } catch (NullPointerException e) {
            System.err.println("Error extracting information from " + url);
        }
    }

    private void insertClothingItemIntoES(String site, String title, String type, String url, String image, String description, double price, String currency, Set<String> colours) {
        Clothing item = new Clothing(convertToId(title), title, type, description, price, currency, url, image, colours);
        elasticsearchSubmitter.postClothing(site, item);
    }

    public String convertToId(String title) {
        return title.replaceAll(" ", "_");
    }

    public String getCurrency(String price) {
        if(price.contains("£")) return "GBP";
        else if (price.contains("€")) return "EUR";
        else if (price.contains("$")) return "USD";
        else return "";
    }

    public double convertPrice(String price) {
        price = price.replaceAll(",", ".");
        price = price.replaceAll("[^0-9.]", "");
        double num =0.0;
        try {
            num = Double.parseDouble(price);
        } catch(NumberFormatException e) {
            System.err.println("Error converting price" + price);
        }
        return num;
    }
}