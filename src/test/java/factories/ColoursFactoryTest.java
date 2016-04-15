package factories;

import com.styleme.factories.ColoursFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static junit.framework.Assert.assertEquals;

/**
 * @author Eibhlin McGeady
 */
public class ColoursFactoryTest {

    private ColoursFactory coloursFactory;
    private String description;
    private Set<String> colours;

    @Before
    public void setUp() {
        coloursFactory = new ColoursFactory();
        description = "Petals on-point. The Maureen Dress is cream and features a yellow and blush floral print, " +
                "plunging neckline, ruching at front shoulders, wrap design with tie closure, asymmetric hem, " +
                "and maxi length. Sheer, unlined.";
        colours = new HashSet<>();
        colours.add("cream");
        colours.add("yellow");
        colours.add("pink");
        colours.add("multi");
    }

    @Test
    public void getColoursFromDetailsTest() {
        Set<String> returnedColours = coloursFactory.getColoursFromDetails(description);
        assertEquals(colours, returnedColours);
    }

    @After
    public void tearDown() {}
}
