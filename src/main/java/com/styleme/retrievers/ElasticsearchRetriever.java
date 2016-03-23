package com.styleme.retrievers;

import com.styleme.configuration.ElasticsearchConfiguration;
import com.styleme.factories.ElasticsearchClientFactory;
import com.styleme.factories.JSONObjectMapperFactory;
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

    public ElasticsearchRetriever() {
        this(ElasticsearchClientFactory.getClient(), new ElasticsearchConfiguration(), new StyleSelector());
    }

    public ElasticsearchRetriever(Client elasticsearchClient, ElasticsearchConfiguration elasticsearchConfiguration, StyleSelector styleSelector) {
        this.elasticsearchClient = elasticsearchClient;
        this.elasticsearchConfiguration = elasticsearchConfiguration;
        this.styleSelector = styleSelector;
    }

    public List<Clothing> getSearch(String styleName, List<String> types, List<String> colours, List<String> range) {
        Style style = getStyle(styleName);
        System.out.println(style.toString());
        List<Clothing> clothing = getClothing(types, colours, range);
        System.out.println(clothing.size());
        List<Clothing> clothes = styleSelector.getClothingForStyle(style, clothing);
        System.out.println(clothes);
        return clothes;
    }

    public Style getStyle(String style) {
        GetResponse getResponse = elasticsearchClient.prepareGet(elasticsearchConfiguration.getFashionIndex(), elasticsearchConfiguration.getStyleType(), style)
                .execute()
                .actionGet();
        return ElasticsearchResponseParser.getResponseToStyle(getResponse, JSONObjectMapperFactory.getObjectReader(Style.class));
    }

    private List<Clothing> getClothing(List<String> types, List<String> colours, List<String> range){
        SearchRequestBuilder searchRequest = elasticsearchClient.prepareSearch(elasticsearchConfiguration.getSitesIndex())
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
        return ElasticsearchResponseParser.searchResponseToClothingList(response, JSONObjectMapperFactory.getObjectReader(Clothing.class));
    }
}
