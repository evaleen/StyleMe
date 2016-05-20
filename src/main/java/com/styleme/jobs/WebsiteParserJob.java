package com.styleme.jobs;

import com.styleme.parsers.WebsiteParser;
import com.styleme.setup.ElasticsearchSetup;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/*
 * @author Eibhlin McGeady
 *
 * Job class that updates the men's and women's clothing items in the database
 *
*/
public class WebsiteParserJob implements Job {

    WebsiteParser websiteParser = new WebsiteParser();
    ElasticsearchSetup elasticsearchSetup = new ElasticsearchSetup();

    public void execute(JobExecutionContext context) throws JobExecutionException {

        elasticsearchSetup.refreshWebsitesIndex("womens");
        websiteParser.parseWebsites("src/main/resources/womensWebsiteUrls.txt", "womens");
        elasticsearchSetup.refreshWebsitesIndex("mens");
        websiteParser.parseWebsites("src/main/resources/mensWebsiteUrls.txt", "mens");

    }

}