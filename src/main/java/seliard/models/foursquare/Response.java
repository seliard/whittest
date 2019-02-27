package seliard.models.foursquare;

import java.util.List;

/**
 * Created by Santiago on 29/06/2017.
 */

public class Response {

    private List<Venue> venues;

    public Response() {
    }

    public Response(List<Venue> venues) {
        this.venues = venues;
    }

    public List<Venue> getVenues() {
        return venues;
    }

    public void setVenues(List<Venue> venues) {
        this.venues = venues;
    }

}
