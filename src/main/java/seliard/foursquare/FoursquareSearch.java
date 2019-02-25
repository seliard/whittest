package seliard.foursquare;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by Santiago on 29/06/2017.
 */

public class FoursquareSearch {

    private Meta meta;
    private Response response;

    public FoursquareSearch() {
    }

    public FoursquareSearch(Meta meta, Response response) {
        this.meta = meta;
        this.response = response;
    }

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

}
