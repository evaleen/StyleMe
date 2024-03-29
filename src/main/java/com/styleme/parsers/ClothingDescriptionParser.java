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
 *  extracts the colours, price and description corresponding to the item
 *  and puts it into Elasticsearch as a JSON obejct
 *
 */
public class ClothingDescriptionParser {


    private ElasticsearchSubmitter elasticsearchSubmitter;
    private ColoursFactory coloursFactory;

    public ClothingDescriptionParser() {
        elasticsearchSubmitter = new ElasticsearchSubmitter();
        coloursFactory = new ColoursFactory();
    }

    public void getDescription(String url, String image, Document document, String site, String title, String type, String gender) throws NullPointerException {
        switch (site) {
            case "topshop" :    getTopshopDescription(url, image, document, title, type, gender);
                                break;
            case "newlook" :    getNewLookDescription(url, image, document, title, type, gender);
                                break;
            case "motel"   :    getMotelDescription(url, image, document, title, type, gender);
                                break;
            case "missguided":  getMissguidedDescription(url, image, document, title, type, gender);
                                break;
            case "nastygal":    getNastyGalDescription(url, image, document, title, type, gender);
                                break;
            case "riverisland": getRiverIslandDescription(url, image, document, title, type, gender);
                                break;
            case "oipolloi":    getOiPolloiDescription(url, image, document, title, type, gender);
        }
    }

    private void getOiPolloiDescription(String url, String image, Document document, String title, String type, String gender) throws NullPointerException {
        String description = document.select("div.product-description").first().text();
        String price;
        if(document.select("p.product-pricing").hasClass("product-price-reduced")) {
            price = document.select("span.price.price-on-sale.price-inc-vat").first().child(0).text();
        } else { price = document.select("span.price.price-full.price-inc-vat").first().child(0).text(); }
        String currency = getCurrency(price);
        double priceNum = convertPrice(price);
        Set<String> colours = coloursFactory.getColoursFromDetails(document.select("span.colour").first().text());
        insertClothingItemIntoES("oipolloi", title, type, gender, url, image, description, priceNum, currency, colours);
    }

    private void getRiverIslandDescription(String url, String image, Document document, String title, String type, String gender) throws NullPointerException {
        String description = document.select("div.product__description").first().text();
        String price = document.select("div.price").first().text();
        String currency = getCurrency(price);
        double priceNum = convertPrice(price);
        Set<String> colours = coloursFactory.getColoursFromDetails(title);
        insertClothingItemIntoES("riverisland", title, type, gender, url, image, description, priceNum, currency, colours);
    }

    private void getNastyGalDescription(String url, String image, Document document, String title, String type, String gender) throws NullPointerException {
        String description = document.select("div.product-description").first().text();
        String price = document.select("span.current-price").first().text();
        String currency = getCurrency(price);
        double priceNum = convertPrice(price);
        String colour = document.getElementsByAttributeValue("property", "og:color").first().attr("content");
        Set<String> colours = coloursFactory.getColoursFromDetails(title + " " + description + " " + colour);
        insertClothingItemIntoES("nastygal", title, type, gender, url, image, description, priceNum, currency, colours);
    }

    private void getMissguidedDescription(String url, String image, Document document, String title, String type, String gender) throws NullPointerException {
        Element detailsDiv = document.select("div.product-essential__description").first();
        String description = detailsDiv.text();
        String price = document.select("span.price").first().text();
        String currency = getCurrency(price);
        double priceNum = convertPrice(price);
        Set<String> colours = coloursFactory.getColoursFromDetails(title);
        insertClothingItemIntoES("missguided", title, type, gender, url, image, description, priceNum, currency, colours);
    }

    private void getMotelDescription(String url, String image, Document document, String title, String type, String gender) throws NullPointerException {
        String description = document.getElementById("Details").child(1).text();
        String price = document.getElementsByClass("ProductPrice").first().text();
        String currency = getCurrency(price);
        double priceNum = convertPrice(price);
        Set<String> colours = coloursFactory.getColoursFromDetails(title);
        insertClothingItemIntoES("motel", title, type, gender, url, image, description, priceNum, currency, colours);
    }

    private void getNewLookDescription(String url, String image, Document document, String title, String type, String gender) throws NullPointerException {
        String description = document.select("div.information-section").first().text();
        Element priceSpan = document.getElementsByClass("salePrice").first();
        if (priceSpan == null) {
            priceSpan = document.getElementsByClass("productPrice").first();
        }
        String price = priceSpan.text();
        String currency = getCurrency(price);
        double priceNum = convertPrice(price);
        Set<String> colours = coloursFactory.getColoursFromDetails(title);
        insertClothingItemIntoES("newlook", title, type, gender, url, image, description, priceNum, currency, colours);
    }

    private void getTopshopDescription(String url, String image, Document document, String title, String type, String gender) throws NullPointerException {
        String description = document.getElementById("productInfo").select("p").text();
        String colour = document.getElementsByClass("product_colour").text();
        Set<String> colours = coloursFactory.getColoursFromDetails(colour);
        String price = document.getElementsByClass("product_price").text();
        price = price.replace("Price:", "").replaceAll("\\s+","");
        String currency = getCurrency(price);
        double priceNum = convertPrice(price);
        insertClothingItemIntoES("topshop", title, type, gender, url, image, description, priceNum, currency, colours);
    }

    private void insertClothingItemIntoES(String site, String title, String type, String gender, String url, String image, String description, double price, String currency, Set<String> colours) {
        Clothing item = new Clothing(convertToId(title), title, type, description, price, currency, url, image, colours);
        elasticsearchSubmitter.postClothing(site, item, gender);
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
        int index = price.indexOf('.');
        if ( index >-1 ) price = price.substring(0, index+2);
        double num;
        try {
            num = Double.parseDouble(price);
        } catch(NumberFormatException e) {
            num = 0.0;
        }
        return num;
    }
}