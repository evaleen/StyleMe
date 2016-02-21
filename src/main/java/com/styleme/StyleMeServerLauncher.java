package com.styleme;
import com.styleme.configuration.StyleMeServerConfiguration;
import com.styleme.endpoints.StyleSearchEndpoints;
import com.styleme.parsers.ArticleParser;
import com.styleme.parsers.SentenceParser;
import com.styleme.parsers.WebsiteParser;
import com.styleme.setup.ElasticsearchSetup;
import com.styleme.vectorizors.Word2VecRawText;
import com.yammer.dropwizard.Service;
import com.yammer.dropwizard.config.Bootstrap;
import com.yammer.dropwizard.config.Environment;
import org.eclipse.jetty.servlets.CrossOriginFilter;

/*
 * @author Eibhlin McGeady
 *
 * Server launcher for StyleMe
 */
public class StyleMeServerLauncher  extends Service<StyleMeServerConfiguration> {

        public static void main(String[] args) throws Exception {
            new StyleMeServerLauncher().run(args);
        }

        @Override
        public void initialize(final Bootstrap<StyleMeServerConfiguration> bootstrap) {}

        @Override
        public void run(StyleMeServerConfiguration styleMeServerConfiguration, Environment environment) throws Exception {
            environment.addResource(new StyleSearchEndpoints());
            environment.addFilter(CrossOriginFilter.class, "/*")
                    .setInitParam("allowedOrigins", "*")
                    .setInitParam("allowedHeaders", "X-Requested-With,Content-Type,Accept,Origin")
                    .setInitParam("allowedMethods", "OPTIONS,GET,PUT,POST,HEAD");

//            ElasticsearchSetup elasticsearchSetup = new ElasticsearchSetup();
//            elasticsearchSetup.setup();
//
//            WebsiteParser websiteParser = new WebsiteParser();
//            websiteParser.parseWebsites("src/main/resources/fashionWebsites/websiteUrls.txt");

//            ArticleParser articleParser = new ArticleParser();
//            articleParser.getStyleArticles("articles.txt", "parsed_style_sentences.txt");


//              Word2VecRawText word2Vec = new Word2VecRawText();
//              word2Vec.vectorize("parsed_style_sentences.txt");
    }
}