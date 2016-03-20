package com.styleme.endpoints;

import java.util.List;

import com.styleme.configuration.ElasticsearchConfiguration;
import com.styleme.pojos.Clothing;
import com.styleme.retrievers.ElasticsearchRetriever;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * @author Eibhlin McGeady
 *
 * Endoints that receive the Ajax request from the frontend and return the relevant information
 *
 */

@Path("/style")
@Produces(MediaType.APPLICATION_JSON)
public class StyleSearchEndpoints {

    private ElasticsearchRetriever elasticsearchClothingRetriever;

    public StyleSearchEndpoints() {
        this(new ElasticsearchRetriever(), new ElasticsearchConfiguration());
    }

    public StyleSearchEndpoints(ElasticsearchRetriever elasticsearchClothingRetriever, ElasticsearchConfiguration elasticsearchConfiguration) {
        this.elasticsearchClothingRetriever = elasticsearchClothingRetriever;
    }

    @Path("/clothes")
    @GET()
    @Consumes(MediaType.APPLICATION_JSON)
    public List<Clothing> getClothing(@QueryParam("style") String style, @QueryParam("types") List<String> types, @QueryParam("colours") List<String> colours,
                                      @QueryParam("range") List<String> range) {
        return elasticsearchClothingRetriever.getSearch(style, types, colours, range);
    }

}
