package com.styleme.parsers;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;

/**
 * @author Eibhlin McGeady
 *
 * Parses each HTML online clothing websites NastyGal, NewLook, Missguided, MotelRocks & Topshop
 * Extracts a link to individual clothing pages, to the clothing items image
 * Extracts attribute information about each clothing item
 */
public class WebsiteParser {

    private ClothingDescriptionParser clothingDescriptionParser;

    public WebsiteParser() {
        clothingDescriptionParser = new ClothingDescriptionParser();
    }

    public void parseWebsites(String websiteFile) {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(websiteFile));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] splitLine = line.split("\\s+");
                String site = splitLine[0];
                String type = splitLine[1];
                parse(splitLine[2], site, type);
            }
            bufferedReader.close();
        } catch (FileNotFoundException e) {
            System.err.println("File not found " + websiteFile);
        } catch (IOException e) {
            System.err.println("IO error reading from file" + websiteFile);
        }
    }

    private void parse(String url, String site, String type) {
        try {
            Document doc = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.152 Safari/537.36")
                    .timeout(100000)
                    .get();
            getClothingLinksAndImages(doc, site, type);
        } catch (IOException e) {
            System.err.println("Error connecting to url " + url);
        }
    }

    private void getClothingLinksAndImages(Document doc, String site, String type) {
        switch (site) {
            case "topshop":    getTopshopClothingImagesAndLinks(doc, type);
                               break;
            case "newlook":    getNewLookClothingImagesAndLinks(doc, type);
                               break;
            case "motel":      getMotelImagesAndLinks(doc, type);
                               break;
            case "missguided": getMissguidedClothingImagesAndLinks(doc, type);
                               break;
            case "nastygal":   getNastyGalClothingImagesAndLinks(doc, type);
       }
    }

    private void getNastyGalClothingImagesAndLinks(Document doc, String type) {
        try {
            Elements list = doc.getElementsByClass("category-item");
            for (Element el : list) {
                String url = el.select("a.product-link").first().absUrl("href");
                String image = el.select("img.category-item-thumb").first().absUrl("src");
                String title = el.select("div.product-name").first().text();
                getLink(url, image, "nastygal", title, type);
            }
        } catch (NullPointerException e) {
            System.err.println("Error extracting information from Nasty Gal site");
        }
    }

    private void getMissguidedClothingImagesAndLinks(Document doc, String type) {
       try {
            Elements list = doc.getElementsByClass("products-grid__item");
            for(Element el : list) {
                String url = el.child(0).absUrl("href");
                Element img = el.getElementsByClass("category-products__image").first();
                String image = img.absUrl("src");
                String title = el.child(0).attr("title");
                getLink(url, image, "missguided", title, type);
            }
       } catch (NullPointerException e) {
           System.err.println("Error extracting information from Missguided site");
       }
    }

    private void getNewLookClothingImagesAndLinks(Document doc, String type) {
        try {
            Elements list = doc.getElementsByClass("product");
            for(Element el : list) {
                Element link = el.child(1);
                String url = link.absUrl("href");
                String image = link.child(0).absUrl("src");
                String title = el.getElementsByClass("desc").first().text();
                getLink(url, image, "newlook", title, type);
            }
        } catch (NullPointerException e) {
            System.err.println("Error extracting information from New Look site");
        }
    }

    private void getMotelImagesAndLinks(Document doc, String type) {
        try {
            Elements list = doc.getElementsByClass("catproddiv");
            for(Element el : list) {
                Element link =  el.getElementsByClass("xProductImage").get(0).child(0);
                String url = link.absUrl("href");
                Element img = link.child(0);
                String image = img.absUrl("src");
                if(image.equals("")) image = img.absUrl("pagespeed_high_res_src");
                String title = el.getElementsByClass("xProductDetails").get(0).text();
                getLink(url, image, "motel", title, type);
            }
        } catch (NullPointerException e) {
            System.err.println("Error extracting information from Motel site");
        }
    }

    private void getTopshopClothingImagesAndLinks(Document doc, String type) {
        try {
            Elements list = doc.getElementsByClass("product");
            for(Element el : list) {
                Element link = el.child(0);
                String url = link.absUrl("href");
                Element img = link.child(0);
                String title = img.attr("alt");
                String image = img.absUrl("src");
                getLink(url, image, "topshop", title, type);
            }
        } catch (NullPointerException e) {
            System.err.println("Error extracting information from Topshop site");
        }
    }

    private void getLink(String url, String image, String site, String title, String type) {
        try {
            Document clothingDesc = Jsoup.connect(url).timeout(100000)
                    .userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.152 Safari/537.36")
                    .get();
            clothingDescriptionParser.getDescription(url, image, clothingDesc, site, title, type);
        } catch (IOException e) {
            System.err.println("Error connecting to url " + url);
        }
    }

}
