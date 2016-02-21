package com.styleme.parsers;

import org.canova.api.util.ClassPathResource;

import java.io.*;
import java.util.HashSet;

/**
 * @author Eibhlin McGeady
 *
 * Parses a sentence to remove common words and punctuation
 *
 */
public class SentenceParser {

    public String removeStopWordsAndPunctuation(String sentences) throws IOException {
        sentences = sentences.toLowerCase();
        HashSet<String> stopWords = getStopWords();
        for(String word : stopWords) {
            sentences = sentences.replaceAll("\\b" + word + "\\b", "");
        }
        sentences = sentences.replaceAll("\\p{P}","");
        return sentences;
    }

    private HashSet<String> getStopWords() throws IOException {
        String filePath = new ClassPathResource("StopWords.txt").getFile().getAbsolutePath();
        HashSet<String> words = new HashSet<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String word;
            while((word = reader.readLine()) != null) {
                words.add(word);
            }
            reader.close();
        } catch (Exception e) {
        e.printStackTrace();
    }
        return words;
    }
}
