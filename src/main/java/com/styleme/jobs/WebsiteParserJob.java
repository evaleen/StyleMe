package com.styleme.jobs;

import com.styleme.parsers.WebsiteParser;
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

    public void execute(JobExecutionContext context) throws JobExecutionException {

        websiteParser.parseWebsites("src/main/resources/fashionWebsites/websiteUrls.txt");

    }

}