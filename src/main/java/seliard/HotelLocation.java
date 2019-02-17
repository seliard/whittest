package seliard;

/**
 * Created by Santiago on 28/06/2017.
 */
public class HotelLocation {

    private String id;
    private String name;
    private double lat;
    private double lon;

    public HotelLocation() {
    }

    HotelLocation(String id, String name, double lat, double lon) {
        this.id = id;
        this.name = name;
        this.lat = lat;
        this.lon = lon;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }
}
