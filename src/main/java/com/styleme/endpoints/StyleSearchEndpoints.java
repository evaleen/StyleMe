package com.styleme.endpoints;

import java.io.IOException;
import java.util.List;

import com.styleme.configuration.ElasticsearchConfiguration;
import com.styleme.pojos.Clothing;
import com.styleme.retrievers.ElasticsearchClothingRetriever;
import org.elasticsearch.index.mapper.SourceToParse;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * @author Eibhlin McGeady
 */

@Path("/style")
@Produces(MediaType.APPLICATION_JSON)
public class StyleSearchEndpoints {

    private ElasticsearchClothingRetriever elasticsearchClothingRetriever;
    private ElasticsearchConfiguration elasticsearchConfiguration;


    public StyleSearchEndpoints() {
        this(new ElasticsearchClothingRetriever(), new ElasticsearchConfiguration());
    }

    public StyleSearchEndpoints(ElasticsearchClothingRetriever elasticsearchClothingRetriever, ElasticsearchConfiguration elasticsearchConfiguration) {
        this.elasticsearchClothingRetriever = elasticsearchClothingRetriever;
        this.elasticsearchConfiguration = elasticsearchConfiguration;
    }

    @Path("/clothes")
    @GET()
    @Consumes(MediaType.APPLICATION_JSON)
    public List<Clothing> getClothing(@QueryParam("style") String style, @QueryParam("types") List<String> types, @QueryParam("colours") List<String> colours,
                                      @QueryParam("range") List<String> range) throws IOException {
        return elasticsearchClothingRetriever.getSearch(style, types, colours, range);
    }

}