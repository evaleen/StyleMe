package com.styleme.endpoints;

import java.util.List;

import com.styleme.pojos.Clothing;
import com.styleme.retrievers.ElasticsearchRetriever;
import com.styleme.updaters.StyleTermUpdater;

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

    private ElasticsearchRetriever elasticsearchRetriever;
    private StyleTermUpdater styleTermUpdater;

    public StyleSearchEndpoints() {
        this(new ElasticsearchRetriever(), new StyleTermUpdater());
    }

    public StyleSearchEndpoints(ElasticsearchRetriever elasticsearchRetriever, StyleTermUpdater styleTermUpdater) {
        this.elasticsearchRetriever = elasticsearchRetriever;
        this.styleTermUpdater = styleTermUpdater;
    }

    @Path("/clothesSuggestions")
    @GET()
    @Consumes(MediaType.APPLICATION_JSON)
    public List<Clothing> getClothingSuggestions(@QueryParam("gender") String gender, @QueryParam("style") String style, @QueryParam("types") List<String> types, @QueryParam("colours") List<String> colours,
                                      @QueryParam("range") List<String> range) {
        return elasticsearchRetriever.getSearchSuggestions(gender, style, types, colours, range);
    }

    @Path("/clothes")
    @GET()
    @Consumes(MediaType.APPLICATION_JSON)
    public List<Clothing> getClothing(@QueryParam("gender") String gender, @QueryParam("style") String style, @QueryParam("types") List<String> types, @QueryParam("colours") List<String> colours,
                                      @QueryParam("range") List<String> range, @QueryParam("incTerms") List<String> incTerms, @QueryParam("decTerms") List<String> decTerms) {
        return elasticsearchRetriever.getSearch(gender, style, types, colours, range, incTerms, decTerms);
    }

    @Path("/incrementTerms")
    @POST()
    @Consumes(MediaType.APPLICATION_JSON)
    public void incrementTerms(@QueryParam("style") String style, @QueryParam("terms") List<String> terms) {
        styleTermUpdater.incrementStyleTerms(style, terms);
    }

    @Path("/decrementTerms")
    @POST()
    @Consumes(MediaType.APPLICATION_JSON)
    public void decrementTerms(@QueryParam("style") String style, @QueryParam("terms") List<String> terms) {
        styleTermUpdater.decrementStyleTerms(style, terms);
    }

}
