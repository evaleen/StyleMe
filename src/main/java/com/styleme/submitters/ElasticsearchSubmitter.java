package com.styleme.submitters;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.styleme.configuration.ElasticsearchConfiguration;
import com.styleme.factories.ElasticsearchClientFactory;
import com.styleme.pojos.Clothing;
import com.styleme.pojos.Style;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.Client;

/**
 *
 * @author Eibhlin McGeady
 *
 * Posts JSON to Elasticsearch
 * Deletes JSON from Elasticsearch
 *
 */
public class ElasticsearchSubmitter {

    private Client elasticsearchClient;
    private ElasticsearchConfiguration elasticsearchConfiguration;
    private ObjectMapper objectMapper;
//    private String fashionIndex = "fashion";
//    private String styleType = "styles";
//    private String sitesIndex = "sites";

    public ElasticsearchSubmitter() {
        this(ElasticsearchClientFactory.getClient());
        elasticsearchConfiguration = new ElasticsearchConfiguration();
        objectMapper = new ObjectMapper();
    }

    public ElasticsearchSubmitter(Client elasticsearchClient) {
        this.elasticsearchClient = elasticsearchClient;
        elasticsearchConfiguration = new ElasticsearchConfiguration();
    }

    public void postStyle(Style style) {
        try {
            IndexResponse response = elasticsearchClient.prepareIndex(elasticsearchConfiguration.getFashionIndex(), elasticsearchConfiguration.getStyleType(), style.getStyle())
                    .setSource(objectMapper.writeValueAsString(style))
                    .execute()
                    .actionGet();
        } catch (Exception e) {
            System.err.println("Failed to post " + style.getStyle());
        }
    }

    public void postClothing(String type, Clothing clothing, String gender) {
        try {
            IndexResponse response = elasticsearchClient.prepareIndex(elasticsearchConfiguration.getSitesIndex(gender), type, clothing.getId())
                    .setSource(objectMapper.writeValueAsString(clothing))
                    .execute()
                    .actionGet();
        } catch (Exception e) {
            System.err.println("Failed to post " + clothing.getName());
        }
    }
}

