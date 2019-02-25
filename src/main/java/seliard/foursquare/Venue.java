package seliard.foursquare;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * Created by Santiago on 29/06/2017.
 */

public class Venue {

    private String id;
    private String name;
    private Location location;
    private List<Category> categories;
    private String url;

    public Venue() {
    }

    public Venue(String id, String name, Location location, List<Category> categories, String url) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.categories = categories;
        this.url = url;
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

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
