package com.styleme.vectorizors;

import com.styleme.submitters.StyleWordVectorsSubmitter;
import org.deeplearning4j.models.embeddings.loader.WordVectorSerializer;
import org.deeplearning4j.models.embeddings.wordvectors.WordVectors;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;

/**
 * @author Eibhlin McGeady
 *
 * Loads the pre-trained Wikipedia vectors
 * Submits the style words to Elasticsearch
 *
 */
public class GloveVectorization {

    private StyleWordVectorsSubmitter styleWordVectorsSubmitter;

    public GloveVectorization() {
        styleWordVectorsSubmitter = new StyleWordVectorsSubmitter();
    }

    public void vectorize(String stylesFileName){
        try {
            File file = new ClassPathResource("glove.6B.300d.txt").getFile();
            WordVectors wordVectors = WordVectorSerializer.loadTxtVectors(file);

            System.out.println("glove");
            styleWordVectorsSubmitter.getStylesAndPostToElasticsearch(wordVectors, stylesFileName);

        } catch (IOException e) {
            System.err.println("Error accessing file");
            e.printStackTrace();
        }

    }
}
