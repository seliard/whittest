package seliard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Santiago on 28/06/2017.
 */

@RestController
public class HotelController {

    @Autowired
    private HotelService hotelService;

    @RequestMapping("/hotels")
    public List<Hotel> getHotels() {
        return hotelService.getHotels();
    }

    @RequestMapping(method = RequestMethod.POST, value = "/hotels")
    public ResponseEntity addHotel(@RequestBody Hotel hotel) {
        return hotelService.addHotel(hotel);
    }

    @RequestMapping("/hotels/{id}")
    public ResponseEntity<?> getHotel(@PathVariable String id) {
        return hotelService.getHotel(id);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/hotels/{id}")
    public ResponseEntity updateHotel(@RequestBody Hotel hotel, @PathVariable String id) {
        return hotelService.updateHotel(id, hotel);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/hotels/{id}")
    public ResponseEntity DeleteHotel(@PathVariable String id) {
        return hotelService.deleteHotel(id);
    }
}
