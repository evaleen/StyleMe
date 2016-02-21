package parsers;

import com.styleme.parsers.SentenceParser;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static junit.framework.Assert.assertEquals;

/**
 * @author Eibhlin McGeady
 */
public class SentenceParserTest {

    private SentenceParser sentenceParser;
    private String before;
    private String after;

    @Before
    public void setUp() {
        sentenceParser = new SentenceParser();
        before = "This . is a sente*nce with pu!n?c/tuation! and s%top wo'rds";
        after  = "    sentence  punctuation  stop words";
    }

    @Test
    public void removeStopWordsAndPunctuationTest() throws IOException {
        String output = sentenceParser.removeStopWordsAndPunctuation(before);
        assertEquals(output, after);
    }

    @After
    public void tearDown() {}


}
