package setup;

import com.styleme.configuration.ElasticsearchConfiguration;
import com.styleme.setup.ElasticsearchSetup;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsRequest;
import org.elasticsearch.client.Client;
import org.elasticsearch.test.ElasticsearchIntegrationTest;
import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertTrue;

/**
 * @author Eibhlin McGeady
 *
 * Unit test for methods in the ElasticsearchSetup class
 *
 */
public class ElasticsearchSetupTest extends ElasticsearchIntegrationTest {

    private ElasticsearchSetup elasticsearchSetup;
    private ElasticsearchConfiguration elasticsearchConfiguration;
    private Client client;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        client = ElasticsearchIntegrationTest.client();
        elasticsearchConfiguration = new ElasticsearchConfiguration();
        elasticsearchSetup = new ElasticsearchSetup(client, elasticsearchConfiguration);
        elasticsearchSetup.setup();
        super.flushAndRefresh();
    }

    @Test
    public void setupTest() throws IOException, JSONException {
        assertTrue(client.admin().indices().exists(new IndicesExistsRequest(elasticsearchConfiguration.getFashionIndex())).actionGet().isExists());
        assertTrue(client.admin().indices().exists(new IndicesExistsRequest(elasticsearchConfiguration.getMappingsIndex())).actionGet().isExists());
        assertTrue(client.admin().indices().exists(new IndicesExistsRequest(elasticsearchConfiguration.getSitesIndex("womens"))).actionGet().isExists());
        assertTrue(client.admin().indices().exists(new IndicesExistsRequest(elasticsearchConfiguration.getSitesIndex("mens"))).actionGet().isExists());
    }

}
