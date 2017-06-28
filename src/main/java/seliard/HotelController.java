package seliard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
