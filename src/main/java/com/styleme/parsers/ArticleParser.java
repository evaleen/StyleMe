package com.styleme.parsers;

import org.canova.api.util.ClassPathResource;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;

/**
 * @author Eibhlin McGeady
 *
 * Parses the webpages containing style articles from www.chictopia.com
 * Extracts article content and stores it in a file
 */
public class ArticleParser {

    private SentenceParser sentenceParser;

    public ArticleParser() {
        sentenceParser  = new SentenceParser();
    }

    public ArticleParser(SentenceParser sentenceParser) {
        this.sentenceParser = sentenceParser;
    }

    public void getStyleArticles(String fileName, String newFileName) throws FileNotFoundException {

        String filePath = new ClassPathResource(fileName).getFile().getAbsolutePath();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            BufferedWriter writer = new BufferedWriter(new FileWriter(new File(newFileName)));
            String url;
            int i = 0;
            while ((url = reader.readLine()) != null) {
                String articles = parse(url);
                articles = sentenceParser.removeStopWordsAndPunctuation(articles);
                writer.write(articles);
                System.out.println(i);
                i++;

            }
            reader.close();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String parse(String url) {
        String text = "";
        Document doc;
        try {
            doc = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.152 Safari/537.36")
                    .timeout(100000)
                    .get();
            Elements elements = doc.getElementsByClass("action_photo");
            for(Element element : elements) {
                Element page = element.select("a").first();
                text += getPageContent(page.absUrl("href"));
            }
        } catch(Exception e) {}
        return text;
    }

    private String getPageContent(String url) throws IOException {
        Document doc = Jsoup.connect(url)
                .userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.152 Safari/537.36")
                .timeout(100000)
                .get();
        Elements element = doc.select("div[itemprop]");
        String text = "";
        try {
            text = element.get(0).text();
        } catch (Exception e) {}
        return text;
    }
}