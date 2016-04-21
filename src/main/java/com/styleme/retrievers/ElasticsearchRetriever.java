package com.styleme.retrievers;

import com.styleme.configuration.ElasticsearchConfiguration;
import com.styleme.factories.ElasticsearchClientFactory;
import com.styleme.parsers.ElasticsearchResponseParser;
import com.styleme.pojos.Clothing;
import com.styleme.pojos.Style;
import com.styleme.selectors.StyleSelector;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.*;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

import static org.elasticsearch.index.query.QueryBuilders.boolQuery;
import static org.elasticsearch.index.query.QueryBuilders.termsQuery;

/**
 * @author Eibhlin McGeady
 *
 * Retrieved clothing JSON objects from Elasticsearch
 */
public class ElasticsearchRetriever {

    private Client elasticsearchClient;
    private ElasticsearchConfiguration elasticsearchConfiguration;
    private StyleSelector styleSelector;
    private ObjectMapper objectMapper;

    public ElasticsearchRetriever() {
        this(ElasticsearchClientFactory.getClient(), new ElasticsearchConfiguration(), new StyleSelector(), new ObjectMapper());
    }

    public ElasticsearchRetriever(Client elasticsearchClient, ElasticsearchConfiguration elasticsearchConfiguration, StyleSelector styleSelector, ObjectMapper objectMapper) {
        this.elasticsearchClient = elasticsearchClient;
        this.elasticsearchConfiguration = elasticsearchConfiguration;
        this.styleSelector = styleSelector;
        this.objectMapper = objectMapper;
    }

    public List<Clothing> getSearchSuggestions(String gender, String styleName, List<String> types, List<String> colours, List<String> range) {
        Style style = getStyle(styleName);
        List<Clothing> clothing = getClothing(gender, types, colours, range);
        return styleSelector.getClothingSuggestionsForStyle(style, clothing);
    }

    public List<Clothing> getSearch(String gender, String styleName, List<String> types, List<String> colours, List<String> range, List<String> incTerms, List<String> decTerms) {
        Style style = getStyle(styleName);
        List<Clothing> clothing = getClothing(gender, types, colours, range);
        return styleSelector.getClothingWithUpdatedScores(style, clothing, incTerms, decTerms);
    }

    public Style getStyle(String style) {
        GetResponse getResponse = elasticsearchClient.prepareGet(elasticsearchConfiguration.getFashionIndex(), elasticsearchConfiguration.getStyleType(), style)
                .execute()
                .actionGet();
        return ElasticsearchResponseParser.getResponseToStyle(getResponse, objectMapper.reader(Style.class));
    }

    public Clothing getClothingItem(String gender, String type, String id) {
        GetResponse getResponse = elasticsearchClient.prepareGet(elasticsearchConfiguration.getSitesIndex(gender), type, id)
                .execute()
                .actionGet();
        return ElasticsearchResponseParser.getResponseToClothing(getResponse, objectMapper.reader(Clothing.class));
    }

    private List<Clothing> getClothing(String gender, List<String> types, List<String> colours, List<String> range){
        SearchRequestBuilder searchRequest = elasticsearchClient.prepareSearch(elasticsearchConfiguration.getSitesIndex(gender))
            .setSearchType(SearchType.DFS_QUERY_THEN_FETCH).setSize(10000);
        BoolQueryBuilder qb = boolQuery();
        double min = Double.parseDouble(range.get(0));
        double max = Double.parseDouble(range.get(1));
        searchRequest.setPostFilter(FilterBuilders.rangeFilter("price").from(min).to(max));
        if(!types.get(0).equals("")) {
            String[] typesArray = types.toArray(new String[types.size()]);
            searchRequest.setQuery(qb.must(termsQuery("type", typesArray)).minimumShouldMatch("1"));
        }
        if (!colours.get(0).equals("")) {
            String[] coloursArray = colours.toArray(new String[colours.size()]);
            searchRequest.setQuery(qb.must(termsQuery("colours", coloursArray)).minimumShouldMatch("1"));
        }
        SearchResponse response = searchRequest.execute().actionGet();
        return ElasticsearchResponseParser.searchResponseToClothingList(response, objectMapper.reader(Clothing.class));
    }
}
