package com.styleme.vectorizors;

import org.deeplearning4j.models.embeddings.loader.WordVectorSerializer;
import org.deeplearning4j.models.word2vec.Word2Vec;
import org.deeplearning4j.text.sentenceiterator.BasicLineIterator;
import org.deeplearning4j.text.sentenceiterator.SentenceIterator;
import org.deeplearning4j.text.tokenization.tokenizer.preprocessor.CommonPreprocessor;
import org.deeplearning4j.text.tokenization.tokenizerfactory.DefaultTokenizerFactory;
import org.deeplearning4j.text.tokenization.tokenizerfactory.TokenizerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.canova.api.util.ClassPathResource;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Collection;

/**
 * @author Eibhlin McGeady
 *
 * Takes in a raw text file and vectorizes the terms in the text
 */
public class Word2VecRawText {

    private static Logger log = LoggerFactory.getLogger(Word2VecRawText.class);

    public void vectorize(String filename) throws Exception {

        log.info("Load & Vectorize Sentences....");
        // Strip white space before and after for each line
        SentenceIterator iter = new BasicLineIterator(filename);
        // Split on white spaces in the line to get words
        TokenizerFactory t = new DefaultTokenizerFactory();
        t.setTokenPreProcessor(new CommonPreprocessor());

        log.info("Building model....");
        Word2Vec vec = new Word2Vec.Builder()
                .minWordFrequency(5)
                .iterations(1)
                .layerSize(100)
                .seed(42)
                .windowSize(5)
                .iterate(iter)
                .tokenizerFactory(t)
                .build();

        log.info("Fitting Word2Vec model....");
        vec.fit();

        log.info("Writing word vectors to text file....");

        // Write word vectors
        WordVectorSerializer.writeWordVectors(vec, "pathToWriteto.txt");

        log.info("Closest Words:");
        Collection<String> lst = vec.wordsNearest("style", 10);
        System.out.println("style" + lst);
        lst = vec.wordsNearest("punk", 10);
        System.out.println("punk" + lst);
        lst = vec.wordsNearest("gothic", 10);
        System.out.println("gothic" + lst);
        lst = vec.wordsNearest("preppy", 10);
        System.out.println("preppy" + lst);
        lst = vec.wordsNearest("leather", 10);
        System.out.println("leather" + lst);
    }
}
