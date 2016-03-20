package com.styleme.parsers;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectReader;
import com.styleme.pojos.Clothing;
import com.styleme.pojos.Style;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.common.collect.Lists;
import org.elasticsearch.search.SearchHit;

import java.io.IOException;
import java.util.List;

/**
 * @author Eibhlin McGeady
 *
 * Parses the search response from an Elasticsearch get query.
 *
 */
public class ElasticsearchResponseParser {

    public static Style getResponseToStyle(GetResponse getResponse, ObjectReader objectReader){
        Style style = null;
        try {
            String source = getResponse.getSourceAsString();
            if(source != null) {
                style = objectReader.readValue(source);
            }
        } catch (Exception e) {
            System.err.println("IO Exception error");
            System.out.println(e);
        }
        return style;
    }

    public static List<Clothing> searchResponseToClothingList(SearchResponse response, ObjectReader objectReader) {
        try {
            MappingIterator<Clothing> deserializedValues = objectReader.readValues(searchResponseToString(response));
            List<Clothing> desrializedValueList = Lists.newArrayList(deserializedValues);
            deserializedValues.close();
            return desrializedValueList;
        } catch (IOException e) {
            System.err.println("Error converting search response to Clothing list");
            e.printStackTrace();
            return null;
        }
    }

    private static String searchResponseToString(SearchResponse response) {
        StringBuilder string = new StringBuilder();
        string.append('[');
        for (SearchHit hit : response.getHits().getHits()) {
            string.append(hit.getSourceAsString());
            string.append(',');
        }
        if (string.length() > 1) {
            string.deleteCharAt(string.length() - 1);
        }
        string.append(']');
        return string.toString();
    }
}
