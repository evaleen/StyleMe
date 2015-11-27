package com.styleme.scraper;
import com.styleme.parsers.WebsiteParser;
import com.styleme.setup.ElasticsearchSetup;

import java.io.IOException;


public class StyleScraper {

    public static void main (String[] args) throws IOException {

        ElasticsearchSetup elasticsearchSetup = new ElasticsearchSetup();
        elasticsearchSetup.setup();

        WebsiteParser websiteParser = new WebsiteParser();
        websiteParser.parseWebsites("src/main/resources/fashionWebsites/websiteUrls.txt");
    }
}