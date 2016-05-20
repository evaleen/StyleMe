package parsers;

import com.styleme.parsers.SentenceParser;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static junit.framework.Assert.assertEquals;

/**
 * @author Eibhlin McGeady
 *
 * Unit tests for methods in the SentenceParser class
 *
 */
public class SentenceParserTest {

    private SentenceParser sentenceParser;
    private String before;
    private String after;

    @Before
    public void setUp() {
        sentenceParser = new SentenceParser();
        before = "’  best place   internet   ---minute …“fashion news";
        after = "  best place   internet   minute fashion news";
    }

    @Test
    public void removeStopWordsAndPunctuationTest() throws IOException {
        String output = sentenceParser.removeStopWordsAndPunctuation(before);
        assertEquals(after, output);
    }

    @After
    public void tearDown() {}


}
