package seliard;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import seliard.foursquare.FoursquareSearch;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Santiago on 28/06/2017.
 */

@Service
class HotelService {

    private List<Hotel> hotelList = new ArrayList<>(Arrays.asList(
            new Hotel("LONBLA", "London Blackfriars (Fleet Street)", new HotelLocation("1", "Blackfriars", 51.513104, -0.105613)),
            new Hotel("LONSOU", "London Southwark (Tate Modern)", new HotelLocation("2", "Southwark (Tate)", 51.505292, -0.100202)),
            new Hotel("SOUANC", "London Southwark (Bankside)", new HotelLocation("3", "Southwark (Bank)", 51.506724, -0.092649)),
            new Hotel("LONMON", "London Bank (Tower)", new HotelLocation("4", "Bank", 51.509607, -0.083538)),
            new Hotel("LONSTM", "hub London Covent Garden", new HotelLocation("5", "Covent Garden", 51.5099988, -0.1272863)),
            new Hotel("LONWAT", "London Waterloo (Westminster Bridge)", new HotelLocation("6", "Waterloo", 51.50318637663801, -0.11531352996826907)),
            new Hotel("LONCOU", "London County Hall", new HotelLocation("7", "County Hall", 51.501472, -0.11876)),
            new Hotel("LONALD", "London City (Aldgate)", new HotelLocation("8", "Aldgate", 51.5142363, -0.0697958)),
            new Hotel("LONLEI", "London Leicester Square", new HotelLocation("9", "Leicester Square", 51.511143, -0.13035)),
            new Hotel("KINPTI", "London Kings Cross", new HotelLocation("10", "Kings Cross", 51.532001, -0.122086))
    ));

    List<Hotel> getHotels() {
        return hotelList;
    }

    ResponseEntity<?> getHotel(String id) {
        if (hotelExists(id)) {
            return ResponseEntity.ok(hotelList.stream().filter(h -> h.getId().equalsIgnoreCase(id)).findFirst().get());
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(id + " not found");
        }
    }

    ResponseEntity addHotel(Hotel hotel) {
        if (hotel.getId().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Id can't be empty.");
        } else {
            if (hotelExists(hotel.getId())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(hotel.getId() + " already exists.");
            } else {
                if (verifyLat(hotel.getLoc().getLat())) {
                    if (verifyLon(hotel.getLoc().getLon())) {
                        hotelList.add(hotel);
                        return ResponseEntity.ok("Hotel added successfully");
                    } else {
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Lon should be a value within -180 and 180.");
                    }
                } else {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Lat should be a value within -90 and 90.");
                }
            }
        }
    }

    private boolean hotelExists(String id) {
        return hotelList.stream().anyMatch(h -> h.getId().equalsIgnoreCase(id));
    }

    private boolean verifyLat(double lat) {
        return lat >= -90 && lat <= 90;
    }

    private boolean verifyLon(double lon) {
        return lon >= -180 && lon <= 180;
    }

    ResponseEntity updateHotel(String id, Hotel hotel) {
        if (hotel.getId().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Id can't be empty.");
        } else {
            if (!verifyLat(hotel.getLoc().getLat())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Lat should be a value within -90 and 90.");
            }
            if (!verifyLon(hotel.getLoc().getLon())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Lon should be a value within -180 and 180.");
            }
            if (hotelExists(id)) {
                if (!id.equalsIgnoreCase(hotel.getId())) {
                    if (hotelExists(hotel.getId())) {
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Hotel id already exists.");
                    } else {
                        for (int i = 0; i < hotelList.size(); i++) {
                            Hotel h = hotelList.get(i);
                            if (h.getId().equalsIgnoreCase(id)) {
                                hotelList.set(i, hotel);
                                return ResponseEntity.ok("Hotel " + id + " updated successfully");
                            }
                        }
                    }
                } else {
                    for (int i = 0; i < hotelList.size(); i++) {
                        Hotel h = hotelList.get(i);
                        if (h.getId().equalsIgnoreCase(id)) {
                            hotelList.set(i, hotel);
                            return ResponseEntity.ok("Hotel " + id + " updated successfully");
                        }
                    }
                }
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Hotel not found.");
            }
        }
        return null;
    }

    ResponseEntity deleteHotel(String id) {
        if (hotelExists(id)) {
            hotelList.removeIf(h -> h.getId().equals(id));
            return ResponseEntity.ok("Hotel " + id + " deleted successfully");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Hotel not found.");
        }
    }

    ResponseEntity<?> getLocationsFrom4SQ(String id, String query, String radius) {
        if (hotelExists(id)) {
            ResponseEntity<Hotel> h = (ResponseEntity<Hotel>) getHotel(id);
            String protocol = "https";
            String domain = "api.foursquare.com";
            String endpoint = "/v2/venues/search";
            String v = "20161016";
            String ll = h.getBody().getLoc().getLat() + "," + h.getBody().getLoc().getLon();
            String intent = "browse";
            String clientId = "CTBUOKXD3S41U3QN4ALR23ANGL40LWKJGE0VQC2GL4LYIXTC";
            String clientSecret = "E2CYW0YRMGZPS5KDTNVBQAGQDWUMKMSZHNXRBJ1INVCLNBKC";

            String apiCall = protocol + "://" + domain + endpoint + "?v=" + v + "&ll=" + ll + "&query=" + query + "&intent=" + intent + "&client_id=" + clientId + "&client_secret=" + clientSecret + "&radius=" + radius;

            System.out.println(apiCall);

            RestTemplate restTemplate = new RestTemplate();
            FoursquareSearch search = restTemplate.getForObject(apiCall, FoursquareSearch.class);

            if (search.getMeta().getCode() == 200) {
                return ResponseEntity.ok(search.getResponse().getVenues());
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Nothing found near " + ll + ".");
            }
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(id + " does not exist.");
        }
    }
}
