package com.styleme.retrievers;

import com.styleme.pojos.Clothing;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Eibhlin McGeady
 */
public class ElasticsearchClothingRetriever {

    public ElasticsearchClothingRetriever() {}

    public List<Clothing> getSearch(String style, List<String> types, List<String> colours, List<String> range, List<String> websites) {
        //get from elasticsearch
        //run fancy algorithm
        //return
        List<Clothing> items = new ArrayList<>();
        items.add(new Clothing("Test Item", "top", "This is a test top.", "20", null));
        return items;
    }
}
