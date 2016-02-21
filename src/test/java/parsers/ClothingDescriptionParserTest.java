package parsers;

import com.styleme.parsers.ClothingDescriptionParser;
import junit.framework.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static junit.framework.TestCase.assertEquals;

/**
 * @author Eibhlin McGeady
 */
public class ClothingDescriptionParserTest {

    private ClothingDescriptionParser clothingDescriptionParser;
    private String title;
    private String id;
    private String price;
    private String currency;
    private double convertedPrice;
    private String colour;
    Set<String> colours;

    @Before
    public void setUp() {
        clothingDescriptionParser = new ClothingDescriptionParser();
        title = "Floral Lace Bodycon in Black";
        id = "Floral_Lace_Bodycon_in_Black";
        price = "£25.00";
        currency = "GBP";
        convertedPrice = 25.00;
        colour = "black";
        colours = new HashSet<>();
        colours.add(colour);

    }

    @Test
    public void convertToIdTest() {
        String output = clothingDescriptionParser.convertToId(title);
        assertEquals(id, output);
    }

    @Test
    public void getCurrencyTest() {
        String output = clothingDescriptionParser.getCurrency(price);
        assertEquals(currency, output);
    }

    @Test
    public void convertPriceTest() {
        double output = clothingDescriptionParser.convertPrice(price);
        assertEquals(convertedPrice, output);
    }

    @Test
    public void getColoursFromTitleTest() {
        Set<String> output = clothingDescriptionParser.getColoursFromTitle(title);
        assertEquals(colours, output);
    }

    @After
    public void tearDown() {}
}
