package com.styleme.vectorizors;

import com.styleme.submitters.StyleWordVectorsSubmitter;
import org.deeplearning4j.models.embeddings.loader.WordVectorSerializer;
import org.deeplearning4j.models.word2vec.Word2Vec;
import org.deeplearning4j.text.sentenceiterator.LineSentenceIterator;
import org.deeplearning4j.text.sentenceiterator.SentenceIterator;
import org.deeplearning4j.text.sentenceiterator.SentencePreProcessor;
import org.deeplearning4j.text.tokenization.tokenizer.TokenPreProcess;
import org.deeplearning4j.text.tokenization.tokenizer.preprocessor.EndingPreProcessor;
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

            SentenceIterator iter = new LineSentenceIterator(file);
            iter.setPreProcessor(new SentencePreProcessor() {
                @Override
                public String preProcess(String sentence) {
                    return sentence.toLowerCase();
                }
            });


            final EndingPreProcessor preProcessor = new EndingPreProcessor();
            TokenizerFactory tokenizer = new DefaultTokenizerFactory();
            tokenizer.setTokenPreProcessor(new TokenPreProcess() {
                @Override
                public String preProcess(String token) {
                    token = token.toLowerCase();
                    String base = preProcessor.preProcess(token);
                    base = base.replaceAll("\\d", "d");
                    if (base.endsWith("ly")) base = base.substring(0, base.length()-2);
                    if (base.endsWith("ing")) base = base.substring(0, base.length()-3);
                    return base;
                }
            });

            Word2Vec vec = new Word2Vec.Builder()
                    .minWordFrequency(5)
                    .iterations(1)
                    .layerSize(100)
                    .seed(42)
                    .windowSize(5)
                    .iterate(iter)
                    .tokenizerFactory(tokenizer)
                    .build();
            vec.fit();

            // Write word vectors
            WordVectorSerializer.writeWordVectors(vec, "src/main/resources/vectorized_words.txt");

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

