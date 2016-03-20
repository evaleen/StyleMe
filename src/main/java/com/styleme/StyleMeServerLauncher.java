package com.styleme;
import com.styleme.configuration.StyleMeServerConfiguration;
import com.styleme.endpoints.StyleSearchEndpoints;
import com.styleme.jobs.WebsiteParserJob;
import com.styleme.parsers.ArticleParser;
import com.styleme.setup.ElasticsearchSetup;
import com.styleme.vectorizors.GloveVectorization;
import com.styleme.vectorizors.Word2VecRawText;
import com.yammer.dropwizard.Service;
import com.yammer.dropwizard.config.Bootstrap;
import com.yammer.dropwizard.config.Environment;
import org.eclipse.jetty.servlets.CrossOriginFilter;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

/*
 * @author Eibhlin McGeady
 *
 * Server launcher for StyleMe
 */
public class StyleMeServerLauncher  extends Service<StyleMeServerConfiguration> {

    private static ElasticsearchSetup elasticsearchSetup;
    private static SchedulerFactory scheduleFactory;
    private static Scheduler scheduler;
    private static ArticleParser articleParser;
    private static Word2VecRawText word2Vec;
    private static GloveVectorization gloveVectorization;
    private String parsedStyleSentencesFile = "parsed_style_sentences.txt";
    private String stylesFile = "styles.txt";
    private String articlesFile = "articles.txt";

    public static void main(String[] args) throws Exception {

        elasticsearchSetup = new ElasticsearchSetup();
        scheduleFactory = new StdSchedulerFactory();
        articleParser = new ArticleParser();
        word2Vec = new Word2VecRawText();
        gloveVectorization = new GloveVectorization();

            new StyleMeServerLauncher().run(args);
    }

    @Override
    public void initialize(final Bootstrap<StyleMeServerConfiguration> bootstrap) {}

    @Override
    public void run(StyleMeServerConfiguration styleMeServerConfiguration, Environment environment) {
        environment.addResource(new StyleSearchEndpoints());
        environment.addFilter(CrossOriginFilter.class, "/*")
                .setInitParam("allowedOrigins", "*")
                .setInitParam("allowedHeaders", "X-Requested-With,Content-Type,Accept,Origin")
                .setInitParam("allowedMethods", "OPTIONS,GET,PUT,POST,HEAD");

        elasticsearchSetup.setup();

        try {
            scheduler = scheduleFactory.getScheduler();
            JobDetail job = newJob(WebsiteParserJob.class)
                    .withIdentity("WebsiteParserJob", "group1")
                    .build();
            CronTrigger trigger = newTrigger()
                    .withIdentity("WebsiteParserTrigger", "group1")
                    .withSchedule(cronSchedule("0 0 0 1 1/1 ? *"))
                    .build();
            scheduler.scheduleJob(job, trigger);
        } catch (SchedulerException e) {
            System.err.println("Scheduler error with WebsiteParserJob");
        }

        //articleParser.getStyleArticles(articlesFile, parsedStyleSentencesFile);
        //word2Vec.vectorize(parsedStyleSentencesFile, stylesFile);
        //gloveVectorization.vectorize(stylesFile);
    }
}