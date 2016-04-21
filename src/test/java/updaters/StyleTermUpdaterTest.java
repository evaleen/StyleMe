package updaters;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.styleme.configuration.ElasticsearchConfiguration;
import com.styleme.pojos.Style;
import com.styleme.retrievers.ElasticsearchRetriever;
import com.styleme.selectors.StyleSelector;
import com.styleme.submitters.ElasticsearchSubmitter;
import com.styleme.updaters.StyleTermUpdater;
import org.elasticsearch.client.Client;
import org.elasticsearch.test.ElasticsearchIntegrationTest;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

/**
 * @author Eibhlin McGeady
 */
public class StyleTermUpdaterTest extends ElasticsearchIntegrationTest {

    private StyleTermUpdater styleTermUpdater;
    private Client elasticsearchClient;
    private ElasticsearchSubmitter elasticsearchSubmitter;
    private ElasticsearchRetriever elasticsearchRetriever;
    private ElasticsearchConfiguration elasticsearchConfiguration;
    private ObjectMapper objectMapper;
    private String styleName;
    private Style style;
    private Set<String> terms;
    private List<String> incTerms;
    private List<String> decTerms;
    private Set<Map<String, Integer>> incrementedTerms;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        elasticsearchClient = ElasticsearchIntegrationTest.client();
        elasticsearchConfiguration = new ElasticsearchConfiguration();
        objectMapper = new ObjectMapper();
        elasticsearchSubmitter = new ElasticsearchSubmitter(elasticsearchClient, elasticsearchConfiguration, objectMapper);
        elasticsearchRetriever = new ElasticsearchRetriever(elasticsearchClient, elasticsearchConfiguration, new StyleSelector(), objectMapper);
        styleTermUpdater = new StyleTermUpdater(elasticsearchRetriever, elasticsearchSubmitter);
        styleName = "preppy";
        terms = new HashSet<>(Arrays.asList("cute", "uni", "funky"));
        style = new Style(styleName, terms);
        incTerms = Arrays.asList("cute", "uni", "funky");
        decTerms = Arrays.asList("cute", "uni", "funky");

        ElasticsearchIntegrationTest.client().admin().indices().prepareCreate(elasticsearchConfiguration.getFashionIndex()).addMapping(elasticsearchConfiguration.getStyleType(), elasticsearchConfiguration.getStyleMapping()).execute().actionGet();
        ElasticsearchIntegrationTest.client().prepareIndex(elasticsearchConfiguration.getFashionIndex(), elasticsearchConfiguration.getStyleType(), style.getStyle()).setSource(objectMapper.writeValueAsString(style)).execute().actionGet();

        super.ensureGreen(elasticsearchConfiguration.getFashionIndex());
        super.ensureSearchable(elasticsearchConfiguration.getFashionIndex());
    }

    @Test
    public void incrementStyleTermsTest() {
        styleTermUpdater.incrementStyleTerms(styleName, incTerms);
        Style updatedStyle = elasticsearchRetriever.getStyle(styleName);
        Set<Map<String, Integer>> updatedTerms = getUpdatedTerms(style.getTerms(), 1);
        //assertEquals(updatedTerms, updatedStyle.getTerms());
        for(Map<String, Integer> term: updatedTerms) {
            assertTrue(updatedStyle.getTerms().contains(term));
        }
    }

    @Test
    public void decrementStyleTermsTest() {
        styleTermUpdater.decrementStyleTerms(styleName, decTerms);
        Style updatedStyle = elasticsearchRetriever.getStyle(styleName);
        Set<Map<String, Integer>> updatedTerms = getUpdatedTerms(style.getTerms(), -1);
        //assertEquals(updatedTerms, updatedStyle.getTerms());
        for(Map<String, Integer> term: updatedTerms) {
            assertTrue(updatedStyle.getTerms().contains(term));
        }
    }

    public Set<Map<String,Integer>> getUpdatedTerms(Set<Map<String, Integer>> terms, int incrementer) {
        for(Map<String, Integer> term : terms) {
            for(String name : term.keySet())
            term.put(name, term.get(name)+ incrementer);
        }
        return terms;
    }
}
