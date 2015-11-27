package com.styleme.setup;

import com.styleme.configuration.ElasticsearchConfiguration;
import com.styleme.factories.ElasticsearchClientFactory;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequestBuilder;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.cluster.ClusterState;
import org.elasticsearch.cluster.metadata.IndexMetaData;
import org.elasticsearch.cluster.metadata.MappingMetaData;
import org.json.JSONException;

import java.io.IOException;
import java.util.Map;

/*
 *
 * @author Eibhlin McGeady
 *
 *  * Sets up ES index with mapping
 */
public class ElasticsearchSetup {

    private Client elasticsearchClient;
    private ElasticsearchConfiguration elasticsearchConfiguration;

    public ElasticsearchSetup() {
        this(ElasticsearchClientFactory.getClient(), new ElasticsearchConfiguration());
    }

    public ElasticsearchSetup(Client elasticsearchClient, ElasticsearchConfiguration elasticsearchConfiguration) {
        this.elasticsearchClient = elasticsearchClient;
        this.elasticsearchConfiguration = elasticsearchConfiguration;
    }

    public void setup() throws IOException, JSONException {
        IndicesExistsResponse indicesExistsResponseFashion = elasticsearchClient.admin().indices().prepareExists(elasticsearchConfiguration.getFashionIndex()).execute().actionGet();
        IndicesExistsResponse indicesExistsResponseSites = elasticsearchClient.admin().indices().prepareExists(elasticsearchConfiguration.getSitesIndex()).execute().actionGet();
        IndicesExistsResponse indicesExistsResponseMappings = elasticsearchClient.admin().indices().prepareExists(elasticsearchConfiguration.getMappingsIndex()).execute().actionGet();


        if(!indicesExistsResponseFashion.isExists()){
            elasticsearchClient.admin().indices().prepareCreate(elasticsearchConfiguration.getFashionIndex()).execute().actionGet();
            System.out.println("Fashion index created");
        }
        if(!indicesExistsResponseSites.isExists()){
            elasticsearchClient.admin().indices().prepareCreate(elasticsearchConfiguration.getSitesIndex()).execute().actionGet();
            System.out.println("Websites index created");
        }
        if (indicesExistsResponseMappings.isExists()) {
            if (!isMappingSet()) {
                System.out.println("No mapping found");
                deleteIndex();
                createMapping();
            } else {
                System.out.println("Everything created");
            }
        } else {
            createMapping();
        }

    }

    private void deleteIndex() {
        DeleteIndexRequestBuilder deleteIndexRequestBuilder = elasticsearchClient.admin().indices().prepareDelete(elasticsearchConfiguration.getMappingsIndex());
        deleteIndexRequestBuilder.execute().actionGet();
        System.out.println("Index deleted");
    }

    private boolean isMappingSet() {
        ClusterState clusterState = elasticsearchClient.admin().cluster().prepareState().setIndices(elasticsearchConfiguration.getMappingsIndex()).execute().actionGet().getState();
        IndexMetaData indexMetaData = clusterState.getMetaData().index(elasticsearchConfiguration.getMappingsIndex());
        MappingMetaData mappingMetaData = indexMetaData.mapping(elasticsearchConfiguration.getTopshopType());
        try {
            Map<String, Object> mapping = mappingMetaData.getSourceAsMap();
            mapping = (Map) mapping.get("properties");
            mapping = (Map) mapping.get("description");
            if (mapping.containsKey("fields")) {
                mapping = (Map) mapping.get("fields");
                mapping = (Map) mapping.get("raw");
                return mapping.get("index").equals("not_analyzed");
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }

    private void createMapping() throws IOException, JSONException {
        String clothingMapping = elasticsearchConfiguration.getClothingMapping();
        String styleMapping = elasticsearchConfiguration.getStyleMapping();
        elasticsearchClient.admin().indices()
                .prepareCreate(elasticsearchConfiguration.getMappingsIndex())
                .addMapping(elasticsearchConfiguration.getAsosType(), clothingMapping)
                .addMapping(elasticsearchConfiguration.getMotelType(), clothingMapping)
                .addMapping(elasticsearchConfiguration.getTopshopType(), clothingMapping)
                .addMapping(elasticsearchConfiguration.getStyleType(), styleMapping)
                .execute().actionGet();
        System.out.println("Mappings index created");
    }
}