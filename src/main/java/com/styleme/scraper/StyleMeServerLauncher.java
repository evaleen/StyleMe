package com.styleme.scraper;
import com.styleme.configuration.StyleMeServerConfiguration;
import com.styleme.endpoints.StyleSearchEndpoints;
import com.styleme.parsers.WebsiteParser;
import com.styleme.setup.ElasticsearchSetup;
import com.yammer.dropwizard.Service;
import com.yammer.dropwizard.config.Bootstrap;
import com.yammer.dropwizard.config.Environment;
import org.eclipse.jetty.servlets.CrossOriginFilter;

import java.io.IOException;


public class StyleMeServerLauncher  extends Service<StyleMeServerConfiguration> {

        public static void main(String[] args) throws Exception {
            new StyleMeServerLauncher().run(args);
        }

        @Override
        public void initialize(final Bootstrap<StyleMeServerConfiguration> bootstrap) {
        }

        @Override
        public void run(StyleMeServerConfiguration argoRecommendsServerConfiguration, Environment environment) throws Exception {
            environment.addResource(new StyleSearchEndpoints());
            environment.addFilter(CrossOriginFilter.class, "/*")
                    .setInitParam("allowedOrigins", "*")
                    .setInitParam("allowedHeaders", "X-Requested-With,Content-Type,Accept,Origin")
                    .setInitParam("allowedMethods", "OPTIONS,GET,PUT,POST,HEAD");

        //ElasticsearchSetup elasticsearchSetup = new ElasticsearchSetup();
        //elasticsearchSetup.setup();

        //WebsiteParser websiteParser = new WebsiteParser();
        //websiteParser.parseWebsites("src/main/resources/fashionWebsites/websiteUrls.txt");
    }
}