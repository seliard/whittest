package seliard.models;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * Created by Santiago on 28/06/2017.
 */
public class HotelLocation {

    private static final int LON_MIN = -180;
    private static final int LON_MAX = 180;

    private static final int LAT_MIN = -90;
    private static final int LAT_MAX = 90;

    @NotNull
    private String id;
    @NotNull
    private String name;
    @NotNull
    @Min(LAT_MIN)
    @Max(LAT_MAX)
    private double lat;
    @NotNull
    @Min(LON_MIN)
    @Max(LON_MAX)
    private double lon;

    public HotelLocation(String id, String name, double lat, double lon) {
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

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }
}
