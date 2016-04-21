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

    public ElasticsearchSubmitter() {
        this(ElasticsearchClientFactory.getClient(), new ElasticsearchConfiguration(), new ObjectMapper());
    }

    public ElasticsearchSubmitter(Client elasticsearchClient, ElasticsearchConfiguration elasticsearchConfiguration, ObjectMapper objectMapper) {
        this.elasticsearchClient = elasticsearchClient;
        this.elasticsearchConfiguration = elasticsearchConfiguration;
        this.objectMapper = objectMapper;
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

