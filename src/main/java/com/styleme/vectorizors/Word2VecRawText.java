package com.styleme.vectorizors;

import com.styleme.submitters.StyleWordVectorsSubmitter;
import org.deeplearning4j.models.embeddings.loader.WordVectorSerializer;
import org.deeplearning4j.models.embeddings.wordvectors.WordVectors;
import org.deeplearning4j.models.word2vec.Word2Vec;
import org.deeplearning4j.text.sentenceiterator.BasicLineIterator;
import org.deeplearning4j.text.sentenceiterator.SentenceIterator;
import org.deeplearning4j.text.tokenization.tokenizer.preprocessor.CommonPreprocessor;
import org.deeplearning4j.text.tokenization.tokenizerfactory.DefaultTokenizerFactory;
import org.deeplearning4j.text.tokenization.tokenizerfactory.TokenizerFactory;

import org.canova.api.util.ClassPathResource;

import java.io.*;

/**
 * @author Eibhlin McGeady
 *
 * Takes in a raw text file containing style/fashion blog sentences
 * vectorizes the terms in the text
 *
 */
public class Word2VecRawText {

    private StyleWordVectorsSubmitter styleWordVectorsSubmitter;
    
    public Word2VecRawText() {
        styleWordVectorsSubmitter = new StyleWordVectorsSubmitter();
    }

    public void vectorize(String fileName, String styleFileName){
        try {
            File file = new ClassPathResource(fileName).getFile();

            SentenceIterator iter = new BasicLineIterator(file);
            TokenizerFactory t = new DefaultTokenizerFactory();
            t.setTokenPreProcessor(new CommonPreprocessor());

            Word2Vec vec = new Word2Vec.Builder()
                    .minWordFrequency(5)
                    .iterations(1)
                    .layerSize(100)
                    .seed(42)
                    .windowSize(5)
                    .iterate(iter)
                    .tokenizerFactory(t)
                    .build();

            vec.fit();

            // Write word vectors
            WordVectorSerializer.writeWordVectors(vec, "src/main/resources/vectorized_words.txt");

            //Word2Vec vec = WordVectorSerializer.loadFullModel("src/main/resources/vectorized_words.txt");

            styleWordVectorsSubmitter.getStylesAndPostToElasticsearch(vec, styleFileName);

        } catch (FileNotFoundException e) {
            System.err.println("File not found");
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("Error writing vectors to vectorized_words.txt");
            e.printStackTrace();
        }
    }
}

