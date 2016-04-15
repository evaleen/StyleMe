package pojos;

import com.styleme.pojos.Style;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static junit.framework.Assert.assertEquals;

/**
 * @author Eibhlin McGeady
 */
public class StyleTest {

    private Style style;
    private String name;
    private Set<Map<String, Integer>> terms;
    private Set<String> setTerms;


    @Before
    public void setUp() {
        name = "Preppy";
        terms = new HashSet<>();
        Map<String, Integer> term1 = new HashMap<>();
        Map<String, Integer> term2 = new HashMap<>();
        term1.put("blazer", 1);
        term2.put("chinos", 1);
        setTerms = new HashSet<>();
        setTerms.add("blazer");
        setTerms.add("chinos");
        terms.add(term1);
        terms.add(term2);
        style = new Style();

    }

    @Test
    public void setGetStyleTest() {
        style.setStyle(name);
        assertEquals(style.getStyle(), name);
    }

    @Test
    public void setGetTermsTest(){
        style.setTerms(terms);
        assertEquals(style.getTerms(), terms);
    }

    @Test
    public void addGetTermsTest() {
        style.addTermsFromSet(setTerms);
        assertEquals(style.getTerms(), terms);
    }

    @After
    public void tearDown() {}
}
