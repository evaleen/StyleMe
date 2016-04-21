package retrievers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.styleme.configuration.ElasticsearchConfiguration;
import com.styleme.pojos.Clothing;
import com.styleme.pojos.Style;
import com.styleme.retrievers.ElasticsearchRetriever;
import com.styleme.selectors.StyleSelector;
import org.elasticsearch.client.ElasticsearchClient;
import org.elasticsearch.test.ElasticsearchIntegrationTest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Eibhlin McGeady
 */
public class ElasticsearchRetreiverTest extends ElasticsearchIntegrationTest {

    private ElasticsearchRetriever elasticsearchRetriever;
    private ElasticsearchConfiguration elasticsearchConfiguration;
    private StyleSelector styleSelector;
    private ObjectMapper objectMapper;
    private Style style;
    private Clothing clothing;
    private Clothing returnedClothing;
    private Set<String> styleTerms;
    private String gender;
    private String styleName;
    private List<String> types;
    private List<String> colours;
    private List<String> range;
    private List<String> incTerms;
    private List<String> decTerms;
    private List<Clothing> clothingList;


    @Before
    public void setUp() throws Exception {
        super.setUp();
        styleName = "preppy";
        gender = "womens";
        types = Arrays.asList("dresses");
        colours = Arrays.asList("red");
        range = Arrays.asList("0", "200");
        incTerms = Arrays.asList("uni");
        decTerms = Arrays.asList("rocker");
        styleTerms = new HashSet<>(Arrays.asList("cute", "uni", "varsity"));
        style = new Style(styleName, styleTerms);
        clothing = setUpClothing();
        returnedClothing = clothing;
        returnedClothing.setScore(6);
        clothingList = Arrays.asList(returnedClothing);
        elasticsearchConfiguration = new ElasticsearchConfiguration();
        styleSelector = new StyleSelector();
        objectMapper = new ObjectMapper();
        elasticsearchRetriever = new ElasticsearchRetriever(ElasticsearchIntegrationTest.client(), elasticsearchConfiguration, styleSelector, objectMapper);

        ElasticsearchIntegrationTest.client().admin().indices().prepareCreate(elasticsearchConfiguration.getFashionIndex()).addMapping(elasticsearchConfiguration.getStyleType(), elasticsearchConfiguration.getStyleMapping()).execute().actionGet();
        ElasticsearchIntegrationTest.client().prepareIndex(elasticsearchConfiguration.getFashionIndex(), elasticsearchConfiguration.getStyleType(), style.getStyle()).setSource(objectMapper.writeValueAsString(style)).execute().actionGet();

        super.ensureGreen(elasticsearchConfiguration.getFashionIndex());
        super.ensureSearchable(elasticsearchConfiguration.getFashionIndex());

        ElasticsearchIntegrationTest.client().admin().indices().prepareCreate(elasticsearchConfiguration.getSitesIndex(gender)).execute().actionGet();
        ElasticsearchIntegrationTest.client().prepareIndex(elasticsearchConfiguration.getSitesIndex(gender), elasticsearchConfiguration.getMissguidedType(), clothing.getId()).setSource(objectMapper.writeValueAsString(clothing)).execute().actionGet();

        super.ensureGreen(elasticsearchConfiguration.getSitesIndex(gender));
        super.ensureSearchable(elasticsearchConfiguration.getSitesIndex(gender));
        super.flushAndRefresh();
    }

    @Test
    public void getSearchSuggestionsTest() {
        List<Clothing> items = elasticsearchRetriever.getSearchSuggestions(gender, styleName, types, colours, range);
        assertEquals(clothingList, items);
    }

    @Test
    public void getSearchTest() {
        List<Clothing> items = elasticsearchRetriever.getSearch(gender, styleName, types, colours, range, incTerms, decTerms);
        assertEquals(clothingList, items);
    }

    @Test
    public void getStyleTest() {
        assertEquals(style, elasticsearchRetriever.getStyle(styleName));
    }

    @Test
    public void getClothingItemTest() {
        assertEquals(returnedClothing, elasticsearchRetriever.getClothingItem(gender, elasticsearchConfiguration.getMissguidedType(), clothing.getId()));
    }

    private Clothing setUpClothing() {
        Clothing item = new Clothing();
        item.setId("varsity_dress");
        item.setDescription("This is a cute preppy dress you could wear to uni");
        item.setName("Varsity Dress");
        item.setType("dresses");
        item.setColours(new HashSet<>(Arrays.asList("red")));
        item.setCurrency("EUR");
        item.setTerms(new HashSet<>(Arrays.asList("funky", "varsity", "uni", "cute")));
        item.setPrice(20.00);
        return item;
    }
}
