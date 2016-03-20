package com.styleme.parsers;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;

/**
 * @author Eibhlin McGeady
 *
 * Parses each HTML online clothing websites Asos, MotelRocks & Topshop
 * Extracts a link to individual clothing pages, to the clothing items image
 * Extracts attribute information about each clothing item
 */
public class WebsiteParser {

    private ClothingDescriptionParser clothingDescriptionParser;

    public WebsiteParser() {
        clothingDescriptionParser = new ClothingDescriptionParser();
    }

    public void parseWebsites(String websiteFile) {
        BufferedReader bufferedReader;
        try {
            bufferedReader = new BufferedReader(new FileReader(websiteFile));
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
            case "topshop": getTopshopClothingImagesAndLinks(doc, type);
                            break;
            case "asos":    getAsosClothingImagesAndLinks(doc, type);
                            break;
            case "motel":   getMotelImagesAndLinks(doc, type);
        }
    }

    private void getMotelImagesAndLinks(Document doc, String type) {
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
    }

    private void getTopshopClothingImagesAndLinks(Document doc, String type) {
        Elements list = doc.getElementsByClass("product_image");
        for(Element el : list) {
            Element link = el.child(0);
            String url = link.absUrl("href");
            String title = link.attr("title");
            Element img = link.child(0);
            String image = img.absUrl("src");
            getLink(url, image, "topshop", title, type);
        }
    }

    private void getAsosClothingImagesAndLinks(Document doc, String type) {
        Elements outOfStock = doc.getElementsByClass("outofstock-msg");
        if(outOfStock.isEmpty()) {
        Elements list = doc.getElementsByClass("productImageLink");
            for(Element link : list) {
                String url = link.absUrl("href");
                String title = link.attr("title");
                Element img = link.child(0);
                String image = img.absUrl("src");
                getLink(url, image, "asos", title, type);
            }
        }
    }

    private void getLink(String url, String image, String site, String title, String type) {
        try {
            Document clothingDesc = Jsoup.connect(url)
                    .timeout(100000)
                    .userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.152 Safari/537.36")
                    .get();
            clothingDescriptionParser.getDescription(url, image, clothingDesc, site, title, type);
        } catch (IOException e) {
            System.err.println("Error connecting to url " + url);
        }
    }

}
