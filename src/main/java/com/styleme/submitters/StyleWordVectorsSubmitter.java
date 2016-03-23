package com.styleme.submitters;

import com.styleme.pojos.Style;
import com.styleme.retrievers.ElasticsearchRetriever;
import org.deeplearning4j.models.embeddings.wordvectors.WordVectors;
import org.springframework.core.io.ClassPathResource;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;
import java.util.Set;

/**
 * @author Eibhlin McGeady
 *
 * Posts the style word vectors to Elasticsearch
 */
public class StyleWordVectorsSubmitter {

    private ElasticsearchRetriever elasticsearchRetriever;
    private ElasticsearchSubmitter elasticsearchSubmitter;

    public StyleWordVectorsSubmitter() {
        elasticsearchRetriever = new ElasticsearchRetriever();
        elasticsearchSubmitter = new ElasticsearchSubmitter();
    }

    public void getStylesAndPostToElasticsearch(WordVectors wordVectors, String fileName) {
        try {
            File inputFile = new ClassPathResource(fileName).getFile();
            BufferedReader bufferedReader = new BufferedReader(new FileReader(inputFile));
            String word;
            while((word = bufferedReader.readLine()) != null) {
                Collection<String> list = wordVectors.wordsNearest(word, 50);
                postToElasticsearch(word, (Set<String>) list);
            }
            bufferedReader.close();
        } catch (IOException e) {
            System.err.println("Could not open file " + fileName);
        }
    }

    private void postToElasticsearch(String styleName, Set<String> list) {
        Style style = elasticsearchRetriever.getStyle(styleName);
        if(style == null) {
            style = new Style(styleName, list);
        } else {
            style.addTerms(list);
        }
        elasticsearchSubmitter.postStyle(style);
    }
}
