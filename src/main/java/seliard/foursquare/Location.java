package seliard.foursquare;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by Santiago on 29/06/2017.
 */

public class Location {

    private String[] formattedAddress;
    private int distance;
    private String postalCode;

    public Location() {
    }

    public Location(String[] formattedAddress, int distance, String postalCode) {
        this.formattedAddress = formattedAddress;
        this.distance = distance;
        this.postalCode = postalCode;
    }

    public String[] getFormattedAddress() {
        return formattedAddress;
    }

    public void setFormattedAddress(String[] formattedAddress) {
        this.formattedAddress = formattedAddress;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

}
