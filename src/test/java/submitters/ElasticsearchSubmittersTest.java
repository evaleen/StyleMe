package submitters;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.styleme.configuration.ElasticsearchConfiguration;
import com.styleme.pojos.Clothing;
import com.styleme.pojos.Style;
import com.styleme.retrievers.ElasticsearchRetriever;
import com.styleme.selectors.StyleSelector;
import com.styleme.submitters.ElasticsearchSubmitter;
import org.elasticsearch.client.Client;
import org.elasticsearch.test.ElasticsearchIntegrationTest;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;

/**
 * @author Eibhlin McGeady
 *
 * Unit tests for methods in the ElasticsearchSubmitters class
 *
 */
public class ElasticsearchSubmittersTest extends ElasticsearchIntegrationTest {

    private Client elasticsearchClient;
    private ElasticsearchSubmitter elasticsearchSubmitter;
    private ElasticsearchRetriever elasticsearchRetriever;
    private ElasticsearchConfiguration elasticsearchConfiguration;
    private ObjectMapper objectMapper;
    private String styleName;
    private Style style;
    private Clothing clothing;
    private String gender;
    private String type;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        elasticsearchClient = ElasticsearchIntegrationTest.client();
        elasticsearchConfiguration = new ElasticsearchConfiguration();
        objectMapper = new ObjectMapper();
        elasticsearchSubmitter = new ElasticsearchSubmitter(elasticsearchClient, elasticsearchConfiguration, objectMapper);
        elasticsearchRetriever = new ElasticsearchRetriever(elasticsearchClient, elasticsearchConfiguration, new StyleSelector(), objectMapper);
        styleName = "preppy";
        style = new Style(styleName, new HashSet<>(Arrays.asList("cute", "uni")));
        clothing = setUpClothing();
        gender = "womens";
        type = elasticsearchConfiguration.getMotelType();

        ElasticsearchIntegrationTest.client().admin().indices().prepareCreate(elasticsearchConfiguration.getFashionIndex()).execute().actionGet();
        super.ensureGreen(elasticsearchConfiguration.getFashionIndex());
        super.ensureSearchable(elasticsearchConfiguration.getFashionIndex());

        ElasticsearchIntegrationTest.client().admin().indices().prepareCreate(elasticsearchConfiguration.getSitesIndex(gender)).execute().actionGet();
        ElasticsearchIntegrationTest.client().prepareIndex(elasticsearchConfiguration.getSitesIndex(gender), elasticsearchConfiguration.getMissguidedType(), clothing.getId()).setSource(objectMapper.writeValueAsString(clothing)).execute().actionGet();
        super.ensureGreen(elasticsearchConfiguration.getSitesIndex(gender));
        super.ensureSearchable(elasticsearchConfiguration.getSitesIndex(gender));
    }

    @Test
    public void postStyleTest() {
        elasticsearchSubmitter.postStyle(style);
        assertEquals(elasticsearchRetriever.getStyle(styleName), style);
    }

    @Test
    public void postClothingTest() {
        elasticsearchSubmitter.postClothing(type, clothing, gender);
        assertEquals(elasticsearchRetriever.getClothingItem(gender, type, clothing.getId()), clothing);
    }

    private Clothing setUpClothing() {
        Clothing item = new Clothing();
        item.setId("red_dress");
        item.setName("Red Dress");
        return item;
    }
}
