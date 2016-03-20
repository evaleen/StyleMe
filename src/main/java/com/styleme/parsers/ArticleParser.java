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
 * Parses the webpages containing style articles from fashion blogs including:
 * www.chictopia.com - chictopia
 * www.hellofashionblog.com - hello
 * www.kyrzayda.com - kyrzayda
 * www.grasiemercedes.com grasie
 * www.thefashionguitar.com - guitar
 * www.lusttforlife.com - lust
 * www.thechroniclesofher.com -tcoh
 * www.zanita.com - zanita
 * www.brooketestoni.com - brooke
 * Extracts article content and stores it in a file
 */
public class ArticleParser {

    private SentenceParser sentenceParser;
    //private Porter porter;

    public ArticleParser() {
        sentenceParser  = new SentenceParser();
        //porter = new Porter();
    }

    public ArticleParser(SentenceParser sentenceParser) {
        this.sentenceParser = sentenceParser;
    }

    public void getStyleArticles(String fileName, String newFileName) {

        try {
            String filePath = new ClassPathResource(fileName).getFile().getAbsolutePath();

            String newFilePath = "src/main/resources/" + newFileName;
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            BufferedWriter writer = new BufferedWriter(new FileWriter(new File(newFilePath)));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] url = line.split(" ");
                String articles = getDocAndParse(url[0], url[1]);
                articles = sentenceParser.removeStopWordsAndPunctuation(articles);
                //articles = porter.stripAffixes(articles);
                writer.write(articles + "\n");

            }
            reader.close();
            writer.close();
        } catch (FileNotFoundException e) {
            System.err.println("Error File not found " + fileName);
        } catch (IOException e) {
            System.err.println("IO Error writing to " + newFileName);
        }
    }

    private String getDocAndParse(String name, String url) {
        try {
            Document doc = Jsoup.connect(url).userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.152 Safari/537.36")
                    .timeout(100000).get();
            return parse(name, doc);
        } catch(Exception e) {
            System.err.printf("Error connecting to %s%n", url);
            return "";
        }
    }

    private String parse(String name, Document doc) {
        switch(name) {
            case "chictopia" : return parseChictopia(doc);
            case "hello"     : return parseBlog(doc, "post");
            case "kyrzayda"  : return parseContinueReadingBlog(doc, "more-link", "post-entry");
            case "grasie"    : return parseContinueReadingBlog(doc, "read-more-image", "article-content");
            case "guitar"    : return parseBlog(doc, "postcontent");
            case "lust"      : return parseContinueReadingBlog(doc, "btn", "post");
            case "tcoh"      : return parseListBlog(doc, "rmlink", "post-body");
            case "zanita"    : return parseListBlog(doc, "title", "main-content");
            case "brooke"    : return parseListBlog(doc, "read-a-post", "entry");
        }
        return "";
    }

    private String parseChictopia(Document doc) {
        String text = "";
        Elements elements = doc.getElementsByClass("action_photo");
        for(Element element : elements) {
            Element page = element.select("a").first();
            text += getPageContent(page.absUrl("href")) + "\n";
        }
        return text;
    }

    private String parseContinueReadingBlog(Document doc, String linkName, String postClassName) {
        String text = "";
        Elements links = doc.getElementsByClass(linkName);
        System.out.println("num links " + links.size());
        for(Element link : links) {
            if(link.hasAttr("href")) {
                text += connectToBlog(link, postClassName);
            }
        }
        return text;
    }

    private String parseListBlog(Document doc, String linkName, String postClassName) {
        String text = "";
        Elements links = doc.getElementsByClass(linkName);
        for(Element link : links) {
            Element a = link.child(0);
            text += connectToBlog(a, postClassName);
        }
        return text;
    }

    private String connectToBlog(Element link, String postClassName) {
        String text = "";
        try {
            Document blog = Jsoup.connect(link.absUrl("href"))
                .userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.152 Safari/537.36")
                .timeout(100000)
                .get();
            text = parseBlog(blog, postClassName) + "\n";
        } catch (IOException e) {
            System.err.println("Error connecting to " + link.absUrl("href"));
        }
        return text;
    }

    private String parseBlog(Document doc, String postClassName) {
        String text = "";
        Elements posts = doc.getElementsByClass(postClassName);
        for(Element post : posts) {
            text += post.text();
            System.out.println(post.text());
        }
        return text;
    }

    private String getPageContent(String url) {
        String text = "";
        try {
            Document doc = Jsoup.connect(url)
                .userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.152 Safari/537.36")
                .timeout(100000)
                .get();
            Elements element = doc.select("div[itemprop]");
            text = element.get(0).text();
        } catch (Exception e) {
            System.err.println("Error connecting to " + url);
        }
        return text;
    }
}