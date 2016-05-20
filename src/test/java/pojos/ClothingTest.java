package pojos;

import com.styleme.pojos.Clothing;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static junit.framework.Assert.assertEquals;

/**
 * @author Eibhlin McGeady
 *
 * Unit tests for methods in the Clothing POJO class
 *
 */
public class ClothingTest {

    private Clothing clothing;
    private String id;
    private String name;
    private String type;
    private String description;
    private double price;
    private String currency;
    private String url;
    private String image;
    private Set<String> colours;
    private Set<String> terms;
    private int score;

    @Before
    public void setUp() {
        clothing = new Clothing();
        id = "123";
        name = "Black Dress";
        type = "dress";
        description = "This is a black dress";
        price = 50.00;
        currency = "EUR";
        url = "www.asos.ie/black/dress";
        image = "www.asos.ie/image/black/dress";
        colours = new HashSet<>();
        colours.add("black");
        terms = new HashSet<>();
        terms.add("funky");
        score = 2;

    }

    @Test
    public void setGetIdTest() {
        clothing.setId(id);
        assertEquals(clothing.getId(), id);
    }

    @Test
    public void setGetNameTest() {
        clothing.setName(name);
        assertEquals(clothing.getName(), name);
    }

    @Test
    public void setGetTypeTest() {
        clothing.setType(type);
        assertEquals(clothing.getType(), type);
    }

    @Test
    public void setGetDescriptionTest() {
        clothing.setDescription(description);
        assertEquals(clothing.getDescription(), description);
    }

    @Test
    public void setGetPriceTest() {
        clothing.setPrice(price);
        assertEquals(clothing.getPrice(), price);
    }

    @Test
    public void setGetCurrencyTest() {
        clothing.setCurrency(currency);
        assertEquals(clothing.getCurrency(), currency);
    }

    @Test
    public void setGetUrlTest() {
        clothing.setUrl(url);
        assertEquals(clothing.getUrl(), url);
    }

    @Test
    public void setGetImageTest() {
        clothing.setImage(image);
        assertEquals(clothing.getImage(), image);
    }

    @Test
    public void setGetColoursTest() {
        clothing.setColours(colours);
        assertEquals(clothing.getColours(), colours);
    }

    @Test
    public void setGetTermsTest() {
        clothing.setTerms(terms);
        assertEquals(clothing.getTerms(), terms);
    }

    @Test
    public void addGetTermsTest() {
        clothing.setTerms(terms);
        clothing.addTerm("chic");
        terms.add("chic");
        assertEquals(clothing.getTerms(), terms);
    }

    @Test
    public void setGetScoreTest() {
        clothing.setScore(score);
        assertEquals(clothing.getScore(), score);
    }

    @After
    public void tearDown() {}
}
