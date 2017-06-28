package seliard;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Santiago on 28/06/2017.
 */

@Service
public class HotelService {

    private List<Hotel> hotelList = new ArrayList<>(Arrays.asList(
            new Hotel("LONBLA","London Blackfriars (Fleet Street)",new HotelLocation("1","Blackfriars",51.513104,-0.105613)),
            new Hotel("LONSOU","London Southwark (Tate Modern)", new HotelLocation("2","Southwark (Tate)",51.505292,-0.100202)),
            new Hotel("SOUANC","London Southwark (Bankside)", new HotelLocation("3","Southwark (Bank)",51.506724,-0.092649)),
            new Hotel("LONMON","London Bank (Tower)", new HotelLocation("4","Bank",51.509607,-0.083538)),
            new Hotel("LONSTM","hub London Covent Garden", new HotelLocation("5","Covent Garden",51.5099988,-0.1272863)),
            new Hotel("LONWAT","London Waterloo (Westminster Bridge)", new HotelLocation("6","Waterloo",51.50318637663801,-0.11531352996826907)),
            new Hotel("LONCOU","London County Hall", new HotelLocation("7","County Hall", 51.501472,-0.11876)),
            new Hotel("LONALD","London City (Aldgate)", new HotelLocation("8","Aldgate",51.5142363,-0.0697958)),
            new Hotel("LONLEI","London Leicester Square", new HotelLocation("9","Leicester Square",51.511143,-0.13035)),
            new Hotel("KINPTI","London Kings Cross", new HotelLocation("10","Kings Cross",51.532001,-0.122086))
    ));

    public List<Hotel> getHotels() {
        return hotelList;
    }

    public ResponseEntity<?> getHotel(String id) {
        if (hotelExists(id)){
            return ResponseEntity.ok(hotelList.stream().filter(h -> h.getId().equalsIgnoreCase(id)).findFirst().get());
        }
        else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(id+" not found");
        }
    }

    public ResponseEntity addHotel(Hotel hotel){
        if (hotel.getId().isEmpty()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Id can't be empty.");
        }
        else {
            if (hotelExists(hotel.getId())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(hotel.getId() + " already exists.");
            } else {
                if (verifyLat(hotel.getLoc().getLat()) == true) {
                    if (verifyLon(hotel.getLoc().getLon()) == true) {
                        hotelList.add(hotel);
                        return ResponseEntity.ok("Hotel added successfully");
                    }
                    else {
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Lon should be a value within -180 and 180.");
                    }
                }
                else {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Lat should be a value within -90 and 90.");
                }
            }
        }
    }

    public boolean hotelExists(String id) {
        if (hotelList.stream().filter(h -> h.getId().equalsIgnoreCase(id)).findFirst().isPresent()){
            return true;
        }
        else {
            return false;
        }
    }

    public boolean verifyLat(double lat){
        if (lat >= -90 && lat <= 90){
            return true;
        }
        else {
            return false;
        }
    }

    public boolean verifyLon(double lon){
        if (lon >= -180 && lon <= 180){
            return true;
        }
        else {
            return false;
        }
    }
}
