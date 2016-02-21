package pojos;

import com.styleme.pojos.Style;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


import java.util.HashSet;
import java.util.Set;

import static junit.framework.Assert.assertEquals;

/**
 * @author Eibhlin McGeady
 */
public class StyleTest {

    private Style style;
    private String name;
    private Set<String> terms;

    @Before
    public void setUp() {
        name = "Preppy";
        terms = new HashSet<>();
        terms.add("blazer");
        terms.add("chinos");
        style = new Style();
        style.setStyle(name);
        style.setTerms(terms);
    }

    @Test
    public void setGetStyleTest() {
        assertEquals(style.getStyle(), name);
    }

    @Test
    public void setGetTermsTest() {
        assertEquals(style.getTerms(), terms);
    }

    @Test
    public void compareToTest() {
        Style otherStyle = new Style();

        otherStyle.setStyle(name);
        otherStyle.setTerms(terms);
        assertEquals(style.compareTo(otherStyle), 0);
    }

    @After
    public void tearDown() {}
}
