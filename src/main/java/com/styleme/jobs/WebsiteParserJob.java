package com.styleme.jobs;

import com.styleme.parsers.WebsiteParser;
import com.styleme.setup.ElasticsearchSetup;
import com.styleme.submitters.ElasticsearchSubmitter;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/*
 * @author Eibhlin McGeady
 *
 * Job class that implements the Website Parser class
*/
public class WebsiteParserJob implements Job {

    WebsiteParser websiteParser = new WebsiteParser();
    ElasticsearchSetup elasticsearchSetup = new ElasticsearchSetup();

    public void execute(JobExecutionContext context) throws JobExecutionException {

        elasticsearchSetup.refreshWebsitesIndex();
        websiteParser.parseWebsites("src/main/resources/fashionWebsites/websiteUrls.txt");

    }

}