package com.styleme.scraper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;


public class StyleScraper {

    public static void main (String[] args) {
        System.out.println("testing GitHub");

        String html = "<html><head><title>First parse</title></head>"
                + "<body><p>Parsed HTML into a doc.</p></body></html>";
        try {
            Document doc = Jsoup.connect("http://eu.topshop.com/en/tseu/category/clothing-485092").get();
            Element link = doc.select("a").first();

            String text = doc.html();
            System.out.println("text:  " + text);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}