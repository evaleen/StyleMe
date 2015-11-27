package com.styleme.submitters;


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
 */
public class ElasticsearchSubmitter {

    private Client elasticsearchClient;
    private String fashionIndex = "fashion";
    private String styleType = "styles";
    private String sitesIndex = "sites";

    public ElasticsearchSubmitter() {
        this(ElasticsearchClientFactory.getClient());
    }

    public ElasticsearchSubmitter(Client elasticsearchClient) {
        this.elasticsearchClient = elasticsearchClient;
    }

    public void postStyle(Style style) {
        try {
            IndexResponse response = elasticsearchClient.prepareIndex(fashionIndex, styleType, style.getStyle())
                    .setSource(style)
                    .execute()
                    .actionGet();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void postClothing(String type, Clothing clothing) {
        try {
            IndexResponse response = elasticsearchClient.prepareIndex(sitesIndex, type, clothing.getId())
                    .setSource(clothing)
                    .execute()
                    .actionGet();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

}

