package com.styleme.scraper;
import com.styleme.parsers.WebsiteParser;
import java.io.IOException;


public class StyleScraper {

    public static void main (String[] args) {
        WebsiteParser websiteParser = new WebsiteParser();
        try {
            websiteParser.parseWebsites("src/main/resources/fashionWebsites/websiteUrls.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}