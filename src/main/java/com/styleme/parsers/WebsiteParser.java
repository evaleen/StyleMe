package com.styleme.parsers;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author Eibhlin McGeady
 * This class parses each HTML clothing shop page and extracts images and link to individual clothing pages.
 */
public class WebsiteParser {

    private ClothingDescriptionParser clothingDescriptionParser;
    final private String FILE_PATH = "src/main/resources/fashionWebsites/";

    public WebsiteParser() {
        clothingDescriptionParser = new ClothingDescriptionParser();
    };

    public void parseWebsites(String websiteFile) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(websiteFile));
        try {
            String line = bufferedReader.readLine();
            int number = 0;
            String prevSite = "";
            while (line != null) {
                String[] splitLine = line.split("\\s+");
                String site = splitLine[0];
                String type = splitLine[1];
                if(site.equals(prevSite)) number++;
                else number = 0;
                prevSite = site;
                parse(splitLine[2], site, String.valueOf(number), type);
                line = bufferedReader.readLine();
            }
        } finally {
            bufferedReader.close();
        }
    }

    private void parse(String url, String site, String number, String type) throws IOException {
        Document doc = Jsoup.connect(url)
                .userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.152 Safari/537.36")
                .timeout(100000)
                .get();
        //String text = doc.html();
        //createWebsiteFile(text, site, number);
        getClothingLinksAndImages(doc, site, type);
    }

//    private void createWebsiteFile(String text, String site, String filename) {
//        Writer writer = null;
//        try {
//            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(FILE_PATH + site + "/" + filename +".txt"), "utf-8"));
//            writer.write(text);
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                writer.close();
//            } catch (Exception e) {}
//        }
//    }

    private void getClothingLinksAndImages(Document doc, String site, String type) throws IOException {
        if(site.equals("topshop")) {
            getTopshopClothingImagesAndLinks(doc, type);
        } else if (site.equals("asos")) {
            getAsosClothingImagesAndLinks(doc, type);
        }
        else if (site.equals("motel")) {
           getMotelImagesAndLinks(doc, type);
        }
    }

//    private void getImages(Document doc, String site) throws IOException {
//        Elements images = doc.getElementsByTag("img");
//        for(Element el: images) {
//            String name = el.attr("alt");
//            String src = el.absUrl("src");
//            if(!src.startsWith("http:")) {
//                getImage(src, site, name);
//            }
//        }
//    }

//    private void getImage(String src, String site, String name) throws IOException {
//        try {
//            URL url = new URL(src);
//            InputStream inputStream = url.openStream();
//            OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(FILE_PATH + site + "/images/"+name + ".jpg"));
//            int pixel = inputStream.read();
//            while (pixel != -1) {
//                outputStream.write(pixel);
//                pixel = inputStream.read();
//            }
//            outputStream.close();
//            inputStream.close();
//        } catch(MalformedURLException e) {
//            System.out.println(src);
//        }
//    }

    private void getMotelImagesAndLinks(Document doc, String type) throws IOException {
        Elements list = doc.getElementsByClass("catproddiv");
        for(Element el : list) {
            Element link =  el.getElementsByClass("xProductImage").get(0).child(0);
            String url = link.absUrl("href");
            Element img = link.child(0);
            String image = img.absUrl("src");
            if(image.equals("")) image = img.absUrl("pagespeed_high_res_src");
            String title = el.getElementsByClass("xProductDetails").get(0).text();
            getLink(url, image, "motel", title, type);
            //getImage(src, "motel", title);
        }
    }

    private void getTopshopClothingImagesAndLinks(Document doc, String type) throws IOException {
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

    private void getAsosClothingImagesAndLinks(Document doc, String type) throws IOException {
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

    private void getLink(String url, String image, String site, String title, String type) throws IOException {
        Document clothingDesc = Jsoup.connect(url)
                .timeout(100000)
                .userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.152 Safari/537.36")
                .get();
        clothingDescriptionParser.getDescription(url, image, clothingDesc, site, title, type);
    }

}
