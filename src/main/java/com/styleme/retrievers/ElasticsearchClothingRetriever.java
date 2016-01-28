package com.styleme.retrievers;

import com.fasterxml.jackson.databind.ObjectReader;
import com.styleme.configuration.ElasticsearchConfiguration;
import com.styleme.factories.ElasticsearchClientFactory;
import com.styleme.factories.JSONObjectMapperFactory;
import com.styleme.parsers.ElasticsearchResponseParser;
import com.styleme.pojos.Clothing;
import com.styleme.pojos.Style;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.*;

import java.io.IOException;
import java.util.List;

import static org.elasticsearch.index.query.QueryBuilders.boolQuery;
import static org.elasticsearch.index.query.QueryBuilders.termsQuery;

/**
 * @author Eibhlin McGeady
 */
public class ElasticsearchClothingRetriever {

    private Client elasticsearchClient;
    private ObjectReader objectReader;
    private ElasticsearchConfiguration elasticsearchConfiguration;

    public ElasticsearchClothingRetriever() {
        this(ElasticsearchClientFactory.getClient(), JSONObjectMapperFactory.getObjectReader(Clothing.class), new ElasticsearchConfiguration());
    }

    public ElasticsearchClothingRetriever(Client elasticsearchClient, ObjectReader objectReader, ElasticsearchConfiguration elasticsearchConfiguration) {
        this.elasticsearchClient = elasticsearchClient;
        this.objectReader = objectReader;
        this.elasticsearchConfiguration = elasticsearchConfiguration;
    }

    public List<Clothing> getSearch(String styleName, List<String> types, List<String> colours, List<String> range) throws IOException {
        //get style info
        //Style style = getStyle(styleName);
        //get clothing info
        List<Clothing> clothing = getClothing(types, colours, range);
        //compare style to clothing and return clothing with style
        //return styleSelector.getClothingforStyle(style, clothing);
        return clothing;
    }

    private Style getStyle(String style) throws IOException {
        GetResponse getResponse = elasticsearchClient.prepareGet(elasticsearchConfiguration.getFashionIndex(), elasticsearchConfiguration.getStyleType(), style)
                .execute()
                .actionGet();
        return ElasticsearchResponseParser.getResponseToStyle(getResponse, objectReader);

    }

    private List<Clothing> getClothing(List<String> types, List<String> colours, List<String> range) throws IOException {
        SearchRequestBuilder searchRequest = elasticsearchClient.prepareSearch(elasticsearchConfiguration.getSitesIndex())
            .setSearchType(SearchType.DFS_QUERY_THEN_FETCH).setSize(1000);
        BoolQueryBuilder qb = boolQuery();
        double min = Double.parseDouble(range.get(0));
        double max = Double.parseDouble(range.get(1));
        searchRequest.setPostFilter(FilterBuilders.rangeFilter("price").from(min).to(max));
        if(!types.get(0).equals("null")) {
            String[] typesArray = types.toArray(new String[types.size()]);
            searchRequest.setQuery(qb.must(termsQuery("type", typesArray)).minimumShouldMatch("1"));
        }
        if (!colours.get(0).equals("null")) {
            String[] coloursArray = colours.toArray(new String[colours.size()]);
            searchRequest.setQuery(qb.must(termsQuery("colours", coloursArray)).minimumShouldMatch("1"));
        }
        SearchResponse response = searchRequest.execute().actionGet();
        return ElasticsearchResponseParser.searchResponseToClothingList(response, objectReader);
    }
}
