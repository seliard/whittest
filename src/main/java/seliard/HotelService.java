package seliard;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import seliard.exceptions.HotelAlreadyExistsException;
import seliard.exceptions.HotelNotFoundException;
import seliard.exceptions.RestTemplateErrorHandler;
import seliard.exceptions.foursquare.FoursquareException;
import seliard.models.Hotel;
import seliard.models.HotelLocation;
import seliard.models.foursquare.FoursquareSearch;
import seliard.models.foursquare.Venue;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Santiago on 28/06/2017.
 */

@Service
public class HotelService {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    private RestTemplateBuilder restTemplateBuilder;
    private RestTemplateErrorHandler restTemplateErrorHandler;

    private static final String FOURSQUARE_DOMAIN = "api.foursquare.com";
    private static final String FOURSQUARE_PATH = "/v2/venues/search";
    private static final String FOURSQUARE_API_VERSION = "20161016";
    private static final String FOURSQUARE_CLIENT_ID = "CTBUOKXD3S41U3QN4ALR23ANGL40LWKJGE0VQC2GL4LYIXTC";
    private static final String FOURSQUARE_CLIENT_SECRET = "E2CYW0YRMGZPS5KDTNVBQAGQDWUMKMSZHNXRBJ1INVCLNBKC";

    @Autowired
    public HotelService(RestTemplateBuilder restTemplateBuilder, RestTemplateErrorHandler restTemplateErrorHandler) {
        this.restTemplateBuilder = restTemplateBuilder;
        this.restTemplateErrorHandler = restTemplateErrorHandler;
    }

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

    public List<Hotel> getHotels() {
        return hotelList;
    }

    public Hotel getHotel(String id) throws HotelNotFoundException{
        logger.info("Searching for hotel: " + id);
        Hotel hotel = hotelList.stream().filter(h -> h.getId().equalsIgnoreCase(id)).findFirst().orElse(null);
        if (hotel != null) {
            return hotel;
        } else {
            throw new HotelNotFoundException("Hotel " + id + " not found");
        }
    }

    public ResponseEntity addHotel(Hotel hotel) throws HotelAlreadyExistsException {
        logger.info("Trying to add a new hotel: " + hotel.getId());
        if (hotelExists(hotel.getId())) {
            throw new HotelAlreadyExistsException(hotel.getId());
        } else {
            hotelList.add(hotel);
            return ResponseEntity.ok("Hotel added successfully");
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

    public ResponseEntity updateHotel(String id, Hotel hotel) {
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

    public ResponseEntity deleteHotel(String id) {
        if (hotelExists(id)) {
            hotelList.removeIf(h -> h.getId().equals(id));
            return ResponseEntity.ok("Hotel " + id + " deleted successfully");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Hotel not found.");
        }
    }

    public List<Venue> getLocationsFrom4SQ(String id, String query, String radius) throws HotelNotFoundException, FoursquareException {
        Hotel hotel = getHotel(id);
        URI foursquareUri = getFoursquareURI(hotel, query, radius);

        logger.info(foursquareUri.toString());

        RestTemplate restTemplate = restTemplateBuilder
                .errorHandler(restTemplateErrorHandler)
                .build();

        ResponseEntity<FoursquareSearch> search = restTemplate.getForEntity(foursquareUri, FoursquareSearch.class);
        if (search.getStatusCode().is2xxSuccessful()) {
                return search.getBody().getResponse().getVenues();
        } else {
                throw new FoursquareException(search.getBody().getMeta().getErrorDetail());
        }
    }

    private URI getFoursquareURI(Hotel hotel, String query, String radius) {
        UriComponents uriComponentsBuilder = UriComponentsBuilder.newInstance()
                .scheme("https")
                .host(FOURSQUARE_DOMAIN)
                .path(FOURSQUARE_PATH)
                .queryParam("v", FOURSQUARE_API_VERSION)
                .queryParam("ll", hotel.getLoc().getLat() + "," + hotel.getLoc().getLon())
                .queryParam("query", query)
                .queryParam("intent", "browse")
                .queryParam("client_id", FOURSQUARE_CLIENT_ID)
                .queryParam("client_secret", FOURSQUARE_CLIENT_SECRET)
                .queryParam("radius", radius)
                .buildAndExpand();

        return uriComponentsBuilder.toUri();
    }
}
