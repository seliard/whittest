package seliard.foursquare;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * Created by Santiago on 29/06/2017.
 */

public class Response {

    private List<Venue> venues;

    public Response(List<Venue> venues) {
        this.venues = venues;
    }

    public Response() {
    }

    public List<Venue> getVenues() {
        return venues;
    }

    public void setVenues(List<Venue> venues) {
        this.venues = venues;
    }

}
