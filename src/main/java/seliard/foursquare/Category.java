package seliard.foursquare;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by Santiago on 29/06/2017.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class Category {

    private String name;

    public Category() {
    }

    public Category(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
