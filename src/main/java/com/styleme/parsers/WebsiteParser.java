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
 */
public class WebsiteParser {

    //Read from file with urls
    //Parse each html from url
    //Store in resources/fashionWebsites/topshop
    final private String FILE_PATH = "src/main/resources/fashionWebsites/";

    public void parseWebsites(String websiteFile) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(websiteFile));
        try {
            String line = bufferedReader.readLine();
            int number = 0;
            String prevSite = "";
            while (line != null) {
                String[] splitLine = line.split("\\s+");
                String site = splitLine[0];
                if(site.equals(prevSite)) number++;
                else number = 0;
                prevSite = site;
                parse(splitLine[1], site, String.valueOf(number));
                line = bufferedReader.readLine();
            }
        } finally {
            bufferedReader.close();
        }
    }

    private void parse(String url, String site, String number) throws IOException {
        Document doc = Jsoup.connect(url)
                .userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.152 Safari/537.36")
                .timeout(100000)
                .get();
        String text = doc.html();
        createWebsiteFile(text, site, number);
        getClothingLinksAndImages(doc, site);
    }

    private void createWebsiteFile(String text, String site, String filename) {
        Writer writer = null;
        try {
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(FILE_PATH + site + "/" + filename +".txt"), "utf-8"));
            writer.write(text);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                writer.close();
            } catch (Exception e) {}
        }
    }

    private void getImages(Document doc, String site) throws IOException {
        Elements images = doc.getElementsByTag("img");
        for(Element el: images) {
            String name = el.attr("alt");
            String src = el.absUrl("src");
            if(!src.startsWith("http:")) {
                getImage(src, site, name);
            }
        }
    }

    private void getImage(String src, String site, String name) throws IOException {
        try {
            URL url = new URL(src);
            InputStream inputStream = url.openStream();
            OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(FILE_PATH + site + "/images/"+name + ".jpg"));
            int pixel = inputStream.read();
            while (pixel != -1) {
                outputStream.write(pixel);
                pixel = inputStream.read();
            }
            outputStream.close();
            inputStream.close();
        } catch(MalformedURLException e) {
            System.out.println(src);
        }
    }

    private void getClothingLinksAndImages(Document doc, String site) throws IOException {
        if(site.equals("topshop")) {
            getImages(doc, site);
            getTopshopClothingLinks(doc);
        } else if (site.equals("asos")) {
            getImages(doc, site);
            getAsosClothingLinks(doc);
        }
        else if (site.equals("motel")) {
            getMotelImagesAndLinks(doc);
        }
    }

    private void getMotelImagesAndLinks(Document doc) throws IOException {
        Elements list = doc.getElementsByClass("xProductImage");
        for(Element el : list) {
            Element link = el.child(0);
            String url = link.absUrl("href");
            Element img = link.child(0);
            String src = img.absUrl("src");
            if(src.equals("")) src = img.absUrl("pagespeed_high_res_src");
            String title = img.attr("alt");
            getLink(url, "motel", title);
            getImage(src, "motel", title);
        }
    }



    private void getTopshopClothingLinks(Document doc) throws IOException {
        Elements list = doc.getElementsByClass("product_description");
        for(Element el : list) {
            Element link = el.child(0);
            String url = link.absUrl("href");
            String title = link.attr("title");
            getLink(url, "topshop", title);
        }
    }

    private void getAsosClothingLinks(Document doc) throws IOException {
        Elements list = doc.getElementsByClass("desc");
        for(Element link : list) {
            String url = link.absUrl("href");
            String title = link.text();
            getLink(url, "asos", title);
        }
    }

    private void getLink(String url, String site, String title) throws IOException {
        Document clothingDesc = Jsoup.connect(url)
                .timeout(100000)
                .userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.152 Safari/537.36")
                .get();
        String text = clothingDesc.html();
        createWebsiteFile(text, site, title);
    }

}
