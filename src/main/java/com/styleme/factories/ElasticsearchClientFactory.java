package com.styleme.factories;

import org.elasticsearch.client.Client;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.node.Node;

import static org.elasticsearch.node.NodeBuilder.nodeBuilder;

/**
 *
 * @author Eibhlin McGeady
 *
 * A factory that creates an Elasticsearch client, with the cluster name recommends.
 *
 */
public class ElasticsearchClientFactory {

    private static String clusterName = "recommends";
    private static Client elasticsearchClient;

    public static Client getClient() {
        if (elasticsearchClient == null) {
            createElasticsearchClient();
        }
        return elasticsearchClient;
    }

    private static void createElasticsearchClient() {
        ImmutableSettings.Builder elasticsearchSettings = ImmutableSettings.settingsBuilder();
        Node elasticsearchNode = nodeBuilder().clusterName(clusterName).client(true).settings(elasticsearchSettings).node();
                elasticsearchClient = elasticsearchNode.client();
    }
}
