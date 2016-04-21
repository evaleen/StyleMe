package endpoints;

import com.styleme.endpoints.StyleSearchEndpoints;
import com.styleme.pojos.Clothing;
import com.styleme.retrievers.ElasticsearchRetriever;
import com.styleme.updaters.StyleTermUpdater;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.when;

/**
 * @author Eibhlin McGeady
 */
public class StyleSearchEndpointsTest {

    private StyleSearchEndpoints styleSearchEndpoints;
    private ElasticsearchRetriever elasticsearchRetriever;
    List<Clothing> suggestedClothing;
    List<Clothing> returnedClothing;
    private String gender;
    private String style;
    private List<String> types;
    private List<String> colours;
    private List<String> range;
    private List<String> incTerms;
    private List<String> decTerms;

    @Before
    public void setUp() {
        elasticsearchRetriever = Mockito.mock(ElasticsearchRetriever.class);
        StyleTermUpdater styleTermUpdater = Mockito.mock(StyleTermUpdater.class);
        styleSearchEndpoints = new StyleSearchEndpoints(elasticsearchRetriever, styleTermUpdater);
        suggestedClothing = new ArrayList<>();
        suggestedClothing.add(new Clothing("test"));
        returnedClothing = new ArrayList<>();
        returnedClothing.add(new Clothing("test1"));
        gender = "womens";
        style = "punk";
        types = Arrays.asList("dresses", "tops");
        colours = Arrays.asList("black");
        range = Arrays.asList("0", "500");

        incTerms = Arrays.asList("preppy");
        decTerms = Arrays.asList("chic");
    }

    @Test
    public void getClothingSuggestionsTest() {
        when(elasticsearchRetriever.getSearchSuggestions(gender, style, types, colours, range)).thenReturn(suggestedClothing);
        List<Clothing> clothing = styleSearchEndpoints.getClothingSuggestions(gender, style, types, colours, range);
        assertEquals(clothing, suggestedClothing);
    }

    @Test
    public void getClothingTest() {
        when(elasticsearchRetriever.getSearch(gender, style, types, colours, range, incTerms, decTerms)).thenReturn(returnedClothing);
        List<Clothing> clothing = styleSearchEndpoints.getClothing(gender, style, types, colours, range, incTerms, decTerms);
        assertEquals(clothing, returnedClothing);
    }

    @After
    public void tearDown() {}

}
