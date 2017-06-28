package seliard;

/**
 * Created by Santiago on 28/06/2017.
 */
public class Hotel {

    private String id;
    private String name;
    private HotelLocation loc;

    public Hotel() {
    }

    public Hotel(String id, String name, HotelLocation loc) {
        this.id = id;
        this.name = name;
        this.loc = loc;
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

    public HotelLocation getLoc() {
        return loc;
    }

    public void setLoc(HotelLocation loc) {
        this.loc = loc;
    }
}
