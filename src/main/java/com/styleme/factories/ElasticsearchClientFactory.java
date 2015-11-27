package com.styleme.factories;

import org.elasticsearch.client.Client;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.node.Node;

import static org.elasticsearch.node.NodeBuilder.nodeBuilder;

/**
 *
 * @author Eibhlin McGeady
 *
 * A factory that creates an Elasticsearch client, given a cluster name.
 */
public class ElasticsearchClientFactory {

    private static String clusterName = "recommends";
    private static Node elasticsearchNode;
    private static Client elasticsearchClient;

    public static void setClusterName(String clusterName) {
        ElasticsearchClientFactory.clusterName = clusterName;
    }

    public static Client getClient() {
        if (elasticsearchClient == null) {
            createElasticsearchClient();
        }
        return elasticsearchClient;
    }

    private static void createElasticsearchClient() {
        elasticsearchNode = nodeBuilder().clusterName(clusterName).client(true).settings(Settings.settingsBuilder().put("http.enabled", false)).node();
        elasticsearchClient = elasticsearchNode.client();
    }
}
