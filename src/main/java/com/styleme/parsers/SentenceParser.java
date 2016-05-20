package com.styleme.parsers;

import org.canova.api.util.ClassPathResource;

import java.io.*;
import java.util.HashSet;

/**
 * @author Eibhlin McGeady
 *
 * Parses text to remove stop words and punctuation
 *
 */
public class SentenceParser {

    public String removeStopWordsAndPunctuation(String sentences) {
        sentences = sentences.toLowerCase();
        sentences = sentences.replaceAll("[.!?]", " .");
        sentences = sentences.replaceAll("[\\p{Punct}&&[^.-]]+", "");
        sentences = sentences.replaceAll("[“”’…]", "");
        sentences = sentences.replace("---", "");
        sentences = sentences.replace("- ", "");
        sentences = sentences.replace(" -", "");
        HashSet<String> stopWords = getStopWords();
        for(String word : stopWords) {
            sentences = sentences.replaceAll("\\b" + word + "\\b", "");
        }

        return sentences;
    }

    private HashSet<String> getStopWords() {
        HashSet<String> words = new HashSet<>();
        try {
            String filePath = new ClassPathResource("StopWords.txt").getFile().getAbsolutePath();
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String word;
            while((word = reader.readLine()) != null) {
                words.add(word);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return words;
    }
}
